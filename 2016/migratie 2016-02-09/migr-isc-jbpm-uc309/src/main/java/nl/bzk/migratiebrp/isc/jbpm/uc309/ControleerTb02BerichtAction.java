/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.AkteOnbekendException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AkteEnum;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controlleer tb02 bericht op juistheid.
 */
@Component("uc309ControleerTb02BerichtAction")
public class ControleerTb02BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT = "2a. Fout";
    private static final String FOUT_MSG_GBA_GEMEENTE = "Verzendende gemeente (originator) is niet een GBA gemeente.";
    private static final String FOUT_AKTENUMMER_NIET_EENDUIDIG = "Niet alle voorkomens van 81.20 Aktenummer zijn gelijk";
    private static final String FOUT_INGANGSDATUM_NIET_GELIJK = "Niet all voorkomens van 85.10 Ingangsdatum geldigheid zijn gelijk";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String ONBEKENDE_FOUT_IN_INHOUD_BERICHT = "Onbekende fout in inhoud bericht";

    @Inject
    private GemeenteService gemeenteRegisterService;

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Map<String, Object> result = new HashMap<>();
        final Long berichtId = (Long) parameters.get("input");
        final Tb02Bericht input = (Tb02Bericht) berichtenDao.leesBericht(berichtId);
        boolean berichtFoutief = false;
        boolean stopControle = false;

        final StringBuilder foutBuilder = new StringBuilder();

        try {
            input.controleerBerichtOpCorrectheid();
        } catch (AkteOnbekendException aoe) {
            berichtFoutief = true;
            stopControle = true;
            foutBuilder.append(aoe.getMessage());
            foutBuilder.append(' ');
        } catch (BerichtInhoudException bie) {
            berichtFoutief = true;
            if (bie.getCause() instanceof AkteEnum.AkteEnumException) {
                for (final String melding : ((AkteEnum.AkteEnumException) bie.getCause()).getFoutMeldingen()) {
                    foutBuilder.append(melding);
                    foutBuilder.append(' ');
                }
            } else {
                foutBuilder.append(ONBEKENDE_FOUT_IN_INHOUD_BERICHT);
            }
        }

        if (!stopControle) {
            berichtFoutief = controleerBerichtInhoudelijk(input, berichtFoutief, foutBuilder);
        }

        if (berichtFoutief) {
            result.put(FOUTMELDING_VARIABELE, foutBuilder.toString().trim());
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        return result;
    }

    private boolean controleerBerichtInhoudelijk(final Tb02Bericht input, final boolean berichtFoutief, final StringBuilder foutBuilder) {
        boolean result = berichtFoutief;

        if (!controleerOriginator(input)) {
            result = true;
            foutBuilder.append(FOUT_MSG_GBA_GEMEENTE);
            foutBuilder.append(' ');
        }

        try {
            input.controleerOfRegisterGemeenteOvereenkomtMetVerzendendeGemeente();
        } catch (BerichtInhoudException bie) {
            result = true;
            foutBuilder.append(bie.getMessage());
            foutBuilder.append(' ');
        }

        if (!input.isAktenummerHetzelfdeAlsAktenummerInHeader()) {
            result = true;
            foutBuilder.append(FOUT_AKTENUMMER_NIET_EENDUIDIG);
            foutBuilder.append(' ');
        }

        if (!input.isIngangsdatumGelijkInMeegegevenAkten()) {
            result = true;
            foutBuilder.append(FOUT_INGANGSDATUM_NIET_GELIJK);
            foutBuilder.append(' ');
        }

        try {
            input.controleerGroepenOpWijzigingen();
        } catch (
            BerichtInhoudException
            | AkteOnbekendException bie)
        {
            result = true;
            foutBuilder.append(bie.getMessage());
            foutBuilder.append(' ');
        }

        return result;
    }

    private boolean controleerOriginator(final Tb02Bericht tb02Bericht) {
        final String originator = tb02Bericht.getBronGemeente();

        // Controle obv gemeente register of verzendende gemeente wel een GBA gemeente is
        final GemeenteRegister gemeenteRegister = gemeenteRegisterService.geefRegister();
        final Gemeente gemeente = gemeenteRegister.zoekGemeenteOpGemeenteCode(originator);

        return gemeente != null && Stelsel.GBA == gemeente.getStelsel();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.ValidationUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Deze actionhandler bepaalt of het ingelezen bulk bestand valide is.
 */
@Component("uc812ValideerBulkBestandAction")
public final class ValideerBulkBestandAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT = "3b. Fout";

    private static final String NIEUWE_REGEL_MET_ENTER = "\r\n";
    private static final String NIEUWE_REGEL = "\n";
    private static final String KOMMA = ",";
    private static final Integer LENGTE_GEMEENTE_CODE = 4;
    private static final Integer LENGTE_ANUMMER = 10;
    private static final String MELDING_FOUT_REGEL = "Fout in regel ";
    private static final String INPUT_PARAMETER_KEY = "input";
    private static final Integer MAXIMALE_INDEX_FOUTMELDING = 3996;

    @Inject
    private BerichtenDao berichtenDao;

    @Inject
    private GemeenteService gemeenteService;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Uc812Bericht uc812Bericht = (Uc812Bericht) berichtenDao.leesBericht((Long) parameters.get(INPUT_PARAMETER_KEY));

        final GemeenteRegister gemeenteRegister = gemeenteService.geefRegister();

        final List<String> foutRegels = valideerInhoudBulkBestand(uc812Bericht.getBulkSynchronisatievraag(), gemeenteRegister);
        if (foutRegels.size() != 0) {

            final Map<String, Object> result = new HashMap<>();

            final StringBuilder foutmelding = new StringBuilder();
            for (final String foutRegel : foutRegels) {
                foutmelding.append(foutRegel).append(".\n");
            }

            final String foutmeldingString;
            if (foutmelding.length() < MAXIMALE_INDEX_FOUTMELDING) {
                foutmeldingString = foutmelding.toString();
            } else {
                foutmeldingString = foutmelding.toString().substring(0, MAXIMALE_INDEX_FOUTMELDING) + "...";
            }

            result.put("foutafhandelingFoutmelding", foutmeldingString);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);

            LOG.debug("result: {}", result);
            return result;
        }

        return null;
    }

    private List<String> valideerInhoudBulkBestand(final String teformatterenTekst, final GemeenteRegister gemeenteRegister) {

        final List<String> foutRegels = new ArrayList<>();

        final String teformatterenTekstGeNormaliseerd = teformatterenTekst.replaceAll(NIEUWE_REGEL_MET_ENTER, NIEUWE_REGEL);
        final String[] regels = teformatterenTekstGeNormaliseerd.split(NIEUWE_REGEL);
        LOG.info("Aantal regels: " + regels.length);

        int huidigeRegel = 1;
        for (final String regel : regels) {

            final String[] regelGesplit = regel.split(KOMMA);

            if (regelGesplit.length != 2) {
                foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": Het aantal kolommen is niet 2. ");
            } else {
                final String gemeente = regelGesplit[0];
                if (LENGTE_GEMEENTE_CODE != gemeente.length()) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": De lengte van de gemeente code is niet " + LENGTE_GEMEENTE_CODE);
                } else if (!bepaalGbaGemeenten(gemeenteRegister, gemeente)) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": Gemeente met code " + gemeente + " is geen GBA-gemeente");
                }
                final String aNummer = regelGesplit[1];
                if (LENGTE_ANUMMER != aNummer.length()) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": De lengte van het a-nummer is niet " + LENGTE_ANUMMER);
                } else if (!ValidationUtil.valideerANummer(aNummer)) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": Het a-nummer " + aNummer + " is geen geldig a-nummer");
                }
            }

            huidigeRegel++;
        }

        return foutRegels;
    }

    private boolean bepaalGbaGemeenten(final GemeenteRegister gemeenteRegister, final String ingelezenGemeente) {

        if (gemeenteRegister.zoekGemeenteOpGemeenteCode(ingelezenGemeente) != null
            && Stelsel.GBA == gemeenteRegister.zoekGemeenteOpGemeenteCode(ingelezenGemeente).getStelsel())
        {
            return true;
        }

        return false;
    }

}

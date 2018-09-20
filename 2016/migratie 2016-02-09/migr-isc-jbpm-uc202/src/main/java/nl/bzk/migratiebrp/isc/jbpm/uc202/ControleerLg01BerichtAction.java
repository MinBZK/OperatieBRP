/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc202;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.Instantie;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.GemeenteService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controleer het (bericht in het) pl sync bericht.
 */
@Component("uc202ControleerLg01BerichtAction")
public final class ControleerLg01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "2b. Fout";
    private static final String FOUT_MSG_GBA_GEMEENTE = "Verzendende gemeente (originator) is niet een GBA gemeente.";
    private static final String FOUT_MSG_CENTRALE = "Ontvangende instantie (recipient) is niet een de centrale voorziening.";
    private static final String FOUT_MSG_INCONSISTENT = "Bericht payload is niet een geldig bericht (kop inconsistent met inhoud).";
    private static final String FOUT_MSG_ANR_KOP = "A-nummer kop incorrect.";
    private static final String FOUT_MSG_VORIG_ANR_KOP = "Vorig a-nummer kop incorrect.";
    private static final String FOUT_MSG_ORIGINATOR_KOP = "Originator kop incorrect.";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String WHITE_SPACE = " ";

    @Inject
    private GemeenteService gemeenteRegisterService;

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final Long berichtId = (Long) parameters.get("input");

        final Lg01Bericht input = (Lg01Bericht) berichtenDao.leesBericht(berichtId);
        final StringBuffer meldingen = new StringBuffer();
        final Map<String, Object> result = new HashMap<>();
        if (!controleerOriginator(input)) {
            LOG.debug(FOUT_MSG_GBA_GEMEENTE);
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_GBA_GEMEENTE);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        } else if (!controleerRecipient(input)) {
            LOG.debug(FOUT_MSG_CENTRALE);
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_CENTRALE);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);

        } else if (!controleerLg01Bericht(input, meldingen)) {
            LOG.debug(FOUT_MSG_INCONSISTENT + meldingen.toString());
            result.put(FOUTMELDING_VARIABELE, FOUT_MSG_INCONSISTENT + meldingen.toString());
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);
        }

        LOG.debug("result: {}", result);
        return result;
    }

    private boolean controleerRecipient(final Lg01Bericht lg01Bericht) {
        final String recipient = lg01Bericht.getDoelGemeente();
        return Instantie.Type.CENTRALE == Instantie.bepaalInstantieType(recipient);
    }

    private boolean controleerOriginator(final Lg01Bericht lg01Bericht) {
        final String originator = lg01Bericht.getBronGemeente();

        // Controle obv gemeente register of verzendende gemeente wel een GBA gemeente is
        final GemeenteRegister gemeenteRegister = gemeenteRegisterService.geefRegister();
        final Gemeente gemeente = gemeenteRegister.zoekGemeenteOpGemeenteCode(originator);

        return gemeente != null && Stelsel.GBA == gemeente.getStelsel();
    }

    private boolean controleerLg01Bericht(final Lg01Bericht bericht, final StringBuffer meldingen) {
        final String aNummerKop = bericht.getHeader(Lo3HeaderVeld.A_NUMMER);
        final String vorigANummerKop = bericht.getHeader(Lo3HeaderVeld.OUD_A_NUMMER);
        final String originator = bericht.getBronGemeente();

        final List<Lo3CategorieWaarde> inhoud = bericht.getCategorieen();
        final String aNummerInhoud = Lo3CategorieWaardeUtil.getElementWaarde(inhoud, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER);
        final String vorigANummerInhoud = Lo3CategorieWaardeUtil.getElementWaarde(inhoud, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ELEMENT_2010);
        final String gemeenteInhoud =
                Lo3CategorieWaardeUtil.getElementWaarde(inhoud, Lo3CategorieEnum.VERBLIJFPLAATS, 0, 0, Lo3ElementEnum.GEMEENTE_VAN_INSCHRIJVING);

        boolean result = true;
        if (emptyAnummer(aNummerKop) || !aNummerKop.equals(aNummerInhoud)) {
            LOG.debug(FOUT_MSG_ANR_KOP);
            meldingen.append(WHITE_SPACE).append(FOUT_MSG_ANR_KOP);
            result = false;
        }
        if (!emptyAnummer(vorigANummerKop) && !vorigANummerKop.equals(vorigANummerInhoud)) {
            LOG.debug(FOUT_MSG_VORIG_ANR_KOP);
            meldingen.append(WHITE_SPACE).append(FOUT_MSG_VORIG_ANR_KOP);
            result = false;
        }

        if (originator == null || "".equals(originator) || !originator.equals(gemeenteInhoud)) {
            LOG.debug(FOUT_MSG_ORIGINATOR_KOP);
            meldingen.append(WHITE_SPACE).append(FOUT_MSG_ORIGINATOR_KOP);
            result = false;
        }

        return result;
    }

    private static boolean emptyAnummer(final String anummer) {
        return anummer == null || "".equals(anummer) || "0000000000".equals(anummer);
    }
}

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
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import org.springframework.stereotype.Component;

/**
 * Controleer het (bericht in het) pl sync bericht.
 */
@Component("uc202ControleerLg01BerichtAction")
public final class ControleerLg01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String FOUT = "1b. Fout";
    private static final String FOUT_MSG_GBA_GEMEENTE = "Verzendende gemeente (originator) is niet een GBA gemeente.";
    private static final String FOUT_MSG_CENTRALE = "Ontvangende instantie (recipient) is niet een de centrale voorziening.";
    private static final String FOUT_MSG_INCONSISTENT = "Bericht payload is niet een geldig bericht (kop inconsistent met inhoud).";
    private static final String FOUT_MSG_ANR_KOP = "A-nummer kop incorrect.";
    private static final String FOUTMELDING_VARIABELE = "actieFoutmelding";
    private static final String WHITE_SPACE = " ";

    private final PartijService partijRegisterService;
    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param partijRegisterService partij register service
     * @param berichtenDao berichten dao
     */
    @Inject
    public ControleerLg01BerichtAction(final PartijService partijRegisterService, final BerichtenDao berichtenDao) {
        this.partijRegisterService = partijRegisterService;
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);
        final Long berichtId = (Long) parameters.get("input");

        final Lg01Bericht input = (Lg01Bericht) berichtenDao.leesBericht(berichtId);
        final StringBuilder meldingen = new StringBuilder();
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
        final String recipient = lg01Bericht.getDoelPartijCode();

        // Controle obv register of verzendende partij wel een GBA bijhouder is
        final PartijRegister partijRegister = partijRegisterService.geefRegister();
        final Partij partij = partijRegister.zoekPartijOpPartijCode(recipient);

        return partij != null && partij.isCentraleVoorziening() && Stelsel.BRP == partij.getStelsel();
    }

    private boolean controleerOriginator(final Lg01Bericht lg01Bericht) {
        final String originator = lg01Bericht.getBronPartijCode();

        // Controle obv register of verzendende partij wel een GBA bijhouder is
        final PartijRegister partijRegister = partijRegisterService.geefRegister();
        final Partij partij = partijRegister.zoekPartijOpPartijCode(originator);

        return partij != null && partij.isBijhouder() && Stelsel.GBA == partij.getStelsel();
    }

    private boolean controleerLg01Bericht(final Lg01Bericht bericht, final StringBuilder meldingen) {
        final String aNummerKop = bericht.getHeaderWaarde(Lo3HeaderVeld.A_NUMMER);

        final List<Lo3CategorieWaarde> inhoud = bericht.getCategorieen();
        final String aNummerInhoud = Lo3CategorieWaardeUtil.getElementWaarde(inhoud, Lo3CategorieEnum.PERSOON, 0, 0, Lo3ElementEnum.ANUMMER);

        boolean result = true;
        if (emptyAnummer(aNummerKop) || !aNummerKop.equals(aNummerInhoud)) {
            LOG.debug(FOUT_MSG_ANR_KOP);
            meldingen.append(WHITE_SPACE).append(FOUT_MSG_ANR_KOP);
            result = false;
        }

        return result;
    }

    private static boolean emptyAnummer(final String anummer) {
        return anummer == null || "".equals(anummer) || "0000000000".equals(anummer);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.persoon;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Hq01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een Hf01 fout bericht aan.
 */
@Component("uc1004MaakHf01BerichtAction")
public final class MaakHf01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao Berichten repo
     */
    @Inject
    public MaakHf01BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Hq01Bericht input = (Hq01Bericht) berichtenDao.leesBericht(berichtId);

        final String foutreden = (String) parameters.get(AdHocVragenConstanten.HF01_FOUTREDEN);
        if (foutreden == null || "".equals(foutreden)) {
            throw new IllegalStateException("Geen foutreden doorgegeven in proces voor Hf01 bericht.");
        }
        final String gemeente = (String) parameters.get(AdHocVragenConstanten.HF01_GEMEENTE_KEY);
        final String aNummer = (String) parameters.get(AdHocVragenConstanten.HF01_ANUMMER_KEY);

        // Fout bericht
        final Hf01Bericht hf01Bericht = new Hf01Bericht();
        hf01Bericht.setCorrelationId(input.getMessageId());
        hf01Bericht.setCategorieen(input.getCategorieen());
        hf01Bericht.setBronPartijCode(input.getDoelPartijCode());
        hf01Bericht.setDoelPartijCode(input.getBronPartijCode());

        hf01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden);
        hf01Bericht.setHeader(Lo3HeaderVeld.GEMEENTE, gemeente);
        hf01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, aNummer);

        hf01Bericht.setHeader(Lo3HeaderVeld.AANTAL, input.getHeaderWaarde(Lo3HeaderVeld.AANTAL));
        hf01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, input.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        hf01Bericht.setCategorieen(input.getCategorieen());

        // opslaan
        final Long hf01BerichtId = berichtenDao.bewaarBericht(hf01Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put(AdHocVragenConstanten.HF01_BERICHT, hf01BerichtId);

        LOG.info("result: {}", result);
        return result;
    }

}

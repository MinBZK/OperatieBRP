/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1004.adres;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Xq01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1004.AdHocVragenConstanten;
import org.springframework.stereotype.Component;

/**
 * Maakt een Xf01 fout bericht aan.
 */
@Component("uc1004MaakXf01BerichtAction")
public final class MaakXf01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao Berichten repo
     */
    @Inject
    public MaakXf01BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Xq01Bericht input = (Xq01Bericht) berichtenDao.leesBericht(berichtId);

        final String foutreden = (String) parameters.get(AdHocVragenConstanten.XF01_FOUTREDEN);
        if (foutreden == null || "".equals(foutreden)) {
            throw new IllegalStateException("Geen foutreden doorgegeven in proces voor Xf01 bericht.");
        }

        // Fout bericht
        final Xf01Bericht xf01Bericht = new Xf01Bericht();
        xf01Bericht.setCorrelationId(input.getMessageId());
        xf01Bericht.setCategorieen(input.getCategorieen());
        xf01Bericht.setBronPartijCode(input.getDoelPartijCode());
        xf01Bericht.setDoelPartijCode(input.getBronPartijCode());

        xf01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden);
        xf01Bericht.setHeader(Lo3HeaderVeld.AANTAL, input.getHeaderWaarde(Lo3HeaderVeld.AANTAL));
        xf01Bericht.setHeader(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN, input.getHeaderWaarde(Lo3HeaderVeld.GEVRAAGDE_RUBRIEKEN));
        xf01Bericht.setHeader(Lo3HeaderVeld.ADRESFUNCTIE, input.getHeaderWaarde(Lo3HeaderVeld.ADRESFUNCTIE));
        xf01Bericht.setHeader(Lo3HeaderVeld.IDENTIFICATIE, input.getHeaderWaarde(Lo3HeaderVeld.IDENTIFICATIE));

        xf01Bericht.setCategorieen(input.getCategorieen());

        // opslaan
        final Long xf01BerichtId = berichtenDao.bewaarBericht(xf01Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put(AdHocVragenConstanten.XF01_BERICHT, xf01BerichtId);

        LOG.info("result: {}", result);
        return result;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import org.springframework.stereotype.Component;

/**
 * Maakt een Af11 fout bericht aan.
 */
@Component("uc1003MaakAf11BerichtAction")
public final class MaakAf11BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakAf11BerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Av01Bericht input = (Av01Bericht) berichtenDao.leesBericht(berichtId);

        final String foutreden = (String) parameters.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY);
        if (foutreden == null || "".equals(foutreden)) {
            throw new IllegalStateException("Geen foutreden doorgegeven in proces voor Af11 bericht.");
        }
        final String gemeente = (String) parameters.get(AfnemersIndicatieJbpmConstants.AF0X_GEMEENTE_KEY);
        final String anummer = (String) parameters.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY);

        // Fout bericht
        final Af11Bericht af11Bericht = new Af11Bericht();
        af11Bericht.setCorrelationId(input.getMessageId());
        af11Bericht.setANummer(input.getANummer());
        af11Bericht.setBronPartijCode(input.getDoelPartijCode());
        af11Bericht.setDoelPartijCode(input.getBronPartijCode());

        af11Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden);
        af11Bericht.setHeader(Lo3HeaderVeld.GEMEENTE, gemeente);
        if (AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_I.equals(foutreden)) {
            af11Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, anummer);
        }

        // opslaan
        final Long af11BerichtId = berichtenDao.bewaarBericht(af11Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put("af11Bericht", af11BerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

}

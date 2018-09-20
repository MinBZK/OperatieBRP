/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AfnemersIndicatieJbpmConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maakt een Af01 fout bericht aan.
 */
@Component("uc1003MaakAf01BerichtAction")
public final class MaakAf01BerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Ap01Bericht input = (Ap01Bericht) berichtenDao.leesBericht(berichtId);

        final String foutreden = (String) parameters.get(AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_KEY);
        if (foutreden == null || "".equals(foutreden)) {
            throw new IllegalStateException("Geen foutreden doorgegeven in proces voor Af01 bericht.");
        }
        final String gemeente = (String) parameters.get(AfnemersIndicatieJbpmConstants.AF0X_GEMEENTE_KEY);
        final String anummer = (String) parameters.get(AfnemersIndicatieJbpmConstants.AF0X_ANUMMER_KEY);

        // Fout bericht
        final Af01Bericht af01Bericht = new Af01Bericht();
        af01Bericht.setCorrelationId(input.getMessageId());
        af01Bericht.setCategorieen(input.getCategorieen());
        af01Bericht.setBronGemeente(input.getDoelGemeente());
        af01Bericht.setDoelGemeente(input.getBronGemeente());

        af01Bericht.setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden);
        af01Bericht.setHeader(Lo3HeaderVeld.GEMEENTE, gemeente);
        if (AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_B.equals(foutreden)
            || AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_I.equals(foutreden)
            || AfnemersIndicatieJbpmConstants.AF0X_FOUTREDEN_V.equals(foutreden))
        {
            af01Bericht.setHeader(Lo3HeaderVeld.A_NUMMER, anummer);
        }

        // opslaan
        final Long af01BerichtId = berichtenDao.bewaarBericht(af01Bericht);
        final Map<String, Object> result = new HashMap<>();
        result.put("af01Bericht", af01BerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

}

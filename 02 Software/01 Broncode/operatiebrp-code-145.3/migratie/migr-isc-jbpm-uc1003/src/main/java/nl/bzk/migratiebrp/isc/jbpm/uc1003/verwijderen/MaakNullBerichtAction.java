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
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;

import org.springframework.stereotype.Component;

/**
 * Maakt een Null bericht aan.
 */
@Component("uc1003MaakNullBerichtAction")
public final class MaakNullBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakNullBerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // Input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Av01Bericht input = (Av01Bericht) berichtenDao.leesBericht(berichtId);

        // Null bericht
        final NullBericht nullBericht = new NullBericht();
        nullBericht.setCorrelationId(input.getMessageId());
        nullBericht.setBronPartijCode(input.getDoelPartijCode());
        nullBericht.setDoelPartijCode(input.getBronPartijCode());

        // opslaan
        final Long nullBerichtId = berichtenDao.bewaarBericht(nullBericht);
        final Map<String, Object> result = new HashMap<>();
        result.put("nullBericht", nullBerichtId);

        LOG.debug("result: {}", result);
        return result;
    }

}

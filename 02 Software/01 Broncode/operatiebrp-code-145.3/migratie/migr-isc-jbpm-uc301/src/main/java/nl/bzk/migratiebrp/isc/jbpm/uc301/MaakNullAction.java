/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc301;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Iv01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maak null bericht.
 */
@Component("uc301MaakNullAction")
public final class MaakNullAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    protected MaakNullAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final Iv01Bericht iv01Bericht = (Iv01Bericht) berichtenDao.leesBericht((Long) parameters.get("iv01Bericht"));

        final NullBericht nullBericht = new NullBericht();
        nullBericht.setCorrelationId(iv01Bericht.getMessageId());
        nullBericht.setBronPartijCode(iv01Bericht.getDoelPartijCode());
        nullBericht.setDoelPartijCode(iv01Bericht.getBronPartijCode());

        final Map<String, Object> result = new HashMap<>();
        result.put("nullBericht", berichtenDao.bewaarBericht(nullBericht));

        return result;
    }

}

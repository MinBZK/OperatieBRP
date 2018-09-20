/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.NullBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * 
 * Maakt een NULL-bericht aan zodat deze verzonden kan worden.
 * 
 */
@Component("uc306MaakNullAction")
public class MaakNullAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final Tv01Bericht response = (Tv01Bericht) parameters.get("tv01Bericht");

        final NullBericht nullBericht = new NullBericht();
        nullBericht.setCorrelationId(response.getMessageId());

        // set originator en recipient
        nullBericht.setBronGemeente(response.getDoelGemeente());
        nullBericht.setDoelGemeente(response.getBronGemeente());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("nullBericht", nullBericht);

        return result;
    }
}

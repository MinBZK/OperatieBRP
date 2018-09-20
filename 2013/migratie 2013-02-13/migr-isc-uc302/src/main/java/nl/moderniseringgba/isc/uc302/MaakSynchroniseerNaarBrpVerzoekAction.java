/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een sync store bericht obv het binnengekomen ib01 bericht.
 */
@Component("uc302MaakSynchroniseerNaarBrpVerzoekAction")
public final class MaakSynchroniseerNaarBrpVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final Ib01Bericht ib01Bericht = (Ib01Bericht) parameters.get("ib01Bericht");

        final SynchroniseerNaarBrpVerzoekBericht storeBericht = new SynchroniseerNaarBrpVerzoekBericht();
        storeBericht.setLo3Persoonslijst(ib01Bericht.getLo3Persoonslijst());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("synchroniseerNaarBrpVerzoekBericht", storeBericht);
        return result;
    }
}

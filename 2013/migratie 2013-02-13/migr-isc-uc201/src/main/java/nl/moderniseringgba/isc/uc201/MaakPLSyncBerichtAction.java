/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc201;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.mvi.impl.PlSyncBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak PL Sync.
 */
@Component("uc201MaakPLSyncBericht")
public final class MaakPLSyncBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                (LeesUitBrpAntwoordBericht) parameters.get("leesUitBrpAntwoordBericht");

        final PlSyncBericht plSyncBericht = new PlSyncBericht();
        plSyncBericht.setLo3Persoonslijst(leesUitBrpAntwoordBericht.getLo3Persoonslijst());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("plSyncBericht", plSyncBericht);

        return result;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc311;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.WijzigingANummerSignaalBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak query.
 */
@Component("uc311MaakLeesUitBrpVerzoekAction")
public final class MaakLeesUitBrpVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final WijzigingANummerSignaalBericht input = (WijzigingANummerSignaalBericht) parameters.get("input");

        final LeesUitBrpVerzoekBericht leesUitBrpVerzoekBericht =
                new LeesUitBrpVerzoekBericht(input.getNieuwANummer());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("leesUitBrpVerzoekBericht", leesUitBrpVerzoekBericht);
        return result;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc308;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.BrpBijhoudingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarLo3VerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Converteer het het Erkenningbericht.
 */
@Component("uc308MaakConverteerNaarLo3VerzoekBerichtAction")
public final class MaakConverteerNaarLo3VerzoekBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final BrpBijhoudingVerzoekBericht brpBijhoudingVerzoekBericht =
                (BrpBijhoudingVerzoekBericht) parameters.get(UC308Constants.BRP_BIJHOUDING_VERZOEK_BERICHT);
        final ConverteerNaarLo3VerzoekBericht converteerNaarLo3VerzoekBericht =
                new ConverteerNaarLo3VerzoekBericht(brpBijhoudingVerzoekBericht.getBrpPersoonslijst());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(UC308Constants.CONVERTEER_NAAR_LO3_VERZOEK, converteerNaarLo3VerzoekBericht);

        return result;

    }
}

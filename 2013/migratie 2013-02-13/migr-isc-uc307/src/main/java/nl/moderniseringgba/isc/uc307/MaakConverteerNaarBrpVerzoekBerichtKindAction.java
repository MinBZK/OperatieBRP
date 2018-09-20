/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maakt een ConverteerNaarBrpVerzoekBericht en zet deze klaar voor verzending.
 */
@Component("uc307MaakConverteerNaarBrpVerzoekBerichtKindAction")
public final class MaakConverteerNaarBrpVerzoekBerichtKindAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Tb01Bericht tb01Bericht = (Tb01Bericht) parameters.get(UC307Constants.TB01_BERICHT);

        final Lo3Persoonslijst lo3PL = tb01Bericht.getLo3Persoonslijst();

        final ConverteerNaarBrpVerzoekBericht converteerNaarBrpVerzoek = new ConverteerNaarBrpVerzoekBericht(lo3PL);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(UC307Constants.CONVERTEER_NAAR_BRP_VERZOEK, converteerNaarBrpVerzoek);

        return result;
    }

}

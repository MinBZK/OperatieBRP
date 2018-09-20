/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3PersoonslijstFormatter;
import nl.moderniseringgba.isc.esb.message.sync.generated.ConverteerNaarBrpVerzoekType;
import nl.moderniseringgba.isc.esb.message.sync.impl.ConverteerNaarBrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maakt een ConverteerNaarBrpVerzoekBericht voor de moeder en zet deze klaar voor verzending.
 */
@Component("uc307MaakConverteerNaarBrpVerzoekBerichtMoederAction")
public final class MaakConverteerNaarBrpVerzoekBerichtMoederAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht =
                (LeesUitBrpAntwoordBericht) parameters.get("syncBericht");
        final ConverteerNaarBrpVerzoekType verzoekType = new ConverteerNaarBrpVerzoekType();

        final String lo3BerichtAsTeletexString =
                Lo3Inhoud.formatInhoud(new Lo3PersoonslijstFormatter().format(leesUitBrpAntwoordBericht
                        .getLo3Persoonslijst()));

        verzoekType.setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
        final ConverteerNaarBrpVerzoekBericht verzoekNaarBrpVerzoek =
                new ConverteerNaarBrpVerzoekBericht(verzoekType);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(UC307Constants.CONVERTEER_NAAR_BRP_VERZOEK, verzoekNaarBrpVerzoek);

        return result;
    }
}

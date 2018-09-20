/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.esb.message.sync.impl.LeesUitBrpAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak Ib01.
 */
@Component("uc301MaakIb01Action")
public final class MaakIb01Action implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String IB01_BERICHT = "ib01Bericht";

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final LeesUitBrpAntwoordBericht response =
                (LeesUitBrpAntwoordBericht) parameters.get("leesUitBrpAntwoordBericht");

        final Ib01Bericht ib01Bericht = new Ib01Bericht();
        ib01Bericht.setHeader(Lo3HeaderVeld.STATUS, "A");
        ib01Bericht.setLo3Persoonslijst(response.getLo3Persoonslijst());
        final Object herhaling = parameters.get("ib01Herhaling");
        if (herhaling != null) {
            ib01Bericht.setHeader(Lo3HeaderVeld.HERHALING, String.valueOf(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) parameters.get(IB01_BERICHT);
            ib01Bericht.setMessageId(orgineel.getMessageId());
        }

        final Ii01Bericht ii01Bericht = (Ii01Bericht) parameters.get("input");
        ib01Bericht.setBronGemeente(ii01Bericht.getDoelGemeente());
        ib01Bericht.setDoelGemeente(ii01Bericht.getBronGemeente());
        ib01Bericht.setCorrelationId(ii01Bericht.getMessageId());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(IB01_BERICHT, ib01Bericht);

        return result;
    }
}

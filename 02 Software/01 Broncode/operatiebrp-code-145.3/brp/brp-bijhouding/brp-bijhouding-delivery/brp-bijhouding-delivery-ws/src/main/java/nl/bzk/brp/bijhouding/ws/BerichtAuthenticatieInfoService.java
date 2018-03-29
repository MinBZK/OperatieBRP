/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws;

import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;

import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Deze class bevat de kennis om de authorisatie informatie (OIN info) uit de request header te halen en toe te voegen
 * aan het bijhouding bericht.
 */
@Service
public final class BerichtAuthenticatieInfoService {

    private static final String OIN_ONDERTEKENAAR = "oin-ondertekenaar";
    private static final String OIN_TRANSPORTEUR = "oin-transporteur";

    /**
     * Vult het bericht aan met authenticatie informatie.
     *
     * @param message                  oorspronkelijke (niet geparste) bericht
     * @param bijhoudingVerzoekBericht het bericht
     * @return het aangevulde bericht
     */
    public BijhoudingVerzoekBericht vulBerichtAanMetOinGegevensUitHttpHeader(
            final Message message,
            final BijhoudingVerzoekBericht bijhoudingVerzoekBericht) {
        final HttpServletRequest httpRequest = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        bijhoudingVerzoekBericht.setOinWaardeOndertekenaar(httpRequest.getHeader(OIN_ONDERTEKENAAR));
        bijhoudingVerzoekBericht.setOinWaardeTransporteur(httpRequest.getHeader(OIN_TRANSPORTEUR));
        return bijhoudingVerzoekBericht;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.verstuurder;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.soap.SOAPFaultException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.processors.xml.XmlException;
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils;
import nl.bzk.brp.funqmachine.verstuurder.soap.ServicesNietBereikbaarException;
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapClient;
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters;
import org.w3c.dom.Node;

/**
 * Soap verstuurder.
 */
public final class SoapVerstuurder {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<QName, SoapClient> clientMap = new HashMap<>();

    /**
     * Verstuurt het bericht.
     * @param brpBericht het saop bericht
     * @param params de parameters
     * @return het antwoord of een soap-fault
     * @throws XmlException Als er iets fout gaat tijdens XML bewerkingen
     */
    public String send(final String brpBericht, final SoapParameters params) throws XmlException {
        LOGGER.info("SOAP verstuur naar {}", params);

        final SoapClient client;
        if (clientMap.containsKey(params.getPortQName())) {
            client = clientMap.get(params.getPortQName());
            LOGGER.info("SOAP client is cached");
        } else {
            client = new SoapClient(params);
            clientMap.put(params.getPortQName(), client);
        }

        try {
            final Node response = client.verzendBerichtNaarService(brpBericht);
            return XmlUtils.toXmlString(response.getFirstChild());
        } catch (SOAPFaultException sfe) {
            // geef SOAPFault terug als bericht
            LOGGER.error("Fout tijdens versturen bericht. Fout wordt terug gegeven.", sfe);
            final Node fault = new DOMSource(sfe.getFault()).getNode();
            return XmlUtils.toXmlString(fault);
        } catch (final SOAPException e) {
            LOGGER.error(String.format("Fout %s met XML bericht:%n%s", e.getMessage(), brpBericht));
            throw new ServicesNietBereikbaarException("Kon SOAP-service niet bereiken. Foutmelding: " + e.getMessage(), e);
        }
    }
}

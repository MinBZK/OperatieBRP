/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.soap;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.soap.SOAPFaultException;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.util.XmlUtils;
import org.w3c.dom.Node;

/**
 *
 */
public class SoapVerstuurder {
    static final Logger LOGGER = LoggerFactory.getLogger();

    private final Map<QName, SoapClient> clientMap = new HashMap<>();

    /**
     *
     * @param brpBericht
     * @param params
     * @return
     */
    String send(String brpBericht, SoapParameters params) {

        LOGGER.info("SOAP verstuur naar {}", params);

        SoapClient client;
        if (clientMap.containsKey(params.getPortQName())) {
            client = clientMap.get(params.getPortQName());
            LOGGER.info("SOAP client is cached");
        } else {
            client = new SoapClient(params);
            clientMap.put(params.getPortQName(), client);
        }

        try {
            Node response = client.verzendBerichtNaarService(brpBericht);
            return XmlUtils.toXmlString(response);
        } catch (SOAPFaultException sfe) {
            // geef SOAPFault terug als bericht
            Node fault = new DOMSource(sfe.getFault()).getNode();
            return XmlUtils.toXmlString(fault);
        } catch (Exception e) {
            //LOGGER.error 'Fout "{}" met XML bericht:\n{}', e.message, brpBericht
            throw new ServicesNietBereikbaarException("Kon SOAP-service niet bereiken. Foutmelding: $e.message", e);
        }
    }
}

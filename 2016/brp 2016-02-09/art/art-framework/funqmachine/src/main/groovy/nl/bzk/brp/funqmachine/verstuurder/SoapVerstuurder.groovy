package nl.bzk.brp.funqmachine.verstuurder

import javax.xml.namespace.QName
import javax.xml.transform.dom.DOMSource
import javax.xml.ws.soap.SOAPFaultException
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import nl.bzk.brp.funqmachine.verstuurder.soap.ServicesNietBereikbaarException
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapClient
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Node

/**
 *
 */
class SoapVerstuurder {
    static final Logger LOGGER = LoggerFactory.getLogger(SoapVerstuurder)

    private final Map<QName, SoapClient> clientMap = new HashMap<>()

    /**
     *
     * @param brpBericht
     * @param params
     * @return
     */
    String send(String brpBericht, SoapParameters params) {

        LOGGER.info("SOAP verstuur naar {}", params)

        SoapClient client;
        if (clientMap.containsKey(params.getPortQName())) {
            client = clientMap.get(params.getPortQName())
            LOGGER.info("SOAP client is cached")
        } else {
            client = new SoapClient(params)
            clientMap.put(params.getPortQName(), client)
        }

        try {
            Node response = client.verzendBerichtNaarService(brpBericht)
            return XmlUtils.toXmlString(response)
        } catch (SOAPFaultException sfe) {
            // geef SOAPFault terug als bericht
            Node fault = new DOMSource(sfe.fault).node
            return XmlUtils.toXmlString(fault)
        } catch (Exception e) {
            LOGGER.error 'Fout "{}" met XML bericht:\n{}', e.message, brpBericht
            throw new ServicesNietBereikbaarException("Kon SOAP-service niet bereiken. Foutmelding: $e.message", e)
        }
    }
}

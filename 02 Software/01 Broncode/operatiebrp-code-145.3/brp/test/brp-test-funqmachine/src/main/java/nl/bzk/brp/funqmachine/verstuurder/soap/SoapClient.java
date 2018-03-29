/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.verstuurder.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext;
import nl.bzk.brp.service.algemeen.request.OIN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * SoapClient voor het versturen van soap berichten.
 */
public class SoapClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);
    private URL wsdlURL = null;

    /**
     * Constructor.
     * @param params parameters voor het ophalen van WSDL en sturen van de requests
     */
    public SoapClient(final SoapParameters params) {
        try {
            wsdlURL = params.getWsdlURL();
        } catch (MalformedURLException e) {
            LOGGER.error("Exception creating SoapClient: {}", e.getMessage());
            throw new ServicesNietBereikbaarException("Kan geen SoapClient creeren naar " + wsdlURL, e);
        }

    }

    private InputStream getInputStream(final String bericht) throws IOException {
        return new ByteArrayInputStream(bericht.getBytes("UTF-8"));
    }

    /**
     * Standaard methode voor het verzenden van een bericht naar de WebService in een integratietest. De opgegeven
     * naam van het bericht bestand zal worden ingelezen en als de body van het verzoek naar de webservice worden
     * verstuurd, waarbij deze methode tevens voor het toevoegen van de benodigde SOAP headers etc. zorgt. Uit het
     * antwoord dat terugkomt van de nl.sandersmee.testtool.ontvanger wordt de body geextraheerd en als {@link Node} geretourneerd
     * door deze methode.
     * @param berichtBestand de bestandsnaam die verwijst naar een bestand met daarin de content die moet worden verstuurd.
     * @return de body van het antwoord, ontzien van allerlei niet direct relevante SOAP zaken.
     */
    public Node verzendBerichtNaarService(final String berichtBestand) throws SOAPException {
        try {
            // Bouw het initiele request bericht
            final SOAPMessage request = bouwInitieleRequestBericht(getInputStream(berichtBestand));

            // Haal het antwoord op
            final AutAutContext.VerzoekAutorisatie verzoek = AutAutContext.get().getAutorisatieVoorVerzoek();
            final SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
            final SOAPConnection connection = factory.createConnection();
            if (verzoek.getOinOndertekenaar() != null) {
                request.getMimeHeaders().addHeader(OIN.OIN_ONDERTEKENAAR, verzoek.getOinOndertekenaar());
            }

            if (verzoek.getOinTransporteur() != null) {
                request.getMimeHeaders().addHeader(OIN.OIN_TRANSPORTEUR, verzoek.getOinTransporteur());
            }

            final SOAPMessage response = connection.call(request, wsdlURL);
            printSOAPMessage(response);
            // Extraheer de content uit het antwoord en retourneer deze.
            return extraheerAntwoordUitSoapBericht(response);
        } catch (final IOException | TransformerConfigurationException | ParserConfigurationException | SAXException | SOAPException e) {
            LOGGER.error("Fout tijdens verzenden van SOAP bericht", e);
            throw new SOAPException(e);
        }
    }

    private SOAPMessage bouwInitieleRequestBericht(final InputStream inputStream)
            throws ParserConfigurationException, IOException, SAXException, SOAPException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        final Document request = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

        final SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPBody().addDocument(request);
        return soapMessage;
    }

    private Node extraheerAntwoordUitSoapBericht(final SOAPMessage soapMessage) throws TransformerConfigurationException, SOAPException {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        final DOMSource source = new DOMSource(soapMessage.getSOAPBody());
        return source.getNode();
    }

    private void printSOAPMessage(final SOAPMessage msg) {
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            LOGGER.info(out.toString(Charset.defaultCharset().name()));
        } catch (final SOAPException | IOException e) {
            LOGGER.error("Fout tijdens printen SOAP message", e);
        }
    }
}

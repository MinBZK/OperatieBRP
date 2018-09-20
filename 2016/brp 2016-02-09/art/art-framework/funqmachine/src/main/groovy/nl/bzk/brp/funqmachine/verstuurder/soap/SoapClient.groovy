package nl.bzk.brp.funqmachine.verstuurder.soap

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.soap.MessageFactory
import javax.xml.soap.SOAPException
import javax.xml.soap.SOAPMessage
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.ws.Dispatch
import javax.xml.ws.Service
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.soap.AddressingFeature
import nl.bzk.brp.funqmachine.jbehave.context.AutAutContext
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import nl.bzk.brp.util.AutorisatieOffloadGegevens
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.SAXException

/**
 * SoapClient voor het versturen van soap berichten.
 */
class SoapClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class)
    private static final int SOAP_REQUEST_TIMEOUT_IN_MILLIS = 5 * 60 * 1000

    private Dispatch<SOAPMessage> dispatcher

    /**
     * Constructor.
     * @param params parameters voor het ophalen van WSDL en sturen van de requests
     */
    SoapClient(final SoapParameters params) {
        try {
            initDispatcher(params)
        } catch (ServicesNietBereikbaarException e) {
            LOGGER.error 'Exception creating SoapClient: {}', e.message
            throw new ServicesNietBereikbaarException("Kan geen SoapClient creeren naar ${params.wsdlURL}", e)
        }
    }

    protected InputStream getInputStream(final String bericht) throws IOException {
        return new ByteArrayInputStream(bericht.getBytes('UTF-8'))
    }

    /**
     * Standaard methode voor het verzenden van een bericht naar de WebService in een integratietest. De opgegeven
     * naam van het bericht bestand zal worden ingelezen en als de body van het verzoek naar de webservice worden
     * verstuurd, waarbij deze methode tevens voor het toevoegen van de benodigde SOAP headers etc. zorgt. Uit het
     * antwoord dat terugkomt van de nl.sandersmee.testtool.ontvanger wordt de body geextraheerd en als {@link org.w3c.dom.Node} geretourneerd
     * door deze methode.
     *
     * @param berichtBestand de bestandsnaam die verwijst naar een bestand met daarin de content die moet worden
     *                       verstuurd.
     * @return de body van het antwoord, ontzien van allerlei niet direct relevante SOAP zaken.
     * @throws javax.xml.crypto.dsig.XMLSignatureException
     * @throws javax.xml.crypto.MarshalException
     */
    public Node verzendBerichtNaarService(final String berichtBestand) throws
        IOException, SOAPException, SAXException, ParserConfigurationException,
        TransformerConfigurationException {
        // Bouw het initiele request bericht
        SOAPMessage request = bouwInitieleRequestBericht(getInputStream(berichtBestand));

        // Haal het antwoord op
        def verzoek = AutAutContext.get().getAutorisatieVoorVerzoek()
        final Map<String, List<String>> headers = new HashMap<>();
        headers.put(AutorisatieOffloadGegevens.OIN_ONDERTEKENAAR, Collections.singletonList(verzoek.getOinOndertekenaar()));
        headers.put(AutorisatieOffloadGegevens.OIN_TRANSPORTEUR, Collections.singletonList(verzoek.getOinTransporteur()));
        dispatcher.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        SOAPMessage response = dispatcher.invoke(request);

        // Extraheer de content uit het antwoord en retourneer deze.
        return extraheerAntwoordUitSoapBericht(response);
    }

    /*
     * Bouwt de initiele request op basis van de opgegeven {@link java.io.InputStream}. De inputstream bevat de
     * content, waarbij deze methode de content omzet naar een geldige {@link javax.xml.soap.SOAPMessage} request.
     *
     * @param inputStream de inpustream naar de content voor de request.
     * @return een {@link javax.xml.soap.SOAPMessage} met daarin de opgegeven content uit de inputstream.
     */
    private SOAPMessage bouwInitieleRequestBericht(final InputStream inputStream) throws ParserConfigurationException,
        IOException, SAXException, SOAPException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document request = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPBody().addDocument(request);
        return soapMessage;
    }

    /*
     * Deze methode extraheert de werkelijke content uit het SOAP antwoord bericht.
     *
     * @param soapMessage het SOAP bericht waaruit de content gehaald wordt.
     * @return een {@link org.w3c.dom.Node} met de werkelijke uit het bericht gehaalde content.
     */
    private Node extraheerAntwoordUitSoapBericht(final SOAPMessage soapMessage) throws
        TransformerConfigurationException, SOAPException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(soapMessage.getSOAPBody());
        return source.getNode();
    }

    /*
     * Initialiseert de standaard {@link javax.xml.ws.Dispatch} instantie die gebruikt wordt om de KERN Service aan te
     * roepen.
     *
     * @throws java.net.MalformedURLException indien de URL van de WSDL niet correct is.
     */
    private void initDispatcher(final SoapParameters params) throws MalformedURLException {
        try {
            LOGGER.debug 'SoapClient dispatcher initialiseren'

            Service s = Service.create(params.wsdlURL, params.serviceQName)
            LOGGER.debug 'SoapClient service created'

            dispatcher = s.createDispatch(params.portQName, SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature())
            LOGGER.debug 'SoapClient dispatcher created'

            Map<String, Object> map = dispatcher.requestContext
            map.put("com.sun.xml.internal.ws.request.timeout", SOAP_REQUEST_TIMEOUT_IN_MILLIS)

            LOGGER.debug 'SoapClient dispatcher initialiseren - KLAAR'
        } catch (Throwable e) {
            LOGGER.error 'Fout bij creeren SOAPClient:', e

            throw new ServicesNietBereikbaarException('Fout bij creeren SOAPClient', e)
        }
    }

    protected void printResponse(final Document document) throws
        Exception {
        String xml = XmlUtils.toXmlString(document.getDocumentElement());
        LOGGER.info "xml:\n $xml"
    }
}

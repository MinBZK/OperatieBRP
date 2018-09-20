/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component.services.soap;

import com.sun.org.apache.xml.internal.security.signature.XMLSignature;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.AddressingFeature;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * SoapClient voor het versturen van soap berichten.
 */
public class SoapClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoapClient.class);

    private static final int SOAP_REQUEST_TIMEOUT_IN_MILLIS = 5 * 60 * 1000;

    private static final String SERVER_PUBLICKEY_ALIAS = "server";
    private static Properties securityProperties;
    private static Crypto     crypto;
    private static KeyStore   keyStore;
    private static PublicKey  publicKey;

    private Dispatch<SOAPMessage> dispatcher;

    static {
        try {
            securityProperties = new Properties();
            securityProperties.load(SoapClient.class.getResourceAsStream("/security/client_sigpropfile.properties"));

            WSSConfig.init();
            crypto = CryptoFactory.getInstance("/security/client_sigpropfile.properties");

            initKeyStore();
        } catch (WSSecurityException | WebServiceException | CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            //LOGGER.error 'Fout bij init SoapClient: {}', e.message
            throw new RuntimeException("Fout bij init SoapClient", e);
        }
    }

    /**
     * Constructor.
     * @param params parameters voor het ophalen van WSDL en sturen van de requests
     */
    public SoapClient(final SoapParameters params) {
        try {
            initDispatcher(params);
        } catch (MalformedURLException | ServicesNietBereikbaarException e) {
            LOGGER.error("Exception creating SoapClient: {}", e.getMessage());
            throw new ServicesNietBereikbaarException("Kan geen SoapClient creeren naar ${params.wsdlURL}", e);
        }
    }

    protected InputStream getInputStream(final String bericht) throws IOException {
        return new ByteArrayInputStream(bericht.getBytes("UTF-8"));
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
    public Node verzendBerichtNaarService(final String berichtBestand) throws WSSecurityException,
        KeyStoreException, IOException, SOAPException, SAXException, ParserConfigurationException,
        TransformerConfigurationException, MarshalException, XMLSignatureException {
        // Bouw het initiele request bericht
        SOAPMessage request = bouwInitieleRequestBericht(getInputStream(berichtBestand));

        // Voeg de benodigde security headers toe aan het bericht
        voegSecurityHeadersToeAanBericht(request);
request.writeTo(System.err);
        // Haal het antwoord op
        SOAPMessage response = dispatcher.invoke(request);

        // Valideer de signature van het antwoord
        valideerSignature(response);

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
     * Deze methode past de opgegeven {@link javax.xml.soap.SOAPMessage} aan door de benodigde security zaken aan het
     * bericht (en dan met name de SOAP envelope) toe te voegen. Het gaat hierbij om de Timestamp en de digital
     * signature.
     *
     * @param request het SOAP request bericht dat voorzien dient te worden van de security zaken.
     */
    private void voegSecurityHeadersToeAanBericht(final SOAPMessage request) throws WSSecurityException,
        KeyStoreException {
        Document requestDoc = request.getSOAPPart();

        WSSecHeader secHeader = new WSSecHeader();
        secHeader.insertSecurityHeader(requestDoc);

        // Voeg de timestamp toe en de maximale "time-to-live" in secondes
        WSSecTimestamp secTimestamp = new WSSecTimestamp();
        secTimestamp.setTimeToLive(300);
        secTimestamp.prepare(requestDoc);
        secTimestamp.prependToHeader(secHeader);

        // Voeg de digital signature toe aan het bericht
        WSSecSignature sign = new WSSecSignature();
        Vector<WSEncryptionPart> signParts = new Vector<>();

        WSEncryptionPart part = new WSEncryptionPart("Body", "http://schemas.xmlsoap.org/soap/envelope/", "Content");
        signParts.add(part);

        sign.setParts(signParts);
        sign.setX509Certificate((X509Certificate) keyStore.getCertificate((String) securityProperties
            .get("org.apache.ws.security.crypto.merlin.keystore.alias")));
        sign.setKeyIdentifierType(WSConstants.ISSUER_SERIAL);
        sign.setSignatureAlgorithm(XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
        sign.setUserInfo((String) securityProperties.get("org.apache.ws.security.crypto.merlin.keystore.alias"),
            (String) securityProperties.get("org.apache.ws.security.crypto.merlin.keystore.password"));

        sign.build(requestDoc, crypto, secHeader);
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
            LOGGER.debug("SoapClient dispatcher initialiseren");

            Service s = Service.create(params.getWsdlURL(), params.getServiceQName());
            dispatcher = s.createDispatch(params.getPortQName(), SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature());

            Map<String, Object> map = dispatcher.getRequestContext();
            map.put("com.sun.xml.internal.ws.request.timeout", SOAP_REQUEST_TIMEOUT_IN_MILLIS);

            LOGGER.debug("SoapClient dispatcher initialiseren - KLAAR");
        } catch (Throwable e) {
            LOGGER.error("Fout bij creeren SOAPClient:", e);

            throw new ServicesNietBereikbaarException("Fout bij creeren SOAPClient", e);
        }
    }

    /**
     * Zet de juist (SOAP) actie voor de dispatcher.
     *
     * @param actie de actie die aangeroepen dient te worden.
     */
    protected void zetActieVoorBericht(final String actie) {
        Map<String, Object> map = dispatcher.getRequestContext();
        map.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        map.put(BindingProvider.SOAPACTION_URI_PROPERTY, actie);
    }

    /*
     * Initialiseert de {@link java.security.KeyStore} die gebruikt wordt voor het maken van de vereiste digital
     * signature.
     */
    private static void initKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException,
        CertificateException {
        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fis =
            SoapClient.class.getResourceAsStream("/"
                + securityProperties.get("org.apache.ws.security.crypto.merlin.file"));
        keyStore.load(fis, ((String) securityProperties.get("org.apache.ws.security.crypto.merlin.keystore.password"))
            .toCharArray());
        fis.close();

        publicKey = keyStore.getCertificate(SERVER_PUBLICKEY_ALIAS).getPublicKey();
    }

    /*
     * Controleert de message signature met de public key van de ontvanger.
     *
     * @param message bericht met de signature
     * @throws java.security.KeyStoreException
     * @throws javax.xml.soap.SOAPException
     * @throws javax.xml.crypto.MarshalException
     * @throws javax.xml.crypto.dsig.XMLSignatureException
     */
    private void valideerSignature(final SOAPMessage message) throws KeyStoreException, SOAPException,
        MarshalException, XMLSignatureException {

        // Haal de PubliekeSleutel element op
        NodeList publiekeSleutelNode =
            message.getSOAPHeader().getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        //Assert.assertEquals("De response bevat geen Signature element", 1, publiekeSleutelNode.getLength());
        // Maak een DOM XMLSignatureFactory dat wordt gebruikt om de document met de XMLSignature te unmarshallen
        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance();
        // Maak een DOMValidateContext
        DOMValidateContext validatieContext =
            new DOMValidateContext(KeySelector.singletonKeySelector(publicKey), publiekeSleutelNode.item(0));
        // Markeer dat de juiste attributen als XML ID worden gezien (nodig daar XML niet eerst reeds is gevalideerd)
        validatieContext.setIdAttributeNS(message.getSOAPBody(),
            "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
        // Markeer specifiek het id veld van de Timestamp node
        NodeList nodes = message.getSOAPPart().getElementsByTagName("wsu:Timestamp");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            element.setIdAttribute("wsu:Id", true);
        }
        // Unmarshal de XMLSignature.
        javax.xml.crypto.dsig.XMLSignature signature = signatureFactory.unmarshalXMLSignature(validatieContext);
        // Valideer de XMLSignature.
        //Assert.assertTrue("De signature van de response is niet geldig.", signature.validate(validatieContext));
    }

    protected void printResponse(final Document document) throws Exception {
        //String xml = XmlUtils.toXmlString(document.getDocumentElement());
        //LOGGER.info "xml:\n $xml"
    }
}

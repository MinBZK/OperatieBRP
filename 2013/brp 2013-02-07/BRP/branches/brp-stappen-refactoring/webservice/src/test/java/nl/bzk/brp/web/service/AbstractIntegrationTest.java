/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.namespace.QName;
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
import javax.xml.ws.soap.AddressingFeature;

import org.apache.cxf.helpers.IOUtils;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSEncryptionPart;
import org.apache.ws.security.WSSConfig;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.apache.ws.security.message.WSSecTimestamp;
import org.apache.xml.security.signature.XMLSignature;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/** Abstracte superclass voor repository (persistence) testcases. */
@ContextConfiguration(locations = { "/config/integratieTest-context.xml" })
public abstract class AbstractIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final Logger      LOGGER                 = LoggerFactory.getLogger(AbstractIntegrationTest.class);
    private static final String      SERVER_PUBLICKEY_ALIAS = "server";
    private static Properties        securityProperties;
    private static Crypto            crypto;
    private static KeyStore          keyStore;

    private final IOperationListener operationListener      = new MyOperationListener();
    private IDatabaseTester          databaseTester;
    private Dispatch<SOAPMessage>    dispatcher;

    @Inject
    @Named("dataSourceSpecial")
    private DataSource               dataSrc;

    protected InputStream getInputStream(final String berichtBestand) throws IOException {
        // Lees het verhuis bericht uit een xml bestand
        InputStream is = getClass().getResourceAsStream(berichtBestand);
        if (null == is) {
            throw new IOException("Kan bestand " + berichtBestand + " niet vinden in de classpath.");
        }
        return is;
    }

    /**
     * Standaard methode voor het verzenden van een bericht naar de WebService in een integratietest. De opgegeven
     * naam van het bericht bestand zal worden ingelezen en als de body van het verzoek naar de webservice worden
     * verstuurd, waarbij deze methode tevens voor het toevoegen van de benodigde SOAP headers etc. zorgt. Uit het
     * antwoord dat terugkomt van de service wordt de body geextraheerd en als {@link Node} geretourneerd door deze
     * methode.
     *
     * @param berichtBestand de bestandsnaam die verwijst naar een bestand met daarin de content die moet worden
     *            verstuurd.
     * @return de body van het antwoord, ontzien van allerlei niet direct relevante SOAP zaken.
     * @throws XMLSignatureException
     * @throws MarshalException
     */
    protected Node verzendBerichtNaarService(final String berichtBestand) throws WSSecurityException,
            KeyStoreException, IOException, SOAPException, SAXException, ParserConfigurationException,
            TransformerConfigurationException, MarshalException, XMLSignatureException
    {
        // Bouw het initiele request bericht
        SOAPMessage request = bouwInitieleRequestBericht(getInputStream(berichtBestand));

        // Voeg de benodigde security headers toe aan het bericht
        voegSecurityHeadersToeAanBericht(request);

        // Haal het antwoord op
        SOAPMessage response = dispatcher.invoke(request);

        // Valideer de signature van het antwoord
        valideerSignature(response);

        // Extraheer de content uit het antwoord en retourneer deze.
        return extraheerAntwoordUitSoapBericht(response);
    }

    /**
     * Bouwt de initiele request op basis van de opgegeven {@link java.io.InputStream}. De inputstream bevat de
     * content, waarbij deze methode de content omzet naar een geldige {@link javax.xml.soap.SOAPMessage} request.
     *
     * @param inputStream de inpustream naar de content voor de request.
     * @return een {@link javax.xml.soap.SOAPMessage} met daarin de opgegeven content uit de inputstream.
     */
    private SOAPMessage bouwInitieleRequestBericht(final InputStream inputStream) throws ParserConfigurationException,
            IOException, SAXException, SOAPException
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        Document request = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPBody().addDocument(request);
        return soapMessage;
    }

    /**
     * Deze methode past de opgegeven {@link SOAPMessage} aan door de benodigde security zaken aan het bericht (en dan
     * met name de SOAP envelope) toe te voegen. Het gaat hierbij om de Timestamp en de digital signature.
     *
     * @param request het SOAP request bericht dat voorzien dient te worden van de security zaken.
     */
    private void voegSecurityHeadersToeAanBericht(final SOAPMessage request) throws WSSecurityException,
            KeyStoreException
    {
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
        Vector<WSEncryptionPart> signParts = new Vector<WSEncryptionPart>();

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

    /**
     * Deze methode extraheert de werkelijke content uit het SOAP antwoord bericht.
     *
     * @param soapMessage het SOAP bericht waaruit de content gehaald wordt.
     * @return een {@link Node} met de werkelijke uit het bericht gehaalde content.
     */
    private Node extraheerAntwoordUitSoapBericht(final SOAPMessage soapMessage)
        throws TransformerConfigurationException, SOAPException
    {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(soapMessage.getSOAPBody());
        return source.getNode();
    }

    /**
     * Initialiseer de voor de integratie test benodigde zaken, zoals de keystore, security properties en de crypto
     * configuratie.
     */
    @BeforeClass
    public static void init() throws IOException, WSSecurityException, NoSuchAlgorithmException, KeyStoreException,
            CertificateException
    {
        securityProperties = new Properties();
        securityProperties.load(BijhoudingServiceIntegrationTest.class
                .getResourceAsStream("/client_sigpropfile.properties"));

        WSSConfig.init();
        crypto = CryptoFactory.getInstance("client_sigpropfile.properties");
        initKeyStore();
    }

    /**
     * Deze Before method wordt gebruikt om de zaken die nodig zijn voor elke integratietest te initialiseren. Zo
     * wordt de database hier geinitialiseerd, maar ook de standaard {@link Dispatch} instantie en de benodigde
     * {@link KeyStore}.
     *
     * @throws Exception indien er een fout is opgetreden tijdens de setup.
     */
    @Before
    public void setUp() throws Exception {
        LOGGER.debug("entering setUp()");
        initDatabase();
        initDispatcher();
        initKeyStore();
        LOGGER.debug("exiting setUp() normaal");
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    /**
     * Initialiseert de database middels dbunit.
     *
     * @throws Exception indien er geen connectie met de database gevonden kan worden of er een andere database
     *             probleem is opgetreden.
     */
    private void initDatabase() throws Exception {
        try {
            databaseTester = new DataSourceDatabaseTester(dataSrc);
            databaseTester.setOperationListener(operationListener);
            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            IDataSet dataset = null;
            try {
                Class<? extends AbstractIntegrationTest> clazz = getClass();
                dataset =
                    new CompositeDataSet(new IDataSet[] {
                        builder.build(clazz.getResourceAsStream("/data/stamgegevensStatisch.xml")),
                        builder.build(clazz.getResourceAsStream("/data/stamgegevensNationaliteit.xml")),
                        builder.build(clazz.getResourceAsStream("/data/stamgegevensLand.xml")),
                        builder.build(clazz.getResourceAsStream("/data/partij.xml")),
                        builder.build(clazz.getResourceAsStream("/data/testdata.xml")) });
            } catch (DataSetException ex) {
                throw new RuntimeException(ex);
            }
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();

            // Sequence ophogen om te voorkomen dat de new toegevoegde data in conflict komt met data in testdata.xml
            Statement st = databaseTester.getConnection().getConnection().createStatement();
            String myString = IOUtils.toString(getClass().getResourceAsStream("/data/afterburner.sql"), "UTF-8");
            st.execute(myString);
        } catch (Exception e) {
            LOGGER.debug("exiting setUp() onverwachts vanwege probleem met de database setup", e);
            throw e;
        }
    }

    /**
     * Definieert de URL naar de webservice.
     *
     * @return URL naar de webservice.
     * @throws MalformedURLException Indien de URL niet valide is.
     */
    abstract URL getWsdlUrl() throws MalformedURLException;

    /**
     * Definieer de service middels de betreffende NameSpace en de naam in de WSDL.
     *
     * @return Service namespace en name in de QName wrapper.
     */
    abstract QName getServiceQName();

    /**
     * Definieer de port middels de betreffende NameSpace en de naam in de WSDL.
     *
     * @return Portname namespace en name in de QName wrapper.
     */
    abstract QName getPortName();

    /**
     * Initialiseert de standaard {@link Dispatch} instantie die gebruikt wordt om de BRP Service aan te roepen.
     * <p/>
     * Merk op dat hier nog hard-coded WSDL locatie en Service en Port namen gebruikt wordt. Als er meerdere WSDLs en/of
     * Ports gebruikt gaan worden, dan zullen deze zaken geextraheerd moeten worden en meegegeven moeten worden aan deze
     * methode als parameters.
     *
     * @throws java.net.MalformedURLException indien de URL van de WSDL niet correct is.
     */
    private void initDispatcher() throws MalformedURLException {
        Service s = Service.create(getWsdlUrl(), getServiceQName());
        dispatcher = s.createDispatch(getPortName(), SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature());
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

    /** Initialiseert de {@link KeyStore} die gebruikt wordt voor het maken van de vereiste digital signature. */
    private static void initKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException
    {
        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream fis =
            BijhoudingServiceIntegrationTest.class.getResourceAsStream("/"
                + securityProperties.get("org.apache.ws.security.crypto.merlin.file"));
        keyStore.load(fis, ((String) securityProperties.get("org.apache.ws.security.crypto.merlin.keystore.password"))
                .toCharArray());
        fis.close();
    }

    /**
     * Private, lokale {@link IOperationListener} implementatie die database connectie voor de unit tests configureerd.
     * Tevens kunnen hier overige features en properties voor dbunit gezet worden.
     */
    private final class MyOperationListener implements IOperationListener {
        @Override
        public void connectionRetrieved(final IDatabaseConnection connection) {
            DatabaseConfig config = connection.getConfig();
            config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
            config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
        }

        @Override
        public void operationSetUpFinished(final IDatabaseConnection connection) {
        }

        @Override
        public void operationTearDownFinished(final IDatabaseConnection connection) {
        }
    }

    /**
     * Controlleert de message signature met de public key van de server.
     *
     * @param message bericht met de signature
     * @throws KeyStoreException
     * @throws SOAPException
     * @throws MarshalException
     * @throws XMLSignatureException
     */
    private void valideerSignature(final SOAPMessage message) throws KeyStoreException, SOAPException,
            MarshalException, XMLSignatureException
    {
        PublicKey publicKey = keyStore.getCertificate(SERVER_PUBLICKEY_ALIAS).getPublicKey();

        // Haal de PubliekeSleutel element op
        NodeList publiekeSleutelNode =
            message.getSOAPHeader().getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        Assert.assertEquals("De response bevat geen Signature element", 1, publiekeSleutelNode.getLength());
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
        Assert.assertTrue("De signature van de response is niet geldig.", signature.validate(validatieContext));
    }
}

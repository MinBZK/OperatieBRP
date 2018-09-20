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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.xml.bind.JAXBException;
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
import nl.bzk.brp.util.AutorisatieOffloadGegevens;
import nl.bzk.brp.utils.XmlUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.message.Message;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.IOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@Transactional(transactionManager = "lezenSchrijvenTransactionManager")
@ContextConfiguration(locations = { "/config/integratieTest-context.xml" })
public abstract class AbstractIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static final   Logger LOGGER                 = LoggerFactory.getLogger(AbstractIntegrationTest.class);
    private static final   String SERVER_PUBLICKEY_ALIAS = "server";
    protected static final String NODE_RESULTAAT         = "//brp:resultaat";
    protected static final String NODE_VERWERKING        = NODE_RESULTAAT + "/brp:verwerking";
    private static KeyStore   keyStore;

    private final IOperationListener operationListener = new MyOperationListener();
    private IDatabaseTester       databaseTester;
    private Dispatch<SOAPMessage> dispatcher;

    @Value("${jetty.port}")
    protected String jettyPort;

    private DataSource dataSrc;

    @Inject
    @Named("lezenSchrijvenDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSrc = dataSource;
    }

    protected InputStream getInputStream(final String berichtBestand) throws IOException {
        // Lees het verhuis bericht uit een xml bestand
        final InputStream is = getClass().getResourceAsStream(berichtBestand);
        if (null == is) {
            throw new IOException("Kan bestand " + berichtBestand + " niet vinden in de classpath.");
        }
        return is;
    }

    /**
     * Standaard methode voor het verzenden van een bericht naar de WebService in een integratietest. De opgegeven
     * naam van het bericht bestand zal worden ingelezen en als de body van het verzoek naar de webservice worden
     * verstuurd, waarbij deze methode tevens voor het toevoegen van de benodigde SOAP headers etc. zorgt. Uit het
     * antwoord dat terugkomt van de service wordt de body geextraheerd en als {@link org.w3c.dom.Node} geretourneerd
     * door deze methode.
     *
     * @param berichtBestand de bestandsnaam die verwijst naar een bestand met daarin de content die moet worden
     *                       verstuurd.
     * @return de body van het antwoord, ontzien van allerlei niet direct relevante SOAP zaken.
     * @throws javax.xml.crypto.dsig.XMLSignatureException
     * @throws javax.xml.crypto.MarshalException
     */
    protected Node verzendBerichtNaarService(final String berichtBestand) throws IOException, JAXBException, KeyStoreException, WSSecurityException, SOAPException, TransformerConfigurationException,
        ParserConfigurationException, SAXException
    {
        return verzendBerichtNaarService(berichtBestand, null, null);
    }

    protected Node verzendBerichtNaarService(final String berichtBestand, String ondertekenaar, String transporteur)
        throws IOException, JAXBException, KeyStoreException, WSSecurityException, SOAPException, TransformerConfigurationException,
        ParserConfigurationException, SAXException
    {
        // Bouw het initiele request bericht
        final SOAPMessage request = bouwInitieleRequestBericht(getInputStream(berichtBestand));

        // Voeg de benodigde security headers toe aan het bericht
        if (ondertekenaar != null && transporteur != null) {
            voegSecurityHeadersToeAanBericht(request, ondertekenaar, transporteur);
        }

        // Haal het antwoord op
        final SOAPMessage response = dispatcher.invoke(request);

        // Valideer de signature van het antwoord
//        valideerSignature(response);

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
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        final Document request = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

        // Eventuele post processing van het Document voordat het bericht naar de server gaat.
        bewerkRequestDocumentVoorVerzending(request);

        final SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        soapMessage.getSOAPBody().addDocument(request);
        return soapMessage;
    }

    /**
     * Pas het Document aan voordat het naar de server wordt gestuurd. Dit is een functie voor post-processing van het
     * document. Bij bijhouding worden bijvoorbeeld hier de objectSleutels ingevuld.
     * @param request het request document.
     */
    protected abstract void bewerkRequestDocumentVoorVerzending(final Document request);

    /**
     * Deze methode past de opgegeven {@link javax.xml.soap.SOAPMessage} aan door de benodigde security zaken aan het
     * bericht (en dan met name de SOAP envelope) toe te voegen. Het gaat hierbij om de Timestamp en de digital
     * signature.
     *
     * @param request het SOAP request bericht dat voorzien dient te worden van de security zaken.
     */
    private void voegSecurityHeadersToeAanBericht(final SOAPMessage request, String ondertekenaar, String transporteur) throws WSSecurityException,
        KeyStoreException, JAXBException
    {
//        List<Header> headersList = new ArrayList<>();
//        final Map<String, Object> requestContext = dispatcher.getRequestContext();
//        Header testSoapHeader1 = new Header(new QName(AuthenticatieOffloadGegevens.OIN_ONDERTEKENAAR), "1234", new JAXBDataBinding(String.class));
//        Header testSoapHeader2 = new Header(new QName(AuthenticatieOffloadGegevens.OIN_TRANSPORTEUR), "5678", new JAXBDataBinding(String.class));
//        headersList.add(testSoapHeader1);
//        headersList.add(testSoapHeader2);
//        requestContext.put(Header.HEADER_LIST, headersList);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put(AutorisatieOffloadGegevens.OIN_ONDERTEKENAAR, Collections.singletonList(ondertekenaar));
        headers.put(AutorisatieOffloadGegevens.OIN_TRANSPORTEUR, Collections.singletonList(transporteur));
        dispatcher.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
    }

    /**
     * Deze methode extraheert de werkelijke content uit het SOAP antwoord bericht.
     *
     * @param soapMessage het SOAP bericht waaruit de content gehaald wordt.
     * @return een {@link org.w3c.dom.Node} met de werkelijke uit het bericht gehaalde content.
     */
    private Node extraheerAntwoordUitSoapBericht(final SOAPMessage soapMessage) throws
            TransformerConfigurationException, SOAPException
    {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        final DOMSource source = new DOMSource(soapMessage.getSOAPBody());
        return source.getNode();
    }

    /**
     * Deze Before method wordt gebruikt om de zaken die nodig zijn voor elke integratietest te initialiseren. Zo
     * wordt de database hier geinitialiseerd, maar ook de standaard {@link javax.xml.ws.Dispatch} instantie en de
     * benodigde {@link java.security.KeyStore}.
     *
     * @throws Exception indien er een fout is opgetreden tijdens de setup.
     */
    @Before
    public void setUp() throws Exception {
        LOGGER.debug("entering setUp()");
        initDatabase();
        initDispatcher();
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
     *                   probleem is opgetreden.
     */
    private void initDatabase() throws Exception {
        try {
            databaseTester = new DataSourceDatabaseTester(dataSrc);
            databaseTester.setOperationListener(operationListener);
            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(true);
            final IDataSet dataset;
            try {
                final Class<? extends AbstractIntegrationTest> clazz = getClass();
                final IDataSet[] dataSets = new IDataSet[getDataBestanden().size()];
                int index = 0;
                for (final String dataBestand : getDataBestanden()) {
                    dataSets[index++] = builder.build(clazz.getResourceAsStream(dataBestand));
                }
                dataset = new CompositeDataSet(dataSets);
            } catch (final DataSetException ex) {
                throw new IllegalStateException("DBUnit dataset kan niet ingeladen worden", ex);
            }
            final ReplacementDataSet filteredDataSet = new ReplacementDataSet(dataset);
            filteredDataSet.addReplacementObject("[NULL]", null);

            databaseTester.setDataSet(filteredDataSet);
            databaseTester.onSetup();

            // Sequence ophogen om te voorkomen dat de new toegevoegde data in conflict komt met data in testdata.xml
            final Statement st = databaseTester.getConnection().getConnection().createStatement();

            final InputStream afterburnerInputStream = getClass().getResourceAsStream("/data/afterburner.sql");

            if (afterburnerInputStream != null) {
                final String myString = IOUtils.toString(afterburnerInputStream, "UTF-8");
                st.execute(myString);
            }

        } catch (final Exception e) {
            LOGGER.debug("exiting setUp() onverwachts vanwege probleem met de database setup", e);
            throw e;
        }
    }

    /**
     * Retourneert de data bestanden die (middels DBUnit) voor elke test in deze class geladen moeten worden in de
     * database.
     *
     * @return een lijst van data bestanden die ingeladen moeten worden.
     */
    private List<String> getDataBestanden() {
        final List<String> dataBestanden = getInitieleDataBestanden();
        final List<String> additioneleDataBestanden = getAdditioneleDataBestanden();
        if (additioneleDataBestanden != null && !additioneleDataBestanden.isEmpty()) {
            dataBestanden.addAll(additioneleDataBestanden);
        }
        return dataBestanden;
    }

    /**
     * Geeft de bestanden die bij de intitialisatie van de database gebruikt worden.
     *
     * @return de lijst met intitiele data bestanden
     */
    protected List<String> getInitieleDataBestanden() {
        final List<String> dataBestanden = new ArrayList<>();
        dataBestanden.addAll(Arrays.asList(
                "/data/stamgegevensStatisch.xml",
                "/data/stamgegevensNationaliteit.xml",
                "/data/stamgegevensLandGebied.xml",
                "/data/partijEnGemeente.xml",
                "/data/stamgegevensAbonnement.xml",
                "/data/testdata.xml"
        ));

        return dataBestanden;
    }

    /**
     * Lijst van eventueel nog additionele data bestanden die ingelezen moeten worden.
     *
     * @return een lijst van additionele data bestanden.
     * @see AbstractIntegrationTest#getDataBestanden()
     */
    protected List<String> getAdditioneleDataBestanden() {
        return Collections.emptyList();
    }

    /**
     * Definieert de URL naar de webservice.
     *
     * @return URL naar de webservice.
     * @throws java.net.MalformedURLException Indien de URL niet valide is.
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
     * Initialiseert de standaard {@link javax.xml.ws.Dispatch} instantie die gebruikt wordt om de BRP Service aan te
     * roepen.
     * <p/>
     * Merk op dat hier nog hard-coded WSDL locatie en Service en Port namen gebruikt wordt. Als er meerdere WSDLs en/of
     * Ports gebruikt gaan worden, dan zullen deze zaken geextraheerd moeten worden en meegegeven moeten worden aan deze
     * methode als parameters.
     *
     * @throws java.net.MalformedURLException indien de URL van de WSDL niet correct is.
     */
    private void initDispatcher() throws MalformedURLException {
        final Service s = Service.create(getWsdlUrl(), getServiceQName());
        dispatcher = s.createDispatch(getPortName(), SOAPMessage.class, Service.Mode.MESSAGE, new AddressingFeature());
    }

    /**
     * Zet de juist (SOAP) actie voor de dispatcher.
     *
     * @param actie de actie die aangeroepen dient te worden.
     */
    protected void zetActieVoorBericht(final String actie) {
        final Map<String, Object> map = dispatcher.getRequestContext();
        map.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        map.put(BindingProvider.SOAPACTION_URI_PROPERTY, actie);
    }

    /**
     * Private, lokale {@link org.dbunit.IOperationListener} implementatie die database connectie voor de unit tests
     * configureerd.
     * Tevens kunnen hier overige features en properties voor dbunit gezet worden.
     */
    private final class MyOperationListener implements IOperationListener {
        @Override
        public void connectionRetrieved(final IDatabaseConnection connection) {
            final DatabaseConfig config = connection.getConfig();
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

    protected void printResponse(final Document document) throws
            Exception
    {
        final String xml = XmlUtils.toXmlString(document.getDocumentElement());
        LOGGER.info("XML Bericht:\n{}", xml);
    }

}

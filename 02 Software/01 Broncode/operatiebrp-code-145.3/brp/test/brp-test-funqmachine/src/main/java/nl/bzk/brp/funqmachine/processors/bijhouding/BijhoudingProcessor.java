/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.bijhouding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.parser.Parser;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import nl.bzk.brp.funqmachine.configuratie.Omgeving;
import nl.bzk.brp.funqmachine.jbehave.context.StepResult;
import nl.bzk.brp.funqmachine.processors.ProcessorException;
import nl.bzk.brp.funqmachine.processors.SqlProcessor;
import nl.bzk.brp.funqmachine.processors.xml.XmlException;
import nl.bzk.brp.funqmachine.verstuurder.SoapVerstuurder;
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.xerces.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Logica voor om een bijhouding te kunnen doen via de business laag van de bijhouding-module.
 */
public final class BijhoudingProcessor {

    private static final ThreadLocal<TransformerFactory> TRANSFORMER_FACTORY_STORE = new ThreadLocal<>();
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String MORGEN_PLACEHOLDER = "${morgen}";
    private static final String GISTER_PLACEHOLDER = "${gister}";
    private static final String GISTEREN_PLACEHOLDER = "${gisteren}";
    private static final String VANDAAG_PLACEHOLDER = "${vandaag}";
    private static final String QUEUE_GBA_TOEVALLIGE_GEBEURTENISSEN = "GbaToevalligeGebeurtenissen";
    private static final String QUEUE_GBA_TOEVALLIGE_GEBEURTENISSEN_ANTWOORDEN = "GbaToevalligeGebeurtenissenAntwoorden";
    private static final String QUERY_AFGELEIDADMINISTRATIED =
            "select pa.tslaatstewijz at time zone 'UTC',pa.*"
                    + " from kern.pers p"
                    + " join kern.his_persafgeleidadministrati pa on pa.pers = p.id and pa.tsverval is not null"
                    + " join kern.actie ac on ac.id = pa.actieinh"
                    + " join kern.admhnd ah on ac.admhnd = ah.id"
                    + " join kern.srtadmhnd sah on sah.id = ah.srt and naam not like 'Ontrelateren'"
                    + " where p.bsn = '%s' order by pa.id desc"
                    + " limit 1";

    private ObjectSleutelService objectSleutelService;
    private BijhoudingService bijhoudingService;

    /**
     * Constructor.
     * @param bijhoudingService de {@link BijhoudingService}
     * @param objectSleutelService de {@link ObjectSleutelService}
     */
    public BijhoudingProcessor(final BijhoudingService bijhoudingService, final ObjectSleutelService objectSleutelService) {
        this.objectSleutelService = objectSleutelService;
        this.bijhoudingService = bijhoudingService;
    }

    /**
     * Lees het XML bericht in, converteert de opgegeven BSN's in het objectsleutel attribuut naar correcte
     * objectsleutels en geef het bericht aan de bijhouding business-laag door met als verwerkingstijdstip het tijdstip
     * dat meegegeven is. Als er geen tijdstip verwerking is meegegeven, dan wordt het nu-moment gekozen (new Date()).
     * @param isBijhouding true als dit een bijhouding betreft, false als dit een bevraging is
     * @param isGbaBericht geeft ana of het een GBA bericht is
     * @param stepResult object waarin het request kan worden weg geschreven
     * @param xmlInputStream de {@link InputStream} naar de XML
     * @param oinOndertekenaar OIN van de ondertekenaar van het bericht
     * @param oinTransporteur OIN van de transporteur van het bericht
     * @param variabelen lijst met variabelen
     */
    public void verwerkenBericht(
            final boolean isBijhouding,
            final boolean isGbaBericht,
            final StepResult stepResult,
            final InputStream xmlInputStream,
            final String oinOndertekenaar,
            final String oinTransporteur,
            final Map<String, String> variabelen) {
        try {
            final Document requestXmlDocument = transformeerNaarDoc(xmlInputStream);

            final XPath xpath = XPathFactory.newInstance().newXPath();

            vervangVariabelen(requestXmlDocument, xpath, variabelen);
            vervangBsnDoorObjectSleutel(requestXmlDocument, xpath);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, MORGEN_PLACEHOLDER);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, GISTER_PLACEHOLDER);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, GISTEREN_PLACEHOLDER);
            vervangDatumPlaceholdersMetEchteDatum(requestXmlDocument, xpath, VANDAAG_PLACEHOLDER);

            valideerBerichtTegenXsd(requestXmlDocument);

            if (Omgeving.getOmgeving().isDockerOmgeving()) {
                LOGGER.info("Testen tegen dokker URL: " + Omgeving.getOmgeving().getHostURL());
                if (isBijhouding) {
                    gebruikDockerOmgevingVoorBijhouding(isGbaBericht, stepResult, requestXmlDocument);
                } else {
                    gebruikDockerOmgevingVoorBevraging(stepResult, requestXmlDocument);
                }
            } else if (isBijhouding) {
                LOGGER.info("Testen tegen business logica");
                final BijhoudingVerzoekBericht bijhoudingVerzoekBericht = converteerNaarBericht(stepResult, requestXmlDocument);
                bijhoudingVerzoekBericht.setOinWaardeOndertekenaar(oinOndertekenaar);
                bijhoudingVerzoekBericht.setOinWaardeTransporteur(oinTransporteur);

                final BijhoudingAntwoordBericht bijhoudingAntwoordBericht;
                if (isGbaBericht) {
                    bijhoudingAntwoordBericht = bijhoudingService.verwerkGbaBericht(bijhoudingVerzoekBericht);
                } else {
                    bijhoudingAntwoordBericht = bijhoudingService.verwerkBrpBericht(bijhoudingVerzoekBericht);
                }
                valideerBerichtTegenXsd(transformeerNaarDoc(new ByteArrayInputStream(bijhoudingAntwoordBericht.getXml().getBytes(Charset.defaultCharset()))));
                stepResult.setResponse(bijhoudingAntwoordBericht.getXml());
            } else {
                throw new ProcessorException("Bevraging kan alleen tegen Docker server getest worden.", false);
            }
        } catch (final SAXException se) {
            throw new ProcessorException(se, true);
        } catch (final XmlException | XPathExpressionException | ParseException | TransformerException |
                ParserConfigurationException | IOException | JMSException | SQLException e) {
            LOGGER.error("Onverwachte fout tijdens verwerkenBericht van bijhoudingsbericht", e);
            throw new ProcessorException(e);
        }
    }

    /**
     * Controleert de tijdstip laatste wijziging van de personen in het bijhoudingsplan tov de database.
     * @param stepResult een {@link StepResult} met daarin het resultaat van de bijhouding.
     */
    public void controleerTijdstipLaatsteWijzigingInResponse(final StepResult stepResult) {
        try {
            final XPathFactory xPathfactory = XPathFactory.newInstance();
            final XPath xpath = xPathfactory.newXPath();
            final XPathExpression bsnXpath = xpath.compile("//*[local-name()='bijhoudingsplanPersoon']");
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(false);
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            final Document doc = docBuilder.parse(new ByteArrayInputStream(stepResult.getResponse().getBytes(Charset.defaultCharset())));
            final NodeList nodeLijstBijhoudingsPlanPersonen = (NodeList) bsnXpath.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodeLijstBijhoudingsPlanPersonen.getLength(); i++) {
                final NodeList childNodes = nodeLijstBijhoudingsPlanPersonen.item(i).getChildNodes();
                controleerBijhoudingsplanPersoon(childNodes);
            }
        } catch (final IOException | SQLException | ParserConfigurationException | SAXException | XPathExpressionException e) {
            throw new ProcessorException(e);
        }
    }

    private void controleerBijhoudingsplanPersoon(final NodeList childNodes) throws SQLException {
        Node persoon = null;
        BijhoudingSituatie bijhoudingSituatie = null;
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node child = childNodes.item(j);
            if ("brp:persoon".equals(child.getNodeName())) {
                persoon = child;
            }
            if ("brp:situatieNaam".equals(child.getNodeName())) {
                bijhoudingSituatie = bepaalBijhoudingSituatie(child.getTextContent());
            }
        }
        if (bijhoudingSituatie == null) {
            throw new ProcessorException("Bijhoudingsplan situatie is onbekend");
        }
        if (bijhoudingSituatie.isVerwerkbaar() && persoon != null) {
            controleerTijdstipLaatsteWijziging(persoon);
        }
    }

    private BijhoudingSituatie bepaalBijhoudingSituatie(final String naam) {
        for (final BijhoudingSituatie situatie : BijhoudingSituatie.values()) {
            if (situatie.getNaam().equals(naam)) {
                return situatie;
            }
        }
        return null;
    }

    private void controleerTijdstipLaatsteWijziging(final Node persoon) throws SQLException {
        final NodeList lijst = persoon.getChildNodes();
        String tijdstipLaatsteWijziging = null;
        String burgerservicenummer = null;
        for (int j = 0; j < lijst.getLength(); j++) {
            final Node child = lijst.item(j);
            if ("brp:afgeleidAdministratief".equals(child.getNodeName())) {
                tijdstipLaatsteWijziging = ((DeferredElementImpl) child).getFirstElementChild().getTextContent();
            }
            if ("brp:identificatienummers".equals(child.getNodeName())) {
                burgerservicenummer = ((DeferredElementImpl) child).getFirstElementChild().getTextContent();
            }
        }
        if (tijdstipLaatsteWijziging != null) {
            final ResultSet resultSet = SqlProcessor.getInstance().voerUit(String.format(QUERY_AFGELEIDADMINISTRATIED, burgerservicenummer));
            if (!resultSet.next()) {
                throw new ProcessorException(String.format("BSN %s heeft geen vervalen waarden in afgeleid administratief", burgerservicenummer));
            }
            final ZonedDateTime zonedDateTime =
                    ZonedDateTime.parse(tijdstipLaatsteWijziging, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneId.of(DatumUtil.UTC));
            final LocalDateTime tijdstipLaatsteWijzigingBericht = zonedDateTime.toLocalDateTime();
            final LocalDateTime localDateTimeDatabase = resultSet.getTimestamp(1).toLocalDateTime();
            if (!tijdstipLaatsteWijzigingBericht.equals(localDateTimeDatabase)) {
                String bericht = "Tijdstip laatste wijziging BSN %s komt niet overeen : %s om %s";
                throw new ProcessorException(String.format(bericht, burgerservicenummer, tijdstipLaatsteWijziging, resultSet.getString(1)));
            }
            LOGGER.info(String.format("Tijdstip laatste wijzing ok: BSN %s", burgerservicenummer));
        } else {
            LOGGER.info(String.format("Tijdstip laatste ontbreekt(eerste inschrijving?) BSN %s", burgerservicenummer));
        }
    }

    private void gebruikDockerOmgevingVoorBijhouding(boolean isGbaBericht, StepResult stepResult,
                                                     Document requestXmlDocument) throws TransformerException, JMSException, XmlException {
        if (!isGbaBericht) {
            LOGGER.info("Testen SOAP bericht");
            verzendSoapBericht(requestXmlDocument, stepResult, true);
        } else {
            LOGGER.info("Testen GBA bericht");
            QueueConnectionFactory factory = new ActiveMQConnectionFactory(Omgeving.getOmgeving().getBrokerUrl());
            stepResult.setRequest(vanDocumentNaarXml(requestXmlDocument));
            if (verstuurGbaBijhoudingsVerzoek(requestXmlDocument, factory)) {
                ontvangGbaAntwoordBericht(stepResult, factory);
            }
        }
    }

    private void gebruikDockerOmgevingVoorBevraging(final StepResult stepResult, final Document requestXmlDocument) throws TransformerException, XmlException {
        LOGGER.info("Testen SOAP bevraging");
        verzendSoapBericht(requestXmlDocument, stepResult, false);
    }

    private void ontvangGbaAntwoordBericht(final StepResult stepResult, final QueueConnectionFactory factory)
            throws JMSException {
        QueueConnection queueConnection = null;
        QueueSession queueSessionAntw = null;
        try {
            queueConnection = factory.createQueueConnection();
            queueConnection.start();
            queueSessionAntw = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            final Queue queueR = queueSessionAntw.createQueue(QUEUE_GBA_TOEVALLIGE_GEBEURTENISSEN_ANTWOORDEN);

            final QueueReceiver receiver = queueSessionAntw.createReceiver(queueR);
            final int receiveTimeOut = 10_000;
            final Message antwoord = receiver.receive(receiveTimeOut);
            if (antwoord != null) {
                antwoord.acknowledge();
                stepResult.setResponse(((ActiveMQTextMessage) antwoord).getText());
            } else {
                throw new ProcessorException("Geen antwoord ontvangen", true);
            }
            receiver.close();
        } finally {
            closeQueueSessionAndConnection(queueConnection, queueSessionAntw);
        }
    }

    private void closeQueueSessionAndConnection(final QueueConnection queueConnection, final QueueSession queueSessionAntw) {
        try {
            if (queueConnection != null) {
                queueConnection.close();
            }
        } catch (final JMSException e) {
            LOGGER.debug(e.getMessage(), e);
        }
        try {
            if (queueSessionAntw != null) {
                queueSessionAntw.close();
            }
        } catch (final JMSException e) {
            LOGGER.debug(e.getMessage(), e);
        }
    }

    private boolean verstuurGbaBijhoudingsVerzoek(Document requestXmlDocument, QueueConnectionFactory factory)
            throws JMSException, TransformerException {
        QueueConnection queueConnection = null;
        boolean result = true;
        try {
            queueConnection = factory.createQueueConnection();
            final QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = queueSession.createQueue(QUEUE_GBA_TOEVALLIGE_GEBEURTENISSEN);

            final QueueSender sender = queueSession.createSender(queue);
            TextMessage message = new ActiveMQTextMessage();
            message.setText(transformeerNaarXml(new DOMSource(requestXmlDocument)));
            sender.send(message);
        } finally {
            try {
                if (queueConnection != null) {
                    queueConnection.close();
                }
            } catch (final JMSException e) {
                LOGGER.error("verstuur", e);
                result = false;
            }
        }
        return result;
    }

    private void valideerBerichtTegenXsd(final Document doc) throws SAXException, IOException {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        final URL xsdUrl = this.getClass().getResource("/xsd/master.xsd");
        final Schema schema = factory.newSchema(xsdUrl);

        // create a Validator instance, which can be used to validate an instance document
        final Validator validator = schema.newValidator();

        // validate the DOM tree
        validator.validate(new DOMSource(doc));
    }

    private void vervangDatumPlaceholdersMetEchteDatum(final Document doc, final XPath xpath, final String datumPlaceholder)
            throws XPathExpressionException {
        final NodeList datumNodes = (NodeList) xpath.evaluate("//*[text()[contains(., '" + datumPlaceholder + "')]]", doc, XPathConstants.NODESET);
        final int nieuweDatum;

        switch (datumPlaceholder) {
            case MORGEN_PLACEHOLDER:
                nieuweDatum = DatumUtil.morgen();
                break;
            case GISTEREN_PLACEHOLDER:
            case GISTER_PLACEHOLDER:
                nieuweDatum = DatumUtil.gisteren();
                break;
            case VANDAAG_PLACEHOLDER:
                nieuweDatum = DatumUtil.vandaag();
                break;
            default:
                throw new IllegalStateException("Onbekende placeholder voor een datum gevonden");
        }

        for (int index = 0; index < datumNodes.getLength(); index++) {
            final Node datumNode = datumNodes.item(index);
            final int jaarDeling = 10_000;
            final int dagDeling = 100;
            datumNode.setTextContent(String.format("%d-%02d-%02d", nieuweDatum / jaarDeling, (nieuweDatum % jaarDeling) / dagDeling, nieuweDatum % dagDeling));
        }
    }

    private void vervangVariabelen(final Document requestXmlDocument, final XPath xpath, final Map<String, String> variabelen) throws XPathExpressionException {
        if (variabelen.isEmpty()) {
            return;
        }
        vervangVariabelen((NodeList) xpath.evaluate("//@*", requestXmlDocument, XPathConstants.NODESET), variabelen);
        vervangVariabelen((NodeList) xpath.evaluate("//node()", requestXmlDocument, XPathConstants.NODESET), variabelen);
    }

    private void vervangVariabelen(final NodeList nodes, final Map<String, String> variabelen) {
        for (int index = 0; index < nodes.getLength(); index++) {
            final Node node = nodes.item(index);
            final String attribuutWaarde = node.getNodeValue();
            if (variabelen.containsKey(attribuutWaarde)) {
                final String nieuweWaarde = attribuutWaarde.replace(attribuutWaarde, variabelen.get(attribuutWaarde));
                node.setNodeValue(nieuweWaarde);
            }
        }
    }

    private void vervangBsnDoorObjectSleutel(final Document doc, final XPath xpath) throws XPathExpressionException, SQLException {
        final NodeList persoonNodes = (NodeList) xpath.evaluate("//*[local-name()='persoon']", doc, XPathConstants.NODESET);
        for (int index = 0; index < persoonNodes.getLength(); index++) {
            final Node node = persoonNodes.item(index);
            final Node objectSleutelNode = node.getAttributes().getNamedItem("brp:objectSleutel");
            if (objectSleutelNode != null) {
                final String bsn = objectSleutelNode.getNodeValue();
                try {
                    final Integer[] persoonIdentificerendeGegevens = SqlProcessor.getInstance().geeftPersoonId(bsn);
                    objectSleutelNode.setNodeValue(
                            objectSleutelService.maakPersoonObjectSleutel(persoonIdentificerendeGegevens[0], persoonIdentificerendeGegevens[1]).maskeren());
                } catch (final ProcessorException pe) {
                    LOGGER.info("Vervanging van BSN door Objectsleutel mislukt: " + pe.getMessage());
                }
            }
        }
    }

    private BijhoudingVerzoekBericht converteerNaarBericht(final StepResult stepResult, final Document doc) throws ParseException, TransformerException {
        final DOMSource source = new DOMSource(doc);
        final Parser<BijhoudingVerzoekBericht> parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bijhoudingVerzoekBericht = parser.parse(converteerNaarStreamSource(source));
        final String xml = transformeerNaarXml(source);
        bijhoudingVerzoekBericht.setXml(xml);

        stepResult.setRequest(xml);
        return bijhoudingVerzoekBericht;
    }

    private StreamSource converteerNaarStreamSource(final DOMSource domSource) throws TransformerException {
        final Transformer transformer = getTransformerFactory().newTransformer();
        final StreamResult result = new StreamResult();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        result.setOutputStream(out);
        transformer.transform(domSource, result);
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        return new StreamSource(in);
    }

    private String transformeerNaarXml(final DOMSource bericht) throws TransformerException {
        final StringWriter stringWriter = new StringWriter();
        final Transformer transformer = getTransformerFactory().newTransformer();
        transformer.transform(bericht, new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    /*
     * Het maken van een DocumentBuilderFactory is duur en omdat deze niet threadsafe is mag je ook niet dezelfde
     * instantie hergebruiken over meerdere threads. Vandaar deze oplossing: 1 DocumentBuilderFactory per thread.
     */
    private static TransformerFactory getTransformerFactory() {
        TransformerFactory result = TRANSFORMER_FACTORY_STORE.get();
        if (result == null) {
            result = TransformerFactory.newInstance();
            TRANSFORMER_FACTORY_STORE.set(result);
        }
        return result;
    }

    private static Document transformeerNaarDoc(final InputStream xmlInputStream) throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        return documentBuilderFactory.newDocumentBuilder().parse(xmlInputStream);

    }

    private void verzendSoapBericht(Document requestXmlDocument, StepResult stepResult, final boolean isBijhouding) throws TransformerException, XmlException {
        final SoapVerstuurder soapVerstuurder = new SoapVerstuurder();
        final String request = vanDocumentNaarXml(requestXmlDocument);
        stepResult.setRequest(request);
        String response;
        if (isBijhouding) {
            final String adres = Omgeving.getOmgeving().getSoapEndpointConfig().getBijhoudingAdres();
            response = soapVerstuurder.send(request, new SoapParameters(
                    "http://" + adres + "/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap",
                    "http://www.bzk.nl/brp/bijhouding/service"));
        } else {
            final String adres = Omgeving.getOmgeving().getSoapEndpointConfig().getBevragingAdres();
            response = soapVerstuurder.send(request, new SoapParameters(
                    "http://" + adres + "/bevraging/LeveringBevragingService/lvgBevraging",
                    "http://www.bzk.nl/brp/levering/bevraging/service"));
        }
        stepResult.setResponse(response);
    }

    private String vanDocumentNaarXml(final Document document) throws TransformerException {
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        final StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer.getBuffer().toString().replaceAll("[\n\r]", "");
    }
}

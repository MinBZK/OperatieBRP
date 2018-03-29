/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.xml;

import com.google.common.collect.Sets;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.ElementSelector;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

/**
 * Util klasse voor XML operaties.
 */
public final class XmlUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();
    private static Transformer transformer = null;

    private static final XMLInputFactory INPUT_FACTORY = XMLInputFactory.newInstance();
    private static final XMLOutputFactory OUTPUT_FACTORY = XMLOutputFactory.newInstance();

    private static final Set<String> STER_SET = Sets.newHashSet("referentienummer", "tijdstipVerzending");
    private static final Set<String> SORTEER_ATTR_SET = Sets.newHashSet("ouder", "onderzoek", "partner", "gegevenInOnderzoek", "bron", "actie");

    private static final XMLEventFactory XML_EVENT_FACTORY = XMLEventFactory.newFactory();

    private static final Map<String, String> NAMESPACES = new HashMap<>();

    static {
        NAMESPACES.put("brp", "http://www.bzk.nl/brp/brp0200");
        NAMESPACES.put("soap", "http://schemas.xmlsoap.org/soap/envelope");
    }

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    private static final String UTF_8 = "UTF-8";

    static {

        try {
            transformer = TRANSFORMER_FACTORY.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new AssertionError(e);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, UTF_8);
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);
        DOCUMENT_BUILDER_FACTORY.setIgnoringElementContentWhitespace(false);
    }


    private XmlUtils() {
    }

    /**
     * Doet een assertion, of in het resultaat op de gegeven xpath expressie de verwachte xml te vinden is.
     * @param xpath xpath naar het te vergelijken deel van de xml
     * @param expectedXML het stuk xml zoals verwacht
     * @param actualXML het resultaat xml, waarin we kijken of het voldoet
     */
    public static void assertGelijk(final String xpath, final String expectedXML, final String actualXML) {
        final Diff myDiff = DiffBuilder.compare(expectedXML)
                .withTest(actualXML)
                .ignoreComments()
                .ignoreWhitespace()
                .withNamespaceContext(XmlUtils.NAMESPACES)
                .withDifferenceEvaluator(new NegeerWildcardsDifferenceEvaluator())
                .build();
        if (myDiff.hasDifferences()) {
            final StringWriter out = new StringWriter();
            final PrintWriter sb = new PrintWriter(out);
            sb.printf("%nBerichten niet gelijk");
            sb.printf("%nVerschillen: ");
            for (Difference difference : myDiff.getDifferences()) {
                sb.printf("%n" + difference.toString());
            }
            LOGGER.error(out.toString());
            throw new AssertionError(out.toString());
        }
    }


    /**
     * Doet een assertion, of twee berichten gelijk zijn, waarbij verschillen in sorteervolgorde zijn toegestaan voor aangewezen attributen.
     * @param expectedXML het stuk xml zoals verwacht
     * @param actualXML het resultaat xml, waarin we kijken of het voldoet
     * @param sorteerAttributen attributen waarbij sortering niet van belang is
     */
    public static void assertGelijkNegeerVolgorde(final String expectedXML, final String actualXML, final Set<String> sorteerAttributen) {
        if (!Sets.difference(sorteerAttributen, SORTEER_ATTR_SET).isEmpty()) {
            throw new IllegalArgumentException("Sortering negeren is alleen toegestaan voor : " + SORTEER_ATTR_SET);
        }
        //maak lijst met elementselectors NB: volgorde is v belang
        final List<ElementSelector> elementSelectorList = maakElementorSelectors(sorteerAttributen);

        //@formatter:off
        final Diff myDiff = DiffBuilder.compare(expectedXML).withTest(actualXML)
                .withNamespaceContext(XmlUtils.NAMESPACES)
                .ignoreComments().ignoreWhitespace()
                .checkForSimilar()
                .withNodeMatcher(new DefaultNodeMatcher(elementSelectorList.toArray(new ElementSelector[elementSelectorList.size()])))
                .withDifferenceEvaluator(DifferenceEvaluators.chain(DifferenceEvaluators.Default, new NegeerWildcardsDifferenceEvaluator()))
                .build();
        //@formatter:on
        if (myDiff.hasDifferences()) {
            final StringWriter out = new StringWriter();
            final PrintWriter sb = new PrintWriter(out);
            sb.printf("%nBerichten niet gelijk");
            sb.printf("%nVerschillen: ");
            for (Difference difference : myDiff.getDifferences()) {
                sb.printf("%n" + difference.toString());
            }
            LOGGER.error(out.toString());
            throw new AssertionError(out.toString());
        }
    }

    private static List<ElementSelector> maakElementorSelectors(final Set<String> sorteerAttributen) {
        //maak lijst met elementselectors NB: volgorde is v belang
        final List<ElementSelector> elementSelectorList = new ArrayList<>();

        if (sorteerAttributen.contains("onderzoek")) {
            ElementSelector onderzoekElementSelector = ElementSelectors.conditionalBuilder()
                    .whenElementIsNamed("onderzoek")
                    .thenUse(ElementSelectors.and(
                            ElementSelectors.byXPath("./brp:onderzoek", NAMESPACES, ElementSelectors.byNameAndAttributesControlNS("verwerkingssoort")),
                            ElementSelectors.byXPath("./brp:gegevensInOnderzoek/brp:gegevenInOnderzoek/brp:elementNaam", NAMESPACES, ElementSelectors.byNameAndText)))
                    .build();
            elementSelectorList.add(onderzoekElementSelector);
        }
        if (sorteerAttributen.contains("gegevenInOnderzoek")) {
            ElementSelector onderzoekElementSelector = ElementSelectors.conditionalBuilder()
                    .whenElementIsNamed("gegevenInOnderzoek")
                    .thenUse(ElementSelectors.byXPath("./brp:elementNaam", NAMESPACES, ElementSelectors.byNameAndText))
                    .build();
            elementSelectorList.add(onderzoekElementSelector);
        }
        if (sorteerAttributen.contains("partner")) {
            ElementSelector partnerElementSelector = ElementSelectors.conditionalBuilder()
                    .whenElementIsNamed("partner")
                    .thenUse(ElementSelectors.and(
                            ElementSelectors.byXPath("./brp:huwelijk", NAMESPACES, ElementSelectors.byNameAndAttributesControlNS("verwerkingssoort")),
                            ElementSelectors.byXPath(
                                    "./brp:geregistreerdPartnerschap", NAMESPACES, ElementSelectors.byNameAndAttributesControlNS("verwerkingssoort"))))
                    .build();

            elementSelectorList.add(partnerElementSelector);
        }
        if (sorteerAttributen.contains("ouder")) {
            ElementSelector partnerElementSelector = ElementSelectors.conditionalBuilder()
                    .whenElementIsNamed("ouder")
                    .thenUse(ElementSelectors.and(
                            ElementSelectors.byXPath("./brp:persoon/brp:identificatienummers/brp:burgerservicenummer", NAMESPACES, ElementSelectors
                                    .byNameAndText)))
                    .build();

            elementSelectorList.add(partnerElementSelector);
        }
        if (sorteerAttributen.contains("bron")) {
            ElementSelector onderzoekElementSelector = ElementSelectors.conditionalBuilder()
                    .whenElementIsNamed("bron")
                    .thenUse(ElementSelectors.and(
                            ElementSelectors.byXPath("./brp:document/brp:omschrijving", NAMESPACES, ElementSelectors.byNameAndText),
                            ElementSelectors.byXPath("./brp:document/brp:partijCode", NAMESPACES, ElementSelectors.byNameAndText)))
                    .build();
            elementSelectorList.add(onderzoekElementSelector);
        }
        if (sorteerAttributen.contains("actie")) {
            ElementSelector onderzoekElementSelector = ElementSelectors.conditionalBuilder()
                    .whenElementIsNamed("actie")
                    .thenUse(ElementSelectors.and(
                            ElementSelectors.byXPath("./brp:partijCode", NAMESPACES, ElementSelectors.byNameAndText),
                            ElementSelectors.byXPath("./brp:datumOntlening", NAMESPACES, ElementSelectors.byNameAndText)))
                    .build();
            elementSelectorList.add(onderzoekElementSelector);
        }
        elementSelectorList.add(ElementSelectors.byName);
        return elementSelectorList;
    }

    /**
     * Geeft de string representatie van een element (plus alle onderliggende elementen).
     * @param xpathExpression de xpath expressie
     * @param document het DOM Document
     * @return de text, lege string als het pad niet bestaat.
     */
    public static String getNodeWaarde(final String xpathExpression, final Document document) {
        return getXPathEngine().evaluate(xpathExpression, document);
    }

    /**
     * XML Pretty print
     * @param xml de xml String
     * @return een geformatteerde XML String
     */
    public static String format(final String xml) {
        final Source xmlInput = new StreamSource(new StringReader(xml));
        final StringWriter outputWriter = new StringWriter();
        final StreamResult xmlOutput = new StreamResult(outputWriter);
        try {
            final Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
        } catch (final TransformerException e) {
            throw new AssertionError("XML format mislukt: " + xml, e);
        }
        return outputWriter.toString();
    }

    /**
     * Transformeert de XML, verandert de waarden in een * voor een gegeven set aan elementen.
     * @param xmlString een XML String
     * @return een getransformeerde String
     */
    public static String ster(final String xmlString) {
        final StringWriter stringWriter = new StringWriter();
        try {
            final XMLEventReader eventReader = INPUT_FACTORY.createXMLEventReader(new StringReader(xmlString));
            final XMLEventWriter eventWriter = OUTPUT_FACTORY.createXMLEventWriter(stringWriter);
            while (eventReader.hasNext()) {
                final XMLEvent xmlEvent = eventReader.nextEvent();
                if (xmlEvent.isStartElement() && STER_SET.contains(xmlEvent.asStartElement().getName().getLocalPart())) {
                    eventWriter.add(xmlEvent);
                    //vervang dynamische content door *
                    eventReader.nextEvent();
                    eventWriter.add(XML_EVENT_FACTORY.createCharacters("*"));
                    continue;
                }
                eventWriter.add(xmlEvent);
            }
        } catch (XMLStreamException e) {
            throw new AssertionError("Wegsterren mislukt", e);
        }
        return format(stringWriter.toString());
    }

    public static String ster2(String content) {

        content = content.replaceAll("<brp:referentienummer>.*?</brp:referentienummer>", "<brp:referentienummer>*</brp:referentienummer>");
        content = content.replaceAll("<brp:tijdstipVerzending>.*?</brp:tijdstipVerzending>", "<brp:tijdstipVerzending>*</brp:tijdstipVerzending>");
        content = content.replaceAll("brp:voorkomenSleutel=\".*?\"", "brp:voorkomenSleutel=\"*\"");
        content = content.replaceAll("brp:communicatieID=\".*?\"", "brp:communicatieID=\"*\"");
        content = content.replaceAll("brp:objectSleutel=\".*?\"", "brp:objectSleutel=\"*\"");
        content = content.replaceAll("brp:referentieID=\".*?\"", "brp:referentieID=\"*\"");
        content = content.replaceAll("<brp:actieInhoud>.*?</brp:actieInhoud>", "<brp:actieInhoud>*</brp:actieInhoud>");
        content = content.replaceAll("<brp:tijdstipVerval>.*?</brp:tijdstipVerval>", "<brp:tijdstipVerval>*</brp:tijdstipVerval>");
        content = content.replaceAll("<brp:actieVerval>.*?</brp:actieVerval>", "<brp:actieVerval>*</brp:actieVerval>");
        content =
                content.replaceAll("<brp:tijdstipLaatsteWijziging>.*?</brp:tijdstipLaatsteWijziging>",
                        "<brp:tijdstipLaatsteWijziging>*</brp:tijdstipLaatsteWijziging>");
        content =
                content.replaceAll("<brp:tijdstipLaatsteWijzigingGBASystematiek>.*?</brp:tijdstipLaatsteWijzigingGBASystematiek>",
                        "<brp:tijdstipLaatsteWijzigingGBASystematiek>*</brp:tijdstipLaatsteWijzigingGBASystematiek>");
        content =
                content.replaceAll("<brp:actieAanpassingGeldigheid>.*?</brp:actieAanpassingGeldigheid>",
                        "<brp:actieAanpassingGeldigheid>*</brp:actieAanpassingGeldigheid>");
        content = content.replaceAll("<brp:tijdstipRegistratie>.*?</brp:tijdstipRegistratie>", "<brp:tijdstipRegistratie>*</brp:tijdstipRegistratie>");
        content =
                content.replaceAll("<brp:voorkomenSleutelGegeven>.*?</brp:voorkomenSleutelGegeven>",
                        "<brp:voorkomenSleutelGegeven>*</brp:voorkomenSleutelGegeven>");

        return content;
    }

    /**
     * Converteert een {@link Resource} naar een {@link DOMSource}
     * @param resource een resource
     * @return een {@link DOMSource}
     */
    public static DOMSource resourceToDomSource(final Resource resource) {
        try {
            return toDOMSource(new InputSource(resource.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Converteert een {@link DOMSource} naar een String.
     * @param domSource een {@link DOMSource}
     * @return een  String
     */
    public static String domSourceToString(final DOMSource domSource) {
        try {
            final StringWriter sw = new StringWriter();
            transformer.transform(domSource, new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("Fout bij omzetten DOMSource naar String", ex);
        }
    }

    /**
     * Deze methode converteert een DOMtree object naar een XML string, zodat
     * dit menselijk leesbaar wordt en eventueel later naar een echte DOM
     * document geconverteerd kan worden.
     * @param node de "root node"
     * @return een XML string of null als er iets fout gaat.
     */
    public static String nodeToString(final Node node) {
        return domSourceToString(new DOMSource(node));
    }

    /**
     * Converteert een {@link Document} naar een XML string.
     * @param document document
     * @return XML string
     */
    public static String documentToString(final Document document) {
        final StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new AssertionError(e);
        }
        return writer.getBuffer().toString().replaceAll("\n|\r", "");
    }


    private static XPathEngine getXPathEngine() {
        final XPathEngine xPathEngine = new JAXPXPathEngine();
        xPathEngine.setNamespaceContext(NAMESPACES);
        return xPathEngine;
    }

    /**
     * Converteert en {@link InputSource} String naar {@link DOMSource}.
     * @param input een {@link InputSource}
     * @return een {@link DOMSource}
     */
    private static DOMSource toDOMSource(final InputSource input) {
        final DocumentBuilder db;
        try {
            db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
            final Document doc = db.parse(input);
            return new DOMSource(doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Converteert en {@link InputStream} naar {@link Document}.
     * @param is een {@link InputStream}
     * @return een {@link Document}
     */
    public static Document inputStreamToDocument(final InputStream is) {
        try {
            return DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().parse(is);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Maakt een DOM Document object gebaseerd opeen xml string
     * @param xmlString de xml string
     * @return het Document
     */
    public static Document stringToDocument(final String xmlString) {
        try {
            return inputStreamToDocument(new ByteArrayInputStream(xmlString.getBytes(UTF_8)));
        } catch (UnsupportedEncodingException e) {
            throw new TestclientExceptie(e);
        }
    }
}

package nl.bzk.brp.funqmachine.processors
import groovy.sql.GroovyRowResult
import groovy.xml.MarkupBuilder
import groovy.xml.XmlUtil
import junit.framework.AssertionFailedError
import nl.bzk.brp.funqmachine.informatie.WaarOnwaar
import nl.bzk.brp.funqmachine.processors.xml.AssertionMisluktError
import nl.bzk.brp.funqmachine.processors.xml.NegeerWildcardsDifferenceListener
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import org.custommonkey.xmlunit.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.xml.sax.SAXParseException

import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import java.util.regex.Pattern

class XmlProcessor {
    private final Logger logger = LoggerFactory.getLogger(XmlProcessor)

    private final def SUPPORTED_REGEX = [
        ~/\[-([1-9])\]/,
        ~/\[\]/,
        ~/\[spatie\]/
    ]

    XmlProcessor() {
        NamespaceContext nsCtx = new SimpleNamespaceContext(XmlUtils.nsMap);
        XMLUnit.setXpathNamespaceContext(nsCtx);

        XMLUnit.setIgnoreWhitespace(true)
        XMLUnit.setIgnoreComments(true)
    }

    /**
     * Doet een assertion, of in het resultaat op de gegeven xpath expressie de verwachte xml te vinden is.
     *
     * @param xpath xpath naar het te vergelijken deel van de xml
     * @param expected het stuk xml zoals verwacht
     * @param result het resultaat xml, waarin we kijken of het voldoet
     */
    void vergelijk(String xpath, def expected, def result) {
        String expectedXmlDeel = haalGedeelteUitXml(XmlUtil.serialize(expected), xpath)
        String actualXmlDeel = haalGedeelteUitXml(result, xpath)

        Diff myDiff = new Diff(expectedXmlDeel, actualXmlDeel)
        myDiff.overrideElementQualifier(new ElementNameAndTextQualifier())
        myDiff.overrideDifferenceListener(new NegeerWildcardsDifferenceListener())

        try {
            XMLAssert.assertXMLEqual(myDiff, true)
        } catch (AssertionFailedError e) {
            //AssertionFailedError is geen subklasse van Exceptie, daarom wrappen we hier
            throw new AssertionMisluktError("XML niet gelijk op xpath: $xpath", e);
        }
    }

    void xpathEvalueertNaar(String expected, String xpath, String xmlString) {
        XMLAssert.assertXpathEvaluatesTo(expected, xpath, xmlString)
    }

    void xpathBestaat(String xpath, String xmlString) {
        XMLAssert.assertXpathExists(xpath, xmlString)
    }

    void xpathBestaatNiet(String xpath, String xmlString) {
        XMLAssert.assertXpathNotExists(xpath, xmlString)
    }

    void waardesVanAttributenInVolgorde(final String xmlBericht, final String groep, final String attr, List<String> verwachteWaardes) {
        final String expr = """//brp:$groep//brp:$attr[text()]"""
        final org.w3c.dom.NodeList nodes = XmlUtils.getNodes(expr, XmlUtils.bouwDocument(xmlBericht))

        verwachteWaardes = verwachteWaardes.collect { it.trim() }
        final List<String> werkelijkeWaardes = nodes.collect { node -> node.textContent }
        assert werkelijkeWaardes == verwachteWaardes: "Sortering van $attr in de groep $groep is niet correct. Verwacht was $verwachteWaardes. Werkelijk was $werkelijkeWaardes."
    }

    void waardeVanXmlAttribuut(final String xmlBericht, final String groep, final int nummer, final String xmlAttr, final String verwachteWaarde){
        final String expr = """//brp:$groep"""
        final String attribuutWaarde = XmlUtils.getAttribuutWaarde(expr, nummer, xmlAttr, XmlUtils.bouwDocument(xmlBericht))

        assert attribuutWaarde == verwachteWaarde: "Xml Attribuut $xmlAttr in de groep $groep is niet correct. Verwacht was $verwachteWaarde. Werkelijk " +
            "was $attribuutWaarde."
    }

    private void trimWhitespace(Node node)
    {
        org.w3c.dom.NodeList children = node.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if(child.getNodeType() == Node.TEXT_NODE) {
                child.setTextContent(child.getTextContent().trim());
            }
            trimWhitespace(child);
        }
    }

    private Node removeFirstNodes(Node node) {
        org.w3c.dom.NodeList children = node.getChildNodes();
        Node gevonden = null
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                return child
            }
            gevonden = removeFirstNodes(child)
            if (gevonden != null) {
                return gevonden;
            }
        }
        return gevonden
    }

    private String prettyFormat(def node) {
        try {

            trimWhitespace(node)
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(node), xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }


    /**
     * Haalt nodes uit de gegeven xml, dmv de xpath expressie. Beide namespaces worden toegevoegd, SOAP en KERN.
     *
     * @param xml de xml om node(s) uit te halen
     * @param xpath expressie naar nodes om eruit te halen
     * @return de gezochte node
     */
    String haalGedeelteUitXml(String xml, String xpath) {
        Node node = XmlUtils.getNode(xpath, XmlUtils.bouwDocument(xml))
        return XmlUtils.toXmlString(node)
    }

    /**
     * Converts to a xml format.
     * @param rows
     * @return
     */
    static String toXml(List<GroovyRowResult> rows) {

        def writer = new StringWriter()
        new MarkupBuilder(writer).with {
            doubleQuotes = true
            def visitor = {k, v ->
                "${k.toUpperCase()}" {v instanceof Map ? v.collect(visitor) : mkp.yield(v ?: '')}
            }
            Results {
                ResultSet(fetchSize: 0) {
                    rows.eachWithIndex {row, idx ->
                        Row(rowNumber: idx + 1) {row.collect visitor}
                    }
                }
            }
        }

        return cleanupDbResult(writer.toString())
    }

    /**
     * Extra processing van de XML uit de database, omdat we Ber.Ber uitlezen en de berichten willen vergelijken.
     *
     * TODO: verwijder zo snel mogelijk
     * @param xml opmaak van een DB resultaat, in specifiek formaat
     * @return aangepaste inhoud voor de kolom Ber.Ber.bericht
     *
     * @deprecated
     */
    @Deprecated
    private static String cleanupDbResult(String xml) {
        def result = xml.replace('&lt;', '<').replace('&gt;', '>')

        // aanpassen xml om tekst rond een soap-bericht uit het db result te halen
        result = result.replaceAll(Pattern.compile('(<DATA>)(.*?)(<soap:Envelope)', Pattern.DOTALL)) {Object[] it -> "${it[1]}${it[3]}"}
        result = result.replaceAll(Pattern.compile('(</soap:Envelope>)(.*?)(</DATA>)', Pattern.DOTALL)) {Object[] it -> "${it[1]}${it[3]}"}

        return result
    }

    String verwijderLegeElementen(String text) {
        try {
            def node = new XmlParser().parseText(text)

            replaceOrDeleteNodes(node)
            replaceOrDeleteAttributes(node)

            return XmlUtil.serialize(node)
        } catch (SAXParseException spe) {
            logger.error 'Kan geen lege elementen verwijderen uit invalide XML: {}', spe.message
            logger.debug '{}', text
        }

        return ''
    }

    private def replaceOrDeleteNodes(groovy.util.Node node) {

        NodeList valueNodes = node.'**'.findAll {
            it.children().size() == 1 && it.children()[0] instanceof String && SUPPORTED_REGEX.any {rgx -> it.children()[0].matches(rgx)}
        }

        valueNodes.each {groovy.util.Node it ->
            switch (it.text()) {
                case SUPPORTED_REGEX[2]:
                    it.children()[0] = ' '
                    break
                case SUPPORTED_REGEX[0]:
                    def parentFromNode = it.text()[2] as int

                    def nodeToRemove = it
                    for (int i = 0; i < parentFromNode; i++) {
                        nodeToRemove = nodeToRemove.parent()
                    }
                    try {
                        nodeToRemove.replaceNode {}
                    } catch (Exception any) { /* ignore */ }
                    break
                case SUPPORTED_REGEX[1]:
                    it.replaceNode {}
                    break
                default:
                    break
            }
        }
    }

    private def replaceOrDeleteAttributes(groovy.util.Node node) {

        NodeList valueNodes = node.'**'.findAll {groovy.util.Node n -> n.attributes()}
            .findAll {it.attributes().any {k, v -> SUPPORTED_REGEX.any {rgx -> v.matches(rgx)}}}

        valueNodes.each {groovy.util.Node it ->
            List removeList = new ArrayList();
            it.attributes().each {k, v ->
                switch (v) {
                    case SUPPORTED_REGEX[0]:
                        def parentFromNode = (v[2] as int) - 1
                        // Verwijder node aangegeven door een negatief getal, -1 is
                        // directe node; -2 is parent daarboven
                        def nodeToRemove = it
                        if (parentFromNode > 0) {
                            for (int i = 0; i < parentFromNode; i++) {
                                nodeToRemove = nodeToRemove.parent()
                            }
                        }

                        try {
                            nodeToRemove.replaceNode {}
                        } catch (Exception any) { /* ignore */ }
                        break
                    case SUPPORTED_REGEX[1]:
                        removeList.add(k);
                        break
                    default:
                        break
                }
            }
            it.attributes().keySet().removeAll(removeList);
        }

    }

    /**
     * Gebruikt de xpathExpressie om binnen de response naar de node te zoeken. Vervolgens wordt van de gevonden node de kinderen
     * platgeslagen en vergeleken met de aangeboden waarde.
     * @param waarde
     * @param xpathExpressie
     * @param response
     */
    void berichtAlsPlatteTekstVanafXPath(final String waarde, final String xpathExpressie, final String response) {
        Document inDocument = XMLUnit.buildControlDocument(response);
        XpathEngine simpleXpathEngine = XMLUnit.newXpathEngine();
        def nodeList = simpleXpathEngine.getMatchingNodes(xpathExpressie, inDocument);
        if (nodeList.length > 0) {
            String gevonden = nodeList.inject("") {
                previousResult, node -> previousResult + prettyFormat(node)
            }
            gevonden = verwijderXmlNsAttributen(gevonden)
            assert gevonden.equals(waarde) : "Gevonden node : '$gevonden' is niet de verwachte waarde : '$waarde'"
        }
        else {
            assert false : "node kon niet gevonden worden"
        }

    }

    String verwijderXmlNsAttributen(final String gevonden) {
        if (gevonden != null) {
            return gevonden.replaceAll(Pattern.compile(" xmlns:((.)*?)\"((.)*?)\""), "")
        }
        return gevonden
    }

    void berichtHeeftGeenKinderenVoorXPath(String xpath, response) {
        xpathBestaat(xpath, response)
        xpathBestaatNiet(xpath + '/*', response)
    }

    void heeftAantalVoorkomensVanEenGroep(final int aantal, final String groep, final String response) {
        xpathEvalueertNaar(aantal as String, "count(//brp:${groep})", response)
    }

    void heeftVoorkomenVanEenGroep(final String waar, final String groep, final String response) {
        if (WaarOnwaar.isWaar(waar)) {
            xpathBestaat("//brp:${groep}", response)
        } else {
            xpathBestaatNiet("//brp:${groep}", response)
        }
    }
}

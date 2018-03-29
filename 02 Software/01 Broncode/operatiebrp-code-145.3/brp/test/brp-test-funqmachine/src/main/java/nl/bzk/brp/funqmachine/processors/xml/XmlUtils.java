/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Util class tbv XML bewerking.
 */
public final class XmlUtils {
    private static final Map<String, String> NAMESPACES = new HashMap<>();
    private static NamespaceContext namespaceContext = new NamespaceContext() {
        @Override
        public Iterator<String> getPrefixes(final String namespaceURI) {
            return NAMESPACES.entrySet().stream().filter(entry -> entry.getValue().equals(namespaceURI)).map(Map.Entry::getKey)
                    .collect(Collectors.toList()).iterator();
        }

        @Override
        public String getPrefix(final String namespaceURI) {
            return NAMESPACES.entrySet().stream().filter(entry -> entry.getValue().equals(namespaceURI)).map(Map.Entry::getKey).findFirst().orElse(null);
        }

        @Override
        public String getNamespaceURI(final String prefix) {
            return NAMESPACES.get(prefix);
        }
    };


    static {
        NAMESPACES.put("brp", "http://www.bzk.nl/brp/brp0200");
        NAMESPACES.put("soap", "http://schemas.xmlsoap.org/soap/envelope/");
        NAMESPACES.put("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    /**
     * private constructor, omdat het om een final class gaat.
     */
    private XmlUtils() {

    }

    public static Map<String, String> getNamespaces() {
        return Collections.unmodifiableMap(NAMESPACES);
    }

    /**
     * Deze methode converteert een DOMtree object naar een XML string, zodat
     * dit menselijk leesbaar wordt en eventueel later naar een echte DOM
     * document geconverteerd kan worden.
     * @param node de "root node"
     * @return een XML string of null als er iets fout gaat.
     * @throws XmlException Als er iets fout gaat tijdens XML bewerkingen
     */
    public static String toXmlString(final Node node) throws XmlException {
        try {
            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            final StringWriter stringWriter = new StringWriter();
            final Result result = new StreamResult(stringWriter);

            final Source source = new DOMSource(node);
            transformer.transform(source, result);
            return stringWriter.toString();
        } catch (final TransformerException e) {
            throw new XmlException(e);
        }
    }

    /**
     * Maakt een DOM Document object gebaseerd opeen xml string
     * @param xmlString de xml string
     * @return het Document
     * @throws XmlException als er iets fout is gegaan met de XML bewerking
     */
    public static Document bouwDocument(final String xmlString) throws XmlException {
        try {
            final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            documentFactory.setNamespaceAware(true);
            documentFactory.setIgnoringElementContentWhitespace(false);
            final DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            return documentBuilder.parse(new ByteArrayInputStream(xmlString.getBytes(Charset.defaultCharset())));
        } catch (final ParserConfigurationException | IOException | SAXException e) {
            throw new XmlException(e);
        }
    }

    /**
     * Geeft de node terug behorende bij de opgegeven xpathExpression.
     * @param xpathExpression de XPath expressie om een node te vinden
     * @param document het document waarin de node gevonden moet worden
     * @return de Node behorende bij de xpathExpression
     * @throws XmlException Als er iets fout gaat tijdens XML bewerkingen
     */
    public static Node getNode(final String xpathExpression, final Document document) throws XmlException {
        final XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(namespaceContext);
        try {
            return (Node) xpath.evaluate(xpathExpression, document, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new XmlException(e);
        }
    }


    /**
     * Maakt van XML een leesbaar formaat ipv 1 regel.
     * @param xml de XML
     * @return de XML in leesbaar formaat
     * @throws XmlException Als er iets fout gaat tijdens XML bewerkingen
     */
    public static String prettify(String xml) throws XmlException {
        final Document document = bouwDocument(xml);
        return toXmlString(document);
    }
}

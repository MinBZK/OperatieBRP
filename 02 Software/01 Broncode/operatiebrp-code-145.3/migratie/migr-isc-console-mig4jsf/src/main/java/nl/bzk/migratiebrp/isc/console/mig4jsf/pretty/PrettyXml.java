/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pretty;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Pretty print XML.
 */
public class PrettyXml {
    private final DocumentBuilder builder;
    private final XPath xpath;

    /**
     * Constructor.
     */
    protected PrettyXml() {
        final DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        try {
            builder = dbfactory.newDocumentBuilder();
        } catch (final ParserConfigurationException e) {
            throw new IllegalArgumentException(e);
        }

        final XPathFactory xPathfactory = XPathFactory.newInstance();
        xpath = xPathfactory.newXPath();
    }

    /**
     * Pretty print XML.
     * @param xml xml
     * @return pretty printed xml
     */
    public final String prettyPrint(final String xml) {
        final StringBuilder sb = new StringBuilder();
        try {
            final Document document = parseXml(xml);
            beforeXml(sb, document);
            outputXml(sb, document);
            afterXml(sb, document);
        } catch (final Exception e) {
            sb.append("<br/>Fout opgetreden tijdens parsen: ").append(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Hook before XML output.
     * @param sb output
     * @param document document
     */
    protected void beforeXml(final StringBuilder sb, final Document document) {
        // hook
    }

    /**
     * Hook after XML output.
     * @param sb output
     * @param document document
     */
    protected void afterXml(final StringBuilder sb, final Document document) {
        // hook
    }

    private Document parseXml(final String xml) throws SAXException, IOException {
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    private void outputXml(final StringBuilder sb, final Document document) {
        sb.append("<table class=\"dataform\">");
        sb.append("<thead><tr><th>XML</th></tr></thead>");
        sb.append("<tbody>");
        sb.append("<tr><td>");
        sb.append("<textarea class=\"pretty-xml\" rows=\"12\" wrap=\"off\" readonly=\"true\">");
        try {
            escape(sb, formatXml(document));
        } catch (final TransformerException e) {
            sb.append("Fout opgetreden tijdens XML format.\n").append(e.getMessage());
        }
        sb.append("</textarea>");
        sb.append("</td></tr>");
        sb.append("</tbody></table>");
        sb.append("<br/>");
    }

    private String formatXml(final Document document) throws TransformerException {
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        final Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "3");

        final DOMSource source = new DOMSource(document);
        final StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);

        return result.getWriter().toString();
    }

    private void escape(final StringBuilder sb, final String xml) {
        for (int i = 0; i < xml.length(); i++) {
            final char ch = xml.charAt(i);

            if (ch == '<') {
                sb.append("&lt;");
            } else {
                sb.append(ch);
            }
        }
    }

    /**
     * Execute an xpath expression.
     * @param expression expression
     * @param source source
     * @return string result
     */
    protected final String evaluateXPath(final String expression, final Node source) {
        try {
            return xpath.evaluate(expression, source);
        } catch (final XPathExpressionException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Geef de waarde van x path.
     * @return x path
     */
    protected final XPath getXPath() {
        return xpath;
    }
}

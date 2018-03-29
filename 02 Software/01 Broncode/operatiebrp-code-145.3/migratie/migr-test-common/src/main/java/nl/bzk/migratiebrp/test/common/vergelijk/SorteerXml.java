/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML sorteerder.
 */
public final class SorteerXml {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final DocumentBuilder documentBuilder;
    private final Transformer transformer;


    /**
     * XML sorteerder.
     */
    public SorteerXml() {
        final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
        documentFactory.setNamespaceAware(true);

        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("Kon JAXP (document builder) niet initialiseren", e);
        }

        // Use a Transformer for output
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        } catch (TransformerConfigurationException e) {
            throw new IllegalArgumentException("Kon JAXP (transformer) niet initialiseren", e);
        }
    }

    /**
     * Sorteer XML.
     * @param input xml
     * @return gesorteerde xml
     */
    public static String sorteer(final String input) {
        return new SorteerXml().sorteerXml(input);
    }

    /**
     * Sorteer een XML string.
     * @param input xml
     * @return gesorteerde xml
     */
    public String sorteerXml(final String input) {
        // Parse
        final Document document;
        try {
            document = documentBuilder.parse(new InputSource(new StringReader(input)));
        } catch (SAXException | IOException e) {
            LOG.info("Kon XML niet parsen om te sorteren; XML wordt onverwerkt teruggegeven.", e);
            return input;
        }
        final Element element = document.getDocumentElement();

        // Sort
        final Document result = documentBuilder.newDocument();
        final Element sortedElement = sorteer(result, element);
        result.appendChild(sortedElement);

        // Format
        return formatNode(sortedElement);
    }

    private String formatNode(final Node node) {
        final StringWriter result = new StringWriter();
        try {
            transformer.transform(new DOMSource(node), new StreamResult(result));
        } catch (TransformerException e) {
            throw new IllegalArgumentException("Kon node niet formatteren", e);
        }

        return result.toString();
    }

    private <T extends Node> T sorteer(final Document document, final T node) {
        // Ignore 'empty' nodes
        if (node.getNodeType() == Node.TEXT_NODE
                && node.getTextContent().trim().length() == 0) {
            return null;
        }

        // Clone node
        final T result = cloneNode(document, node);

        // Recursive sort contents of children
        final List<Node> resultChildren = new ArrayList<>();
        final NodeList children = node.getChildNodes();
        for (int childIndex = 0; childIndex < children.getLength(); childIndex++) {
            final Node sortedChild = sorteer(document, children.item(childIndex));
            if (sortedChild != null) {
                resultChildren.add(sortedChild);
            }
        }

        // Sort children and append to cloned node
        Collections.sort(resultChildren, new NodeComparator());
        for (final Node resultChild : resultChildren) {
            result.appendChild(resultChild);
        }

        // Done
        return result;
    }

    private static <T extends Node> T cloneNode(final Document document, final T node) {
        final T result;
        if (node instanceof Attr) {
            result = (T) document.createAttributeNS(node.getNamespaceURI(), node.getNodeName());
        } else if (node instanceof CDATASection) {
            result = (T) document.createCDATASection(node.getNodeValue());
        } else if (node instanceof Element) {
            result = (T) document.createElementNS(node.getNamespaceURI(), node.getNodeName());
        } else if (node instanceof EntityReference) {
            result = (T) document.createEntityReference(node.getNodeName());
        } else if (node instanceof ProcessingInstruction) {
            result = (T) document.createProcessingInstruction(node.getNodeName(), node.getNodeValue());
        } else if (node instanceof Text) {
            result = (T) document.createTextNode(node.getNodeValue());
        } else {
            throw new IllegalArgumentException("Unknown node type to clone. Class is '" + node.getClass() + "'.");
        }
        return result;
    }

    private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(final Node o1, final Node o2) {
            final String xml1 = formatNode(o1);
            final String xml2 = formatNode(o2);

            return xml1.compareTo(xml2);
        }
    }
}

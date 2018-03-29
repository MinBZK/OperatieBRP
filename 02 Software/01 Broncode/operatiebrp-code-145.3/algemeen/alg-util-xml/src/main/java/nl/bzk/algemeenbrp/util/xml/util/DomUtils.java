/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.util;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * Dom utilities.
 */
public final class DomUtils {

    private DomUtils() {
        // Niet instantieerbaar
    }

    /**
     * Geef het eerste kind element met de gegeven naam.
     * @param element element waarin het kind gevonden moet worden
     * @param name naam
     * @return kind indien gevonden, anders null
     */
    public static Element getChildElement(final Element element, final String name) {
        final NodeList nodeList = element.getChildNodes();

        for (int index = 0; index < nodeList.getLength(); index++) {
            final Node node = nodeList.item(index);

            if (node instanceof Element && name.equals(node.getLocalName())) {
                return (Element) node;
            }
        }
        return null;
    }

    /**
     * Geef het attribuut met de gegeven naam.
     * @param element element waarin het kind gevonden moet worden
     * @param name naam
     * @return attribuut indien gevonden, anders null
     */
    public static Attr getChildAttribute(final Element element, final String name) {
        return element.getAttributeNode(name);
    }

    /**
     * Geef alle kind elementen met de gegeven naam.
     * @param element element waarin de kind element gevonden moet worden
     * @param name naam
     * @return lijst van kind elementen, kan een lege lijst zijn
     */
    public static List<Element> getDirectChildrenNamed(final Element element, final String name) {
        final NodeList nodeList = element.getChildNodes();

        final List<Element> result = new ArrayList<>();
        for (int index = 0; index < nodeList.getLength(); index++) {
            final Node node = nodeList.item(index);

            if (node instanceof Element && name.equals(node.getLocalName())) {
                result.add((Element) node);
            }
        }
        return result;
    }

    /**
     * Bepaal of de gegeven node genegeerd kan worden.
     * @param node node
     * @return true, als de node genegeerd kan worden, anders false
     */
    public static boolean isIgnorableNode(final Node node) {
        boolean result = false;
        if (node instanceof Comment || node instanceof Notation || node instanceof ProcessingInstruction) {
            // Commentaar, etc kan genegeerd worden
            result = true;
        }

        if (node instanceof Text && node.getTextContent().trim().isEmpty()) {
            // Lege text kan genegeerd worden
            result = true;
        }

        if (node instanceof Attr) {
            if (node.getNodeName().startsWith("xmlns")) {
                // Alles met namespaces wordt genegeerd
                result = true;
            }

            if ("class".equals(node.getLocalName())) {
                // Class identificatie wordt door het framework gebruikt.
                result = true;
            }
        }

        return result;
    }

}

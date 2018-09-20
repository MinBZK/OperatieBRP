/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;

/**
 * In binding-objecttypen-bericht.xml mogen de bindings voor bericht persoon niet meer voorkomen
 */
public abstract class AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

    /**
     * Verwijder de node gevonden via het path binnen het root element.
     * @param rootElementBinding root element
     * @param path zoek dit pad vanaf het root element
     */
    protected final void removeNode(final Document rootElementBinding, final String path) {
        final Element gevonden = zoekElement(rootElementBinding, path);
        if (gevonden != null) {
            gevonden.detach();
        }
    }

    /**
     * find de node via het path binnen het root element.
     * @param rootElementBinding root element
     * @param path zoek dit pad vanaf het root element
     * @return node die gevonden is of null indien niet gevonden
     */
    protected final Element zoekElement(final Document rootElementBinding, final String path) {
        Element findElement = null;
        final List<Object> structures = XPathHelper.maakXPathExpressie(path).evaluate(rootElementBinding);
        if (!structures.isEmpty()) {
            findElement = (Element) structures.get(0);
        }
        return findElement;
    }
}

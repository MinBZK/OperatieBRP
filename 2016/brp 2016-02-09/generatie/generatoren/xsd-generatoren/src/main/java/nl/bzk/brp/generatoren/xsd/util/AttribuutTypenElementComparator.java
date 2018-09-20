/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd.util;

import java.util.Comparator;

import org.jdom2.Element;

/**
 * Simpele comparator om elementen op alfabet van hun attribuut 'name' te sorteren.
 * Dus bijvoorbeeld:
 * <simpleType name="SoortPersoonCode-s">
 * komt voor
 * <simpleType name="VerwerkingCode-s">
 */
public class AttribuutTypenElementComparator implements Comparator<Element> {

    @Override
    public int compare(final Element e1, final Element e2) {
        return e1.getAttribute("name").getValue().compareTo(e2.getAttribute("name").getValue());
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges.bijhouden;

import nl.bzk.brp.generatoren.jibx.customchanges.AbstractHandmatigeVerwijzing;
import nl.bzk.brp.generatoren.jibx.customchanges.HandmatigeWijziging;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

/**
 * deze bindings worden door bijhouding gebruikt, daar heet het element acties en niet 'bijgehoudenActies'
 */
public class HernoemBijgehoudenActiesStructure extends AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

    @Override
    public void voerUit(final Document rootElementBinding)
            throws XPathFactoryConfigurationException, XPathExpressionException
    {
        final Element structure = zoekElement(rootElementBinding, "/binding/mapping[@type-name='AbstractAdministratieveHandelingBericht']/structure[@name='bijgehoudenActies']");
        if (structure != null) {
            structure.setAttribute("name", "acties");
        }
    }
}

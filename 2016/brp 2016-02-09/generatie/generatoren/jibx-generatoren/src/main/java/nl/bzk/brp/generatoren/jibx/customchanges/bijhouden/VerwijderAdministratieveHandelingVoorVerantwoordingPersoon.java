/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges.bijhouden;

import nl.bzk.brp.generatoren.jibx.customchanges.AbstractHandmatigeVerwijzing;
import nl.bzk.brp.generatoren.jibx.customchanges.HandmatigeWijziging;
import org.jdom2.Document;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

/**
 * dit heeft met verantwoording van persoon te maken, dit komt in de bijhouding in principe niet binnen.
 */
public class VerwijderAdministratieveHandelingVoorVerantwoordingPersoon extends AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

    @Override
    public void voerUit(final Document rootElementBinding)
            throws XPathFactoryConfigurationException, XPathExpressionException
    {
        removeNode(rootElementBinding,
                "/binding/mapping[@type-name='AbstractPersoonAdministratieveHandelingBericht']/structure[@field='administratieveHandeling']");
    }
}

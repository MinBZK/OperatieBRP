/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges;

import org.jdom2.Attribute;
import org.jdom2.Document;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;
import java.util.List;

/**
 * De binding maakt van ISO31661Alpha2 element iSO31661Alpha2, omdat we alleen de eerste letter lowercasen.
 * Echter wil Gert-Jan hier iso31661Alpha2Code, dus de eerste 3 letters lower casen en "Code" aan het eind.
 * Dit moet in het BMR worden opgelost
 * door een XSD_IDENT op te nemen voor dit attribuut. Het verzoek ligt nu bij Gert-Jan, voorlopig lossen we het hier op
 * met een handmatige wijziging.
 */
public class ISO31661Alpha2 extends AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

    @Override
    public void voerUit(final Document rootElementBinding)
            throws XPathFactoryConfigurationException, XPathExpressionException
    {
        final List<Object> structures = XPathHelper.maakXPathExpressie(
                "/binding/mapping[@class='nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied']/structure/structure[@name='iSO31661Alpha2']/@name")
                                                 .evaluate(rootElementBinding);
        final Attribute attribuut = (Attribute) structures.get(0);
        attribuut.setValue("iso31661Alpha2Code");

    }
}

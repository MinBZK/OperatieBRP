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
 * In binding-objecttypen-bericht.xml moet het veld soortActie onder AbstractActieBericht niet ge-bind worden.
 * Het attribuut hoort in het BMR ook niet het vlaggetje in_bericht = 'J' te hebben. Moet nog met Gert-Jan gekeken
 * worden waarom het op 'J' is gezet.
 */
public class SoortActieBijhouding extends AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

    @Override
    public void voerUit(final Document rootElementBinding)
            throws XPathFactoryConfigurationException, XPathExpressionException
    {
        removeNode(rootElementBinding, "/binding/mapping[@class='nl.bzk.brp.model.bericht.kern.AbstractActieBericht']/structure[@field='soort']");
    }
}

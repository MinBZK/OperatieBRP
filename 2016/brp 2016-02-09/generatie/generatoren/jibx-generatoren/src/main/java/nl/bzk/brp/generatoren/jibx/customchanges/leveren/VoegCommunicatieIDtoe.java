/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges.leveren;

import nl.bzk.brp.generatoren.jibx.customchanges.AbstractHandmatigeVerwijzing;
import nl.bzk.brp.generatoren.jibx.customchanges.HandmatigeWijziging;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

/**
 * Voeg een value toe voor de communicatieID voor DELTA-1626 & DELTA-1627.
 * <value style="attribute" name="communicatieID" usage="optional" get-method="getCommunicatieID"/>
 */
public class VoegCommunicatieIDtoe extends AbstractHandmatigeVerwijzing implements HandmatigeWijziging {

	@Override
	public void voerUit(final Document rootElementBinding)
			throws XPathFactoryConfigurationException, XPathExpressionException {
		final Element element = zoekElement(rootElementBinding, "/binding/mapping[@class='nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView']");
		if (element != null) {
			element.addContent(new Element("value")
					.setAttribute("style", "attribute")
					.setAttribute("name", "communicatieID")
					.setAttribute("usage", "optional")
					.setAttribute("get-method", "getCommunicatieID"));
		}
	}
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges;

import org.jdom2.Document;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

/**
 *
 */
public interface HandmatigeWijziging {

    /**
     * Dient het document aan te passen zoals gewenst.
     * @param rootElementBinding
     * @throws XPathFactoryConfigurationException
     * @throws XPathExpressionException
     */
    public void voerUit(final Document rootElementBinding)
            throws XPathFactoryConfigurationException, XPathExpressionException;

}

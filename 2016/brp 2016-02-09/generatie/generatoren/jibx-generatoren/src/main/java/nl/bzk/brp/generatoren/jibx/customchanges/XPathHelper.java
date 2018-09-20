/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.customchanges;


import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 * Helper class voor handmatige aanpassingen middels xpath.
 */
public class XPathHelper {

    private static final XPathFactory xPathFactory = XPathFactory.instance();

    public static XPathExpression<Object> maakXPathExpressie(final String expressieString) {
        return xPathFactory.compile(expressieString);
    }

}

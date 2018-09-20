/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.mvi;

import java.io.Serializable;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.moderniseringgba.isc.esb.message.AbstractBericht;

/**
 * Basis implementatie voor een MVI bericht.
 */
public abstract class AbstractMviBericht extends AbstractBericht implements MviBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String MVI_PREFIX = "mvi";
    private static final String MVI_NS = "http://mvi.vospg.gbav.gba.nl/pl_sync";

    private static final XPath XPATH;
    static {
        XPATH = XPathFactory.newInstance().newXPath();

        XPATH.setNamespaceContext(new NamespaceContext() {

            @Override
            public Iterator<String> getPrefixes(final String namespaceURI) {
                throw new UnsupportedOperationException("not implemented");
            }

            @Override
            public String getPrefix(final String namespaceURI) {
                return MVI_PREFIX;
            }

            @Override
            public String getNamespaceURI(final String prefix) {
                return MVI_NS;
            }
        });
    }

    /**
     * Evalueer een xpath expressie.
     * 
     * @param document
     *            document
     * @param expression
     *            xpath
     * @return resultaat
     */
    protected final String evaluateXpath(final Object document, final String expression) {
        try {
            return XPATH.evaluate(expression, document);
        } catch (final XPathExpressionException e) {
            throw new IllegalArgumentException("fout in xpath expressie.", e);
        }
    }
}

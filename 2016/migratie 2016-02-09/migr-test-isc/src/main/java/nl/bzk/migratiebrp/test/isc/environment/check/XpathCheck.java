/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.check;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Controle voor Xpath expressie.
 */
public final class XpathCheck implements Check {

    @Override
    public boolean check(final CheckContext context, final Bericht bericht, final TestBericht testBericht, final String config) throws TestException {
        final int firstIndex = config.indexOf(',');
        if (firstIndex == -1) {
            throw new TestException("Ongeldige configuratie voor xpath check: " + config);
        }

        final String xpathExpressie = config.substring(0, firstIndex);
        final String verwachtResultaat = config.substring(firstIndex + 1);
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(new ByteArrayInputStream(bericht.getInhoud().getBytes("UTF-8")));
            final XPathFactory xPathfactory = XPathFactory.newInstance();
            final XPath xpath = xPathfactory.newXPath();
            final XPathExpression expr = xpath.compile(xpathExpressie);

            final String resultaat = (String) expr.evaluate(doc, XPathConstants.STRING);
            return verwachtResultaat.equals(resultaat);

        } catch (final
            IOException
            | ParserConfigurationException
            | SAXException
            | XPathExpressionException e)
        {
            throw new TestException("Probleem tijdens xpath check", e);
        }

    }

}

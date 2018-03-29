/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Source;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.funqmachine.processors.xml.NegeerWildcardsDifferenceEvaluator;
import nl.bzk.brp.funqmachine.processors.xml.XmlException;
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

/**
 * XML Processor class waar bewerkingen op/met XML worden gedaan.
 */
public class XmlProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Doet een assertion, of in het resultaat op de gegeven xpath expressie de verwachte xml te vinden is.
     * @param xpath xpath naar het te vergelijken deel van de xml
     * @param expected het stuk xml zoals verwacht
     * @param result het resultaat xml, waarin we kijken of het voldoet
     */
    public void vergelijk(String xpath, String expected, String result) {
        try {
            String expectedXmlDeel = haalGedeelteUitXml(expected, xpath);
            String actualXmlDeel = haalGedeelteUitXml(result, xpath);
            final Diff myDiff =
                    DiffBuilder.compare(expectedXmlDeel).withTest(actualXmlDeel).ignoreComments().ignoreWhitespace()
                            .withNamespaceContext(XmlUtils.getNamespaces()).withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                            .withDifferenceEvaluator(DifferenceEvaluators.chain(DifferenceEvaluators.Default, new NegeerWildcardsDifferenceEvaluator()))
                            .build();

            final List<String> differences = new ArrayList<>();
            final List<Difference> realDifferent = new ArrayList<>();
            if (myDiff.hasDifferences()) {
                myDiff.getDifferences().forEach(difference -> {
                    if (difference.getResult() == ComparisonResult.DIFFERENT) {
                        differences.add(difference.toString());
                        LOGGER.error(difference.toString());
                        realDifferent.add(difference);
                    }
                });
            }

            assertTrue(String.format("XML niet gelijk op xpath %s%n%s", xpath, differences), realDifferent.isEmpty());
        } catch (final XmlException e) {
            throw new ProcessorException(e);
        }
    }

    /**
     * Controleert de verwerking in het resultaat tegen het verwachte.
     * @param expected de verwachte verwerking
     * @param antwoordBericht het antwoord bericht
     */
    public void controleerVerwerkingInAntwoordBericht(final String expected, final String antwoordBericht) {
        if (antwoordBericht == null) {
            throw new AssertionError("geen antwoordbericht verkregen");
        }
        JAXPXPathEngine xPathEngine = getXPathEngine();
        assertEquals(expected, xPathEngine.evaluate("//brp:resultaat/brp:verwerking", convertToSource(antwoordBericht)));
    }

    private JAXPXPathEngine getXPathEngine() {
        final XPathEngine xPathEngine = new JAXPXPathEngine();
        xPathEngine.setNamespaceContext(XmlUtils.getNamespaces());
        return (JAXPXPathEngine) xPathEngine;
    }

    private Source convertToSource(String xmlString) {
        return Input.fromString(xmlString).build();
    }

    private String haalGedeelteUitXml(String xml, String xpath) throws XmlException {
        Node node = XmlUtils.getNode(xpath, XmlUtils.bouwDocument(xml));
        return XmlUtils.toXmlString(node);
    }
}

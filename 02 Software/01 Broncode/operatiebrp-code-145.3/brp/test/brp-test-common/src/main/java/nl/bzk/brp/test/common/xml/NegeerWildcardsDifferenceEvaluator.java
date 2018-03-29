/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.xml;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DifferenceEvaluator;

/**
 * Deze klasse wordt gebruikt door XMLUnit om de xml-vergelijkingen te doen. Er zijn excepties opgenomen om met wild-
 * cards om te kunnen gaan (plaatsen van het {@link #NEGEER_KARAKTER} in de expected xml's). LETOP: Deze class is stateful!
 */
public final class NegeerWildcardsDifferenceEvaluator implements DifferenceEvaluator {
    private static final String       NEGEER_KARAKTER = "*";
    private final        List<String> xpathsOverslaan = new ArrayList<>();

    @Override
    public ComparisonResult evaluate(final Comparison comparison, final ComparisonResult comparisonResult) {
        final Comparison.Detail controlDetails = comparison.getControlDetails();

        final String xpathExpected = bepaalControlDetailsXPath(controlDetails);
        final String xpathActual = comparison.getTestDetails().getXPath();
        final Node controlNode = controlDetails.getTarget();

        final String nodeWaarde;
        if (controlNode instanceof CharacterData) {
            // Node waarde
            nodeWaarde = ((CharacterData) controlNode).getData();
        } else {
            // Attribuut waarde
            nodeWaarde = String.valueOf(controlDetails.getValue());
        }

        ComparisonResult resultaat = comparisonResult;

        if (NEGEER_KARAKTER.equals(nodeWaarde)) {
            xpathsOverslaan.add(xpathExpected);
            resultaat = ComparisonResult.EQUAL;
        } else if (controlNode != null) {
            final int aantalKinderen = controlNode.getChildNodes().getLength();
            if (ComparisonType.CHILD_NODELIST_LENGTH == comparison.getType() && aantalKinderen == 1) {
                final String waardeEersteKind = controlNode.getFirstChild().getNodeValue();
                if (NEGEER_KARAKTER.equals(waardeEersteKind)) {
                    xpathsOverslaan.add(xpathExpected);
                    resultaat = ComparisonResult.EQUAL;
                }
            }
        }

        // Als resultaat nog niet EQUAL is, dan kan het actuele XPath nog steeds wel als EQUAL worden aangemerkt als een deel van het actuele XPath eerder is
        // aangemerkt als EQUAL (dmv NEGEER_KARAKTER). LET OP Dit is dus stateful
        if (resultaat != ComparisonResult.EQUAL) {
            for (String xpathOverslaan : xpathsOverslaan) {
                if (xpathActual != null && xpathActual.contains(xpathOverslaan)) {
                    resultaat = ComparisonResult.EQUAL;
                    break;
                }
            }
        }
        return resultaat;
    }

    private String bepaalControlDetailsXPath(final Comparison.Detail controlDetails) {
        final String xpathExpected;
        final String controlDetailsXPath = controlDetails.getXPath();
        if (controlDetailsXPath != null && controlDetailsXPath.contains("/text()")) {
            xpathExpected = controlDetailsXPath.substring(0, controlDetailsXPath.length() - "/text()[1]".length());
        } else {
            xpathExpected = controlDetailsXPath;
        }
        return xpathExpected;
    }
}

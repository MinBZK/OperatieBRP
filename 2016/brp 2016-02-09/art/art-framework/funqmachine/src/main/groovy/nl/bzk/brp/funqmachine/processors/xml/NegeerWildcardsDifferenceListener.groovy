package nl.bzk.brp.funqmachine.processors.xml

import org.apache.xerces.dom.TextImpl
import org.custommonkey.xmlunit.Difference
import org.custommonkey.xmlunit.DifferenceConstants
import org.custommonkey.xmlunit.DifferenceListener

/**
 * Deze klasse wordt gebruikt door XMLUnit om de xml-vergelijkingen te doen. Er zijn excepties opgenomen om met wild-
 * cards om te kunnen gaan (* in de expected xml's).
 */
class NegeerWildcardsDifferenceListener implements DifferenceListener {

    final List xpathsOverslaan = new ArrayList()

    @Override
    public int differenceFound(Difference difference) {
        def xpathExpected = difference.getControlNodeDetail().getXpathLocation()
        if (xpathExpected != null && xpathExpected.contains("/text()")) {
            xpathExpected = xpathExpected.substring(0, xpathExpected.length() - "/text()[1]".length())
        }
        def xpathActual = difference.getTestNodeDetail().getXpathLocation()
        def nodeWaarde;
        if (difference.controlNodeDetail.node instanceof TextImpl) {
            //Node waarde
            nodeWaarde = ((TextImpl) difference.controlNodeDetail.node).data
        } else {
            //Attribuut waarde
            nodeWaarde = difference.controlNodeDetail.value
        }
        if (nodeWaarde == '*') {
            xpathsOverslaan.add(xpathExpected)
            return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
        }
        def controlNode = difference.getControlNodeDetail().getNode()

        if (controlNode != null) {
            def aantalKinderen = controlNode.getChildNodes().getLength()
            if (DifferenceConstants.CHILD_NODELIST_LENGTH_ID == difference.getId()
                && aantalKinderen == 1) {
                def waardeEersteKind = controlNode.getFirstChild().getNodeValue()
                if (waardeEersteKind == '*') {
                    xpathsOverslaan.add(xpathExpected)
                    return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
                }
            }
        }

        for (String xpathOverslaan : xpathsOverslaan) {
            if (xpathActual.contains(xpathOverslaan)) {
                return RETURN_IGNORE_DIFFERENCE_NODES_IDENTICAL;
            }
        }

        return RETURN_ACCEPT_DIFFERENCE;
    }

    @Override
    void skippedComparison(final org.w3c.dom.Node node, final org.w3c.dom.Node node1) {

    }

}

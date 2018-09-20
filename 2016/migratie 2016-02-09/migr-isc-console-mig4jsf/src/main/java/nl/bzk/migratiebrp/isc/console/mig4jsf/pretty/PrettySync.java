/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.pretty;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Pretty print sync berichten.
 */
public final class PrettySync extends PrettyXml {

    private static final String FOUTMELDING_PARSEN_BERICHT = "Fout opgetreden bij parsen bericht: ";
    private static final String XPATH_LO3_BERICHT_ALS_TELETEX = "//lo3BerichtAsTeletexString/text()";
    private static final String XPATH_SELECT_LO3_CONVERTEERNAARBRPVERZOEK = XPATH_LO3_BERICHT_ALS_TELETEX;
    private static final String XPATH_SELECT_LO3_SYNCHRONISEERNAARBRPVERZOEK = XPATH_LO3_BERICHT_ALS_TELETEX;

    private static final String XPATH_LO3PL_TEXT = "//lo3Pl/text()";
    private static final String XPATH_SELECT_LO3_CONVERTEERNAARLO3ANTWOORD = XPATH_LO3PL_TEXT;
    private static final String XPATH_SELECT_LO3_LEESUITBRPANTWOORD = XPATH_LO3PL_TEXT;
    private static final String XPATH_SELECT_LO3_SYNCHRONISEERNAARLO3ANTWOORD = XPATH_LO3PL_TEXT;

    private static final String DIV_START_XML = "<div class='pretty-sync-xml'>";
    private static final String DIV_START_LO3 = "<div class='pretty-sync-lo3'>";
    private static final String DIV_START_TAB_CONTAINER = "<div class='tab-container'>";
    private static final String DIV_END = "</div>";

    private final PrettyLo3 prettyLo3 = new PrettyLo3();

    @Override
    protected void beforeXml(final StringBuilder sb, final Document document) {
        sb.append(DIV_START_XML);
    }

    @Override
    protected void afterXml(final StringBuilder sb, final Document document) {
        sb.append(DIV_END);
        outputAdditional(sb, document);
    }

    private void outputAdditional(final StringBuilder sb, final Document document) {
        final String baseElementName = stripNamespace(document.getDocumentElement().getNodeName());
        switch (baseElementName) {
            case "converteerNaarBrpVerzoek":
                outputForConverteerNaarBrpVerzoek(sb, document);
                break;
            case "converteerNaarLo3Antwoord":
                outputForConverteerNaarLo3Antwoord(sb, document);
                break;
            case "leesUitBrpAntwoord":
                outputForLeesUitBrpAntwoord(sb, document);
                break;
            case "synchroniseerNaarBrpVerzoek":
                outputForSynchroniseerNaarBrpVerzoek(sb, document);
                break;
            case "synchroniseerNaarBrpAntwoord":
                outputForSynchroniseerNaarBrpAntwoord(sb, document);
                break;
            default:
                break;
        }

    }

    private String stripNamespace(final String nodeName) {
        final int separatorLocation = nodeName.indexOf(':');

        if (separatorLocation == -1) {
            return nodeName;
        } else {
            return nodeName.substring(separatorLocation + 1);
        }
    }

    private void outputSynchronisatieOfConversieBericht(final StringBuilder sb, final String opgehaaldeWaardeXpath) {
        sb.append(DIV_START_LO3);
        try {
            prettyLo3.appendInhoud(sb, opgehaaldeWaardeXpath);
        } catch (final BerichtSyntaxException e) {
            sb.append(FOUTMELDING_PARSEN_BERICHT + e);
        }
        sb.append(DIV_END);

    }

    private void outputForConverteerNaarBrpVerzoek(final StringBuilder sb, final Document document) {
        outputSynchronisatieOfConversieBericht(sb, evaluateXPath(XPATH_SELECT_LO3_CONVERTEERNAARBRPVERZOEK, document));
    }

    private void outputForConverteerNaarLo3Antwoord(final StringBuilder sb, final Document document) {
        outputSynchronisatieOfConversieBericht(sb, evaluateXPath(XPATH_SELECT_LO3_CONVERTEERNAARLO3ANTWOORD, document));
    }

    private void outputForLeesUitBrpAntwoord(final StringBuilder sb, final Document document) {
        outputSynchronisatieOfConversieBericht(sb, evaluateXPath(XPATH_SELECT_LO3_LEESUITBRPANTWOORD, document));
    }

    private void outputForSynchroniseerNaarBrpVerzoek(final StringBuilder sb, final Document document) {
        outputSynchronisatieOfConversieBericht(sb, evaluateXPath(XPATH_SELECT_LO3_SYNCHRONISEERNAARBRPVERZOEK, document));
    }

    private void outputForSynchroniseerNaarBrpAntwoord(final StringBuilder sb, final Document document) {
        NodeList nodeList;
        try {
            nodeList = (NodeList) getXPath().evaluate(XPATH_SELECT_LO3_SYNCHRONISEERNAARLO3ANTWOORD, document, XPathConstants.NODESET);
        } catch (final XPathExpressionException e) {
            throw new IllegalArgumentException(e);
        }

        if (nodeList != null && nodeList.getLength() > 0) {
            sb.append(DIV_START_LO3);
            sb.append(DIV_START_TAB_CONTAINER);

            sb.append("<ul>");
            for (int i = 0; i < nodeList.getLength(); i++) {
                sb.append("<li><a href=\"#tabs-").append(i).append("\">LO3 Persoonslijst</a></li>");
            }
            sb.append("</ul>");

            for (int i = 0; i < nodeList.getLength(); i++) {
                sb.append("<div id=\"tabs-").append(i).append("\">");
                sb.append("<br/>");

                final Node node = nodeList.item(i);
                try {
                    prettyLo3.appendInhoud(sb, node.getNodeValue());
                } catch (
                        DOMException
                        | BerichtSyntaxException e)
                {
                    sb.append(FOUTMELDING_PARSEN_BERICHT + e);
                }
                sb.append(DIV_END);

            }

            // TAB_CONTAINER
            sb.append(DIV_END);

            // LO3
            sb.append(DIV_END);
        }
    }
}

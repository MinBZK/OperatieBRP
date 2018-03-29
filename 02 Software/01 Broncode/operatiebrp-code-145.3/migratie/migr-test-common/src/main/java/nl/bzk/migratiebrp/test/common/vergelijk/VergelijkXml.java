/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.MigratieXml;
import nl.bzk.migratiebrp.test.common.vergelijk.vergelijking.VergelijkingContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DefaultComparisonFormatter;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.ElementSelectors;

/**
 * Util om 'ongesorteerde' xml te vergelijken.
 *
 * <code>&lt;bla>&lt;x>1&lt;/x>&lt;y>2&lt;/y>&lt;/bla></code> en
 * <code>&lt;bla>&lt;y>2&lt;/y>&lt;x>1&lt;/x>&lt;/bla></code> zullen gelijk zijn.
 */
public final class VergelijkXml {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Pattern ACTIE_PATTERN = Pattern.compile("<actie(Inhoud|Geldigheid|Verval)\\sid=\"(.*?)\">.*?</actie\\1>", Pattern.DOTALL);
    private static final String PERSOON_ID = "persoonId";
    private static final String RELATIE_ID = "relatieId";

    private VergelijkXml() {
        // Niet instantieerbaar
    }

    /**
     * Vergelijk de XML van persoonslijsten. Als de "actueel" uit de database wordt gelezen, dan wordt aan de "verwacht" een relatieId toegevoegd.
     * @param verwachtePL de verwachte persoonslijst
     * @param actuelePL de actuele persoonslijst
     * @param isActueelUitDatabase true als de actuele persoonlijst uit de database komt.
     * @param verschillenLog een {@link StringBuilder} waarin de logging in terug gegeven kan worden
     * @return true als de XML overeenkomen.
     */
    public static boolean vergelijkXmlNegeerActieId(final BrpPersoonslijst verwachtePL, final BrpPersoonslijst actuelePL, final boolean isActueelUitDatabase,
                                                    final StringBuilder verschillenLog) {
        final String verwacht = getXml(verwachtePL);
        final String actueel = getXml(actuelePL);

        final String aangepasteVerwacht = isActueelUitDatabase ? voegGegevensToe(verwacht) : verwacht;
        boolean resultaat = true;

        // Vergelijk
        if (!vergelijkXml(new VergelijkingContext(), aangepasteVerwacht, actueel, verschillenLog, isActueelUitDatabase)) {
            resultaat = false;
        }
        LOG.info(verschillenLog.toString());
        return resultaat;
    }

    private static String getXml(final BrpPersoonslijst pl) {
        try (final StringWriter writer = new StringWriter()) {
            MigratieXml.encode(pl, writer);
            return writer.toString();
        } catch (XmlException | IOException e) {
            final String mesg = "Fout tijdens vertalen BRP-PL naar XML";
            LOG.error(mesg, e);
            throw new IllegalStateException(mesg, e);
        }
    }


    /**
     * Vergelijk ongesorteerd XML en houdt rekening met <actie id=""> en <actie ref=""> zoals in de BRP persoonslijst
     * wordt gegenereerd door simple xml.
     * @param verwacht verwachte xml
     * @param actueel actuele xml
     * @return true, als de xml vergelijkbaar is, anders false
     */
    public static boolean vergelijkXmlMetActies(final String verwacht, final String actueel) {
        // Verwachte acties
        final Map<String, String> verwachteActies = new HashMap<>();
        final String verwachtParsed = verwerkActies(verwacht, verwachteActies);
        // Actuele acties
        final Map<String, String> actueleActies = new HashMap<>();
        final String actueelParsed = verwerkActies(actueel, actueleActies);

        final VergelijkingContext vergelijkingContext = new VergelijkingContext();

        boolean resultaat = true;

        // Vergelijk
        final StringBuilder verschillenLog = new StringBuilder();
        if (!vergelijkXml(vergelijkingContext, verwachtParsed, actueelParsed, verschillenLog, false)) {
            resultaat = false;
        }

        // Vergelijk acties
        if (resultaat) {
            for (final Map.Entry<String, String> entry : verwachteActies.entrySet()) {
                final String key = vergelijkingContext.getConstantVariable(entry.getKey());
                if (!actueleActies.containsKey(key) || !vergelijkXml(vergelijkingContext, entry.getValue(), actueleActies.get(key), verschillenLog, false)) {
                    resultaat = false;
                    break;
                }
            }
        }

        LOG.info(verschillenLog.toString());
        return resultaat;
    }

    /**
     * Database PL bevat 3 nodes meer dan een in memory PL. Dit zijn relatieId, persoonId en persoonVersie.
     * @param pl de XML van de persoon.
     * @return de XML aangevuld met de genoemde velden
     */
    private static String voegGegevensToe(final String pl) {
        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        try {
            final Document document = builderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(pl.getBytes(Charset.defaultCharset())));
            final XPathFactory xPathFactory = XPathFactory.newInstance();
            final XPath xpath = xPathFactory.newXPath();
            final XPathExpression relatieNodeExpression = xpath.compile("//relaties/relatie");
            final NodeList relatieNodes = (NodeList) relatieNodeExpression.evaluate(document, XPathConstants.NODESET);
            final String textContentDecimal = "{decimal}";
            for (int relatieIndex = 0; relatieIndex < relatieNodes.getLength(); relatieIndex++) {
                final Node relatieNode = relatieNodes.item(relatieIndex);
                if (!bevatRelatieRelatieIdNode(relatieNode)) {
                    final Element relatieId = document.createElement(RELATIE_ID);
                    relatieId.setTextContent(textContentDecimal);
                    relatieNode.appendChild(relatieId);
                }
            }

            final XPathExpression brpPersoonslijstExpression = xpath.compile("//brpPersoonslijst");
            final Node rootNode = (Node) brpPersoonslijstExpression.evaluate(document, XPathConstants.NODE);

            final XPathExpression persoonIdExpression = xpath.compile("//persoonId");
            if (persoonIdExpression.evaluate(document, XPathConstants.NODE) == null) {
                final Element node = document.createElement(PERSOON_ID);
                node.setTextContent(textContentDecimal);
                rootNode.appendChild(node);
            }

            final XPathExpression persoonVersieExpression = xpath.compile("//persoonVersie");
            if (persoonVersieExpression.evaluate(document, XPathConstants.NODE) == null) {
                final Element node = document.createElement("persoonVersie");
                node.setTextContent(textContentDecimal);
                rootNode.appendChild(node);
            }

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            // send DOM to file
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            tr.transform(new DOMSource(document), new StreamResult(baos));
            return baos.toString(Charset.defaultCharset().name());
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | TransformerException e) {
            LOG.error("Fout tijdens aanpassen verwachte XML", e);
            throw new IllegalStateException(e);
        }
    }

    private static boolean bevatRelatieRelatieIdNode(final Node relatieNode) {
        boolean bevatRelatieIdNode = false;
        final NodeList childNodes = relatieNode.getChildNodes();
        for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
            final Node child = childNodes.item(childIndex);
            if ("relatieId".equals(child.getNodeName())) {
                bevatRelatieIdNode = true;
                break;
            }
        }
        return bevatRelatieIdNode;
    }

    /**
     * Haal <actie id=""> eruit en vervang door <actie ref=""/>, en voeg deze toe (op key van id) aan de map.
     * @param verwacht verwachte string (met acties met id)
     * @param acties actie map
     * @return verwachte verwachte string
     */
    private static String verwerkActies(final String verwacht, final Map<String, String> acties) {
        final StringBuilder result = new StringBuilder();
        final Matcher matcher = ACTIE_PATTERN.matcher(verwacht);

        int index = 0;

        while (matcher.find()) {
            final int groupStartIndex = matcher.start();
            final int groupEndIndex = matcher.end();

            if (groupStartIndex != index) {
                result.append(verwacht.substring(index, groupStartIndex));
            }

            // Handle group
            final String actie = matcher.group();
            final String typeActie = matcher.group(1);
            final String id = matcher.group(2);

            acties.put(id, actie);

            final String actieRef = "<actie" + typeActie + " ref=\"" + id + "\"/>";
            result.append(actieRef);

            index = groupEndIndex;
        }

        if (index != verwacht.length()) {
            result.append(verwacht.substring(index));
        }
        return result.toString();

    }

    /**
     * Vergelijk 'ongesorteerde' xml.
     * @param verwacht verwachte xml
     * @param actueel actuele xml
     * @return true, als de xml vergelijkbaar is, anders false
     */
    public static boolean vergelijkXml(final String verwacht, final String actueel) {
        return vergelijkXml(new VergelijkingContext(), verwacht, actueel, new StringBuilder(), false);
    }

    private static boolean vergelijkXml(final VergelijkingContext vergelijkingContext, final String verwacht, final String actueel,
                                        final StringBuilder verschillenLog, final boolean isActueelUitDatabase) {
        final Diff differences = DiffBuilder.compare(verwacht).withTest(actueel).ignoreComments().ignoreWhitespace().withNodeMatcher(
                new DefaultNodeMatcher(ElementSelectors.byName))
                .withDifferenceEvaluator(
                        DifferenceEvaluators
                                .chain(DifferenceEvaluators.Default, new MyDifferenceListener(vergelijkingContext, verschillenLog, isActueelUitDatabase)))
                .checkForSimilar().build();
        return !differences.hasDifferences();
    }

    /**
     * Difference evaluator die inhoudelijke ongelijkheden (attribuut waarde en text waarde) ook nog controleert via de
     * {@link Vergelijk#vergelijk} methode (om {decimal} en {messageId} te verwerken).
     */
    private static final class MyDifferenceListener implements DifferenceEvaluator {
        private final VergelijkingContext vergelijkingContext;
        private final StringBuilder verschillenLog;
        private final boolean isActueelUitDatabase;

        /**
         * Default constructor.
         * @param vergelijkingContext De context voor het vergelijken.
         * @param verschillenLog een {@link StringBuilder} waarin de logging in terug gegeven kan worden.
         */
        MyDifferenceListener(final VergelijkingContext vergelijkingContext, final StringBuilder verschillenLog, final boolean isAcuteelUitDatabase) {
            this.vergelijkingContext = vergelijkingContext;
            this.verschillenLog = verschillenLog;
            this.isActueelUitDatabase = isAcuteelUitDatabase;
        }

        @Override
        public ComparisonResult evaluate(final Comparison comparison, final ComparisonResult outcome) {
            ComparisonResult result = outcome;
            final Comparison.Detail controlDetails = comparison.getControlDetails();
            final Comparison.Detail testDetails = comparison.getTestDetails();
            final String controlText = String.valueOf(controlDetails.getValue()).trim();
            final String testText = String.valueOf(testDetails.getValue()).trim();

            if (outcome != ComparisonResult.EQUAL) {
                final boolean
                        isTextValueAndAcceptedDifference =
                        ComparisonType.TEXT_VALUE == comparison.getType() && isVerschilToegestaan(comparison, controlText, testText);
                final boolean
                        isAttribuutValueAndAcceptedDifference =
                        ComparisonType.ATTR_VALUE == comparison.getType() && (isActieId(controlDetails) || Vergelijk
                                .vergelijk(vergelijkingContext, controlText, testText));

                // Deze boolean + methode kunnen weg als we onderzoek wel goed terug mappen bij lezen database
                final boolean uitschakelenVanRelaties = isVerschilVoorRelatieEnKanWordenGenegeerd(comparison);

                if (isTextValueAndAcceptedDifference || isAttribuutValueAndAcceptedDifference || uitschakelenVanRelaties) {
                    result = ComparisonResult.EQUAL;
                }

                if (ComparisonResult.DIFFERENT.equals(result)) {
                    logDifference(comparison);
                }
            }
            return result;
        }

        private boolean isVerschilVoorRelatieEnKanWordenGenegeerd(final Comparison comparison) {
            final Comparison.Detail controlDetails = comparison.getControlDetails();
            final boolean gebruikControleDetails = controlDetails != null && controlDetails.getXPath() != null;

            final Comparison.Detail teGebruikenNode = gebruikControleDetails ? controlDetails : comparison.getTestDetails();
            
            if (teGebruikenNode != null && teGebruikenNode.getXPath() != null) {
                final boolean isRelatie = teGebruikenNode.getXPath().contains("relaties");
                return isActueelUitDatabase && isRelatie;
            }
            return false;
        }

        private boolean isVerschilToegestaan(final Comparison comparison, final String controlText, final String testText) {
            final String xPath = comparison.getControlDetails().getXPath();
            final String datumTijdRegistratie = "datumTijdRegistratie";
            final boolean
                    isToegestaanVerschilOpAfgeleidAdministratief =
                    xPath.contains("persoonAfgeleidAdministratiefStapel") && (xPath.contains(datumTijdRegistratie) || xPath.contains("datumTijdVerval"));
            final boolean isToegestaanVerschilOpRelatieStapelTsReg = xPath.contains("relatieStapel") && xPath.contains(datumTijdRegistratie);
            final boolean isToegestaanVerschilOpIkBetrokkenheidTsReg = xPath.contains("ikBetrokkenheid") && xPath.contains(datumTijdRegistratie);
            final boolean isToegestaanVerschilOpRelaties = isToegestaanVerschilOpIkBetrokkenheidTsReg || isToegestaanVerschilOpRelatieStapelTsReg;
            final boolean isToegestaanVerschilOpID = xPath.contains(PERSOON_ID) || xPath.contains(RELATIE_ID);

            return isToegestaanVerschilOpAfgeleidAdministratief || isToegestaanVerschilOpID || isToegestaanVerschilOpRelaties || Vergelijk
                    .vergelijk(vergelijkingContext, controlText, testText);
        }

        private void logDifference(final Comparison comparison) {

            final Comparison.Detail controlDetails = comparison.getControlDetails();
            final Comparison.Detail testDetails = comparison.getTestDetails();
            final ComparisonType type = comparison.getType();

            switch (type) {
                case ATTR_VALUE:
                    verschillenLog.append("Attribuut waarde inhoudelijk verschillend");
                    break;
                case TEXT_VALUE:
                    verschillenLog.append("Element waarde inhoudelijk verschillend");
                    break;
                default:
                    verschillenLog.append("Verschil gevonden");
            }
            verschillenLog.append("\n");
            verschillenLog.append(String.format("Difference : %s%n", new DefaultComparisonFormatter().getDescription(comparison)));
            verschillenLog.append(String.format("Type       : %s%n", type));
            verschillenLog.append(String.format("ControlNode: %s%n", String.valueOf(controlDetails.getXPath())));
            verschillenLog.append(String.format("TestNode   : %s%n", String.valueOf(testDetails.getXPath())));
            verschillenLog.append("\n");
        }

        private boolean isActieId(final Comparison.Detail controlDetails) {
            return controlDetails.getParentXPath().contains("actie") && "id".equals(controlDetails.getTarget().getNodeName());
        }
    }
}

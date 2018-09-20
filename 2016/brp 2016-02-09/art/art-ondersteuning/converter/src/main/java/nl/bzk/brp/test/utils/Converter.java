/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import nl.bzk.brp.test.utils.ui.ConverterForm;
import nl.bzk.brp.test.utils.validation.XsdValidation;

import org.apache.commons.io.IOUtils;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Converter {

    private static final XPath XPATH;
    static {
        XPATH = XPathFactory.newInstance().newXPath();
        SimpleNamespaceContext namespaceContext = new SimpleNamespaceContext();
        namespaceContext.bindNamespaceUri("soap", "http://schemas.xmlsoap.org/soap/envelope/");
        namespaceContext.bindNamespaceUri("brp", "http://www.bzk.nl/brp/brp0100");
        namespaceContext.bindNamespaceUri("stuf", "http://www.kinggemeenten.nl/StUF/StUF0302");
        namespaceContext.bindNamespaceUri("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        namespaceContext.bindNamespaceUri("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        XPATH.setNamespaceContext(namespaceContext);
    }

    public static void main(final String[] args) {
        new ConverterForm().setVisible(true);
    }

    private ConverterConfiguratie configuratie;
    private ConverterResultaat resultaat;

    public Converter(final ConverterConfiguratie configuratie) {
        this.configuratie = configuratie;
        this.resultaat = new ConverterResultaat();
    }

    public ConverterResultaat getResultaat() {
        return resultaat;
    }

    public void startConversie() throws Exception {
        this.leegOutputDirectory();
        for (File inputFile : this.configuratie.getInputFiles()) {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document inputDocument = documentBuilder.parse(this.pasFindReplaceToe(inputFile));
            verwijderLegeTextNodes(inputDocument.getChildNodes());

            Document outputDocument;
            String valideOmschrijving;
            if (inputDocument.getDocumentElement().getChildNodes().item(0).getNodeName().equals("soap:Fault")) {
                outputDocument = inputDocument;
                valideOmschrijving = "NVT - Soap Fault";
            } else {
                outputDocument = documentBuilder.parse(new FileInputStream(this.configuratie.getTemplateFile()));
                verwijderLegeTextNodes(outputDocument.getChildNodes());

                // Voor de xpath context root: strip de soap envelop node,
                // het eerste child is de soap node, het eerste child daarvan de brp root.
                Node inputXpathContext = inputDocument.getChildNodes().item(0).getChildNodes().item(0);
                // Neem het output document als template xpath context, zodat de compares van nieuwe nodes kunnen matchen.
                Node templateXpathContext = outputDocument.getChildNodes().item(0).getChildNodes().item(0);
                this.pasConversieToe(templateXpathContext, inputXpathContext, outputDocument.getDocumentElement());

                // Valideer of het output document aan de XSD voldoet.
                valideOmschrijving = XsdValidation.valideer(outputDocument);
            }

            // Schrijf de output weg.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(outputDocument);

            File outputFile = new File(this.configuratie.getPadNaarOutputFolder() + "/" + inputFile.getName());
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);


            this.resultaat.bestandGeconverteerd(inputFile.getName(), valideOmschrijving);

            System.out.println("");
            System.out.println("Input file '" + inputFile.getAbsolutePath() + "':");
            for (ConverterCase converterCase : ConverterCase.values()) {
                System.out.println("Case '" + converterCase.getOmschrijving() + "': " + this.resultaat.getAantalResultaten(converterCase));
            }
        }
    }

    private void leegOutputDirectory() {
        List<File> huidigeOutputFiles = Arrays.asList(configuratie.getPadNaarOutputFolder().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        }));
        for (File huidigeOutputFile : huidigeOutputFiles) {
            huidigeOutputFile.delete();
        }
    }

    private InputStream pasFindReplaceToe(final File inputFile) throws Exception {
        String inputContent = IOUtils.toString(new FileInputStream(inputFile));
        for (Entry<String, String> findReplaceEntry : this.configuratie.getFindReplaceValues()) {
            // Vervang de oude tekst door de nieuwe tekst.
            inputContent = inputContent.replaceAll(findReplaceEntry.getKey(), findReplaceEntry.getValue());
        }
        return IOUtils.toInputStream(inputContent);
    }

    private ToegepasteActie pasConversieToe(final Node templateXpathContext, final Node inputXpathContext, final Element element) throws Exception {
        boolean isLeaf = true;
        boolean ouderTagVerwijderen = true;
        for (int index = 0; index < element.getChildNodes().getLength(); index++) {
            Node childNode = element.getChildNodes().item(index);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                isLeaf = false;
                ToegepasteActie toegepasteActie = this.pasConversieToe(templateXpathContext, inputXpathContext, childElement);
                // We moeten de ouder tag verwijderen als alle kinderen verwijderd zijn of geen bestaansrecht match hebben.
                ouderTagVerwijderen = ouderTagVerwijderen
                        && (toegepasteActie == ToegepasteActie.VERWIJDERD || toegepasteActie == ToegepasteActie.MATCH_GEEN_BESTAANSRECHT);
                // Houd rekening in de index met de verwijdering van de kind tag.
                if (toegepasteActie == ToegepasteActie.VERWIJDERD) {
                    index--;
                }
            }
        }
        String xpathForElement = this.genereerXPathVoorElement(element, false);
        String xpathForElementMetIndex = this.genereerXPathVoorElement(element, true);
        MatchContext context = new MatchContext();
        if (isLeaf) {
            ConverterCase converterCase = null;
            String waarde = null;

            // Case STRUCTUUR: Indien beschikbaar, pas de structuur mapping toe.
            if (doeStructuurMatch(element, xpathForElementMetIndex, inputXpathContext, context)) {
                // Niks meer te doen, match is al geslaagd.

            // Case MAPPING: Indien beschikbaar, pas de nieuw naar oud mapping toe.
            } else if (doeMappingMatch(element, xpathForElement, inputXpathContext, context)) {
                // Niks meer te doen, match is al geslaagd.

            // Case NEW: Als deze tag expliciet als nieuwe tag is geconfigureerd, laten we deze zoals ie is.
            } else if (matchtNieuweTag(element, templateXpathContext)) {
                converterCase = ConverterCase.NEW;
                if (configuratie.isDebug()) {
                    System.out.println("Toepassen nieuwe tag voor: '" + xpathForElement + "', behouden template waarde: '" + element.getTextContent() + "'.");
                }

            // Case SAME_XPATH: Indien mogelijk, pas dezelfde xpath toe op het oude document.
            } else if (heeftZelfdeXpath(xpathForElementMetIndex, inputXpathContext)) {
                XPathExpression expressie = XPATH.compile(xpathForElementMetIndex);
                context.setMatchingElement((Element) expressie.evaluate(inputXpathContext, XPathConstants.NODE));
                String inhoud = expressie.evaluate(inputXpathContext);
                element.setTextContent(inhoud);
                waarde = inhoud;
                converterCase = ConverterCase.SAME_XPATH;
                if (this.configuratie.isDebug()) {
                    System.out.println("Toepassen gelijke mapping met waarde '" + inhoud + "' voor xpath: '" + xpathForElementMetIndex + "'.");
                }

            // Case DEFAULT_BESTAANSRECHT: Indien beschikbaar, ken een default waarde toe, die het bestaansrecht aan
            // zijn parent tags geeft.
            } else if (matchMetBestaansrecht(element, templateXpathContext) != null) {
                String defaultWaarde = matchMetBestaansrecht(element, templateXpathContext);
                element.setTextContent(defaultWaarde);
                waarde = defaultWaarde;
                converterCase = ConverterCase.DEFAULT_BESTAANSRECHT;
                if (configuratie.isDebug()) {
                    System.out.println("Toepassen default waarde met bestaansrecht '" + defaultWaarde + "' voor xpath: '" + xpathForElement + "'.");
                }

            // Case DEFAULT_GEEN_BESTAANSRECHT: Indien beschikbaar, ken een default waarde toe, die geen bestaansrecht aan
            // zijn parent tags geeft. Oftewel, als er alleen maar een default binnen die parents gevonden is, moeten die
            // alsnog verwijderd worden.
            } else if (matchZonderBestaansrecht(element, templateXpathContext) != null) {
                String defaultWaarde = matchZonderBestaansrecht(element, templateXpathContext);
                element.setTextContent(defaultWaarde);
                waarde = defaultWaarde;
                converterCase = ConverterCase.DEFAULT_GEEN_BESTAANSRECHT;
                if (configuratie.isDebug()) {
                    System.out.println("Toepassen default waarde zonder bestaansrecht '" + defaultWaarde + "' voor xpath: '" + xpathForElement + "'.");
                }

            // Case NOT_FOUND: als alle cases tot nu toe geen resultaat hebben opgeleverd, dan verwijderen we het element.
            } else {
                element.getParentNode().removeChild(element);
                converterCase = ConverterCase.NOT_FOUND;
                if (this.configuratie.isDebug()) {
                    System.out.println("Toepassen niet gevonden voor: '" + xpathForElementMetIndex + "', element verwijderd.");
                }
            }
            // Kan null zijn, omdat in geval van bepaalde matches in een aparte methode gewerkt wordt.
            // Daar wordt dan ook de caseOccured gedaan, dus hier alleen als de var een waarde heef doorgaan.
            if (converterCase != null) {
                this.resultaat.caseOccurred(converterCase, xpathForElement, waarde);
            }

            // Als er een matching element gevonden is in de input, zorg dan ook voor een goede afhandeling van de attributen.
            if (context.getMatchingElement() != null) {
                this.matchAttributen(element, context.getMatchingElement());
            }

            if (converterCase == ConverterCase.NOT_FOUND) {
                // Deze (leaf) node is verwijderd als hij niet gevonden is.
                return ToegepasteActie.VERWIJDERD;
            } else if (converterCase == ConverterCase.DEFAULT_GEEN_BESTAANSRECHT) {
                // Een match zonder bestaansrecht betekent
                // dat de parent tag toch verwijderd moet worden als er geen andere matches gevonden zijn voor zijn kinderen.
                return ToegepasteActie.MATCH_GEEN_BESTAANSRECHT;
            } else {
                // Er is een match gevonden, en die geeft de parent ook recht op bestaan. :)
                return ToegepasteActie.MATCH_BESTAANSRECHT;
            }
        } else {
            // Als er een zelfde tag is in de input, match dan ook de attributen van deze niet-leaf.
            if (xpathForElementMetIndex.length() > 0 && heeftZelfdeXpath(xpathForElementMetIndex, inputXpathContext)) {
                XPathExpression expressie = XPATH.compile(xpathForElementMetIndex);
                this.matchAttributen(element, (Element) expressie.evaluate(inputXpathContext, XPathConstants.NODE));
            }

            // Deze (parent) node wordt verwijderd als alle kinderen verwijderd zijn.
            if (ouderTagVerwijderen) {
                element.getParentNode().removeChild(element);
            }
            if (ouderTagVerwijderen) {
                // Parent tag is verwijderd.
                return ToegepasteActie.VERWIJDERD;
            } else {
                // Deze parent tag mag blijven (niet zelf een match, maar wel een van zijn kids).
                return ToegepasteActie.MATCH_BESTAANSRECHT;
            }
        }
    }

    private void matchAttributen(final Element element, final Element matchingElement) {
        List<Attr> teVerwijderenAttributen = new ArrayList<>();
        NamedNodeMap nodeMap = element.getAttributes();
        // Voor elk attribuut uit de template:
        for (int i = 0; i < nodeMap.getLength(); i++) {
            Attr attribuut = (Attr) nodeMap.item(i);
            Attr matchingAttribuut = null;
            NamedNodeMap matchingNodeMap = matchingElement.getAttributes();
            for (int j = 0; j < matchingNodeMap.getLength(); j++) {
                Attr possibleMatchingAttribuut = (Attr) matchingNodeMap.item(j);
                if (attribuut.getName().equals(possibleMatchingAttribuut.getName())) {
                    matchingAttribuut = possibleMatchingAttribuut;
                }
            }
            // Als dat attribuut ook bestaat in de input: neem de waarde over.
            if (matchingAttribuut != null) {
                attribuut.setNodeValue(matchingAttribuut.getValue());
            } else {
                // Als dat attribuut niet bestaat in de input: verwijder het attribuut.
                teVerwijderenAttributen.add(attribuut);
            }
        }
        // Verwijder de te verwijderen attributen.
        for (Attr teVerwijderenAttribuut : teVerwijderenAttributen) {
            element.removeAttributeNode(teVerwijderenAttribuut);
        }
    }

    private boolean matchtNieuweTag(final Element element, final Node templateXpathContext) throws Exception {
        return matchElementOpXPath(element, this.configuratie.getNieuweTags(), templateXpathContext) != null;
    }

    private String matchMetBestaansrecht(final Element element, final Node templateXpathContext) throws Exception {
        return matchDefault(element, this.configuratie.getDefaultBestaansrechtValues(), templateXpathContext);
    }

    private String matchZonderBestaansrecht(final Element element, final Node templateXpathContext) throws Exception {
        return matchDefault(element, this.configuratie.getDefaultGeenBestaansrechtValues(), templateXpathContext);
    }

    private String matchDefault(final Element element, final Map<String, String> xpathsStringDefaults, final Node templateXpathContext) throws Exception {
        String gevondenDefault = null;
        String gevondenDoorXpath = matchElementOpXPath(element, xpathsStringDefaults.keySet(), templateXpathContext);
        if (gevondenDoorXpath != null) {
            gevondenDefault = xpathsStringDefaults.get(gevondenDoorXpath);
        }
        return gevondenDefault;
    }

    private String matchElementOpXPath(final Element element, final Collection<String> xpathsStrings, final Node templateXpathContext) throws Exception {
        String gevondenDoorXpath = null;
        for (String xpathString : xpathsStrings) {
            NodeList gevondenNodes = (NodeList) XPATH.compile(xpathString).evaluate(templateXpathContext, XPathConstants.NODESET);
            for (int index = 0; index < gevondenNodes.getLength(); index++) {
                Node eenGevondenNode = gevondenNodes.item(index);
                if (eenGevondenNode.compareDocumentPosition(element) == 0) {
                    gevondenDoorXpath = xpathString;
                }
            }
        }
        return gevondenDoorXpath;
    }

    private boolean heeftZelfdeXpath(final String xpath, final Node context) throws Exception {
        // Test op aantal voorkomens, via count( ... ) om de xpath expressie heen.
        // Dit, aangezien de directe evaluate geen onderscheid maakt tussen niet gevonden en lege inhoud.
        int aantalVoorkomens = Integer.parseInt(XPATH.compile("count(" + xpath + ")").evaluate(context));
        return aantalVoorkomens > 0;
    }

    private String genereerXPathVoorElement(final Element element, final boolean metIndex) {
        List<Element> path = new ArrayList<Element>();
        Element current = element;
        path.add(0, current);
        while (current.getParentNode() != null && current.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
            current = (Element)current.getParentNode();
            // Skip het buitenste soap element.
            if (!current.getNodeName().startsWith("soap")) {
                path.add(0, current);
            }
        }
        // Strip het root element.
        path.remove(0);
        String expressie = "";
        if (path.size() > 0) {
            for (Element pathElement : path) {
                expressie += "/" + genereerXPathVoorTag(pathElement, metIndex);
            }
            // Knip de eerste '/' eraf, aangezien we niet met absolute xpaths werken.
            expressie = expressie.substring(1);
        }
        return expressie;
    }

    private String genereerXPathVoorTag(final Element pathElement, final boolean withIndex) {
        String tagNaam = pathElement.getNodeName();
        if (!withIndex) {
            return tagNaam;
        }
        int xpathIndex = 0;
        boolean foundMyself = false;
        NodeList siblings = pathElement.getParentNode().getChildNodes();
        for (int index = 0; index < siblings.getLength() && !foundMyself; index++) {
            Node sibling = siblings.item(index);
            if (sibling.getNodeType() == Node.ELEMENT_NODE && sibling.getNodeName().equals(pathElement.getNodeName())) {
                xpathIndex++;
            }
            if (sibling == pathElement) {
                foundMyself = true;
            }
        }
        return tagNaam + "[" + xpathIndex + "]";
    }

    private void verwijderLegeTextNodes(final NodeList nodeList) {
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.TEXT_NODE && node.getTextContent().trim().equals("")) {
                node.getParentNode().removeChild(node);
                index--;
            } else {
                // Recursieve call voor de huidige node.
                verwijderLegeTextNodes(node.getChildNodes());
            }
        }
    }

    private boolean doeMappingMatch(final Element element, final String xpathVoorElement,
            final Node inputXpathContext, final MatchContext context) throws Exception {
        boolean mappingMatch = this.configuratie.getNieuwNaarOudMapping().containsKey(xpathVoorElement);
        if (mappingMatch) {
            String xpathVoorOudExpressie = this.configuratie.getNieuwNaarOudMapping().get(xpathVoorElement);
            // Test op aantal voorkomens, via count( ... ) om de xpath expressie heen.
            // Dit, aangezien de directe evaluate geen onderscheid maakt tussen niet gevonden en lege inhoud.
            int aantalVoorkomens = Integer.parseInt(XPATH.compile("count(" + xpathVoorOudExpressie + ")").evaluate(inputXpathContext));
            boolean komtVoorInOudeXml = aantalVoorkomens > 0;
            if (komtVoorInOudeXml) {
                XPathExpression xpathVoorOud = XPATH.compile(xpathVoorOudExpressie);
                context.setMatchingElement((Element) xpathVoorOud.evaluate(inputXpathContext, XPathConstants.NODE));
                String inhoud = xpathVoorOud.evaluate(inputXpathContext);
                element.setTextContent(inhoud);
                this.resultaat.caseOccurred(ConverterCase.MAPPING, xpathVoorElement, inhoud);
                if (configuratie.isDebug()) {
                    System.out.println("Toepassen nieuw naar oud mapping naar waarde '" + inhoud + "' voor xpath: '" + xpathVoorElement + "'.");
                }
            } else {
                // Mapping aanwezig, maar geen match gevonden in input.
                mappingMatch = false;
                this.resultaat.caseOccurred(ConverterCase.MAPPING_FAILED, xpathVoorElement);
                if (configuratie.isDebug()) {
                    System.out.println("Toepassen nieuw naar oud mapping, maar geen waarde voor xpath: '" + xpathVoorElement + "'.");
                }
            }
        }
        return mappingMatch;
    }

    private boolean doeStructuurMatch(final Element element, final String xpathVoorElementMetIndex,
            final Node inputXpathContext, final MatchContext context) throws Exception {
        List<Entry<String, String>> structuurMatches = new ArrayList<Entry<String,String>>();
        for (Entry<String, String> structuurEntry : this.configuratie.getStructuurMapping().entrySet()) {
            if (xpathVoorElementMetIndex.startsWith(structuurEntry.getKey())) {
                structuurMatches.add(structuurEntry);
            }
        }
        // Sorteer op lengte, zodat hij probeert van de minst naar meest specifieke.
        Collections.sort(structuurMatches, new Comparator<Entry<String, String>>() {
            @Override
            public int compare(final Entry<String, String> o1, final Entry<String, String> o2) {
                return o1.getKey().length() - o2.getKey().length();
            }
        });
        boolean matchApplied = false;
        for (int i = 0; i < structuurMatches.size() && !matchApplied; i++) {
            Entry<String, String> structuurMatch = structuurMatches.get(i);
            String xpathVoorOudExpressie = xpathVoorElementMetIndex.replace(structuurMatch.getKey(), structuurMatch.getValue());
            int aantalVoorkomens = Integer.parseInt(XPATH.compile("count(" + xpathVoorOudExpressie + ")").evaluate(inputXpathContext));
            boolean komtVoorInOudeXml = aantalVoorkomens > 0;
            if (komtVoorInOudeXml) {
                XPathExpression xpathVoorOud = XPATH.compile(xpathVoorOudExpressie);
                context.setMatchingElement((Element) xpathVoorOud.evaluate(inputXpathContext, XPathConstants.NODE));
                String inhoud = xpathVoorOud.evaluate(inputXpathContext);
                element.setTextContent(inhoud);
                matchApplied = true;
                this.resultaat.caseOccurred(ConverterCase.STRUCTUUR, xpathVoorElementMetIndex, inhoud);
                if (configuratie.isDebug()) {
                    System.out.println("Toepassen structuur mapping naar waarde '" + inhoud + "' voor xpath: '" + xpathVoorElementMetIndex + "'.");
                }
            }
        }
        if (!matchApplied) {
            // Structuur mapping aanwezig, maar geen match gevonden.
            this.resultaat.caseOccurred(ConverterCase.STRUCTUUR_NO_MATCH, xpathVoorElementMetIndex);
            if (configuratie.isDebug()) {
                System.out.println("Structuur match gevonden, maar geen waarde voor xpath: '" + xpathVoorElementMetIndex + "'.");
            }
        }
        return matchApplied;
    }

}

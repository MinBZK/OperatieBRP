/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.xml;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.StringReader;
import java.util.List;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.algemeen.Melding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * XPathUtils.
 */
@NotThreadSafe
public final class XPathHelper {

    private final XPath xPath;

    public XPathHelper() {
        final XPathFactory xPathFactory = XPathFactory.newInstance();
        xPath = xPathFactory.newXPath();
        final SimpleNamespaceContext nsContext = new SimpleNamespaceContext();
        nsContext.bindNamespaceUri("brp", "http://www.bzk.nl/brp/brp0200");
        xPath.setNamespaceContext(nsContext);
    }

    /**
     * Assert dat de XML een element bevat.
     * @param xml de XML
     * @param xpathExpressie een xPath expressie
     * @return aanwezig
     */
    public boolean isNodeAanwezig(final String xml, final String xpathExpressie) {
        final NodeList nodeList = evalAlsNodeList(xml, xpathExpressie);
        return nodeList.getLength() > 0;
    }

    /**
     * Assert dat de XML geen element bevat.
     * @param xml de XML
     * @param xpathExpressie een xPath expressie
     */
    public void assertNodeBestaat(final String xml, final String xpathExpressie) {
        final NodeList nodeList = evalAlsNodeList(xml, xpathExpressie);
        Assert.isTrue(nodeList.getLength() > 0, String.format("Node niet gevonden voor xPath [%s] in XML [%s]", xpathExpressie, xml));
    }

    /**
     * Assert dat de XML geen element bevat.
     * @param xml de XML
     * @param xpathExpressie een xPath expressie
     */
    public void assertNodeBestaatNiet(final String xml, final String xpathExpressie) {
        final NodeList nodeList = evalAlsNodeList(xml, xpathExpressie);
        Assert.isTrue(nodeList.getLength() == 0, String.format("Node wel gevonden voor xPath [%s] in XML [%s]", xpathExpressie, xml));
    }

    /**
     * Assert dat de XML geen kind elementen bevat.
     * @param xml de XML
     * @param xpathExpressie een xPath expressie
     */
    public void assertLeafNodeBestaat(final String xml, final String xpathExpressie) {
        final Node node = evalAlsNode(xml, xpathExpressie);
        Assert.notNull(node, String.format("Geen node gevonden voor xPath [%s] in XML [%s]", xpathExpressie, xml));
        Assert.isTrue(node.getChildNodes().getLength() <= 1 && (node.getChildNodes().getLength() == 0
                || node.getFirstChild().getNodeType() == Node.TEXT_NODE), "Node is geen leaf");
    }

    /**
     * Assert dat XML voor de gegeven xPath expressie de gegeven waarde bevat.
     * @param xml de XML
     * @param xpathExpressie de xPath expressie
     * @param waarde de waarde
     */
    public void assertWaardeGelijk(final String xml, final String xpathExpressie, final String waarde) {
        final String gevondenWaarde = evalAlsString(xml, xpathExpressie);
        Assert.isTrue(StringUtils.equals(waarde, gevondenWaarde),
                String.format("Waarde [%s] in XML niet als verwacht [%s] voor xPath [%s]", waarde, gevondenWaarde, xpathExpressie));
    }

    /**
     * Assert dat XML voor de gegeven xPath expressie de gegeven waarde bevat.
     * @param xml de XML
     * @param xpathExpressie de xPath expressie
     * @param waarde de waarde
     */
    public void assertPlatteWaardeGelijk(final String xml, final String xpathExpressie, final String waarde) {
        assertWaardeGelijk(xml, xpathExpressie, waarde);
    }

    /**
     * Assert dat XML voor de gegeven xPath expressie de gegeven waarde bevat.
     * @param xml de XML
     * @param regelCode de regel code
     * @param regelTekst de regel tekst
     */
    public void assertMeldingBestaat(final String xml, final String regelCode, final Object regelTekst) {
        if (!isNodeAanwezig(xml, String.format("//brp:melding[brp:regelCode[text() = '%s'] and brp:melding[text() = '%s']]",
                regelCode, regelTekst))) {
            throw new AssertionError(String.format("Melding niet gevonden met regelCode=%s en regelTekst=%s in xml=%s", regelCode, regelTekst, xml));
        }
    }

    /**
     * Assert regel bestaat.
     * @param xml de XML
     * @param regelCode de regel code
     */
    public void assertRegelCodeBestaat(final String xml, final String regelCode) {
        if (!isNodeAanwezig(xml, String.format("//brp:melding[brp:regelCode[text() = '%s'] and //brp:melding/brp:melding[string-length(text())>0]]",
                regelCode))) {
            throw new AssertionError(String.format("Melding niet gevonden met regelCode=%s in xml=%s", regelCode, xml));
        }
    }

    /**
     * Assert dat het aantal meldingen gelijk is aan het aantal verwachte meldingen.
     * @param xml de XML
     * @param count het aantal meldingen
     */
    public void assertAantalMeldingenGelijk(final String xml, final int count) {
        final Number number = evalAlsNumber(xml, "count(//brp:melding[@brp:objecttype = 'Melding'])");
        Assert.isTrue(number.intValue() == count, String.format("Aantal meldingen incorrect %d ipv %d", number.intValue(), count));
    }

    /**
     * Return het aantal elementen.
     * @param xml de XML
     * @param element naam van het element (zonder brp:)
     * @return aantal keer dat het element voorkomt
     */
    public int getAantalElementen(final String xml, final String element) {
        return evalAlsNumber(xml, String.format("count(//brp:%s)", element));
    }

    /**
     * Assert dat de verwantwoording correct is.
     * @param xml de XML
     */
    public void assertVerantwoordingCorrect(final String xml) {
        final NodeList actieNodeList = evalAlsNodeList(xml, "//*[@brp:objecttype='Actie']/@brp:objectSleutel");
        final Set<String> actieIdSet = Sets.newHashSet();
        for (int i = 0; i < actieNodeList.getLength(); i++) {
            actieIdSet.add(actieNodeList.item(i).getTextContent());
        }

        final NodeList verantwoordingList = evalAlsNodeList(xml, "//*[name() = 'brp:actieInhoud' or name() = 'brp:actieVerval' or name() = "
                + "'brp:actieAanpassingGeldigheid']");
        final Set<String> verantwoordingIdSet = Sets.newHashSet();
        for (int i = 0; i < verantwoordingList.getLength(); i++) {
            verantwoordingIdSet.add(verantwoordingList.item(i).getTextContent());
        }
        Assert.isTrue(actieIdSet.equals(verantwoordingIdSet),
                String.format("Verantwoording niet correct: actieIds=%s verantwoordingIds=%s", actieIdSet, verantwoordingIdSet));
    }

    /**
     * Assert dat referentieID uit 1 of meldingen naar juiste communicatieID's verwijzen.
     * @param xml het xml bericht
     * @param regelNummer regelnummer van {@link Melding}
     * @param verwachteAantalMeldingen verwacht aantal meldingen voor dit regelnummer
     * @param bsnLijst lijst met bsn's van personen waar referentieID naar verwijzen
     */
    public void assertMeldingReferentiesNaarPersonenCorrect(final String xml, final String regelNummer, final int verwachteAantalMeldingen,
                                                                   final List<String> bsnLijst) {
        final NodeList referentieIdNodeList = evalAlsNodeList(xml,
                String.format("//brp:melding[./brp:regelCode[text() = '%s']]/@brp:referentieID", regelNummer));

        //assert aantal meldingen correct
        Assert.isTrue(verwachteAantalMeldingen == referentieIdNodeList.getLength(),
                String.format("Aantal meldingen met regelnummer %s is anders dan verwacht, expected : %d actual : %d", regelNummer, verwachteAantalMeldingen,
                        referentieIdNodeList.getLength()));

        final Set<String> referentieIdSet = mapWaardenNodeListOpSet(referentieIdNodeList);
        final StringBuilder bsnExpressies = new StringBuilder();
        bsnLijst.forEach(bsn -> {
            if (bsnExpressies.length() != 0) {
                bsnExpressies.append("or ");
            }
            bsnExpressies.append(String.format("text() = '%s' ", bsn));
        });
        final NodeList communicatieidNodelist = evalAlsNodeList(xml,
                String.format("//brp:persoon[./brp:identificatienummers/brp:burgerservicenummer[%s]]/@brp:communicatieID", bsnExpressies.toString()));
        final Set<String> communicatieIdSet = mapWaardenNodeListOpSet(communicatieidNodelist);

        //assert referenties correct
        Assert.isTrue(Sets.symmetricDifference(referentieIdSet, communicatieIdSet).isEmpty(),
                String.format("ReferentieID's van meldingen met regelnummer %s matchen niet correct op communicatieID's van personen", regelNummer));
    }

    /**
     * Assert dat het attribuut (met parent het gegeven voorkomen) de verwachte waardes heeft.
     * @param xml de XML
     * @param voorkomen naam van het voorkomen element (zonder brp:)
     * @param attribuut naam van het attribuut element (zonder brp:)
     * @param verwachteWaardes lijst met verwachte waarden
     */
    public void assertBerichtHeeftWaardes(final String xml, final String voorkomen, final String attribuut, final List<String> verwachteWaardes) {
        final String xpathExpressie = String.format("//brp:%s[ancestor::*[name() = 'brp:%s']]", attribuut, voorkomen);
        final NodeList nodeList = evalAlsNodeList(xml, xpathExpressie);
        final List<String> gevondenWaarden = Lists.newArrayListWithCapacity(nodeList.getLength());
        for (int i = 0; i < nodeList.getLength(); i++) {
            gevondenWaarden.add(nodeList.item(i).getTextContent());
        }
        Assert.isTrue(gevondenWaarden.equals(verwachteWaardes),
                String.format("Gevonden waarden %s komen niet overeen met expected %s",
                        gevondenWaarden, verwachteWaardes));
    }

    /**
     * Assert dat het attribuut wel/niet aanwezig is binnen het voorkomen met de gegeven index.
     * @param xml de XML
     * @param voorkomen naam van het voorkomen element (zonder brp:)
     * @param voorkomenIndex index van het voorkomen in het bericht
     * @param attribuut naam van het attribuut element
     * @param aanwezig indicatie of het attribuut aanwezig is
     */
    public void assertBerichtHeeftAttribuutAanwezigheid(final String xml,
                                                               final String voorkomen, final Integer voorkomenIndex, final String attribuut,
                                                               final boolean aanwezig) {
        final boolean nodeGevonden = getAttribuutNodeVanVoorkomen(xml, voorkomen, voorkomenIndex, attribuut) != null;
        Assert.isTrue(nodeGevonden && aanwezig || !nodeGevonden && !aanwezig, String.format("Attribuut[%s in groep: %s] aanwezigheid incorrect",
                attribuut, voorkomen));
    }

    /**
     * Assert dat het attribuut de verwachte waarde heeft in voorkomen met de gegeven index.
     * @param xml de XML
     * @param voorkomen naam van het voorkomen (zonder brp:)
     * @param voorkomenIndex index van het voorkomen in het bericht
     * @param attribuut naam van het attribuut element
     * @param verwachteWaarde de verwachte waarde
     */
    public void assertBerichtHeeftAttribuutWaarde(final String xml, final String voorkomen, final Integer voorkomenIndex,
                                                         final String attribuut, final String verwachteWaarde) {
        final Node attribuutNode = getAttribuutNodeVanVoorkomen(xml, voorkomen, voorkomenIndex, attribuut);
        Assert.isTrue(attribuutNode != null && verwachteWaarde.equals(attribuutNode.getTextContent()),
                String.format("Attribuut[%s] met waarde[%s] in voorkomen[%s] met index[%d] kan niet gevonden worden", attribuut, verwachteWaarde,
                        voorkomen, voorkomenIndex));
    }

    /**
     * Assert dat het voorkomen met index de gegeven verwerkingssoort heeft.
     * @param xml de XML
     * @param voorkomen naam van het voorkomen element (zonder brp:)
     * @param voorkomenIndex index van het voorkomen element in het bericht
     * @param verwerkingssoort de verwerkingssoort
     */
    public void assertBerichtVerwerkingssoortCorrect(final String xml, final String voorkomen, final int voorkomenIndex,
                                                            final Verwerkingssoort verwerkingssoort) {
        final Node node = getNode(xml, voorkomen, voorkomenIndex);
        Assert.notNull(node, "Node kan niet gevonden worden");

        final Node attrNode = node.getAttributes().getNamedItem("brp:verwerkingssoort");
        Assert.notNull(attrNode, "Verwerkingssoort attribuut kan niet gevonden worden");
        Assert
                .isTrue(verwerkingssoort.getNaam().equals(attrNode.getTextContent()), String.format("Verwerkingssoort incorrect, verwacht '%s' gevonden '%s'",
                        verwerkingssoort.getNaam(), attrNode.getTextContent()));
    }

    private Node getAttribuutNodeVanVoorkomen(final String xml, final String voorkomen, final Integer voorkomenIndex, final String attribuut) {
        final Node node = getNode(xml, voorkomen, voorkomenIndex);
        if (node != null) {
            final NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                final Node item = childNodes.item(i);
                if (attribuut.equals(item.getLocalName())) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * Zoek recursief naar een attribuutnode.
     * @param xml xmlBericht
     * @param voorkomen voorkomen waarbinnen gezocht wordt
     * @param attribuut attribuut
     * @return true indien attribuut aanwezig binnen node
     */
    public boolean bestaatAttribuutNodeBinnenContainer(final String xml, final String voorkomen, final String attribuut) {
        Node attrNode;
        final Node node = getNode(xml, voorkomen, 1);
        if (node != null) {
            attrNode = zoekRecursiefBinnenNode(node, attribuut);
            if (attrNode != null) {
                return true;
            }
        }
        return false;
    }

    private Set<String> mapWaardenNodeListOpSet(final NodeList nodelist) {
        final Set<String> waardenSet = Sets.newHashSet();
        for (int i = 0; i < nodelist.getLength(); i++) {
            waardenSet.add(nodelist.item(i).getTextContent());
        }
        return waardenSet;
    }

    private Node zoekRecursiefBinnenNode(Node node, String attribuut) {
        Node attrNode;
        if (attribuut.equals(node.getLocalName())) {
            return node;
        }

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i);
            attrNode = zoekRecursiefBinnenNode(childNode, attribuut);
            if (attrNode != null) {
                return attrNode;
            }
        }
        return null;
    }

    private Node getNode(final String xml, final String voorkomen, final Integer voorkomenIndex) {
        final NodeList nodeList = evalAlsNodeList(xml, String.format("//brp:%s", voorkomen));
        return nodeList.item(voorkomenIndex - 1);
    }

    private Boolean evalAlsBoolean(final String xml, final String xpathExpressie) {
        final XPathExpression xPathExpression = geefXPathExpressie(xpathExpressie);
        try {
            return (Boolean) xPathExpression.evaluate(new InputSource(new StringReader(xml)), XPathConstants.BOOLEAN);

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Evaluatie voor Boolean mislukt", e);
        }
    }

    private String evalAlsString(final String xml, final String xpathExpressie) {
        final XPathExpression xPathExpression = geefXPathExpressie(xpathExpressie);
        try {
            return (String) xPathExpression.evaluate(new InputSource(new StringReader(xml)), XPathConstants.STRING);

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Evaluatie voor String mislukt", e);
        }
    }

    private int evalAlsNumber(final String xml, final String xpathExpressie) {
        final XPathExpression xPathExpression = geefXPathExpressie(xpathExpressie);
        try {
            return ((Number) xPathExpression.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NUMBER)).intValue();

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Evaluatie voor Number mislukt", e);
        }
    }

    private Node evalAlsNode(final String xml, final String xpathExpressie) {
        final XPathExpression xPathExpression = geefXPathExpressie(xpathExpressie);
        try {
            return (Node) xPathExpression.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Evaluatie voor Node mislukt", e);
        }
    }

    private NodeList evalAlsNodeList(final String xml, final String xpathExpressie) {
        final XPathExpression xPathExpression = geefXPathExpressie(xpathExpressie);
        try {
            return (NodeList) xPathExpression.evaluate(new InputSource(new StringReader(xml)), XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Evaluatie voor NodeList mislukt", e);
        }
    }

    private XPathExpression geefXPathExpressie(final String xpathExpressie) {
        try {
            return xPath.compile(xpathExpressie);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("xPath Expressie niet geldig: " + xpathExpressie, e);
        }
    }

    public void assertAantal(final String response, final String element, final int aantal) {
        final int aantalGevonden = getAantalElementen(response, element);
        Assert.isTrue(aantalGevonden == aantal, "Aantal komt niet overeen");
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import java.io.StringWriter;
import java.text.ParseException;
import java.time.ZonedDateTime;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * De abstracte basis voor het parsen van inkomende bevragingsberichten.
 * @param <T> het type {@link Verzoek}.
 */
public abstract class AbstractDienstVerzoekParser<T extends Verzoek> implements VerzoekParser<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Hulpmethode om de text uit een node te halen middels xpath.
     * @param locatie de locatie van de node als xpath.
     * @param xPath een XPath instantie
     * @param node de basis node
     * @return de text
     */
    protected static String getNodeTextContent(final String locatie, final XPath xPath, final Node node) {
        try {
            final Node xPathNode = (Node) xPath.evaluate(locatie, node, XPathConstants.NODE);
            if (xPathNode != null) {
                return xPathNode.getTextContent();
            }
        } catch (final XPathExpressionException e) {
            LOGGER.error("XPath voor text content kon niet worden geëvalueerd voor locatie {}.", locatie);
            throw new UnsupportedOperationException(e);
        }
        return null;
    }

    /**
     * Hulpmethode om een xml fragment uit een node te halen middels xpath.
     * @param locatie de locatie van de node als xpath.
     * @param xPath een XPath instantie
     * @param node de basis node
     * @return de text
     */
    protected static String getXmlFragment(final String locatie, final XPath xPath, final Node node) {
        try {
            final Node xPathNode = (Node) xPath.evaluate(locatie, node, XPathConstants.NODE);
            if (xPathNode != null) {
                final StringWriter buf = new StringWriter();
                final Transformer xform = TransformerFactory.newInstance().newTransformer();
                xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                xform.transform(new DOMSource(xPathNode), new StreamResult(buf));
                return buf.toString();
            }
        } catch (final XPathExpressionException | TransformerException e) {
            LOGGER.error("XPath voor text content kon niet worden geëvalueerd voor locatie {}.", locatie);
            throw new UnsupportedOperationException(e);
        }
        return null;
    }

    /**
     * Hulpmethode om de nodes uit een node te halen middels xpath.
     * @param locatie de locatie van de node als xpath.
     * @param xPath een XPath instantie
     * @param node de basis node
     * @return de text
     */
    protected static NodeList getNodeList(final String locatie, final XPath xPath, final Node node) {
        try {
            return (NodeList) xPath.evaluate(locatie, node, XPathConstants.NODESET);
        } catch (final XPathExpressionException e) {
            LOGGER.error("XPath voor node list kon niet worden geëvalueerd voor locatie {}.", locatie);
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public final void vulStuurgegevens(final T verzoek, final Node node, final XPath xPath) throws ParseException {
        verzoek.getStuurgegevens().setCommunicatieId(
                getNodeTextContent(getPrefix() + "/brp:stuurgegevens/@brp:communicatieID", xPath, node));
        verzoek.getStuurgegevens()
                .setZendendePartijCode(getNodeTextContent(getPrefix() + "/brp:stuurgegevens/brp:zendendePartij", xPath, node));
        verzoek.getStuurgegevens().setZendendSysteem(
                getNodeTextContent(getPrefix() + "/brp:stuurgegevens/brp:zendendeSysteem", xPath, node));
        verzoek.getStuurgegevens()
                .setReferentieNummer(getNodeTextContent(getPrefix() + "/brp:stuurgegevens/brp:referentienummer", xPath, node));
        final String tijdstipVerzendingString = getNodeTextContent(getPrefix() + "/brp:stuurgegevens/brp:tijdstipVerzending", xPath, node);
        if (tijdstipVerzendingString != null) {
            final ZonedDateTime tijdstipVerzending = DatumUtil.vanXsdDatumTijdNaarZonedDateTime(tijdstipVerzendingString);
            verzoek.getStuurgegevens().setTijdstipVerzending(tijdstipVerzending);
        }
    }
}

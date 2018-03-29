/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import nl.bzk.brp.service.algemeen.request.Verzoek;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.w3c.dom.Node;

/**
 * Generieke verzoekparser die voor de verschillende diensten een specifiek verzoek maakt.
 * @param <T> Het type {@link Verzoek}
 */
public abstract class AbstractGeneriekeBerichtParser<T extends Verzoek> {
    /**
     * De namespace URI van de BRP.
     */
    public static final String BRP_NAMESPACE_URI = "http://www.bzk.nl/brp/brp0200";
    /**
     * Scheidingsteken binnen XPath expressies.
     */
    public static final String SLASH = "/";
    /**
     * De intern gehanteerde brp namespace prefix.
     */
    public static final String BRP_NAMESPACE_PREFIX = "brp:";

    /**
     * De {@link XPath} instantie welke gebruikt wordt om de xpath expressie te evalueren.
     */
    protected final XPath xpath = initializeXPath();

    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    /**
     * Geeft de specialisatie van {@link VerzoekParser} welke gebruikt wordt om het verzoek te parsen.
     * @param node de node
     * @return de specialisatie van {@link VerzoekParser}
     */
    protected abstract VerzoekParser<T> geefDienstSpecifiekeParser(final Node node);

    /**
     * Maak een nieuw specifiek verzoek aan.
     * @return het specifieke verzoek
     */
    protected abstract T maakVerzoek();

    /**
     * Parst een verzoek XML op een generieke manier. Hierbij wordt er een {@link Node} en een {@link XPath} gebruikt.
     * @param node de {@link Node}
     * @return het {@link Verzoek}
     * @throws VerzoekParseException fout bij het parsen.
     */
    public final T parse(final Node node) throws VerzoekParseException {
        try {
            final T verzoek = maakVerzoek();
            final VerzoekParser<T> dienstBerichtParser = geefDienstSpecifiekeParser(node);
            dienstBerichtParser.vulStuurgegevens(verzoek, node, xpath);
            dienstBerichtParser.vulParameters(verzoek, node, xpath);
            dienstBerichtParser.vulDienstSpecifiekeGegevens(verzoek, node, xpath);
            zetBerichtXml(verzoek, node);
            return verzoek;
        } catch (final TransformerException | ParseException e) {
            throw new VerzoekParseException(e);
        }
    }

    private void zetBerichtXml(final Verzoek verzoek, final Node node) throws TransformerException {
        final StringWriter sw = new StringWriter();
        final StreamResult result = new StreamResult(sw);
        final Transformer stringTransformer = TRANSFORMER_FACTORY.newTransformer();
        stringTransformer.transform(new DOMSource(node), result);
        verzoek.setXmlBericht(sw.toString());
    }

    private static XPath initializeXPath() {
        final Map<String, String> mappings = new HashMap<>();
        mappings.put(BRP_NAMESPACE_PREFIX.replace(":", ""), BRP_NAMESPACE_URI);
        final SimpleNamespaceContext simpleNamespaceContext = new SimpleNamespaceContext();
        simpleNamespaceContext.setBindings(mappings);
        final XPathFactory xPathFactory = XPathFactory.newInstance();
        final XPath xpath = xPathFactory.newXPath();
        xpath.setNamespaceContext(simpleNamespaceContext);
        return xpath;
    }
}

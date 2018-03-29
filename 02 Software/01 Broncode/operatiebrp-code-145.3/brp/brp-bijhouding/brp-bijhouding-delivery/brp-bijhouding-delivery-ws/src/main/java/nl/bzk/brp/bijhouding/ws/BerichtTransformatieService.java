/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.ws;

import nl.bzk.brp.bijhouding.bericht.writer.WriteException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Transformeert bijhoudingsberichten in DOM structuren.
 */
@Service
public final class BerichtTransformatieService {

    private static final ThreadLocal<DocumentBuilderFactory> DOCUMENT_BUILDER_FACTORY_STORE = new ThreadLocal<>();
    private static final ThreadLocal<TransformerFactory> TRANSFORMER_FACTORY_STORE = new ThreadLocal<>();

    /**
     * Leest de inhoud van het bericht en zet dit om in XML.
     *
     * @param bericht bericht
     * @return de inhoud van het bericht als String
     * @throws IOException          wanneer het lezen van het bericht fout gaat
     * @throws TransformerException wanneer transformeren van het bericht fout gaat
     */
    public String transformeerNaarString(final DOMSource bericht) throws TransformerException {
        final StringWriter stringWriter = new StringWriter();
        final Transformer transformer = getTransformerFactory().newTransformer();
        transformer.transform(bericht, new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    /**
     * Maakt een {@link DOMSource} o.b.v. het gegeven xml bericht.
     *
     * @param xmlBericht het bericht dat moet worden getransformeert
     * @return de DOMSource
     * @throws WriteException wanneer er fouten optreden bij het maken van de DOMSource
     */
    public DOMSource transformeerNaarDOMSource(final String xmlBericht) throws WriteException {
        final DocumentBuilderFactory documentBuilderFactory = getDocumentBuilderFactory();
        final DocumentBuilder db;
        try {
            db = documentBuilderFactory.newDocumentBuilder();
            final Document doc = db.parse(new InputSource(new StringReader(xmlBericht)));
            return new DOMSource(doc);
        } catch (
                ParserConfigurationException
                        | SAXException
                        | IOException e) {
            throw new WriteException("Fout bij het omzetten van een BijhoudingAntwoordBericht in XML.", e);
        }
    }

    /*
     * Het maken van een DocumentBuilderFactory is duur en omdat deze niet threadsafe is mag je ook niet dezelfde
     * instantie hergebruiken over meerdere threads. Vandaar deze oplossing: 1 DocumentBuilderFactory per thread.
     */
    private static DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory result = DOCUMENT_BUILDER_FACTORY_STORE.get();
        if (result == null) {
            result = DocumentBuilderFactory.newInstance();
            result.setNamespaceAware(true);
            DOCUMENT_BUILDER_FACTORY_STORE.set(result);
        }
        return result;
    }

    /*
     * Het maken van een DocumentBuilderFactory is duur en omdat deze niet threadsafe is mag je ook niet dezelfde
     * instantie hergebruiken over meerdere threads. Vandaar deze oplossing: 1 DocumentBuilderFactory per thread.
     */
    private static TransformerFactory getTransformerFactory() {
        TransformerFactory result = TRANSFORMER_FACTORY_STORE.get();
        if (result == null) {
            result = TransformerFactory.newInstance();
            TRANSFORMER_FACTORY_STORE.set(result);
        }
        return result;
    }
}

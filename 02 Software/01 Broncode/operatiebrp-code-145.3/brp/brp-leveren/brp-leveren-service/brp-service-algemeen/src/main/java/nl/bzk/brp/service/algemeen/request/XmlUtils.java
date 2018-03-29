/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Util klasse voor XML.
 */
public final class XmlUtils {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    static {
        DOCUMENT_BUILDER_FACTORY.setNamespaceAware(true);
    }

    private XmlUtils() {
    }

    /**
     * Converteert en input String naar DOMSource.
     * @param input een xml String
     * @return een DOMSource
     * @throws MaakDomSourceException als DOMSource niet gemaakt kan worden
     */
    public static DOMSource toDOMSource(final InputSource input) throws MaakDomSourceException {
        final DocumentBuilder db;
        try {
            db = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
            final Document doc = db.parse(input);
            return new DOMSource(doc);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MaakDomSourceException(e);
        }
    }
}

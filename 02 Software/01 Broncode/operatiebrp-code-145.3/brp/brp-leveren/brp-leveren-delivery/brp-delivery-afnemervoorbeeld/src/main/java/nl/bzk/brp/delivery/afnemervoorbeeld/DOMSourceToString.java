/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemervoorbeeld;

import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Transformeert een DOMSource naar String.
 */
final class DOMSourceToString {

    private static final TransformerFactory FACTORY = TransformerFactory.newInstance();

    private DOMSourceToString() {
    }

    static String toString(final DOMSource domSource) {

        final StringWriter stringWriter = new StringWriter();
        final Result result = new StreamResult(stringWriter);

        Transformer transformer;
        try {
            transformer = FACTORY.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new IllegalStateException(e);
        }
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");
        try {
            transformer.transform(domSource, result);
        } catch (TransformerException e) {
            throw new IllegalStateException(e);
        }
        return stringWriter.toString();
    }
}

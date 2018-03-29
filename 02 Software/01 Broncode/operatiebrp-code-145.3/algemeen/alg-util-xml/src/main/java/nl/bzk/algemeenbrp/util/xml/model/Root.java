/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Geef de root van een XML structuur aan.
 * @param <T> type van de waarde
 */
public final class Root<T> {

    private final XmlObject<T> element;
    private final String name;

    /**
     * Constructor.
     * @param element element
     * @param name naam
     */
    public Root(final XmlObject<T> element, final String name) {
        this.element = element;
        this.name = name;
    }

    /**
     * Encodeer de XML structuur.
     * @param context context
     * @param value waarde
     * @param writer writer om XML te schrijven.
     * @throws ConfigurationException configuratie problemen (bij annotaties in kinderen)
     * @throws EncodeException encodeer problemen
     */
    public void encode(final Context context, final T value, final Writer writer) throws XmlException {
        element.encode(context, value.getClass(), name, value, writer);
    }

    /**
     * Decodeer de XML structuur.
     * @param context context
     * @param document DOM XML document
     * @return de gelezen waarde
     * @throws ConfigurationException configuratie problemen (bij annotaties in kinderen)
     * @throws DecodeException decodeer problemen
     */
    public T decode(final Context context, final Document document) throws XmlException {
        final Element documentElement = document.getDocumentElement();
        if (name.equals(documentElement.getLocalName())) {
            return element.decode(context, documentElement);
        } else {
            throw new DecodeException(
                    context.getElementStack(),
                    "Root node met naam '" + name + "' verwacht, maar '" + documentElement.getLocalName() + "' gevonden.");
        }
    }

    @Override
    public String toString() {
        return "Root [element=" + element + ", name=" + name + "]";
    }
}

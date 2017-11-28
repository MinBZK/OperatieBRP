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

/**
 * XML Element.
 * @param <T> type van de waarde van het element
 */
public interface XmlObject<T> {

    /**
     * Encodeer een element.
     * @param context encoding context
     * @param clazzFromParent class die vanuit de parent geconfigureerd is
     * @param nameFromParent naam die vanuit de parent geconfigureerd is
     * @param value de daadwerkelijke waarde
     * @param writer writer
     * @throws ConfigurationException bij configuratie fouten (annotaties in het kind)
     * @throws EncodeException bij encodeer fouten
     */
    void encode(final Context context, Class<?> clazzFromParent, String nameFromParent, T value, Writer writer)
            throws XmlException;

    /**
     * Decodeer een element.
     * @param context decoding context
     * @param element jaxp element
     * @return de waarde
     * @throws ConfigurationException bij configuratie fouten (annotaties in het kind)
     * @throws DecodeException bij decodeer fouten
     */
    T decode(Context context, org.w3c.dom.Element element) throws XmlException;
}

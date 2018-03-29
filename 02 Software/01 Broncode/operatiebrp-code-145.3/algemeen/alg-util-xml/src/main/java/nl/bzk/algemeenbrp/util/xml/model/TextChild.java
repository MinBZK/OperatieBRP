/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.IOException;
import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.converter.Converter;
import nl.bzk.algemeenbrp.util.xml.converter.ConverterFactory;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Een text als kind van een {@link CompositeObject}.
 * @param <T> type van de waarde
 */
public final class TextChild<T> extends AbstractChild<T> {

    private static final String TEXT_ELEMENT = "**text**";

    private final Converter<T> converter;

    /**
     * Constructor.
     * @param accessor toegang tot de waarde
     * @param name naam voor in xml
     * @param clazz klasse van de waarde (wordt gebruikt voor {@link Converter}
     */
    public TextChild(final Accessor<T> accessor, final String name, final Class<T> clazz) {
        super(accessor, name);
        this.converter = ConverterFactory.getConverter(clazz);
    }

    /**
     * Schrijf dit attribuut (met de geconfigureerde naam).
     * @param value waarde
     * @param writer writer om XML mee te schrijven
     * @throws EncodeException bij encodeer fouten
     */
    @Override
    public void encode(final Context context, final T value, final Writer writer) throws EncodeException {
        context.pushElement(TEXT_ELEMENT);
        if (value == null) {
            return;
        }

        try {
            writer.append(converter.encode(context, value));
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
        context.popElement();
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    /**
     * Lees dit attribuut (met de geconfigureerde naam).
     * @param element XML DOM element
     * @return gelezen waarde
     * @throws DecodeException bij decodeer problemen
     */
    @Override
    public T decode(final Context context, final Node element, final T previousValue) throws DecodeException {
        if (previousValue != null) {
            throw new DecodeException(context.getElementStack(), "Tweede voorkomen van " + getName() + " niet verwacht.");
        }

        context.pushElement(TEXT_ELEMENT);
        try {
            final String value = element.getTextContent();
            return value == null || "".equals(value) ? null : converter.decode(context, value);
        } finally {
            context.popElement();
        }
    }

    @Override
    public boolean canDecode(final Node node) {
        return node instanceof Text;
    }

    @Override
    public String toString() {
        return "Text [converter=" + converter + ", getAccessor()=" + getAccessor() + ", getName()=" + getName() + "]";
    }

}

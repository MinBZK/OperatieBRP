/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.IOException;
import java.io.Writer;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.converter.Converter;
import nl.bzk.algemeenbrp.util.xml.converter.ConverterFactory;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

/**
 * Een attribuut van een {@link CompositeObject}.
 *
 * @param <T> type van het attribuut
 */
public final class AttributeChild<T> extends AbstractChild<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Converter<T> converter;

    /**
     * Constructor.
     *
     * @param accessor toegang tot de waarde
     * @param name naam voor in xml
     */
    public AttributeChild(final Accessor<T> accessor, final String name) {
        super(accessor, name);
        LOGGER.debug("AttributeChild(accessor={}, name={})", accessor, name);
        this.converter = ConverterFactory.getConverter(accessor.getClazz());
    }

    /**
     * Schrijf dit attribuut.
     *
     * @param value waarde
     * @param writer writer om XML mee te schrijven
     * @throws EncodeException bij encodeer foute
     */
    @Override
    public void encode(final Context context, final T value, final Writer writer) throws EncodeException {
        LOGGER.debug("encode(value={})", value);
        if (value == null) {
            return;
        }

        try {
            writer.append(" ");
            writer.append(getName());
            writer.append("=\"");
            writer.append(converter.encode(context, value));
            writer.append("\"");
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
    }

    @Override
    public boolean isAttribute() {
        return true;
    }

    /**
     * Lees dit attribuut.
     *
     * @param alternativeName alternatieve naam
     * @param element XML DOM element
     * @return gelezen waarde
     * @throws DecodeException bij decodeer problemen
     */
    @Override
    public T decode(final Context context, final Node element, final T previousValue) throws DecodeException {
        if (element == null) {
            return null;
        }
        final String value = ((Attr) element).getValue();
        return value == null || "".equals(value) ? null : converter.decode(context, value);
    }

    @Override
    public boolean canDecode(final Node node) {
        return node instanceof Attr && node.getLocalName().equals(getName());
    }

    @Override
    public String toString() {
        return "Attribute [converter=" + converter + ", getAccessor()=" + getAccessor() + ", getName()=" + getName() + "]";
    }

}

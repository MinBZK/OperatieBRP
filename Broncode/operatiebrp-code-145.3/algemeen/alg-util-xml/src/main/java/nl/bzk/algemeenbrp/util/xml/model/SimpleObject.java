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
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;
import nl.bzk.algemeenbrp.util.xml.util.XmlConstants;

/**
 * Een element met een waarde die direct in de XML gecodeerd wordt.
 * @param <T>type van de waarde
 */
public final class SimpleObject<T> implements XmlObject<T> {

    private final Class<T> clazz;
    private final Converter<T> converter;

    /**
     * Constructor.
     * @param clazz type van de waarde
     */
    public SimpleObject(final Class<T> clazz) {
        this.clazz = clazz;
        this.converter = ConverterFactory.getConverter(clazz);
    }

    @Override
    public void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final T value, final Writer writer)
            throws EncodeException {

        if (value == null) {
            return;
        }
        context.pushElement(nameFromParent);
        try {
            writer.write(XmlConstants.START_ELEMENT);
            writer.write(nameFromParent);
            if (!ReflectionUtils.isSameClass(value.getClass(), clazzFromParent)) {
                writer.write(" class=\"");
                writer.write(value.getClass().getName());
                writer.write("\"");
            }
            writer.write(XmlConstants.ELEMENT_END);

            writer.append(converter.encode(context, value));

            writer.write(XmlConstants.END_ELEMENT);
            writer.write(nameFromParent);
            writer.write(XmlConstants.ELEMENT_END);
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
        context.popElement();
    }

    @Override
    public T decode(final Context context, final org.w3c.dom.Element domElement) throws DecodeException {
        if (domElement == null) {
            return null;
        }
        context.pushElement(domElement.getLocalName());
        try {
            final String value = domElement.getTextContent();
            return converter.decode(context, value);
        } finally {
            context.popElement();
        }
    }

    @Override
    public String toString() {
        return "Simple [clazz=" + clazz + ", converter=" + converter + "]";
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.util.DomUtils;
import nl.bzk.algemeenbrp.util.xml.util.XmlConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Een map van elementen als kind van een {@link CompositeObject}.
 * @param <T> type van de map
 */
public final class MapChild<T extends Map<?, ?>> extends AbstractChild<T> {

    private final boolean inline;
    private final String entryName;
    private final String keyName;
    private final Class<?> keyType;
    private final AttributeChild<Object> keyAttribute;
    private final String valueName;
    private final Class<?> valueType;
    private final AttributeChild<Object> valueAttribute;
    private final boolean outputAsAttributes;

    /**
     * Constructor.
     * @param accessor toegang tot de waarde
     * @param name naam
     * @param inline indicatie inlnie
     * @param entryName naam voor de afzonderlijke rijen
     * @param keyName naam voor de key
     * @param keyType type van de key
     * @param valueName naam voor de value
     * @param valueType type van de value
     * @param outputAsAttributes indicatie dat key en value als attribuut verwerkt moeten worden
     */
    public MapChild(
            final Accessor<T> accessor,
            final String name,
            final boolean inline,
            final String entryName,
            final String keyName,
            final Class<?> keyType,
            final String valueName,
            final Class<?> valueType,
            final boolean outputAsAttributes) {
        super(accessor, name);
        this.inline = inline;
        this.entryName = entryName;
        this.keyName = keyName;
        this.keyType = keyType;
        keyAttribute = (AttributeChild<Object>) new AttributeChild<>(new Accessor<>(keyType, null, null, null, null), keyName);
        this.valueName = valueName;
        this.valueType = valueType;
        valueAttribute = (AttributeChild<Object>) new AttributeChild<>(new Accessor<>(valueType, null, null, null, null), valueName);
        this.outputAsAttributes = outputAsAttributes;
    }

    @Override
    public void encode(final Context context, final T value, final Writer writer) throws XmlException {
        if (value == null) {
            return;
        }

        try {
            if (!inline) {
                writer.write(XmlConstants.START_ELEMENT);
                writer.write(getName());
                writer.write(XmlConstants.ELEMENT_END);
            }

            for (final Map.Entry<?, ?> entry : value.entrySet()) {
                writer.write("<");
                writer.write(entryName);

                if (outputAsAttributes) {
                    keyAttribute.encode(context, entry.getKey(), writer);
                    valueAttribute.encode(context, entry.getValue(), writer);

                    writer.write(XmlConstants.ELEMENT_CLOSE);
                } else {
                    writer.write(XmlConstants.ELEMENT_END);

                    final XmlObject keyObject = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(keyType);
                    keyObject.encode(context, keyType, keyName, entry.getKey(), writer);
                    final XmlObject valueObject = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(valueType);
                    valueObject.encode(context, valueType, valueName, entry.getValue(), writer);

                    writer.write(XmlConstants.END_ELEMENT);
                    writer.write(entryName);
                    writer.write(XmlConstants.ELEMENT_END);

                }
            }

            if (!inline) {
                writer.write(XmlConstants.END_ELEMENT);
                writer.write(getName());
                writer.write(XmlConstants.ELEMENT_END);

            }
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public T decode(final Context context, final Node domElement, final T previousValue) throws XmlException {
        if (!inline) {
            if (previousValue != null) {
                throw new DecodeException(context.getElementStack(), "Tweede voorkomen van " + getName() + " niet verwacht.");
            }
            return decodeParent(context, (Element) domElement);
        } else {
            return decodeRecord(context, (Element) domElement, previousValue);
        }
    }

    private T decodeParent(final Context context, final Element domElement) throws XmlException {
        context.pushElement(domElement.getLocalName());
        try {
            Map result = new HashMap<>();
            for (final Element childDomElement : DomUtils.getDirectChildrenNamed(domElement, entryName)) {
                result = decodeRecord(context, childDomElement, (T) result);
            }
            return (T) result;
        } finally {
            context.popElement();
        }
    }

    private T decodeRecord(final Context context, final Element domElement, final T previousValue)
            throws XmlException {
        final Map result = previousValue == null ? new HashMap<>() : previousValue;

        final Object key;
        final Object value;
        if (outputAsAttributes) {
            key = keyAttribute.decode(context, DomUtils.getChildAttribute(domElement, keyName), null);
            value = valueAttribute.decode(context, DomUtils.getChildAttribute(domElement, valueName), null);
        } else {
            final XmlObject keyObject = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(keyType);
            key = keyObject.decode(context, DomUtils.getChildElement(domElement, keyName));
            final XmlObject valueObject = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(valueType);
            value = valueObject.decode(context, DomUtils.getChildElement(domElement, valueName));
        }

        result.put(key, value);

        return (T) result;
    }

    @Override
    public boolean canDecode(final Node node) {
        final boolean result;

        if (node instanceof Element) {
            if (inline) {
                result = node.getLocalName().equals(entryName);
            } else {
                result = node.getLocalName().equals(getName());
            }
        } else {
            result = false;
        }
        return result;
    }

}

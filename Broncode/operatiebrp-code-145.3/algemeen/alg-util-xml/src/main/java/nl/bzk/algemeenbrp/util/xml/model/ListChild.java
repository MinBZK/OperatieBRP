/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.util.DomUtils;
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;
import nl.bzk.algemeenbrp.util.xml.util.XmlConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Een lijst van elementen als kind van een {@link CompositeObject}.
 * @param <T> type van de lijst
 */
public final class ListChild<T extends Collection> extends AbstractChild<T> {

    private final boolean inline;
    private final Class<?> elementType;
    private final String elementName;

    /**
     * Constructor.
     * @param accessor toegang tot de waarde
     * @param name naam
     * @param inline indicatie inline
     * @param elementType type van de afzonderlijke rijen
     * @param elementName naam voor de afzonderlijke rijen
     */
    public ListChild(final Accessor<T> accessor, final String name, final boolean inline, final Class<?> elementType, final String elementName) {
        super(accessor, name);
        this.inline = inline;
        this.elementType = elementType;
        this.elementName = elementName;
    }

    @Override
    public void encode(final Context context, final T value, final Writer writer) throws XmlException {
        if (value == null) {
            return;
        }

        context.pushElement(getName());

        try {
            if (!inline) {
                writer.write(XmlConstants.START_ELEMENT);
                writer.write(getName());

                // Class
                if (!ReflectionUtils.isSameClass(value.getClass(), getAccessor().getClazz())) {
                    writer.write(" class=\"");
                    writer.write(value.getClass().getName());
                    writer.write("\"");
                }

                writer.write(XmlConstants.ELEMENT_END);
            }

            for (final Object elementValue : value) {
                writeElement(context, elementValue, writer);
            }

            if (!inline) {
                writer.write(XmlConstants.END_ELEMENT);
                writer.write(getName());
                writer.write(XmlConstants.ELEMENT_END);
            }
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        } finally {
            context.popElement();
        }
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    private <E> void writeElement(final Context context, final E value, final Writer writer) throws XmlException {
        if (value == null) {
            return;
        }

        final Class<E> elementClass = (Class<E>) value.getClass();
        final XmlObject<E> element = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(elementClass);

        element.encode(context, elementType, elementName, value, writer);

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
            Collection result = instantieerCollection(context);
            for (final Element childDomElement : DomUtils.getDirectChildrenNamed(domElement, elementName)) {
                result = decodeRecord(context, childDomElement, (T) result);
            }
            return (T) result;
        } finally {
            context.popElement();
        }
    }

    private T decodeRecord(final Context context, final Element domElement, final T previousValue) throws XmlException {
        final Collection result = previousValue == null ? instantieerCollection(context) : previousValue;

        final String className = domElement.getAttribute("class");

        final XmlObject<?> element;
        if (className == null || "".equals(className)) {

            element = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(elementType);
        } else {
            try {
                element = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(Class.forName(className));
            } catch (final ClassNotFoundException e) {
                throw new DecodeException(context.getElementStack(), e);
            }
        }

        result.add(element.decode(context, domElement));

        return (T) result;

    }

    private Collection<?> instantieerCollection(final Context context) throws DecodeException {

        final Class<?> clazz = getAccessor().getClazz();
        final Collection<?> result;

        if (clazz.isInterface()) {
            // Probeer een correcte implementatie te instantieren
            if (List.class.isAssignableFrom(clazz)) {
                result = new ArrayList<>();
            } else if (Set.class.isAssignableFrom(clazz)) {
                result = new HashSet<>();
            } else {
                throw new DecodeException(context.getElementStack(), "Kan geen collection instantieren voor type " + clazz);
            }
        } else {
            try {
                result = (Collection<?>) clazz.newInstance();
            } catch (final ReflectiveOperationException e) {
                throw new DecodeException(context.getElementStack(), "Kan collection van type " + clazz + " niet instantieren", e);
            }
        }
        return result;
    }

    @Override
    public boolean canDecode(final Node node) {
        final boolean result;

        if (node instanceof Element) {
            if (inline) {
                result = node.getLocalName().equals(elementName);
            } else {
                result = node.getLocalName().equals(getName());
            }
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return "ChildList [inline=" + inline + ", elementType=" + elementType + ", elementName=" + elementName + ", getAccessor()=" + getAccessor()
                + ", getName()=" + getName() + "]";
    }

}

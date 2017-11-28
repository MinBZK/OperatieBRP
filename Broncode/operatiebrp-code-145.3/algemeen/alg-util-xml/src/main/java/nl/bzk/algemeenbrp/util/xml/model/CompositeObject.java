/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.util.DomUtils;
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;
import nl.bzk.algemeenbrp.util.xml.util.XmlConstants;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Een samengesteld element.
 * @param <T> type van de waarde van het element
 */
public final class CompositeObject<T> implements XmlObject<T> {

    private final Class<T> clazz;
    private final Constructor<T> constructor;
    private final List<Child<?>> children;

    /**
     * Constructor.
     * @param clazz klasse van de waarde
     * @param constructor constructor om de waarde te instantieren bij decoderen
     * @param children kind elementen
     */
    public CompositeObject(final Class<T> clazz, final Constructor<T> constructor, final List<Child<?>> children) {
        this.clazz = clazz;
        this.constructor = constructor;
        ReflectionUtils.setAccessible(constructor);
        this.children = children;
    }

    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */

    public Class<T> getClazz() {
        return clazz;
    }

    public Constructor<T> getConstructor() {
        return constructor;
    }

    public List<Child<?>> getChildren() {
        return children;
    }

    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */

    @Override
    public void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final T value, final Writer writer)
            throws XmlException {
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

            // Attributes
            for (final Child<?> child : children) {
                if (child.isAttribute()) {
                    writeChild(context, value, child, writer);
                }
            }

            writer.write(XmlConstants.ELEMENT_END);

            // Children
            for (final Child<?> child : children) {
                if (!child.isAttribute()) {
                    writeChild(context, value, child, writer);
                }
            }

            writer.write(XmlConstants.END_ELEMENT);
            writer.write(nameFromParent);
            writer.write(XmlConstants.ELEMENT_END);
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }

        context.popElement();
    }

    private <E> void writeChild(final Context context, final Object parent, final Child<E> child, final Writer writer)
            throws XmlException {
        E value;
        try {
            value = child.getAccessor().getValueFromParent(parent);
        } catch (final ReflectiveOperationException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
        child.encode(context, value, writer);
    }

    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */

    @Override
    public T decode(final Context context, final Element element) throws XmlException {
        if (element == null) {
            return null;
        }

        context.pushElement(element.getLocalName());

        try {
            // Lees 'children' op volgorde van XML
            final Map<Child, Object> childValues = new HashMap<>();

            loopDoorAttributen(context, element, childValues);

            loopDoorChildren(context, element, childValues);

            final Object[] parameters = verwerkConstructor(childValues);

            T instance = maakInstantie(context, parameters);

            verwerkSetters(context, childValues, instance);

            return instance;
        } finally {
            context.popElement();
        }
    }

    private void loopDoorAttributen(final Context context, final Element element, final Map<Child, Object> childValues)
            throws XmlException {
        final NamedNodeMap attributes = element.getAttributes();
        for (int attributeIndex = 0; attributeIndex < attributes.getLength(); attributeIndex++) {
            final Node childNode = attributes.item(attributeIndex);
            verwerkChildElement(context, childValues, childNode);
        }
    }

    private void loopDoorChildren(final Context context, final Element element, final Map<Child, Object> childValues)
            throws XmlException {
        Node childElement = element.getFirstChild();
        while (childElement != null) {
            verwerkChildElement(context, childValues, childElement);

            childElement = childElement.getNextSibling();
        }
    }

    private Object[] verwerkConstructor(final Map<Child, Object> childValues) {
        final Object[] parameters = new Object[constructor.getParameterTypes().length];
        for (final Map.Entry<Child, Object> childValue : childValues.entrySet()) {
            if (childValue.getKey().getAccessor().getConstructorIndex() != null) {
                parameters[childValue.getKey().getAccessor().getConstructorIndex()] = childValue.getValue();
            }
        }
        return parameters;
    }

    private T maakInstantie(final Context context, final Object[] parameters) throws DecodeException {
        T instance;
        try {
            instance = constructor.newInstance(parameters);
        } catch (final ReflectiveOperationException e) {
            throw new DecodeException(context.getElementStack(), e);
        }
        return instance;
    }

    private void verwerkSetters(final Context context, final Map<Child, Object> childValues, final T instance) throws DecodeException {
        for (final Map.Entry<Child, Object> childValue : childValues.entrySet()) {
            if (childValue.getKey().getAccessor().hasFieldOrSetterAndNoConstructor()) {
                try {
                    childValue.getKey().getAccessor().setValueInParent(instance, childValue.getValue());
                } catch (final ReflectiveOperationException e) {
                    throw new DecodeException(context.getElementStack(), e);
                }
            }
        }
    }

    private void verwerkChildElement(final Context context, final Map<Child, Object> childValues, final Node childElement)
            throws XmlException {
        final Child child = findChildForElement(childElement);
        if (child == null) {
            if (!DomUtils.isIgnorableNode(childElement)) {
                final String mesg;
                if (childElement.getParentNode() != null) {
                    mesg = String.format("Node %s niet verwacht voor parent %s", childElement.getLocalName(), childElement.getParentNode().getLocalName());
                } else {
                    mesg = String.format("Node %s niet verwacht", childElement.getLocalName());
                }
                throw new DecodeException(context.getElementStack(), mesg);
            } else {
                return;
            }
        }

        childValues.put(child, child.decode(context, childElement, childValues.get(child)));
    }

    private Child<?> findChildForElement(final Node childElement) {
        for (final Child<?> child : children) {
            if (child.canDecode(childElement)) {
                return child;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Composite [clazz=" + clazz + ", constructor=" + constructor + ", children=" + children + "]";
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Een element als kind van een {@link CompositeObject}.
 * @param <T> type van de waarde van het element
 */
public final class ElementChild<T> extends AbstractChild<T> {

    /**
     * Constructor.
     * @param accessor toegang tot de waarde
     * @param name naam
     */
    public ElementChild(final Accessor<T> accessor, final String name) {
        super(accessor, name);
    }

    @Override
    public void encode(final Context context, final T value, final Writer writer) throws XmlException {
        if (value == null) {
            return;
        }

        final Class<T> valueClazz = (Class<T>) value.getClass();
        final XmlObject<T> element = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(valueClazz);
        element.encode(context, getAccessor().getClazz(), getName(), value, writer);
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public T decode(final Context context, final Node domElement, final T previousValue) throws XmlException {
        if (previousValue != null) {
            throw new DecodeException(context.getElementStack(), "Tweede voorkomen van " + getName() + " niet verwacht.");
        }
        final String className = ((Element) domElement).getAttribute("class");

        final XmlObject<?> element;
        if (className == null || "".equals(className)) {
            element = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(getAccessor().getClazz());
        } else {
            try {
                element = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(Class.forName(className));
            } catch (final ClassNotFoundException e) {
                throw new DecodeException(context.getElementStack(), e);
            }
        }

        return (T) element.decode(context, (Element) domElement);
    }

    @Override
    public boolean canDecode(final Node node) {
        return node instanceof Element && node.getLocalName().equals(getName());
    }

    @Override
    public String toString() {
        return "ChildElement [getAccessor()=" + getAccessor() + ", getName()=" + getName() + "]";
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model.optional;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.AttributeChild;
import nl.bzk.algemeenbrp.util.xml.model.Child;
import nl.bzk.algemeenbrp.util.xml.model.CompositeObject;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.algemeenbrp.util.xml.util.DomUtils;

/**
 * Een composite object waarbij een 'id' attribuut gebruikt wordt om xml 'ref's te ondersteunen.
 * @param <T> type object.
 */
public class CompositeObjectWithRefs<T> implements XmlObject<T> {

    private static final String REF_ELEMENT = "**ref**";

    private final CompositeObject<T> base;
    private final Child idAttribute;
    private final AttributeChild refAttribute;

    /**
     * Constructor.
     * @param base default composite config
     */
    public CompositeObjectWithRefs(final CompositeObject<T> base) {
        this.base = base;
        idAttribute = findIdAttribute(base.getChildren());
        refAttribute = new AttributeChild<>(idAttribute.getAccessor(), "ref");
    }

    private static Child<?> findIdAttribute(final List<Child<?>> children) {
        for (final Child<?> child : children) {
            if (child.isAttribute() && "id".equals(child.getName())) {
                return child;
            }
        }
        throw new IllegalArgumentException("Geen id attribute aanwezig");
    }
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */

    @Override
    public final void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final T value, final Writer writer)
            throws XmlException {
        if (value == null) {
            return;
        }

        context.pushElement(REF_ELEMENT);
        try {
            final Object idValue;
            try {
                idValue = idAttribute.getAccessor().getValueFromParent(value);
            } catch (final ReflectiveOperationException e) {
                throw new EncodeException(context.getElementStack(), e);
            }

            if (RefsHelper.isIdAlreadyWritten(context, base.getClazz(), idValue)) {
                try {
                    writer.write("<");
                    writer.write(nameFromParent);

                    refAttribute.encode(context, refAttribute.getAccessor().getValueFromParent(value), writer);

                    writer.write(" />");
                } catch (final IOException | ReflectiveOperationException e) {
                    throw new EncodeException(context.getElementStack(), e);
                }

            } else {
                RefsHelper.registerWrittenId(context, base.getClazz(), idValue);
                base.encode(context, clazzFromParent, nameFromParent, value, writer);
            }
        } finally {
            context.popElement();
        }

    }

    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */
    /* ***************************************************************** */

    @Override
    public final T decode(final Context context, final org.w3c.dom.Element element) throws XmlException {
        if (element == null) {
            return null;
        }

        context.pushElement(REF_ELEMENT);
        try {
            T result;
            final Object refValue = refAttribute.decode(context, DomUtils.getChildAttribute(element, refAttribute.getName()), null);
            if (refValue != null) {
                result = getReference(refValue, (T) RefsHelper.getReadId(context, base.getClazz(), refValue));
            } else {
                result = base.decode(context, element);

                final Object idValue;
                try {
                    idValue = idAttribute.getAccessor().getValueFromParent(result);
                } catch (final ReflectiveOperationException e) {
                    throw new DecodeException(context.getElementStack(), e);
                }

                RefsHelper.registerReadId(context, base.getClazz(), idValue, result);
            }
            return result;
        } finally {
            context.popElement();
        }
    }

    /**
     * Hook om het te refereren object aan te passen.
     * @param idValue id
     * @param instance default gerefereerde object (kan dus leeg zijn en het object binnen zichzelf wordt gerefereerd)
     * @return te gebruiken object
     */
    protected T getReference(final Object idValue, final T instance) {
        return instance;
    }

    @Override
    public final String toString() {
        return "CompositeObjectWithRefs [base=" + base + ", idAttribute=" + idAttribute + ", refAttribute=" + refAttribute + "]";
    }
}

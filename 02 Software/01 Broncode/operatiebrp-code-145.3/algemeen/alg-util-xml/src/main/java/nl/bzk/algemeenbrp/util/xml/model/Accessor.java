/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.xml.util.ReflectionUtils;

/**
 * Toegangs gegevens (onderdeel van een {@link CompositeObject}.
 *
 * @param <T> type object waarvoor de toegang is.
 */
public final class Accessor<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final Class<T> clazz;
    private final Field field;

    private final Method getter;

    private final Method setter;
    private final Integer constructorIndex;

    /**
     * Constuctor.
     *
     * @param clazz type waarvoor toegang worden geboden
     * @param field veld
     * @param getter getter methode
     * @param setter setter methode
     * @param constructorIndex index in constructor
     */
    public Accessor(final Class<T> clazz, final Field field, final Method getter, final Method setter, final Integer constructorIndex) {
        LOGGER.debug("Accessor(clazz={}, field={}, getter={}, setter={}, constructorIndex={})", clazz, field, getter, setter, constructorIndex);
        this.clazz = clazz;

        this.field = field;
        ReflectionUtils.setAccessible(this.field);
        this.getter = getter;
        ReflectionUtils.setAccessible(this.getter);
        this.setter = setter;
        ReflectionUtils.setAccessible(this.setter);
        this.constructorIndex = constructorIndex;
    }

    /**
     * Geeft de type waartoe toegang worden geboden.
     *
     * @return type
     */
    public Class<T> getClazz() {
        return clazz;
    }

    /**
     * Geef de constructor index.
     *
     * @return constructor index
     */
    public Integer getConstructorIndex() {
        return constructorIndex;
    }

    /**
     * Geef de waarde uit het parent object.
     *
     * @param parent parent
     * @return de waarde
     * @throws ReflectiveOperationException bij toegangsfouten
     */
    @SuppressWarnings("unchecked")
    public T getValueFromParent(final Object parent) throws ReflectiveOperationException {
        final T result;
        if (getter != null) {
            result = (T) getter.invoke(parent);
        } else if (field != null) {
            result = (T) field.get(parent);
        } else {
            result = null;
        }
        LOGGER.debug("getValueFromParent(parent.class={}) -> {}", parent.getClass().getSimpleName(), result);
        return result;
    }

    /**
     * Bepaal of de waarde gezet moet worden via {@link #setValueInParent(Object, Object)}.
     *
     * @return true, als de waarde gezet moet worden via {@link #setValueInParent(Object, Object)}.
     */
    public boolean hasFieldOrSetterAndNoConstructor() {
        return constructorIndex == null && (setter != null || field != null);
    }

    /**
     * Zet de waarde in het parent object.
     *
     * @param parent parent
     * @param value waarde
     * @throws ReflectiveOperationException bij toegangsfouten
     */
    public void setValueInParent(final Object parent, final T value) throws ReflectiveOperationException {
        LOGGER.debug("setValueInParent(parent.class={}, value.class={}, value={})", parent.getClass().getSimpleName(),
                value == null ? "null" : value.getClass().getSimpleName(), value);
        if (setter != null) {
            setter.invoke(parent, value);
        } else if (field != null) {
            field.set(parent, value);
        }
    }

    @Override
    public String toString() {
        return "Accessor [clazz=" + clazz + ", field=" + field + ", getter=" + getter + ", setter=" + setter + ", constructorIndex=" + constructorIndex + "]";
    }

}

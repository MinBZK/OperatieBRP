/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.lang.reflect.InvocationTargetException;
import org.springframework.util.Assert;

/**
 * Represents an operation item type.
 * @param <T> the type of the operation item
 */
interface OperationItem<T> {

    /**
     * Creates a builder for building operation items.
     * @param <T> the type of operation item fields this builder should build
     * @return a builder
     */
    static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Returns a string of specified length using the SpdConstants.PADDING_CHAR.
     * @param length length of the string to be returned
     * @return string of specified length
     */
    static String paddedValueOfLength(final int length) {
        return paddedValueOfLength(" ", length);
    }

    /**
     * Returns the specified value padded to specified length using the SpdConstants.PADDING_CHAR.
     * @param value value to be padded
     * @param length length of the string to be returned
     * @return value padded to specified length
     */
    static String paddedValueOfLength(final String value, final int length) {
        Assert.isTrue(length > 0, "De waarde mag niet leeg zijn.");
        final String pattern = "%" + length + "s";
        return String.format(pattern, value == null ? " " : value);
    }

    /**
     * Name.
     * @return name
     */
    String name();

    /**
     * Length.
     * @return length
     */
    int length();

    /**
     * Indicates whether this operation item is optional.
     * @return a boolean indicating if this operation item is optional or not
     */
    boolean isOptional();

    /**
     * Indicates whether this operation item is mandatory.
     * @return a boolean indicating if this operation item is mandatory or not
     */
    boolean isMandatory();

    /**
     * Indicates whether the length of the value of this operation item may be shorter than the specified length.
     * @return a boolean indicating if this operation item may be shorter or not
     */
    boolean mayBeShorter();

    /**
     * Indicates whether this operation item is empty.
     * @return a boolean indicating if this operation item is empty or not
     */
    boolean isEmpty();

    /**
     * Returns the value of this operation item.
     * @return the value
     */
    T getValue();

    /**
     * Sets the value of this operation item.
     * @param val the value
     */
    void setValue(T val);

    /**
     * Sets the value of this operation item based on the string representation of that value.
     * @param val the string representation of the value
     */
    void setRawValue(String val);

    /**
     * Returns the sPd-protocol representation of this operation item.
     * @return the sPd-protocol representation of this operation item
     */
    String toSpdString();

    /**
     * Validates this operation item.
     * @return if this operation item is valid or not
     */
    boolean validate();

    /**
     * Builder for building operation items.
     * @param <T> the type of operation item fields this builder should build
     */
    final class Builder<T> {
        private String name;
        private int length;
        private boolean optional;
        private boolean shorter;
        private Class<? extends Field<T>> target;

        /**
         * Constructor.
         */
        Builder() {
        }

        /**
         * Sets the name.
         * @param value name
         * @return the current builder
         */
        Builder<T> name(final String value) {
            this.name = value;
            return this;
        }

        /**
         * Sets the length.
         * @param value name
         * @return the current builder
         */
        Builder<T> length(final int value) {
            this.length = value;
            return this;
        }

        /**
         * Sets if the field is optional.
         * @return the current builder
         */
        Builder<T> optional() {
            this.optional = true;
            return this;
        }

        /**
         * Sets if the field may be shorter.
         * @return the current builder
         */
        Builder<T> mayBeShorter() {
            this.shorter = true;
            return this;
        }

        /**
         * Sets the target type.
         * @param t the target type of this builder
         * @return the current builder
         */
        Builder<T> target(final Class<? extends Field<T>> t) {
            this.target = t;
            return this;
        }

        /**
         * Builds the operation item.
         * @return the operation item field of the specified type
         */
        Field<T> build() {
            Field<T> field;
            if (target == null) {
                field = new Field<>(name, length, optional, shorter);
            } else {
                try {
                    field = target.getDeclaredConstructor(String.class, boolean.class).newInstance(name, optional);
                } catch (NoSuchMethodException e) {
                    try {
                        field =
                                target.getDeclaredConstructor(String.class, int.class, boolean.class, boolean.class)
                                        .newInstance(name, length, optional, shorter);
                    } catch (
                            NoSuchMethodException
                                    | IllegalAccessException
                                    | InstantiationException
                                    | InvocationTargetException ex) {
                        throw new IllegalStateException(ex);
                    }
                } catch (
                        IllegalAccessException
                                | InstantiationException
                                | InvocationTargetException e) {
                    throw new IllegalStateException(e);
                }
            }

            return field;
        }
    }
}

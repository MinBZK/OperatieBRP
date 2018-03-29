/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

/**
 * Basic representation of an operation item field.
 * @param <T> the type of the field
 */
class Field<T> implements OperationItem<T> {
    private final String name;
    private final int length;
    private final boolean optional;
    private final boolean shorter;
    private T value;

    /**
     * Constructor.
     * @param name name of the field
     * @param length length of the field
     * @param optional whether the field may be empty or not
     * @param shorter whether the length of the value of the field may be shorter than the specified length
     */
    Field(final String name, final int length, final boolean optional, final boolean shorter) {
        this.name = name;
        this.length = length;
        this.optional = optional;
        this.shorter = shorter;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public boolean isOptional() {
        return optional;
    }

    @Override
    public boolean isMandatory() {
        return !isOptional();
    }

    @Override
    public boolean mayBeShorter() {
        return shorter;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(final T val) {
        value = val;
    }

    @Override
    public void setRawValue(final String val) {
        throw new IllegalStateException("setRawValue not implemented for generic Field class.");
    }

    @Override
    public boolean isEmpty() {
        return getValue() == null;
    }

    @Override
    public boolean validate() {
        if (isMandatory() && isEmpty()) {
            throw new IllegalStateException(String.format("field %s is mandatory and empty", name()));
        }

        return true;
    }

    @Override
    public String toSpdString() {
        validate();

        if (isOptional() && isEmpty()) {
            return OperationItem.paddedValueOfLength(length());
        } else {
            return OperationItem.paddedValueOfLength(getValue().toString(), length());
        }
    }
}

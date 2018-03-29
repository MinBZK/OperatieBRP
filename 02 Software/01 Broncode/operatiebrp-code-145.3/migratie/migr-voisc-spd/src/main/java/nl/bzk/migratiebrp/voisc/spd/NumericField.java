/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import org.springframework.util.Assert;

/**
 * Numeric field.
 */
final class NumericField extends Field<Integer> {

    /**
     * Constructor.
     * @param name name of the field
     * @param length length of the field
     * @param optional whether the field may be empty or not
     * @param mayBeShorter whether the length of the value of the field may be shorter than the specified length
     */
    NumericField(final String name, final int length, final boolean optional, final boolean mayBeShorter) {
        super(name, length, optional, mayBeShorter);
    }

    /**
     * Returns a builder for building numeric fields.
     * @return a builder for building numeric fields
     */
    public static Builder<Integer> builder() {
        return OperationItem.<Integer>builder().target(NumericField.class);
    }

    @Override
    public void setValue(final Integer val) {
        Assert.isTrue(isOptional() || (isMandatory() && val != null && val > 0), String.format("%s should be a positive number, but is: %d", name(), val));
        super.setValue(val);
    }

    @Override
    public void setRawValue(final String val) {
        Assert.isTrue(isOptional() || (val != null && !val.trim().isEmpty()), String.format("value %s for field %s is invalid", val, name()));
        if (val != null && !val.trim().isEmpty()) {
            final Integer converted = Integer.valueOf(val.trim());
            Assert.isTrue(
                    isOptional() || (isMandatory() && converted > 0), String.format("%s should be a positive number, but is: %d", name(), converted));
            super.setValue(converted);
        }
    }

    @Override
    public String toSpdString() {
        validate();

        if (isOptional() && isEmpty()) {
            return OperationItem.paddedValueOfLength(length());
        } else {
            return String.format(String.format("%%0%dd", length()), getValue());
        }
    }
}

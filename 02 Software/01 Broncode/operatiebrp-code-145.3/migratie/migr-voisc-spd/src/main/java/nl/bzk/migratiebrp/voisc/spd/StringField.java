/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import org.springframework.util.Assert;

/**
 * String field.
 */
final class StringField extends Field<String> {

    /**
     * Constructor.
     * @param name name of the field
     * @param length length of the field
     * @param optional whether the field may be empty or not
     * @param mayBeShorter whether the length of the value of the field may be shorter than the specified length
     */
    StringField(final String name, final int length, final boolean optional, final boolean mayBeShorter) {
        super(name, length, optional, mayBeShorter);
    }

    /**
     * Returns a builder for building string fields.
     * @return a builder for building string fields
     */
    public static OperationItem.Builder<String> builder() {
        return OperationItem.<String>builder().target(StringField.class);
    }

    @Override
    public void setRawValue(final String raw) {
        Assert.isTrue(isOptional() || (raw != null && !raw.trim().isEmpty()), String.format("%s should have length %d", name(), length()));
        if (mayBeShorter()) {
            Assert.isTrue(isOptional() || (raw != null && raw.length() <= length()), String.format("%s should have maximum length %d", name(), length()));
        } else {
            Assert.isTrue(isOptional() || (raw != null && raw.length() == length()), String.format("%s should have length %d", name(), length()));
        }
        setValue(raw == null ? null : raw.trim());
    }
}

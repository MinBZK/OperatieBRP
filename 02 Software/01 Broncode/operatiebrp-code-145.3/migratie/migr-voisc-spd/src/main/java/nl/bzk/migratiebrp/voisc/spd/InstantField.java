/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.time.Instant;
import org.springframework.util.Assert;

/**
 * Field describing Instant values.
 */
final class InstantField extends Field<Instant> {

    private static final int FIELD_LENGTH = 11;

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    InstantField(final String name, final boolean optional) {
        super(name, FIELD_LENGTH, optional, false);
    }

    /**
     * Returns a builder for building instant fields.
     * @return a builder for building instant fields
     */
    public static Builder<Instant> builder() {
        return OperationItem.<Instant>builder().target(InstantField.class);
    }

    @Override
    public void setRawValue(final String raw) {
        if (isOptional() && (raw == null || raw.trim().isEmpty())) {
            setValue(null);
        } else {
            Assert.isTrue(raw != null && raw.length() == length(), String.format("value %s for field %s should have length %d", raw, name(), length()));
            setValue(SpdTimeConverter.convertSpdTimeStringToInstant(raw));
        }
    }

    @Override
    public String toSpdString() {
        validate();

        if (isOptional() && isEmpty()) {
            return OperationItem.paddedValueOfLength(length());
        } else {
            return SpdTimeConverter.convertInstantToSpdTimeString(getValue());
        }
    }
}

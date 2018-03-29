/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;
import org.springframework.util.Assert;

/**
 * Field describing Priority enumeration values.
 */
final class PriorityField extends Field<SpdConstants.Priority> {

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    PriorityField(final String name, final boolean optional) {
        super(name, 1, optional, false);
    }

    /**
     * Returns a builder for building Priority fields.
     * @return a builder for building Priority fields
     */
    public static OperationItem.Builder<SpdConstants.Priority> builder() {
        return OperationItem.<SpdConstants.Priority>builder().target(PriorityField.class).name("Priority");
    }

    @Override
    public void setRawValue(final String raw) {
        if (isOptional() && (raw == null || raw.trim().isEmpty())) {
            setValue(SpdConstants.Priority.defaultValue());
        } else {
            Assert.isTrue(raw != null && !raw.trim().isEmpty(), String.format("field %s should not be null or empty", name()));
            Assert.isTrue(raw != null && raw.length() == length(), String.format("value %s for field %s should have length %d", raw, name(), length()));

            if (raw != null) {
                final Optional<SpdConstants.Priority> optional = SpdConstants.Priority.fromCode(Integer.parseInt(raw));
                if (optional.isPresent()) {
                    setValue(optional.get());
                } else {
                    throw new IllegalArgumentException(String.format("illegal priority: %s", raw));
                }
            }
        }
    }

    @Override
    public String toSpdString() {
        validate();

        if (isOptional() && isEmpty()) {
            return OperationItem.paddedValueOfLength(length());
        } else {
            return String.valueOf(getValue().code());
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import org.springframework.util.Assert;

abstract class AbstractEnumField<T extends SpdConstants.CodeEnum> extends Field<T> implements EnumField<T> {
    AbstractEnumField(final String name, final int length, final boolean optional, final boolean shorter) {
        super(name, length, optional, shorter);
    }

    @Override
    public void setRawValue(final String raw) {
        if (isOptional() && (raw == null || raw.trim().isEmpty())) {
            setValue(defaultEnum());
        } else {
            Assert.isTrue(raw != null && !raw.trim().isEmpty(), String.format("field %s should not be null or empty", name()));
            Assert.isTrue(raw != null && raw.length() == length(), String.format("value %s for field %s should have length %d", raw, name(), length()));
            setValue(enumForCode(raw).orElseThrow(() -> new IllegalArgumentException(String.format("illegal enum value: %s", raw))));
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

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;

/**
 * Field describing NonReceiptReason enumeration values.
 */
final class NonReceiptReasonField extends AbstractEnumField<SpdConstants.NonReceiptReason> {

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    NonReceiptReasonField(final String name, final boolean optional) {
        super(name, 1, optional, false);
    }

    /**
     * Returns a builder for building NonReceiptReason fields.
     * @return a builder for building NonReceiptReason fields
     */
    public static Builder<SpdConstants.NonReceiptReason> builder() {
        return OperationItem.<SpdConstants.NonReceiptReason>builder().target(NonReceiptReasonField.class).name("NonReceiptReason");
    }

    @Override
    public SpdConstants.NonReceiptReason defaultEnum() {
        return null;
    }

    @Override
    public Optional<SpdConstants.NonReceiptReason> enumForCode(final String raw) {
        return SpdConstants.NonReceiptReason.fromCode(Integer.parseInt(raw));
    }
}

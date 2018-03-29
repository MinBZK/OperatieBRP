/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;

/**
 * Field describing Attention enumeration values.
 */
final class AttentionField extends AbstractEnumField<SpdConstants.Attention> {

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    AttentionField(final String name, final boolean optional) {
        super(name, 1, optional, false);
    }

    /**
     * Returns a builder for building attention fields.
     * @return a builder for building attention fields
     */
    public static OperationItem.Builder<SpdConstants.Attention> builder() {
        return OperationItem.<SpdConstants.Attention>builder().target(AttentionField.class).name("Attention");
    }

    @Override
    public SpdConstants.Attention defaultEnum() {
        return null;
    }

    @Override
    public Optional<SpdConstants.Attention> enumForCode(final String raw) {
        return SpdConstants.Attention.fromCode(Integer.parseInt(raw));
    }
}

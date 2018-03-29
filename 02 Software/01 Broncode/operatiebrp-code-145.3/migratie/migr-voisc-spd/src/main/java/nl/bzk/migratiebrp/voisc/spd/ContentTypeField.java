/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;

/**
 * Field describing ContentType enumeration values.
 */
final class ContentTypeField extends AbstractEnumField<SpdConstants.ContentType> {

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    ContentTypeField(final String name, final boolean optional) {
        super(name, 1, optional, false);
    }

    /**
     * Returns a builder for building content type fields.
     * @return a builder for building content type fields
     */
    public static OperationItem.Builder<SpdConstants.ContentType> builder() {
        return OperationItem.<SpdConstants.ContentType>builder().target(ContentTypeField.class).name("Content Type");
    }

    @Override
    public SpdConstants.ContentType defaultEnum() {
        return SpdConstants.ContentType.defaultValue();
    }

    @Override
    public Optional<SpdConstants.ContentType> enumForCode(final String raw) {
        return SpdConstants.ContentType.fromCode(Integer.parseInt(raw));
    }
}

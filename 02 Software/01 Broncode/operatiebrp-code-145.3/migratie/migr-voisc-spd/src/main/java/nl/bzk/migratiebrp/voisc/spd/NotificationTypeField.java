/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;

/**
 * Field describing NotificationType enumeration values.
 */
final class NotificationTypeField extends AbstractEnumField<SpdConstants.NotificationType> {

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    NotificationTypeField(final String name, final boolean optional) {
        super(name, 1, optional, false);
    }

    /**
     * Returns a builder for building NotificationType fields.
     * @return a builder for building NotificationType fields
     */
    public static OperationItem.Builder<SpdConstants.NotificationType> builder() {
        return OperationItem.<SpdConstants.NotificationType>builder().target(NotificationTypeField.class).name("NotificationType");
    }

    @Override
    public SpdConstants.NotificationType defaultEnum() {
        return null;
    }

    @Override
    public Optional<SpdConstants.NotificationType> enumForCode(final String raw) {
        return SpdConstants.NotificationType.fromCode(Integer.parseInt(raw));
    }
}

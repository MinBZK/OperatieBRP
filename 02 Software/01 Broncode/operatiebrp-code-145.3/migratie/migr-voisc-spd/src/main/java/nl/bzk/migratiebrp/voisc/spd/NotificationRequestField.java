/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;

/**
 * Field describing NotificationRequest enumeration values.
 */
final class NotificationRequestField extends AbstractEnumField<SpdConstants.NotificationRequest> {

    /**
     * Constructor.
     * @param name field name. Used in error messages.
     * @param optional whether this field is optional or not.
     */
    NotificationRequestField(final String name, final boolean optional) {
        super(name, 1, optional, false);
    }

    /**
     * Returns a builder for building NotificationRequest fields.
     * @return a builder for building NotificationRequest fields
     */
    public static OperationItem.Builder<SpdConstants.NotificationRequest> builder() {
        return OperationItem.<SpdConstants.NotificationRequest>builder().target(NotificationRequestField.class).name("NotificationRequest");
    }

    @Override
    public SpdConstants.NotificationRequest defaultEnum() {
        return SpdConstants.NotificationRequest.defaultValue();
    }

    @Override
    public Optional<SpdConstants.NotificationRequest> enumForCode(final String raw) {
        return SpdConstants.NotificationRequest.fromCode(Integer.parseInt(raw));
    }
}

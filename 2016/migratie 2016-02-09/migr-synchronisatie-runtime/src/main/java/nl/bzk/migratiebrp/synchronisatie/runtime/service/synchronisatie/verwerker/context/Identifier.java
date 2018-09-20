/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Identifier.
 */
public final class Identifier {
    private final Object[] objects;

    /**
     * Constructor.
     *
     * @param objects
     *            identifiers
     */
    public Identifier(final Object... objects) {
        this.objects = objects;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Identifier)) {
            return false;
        }
        final Identifier castOther = (Identifier) other;
        return new EqualsBuilder().append(objects, castOther.objects).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(objects).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("objects", objects).toString();
    }
}

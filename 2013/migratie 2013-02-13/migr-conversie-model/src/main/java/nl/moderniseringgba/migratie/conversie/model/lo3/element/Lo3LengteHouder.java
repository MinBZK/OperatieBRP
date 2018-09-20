/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.element;

import java.io.Serializable;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Element;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert het LO3 Element 'Lengte Houder'.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3LengteHouder implements Lo3Element, Serializable {
    private static final long serialVersionUID = 1L;

    @Text
    private final int lengte;

    /**
     * Maakt een Lo3LengteHouder object.
     * 
     * @param lengte
     *            de LO3 Lengte houder
     */
    public Lo3LengteHouder(@Text final int lengte) {
        this.lengte = lengte;
    }

    /**
     * @return de LO3 lengte van de houder.
     */
    public int getLengte() {
        return lengte;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3LengteHouder)) {
            return false;
        }
        final Lo3LengteHouder castOther = (Lo3LengteHouder) other;
        return new EqualsBuilder().append(lengte, castOther.lengte).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(lengte).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("lengte", lengte).toString();
    }

}

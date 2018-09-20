/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp;

import java.io.Serializable;

import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Verhuis antwoord.
 */
public abstract class BrpBijhoudingAntwoordBericht extends AbstractBrpBericht implements BrpAntwoordBericht,
        Serializable {

    private static final String BERICHT_TYPE_ELEMENT = "berichtType";
    private static final String STATUS_ELEMENT = "status";
    private static final String TOELICHTING_ELEMENT = "toelichting";

    private static final long serialVersionUID = 1L;

    /* ************************************************************************************************************* */

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * 
     * @return De status {@link StatusType} van het bericht.
     */
    public abstract StatusType getStatus();

    /**
     * Geeft de toelichting van het bericht terug.
     * 
     * @return De toelichting van het bericht.
     */
    public abstract String getToelichting();

    /* ************************************************************************************************************* */

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBijhoudingAntwoordBericht)) {
            return false;
        }
        final BrpBijhoudingAntwoordBericht castOther = (BrpBijhoudingAntwoordBericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other))
                .append(getBerichtType(), castOther.getBerichtType()).append(getStatus(), castOther.getStatus())
                .append(getToelichting(), castOther.getToelichting()).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(getBerichtType()).append(getStatus())
                .append(getToelichting()).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(BERICHT_TYPE_ELEMENT, getBerichtType()).append(STATUS_ELEMENT, getStatus())
                .append(TOELICHTING_ELEMENT, getToelichting()).toString();
    }
}

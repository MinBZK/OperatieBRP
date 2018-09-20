/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Combinatie BrpRedenVerkrijgingNederlandschapCode en BrpRedenVerliesNederlandschapCode.
 */
public final class RedenVerkrijgingVerliesPaar {

    private final BrpRedenVerkrijgingNederlandschapCode verkrijging;
    private final BrpRedenVerliesNederlandschapCode verlies;

    /**
     * Maakt een RedenVerkrijgingVerliesPaar.
     * 
     * @param verkrijging
     *            de BrpRedenVerkrijgingNederlandschapCode
     * @param verlies
     *            de BrpRedenVerliesNederlandschapCode
     * @throws NullPointerException
     *             als beide argumenten null zijn
     */
    public RedenVerkrijgingVerliesPaar(
            final BrpRedenVerkrijgingNederlandschapCode verkrijging,
            final BrpRedenVerliesNederlandschapCode verlies) {
        if (verkrijging == null && verlies == null) {
            throw new NullPointerException("Reden verkrijging en reden verlies mogen niet allebei ontbreken");
        }
        this.verkrijging = verkrijging;
        this.verlies = verlies;
    }

    /**
     * @return de BrpRedenVerkrijgingNederlandschapCode
     */
    public BrpRedenVerkrijgingNederlandschapCode getVerkrijging() {
        return verkrijging;
    }

    /**
     * @return de BrpRedenVerliesNederlandschapCode
     */
    public BrpRedenVerliesNederlandschapCode getVerlies() {
        return verlies;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RedenVerkrijgingVerliesPaar)) {
            return false;
        }
        final RedenVerkrijgingVerliesPaar castOther = (RedenVerkrijgingVerliesPaar) other;
        return new EqualsBuilder().append(verkrijging, castOther.verkrijging).append(verlies, castOther.verlies)
                .isEquals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(verkrijging).append(verlies).toHashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("verkrijging", verkrijging)
                .append("verlies", verlies).toString();
    }

}

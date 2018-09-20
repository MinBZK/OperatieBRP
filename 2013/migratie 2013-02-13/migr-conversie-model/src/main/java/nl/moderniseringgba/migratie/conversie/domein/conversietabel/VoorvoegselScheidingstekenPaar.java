/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Combinatie voorvoegsel en scheidingsteken in de BRP die mapt op het LO3 voorvoegsel.
 */
public final class VoorvoegselScheidingstekenPaar {

    private final String voorvoegsel;
    private final Character scheidingsteken;

    /**
     * @param voorvoegsel
     *            het BRP voorvoegsel
     * @param scheidingsteken
     *            het BRP scheidingsteken
     */
    public VoorvoegselScheidingstekenPaar(final String voorvoegsel, final Character scheidingsteken) {
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * @return het voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * @return het scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VoorvoegselScheidingstekenPaar)) {
            return false;
        }
        final VoorvoegselScheidingstekenPaar castOther = (VoorvoegselScheidingstekenPaar) other;
        return new EqualsBuilder().append(voorvoegsel, castOther.voorvoegsel)
                .append(scheidingsteken, castOther.scheidingsteken).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(voorvoegsel).append(scheidingsteken).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("voorvoegsel", voorvoegsel)
                .append("scheidingsteken", scheidingsteken).toString();
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Het paar van BRP codes die mappen op de LO3 Aangifte Adreshouding Code.
 */
public final class AangeverAdreshoudingRedenWijzigingAdresPaar {

    private final BrpAangeverAdreshoudingCode brpAangeverAdreshoudingCode;
    private final BrpRedenWijzigingAdresCode brpRedenWijzigingAdresCode;

    /**
     * Maakt een AangeverAdreshoudingRedenWijzigingAdresPaar object.
     * 
     * @param brpAangeverAdreshoudingCode
     *            de aangever adreshouding code in de BRP
     * @param brpRedenWijzigingAdresCode
     *            de reden wijzging adres code in de BRP
     */
    public AangeverAdreshoudingRedenWijzigingAdresPaar(
            final BrpAangeverAdreshoudingCode brpAangeverAdreshoudingCode,
            final BrpRedenWijzigingAdresCode brpRedenWijzigingAdresCode) {
        this.brpAangeverAdreshoudingCode = brpAangeverAdreshoudingCode;
        this.brpRedenWijzigingAdresCode = brpRedenWijzigingAdresCode;
    }

    public BrpAangeverAdreshoudingCode getBrpAangeverAdreshoudingCode() {
        return brpAangeverAdreshoudingCode;
    }

    public BrpRedenWijzigingAdresCode getBrpRedenWijzigingAdresCode() {
        return brpRedenWijzigingAdresCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AangeverAdreshoudingRedenWijzigingAdresPaar)) {
            return false;
        }

        final AangeverAdreshoudingRedenWijzigingAdresPaar castOther =
                (AangeverAdreshoudingRedenWijzigingAdresPaar) other;

        return isGelijk(brpAangeverAdreshoudingCode, castOther.brpAangeverAdreshoudingCode)
                && isGelijk(brpRedenWijzigingAdresCode, castOther.brpRedenWijzigingAdresCode);
    }

    private static boolean isGelijk(final Object o1, final Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(brpAangeverAdreshoudingCode).append(brpRedenWijzigingAdresCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("brpAangeverAdreshoudingCode", brpAangeverAdreshoudingCode)
                .append("brpRedenWijzigingAdresCode", brpRedenWijzigingAdresCode).toString();
    }

}

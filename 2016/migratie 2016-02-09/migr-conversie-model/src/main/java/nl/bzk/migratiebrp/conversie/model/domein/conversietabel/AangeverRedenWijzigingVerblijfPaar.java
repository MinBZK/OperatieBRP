/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Het paar van BRP codes die mappen op de LO3 Aangifte Adreshouding Code.
 */
public final class AangeverRedenWijzigingVerblijfPaar {

    private final BrpAangeverCode brpAangeverCode;
    private final BrpRedenWijzigingVerblijfCode brpRedenWijzigingVerblijfCode;

    /**
     * Maakt een AangeverRedenWijzigingVerblijfPaar object.
     * 
     * @param brpAangeverCode
     *            de aangever code in de BRP
     * @param brpRedenWijzigingVerblijfCode
     *            de reden wijzging verblijf code in de BRP
     */
    public AangeverRedenWijzigingVerblijfPaar(
        final BrpAangeverCode brpAangeverCode,
        final BrpRedenWijzigingVerblijfCode brpRedenWijzigingVerblijfCode)
    {
        this.brpAangeverCode = brpAangeverCode;
        this.brpRedenWijzigingVerblijfCode = brpRedenWijzigingVerblijfCode;
    }

    /**
     * Geef de waarde van brp aangever code.
     *
     * @return brp aangever code
     */
    public BrpAangeverCode getBrpAangeverCode() {
        return brpAangeverCode;
    }

    /**
     * Geef de waarde van brp reden wijziging verblijf code.
     *
     * @return brp reden wijziging verblijf code
     */
    public BrpRedenWijzigingVerblijfCode getBrpRedenWijzigingVerblijfCode() {
        return brpRedenWijzigingVerblijfCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AangeverRedenWijzigingVerblijfPaar)) {
            return false;
        }

        final AangeverRedenWijzigingVerblijfPaar castOther = (AangeverRedenWijzigingVerblijfPaar) other;

        return isGelijk(brpAangeverCode, castOther.brpAangeverCode)
               && isGelijk(brpRedenWijzigingVerblijfCode, castOther.brpRedenWijzigingVerblijfCode);
    }

    private static boolean isGelijk(final Object o1, final Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(brpAangeverCode).append(brpRedenWijzigingVerblijfCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("brpAangeverCode", brpAangeverCode)
                                                                          .append(
                                                                              "brpRedenWijzigingVerblijfCode",
                                                                              brpRedenWijzigingVerblijfCode)
                                                                          .toString();
    }

}

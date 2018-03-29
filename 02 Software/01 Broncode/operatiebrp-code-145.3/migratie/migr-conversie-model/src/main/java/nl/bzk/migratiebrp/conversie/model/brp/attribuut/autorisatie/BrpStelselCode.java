/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Text;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuut;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze enum representeert een BRP Stelsel.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpStelselCode implements BrpAttribuut, Comparable<BrpStelselCode> {

    /**
     * BRP.
     */
    public static final BrpStelselCode BRP = new BrpStelselCode((short) 1);
    /**
     * GBA.
     */
    public static final BrpStelselCode GBA = new BrpStelselCode((short) 2);

    @Text
    private final short code;

    /**
     * Maakt een BrpAandMediumCode.
     * @param brpStelselCode BRP code
     */
    public BrpStelselCode(@Text final short brpStelselCode) {
        code = brpStelselCode;
    }

    /**
     * Geef de waarde van code.
     * @return the code
     */
    public short getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpStelselCode)) {
            return false;
        }
        final BrpStelselCode castOther = (BrpStelselCode) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
    }

    @Override
    public int compareTo(final BrpStelselCode o) {
        return new CompareToBuilder().append(code, o.code).build();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("code", code).toString();
    }

}

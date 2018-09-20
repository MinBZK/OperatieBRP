/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuut;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze enum representeert een BRP Effect afnemerindicaties.
 * 
 * Deze class is immutable en threadsafe.
 */
public final class BrpEffectAfnemerindicatiesCode implements BrpAttribuut, Comparable<BrpEffectAfnemerindicatiesCode> {

    /** Plaatsen. */
    public static final BrpEffectAfnemerindicatiesCode PLAATSEN = new BrpEffectAfnemerindicatiesCode((short) 1);
    /** Verwijderen. */
    public static final BrpEffectAfnemerindicatiesCode VERWIJDEREN = new BrpEffectAfnemerindicatiesCode((short) 2);

    @Text
    private final short code;

    /**
     * Maakt een BRP Effect afnemerindicaties.
     * 
     * @param brpCode
     *            BRP code
     */
    public BrpEffectAfnemerindicatiesCode(@Text final short brpCode) {
        code = brpCode;
    }

    /**
     * Geef de waarde van code.
     *
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
        if (!(other instanceof BrpEffectAfnemerindicatiesCode)) {
            return false;
        }
        final BrpEffectAfnemerindicatiesCode castOther = (BrpEffectAfnemerindicatiesCode) other;
        return new EqualsBuilder().append(getCode(), castOther.getCode()).isEquals();
    }

    @Override
    public int compareTo(final BrpEffectAfnemerindicatiesCode o) {
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

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAttribuut;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze enum representeert een BRP Protocolleringsniveau.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpProtocolleringsniveauCode implements BrpAttribuut {

    /** Geen beperkingen. */
    public static final BrpProtocolleringsniveauCode GEEN_BEPERKINGEN = new BrpProtocolleringsniveauCode("0");
    /** Geheim. */
    public static final BrpProtocolleringsniveauCode GEHEIM = new BrpProtocolleringsniveauCode("2");

    @Text
    private final String code;

    /**
     * Maakt een BrpProtocolleringsniveauCode.
     *
     * @param brpCode
     *            BRP code
     */
    public BrpProtocolleringsniveauCode(@Text final String brpCode) {
        code = brpCode;
    }

    /**
     * Geef de waarde van code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpProtocolleringsniveauCode)) {
            return false;
        }
        final BrpProtocolleringsniveauCode castOther = (BrpProtocolleringsniveauCode) other;
        return new EqualsBuilder().append(code, castOther.code).isEquals();
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

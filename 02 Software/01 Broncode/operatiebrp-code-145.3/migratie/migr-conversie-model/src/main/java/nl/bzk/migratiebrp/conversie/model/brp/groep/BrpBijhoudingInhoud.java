/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP Bijhouding.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpBijhoudingInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "bijhoudingspartijCode", required = true)
    private final BrpPartijCode bijhoudingspartijCode;

    @Element(name = "bijhoudingsaardCode", required = true)
    private final BrpBijhoudingsaardCode bijhoudingsaardCode;

    @Element(name = "nadereBijhoudingsaardCode", required = true)
    private final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode;

    /**
     * Maakt een BrpBijhoudingInhoud object.
     * @param bijhoudingspartijCode bijhoudingspartij
     * @param bijhoudingsaardCode bijhoudingsaardCode
     * @param nadereBijhoudingsaardCode nadereBijhoudingsaardCode
     */
    public BrpBijhoudingInhoud(
            @Element(name = "bijhoudingspartijCode", required = true) final BrpPartijCode bijhoudingspartijCode,
            @Element(name = "bijhoudingsaardCode", required = true) final BrpBijhoudingsaardCode bijhoudingsaardCode,
            @Element(name = "nadereBijhoudingsaardCode", required = true) final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode) {
        this.bijhoudingspartijCode = bijhoudingspartijCode;
        this.bijhoudingsaardCode = bijhoudingsaardCode;
        this.nadereBijhoudingsaardCode = nadereBijhoudingsaardCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    /**
     * Geef de waarde van bijhoudingspartij code van BrpBijhoudingInhoud.
     * @return de waarde van bijhoudingspartij code van BrpBijhoudingInhoud
     */
    public BrpPartijCode getBijhoudingspartijCode() {
        return bijhoudingspartijCode;
    }

    /**
     * Geef de waarde van bijhoudingsaard code van BrpBijhoudingInhoud.
     * @return de waarde van bijhoudingsaard code van BrpBijhoudingInhoud
     */
    public BrpBijhoudingsaardCode getBijhoudingsaardCode() {
        return bijhoudingsaardCode;
    }

    /**
     * Geef de waarde van nadere bijhoudingsaard code van BrpBijhoudingInhoud.
     * @return de waarde van nadere bijhoudingsaard code van BrpBijhoudingInhoud
     */
    public BrpNadereBijhoudingsaardCode getNadereBijhoudingsaardCode() {
        return nadereBijhoudingsaardCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpBijhoudingInhoud)) {
            return false;
        }
        final BrpBijhoudingInhoud castOther = (BrpBijhoudingInhoud) other;
        return new EqualsBuilder().append(bijhoudingspartijCode, castOther.bijhoudingspartijCode)
                .append(bijhoudingsaardCode, castOther.bijhoudingsaardCode)
                .append(nadereBijhoudingsaardCode, castOther.nadereBijhoudingsaardCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bijhoudingspartijCode)
                .append(bijhoudingsaardCode)
                .append(nadereBijhoudingsaardCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("bijhoudingspartijCode", bijhoudingspartijCode)
                .append("bijhoudingsaardCode", bijhoudingsaardCode)
                .append("redenOpschortingBijhoudingCode", nadereBijhoudingsaardCode)
                .toString();
    }
}

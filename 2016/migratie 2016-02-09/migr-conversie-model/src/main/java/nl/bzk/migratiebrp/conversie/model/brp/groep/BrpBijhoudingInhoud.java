/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP Bijhouding.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpBijhoudingInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "bijhoudingspartijCode", required = true)
    private final BrpPartijCode bijhoudingspartijCode;

    @Element(name = "bijhoudingsaardCode", required = true)
    private final BrpBijhoudingsaardCode bijhoudingsaardCode;

    @Element(name = "nadereBijhoudingsaardCode", required = true)
    private final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode;

    @Element(name = "indicatieOnverwerktDocumentAanwezig", required = true)
    private final BrpBoolean indicatieOnverwerktDocumentAanwezig;

    /**
     * Maakt een BrpBijhoudingInhoud object.
     * 
     * @param bijhoudingspartijCode
     *            bijhoudingspartij
     * @param bijhoudingsaardCode
     *            bijhoudingsaardCode
     * @param nadereBijhoudingsaardCode
     *            nadereBijhoudingsaardCode
     * @param indicatieOnverwerktDocumentAanwezig
     *            indicatie onverwerkt document aanwezig
     */
    public BrpBijhoudingInhoud(
        @Element(name = "bijhoudingspartijCode", required = true) final BrpPartijCode bijhoudingspartijCode,
        @Element(name = "bijhoudingsaardCode", required = true) final BrpBijhoudingsaardCode bijhoudingsaardCode,
        @Element(name = "nadereBijhoudingsaardCode", required = true) final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode,
        @Element(name = "indicatieOnverwerktDocumentAanwezig", required = true) final BrpBoolean indicatieOnverwerktDocumentAanwezig)
    {
        this.bijhoudingspartijCode = bijhoudingspartijCode;
        this.bijhoudingsaardCode = bijhoudingsaardCode;
        this.nadereBijhoudingsaardCode = nadereBijhoudingsaardCode;
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }

    /* (non-Javadoc)
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return false;
    }

    /**
     * Geef de waarde van bijhoudingspartij code.
     *
     * @return bijhoudingspartij code
     */
    public BrpPartijCode getBijhoudingspartijCode() {
        return bijhoudingspartijCode;
    }

    /**
     * Geef de waarde van bijhoudingsaard code.
     *
     * @return the bijhoudingsaardcode
     */
    public BrpBijhoudingsaardCode getBijhoudingsaardCode() {
        return bijhoudingsaardCode;
    }

    /**
     * Geef de waarde van nadere bijhoudingsaard code.
     *
     * @return the redenOpschortingBijhoudingCode
     */
    public BrpNadereBijhoudingsaardCode getNadereBijhoudingsaardCode() {
        return nadereBijhoudingsaardCode;
    }

    /**
     * Geef de waarde van indicatie onverwerkt document aanwezig.
     *
     * @return indicatie onverwerkt document aanwezig
     */
    public BrpBoolean getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
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
                                  .append(indicatieOnverwerktDocumentAanwezig, castOther.indicatieOnverwerktDocumentAanwezig)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(bijhoudingspartijCode)
                                    .append(bijhoudingsaardCode)
                                    .append(nadereBijhoudingsaardCode)
                                    .append(indicatieOnverwerktDocumentAanwezig)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("bijhoudingspartijCode", bijhoudingspartijCode)
                                                                          .append("bijhoudingsaardCode", bijhoudingsaardCode)
                                                                          .append(
                                                                              "redenOpschortingBijhoudingCode",
                                                                              nadereBijhoudingsaardCode)
                                                                          .append(
                                                                              "onverwerktDocumentAanwezig",
                                                                              indicatieOnverwerktDocumentAanwezig)
                                                                          .toString();
    }
}

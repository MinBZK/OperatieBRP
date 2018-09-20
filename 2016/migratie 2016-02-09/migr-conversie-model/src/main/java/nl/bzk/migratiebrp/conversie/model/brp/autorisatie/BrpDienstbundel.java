/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert het BRP objecttype Dienstbundel.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpDienstbundel {

    @ElementList(name = "diensten", entry = "dienst", type = BrpDienst.class, required = false)
    private final List<BrpDienst> diensten;
    @ElementList(name = "lo3rubrieken", entry = "lo3Rubriek", type = BrpDienstbundelLo3Rubriek.class, required = false)
    private final List<BrpDienstbundelLo3Rubriek> lo3Rubrieken;
    @Element(name = "dienstbundelStapel", required = false)
    private final BrpStapel<BrpDienstbundelInhoud> dienstbundelStapel;

    /**
     * Maak een nieuw BrpDienstBundel object.
     *
     * @param diensten
     *            de diensten
     * @param lo3Rubrieken
     *            de lo3Rubrieken
     * @param dienstbundelStapel
     *            de dienstbundel stapels
     */
    public BrpDienstbundel(
        @ElementList(name = "diensten", entry = "dienst", type = BrpDienst.class, required = false) final List<BrpDienst> diensten,
        @ElementList(name = "lo3rubrieken", entry = "lo3Rubriek", type = BrpDienstbundelLo3Rubriek.class, required = false) final List<BrpDienstbundelLo3Rubriek> lo3Rubrieken,
        @Element(name = "dienstbundelStapel", required = false) final BrpStapel<BrpDienstbundelInhoud> dienstbundelStapel)
    {
        super();
        this.diensten = diensten;
        this.lo3Rubrieken = lo3Rubrieken;
        this.dienstbundelStapel = dienstbundelStapel;
    }

    /**
     * Geef de waarde van diensten.
     *
     * @return diensten
     */
    public List<BrpDienst> getDiensten() {
        return diensten;
    }

    /**
     * Geef de waarde van lo3rubrieken.
     *
     * @return lo3rubrieken
     */
    public List<BrpDienstbundelLo3Rubriek> getLo3Rubrieken() {
        return lo3Rubrieken;
    }

    /**
     * Geef de waarde van dienstbundel stapel.
     *
     * @return dienstbundel stapel
     */
    public BrpStapel<BrpDienstbundelInhoud> getDienstbundelStapel() {
        return dienstbundelStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundel)) {
            return false;
        }
        final BrpDienstbundel castOther = (BrpDienstbundel) other;
        return new EqualsBuilder().append(diensten, castOther.diensten)
                                  .append(lo3Rubrieken, castOther.lo3Rubrieken)
                                  .append(dienstbundelStapel, castOther.dienstbundelStapel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(diensten).append(lo3Rubrieken).append(dienstbundelStapel).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("diensten", diensten)
                                                                          .append("lo3Rubrieken", lo3Rubrieken)
                                                                          .append("dienstBundelStapel", dienstbundelStapel)
                                                                          .toString();
    }

}

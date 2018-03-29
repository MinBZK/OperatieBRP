/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Migratie representatie voor een dienstbundel (BRP inhoud, LO3 historie).
 *
 * Deze class is immutable en thread-safe.
 */
public final class TussenDienstbundel {

    @ElementList(name = "diensten", entry = "dienst", type = TussenDienst.class, required = false)
    private final List<TussenDienst> diensten;
    @ElementList(name = "lo3rubrieken", entry = "lo3Rubriek", type = TussenDienstbundelLo3Rubriek.class, required = false)
    private final List<TussenDienstbundelLo3Rubriek> lo3Rubrieken;
    @Element(name = "dienstbundelStapel", required = false)
    private final TussenStapel<BrpDienstbundelInhoud> dienstbundelStapel;

    /**
     * Maak een nieuw TussenDienstBundel object.
     * @param diensten de diensten
     * @param lo3Rubrieken de lo3Rubrieken
     * @param dienstbundelStapel de dienstbundel stapels
     */
    public TussenDienstbundel(
            @ElementList(name = "diensten", entry = "dienst", type = TussenDienst.class, required = false) final List<TussenDienst> diensten,
            @ElementList(name = "lo3rubrieken", entry = "lo3Rubriek", type = TussenDienstbundelLo3Rubriek.class,
                    required = false) final List<TussenDienstbundelLo3Rubriek> lo3Rubrieken,
            @Element(name = "dienstbundelStapel", required = false) final TussenStapel<BrpDienstbundelInhoud> dienstbundelStapel) {
        super();
        this.diensten = diensten;
        this.lo3Rubrieken = lo3Rubrieken;
        this.dienstbundelStapel = dienstbundelStapel;
    }

    /**
     * Geef de waarde van diensten van TussenDienstbundel.
     * @return de waarde van diensten van TussenDienstbundel
     */
    public List<TussenDienst> getDiensten() {
        return diensten;
    }

    /**
     * Geef de waarde van lo3 rubrieken van TussenDienstbundel.
     * @return de waarde van lo3 rubrieken van TussenDienstbundel
     */
    public List<TussenDienstbundelLo3Rubriek> getLo3Rubrieken() {
        return lo3Rubrieken;
    }

    /**
     * Geef de waarde van dienstbundel stapel van TussenDienstbundel.
     * @return de waarde van dienstbundel stapel van TussenDienstbundel
     */
    public TussenStapel<BrpDienstbundelInhoud> getDienstbundelStapel() {
        return dienstbundelStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenDienstbundel)) {
            return false;
        }
        final TussenDienstbundel castOther = (TussenDienstbundel) other;
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

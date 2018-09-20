/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpSoortDienstCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Migratie representatie voor een dienst (BRP inhoud, LO3 historie).
 *
 * Deze class is immutable en thread-safe.
 */
public final class TussenDienst {

    @Element(name = "effectAfnemersindicatie", required = false)
    private final BrpEffectAfnemerindicatiesCode effectAfnemersindicatie;
    @Element(name = "soort", required = false)
    private final BrpSoortDienstCode soortDienstCode;
    @Element(name = "dienstStapel", required = false)
    private final TussenStapel<BrpDienstInhoud> dienstStapel;
    @Element(name = "dienstAttenderingStapel", required = false)
    private final TussenStapel<BrpDienstAttenderingInhoud> dienstAttenderingStapel;
    @Element(name = "dienstSelectieStapel", required = false)
    private final TussenStapel<BrpDienstSelectieInhoud> dienstSelectieStapel;

    /**
     * Maak een nieuw TussenDienst object.
     *
     * @param effectAfnemersindicatie
     *            de effectAfnemersindicatie
     * @param soortDienstCode
     *            de soortDienstCode
     * @param dienstStapel
     *            de dienst stapels
     * @param dienstAttenderingStapel
     *            de dienst attendering stapels
     * @param dienstSelectieStapel
     *            de dienst selectie stapels
     */
    public TussenDienst(
        @Element(name = "effectAfnemersindicatie", required = false) final BrpEffectAfnemerindicatiesCode effectAfnemersindicatie,
        @Element(name = "soort", required = false) final BrpSoortDienstCode soortDienstCode,
        @Element(name = "dienstStapel", required = false) final TussenStapel<BrpDienstInhoud> dienstStapel,
        @Element(name = "dienstAttenderingStapel", required = false) final TussenStapel<BrpDienstAttenderingInhoud> dienstAttenderingStapel,
        @Element(name = "dienstSelectieStapel", required = false) final TussenStapel<BrpDienstSelectieInhoud> dienstSelectieStapel)
    {
        super();
        this.effectAfnemersindicatie = effectAfnemersindicatie;
        this.soortDienstCode = soortDienstCode;
        this.dienstStapel = dienstStapel;
        this.dienstAttenderingStapel = dienstAttenderingStapel;
        this.dienstSelectieStapel = dienstSelectieStapel;
    }

    /**
     * Geef de waarde van soort dienst code.
     *
     * @return de soort dienst code
     */
    public BrpSoortDienstCode getSoortDienstCode() {
        return soortDienstCode;
    }

    /**
     * Geef de waarde van dienst stapel.
     *
     * @return dienst stapel
     */
    public TussenStapel<BrpDienstInhoud> getDienstStapel() {
        return dienstStapel;
    }

    /**
     * Geef de waarde van effectAfnemersindicatie.
     *
     * @return effectAfnemersindicatie
     */
    public BrpEffectAfnemerindicatiesCode getEffectAfnemersindicatie() {
        return effectAfnemersindicatie;
    }

    /**
     * Geef de waarde van dienst attendering stapel.
     *
     * @return dienst attendering stapel
     */
    public TussenStapel<BrpDienstAttenderingInhoud> getDienstAttenderingStapel() {
        return dienstAttenderingStapel;
    }

    /**
     * Geef de waarde van dienst selectie stapel.
     *
     * @return dienst selectie stapel
     */
    public TussenStapel<BrpDienstSelectieInhoud> getDienstSelectieStapel() {
        return dienstSelectieStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenDienst)) {
            return false;
        }
        final TussenDienst castOther = (TussenDienst) other;
        return new EqualsBuilder().append(effectAfnemersindicatie, castOther.effectAfnemersindicatie)
                .append(soortDienstCode, castOther.soortDienstCode)
                .append(dienstStapel, castOther.dienstStapel)
                .append(dienstAttenderingStapel, castOther.dienstAttenderingStapel)
                .append(dienstSelectieStapel, castOther.dienstSelectieStapel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(effectAfnemersindicatie)
                                    .append(soortDienstCode)
                                    .append(dienstStapel)
                                    .append(dienstAttenderingStapel)
                                    .append(dienstSelectieStapel)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("effectAfnemersindicatie", effectAfnemersindicatie)
                .append("soortDienstCode", soortDienstCode)
                .append("dienstStapel", dienstStapel)
                .append("dienstAttenderingStapel", dienstAttenderingStapel)
                .append("dienstSelectieStapel", dienstSelectieStapel)
                .toString();
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpSoortDienstCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert het BRP objecttype Dienst.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpDienst {

    @Element(name = "effectAfnemersindicatie", required = false)
    private final BrpEffectAfnemerindicatiesCode effectAfnemersindicatie;
    @Element(name = "soort", required = false)
    private final BrpSoortDienstCode soortDienstCode;
    @Element(name = "dienstStapel", required = false)
    private final BrpStapel<BrpDienstInhoud> dienstStapel;
    @Element(name = "dienstAttenderingStapel", required = false)
    private final BrpStapel<BrpDienstAttenderingInhoud> dienstAttenderingStapel;
    @Element(name = "dienstSelectieStapel", required = false)
    private final BrpStapel<BrpDienstSelectieInhoud> dienstSelectieStapel;

    /**
     * Maak een nieuw BrpDienst object.
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
    public BrpDienst(
        @Element(name = "effectAfnemersindicatie", required = false) final BrpEffectAfnemerindicatiesCode effectAfnemersindicatie,
        @Element(name = "soort", required = false) final BrpSoortDienstCode soortDienstCode,
        @Element(name = "dienstStapel", required = false) final BrpStapel<BrpDienstInhoud> dienstStapel,
        @Element(name = "dienstAttenderingStapel", required = false) final BrpStapel<BrpDienstAttenderingInhoud> dienstAttenderingStapel,
        @Element(name = "dienstSelectieStapel", required = false) final BrpStapel<BrpDienstSelectieInhoud> dienstSelectieStapel)
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
    public BrpStapel<BrpDienstInhoud> getDienstStapel() {
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
    public BrpStapel<BrpDienstAttenderingInhoud> getDienstAttenderingStapel() {
        return dienstAttenderingStapel;
    }

    /**
     * Geef de waarde van dienst selectie stapel.
     *
     * @return dienst selectie stapel
     */
    public BrpStapel<BrpDienstSelectieInhoud> getDienstSelectieStapel() {
        return dienstSelectieStapel;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienst)) {
            return false;
        }
        final BrpDienst castOther = (BrpDienst) other;
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

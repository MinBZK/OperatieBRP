/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Combinatie adellijke titel en predikaat in de BRP die mapt op de LO3 adellijke titel/predikaat.
 */
public final class AdellijkeTitelPredikaatPaar {

    private final BrpCharacter adellijkeTitel;
    private final BrpCharacter predikaat;
    private final BrpGeslachtsaanduidingCode geslachtsaanduiding;

    /**
     * @param adellijkeTitel de adellijke titel
     * @param predikaat het predikaat
     * @param geslachtsaanduiding de geslachtsaanduiding, mag niet null zijn
     */
    public AdellijkeTitelPredikaatPaar(
            final BrpCharacter adellijkeTitel,
            final BrpCharacter predikaat,
            final BrpGeslachtsaanduidingCode geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            throw new NullPointerException("geslachtsaanduiding mag niet null zijn");
        }
        this.adellijkeTitel = adellijkeTitel;
        this.predikaat = predikaat;
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    /**
     * Geef de waarde van adellijke titel.
     * @return de adellijke titel, of null
     */
    public BrpCharacter getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * Geef de waarde van predikaat.
     * @return het predikaat, of null
     */
    public BrpCharacter getPredikaat() {
        return predikaat;
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     * @return de geslachtsaanduiding waarop de title of predikaat betrekking heeft
     */
    public BrpGeslachtsaanduidingCode getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AdellijkeTitelPredikaatPaar)) {
            return false;
        }
        final AdellijkeTitelPredikaatPaar castOther = (AdellijkeTitelPredikaatPaar) other;
        return new EqualsBuilder().append(adellijkeTitel, castOther.adellijkeTitel)
                .append(predikaat, castOther.predikaat)
                .append(geslachtsaanduiding, castOther.geslachtsaanduiding)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(adellijkeTitel).append(predikaat).append(geslachtsaanduiding).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("adellijkeTitel", adellijkeTitel)
                .append("predikaat", predikaat)
                .append("geslachtsaanduiding", geslachtsaanduiding)
                .toString();
    }

}

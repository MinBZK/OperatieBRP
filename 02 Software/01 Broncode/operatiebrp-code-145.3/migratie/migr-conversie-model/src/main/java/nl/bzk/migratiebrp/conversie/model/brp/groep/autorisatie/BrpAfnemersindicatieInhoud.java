/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP Afnemersindicatie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpAfnemersindicatieInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "datumAanvangMaterielePeriode", required = false)
    private final BrpDatum datumAanvangMaterielePeriode;

    @Element(name = "datumEindeVolgen", required = false)
    private final BrpDatum datumEindeVolgen;

    @Element(name = "isLeeg", required = false)
    private final boolean leeg;

    /**
     * Maak een nieuw BrpAfnemersIndicatieInhoud object.
     * @param datumAanvangMaterielePeriode datum aanvang materieel periode
     * @param datumEindeVolgen datum einde volgen
     * @param leeg moet deze inhoud worden behandeld als leeg?
     */
    public BrpAfnemersindicatieInhoud(
            @Element(name = "datumAanvangMaterielePeriode", required = false) final BrpDatum datumAanvangMaterielePeriode,
            @Element(name = "datumEindeVolgen", required = false) final BrpDatum datumEindeVolgen,
            @Element(name = "isLeeg", required = false) final boolean leeg) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
        this.datumEindeVolgen = datumEindeVolgen;
        this.leeg = leeg;
    }

    /**
     * Geef de waarde van datum aanvang materiele periode van BrpAfnemersindicatieInhoud.
     * @return de waarde van datum aanvang materiele periode van BrpAfnemersindicatieInhoud
     */
    public BrpDatum getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * Geef de waarde van datum einde volgen van BrpAfnemersindicatieInhoud.
     * @return de waarde van datum einde volgen van BrpAfnemersindicatieInhoud
     */
    public BrpDatum getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return leeg;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAfnemersindicatieInhoud)) {
            return false;
        }
        final BrpAfnemersindicatieInhoud castOther = (BrpAfnemersindicatieInhoud) other;
        return new EqualsBuilder().append(datumAanvangMaterielePeriode, castOther.datumAanvangMaterielePeriode)
                .append(datumEindeVolgen, castOther.datumEindeVolgen)
                .append(leeg, castOther.leeg)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumAanvangMaterielePeriode).append(datumEindeVolgen).append(leeg).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datumAanvangMaterielePeriode", datumAanvangMaterielePeriode)
                .append("datumEindeVolgen", datumEindeVolgen)
                .append("leeg", leeg)
                .toString();
    }
}

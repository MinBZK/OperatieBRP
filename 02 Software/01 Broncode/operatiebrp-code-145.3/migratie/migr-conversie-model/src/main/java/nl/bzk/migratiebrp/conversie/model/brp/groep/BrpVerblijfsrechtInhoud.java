/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Verblijfstitel.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpVerblijfsrechtInhoud extends AbstractBrpGroepInhoud {
    @Element(name = "aanduidingVerblijfsrechtCode", required = false)
    private final BrpVerblijfsrechtCode aanduidingVerblijfsrechtCode;
    @Element(name = "datumMededelingVerblijfsrecht", required = false)
    private final BrpDatum datumMededelingVerblijfsrecht;
    @Element(name = "datumVoorzienEindeVerblijfsrecht", required = false)
    private final BrpDatum datumVoorzienEindeVerblijfsrecht;
    @Element(name = "datumAanvangVerblijfsrecht", required = false)
    private final BrpDatum datumAanvangVerblijfsrecht;

    /**
     * Maakt een BrpVerblijfsrechtInhoud object.
     * @param aanduidingVerblijfsrechtCode de unieke verblijfstitel code
     * @param datumMededelingVerblijfsrecht de datum mededeling verblijfstitel
     * @param datumVoorzienEindeVerblijfsrecht datum
     * @param datumAanvangVerblijfsrecht datum aanvang verblijfstitel
     */
    public BrpVerblijfsrechtInhoud(
            @Element(name = "aanduidingVerblijfsrechtCode", required = false) final BrpVerblijfsrechtCode aanduidingVerblijfsrechtCode,
            @Element(name = "datumMededelingVerblijfsrecht", required = false) final BrpDatum datumMededelingVerblijfsrecht,
            @Element(name = "datumVoorzienEindeVerblijfsrecht", required = false) final BrpDatum datumVoorzienEindeVerblijfsrecht,
            @Element(name = "datumAanvangVerblijfsrecht", required = false) final BrpDatum datumAanvangVerblijfsrecht) {
        this.aanduidingVerblijfsrechtCode = aanduidingVerblijfsrechtCode;
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    /**
     * Geef de waarde van aanduiding verblijfsrecht code van BrpVerblijfsrechtInhoud.
     * @return de waarde van aanduiding verblijfsrecht code van BrpVerblijfsrechtInhoud
     */
    public BrpVerblijfsrechtCode getAanduidingVerblijfsrechtCode() {
        return aanduidingVerblijfsrechtCode;
    }

    /**
     * Geef de waarde van datum mededeling verblijfsrecht van BrpVerblijfsrechtInhoud.
     * @return de waarde van datum mededeling verblijfsrecht van BrpVerblijfsrechtInhoud
     */
    public BrpDatum getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum aanvang verblijfstitel van BrpVerblijfsrechtInhoud.
     * @return de waarde van datum aanvang verblijfstitel van BrpVerblijfsrechtInhoud
     */
    public BrpDatum getDatumAanvangVerblijfstitel() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * Geef de waarde van datum voorzien einde verblijfsrecht van BrpVerblijfsrechtInhoud.
     * @return de waarde van datum voorzien einde verblijfsrecht van BrpVerblijfsrechtInhoud
     */
    public BrpDatum getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(
                aanduidingVerblijfsrechtCode,
                datumMededelingVerblijfsrecht,
                datumVoorzienEindeVerblijfsrecht,
                datumAanvangVerblijfsrecht);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpVerblijfsrechtInhoud)) {
            return false;
        }
        final BrpVerblijfsrechtInhoud castOther = (BrpVerblijfsrechtInhoud) other;
        return new EqualsBuilder().append(aanduidingVerblijfsrechtCode, castOther.aanduidingVerblijfsrechtCode)
                .append(datumMededelingVerblijfsrecht, castOther.datumMededelingVerblijfsrecht)
                .append(datumVoorzienEindeVerblijfsrecht, castOther.datumVoorzienEindeVerblijfsrecht)
                .append(datumAanvangVerblijfsrecht, castOther.datumAanvangVerblijfsrecht)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aanduidingVerblijfsrechtCode)
                .append(datumMededelingVerblijfsrecht)
                .append(datumVoorzienEindeVerblijfsrecht)
                .append(datumAanvangVerblijfsrecht)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("aanduidingVerblijfsrechtCode", aanduidingVerblijfsrechtCode)
                .append("datumMededelingVerblijfsrecht", datumMededelingVerblijfsrecht)
                .append("datumVoorzienEindeVerblijfsrecht", datumVoorzienEindeVerblijfsrecht)
                .append("datumAanvangVerblijfsrecht", datumAanvangVerblijfsrecht)
                .toString();
    }
}

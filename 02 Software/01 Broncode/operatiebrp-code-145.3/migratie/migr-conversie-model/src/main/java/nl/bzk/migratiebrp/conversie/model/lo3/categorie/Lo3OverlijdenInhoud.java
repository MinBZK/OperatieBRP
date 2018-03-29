/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de LO3 categorie Overlijden.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3OverlijdenInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0810)
    @Element(name = "datum", required = false)
    private final Lo3Datum datum;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0820)
    @Element(name = "gemeenteCode", required = false)
    private final Lo3GemeenteCode gemeenteCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0830)
    @Element(name = "landCode", required = false)
    private final Lo3LandCode landCode;

    /**
     * Default constructor (alles null).
     */
    public Lo3OverlijdenInhoud() {
        this(null, null, null);
    }

    /**
     * Maakt een Lo3OverlijdenInhoud object.
     * @param datum de datum van overlijden, mag niet null zijn
     * @param gemeenteCode de gemeente van overlijden, mag niet null zijn
     * @param landCode het land van overlijden, mag niet null zijn
     */
    public Lo3OverlijdenInhoud(
            @Element(name = "datum", required = false) final Lo3Datum datum,
            @Element(name = "gemeenteCode", required = false) final Lo3GemeenteCode gemeenteCode,
            @Element(name = "landCode", required = false) final Lo3LandCode landCode) {
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.landCode = landCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(datum, gemeenteCode, landCode);
    }

    /**
     * Geef de waarde van datum van Lo3OverlijdenInhoud.
     * @return de waarde van datum van Lo3OverlijdenInhoud
     */
    public Lo3Datum getDatum() {
        return datum;
    }

    /**
     * Geef de waarde van gemeente code van Lo3OverlijdenInhoud.
     * @return de waarde van gemeente code van Lo3OverlijdenInhoud
     */
    public Lo3GemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van land code van Lo3OverlijdenInhoud.
     * @return de waarde van land code van Lo3OverlijdenInhoud
     */
    public Lo3LandCode getLandCode() {
        return landCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3OverlijdenInhoud)) {
            return false;
        }
        final Lo3OverlijdenInhoud castOther = (Lo3OverlijdenInhoud) other;
        return new EqualsBuilder().append(datum, castOther.datum)
                .append(gemeenteCode, castOther.gemeenteCode)
                .append(landCode, castOther.landCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum).append(gemeenteCode).append(landCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datum", datum)
                .append("gemeenteCode", gemeenteCode)
                .append("landCode", landCode)
                .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3Datum datum;
        private Lo3GemeenteCode gemeenteCode;
        private Lo3LandCode landCode;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            //lege builder
        }

        /**
         * Maak een builder initieel gevud met de gegeven inhoud.
         * @param inhoud inhoud
         */
        public Builder(final Lo3OverlijdenInhoud inhoud) {
            datum = inhoud.datum;
            gemeenteCode = inhoud.gemeenteCode;
            landCode = inhoud.landCode;
        }

        /**
         * Maak de inhoud.
         * @return inhoud
         */
        public Lo3OverlijdenInhoud build() {
            return new Lo3OverlijdenInhoud(datum, gemeenteCode, landCode);
        }

        /**
         * Zet de waarden voor datum van Lo3OverlijdenInhoud.
         * @param datum de nieuwe waarde voor datum van Lo3OverlijdenInhoud
         */
        public void setDatum(final Lo3Datum datum) {
            this.datum = datum;
        }

        /**
         * Zet de waarden voor gemeente code van Lo3OverlijdenInhoud.
         * @param gemeenteCode de nieuwe waarde voor gemeente code van Lo3OverlijdenInhoud
         */
        public void setGemeenteCode(final Lo3GemeenteCode gemeenteCode) {
            this.gemeenteCode = gemeenteCode;
        }

        /**
         * Zet de waarden voor land code van Lo3OverlijdenInhoud.
         * @param landCode de nieuwe waarde voor land code van Lo3OverlijdenInhoud
         */
        public void setLandCode(final Lo3LandCode landCode) {
            this.landCode = landCode;
        }
    }

}

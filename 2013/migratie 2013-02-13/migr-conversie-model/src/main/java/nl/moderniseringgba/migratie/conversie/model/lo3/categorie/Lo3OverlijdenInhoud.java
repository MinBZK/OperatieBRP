/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de LO3 categorie Overlijden.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3OverlijdenInhoud implements Lo3CategorieInhoud {

    // 08 Overlijden
    @Element(name = "datum", required = false)
    private final Lo3Datum datum;
    @Element(name = "gemeenteCode", required = false)
    private final Lo3GemeenteCode gemeenteCode;
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
     * 
     * @param datum
     *            de datum van overlijden, mag niet null zijn
     * @param gemeenteCode
     *            de gemeente van overlijden, mag niet null zijn
     * @param landCode
     *            het land van overlijden, mag niet null zijn
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie06Overlijden}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn {@link Lo3CategorieValidator#valideerCategorie06Overlijden}
     */
    public Lo3OverlijdenInhoud(@Element(name = "datum", required = false) final Lo3Datum datum, @Element(
            name = "gemeenteCode", required = false) final Lo3GemeenteCode gemeenteCode, @Element(name = "landCode",
            required = false) final Lo3LandCode landCode) {
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.landCode = landCode;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(datum, gemeenteCode, landCode);
    }

    /**
     * @return the datum, of null
     */
    public Lo3Datum getDatum() {
        return datum;
    }

    /**
     * @return the gemeenteCode, of null
     */
    public Lo3GemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * @return the landCode, of null
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
        return new EqualsBuilder().append(datum, castOther.datum).append(gemeenteCode, castOther.gemeenteCode)
                .append(landCode, castOther.landCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datum).append(gemeenteCode).append(landCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("datum", datum)
                .append("gemeenteCode", gemeenteCode).append("landCode", landCode).toString();
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

        }

        /**
         * Maak een builder initieel gevud met de gegeven inhoud.
         * 
         * @param inhoud
         *            inhoud
         */
        public Builder(final Lo3OverlijdenInhoud inhoud) {
            datum = inhoud.datum;
            gemeenteCode = inhoud.gemeenteCode;
            landCode = inhoud.landCode;
        }

        /**
         * Maak de inhoud.
         * 
         * @return inhoud
         */
        public Lo3OverlijdenInhoud build() {
            return new Lo3OverlijdenInhoud(datum, gemeenteCode, landCode);
        }

        /**
         * @param datum
         *            the datum to set
         */
        public void setDatum(final Lo3Datum datum) {
            this.datum = datum;
        }

        /**
         * @param gemeenteCode
         *            the gemeenteCode to set
         */
        public void setGemeenteCode(final Lo3GemeenteCode gemeenteCode) {
            this.gemeenteCode = gemeenteCode;
        }

        /**
         * @param landCode
         *            the landCode to set
         */
        public void setLandCode(final Lo3LandCode landCode) {
            this.landCode = landCode;
        }

    }

}

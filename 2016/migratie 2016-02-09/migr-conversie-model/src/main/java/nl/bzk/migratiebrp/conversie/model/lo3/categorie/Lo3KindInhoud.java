/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van een LO3 Kind categorie. Deze class is immutable en threadsafe.
 */
public final class Lo3KindInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0110)
    @Element(name = "aNummer", required = false)
    private final Lo3Long aNummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0120)
    @Element(name = "burgerservicenummer", required = false)
    private final Lo3Integer burgerservicenummer;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0210)
    @Element(name = "voornamen", required = false)
    private final Lo3String voornamen;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0220)
    @Element(name = "adellijkeTitelPredikaatCode", required = false)
    private final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0230)
    @Element(name = "voorvoegselGeslachtsnaam", required = false)
    private final Lo3String voorvoegselGeslachtsnaam;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0240)
    @Element(name = "geslachtsnaam", required = false)
    private final Lo3String geslachtsnaam;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0310)
    @Element(name = "geboortedatum", required = false)
    private final Lo3Datum geboortedatum;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0320)
    @Element(name = "geboorteGemeenteCode", required = false)
    private final Lo3GemeenteCode geboorteGemeenteCode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0330)
    @Element(name = "geboorteLandCode", required = false)
    private final Lo3LandCode geboorteLandCode;

    /**
     * Maakt een leeg Lo3KindInhoud object. LET OP: Dit is alleen voor Simple-XML. Gebruik de Builder!
     */

    public Lo3KindInhoud() {
        this(null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3KindInhoud object. LET OP: Dit is alleen voor Simple-XML. Gebruik de Builder!
     *
     * @param aNummer
     *            het LO3 A-nummer, mag null zijn
     * @param burgerservicenummer
     *            het LO3 burgerservicenummer, mag null zijn
     * @param voornamen
     *            de LO3 voornaam of voornamen, mag null zijn
     * @param adellijkeTitelPredikaatCode
     *            de LO3 adelijke titel / predikaat code, mag null zijn
     * @param voorvoegselGeslachtsnaam
     *            de LO3 voorvoegsel van de geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 10 karakters
     * @param geslachtsnaam
     *            de LO3 geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 200 karakters
     * @param geboortedatum
     *            de geboortedatum, mag null zijn
     * @param geboorteGemeenteCode
     *            de geboorte gemeente, mag null zijn
     * @param geboorteLandCode
     *            de landcode mag null zijn
     */
    public Lo3KindInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "aNummer", required = false) final Lo3Long aNummer,
        @Element(name = "burgerservicenummer", required = false) final Lo3Integer burgerservicenummer,
        @Element(name = "voornamen", required = false) final Lo3String voornamen,
        @Element(name = "adellijkeTitelPredikaatCode", required = false) final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
        @Element(name = "voorvoegselGeslachtsnaam", required = false) final Lo3String voorvoegselGeslachtsnaam,
        @Element(name = "geslachtsnaam", required = false) final Lo3String geslachtsnaam,
        @Element(name = "geboortedatum", required = false) final Lo3Datum geboortedatum,
        @Element(name = "geboorteGemeenteCode", required = false) final Lo3GemeenteCode geboorteGemeenteCode,
        @Element(name = "geboorteLandCode", required = false) final Lo3LandCode geboorteLandCode)
    {
        this.aNummer = aNummer;
        this.burgerservicenummer = burgerservicenummer;
        this.voornamen = voornamen;
        this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        this.geslachtsnaam = geslachtsnaam;
        this.geboortedatum = geboortedatum;
        this.geboorteGemeenteCode = geboorteGemeenteCode;
        this.geboorteLandCode = geboorteLandCode;
    }

    private Lo3KindInhoud(final Lo3KindInhoud.Builder builder) {
        aNummer = builder.aNummer;
        burgerservicenummer = builder.burgerservicenummer;
        voornamen = builder.voornamen;
        adellijkeTitelPredikaatCode = builder.adellijkeTitelPredikaatCode;
        voorvoegselGeslachtsnaam = builder.voorvoegselGeslachtsnaam;
        geslachtsnaam = builder.geslachtsnaam;
        geboortedatum = builder.geboortedatum;
        geboorteGemeenteCode = builder.geboorteGemeenteCode;
        geboorteLandCode = builder.geboorteLandCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(
            aNummer,
            burgerservicenummer,
            voornamen,
            adellijkeTitelPredikaatCode,
            voorvoegselGeslachtsnaam,
            geslachtsnaam,
            geboortedatum,
            geboorteGemeenteCode,
            geboorteLandCode);
    }

    /**
     * @return the aNummer
     */
    public Lo3Long getaNummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van burgerservicenummer.
     *
     * @return the burgerservicenummer
     */
    public Lo3Integer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van voornamen.
     *
     * @return the voornamen
     */
    public Lo3String getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van adellijke titel predikaat code.
     *
     * @return the adellijkeTitelPredikaatCode
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * Geef de waarde van voorvoegsel geslachtsnaam.
     *
     * @return the voorvoegselGeslachtsnaam
     */
    public Lo3String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsnaam.
     *
     * @return the geslachtsnaam
     */
    public Lo3String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Geef de waarde van geboortedatum.
     *
     * @return the geboortedatum
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code.
     *
     * @return the gemeenteCode
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code.
     *
     * @return the landCode
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3KindInhoud)) {
            return false;
        }
        final Lo3KindInhoud castOther = (Lo3KindInhoud) other;
        return new EqualsBuilder().append(aNummer, castOther.aNummer)
                                  .append(burgerservicenummer, castOther.burgerservicenummer)
                                  .append(voornamen, castOther.voornamen)
                                  .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                                  .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                                  .append(geslachtsnaam, castOther.geslachtsnaam)
                                  .append(geboortedatum, castOther.geboortedatum)
                                  .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                                  .append(geboorteLandCode, castOther.geboorteLandCode)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aNummer)
                                    .append(burgerservicenummer)
                                    .append(voornamen)
                                    .append(adellijkeTitelPredikaatCode)
                                    .append(voorvoegselGeslachtsnaam)
                                    .append(geslachtsnaam)
                                    .append(geboortedatum)
                                    .append(geboorteGemeenteCode)
                                    .append(geboorteLandCode)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aNummer", aNummer)
                                                                          .append("burgerservicenummer", burgerservicenummer)
                                                                          .append("voornamen", voornamen)
                                                                          .append("adellijkeTitelPredikaatCode", adellijkeTitelPredikaatCode)
                                                                          .append("voorvoegselGeslachtsnaam", voorvoegselGeslachtsnaam)
                                                                          .append("geslachtsnaam", geslachtsnaam)
                                                                          .append("geboortedatum", geboortedatum)
                                                                          .append("geboorteGemeenteCode", geboorteGemeenteCode)
                                                                          .append("geboorteLandCode", geboorteLandCode)
                                                                          .toString();
    }

    /** Builder. */
    public static final class Builder {

        private Lo3Long aNummer;
        private Lo3Integer burgerservicenummer;
        private Lo3String voornamen;
        private Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
        private Lo3String voorvoegselGeslachtsnaam;
        private Lo3String geslachtsnaam;
        private Lo3Datum geboortedatum;
        private Lo3GemeenteCode geboorteGemeenteCode;
        private Lo3LandCode geboorteLandCode;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initieel gevulde builder.
         *
         * @param inhoud
         *            initiele vulling
         */
        public Builder(final Lo3KindInhoud inhoud) {
            aNummer = inhoud.aNummer;
            burgerservicenummer = inhoud.burgerservicenummer;
            voornamen = inhoud.voornamen;
            adellijkeTitelPredikaatCode = inhoud.adellijkeTitelPredikaatCode;
            voorvoegselGeslachtsnaam = inhoud.voorvoegselGeslachtsnaam;
            geslachtsnaam = inhoud.geslachtsnaam;
            geboortedatum = inhoud.geboortedatum;
            geboorteGemeenteCode = inhoud.geboorteGemeenteCode;
            geboorteLandCode = inhoud.geboorteLandCode;
        }

        /**
         * Build.
         *
         * @return inhoud
         */
        public Lo3KindInhoud build() {
            return new Lo3KindInhoud(this);
        }

        /**
         * @param param
         *            the aNummer to set
         * @return de builder
         */
        public Builder aNummer(final Lo3Long param) {
            aNummer = param;
            return this;
        }

        /**
         * @param param
         *            the burgerservicenummer to set
         * @return de builder
         */
        public Builder burgerservicenummer(final Lo3Integer param) {
            burgerservicenummer = param;
            return this;
        }

        /**
         * @param param
         *            the voornamen to set
         * @return de builder
         */
        public Builder voornamen(final Lo3String param) {
            voornamen = param;
            return this;
        }

        /**
         * @param param
         *            the adellijkeTitelPredikaatCode to set
         * @return de builder
         */
        public Builder adellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode param) {
            adellijkeTitelPredikaatCode = param;
            return this;
        }

        /**
         * @param param
         *            the voorvoegselGeslachtsnaam to set
         * @return de builder
         */
        public Builder voorvoegselGeslachtsnaam(final Lo3String param) {
            voorvoegselGeslachtsnaam = param;
            return this;
        }

        /**
         * @param param
         *            the geslachtsnaam to set
         * @return de builder
         */
        public Builder geslachtsnaam(final Lo3String param) {
            geslachtsnaam = param;
            return this;
        }

        /**
         * @param param
         *            the geboortedatum to set
         * @return de builder
         */
        public Builder geboortedatum(final Lo3Datum param) {
            geboortedatum = param;
            return this;
        }

        /**
         * @param param
         *            the geboorteGemeenteCode to set
         * @return de builder
         */
        public Builder geboorteGemeenteCode(final Lo3GemeenteCode param) {
            geboorteGemeenteCode = param;
            return this;
        }

        /**
         * @param param
         *            the geboorteLandCode to set
         * @return de builder
         */
        public Builder geboorteLandCode(final Lo3LandCode param) {
            geboorteLandCode = param;
            return this;
        }
    }
}

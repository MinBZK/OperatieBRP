/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van een LO3 Ouder categorie (zowel Ouder1 als Ouder2)
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3OuderInhoud implements Lo3CategorieInhoud {

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0110)
    @Element(name = "aNummer")
    private final Lo3String aNummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0120)
    @Element(name = "burgerservicenummer")
    private final Lo3String burgerservicenummer;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0210)
    @Element(name = "voornamen")
    private final Lo3String voornamen;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0220)
    @Element(name = "adellijkeTitelPredikaatCode")
    private final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0230)
    @Element(name = "voorvoegselGeslachtsnaam")
    private final Lo3String voorvoegselGeslachtsnaam;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0240)
    @Element(name = "geslachtsnaam")
    private final Lo3String geslachtsnaam;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0310)
    @Element(name = "geboortedatum")
    private final Lo3Datum geboortedatum;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0320)
    @Element(name = "geboorteGemeenteCode")
    private final Lo3GemeenteCode geboorteGemeenteCode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0330)
    @Element(name = "geboorteLandCode")
    private final Lo3LandCode geboorteLandCode;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0410)
    @Element(name = "geslachtsaanduiding")
    private final Lo3Geslachtsaanduiding geslachtsaanduiding;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6210)
    @Element(name = "familierechtelijkeBetrekking")
    private final Lo3Datum familierechtelijkeBetrekking;

    /**
     * Default constructor (alles null).
     */
    public Lo3OuderInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3OuderInhoud object.
     * @param aNummer het LO3 A-nummer, mag null zijn
     * @param burgerservicenummer het LO3 burgerservicenummer, mag null zijn
     * @param voornamen de LO3 voornaam of voornamen, mag null zijn
     * @param adellijkeTitelPredikaatCode de LO3 adelijke titel / predikaat code, mag null zijn
     * @param voorvoegselGeslachtsnaam de LO3 voorvoegsel van de geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 10 karakters
     * @param geslachtsnaam de LO3 geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 200 karakters
     * @param geboortedatum de geboortedatum, mag null zijn
     * @param geboorteGemeenteCode de geboorte gemeente, mag null zijn
     * @param geboorteLandCode de landcode mag null zijn
     * @param geslachtsaanduiding de LO3 geslachtsaanduiding, mag null zijn
     * @param familierechtelijkeBetrekking de LO3 familierechtelijke betrekking, mag null zijn
     */
    public Lo3OuderInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "aNummer") final Lo3String aNummer,
        @Element(name = "burgerservicenummer") final Lo3String burgerservicenummer,
        @Element(name = "voornamen") final Lo3String voornamen,
        @Element(name = "adellijkeTitelPredikaatCode") final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
        @Element(name = "voorvoegselGeslachtsnaam") final Lo3String voorvoegselGeslachtsnaam,
        @Element(name = "geslachtsnaam") final Lo3String geslachtsnaam,
        @Element(name = "geboortedatum") final Lo3Datum geboortedatum,
        @Element(name = "geboorteGemeenteCode") final Lo3GemeenteCode geboorteGemeenteCode,
        @Element(name = "geboorteLandCode") final Lo3LandCode geboorteLandCode,
        @Element(name = "geslachtsaanduiding") final Lo3Geslachtsaanduiding geslachtsaanduiding,
        @Element(name = "familierechtelijkeBetrekking") final Lo3Datum familierechtelijkeBetrekking) {
        this.aNummer = aNummer;
        this.burgerservicenummer = burgerservicenummer;
        this.voornamen = voornamen;
        this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        this.geslachtsnaam = geslachtsnaam;
        this.geboortedatum = geboortedatum;
        this.geboorteGemeenteCode = geboorteGemeenteCode;
        this.geboorteLandCode = geboorteLandCode;
        this.geslachtsaanduiding = geslachtsaanduiding;
        this.familierechtelijkeBetrekking = familierechtelijkeBetrekking;
    }

    private Lo3OuderInhoud(final Lo3OuderInhoud.Builder builder) {
        aNummer = builder.aNummer;
        burgerservicenummer = builder.burgerservicenummer;
        voornamen = builder.voornamen;
        adellijkeTitelPredikaatCode = builder.adellijkeTitelPredikaatCode;
        voorvoegselGeslachtsnaam = builder.voorvoegselGeslachtsnaam;
        geslachtsnaam = builder.geslachtsnaam;
        geboortedatum = builder.geboortedatum;
        geboorteGemeenteCode = builder.geboorteGemeenteCode;
        geboorteLandCode = builder.geboorteLandCode;
        geslachtsaanduiding = builder.geslachtsaanduiding;
        familierechtelijkeBetrekking = builder.familierechtelijkeBetrekking;
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Lo3Validatie.isEenParameterGevuld(
                aNummer,
                burgerservicenummer,
                voornamen,
                adellijkeTitelPredikaatCode,
                voorvoegselGeslachtsnaam,
                geslachtsnaam,
                geboortedatum,
                geboorteGemeenteCode,
                geboorteLandCode,
                geslachtsaanduiding,
                familierechtelijkeBetrekking);
    }

    /**
     * Geef de waarde van onbekende ouder van Lo3OuderInhoud.
     * @return de waarde van onbekende ouder van Lo3OuderInhoud
     */
    public boolean isOnbekendeOuder() {
        final Lo3String puntGeslachtsnaam = Lo3String.wrap(".");
        return AbstractLo3Element.equalsWaarde(puntGeslachtsnaam, geslachtsnaam)
                && Lo3Validatie.isElementGevuld(familierechtelijkeBetrekking)
                && !Lo3Validatie.isEenParameterGevuld(
                aNummer,
                burgerservicenummer,
                voornamen,
                adellijkeTitelPredikaatCode,
                voorvoegselGeslachtsnaam,
                geboortedatum,
                geboorteGemeenteCode,
                geboorteLandCode,
                geslachtsaanduiding);
    }

    /**
     * Geef de waarde van jurische geen ouder van Lo3OuderInhoud.
     * @return de waarde van jurische geen ouder van Lo3OuderInhoud
     */
    public boolean isJurischeGeenOuder() {
        return isLeeg();
    }

    /**
     * @return the aNummer
     */
    public Lo3String getaNummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van burgerservicenummer van Lo3OuderInhoud.
     * @return de waarde van burgerservicenummer van Lo3OuderInhoud
     */
    public Lo3String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van voornamen van Lo3OuderInhoud.
     * @return de waarde van voornamen van Lo3OuderInhoud
     */
    public Lo3String getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van adellijke titel predikaat code van Lo3OuderInhoud.
     * @return de waarde van adellijke titel predikaat code van Lo3OuderInhoud
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * Geef de waarde van voorvoegsel geslachtsnaam van Lo3OuderInhoud.
     * @return de waarde van voorvoegsel geslachtsnaam van Lo3OuderInhoud
     */
    public Lo3String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsnaam van Lo3OuderInhoud.
     * @return de waarde van geslachtsnaam van Lo3OuderInhoud
     */
    public Lo3String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Geef de waarde van geboortedatum van Lo3OuderInhoud.
     * @return de waarde van geboortedatum van Lo3OuderInhoud
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code van Lo3OuderInhoud.
     * @return de waarde van geboorte gemeente code van Lo3OuderInhoud
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code van Lo3OuderInhoud.
     * @return de waarde van geboorte land code van Lo3OuderInhoud
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van Lo3OuderInhoud.
     * @return de waarde van geslachtsaanduiding van Lo3OuderInhoud
     */
    public Lo3Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geef de waarde van familierechtelijke betrekking van Lo3OuderInhoud.
     * @return de waarde van familierechtelijke betrekking van Lo3OuderInhoud
     */
    public Lo3Datum getFamilierechtelijkeBetrekking() {
        return familierechtelijkeBetrekking;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3OuderInhoud)) {
            return false;
        }
        final Lo3OuderInhoud castOther = (Lo3OuderInhoud) other;
        return new EqualsBuilder().append(aNummer, castOther.aNummer)
                .append(burgerservicenummer, castOther.burgerservicenummer)
                .append(voornamen, castOther.voornamen)
                .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                .append(geslachtsnaam, castOther.geslachtsnaam)
                .append(geboortedatum, castOther.geboortedatum)
                .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                .append(geboorteLandCode, castOther.geboorteLandCode)
                .append(geslachtsaanduiding, castOther.geslachtsaanduiding)
                .append(familierechtelijkeBetrekking, castOther.familierechtelijkeBetrekking)
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
                .append(geslachtsaanduiding)
                .append(familierechtelijkeBetrekking)
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
                .append("geslachtsaanduiding", geslachtsaanduiding)
                .append("familierechtelijkeBetrekking", familierechtelijkeBetrekking)
                .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {

        private Lo3String aNummer;
        private Lo3String burgerservicenummer;
        private Lo3String voornamen;
        private Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
        private Lo3String voorvoegselGeslachtsnaam;
        private Lo3String geslachtsnaam;
        private Lo3Datum geboortedatum;
        private Lo3GemeenteCode geboorteGemeenteCode;
        private Lo3LandCode geboorteLandCode;
        private Lo3Geslachtsaanduiding geslachtsaanduiding;
        private Lo3Datum familierechtelijkeBetrekking;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            //lege builder
        }

        /**
         * Maak een initieel gevulde builder.
         * @param inhoud initiele vulling
         */
        public Builder(final Lo3OuderInhoud inhoud) {
            aNummer = inhoud.aNummer;
            burgerservicenummer = inhoud.burgerservicenummer;
            voornamen = inhoud.voornamen;
            adellijkeTitelPredikaatCode = inhoud.adellijkeTitelPredikaatCode;
            voorvoegselGeslachtsnaam = inhoud.voorvoegselGeslachtsnaam;
            geslachtsnaam = inhoud.geslachtsnaam;
            geboortedatum = inhoud.geboortedatum;
            geboorteGemeenteCode = inhoud.geboorteGemeenteCode;
            geboorteLandCode = inhoud.geboorteLandCode;
            geslachtsaanduiding = inhoud.geslachtsaanduiding;
            familierechtelijkeBetrekking = inhoud.familierechtelijkeBetrekking;
        }

        /**
         * Build.
         * @return inhoud
         */
        public Lo3OuderInhoud build() {
            return new Lo3OuderInhoud(this);

        }

        /**
         * @param param the aNummer to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder anummer(final Lo3String param) {
            aNummer = param;
            return this;
        }

        /**
         * @param param the burgerservicenummer to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder burgerservicenummer(final Lo3String param) {
            burgerservicenummer = param;
            return this;
        }

        /**
         * @param param the voornamen to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder voornamen(final Lo3String param) {
            voornamen = param;
            return this;
        }

        /**
         * @param param the adellijkeTitelPredikaatCode to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder adellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode param) {
            adellijkeTitelPredikaatCode = param;
            return this;
        }

        /**
         * @param param the voorvoegselGeslachtsnaam to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder voorvoegselGeslachtsnaam(final Lo3String param) {
            voorvoegselGeslachtsnaam = param;
            return this;
        }

        /**
         * @param param the geslachtsnaam to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder geslachtsnaam(final Lo3String param) {
            geslachtsnaam = param;
            return this;
        }

        /**
         * @param param the geboortedatum to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder geboortedatum(final Lo3Datum param) {
            geboortedatum = param;
            return this;
        }

        /**
         * @param param the geboorteGemeenteCode to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder geboorteGemeenteCode(final Lo3GemeenteCode param) {
            geboorteGemeenteCode = param;
            return this;
        }

        /**
         * @param param the geboorteLandCode to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder geboorteLandCode(final Lo3LandCode param) {
            geboorteLandCode = param;
            return this;
        }

        /**
         * @param param the geslachtsaanduiding to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder geslachtsaanduiding(final Lo3Geslachtsaanduiding param) {
            geslachtsaanduiding = param;
            return this;
        }

        /**
         * @param param the familierechtelijkeBetrekking to set
         * @return this builder
         */
        public Lo3OuderInhoud.Builder familierechtelijkeBetrekking(final Lo3Datum param) {
            familierechtelijkeBetrekking = param;
            return this;
        }
    }
}

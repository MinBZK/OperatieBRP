/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
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
 * Deze class representeert de inhoud van een LO3 Persoon categorie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3PersoonInhoud implements Lo3CategorieInhoud {

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

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_2010)
    @Element(name = "vorigANummer")
    private final Lo3String vorigANummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_2020)
    @Element(name = "volgendANummer")
    private final Lo3String volgendANummer;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6110)
    @Element(name = "aanduidingNaamgebruikCode")
    private final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode;

    /**
     * Default constructor (alles null).
     */
    public Lo3PersoonInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3PersoonInhoud object.
     * @param aNummer het LO3 A-nummer
     * @param burgerservicenummer het LO3 burgerservicenummer, mag null zijn
     * @param voornamen de LO3 voornaam of voornamen, mag null zijn
     * @param adellijkeTitelPredikaatCode de LO3 adelijke titel / predikaat code, mag null zijn
     * @param voorvoegselGeslachtsnaam de LO3 voorvoegsel van de geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 10 karakters
     * @param geslachtsnaam de LO3 geslachtsnaam, mag niet null zijn en lengte tussen 1 en 200 karakters
     * @param geboortedatum de geboortedatum, mag niet null zijn
     * @param geboorteGemeenteCode de geboorte gemeente, mag niet null zijn
     * @param geboorteLandCode de geboorte landcode mag niet null zijn
     * @param geslachtsaanduiding de LO3 geslachtsaanduiding, mag niet null zijn
     * @param vorigANummer het administratienummer dat eerder aan de betrokken persoon is toegekend geweest, mag null zijn
     * @param volgendANummer het administratienummer dat nadien aan de betrokken persoon is toegekend, mag null zijn
     * @param aanduidingNaamgebruikCode de code voor aanduiding naamgebruik, mag niet null zijn
     */
    public Lo3PersoonInhoud(
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
        @Element(name = "vorigANummer") final Lo3String vorigANummer,
        @Element(name = "volgendANummer") final Lo3String volgendANummer,
        @Element(name = "aanduidingNaamgebruikCode") final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode) {
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
        this.aanduidingNaamgebruikCode = aanduidingNaamgebruikCode;
        this.vorigANummer = vorigANummer;
        this.volgendANummer = volgendANummer;
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
                aanduidingNaamgebruikCode,
                vorigANummer,
                volgendANummer);
    }

    /**
     * Geef de waarde van a nummer van Lo3PersoonInhoud.
     * @return de waarde van a nummer van Lo3PersoonInhoud
     */
    public Lo3String getANummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van burgerservicenummer van Lo3PersoonInhoud.
     * @return de waarde van burgerservicenummer van Lo3PersoonInhoud
     */
    public Lo3String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van voornamen van Lo3PersoonInhoud.
     * @return de waarde van voornamen van Lo3PersoonInhoud
     */
    public Lo3String getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van adellijke titel predikaat code van Lo3PersoonInhoud.
     * @return de waarde van adellijke titel predikaat code van Lo3PersoonInhoud
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * Geef de waarde van voorvoegsel geslachtsnaam van Lo3PersoonInhoud.
     * @return de waarde van voorvoegsel geslachtsnaam van Lo3PersoonInhoud
     */
    public Lo3String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsnaam van Lo3PersoonInhoud.
     * @return de waarde van geslachtsnaam van Lo3PersoonInhoud
     */
    public Lo3String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Geef de waarde van geboortedatum van Lo3PersoonInhoud.
     * @return de waarde van geboortedatum van Lo3PersoonInhoud
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code van Lo3PersoonInhoud.
     * @return de waarde van geboorte gemeente code van Lo3PersoonInhoud
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code van Lo3PersoonInhoud.
     * @return de waarde van geboorte land code van Lo3PersoonInhoud
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van Lo3PersoonInhoud.
     * @return de waarde van geslachtsaanduiding van Lo3PersoonInhoud
     */
    public Lo3Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geef de waarde van vorig a nummer van Lo3PersoonInhoud.
     * @return de waarde van vorig a nummer van Lo3PersoonInhoud
     */
    public Lo3String getVorigANummer() {
        return vorigANummer;
    }

    /**
     * Geef de waarde van volgend a nummer van Lo3PersoonInhoud.
     * @return de waarde van volgend a nummer van Lo3PersoonInhoud
     */
    public Lo3String getVolgendANummer() {
        return volgendANummer;
    }

    /**
     * Geef de waarde van aanduiding naamgebruik code van Lo3PersoonInhoud.
     * @return de waarde van aanduiding naamgebruik code van Lo3PersoonInhoud
     */
    public Lo3AanduidingNaamgebruikCode getAanduidingNaamgebruikCode() {
        return aanduidingNaamgebruikCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3PersoonInhoud)) {
            return false;
        }
        final Lo3PersoonInhoud castOther = (Lo3PersoonInhoud) other;
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
                .append(vorigANummer, castOther.vorigANummer)
                .append(volgendANummer, castOther.volgendANummer)
                .append(aanduidingNaamgebruikCode, castOther.aanduidingNaamgebruikCode)
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
                .append(vorigANummer)
                .append(volgendANummer)
                .append(aanduidingNaamgebruikCode)
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
                .append("vorigANummer", vorigANummer)
                .append("volgendANummer", volgendANummer)
                .append("aanduidingNaamgebruikCode", aanduidingNaamgebruikCode)
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
        private Lo3String vorigANummer;
        private Lo3String volgendANummer;
        private Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode;

        /**
         * Maak een lege builder.
         */
        public Builder() {
            //lege builder
        }

        /**
         * Maak een buildeer gevud met de gegeven inhoud.
         * @param inhoud inhoud
         */
        public Builder(final Lo3PersoonInhoud inhoud) {
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
            vorigANummer = inhoud.vorigANummer;
            volgendANummer = inhoud.volgendANummer;
            aanduidingNaamgebruikCode = inhoud.aanduidingNaamgebruikCode;
        }

        /**
         * Maak de inhoud.
         * @return inhoud
         */
        public Lo3PersoonInhoud build() {
            return new Lo3PersoonInhoud(
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
                    vorigANummer,
                    volgendANummer,
                    aanduidingNaamgebruikCode);
        }

        /**
         * @param aNummer the aNummer to set
         */
        public void setaNummer(final Lo3String aNummer) {
            this.aNummer = aNummer;
        }

        /**
         * Zet de waarden voor burgerservicenummer van Lo3PersoonInhoud.
         * @param burgerservicenummer de nieuwe waarde voor burgerservicenummer van Lo3PersoonInhoud
         */
        public void setBurgerservicenummer(final Lo3String burgerservicenummer) {
            this.burgerservicenummer = burgerservicenummer;
        }

        /**
         * Zet de waarden voor voornamen van Lo3PersoonInhoud.
         * @param voornamen de nieuwe waarde voor voornamen van Lo3PersoonInhoud
         */
        public void setVoornamen(final Lo3String voornamen) {
            this.voornamen = voornamen;
        }

        /**
         * Zet de waarden voor adellijke titel predikaat code van Lo3PersoonInhoud.
         * @param adellijkeTitelPredikaatCode de nieuwe waarde voor adellijke titel predikaat code van Lo3PersoonInhoud
         */
        public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
            this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        }

        /**
         * Zet de waarden voor voorvoegsel geslachtsnaam van Lo3PersoonInhoud.
         * @param voorvoegselGeslachtsnaam de nieuwe waarde voor voorvoegsel geslachtsnaam van Lo3PersoonInhoud
         */
        public void setVoorvoegselGeslachtsnaam(final Lo3String voorvoegselGeslachtsnaam) {
            this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        }

        /**
         * Zet de waarden voor geslachtsnaam van Lo3PersoonInhoud.
         * @param geslachtsnaam de nieuwe waarde voor geslachtsnaam van Lo3PersoonInhoud
         */
        public void setGeslachtsnaam(final Lo3String geslachtsnaam) {
            this.geslachtsnaam = geslachtsnaam;
        }

        /**
         * Zet de waarden voor geboortedatum van Lo3PersoonInhoud.
         * @param geboortedatum de nieuwe waarde voor geboortedatum van Lo3PersoonInhoud
         */
        public void setGeboortedatum(final Lo3Datum geboortedatum) {
            this.geboortedatum = geboortedatum;
        }

        /**
         * Zet de waarden voor geboorte gemeente code van Lo3PersoonInhoud.
         * @param geboorteGemeenteCode de nieuwe waarde voor geboorte gemeente code van Lo3PersoonInhoud
         */
        public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
            this.geboorteGemeenteCode = geboorteGemeenteCode;
        }

        /**
         * Zet de waarden voor geboorte land code van Lo3PersoonInhoud.
         * @param geboorteLandCode de nieuwe waarde voor geboorte land code van Lo3PersoonInhoud
         */
        public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
            this.geboorteLandCode = geboorteLandCode;
        }

        /**
         * Zet de waarden voor geslachtsaanduiding van Lo3PersoonInhoud.
         * @param geslachtsaanduiding de nieuwe waarde voor geslachtsaanduiding van Lo3PersoonInhoud
         */
        public void setGeslachtsaanduiding(final Lo3Geslachtsaanduiding geslachtsaanduiding) {
            this.geslachtsaanduiding = geslachtsaanduiding;
        }

        /**
         * Zet de waarden voor vorig a nummer van Lo3PersoonInhoud.
         * @param vorigANummer de nieuwe waarde voor vorig a nummer van Lo3PersoonInhoud
         */
        public void setVorigANummer(final Lo3String vorigANummer) {
            this.vorigANummer = vorigANummer;
        }

        /**
         * Zet de waarden voor volgend a nummer van Lo3PersoonInhoud.
         * @param volgendANummer de nieuwe waarde voor volgend a nummer van Lo3PersoonInhoud
         */
        public void setVolgendANummer(final Lo3String volgendANummer) {
            this.volgendANummer = volgendANummer;
        }

        /**
         * Zet de waarden voor aanduiding naamgebruik code van Lo3PersoonInhoud.
         * @param aanduidingNaamgebruikCode de nieuwe waarde voor aanduiding naamgebruik code van Lo3PersoonInhoud
         */
        public void setAanduidingNaamgebruikCode(final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode) {
            this.aanduidingNaamgebruikCode = aanduidingNaamgebruikCode;
        }
    }
}

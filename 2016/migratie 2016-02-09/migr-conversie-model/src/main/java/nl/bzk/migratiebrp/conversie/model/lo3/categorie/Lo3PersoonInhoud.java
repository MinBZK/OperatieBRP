/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
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
 * Deze class representeert de inhoud van een LO3 Persoon categorie.
 *
 * Deze class is immutable en threadsafe.
 *
 *
 *
 *
 */
public final class Lo3PersoonInhoud implements Lo3CategorieInhoud {

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

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0410)
    @Element(name = "geslachtsaanduiding", required = false)
    private final Lo3Geslachtsaanduiding geslachtsaanduiding;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_2010)
    @Element(name = "vorigANummer", required = false)
    private final Lo3Long vorigANummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_2020)
    @Element(name = "volgendANummer", required = false)
    private final Lo3Long volgendANummer;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_6110)
    @Element(name = "aanduidingNaamgebruikCode", required = false)
    private final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode;

    /**
     * Default constructor (alles null).
     */
    public Lo3PersoonInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3PersoonInhoud object.
     *
     * @param aNummer
     *            het LO3 A-nummer
     * @param burgerservicenummer
     *            het LO3 burgerservicenummer, mag null zijn
     * @param voornamen
     *            de LO3 voornaam of voornamen, mag null zijn
     * @param adellijkeTitelPredikaatCode
     *            de LO3 adelijke titel / predikaat code, mag null zijn
     * @param voorvoegselGeslachtsnaam
     *            de LO3 voorvoegsel van de geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 10 karakters
     * @param geslachtsnaam
     *            de LO3 geslachtsnaam, mag niet null zijn en lengte tussen 1 en 200 karakters
     * @param geboortedatum
     *            de geboortedatum, mag niet null zijn
     * @param geboorteGemeenteCode
     *            de geboorte gemeente, mag niet null zijn
     * @param geboorteLandCode
     *            de geboorte landcode mag niet null zijn
     * @param geslachtsaanduiding
     *            de LO3 geslachtsaanduiding, mag niet null zijn
     * @param vorigANummer
     *            het administratienummer dat eerder aan de betrokken persoon is toegekend geweest, mag null zijn
     * @param volgendANummer
     *            het administratienummer dat nadien aan de betrokken persoon is toegekend, mag null zijn
     * @param aanduidingNaamgebruikCode
     *            de code voor aanduiding naamgebruik, mag niet null zijn
     */
    public Lo3PersoonInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "aNummer", required = false) final Lo3Long aNummer,
        @Element(name = "burgerservicenummer", required = false) final Lo3Integer burgerservicenummer,
        @Element(name = "voornamen", required = false) final Lo3String voornamen,
        @Element(name = "adellijkeTitelPredikaatCode", required = false) final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
        @Element(name = "voorvoegselGeslachtsnaam", required = false) final Lo3String voorvoegselGeslachtsnaam,
        @Element(name = "geslachtsnaam", required = false) final Lo3String geslachtsnaam,
        @Element(name = "geboortedatum", required = false) final Lo3Datum geboortedatum,
        @Element(name = "geboorteGemeenteCode", required = false) final Lo3GemeenteCode geboorteGemeenteCode,
        @Element(name = "geboorteLandCode", required = false) final Lo3LandCode geboorteLandCode,
        @Element(name = "geslachtsaanduiding", required = false) final Lo3Geslachtsaanduiding geslachtsaanduiding,
        @Element(name = "vorigANummer", required = false) final Lo3Long vorigANummer,
        @Element(name = "volgendANummer", required = false) final Lo3Long volgendANummer,
        @Element(name = "aanduidingNaamgebruikCode", required = false) final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode)
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
        return !Validatie.isEenParameterGevuld(
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
     * Geef de waarde van a nummer.
     *
     * @return the aNummer
     */
    public Lo3Long getANummer() {
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
     * @return the geboorteGemeenteCode
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code.
     *
     * @return the geboorteLandCode
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     *
     * @return the geslachtsaanduiding
     */
    public Lo3Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geef de waarde van vorig a nummer.
     *
     * @return the vorigANummer, of null
     */
    public Lo3Long getVorigANummer() {
        return vorigANummer;
    }

    /**
     * Geef de waarde van volgend a nummer.
     *
     * @return the volgendANummer, of null
     */
    public Lo3Long getVolgendANummer() {
        return volgendANummer;
    }

    /**
     * Geef de waarde van aanduiding naamgebruik code.
     *
     * @return the aanduidingNaamgebruikCode
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
        private Lo3Long aNummer;
        private Lo3Integer burgerservicenummer;
        private Lo3String voornamen;
        private Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
        private Lo3String voorvoegselGeslachtsnaam;
        private Lo3String geslachtsnaam;
        private Lo3Datum geboortedatum;
        private Lo3GemeenteCode geboorteGemeenteCode;
        private Lo3LandCode geboorteLandCode;
        private Lo3Geslachtsaanduiding geslachtsaanduiding;
        private Lo3Long vorigANummer;
        private Lo3Long volgendANummer;
        private Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode;

        /**
         * Maak een lege builder.
         */
        public Builder() {
        }

        /**
         * Maak een buildeer gevud met de gegeven inhoud.
         *
         * @param inhoud
         *            inhoud
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
         *
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
         * @param aNummer
         *            the aNummer to set
         */
        public void setaNummer(final Lo3Long aNummer) {
            this.aNummer = aNummer;
        }

        /**
         * Zet de waarde van burgerservicenummer.
         *
         * @param burgerservicenummer
         *            the burgerservicenummer to set
         */
        public void setBurgerservicenummer(final Lo3Integer burgerservicenummer) {
            this.burgerservicenummer = burgerservicenummer;
        }

        /**
         * Zet de waarde van voornamen.
         *
         * @param voornamen
         *            the voornamen to set
         */
        public void setVoornamen(final Lo3String voornamen) {
            this.voornamen = voornamen;
        }

        /**
         * Zet de waarde van adellijke titel predikaat code.
         *
         * @param adellijkeTitelPredikaatCode
         *            the adellijkeTitelPredikaatCode to set
         */
        public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
            this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        }

        /**
         * Zet de waarde van voorvoegsel geslachtsnaam.
         *
         * @param voorvoegselGeslachtsnaam
         *            the voorvoegselGeslachtsnaam to set
         */
        public void setVoorvoegselGeslachtsnaam(final Lo3String voorvoegselGeslachtsnaam) {
            this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        }

        /**
         * Zet de waarde van geslachtsnaam.
         *
         * @param geslachtsnaam
         *            the geslachtsnaam to set
         */
        public void setGeslachtsnaam(final Lo3String geslachtsnaam) {
            this.geslachtsnaam = geslachtsnaam;
        }

        /**
         * Zet de waarde van geboortedatum.
         *
         * @param geboortedatum
         *            the geboortedatum to set
         */
        public void setGeboortedatum(final Lo3Datum geboortedatum) {
            this.geboortedatum = geboortedatum;
        }

        /**
         * Zet de waarde van geboorte gemeente code.
         *
         * @param geboorteGemeenteCode
         *            the geboorteGemeenteCode to set
         */
        public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
            this.geboorteGemeenteCode = geboorteGemeenteCode;
        }

        /**
         * Zet de waarde van geboorte land code.
         *
         * @param geboorteLandCode
         *            the geboorteLandCode to set
         */
        public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
            this.geboorteLandCode = geboorteLandCode;
        }

        /**
         * Zet de waarde van geslachtsaanduiding.
         *
         * @param geslachtsaanduiding
         *            the geslachtsaanduiding to set
         */
        public void setGeslachtsaanduiding(final Lo3Geslachtsaanduiding geslachtsaanduiding) {
            this.geslachtsaanduiding = geslachtsaanduiding;
        }

        /**
         * Zet de waarde van vorig a nummer.
         *
         * @param vorigANummer
         *            the vorigANummer to set
         */
        public void setVorigANummer(final Lo3Long vorigANummer) {
            this.vorigANummer = vorigANummer;
        }

        /**
         * Zet de waarde van volgend a nummer.
         *
         * @param volgendANummer
         *            the volgendANummer to set
         */
        public void setVolgendANummer(final Lo3Long volgendANummer) {
            this.volgendANummer = volgendANummer;
        }

        /**
         * Zet de waarde van aanduiding naamgebruik code.
         *
         * @param aanduidingNaamgebruikCode
         *            the aanduidingNaamgebruikCode to set
         */
        public void setAanduidingNaamgebruikCode(final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode) {
            this.aanduidingNaamgebruikCode = aanduidingNaamgebruikCode;
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de LO3 categorie Verwijziging (21).
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3VerwijzingInhoud implements Lo3CategorieInhoud {

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

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0910)
    @Element(name = "gemeenteInschrijving", required = false)
    private final Lo3GemeenteCode gemeenteInschrijving;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0920)
    @Element(name = "datumInschrijving", required = false)
    private final Lo3Datum datumInschrijving;

    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_7010)
    @Element(name = "indicatieGeheimCode", required = false)
    private final Lo3IndicatieGeheimCode indicatieGeheimCode;

    /**
     * Maak een Lo3VerwijzingInhoud object.
     * @param aNummer het LO3 A-nummer
     * @param burgerservicenummer het LO3 burgerservicenummer, mag null zijn
     * @param voornamen de LO3 voornaam of voornamen, mag null zijn
     * @param adellijkeTitelPredikaatCode de LO3 adelijke titel / predikaat code, mag null zijn
     * @param voorvoegselGeslachtsnaam de LO3 voorvoegsel van de geslachtsnaam, mag null zijn, als gevuld dan lengte tussen 1 en 10 karakters
     * @param geslachtsnaam de LO3 geslachtsnaam, mag niet null zijn en lengte tussen 1 en 200 karakters
     * @param geboortedatum de geboortedatum, mag niet null zijn
     * @param geboorteGemeenteCode de geboorte gemeente, mag niet null zijn
     * @param geboorteLandCode de geboorte landcode mag niet null zijn
     * @param gemeenteInschrijving de LO3 Gemeente van inschrijving, mag niet null zijn
     * @param datumInschrijving de LO3 Datum van inschrijving, mag niet null zijn
     * @param indicatieGeheimCode indicatie geheim verwijsgegevens
     */
    public Lo3VerwijzingInhoud(
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
        @Element(name = "gemeenteInschrijving", required = false) final Lo3GemeenteCode gemeenteInschrijving,
        @Element(name = "datumInschrijving", required = false) final Lo3Datum datumInschrijving,
        @Element(name = "indicatieGeheimCode", required = false) final Lo3IndicatieGeheimCode indicatieGeheimCode) {
        super();
        this.aNummer = aNummer;
        this.burgerservicenummer = burgerservicenummer;
        this.voornamen = voornamen;
        this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        this.geslachtsnaam = geslachtsnaam;
        this.geboortedatum = geboortedatum;
        this.geboorteGemeenteCode = geboorteGemeenteCode;
        this.geboorteLandCode = geboorteLandCode;
        this.gemeenteInschrijving = gemeenteInschrijving;
        this.datumInschrijving = datumInschrijving;
        this.indicatieGeheimCode = indicatieGeheimCode;
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
                gemeenteInschrijving,
                datumInschrijving,
                indicatieGeheimCode);
    }

    /**
     * Geef de waarde van a nummer van Lo3VerwijzingInhoud.
     * @return de waarde van a nummer van Lo3VerwijzingInhoud
     */
    public Lo3Long getANummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van burgerservicenummer van Lo3VerwijzingInhoud.
     * @return de waarde van burgerservicenummer van Lo3VerwijzingInhoud
     */
    public Lo3Integer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van voornamen van Lo3VerwijzingInhoud.
     * @return de waarde van voornamen van Lo3VerwijzingInhoud
     */
    public Lo3String getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van adellijke titel predikaat code van Lo3VerwijzingInhoud.
     * @return de waarde van adellijke titel predikaat code van Lo3VerwijzingInhoud
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * Geef de waarde van voorvoegsel geslachtsnaam van Lo3VerwijzingInhoud.
     * @return de waarde van voorvoegsel geslachtsnaam van Lo3VerwijzingInhoud
     */
    public Lo3String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsnaam van Lo3VerwijzingInhoud.
     * @return de waarde van geslachtsnaam van Lo3VerwijzingInhoud
     */
    public Lo3String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Geef de waarde van geboortedatum van Lo3VerwijzingInhoud.
     * @return de waarde van geboortedatum van Lo3VerwijzingInhoud
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code van Lo3VerwijzingInhoud.
     * @return de waarde van geboorte gemeente code van Lo3VerwijzingInhoud
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code van Lo3VerwijzingInhoud.
     * @return de waarde van geboorte land code van Lo3VerwijzingInhoud
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * Geef de waarde van gemeente inschrijving van Lo3VerwijzingInhoud.
     * @return de waarde van gemeente inschrijving van Lo3VerwijzingInhoud
     */
    public Lo3GemeenteCode getGemeenteInschrijving() {
        return gemeenteInschrijving;
    }

    /**
     * Geef de waarde van datum inschrijving van Lo3VerwijzingInhoud.
     * @return de waarde van datum inschrijving van Lo3VerwijzingInhoud
     */
    public Lo3Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * Geef de waarde van indicatie geheim code van Lo3VerwijzingInhoud.
     * @return de waarde van indicatie geheim code van Lo3VerwijzingInhoud
     */
    public Lo3IndicatieGeheimCode getIndicatieGeheimCode() {
        return indicatieGeheimCode;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3VerwijzingInhoud)) {
            return false;
        }
        final Lo3VerwijzingInhoud castOther = (Lo3VerwijzingInhoud) other;
        return new EqualsBuilder().append(aNummer, castOther.aNummer)
                .append(burgerservicenummer, castOther.burgerservicenummer)
                .append(voornamen, castOther.voornamen)
                .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                .append(geslachtsnaam, castOther.geslachtsnaam)
                .append(geboortedatum, castOther.geboortedatum)
                .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                .append(geboorteLandCode, castOther.geboorteLandCode)
                .append(gemeenteInschrijving, castOther.gemeenteInschrijving)
                .append(datumInschrijving, castOther.datumInschrijving)
                .append(indicatieGeheimCode, castOther.indicatieGeheimCode)
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
                .append(gemeenteInschrijving)
                .append(datumInschrijving)
                .append(indicatieGeheimCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("aNummer", aNummer)
                .append("burgerservicenummer", burgerservicenummer)
                .append("voornamen", voornamen)
                .append("adellijkeTitelPredikaatCode", adellijkeTitelPredikaatCode)
                .append("voorvoegselGeslachtsnaam", voorvoegselGeslachtsnaam)
                .append("geslachtsnaam", geslachtsnaam)
                .append("geboortedatum", geboortedatum)
                .append("geboorteGemeenteCode", geboorteGemeenteCode)
                .append("geboorteLandCode", geboorteLandCode)
                .append("gemeenteInschrijving", gemeenteInschrijving)
                .append("datumInschrijving", datumInschrijving)
                .append("indicatieGeheimCode", indicatieGeheimCode)
                .toString();
    }

    /**
     * Builder.
     */
    public static final class Builder {
        private Lo3Long administratienummer;
        private Lo3Integer burgerservicenummer;
        private Lo3String voornamen;
        private Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
        private Lo3String voorvoegselGeslachtsnaam;
        private Lo3String geslachtsnaam;
        private Lo3Datum geboortedatum;
        private Lo3GemeenteCode geboorteGemeenteCode;
        private Lo3LandCode geboorteLandCode;
        private Lo3GemeenteCode gemeenteInschrijving;
        private Lo3Datum datumInschrijving;
        private Lo3IndicatieGeheimCode indicatieGeheimCode;

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
        public Builder(final Lo3VerwijzingInhoud inhoud) {
            administratienummer = inhoud.aNummer;
            burgerservicenummer = inhoud.burgerservicenummer;
            voornamen = inhoud.voornamen;
            adellijkeTitelPredikaatCode = inhoud.adellijkeTitelPredikaatCode;
            voorvoegselGeslachtsnaam = inhoud.voorvoegselGeslachtsnaam;
            geslachtsnaam = inhoud.geslachtsnaam;
            geboortedatum = inhoud.geboortedatum;
            geboorteGemeenteCode = inhoud.geboorteGemeenteCode;
            geboorteLandCode = inhoud.geboorteLandCode;
            gemeenteInschrijving = inhoud.gemeenteInschrijving;
            datumInschrijving = inhoud.datumInschrijving;
            indicatieGeheimCode = inhoud.indicatieGeheimCode;
        }

        /**
         * Build.
         * @return inhoud
         */
        public Lo3VerwijzingInhoud build() {
            return new Lo3VerwijzingInhoud(
                    administratienummer,
                    burgerservicenummer,
                    voornamen,
                    adellijkeTitelPredikaatCode,
                    voorvoegselGeslachtsnaam,
                    geslachtsnaam,
                    geboortedatum,
                    geboorteGemeenteCode,
                    geboorteLandCode,
                    gemeenteInschrijving,
                    datumInschrijving,
                    indicatieGeheimCode);
        }

        /**
         * Zet de waarden voor administratienummer van Lo3VerwijzingInhoud.
         * @param administratienummer de nieuwe waarde voor administratienummer van Lo3VerwijzingInhoud
         */
        public void setAdministratienummer(final Lo3Long administratienummer) {
            this.administratienummer = administratienummer;
        }

        /**
         * Zet de waarden voor burgerservicenummer van Lo3VerwijzingInhoud.
         * @param burgerservicenummer de nieuwe waarde voor burgerservicenummer van Lo3VerwijzingInhoud
         */
        public void setBurgerservicenummer(final Lo3Integer burgerservicenummer) {
            this.burgerservicenummer = burgerservicenummer;
        }

        /**
         * Zet de waarden voor voornamen van Lo3VerwijzingInhoud.
         * @param voornamen de nieuwe waarde voor voornamen van Lo3VerwijzingInhoud
         */
        public void setVoornamen(final Lo3String voornamen) {
            this.voornamen = voornamen;
        }

        /**
         * Zet de waarden voor adellijke titel predikaat code van Lo3VerwijzingInhoud.
         * @param adellijkeTitelPredikaatCode de nieuwe waarde voor adellijke titel predikaat code van Lo3VerwijzingInhoud
         */
        public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
            this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        }

        /**
         * Zet de waarden voor voorvoegsel geslachtsnaam van Lo3VerwijzingInhoud.
         * @param voorvoegselGeslachtsnaam de nieuwe waarde voor voorvoegsel geslachtsnaam van Lo3VerwijzingInhoud
         */
        public void setVoorvoegselGeslachtsnaam(final Lo3String voorvoegselGeslachtsnaam) {
            this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        }

        /**
         * Zet de waarden voor geslachtsnaam van Lo3VerwijzingInhoud.
         * @param geslachtsnaam de nieuwe waarde voor geslachtsnaam van Lo3VerwijzingInhoud
         */
        public void setGeslachtsnaam(final Lo3String geslachtsnaam) {
            this.geslachtsnaam = geslachtsnaam;
        }

        /**
         * Zet de waarden voor geboortedatum van Lo3VerwijzingInhoud.
         * @param geboortedatum de nieuwe waarde voor geboortedatum van Lo3VerwijzingInhoud
         */
        public void setGeboortedatum(final Lo3Datum geboortedatum) {
            this.geboortedatum = geboortedatum;
        }

        /**
         * Zet de waarden voor geboorte gemeente code van Lo3VerwijzingInhoud.
         * @param geboorteGemeenteCode de nieuwe waarde voor geboorte gemeente code van Lo3VerwijzingInhoud
         */
        public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
            this.geboorteGemeenteCode = geboorteGemeenteCode;
        }

        /**
         * Zet de waarden voor geboorte land code van Lo3VerwijzingInhoud.
         * @param geboorteLandCode de nieuwe waarde voor geboorte land code van Lo3VerwijzingInhoud
         */
        public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
            this.geboorteLandCode = geboorteLandCode;
        }

        /**
         * Zet de waarden voor gemeente inschrijving van Lo3VerwijzingInhoud.
         * @param gemeenteInschrijving de nieuwe waarde voor gemeente inschrijving van Lo3VerwijzingInhoud
         */
        public void setGemeenteInschrijving(final Lo3GemeenteCode gemeenteInschrijving) {
            this.gemeenteInschrijving = gemeenteInschrijving;
        }

        /**
         * Zet de waarden voor datum inschrijving van Lo3VerwijzingInhoud.
         * @param datumInschrijving de nieuwe waarde voor datum inschrijving van Lo3VerwijzingInhoud
         */
        public void setDatumInschrijving(final Lo3Datum datumInschrijving) {
            this.datumInschrijving = datumInschrijving;
        }

        /**
         * Zet de waarden voor indicatie geheim code van Lo3VerwijzingInhoud.
         * @param indicatieGeheimCode de nieuwe waarde voor indicatie geheim code van Lo3VerwijzingInhoud
         */
        public void setIndicatieGeheimCode(final Lo3IndicatieGeheimCode indicatieGeheimCode) {
            this.indicatieGeheimCode = indicatieGeheimCode;
        }
    }

}

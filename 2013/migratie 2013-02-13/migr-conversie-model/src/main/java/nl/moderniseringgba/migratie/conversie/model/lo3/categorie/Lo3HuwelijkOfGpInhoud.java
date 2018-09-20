/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de LO3 categorie Huwelijk/Geregistreerd partnerschap (05).
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class Lo3HuwelijkOfGpInhoud implements Lo3CategorieInhoud {

    // 01 Identificatienummers
    @Element(name = "aNummer", required = false)
    private final Long aNummer;
    @Element(name = "burgerservicenummer", required = false)
    private final Long burgerservicenummer;

    // 02 Naam
    @Element(name = "voornamen", required = false)
    private final String voornamen;
    @Element(name = "adellijkeTitelPredikaatCode", required = false)
    private final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
    @Element(name = "voorvoegselGeslachtsnaam", required = false)
    private final String voorvoegselGeslachtsnaam;
    @Element(name = "geslachtsnaam", required = false)
    private final String geslachtsnaam;

    // 03 Geboorte
    @Element(name = "geboortedatum", required = false)
    private final Lo3Datum geboortedatum;
    @Element(name = "geboorteGemeenteCode", required = false)
    private final Lo3GemeenteCode geboorteGemeenteCode;
    @Element(name = "geboorteLandCode", required = false)
    private final Lo3LandCode geboorteLandCode;

    // 04 Geslacht
    @Element(name = "geslachtsaanduiding", required = false)
    private final Lo3Geslachtsaanduiding geslachtsaanduiding;

    // 06 Huwelijkssluiting/aangaan geregistreerd partnerschap
    @Element(name = "datumSluitingHuwelijkOfAangaanGp", required = false)
    private final Lo3Datum datumSluitingHuwelijkOfAangaanGp;
    @Element(name = "gemeenteCodeSluitingHuwelijkOfAangaanGp", required = false)
    private final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp;
    @Element(name = "landCodeSluitingHuwelijkOfAangaanGp", required = false)
    private final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp;

    // 07 Ontbinding huwelijk/geregistreerd partnerschap
    @Element(name = "datumOntbindingHuwelijkOfGp", required = false)
    private final Lo3Datum datumOntbindingHuwelijkOfGp;
    @Element(name = "gemeenteCodeOntbindingHuwelijkOfGp", required = false)
    private final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp;
    @Element(name = "landCodeOntbindingHuwelijkOfGp", required = false)
    private final Lo3LandCode landCodeOntbindingHuwelijkOfGp;
    @Element(name = "redenOntbindingHuwelijkOfGpCode", required = false)
    private final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode;

    // 15 Soort verbintenis
    @Element(name = "soortVerbintenis", required = false)
    private final Lo3SoortVerbintenis soortVerbintenis;

    /**
     * Default constructor (alles null).
     */
    public Lo3HuwelijkOfGpInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null);
    }

    /**
     * Maakt een Lo3HuwelijkOfGpInhoud object.
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
     *            de LO3 geslachtsnaam, mag niet null zijn en lengte tussen 1 en 200 karakters
     * @param geboortedatum
     *            de geboortedatum, mag niet null zijn
     * @param geboorteGemeenteCode
     *            de geboorte gemeente, mag niet null zijn
     * @param geboorteLandCode
     *            de landcode mag niet null zijn
     * @param geslachtsaanduiding
     *            de LO3 geslachtsaanduiding, mag null zijn
     * @param datumSluitingHuwelijkOfAangaanGp
     *            de datum waarop het huwelijk is voltrokken of het partnerschap is aangegaan, mag null zijn
     * @param gemeenteCodeSluitingHuwelijkOfAangaanGp
     *            de gemeente waar het huwelijk is voltrokken of het partnerschap is aangegaan, mag null zijn
     * @param landCodeSluitingHuwelijkOfAangaanGp
     *            het land waar het huwelijk is voltrokken of het partnerschap is aangegaan, mag null zijn
     * @param datumOntbindingHuwelijkOfGp
     *            de datum waarop het huwelijk of geregistreerd partnerschap is ontbonden of nietig verklaard, mag null
     *            zijn
     * @param gemeenteCodeOntbindingHuwelijkOfGp
     *            de gemeente waar het huwelijk of geregistreerd partnerschap is ontbonden of nietig verklaard, mag null
     *            zijn
     * @param landCodeOntbindingHuwelijkOfGp
     *            het land waar het huwelijk of geregistreerd partnerschap is ontbonden of nietig verklaard, mag null
     *            zijn
     * @param redenOntbindingHuwelijkOfGpCode
     *            de reden waarom het huwelijk/geregistreerd partnerschap is ontbonden of nietig verklaard, mag null
     *            zijn
     * @param soortVerbintenis
     *            de soort verbintenis die is aangegaan, mag null zijn
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie05Huwelijk}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn {@link Lo3CategorieValidator#valideerCategorie05Huwelijk}
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3HuwelijkOfGpInhoud(
    // CHECKSTYLE:ON
            @Element(name = "aNummer", required = false) final Long aNummer,
            @Element(name = "burgerservicenummer", required = false) final Long burgerservicenummer,
            @Element(name = "voornamen", required = false) final String voornamen,
            @Element(name = "adellijkeTitelPredikaatCode", required = false) final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            @Element(name = "voorvoegselGeslachtsnaam", required = false) final String voorvoegselGeslachtsnaam,
            @Element(name = "geslachtsnaam", required = false) final String geslachtsnaam,
            @Element(name = "geboortedatum", required = false) final Lo3Datum geboortedatum,
            @Element(name = "geboorteGemeenteCode", required = false) final Lo3GemeenteCode geboorteGemeenteCode,
            @Element(name = "geboorteLandCode", required = false) final Lo3LandCode geboorteLandCode,
            @Element(name = "geslachtsaanduiding", required = false) final Lo3Geslachtsaanduiding geslachtsaanduiding,
            @Element(name = "datumSluitingHuwelijkOfAangaanGp", required = false) final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
            @Element(name = "gemeenteCodeSluitingHuwelijkOfAangaanGp", required = false) final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp,
            @Element(name = "landCodeSluitingHuwelijkOfAangaanGp", required = false) final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp,
            @Element(name = "datumOntbindingHuwelijkOfGp", required = false) final Lo3Datum datumOntbindingHuwelijkOfGp,
            @Element(name = "gemeenteCodeOntbindingHuwelijkOfGp", required = false) final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp,
            @Element(name = "landCodeOntbindingHuwelijkOfGp", required = false) final Lo3LandCode landCodeOntbindingHuwelijkOfGp,
            @Element(name = "redenOntbindingHuwelijkOfGpCode", required = false) final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode,
            @Element(name = "soortVerbintenis", required = false) final Lo3SoortVerbintenis soortVerbintenis) {
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
        this.datumSluitingHuwelijkOfAangaanGp = datumSluitingHuwelijkOfAangaanGp;
        this.gemeenteCodeSluitingHuwelijkOfAangaanGp = gemeenteCodeSluitingHuwelijkOfAangaanGp;
        this.landCodeSluitingHuwelijkOfAangaanGp = landCodeSluitingHuwelijkOfAangaanGp;
        this.datumOntbindingHuwelijkOfGp = datumOntbindingHuwelijkOfGp;
        this.gemeenteCodeOntbindingHuwelijkOfGp = gemeenteCodeOntbindingHuwelijkOfGp;
        this.landCodeOntbindingHuwelijkOfGp = landCodeOntbindingHuwelijkOfGp;
        this.redenOntbindingHuwelijkOfGpCode = redenOntbindingHuwelijkOfGpCode;
        this.soortVerbintenis = soortVerbintenis;
    }

    /*
     * Een huwelijk/gp is leeg als zowel groep 06 als groep 07 niet gevuld zijn.
     */
    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(datumSluitingHuwelijkOfAangaanGp,
                gemeenteCodeSluitingHuwelijkOfAangaanGp, landCodeSluitingHuwelijkOfAangaanGp,
                datumOntbindingHuwelijkOfGp, gemeenteCodeOntbindingHuwelijkOfGp, landCodeOntbindingHuwelijkOfGp,
                redenOntbindingHuwelijkOfGpCode);
    }

    /**
     * @return the aNummer, of null
     */
    public Long getaNummer() {
        return aNummer;
    }

    /**
     * @return the burgerservicenummer, of null
     */
    public Long getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * @return the voornamen, of null
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * @return the adellijkeTitelPredikaatCode, of null
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * @return the voorvoegselGeslachtsnaam, of null
     */
    public String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * @return the geslachtsnaam, of null
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * @return the geboortedatum, of null
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * @return the geboorteGemeenteCode, of null
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * @return the geboorteLandCode, of null
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * @return the geslachtsaanduiding, of null
     */
    public Lo3Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * @return the datumSluitingHuwelijkOfAangaanGp, of null
     */
    public Lo3Datum getDatumSluitingHuwelijkOfAangaanGp() {
        return datumSluitingHuwelijkOfAangaanGp;
    }

    /**
     * @return the gemeenteCodeSluitingHuwelijkOfAangaanGp, of null
     */
    public Lo3GemeenteCode getGemeenteCodeSluitingHuwelijkOfAangaanGp() {
        return gemeenteCodeSluitingHuwelijkOfAangaanGp;
    }

    /**
     * @return the landCodeSluitingHuwelijkOfAangaanGp, of null
     */
    public Lo3LandCode getLandCodeSluitingHuwelijkOfAangaanGp() {
        return landCodeSluitingHuwelijkOfAangaanGp;
    }

    /**
     * @return the datumOntbindingHuwelijkOfGp, of null
     */
    public Lo3Datum getDatumOntbindingHuwelijkOfGp() {
        return datumOntbindingHuwelijkOfGp;
    }

    /**
     * @return the gemeenteCodeOntbindingHuwelijkOfGp, of null
     */
    public Lo3GemeenteCode getGemeenteCodeOntbindingHuwelijkOfGp() {
        return gemeenteCodeOntbindingHuwelijkOfGp;
    }

    /**
     * @return the landCodeOntbindingHuwelijkOfGp, of null
     */
    public Lo3LandCode getLandCodeOntbindingHuwelijkOfGp() {
        return landCodeOntbindingHuwelijkOfGp;
    }

    /**
     * @return the redenOntbindingHuwelijkOfGpCode, of null
     */
    public Lo3RedenOntbindingHuwelijkOfGpCode getRedenOntbindingHuwelijkOfGpCode() {
        return redenOntbindingHuwelijkOfGpCode;
    }

    /**
     * @return the soortVerbintenis, of null
     */
    public Lo3SoortVerbintenis getSoortVerbintenis() {
        return soortVerbintenis;
    }

    /**
     * @return true als groep 05.07 (ontbinding) is gevuld, anders false
     */
    public boolean isOntbonden() {
        return datumOntbindingHuwelijkOfGp != null || gemeenteCodeOntbindingHuwelijkOfGp != null
                || landCodeOntbindingHuwelijkOfGp != null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3HuwelijkOfGpInhoud)) {
            return false;
        }
        final Lo3HuwelijkOfGpInhoud castOther = (Lo3HuwelijkOfGpInhoud) other;
        return new EqualsBuilder().append(aNummer, castOther.aNummer)
                .append(burgerservicenummer, castOther.burgerservicenummer).append(voornamen, castOther.voornamen)
                .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                .append(geslachtsnaam, castOther.geslachtsnaam).append(geboortedatum, castOther.geboortedatum)
                .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                .append(geboorteLandCode, castOther.geboorteLandCode)
                .append(geslachtsaanduiding, castOther.geslachtsaanduiding)
                .append(datumSluitingHuwelijkOfAangaanGp, castOther.datumSluitingHuwelijkOfAangaanGp)
                .append(gemeenteCodeSluitingHuwelijkOfAangaanGp, castOther.gemeenteCodeSluitingHuwelijkOfAangaanGp)
                .append(landCodeSluitingHuwelijkOfAangaanGp, castOther.landCodeSluitingHuwelijkOfAangaanGp)
                .append(datumOntbindingHuwelijkOfGp, castOther.datumOntbindingHuwelijkOfGp)
                .append(gemeenteCodeOntbindingHuwelijkOfGp, castOther.gemeenteCodeOntbindingHuwelijkOfGp)
                .append(landCodeOntbindingHuwelijkOfGp, castOther.landCodeOntbindingHuwelijkOfGp)
                .append(redenOntbindingHuwelijkOfGpCode, castOther.redenOntbindingHuwelijkOfGpCode)
                .append(soortVerbintenis, castOther.soortVerbintenis).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aNummer).append(burgerservicenummer).append(voornamen)
                .append(adellijkeTitelPredikaatCode).append(voorvoegselGeslachtsnaam).append(geslachtsnaam)
                .append(geboortedatum).append(geboorteGemeenteCode).append(geboorteLandCode)
                .append(geslachtsaanduiding).append(datumSluitingHuwelijkOfAangaanGp)
                .append(gemeenteCodeSluitingHuwelijkOfAangaanGp).append(landCodeSluitingHuwelijkOfAangaanGp)
                .append(datumOntbindingHuwelijkOfGp).append(gemeenteCodeOntbindingHuwelijkOfGp)
                .append(landCodeOntbindingHuwelijkOfGp).append(redenOntbindingHuwelijkOfGpCode)
                .append(soortVerbintenis).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aNummer", aNummer)
                .append("burgerservicenummer", burgerservicenummer).append("voornamen", voornamen)
                .append("adellijkeTitelPredikaatCode", adellijkeTitelPredikaatCode)
                .append("voorvoegselGeslachtsnaam", voorvoegselGeslachtsnaam).append("geslachtsnaam", geslachtsnaam)
                .append("geboortedatum", geboortedatum).append("geboorteGemeenteCode", geboorteGemeenteCode)
                .append("geboorteLandCode", geboorteLandCode).append("geslachtsaanduiding", geslachtsaanduiding)
                .append("datumSluitingHuwelijkOfAangaanGp", datumSluitingHuwelijkOfAangaanGp)
                .append("gemeenteCodeSluitingHuwelijkOfAangaanGp", gemeenteCodeSluitingHuwelijkOfAangaanGp)
                .append("landCodeSluitingHuwelijkOfAangaanGp", landCodeSluitingHuwelijkOfAangaanGp)
                .append("datumOntbindingHuwelijkOfGp", datumOntbindingHuwelijkOfGp)
                .append("gemeenteCodeOntbindingHuwelijkOfGp", gemeenteCodeOntbindingHuwelijkOfGp)
                .append("landCodeOntbindingHuwelijkOfGp", landCodeOntbindingHuwelijkOfGp)
                .append("redenOntbindingHuwelijkOfGpCode", redenOntbindingHuwelijkOfGpCode)
                .append("soortVerbintenis", soortVerbintenis).toString();
    }

    /** Builder. */
    public static final class Builder {

        private Long aNummer;
        private Long burgerservicenummer;
        private String voornamen;
        private Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
        private String voorvoegselGeslachtsnaam;
        private String geslachtsnaam;
        private Lo3Datum geboortedatum;
        private Lo3GemeenteCode geboorteGemeenteCode;
        private Lo3LandCode geboorteLandCode;
        private Lo3Geslachtsaanduiding geslachtsaanduiding;
        private Lo3Datum datumSluitingHuwelijkOfAangaanGp;
        private Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp;
        private Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp;
        private Lo3Datum datumOntbindingHuwelijkOfGp;
        private Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp;
        private Lo3LandCode landCodeOntbindingHuwelijkOfGp;
        private Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode;
        private Lo3SoortVerbintenis soortVerbintenis;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initieel gevulde builder.
         * 
         * @param inhoud
         *            initiele vulling
         */
        public Builder(final Lo3HuwelijkOfGpInhoud inhoud) {
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
            datumSluitingHuwelijkOfAangaanGp = inhoud.datumSluitingHuwelijkOfAangaanGp;
            gemeenteCodeSluitingHuwelijkOfAangaanGp = inhoud.gemeenteCodeSluitingHuwelijkOfAangaanGp;
            landCodeSluitingHuwelijkOfAangaanGp = inhoud.landCodeSluitingHuwelijkOfAangaanGp;
            datumOntbindingHuwelijkOfGp = inhoud.datumOntbindingHuwelijkOfGp;
            gemeenteCodeOntbindingHuwelijkOfGp = inhoud.gemeenteCodeOntbindingHuwelijkOfGp;
            landCodeOntbindingHuwelijkOfGp = inhoud.landCodeOntbindingHuwelijkOfGp;
            redenOntbindingHuwelijkOfGpCode = inhoud.redenOntbindingHuwelijkOfGpCode;
            soortVerbintenis = inhoud.soortVerbintenis;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3HuwelijkOfGpInhoud build() {
            return new Lo3HuwelijkOfGpInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                    voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode, geboorteLandCode,
                    geslachtsaanduiding, datumSluitingHuwelijkOfAangaanGp, gemeenteCodeSluitingHuwelijkOfAangaanGp,
                    landCodeSluitingHuwelijkOfAangaanGp, datumOntbindingHuwelijkOfGp,
                    gemeenteCodeOntbindingHuwelijkOfGp, landCodeOntbindingHuwelijkOfGp,
                    redenOntbindingHuwelijkOfGpCode, soortVerbintenis);
        }

        /**
         * @param aNummer
         *            the aNummer to set
         */
        public void setaNummer(final Long aNummer) {
            this.aNummer = aNummer;
        }

        /**
         * @param burgerservicenummer
         *            the burgerservicenummer to set
         */
        public void setBurgerservicenummer(final Long burgerservicenummer) {
            this.burgerservicenummer = burgerservicenummer;
        }

        /**
         * @param voornamen
         *            the voornamen to set
         */
        public void setVoornamen(final String voornamen) {
            this.voornamen = voornamen;
        }

        /**
         * @param adellijkeTitelPredikaatCode
         *            the adellijkeTitelPredikaatCode to set
         */
        public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
            this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        }

        /**
         * @param voorvoegselGeslachtsnaam
         *            the voorvoegselGeslachtsnaam to set
         */
        public void setVoorvoegselGeslachtsnaam(final String voorvoegselGeslachtsnaam) {
            this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        }

        /**
         * @param geslachtsnaam
         *            the geslachtsnaam to set
         */
        public void setGeslachtsnaam(final String geslachtsnaam) {
            this.geslachtsnaam = geslachtsnaam;
        }

        /**
         * @param geboortedatum
         *            the geboortedatum to set
         */
        public void setGeboortedatum(final Lo3Datum geboortedatum) {
            this.geboortedatum = geboortedatum;
        }

        /**
         * @param geboorteGemeenteCode
         *            the geboorteGemeenteCode to set
         */
        public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
            this.geboorteGemeenteCode = geboorteGemeenteCode;
        }

        /**
         * @param geboorteLandCode
         *            the geboorteLandCode to set
         */
        public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
            this.geboorteLandCode = geboorteLandCode;
        }

        /**
         * @param geslachtsaanduiding
         *            the geslachtsaanduiding to set
         */
        public void setGeslachtsaanduiding(final Lo3Geslachtsaanduiding geslachtsaanduiding) {
            this.geslachtsaanduiding = geslachtsaanduiding;
        }

        /**
         * @param datumSluitingHuwelijkOfAangaanGp
         *            the datumSluitingHuwelijkOfAangaanGp to set
         */
        public void setDatumSluitingHuwelijkOfAangaanGp(final Lo3Datum datumSluitingHuwelijkOfAangaanGp) {
            this.datumSluitingHuwelijkOfAangaanGp = datumSluitingHuwelijkOfAangaanGp;
        }

        /**
         * @param gemeenteCodeSluitingHuwelijkOfAangaanGp
         *            the gemeenteCodeSluitingHuwelijkOfAangaanGp to set
         */
        public void setGemeenteCodeSluitingHuwelijkOfAangaanGp(
                final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp) {
            this.gemeenteCodeSluitingHuwelijkOfAangaanGp = gemeenteCodeSluitingHuwelijkOfAangaanGp;
        }

        /**
         * @param landCodeSluitingHuwelijkOfAangaanGp
         *            the landCodeSluitingHuwelijkOfAangaanGp to set
         */
        public void setLandCodeSluitingHuwelijkOfAangaanGp(final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp) {
            this.landCodeSluitingHuwelijkOfAangaanGp = landCodeSluitingHuwelijkOfAangaanGp;
        }

        /**
         * @param datumOntbindingHuwelijkOfGp
         *            the datumOntbindingHuwelijkOfGp to set
         */
        public void setDatumOntbindingHuwelijkOfGp(final Lo3Datum datumOntbindingHuwelijkOfGp) {
            this.datumOntbindingHuwelijkOfGp = datumOntbindingHuwelijkOfGp;
        }

        /**
         * @param gemeenteCodeOntbindingHuwelijkOfGp
         *            the gemeenteCodeOntbindingHuwelijkOfGp to set
         */
        public void setGemeenteCodeOntbindingHuwelijkOfGp(final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp) {
            this.gemeenteCodeOntbindingHuwelijkOfGp = gemeenteCodeOntbindingHuwelijkOfGp;
        }

        /**
         * @param landCodeOntbindingHuwelijkOfGp
         *            the landCodeOntbindingHuwelijkOfGp to set
         */
        public void setLandCodeOntbindingHuwelijkOfGp(final Lo3LandCode landCodeOntbindingHuwelijkOfGp) {
            this.landCodeOntbindingHuwelijkOfGp = landCodeOntbindingHuwelijkOfGp;
        }

        /**
         * @param redenOntbindingHuwelijkOfGpCode
         *            the redenOntbindingHuwelijkOfGpCode to set
         */
        public void setRedenOntbindingHuwelijkOfGpCode(
                final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode) {
            this.redenOntbindingHuwelijkOfGpCode = redenOntbindingHuwelijkOfGpCode;
        }

        /**
         * @param soortVerbintenis
         *            the soortVerbintenis to set
         */
        public void setSoortVerbintenis(final Lo3SoortVerbintenis soortVerbintenis) {
            this.soortVerbintenis = soortVerbintenis;
        }
    }
}

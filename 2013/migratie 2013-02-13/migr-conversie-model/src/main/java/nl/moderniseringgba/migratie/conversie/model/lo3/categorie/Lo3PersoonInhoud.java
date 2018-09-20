/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

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

    // 20 A-nummerverwijzingen
    @Element(name = "vorigANummer", required = false)
    private final Long vorigANummer;
    @Element(name = "volgendANummer", required = false)
    private final Long volgendANummer;

    // 61 Naamgebruik
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
     * @param aanduidingNaamgebruikCode
     *            de code voor aanduiding naamgebruik, mag niet null zijn
     * @param vorigANummer
     *            het administratienummer dat eerder aan de betrokken persoon is toegekend geweest, mag null zijn
     * @param volgendANummer
     *            het administratienummer dat nadien aan de betrokken persoon is toegekend, mag null zijn
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie01Persoon}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn {@link Lo3CategorieValidator#valideerCategorie01Persoon}
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3PersoonInhoud(
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
            @Element(name = "aanduidingNaamgebruikCode", required = false) final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode,
            @Element(name = "vorigANummer", required = false) final Long vorigANummer,
            @Element(name = "volgendANummer", required = false) final Long volgendANummer) {
        // CHECKSTYLE:ON

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

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(aNummer, burgerservicenummer, voornamen,
                adellijkeTitelPredikaatCode, voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum,
                geboorteGemeenteCode, geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                volgendANummer);
    }

    /**
     * @return the aNummer
     */
    public Long getaNummer() {
        return aNummer;
    }

    /**
     * @return the burgerservicenummer
     */
    public Long getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * @return the voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * @return the adellijkeTitelPredikaatCode
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * @return the voorvoegselGeslachtsnaam
     */
    public String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * @return the geslachtsnaam
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * @return the geboortedatum
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * @return the geboorteGemeenteCode
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * @return the geboorteLandCode
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * @return the geslachtsaanduiding
     */
    public Lo3Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * @return the aanduidingNaamgebruikCode
     */
    public Lo3AanduidingNaamgebruikCode getAanduidingNaamgebruikCode() {
        return aanduidingNaamgebruikCode;
    }

    /**
     * @return the vorigANummer, of null
     */
    public Long getVorigANummer() {
        return vorigANummer;
    }

    /**
     * @return the volgendANummer, of null
     */
    public Long getVolgendANummer() {
        return volgendANummer;
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
                .append(burgerservicenummer, castOther.burgerservicenummer).append(voornamen, castOther.voornamen)
                .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                .append(geslachtsnaam, castOther.geslachtsnaam).append(geboortedatum, castOther.geboortedatum)
                .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                .append(geboorteLandCode, castOther.geboorteLandCode)
                .append(geslachtsaanduiding, castOther.geslachtsaanduiding)
                .append(aanduidingNaamgebruikCode, castOther.aanduidingNaamgebruikCode)
                .append(vorigANummer, castOther.vorigANummer).append(volgendANummer, castOther.volgendANummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aNummer).append(burgerservicenummer).append(voornamen)
                .append(adellijkeTitelPredikaatCode).append(voorvoegselGeslachtsnaam).append(geslachtsnaam)
                .append(geboortedatum).append(geboorteGemeenteCode).append(geboorteLandCode)
                .append(geslachtsaanduiding).append(aanduidingNaamgebruikCode).append(vorigANummer)
                .append(volgendANummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("aNummer", aNummer)
                .append("burgerservicenummer", burgerservicenummer).append("voornamen", voornamen)
                .append("adellijkeTitelPredikaatCode", adellijkeTitelPredikaatCode)
                .append("voorvoegselGeslachtsnaam", voorvoegselGeslachtsnaam).append("geslachtsnaam", geslachtsnaam)
                .append("geboortedatum", geboortedatum).append("geboorteGemeenteCode", geboorteGemeenteCode)
                .append("geboorteLandCode", geboorteLandCode).append("geslachtsaanduiding", geslachtsaanduiding)
                .append("aanduidingNaamgebruikCode", aanduidingNaamgebruikCode).append("vorigANummer", vorigANummer)
                .append("volgendANummer", volgendANummer).toString();
    }

    /**
     * Builder.
     */
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
        private Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode;
        private Long vorigANummer;
        private Long volgendANummer;

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
            aanduidingNaamgebruikCode = inhoud.aanduidingNaamgebruikCode;
            vorigANummer = inhoud.vorigANummer;
            volgendANummer = inhoud.volgendANummer;
        }

        /**
         * Maak de inhoud.
         * 
         * @return inhoud
         */
        public Lo3PersoonInhoud build() {
            return new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                    voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode, geboorteLandCode,
                    geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer, volgendANummer);
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
         * @param aanduidingNaamgebruikCode
         *            the aanduidingNaamgebruikCode to set
         */
        public void setAanduidingNaamgebruikCode(final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode) {
            this.aanduidingNaamgebruikCode = aanduidingNaamgebruikCode;
        }

        /**
         * @param vorigANummer
         *            the vorigANummer to set
         */
        public void setVorigANummer(final Long vorigANummer) {
            this.vorigANummer = vorigANummer;
        }

        /**
         * @param volgendANummer
         *            the volgendANummer to set
         */
        public void setVolgendANummer(final Long volgendANummer) {
            this.volgendANummer = volgendANummer;
        }

    }

}

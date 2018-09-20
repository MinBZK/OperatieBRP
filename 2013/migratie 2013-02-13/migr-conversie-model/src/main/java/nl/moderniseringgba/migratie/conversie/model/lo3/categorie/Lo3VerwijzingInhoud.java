/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de LO3 categorie Verwijziging (21).
 * 
 * Deze class is immutable en threadsafe.
 */
public final class Lo3VerwijzingInhoud implements Lo3CategorieInhoud {

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

    // 09 Gemeente
    @Element(name = "gemeenteInschrijving", required = false)
    private final Lo3GemeenteCode gemeenteInschrijving;
    @Element(name = "datumInschrijving", required = false)
    private final Lo3Datum datumInschrijving;

    // 11 Adres
    @Element(name = "straatnaam", required = false)
    private final String straatnaam;
    @Element(name = "naamOpenbareRuimte", required = false)
    private final String naamOpenbareRuimte;
    @Element(name = "huisnummer", required = false)
    private final Lo3Huisnummer huisnummer;
    @Element(name = "huisletter", required = false)
    private final Character huisletter;
    @Element(name = "huisnummertoevoeging", required = false)
    private final String huisnummertoevoeging;
    @Element(name = "aanduidingHuisnummer", required = false)
    private final Lo3AanduidingHuisnummer aanduidingHuisnummer;
    @Element(name = "postcode", required = false)
    private final String postcode;
    @Element(name = "woonplaatsnaam", required = false)
    private final String woonplaatsnaam;
    @Element(name = "identificatiecodeVerblijfplaats", required = false)
    private final String identificatiecodeVerblijfplaats;
    @Element(name = "identificatiecodeNummeraanduiding", required = false)
    private final String identificatiecodeNummeraanduiding;

    // 12 Locatie
    @Element(name = "locatieBeschrijving", required = false)
    private final String locatieBeschrijving;

    // 70 Geheim
    @Element(name = "indicatieGeheimCode", required = false)
    private final Lo3IndicatieGeheimCode indicatieGeheimCode;

    /**
     * Default constructor (alles null).
     */
    public Lo3VerwijzingInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null);
    }

    /**
     * Maak een Lo3VerwijzingInhoud object.
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
     * @param gemeenteInschrijving
     *            de LO3 Gemeente van inschrijving, mag niet null zijn
     * @param datumInschrijving
     *            de LO3 Datum van inschrijving, mag niet null zijn
     * @param straatnaam
     *            de LO3 Straatnaam, mag null zijn
     * @param naamOpenbareRuimte
     *            de LO3 Naam openbare ruimte, mag null zijn
     * @param huisnummer
     *            het LO3 Huisnummer, mag null zijn
     * @param huisletter
     *            de LO3 Huisletter, mag null zijn
     * @param huisnummertoevoeging
     *            de LO3 Huisnummertoevoeging, mag null zijn
     * @param aanduidingHuisnummer
     *            de LO3 Aanduiding bij huisnummer, mag null zijn
     * @param postcode
     *            de LO3 Postcode, mag null zijn
     * @param woonplaatsnaam
     *            de LO3 Woonplaatsnaam, mag null zijn
     * @param identificatiecodeVerblijfplaats
     *            de LO3 Identificatiecode verblijfplaats, mag null zijn
     * @param identificatiecodeNummeraanduiding
     *            de LO3 Identificatiecode nummeraanduiding, mag null zijn
     * @param locatieBeschrijving
     *            de LO3 Locatiebeschrijving, mag null zijn
     * @param indicatieGeheimCode
     *            70.10 indicatie geheim
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3VerwijzingInhoud(
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
            @Element(name = "gemeenteInschrijving", required = false) final Lo3GemeenteCode gemeenteInschrijving,
            @Element(name = "datumInschrijving", required = false) final Lo3Datum datumInschrijving,
            @Element(name = "straatnaam", required = false) final String straatnaam,
            @Element(name = "naamOpenbareRuimte", required = false) final String naamOpenbareRuimte,
            @Element(name = "huisnummer", required = false) final Lo3Huisnummer huisnummer,
            @Element(name = "huisletter", required = false) final Character huisletter,
            @Element(name = "huisnummertoevoeging", required = false) final String huisnummertoevoeging,
            @Element(name = "aanduidingHuisnummer", required = false) final Lo3AanduidingHuisnummer aanduidingHuisnummer,
            @Element(name = "postcode", required = false) final String postcode,
            @Element(name = "woonplaatsnaam", required = false) final String woonplaatsnaam,
            @Element(name = "identificatiecodeVerblijfplaats", required = false) final String identificatiecodeVerblijfplaats,
            @Element(name = "identificatiecodeNummeraanduiding", required = false) final String identificatiecodeNummeraanduiding,
            @Element(name = "locatieBeschrijving", required = false) final String locatieBeschrijving,
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
        this.straatnaam = straatnaam;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.aanduidingHuisnummer = aanduidingHuisnummer;
        this.postcode = postcode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.identificatiecodeVerblijfplaats = identificatiecodeVerblijfplaats;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.locatieBeschrijving = locatieBeschrijving;
        this.indicatieGeheimCode = indicatieGeheimCode;
    }

    // @Override
    // public void valideer() {
    // Lo3CategorieValidator.valideerCategorie21Verwijzing(this);
    // }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(aNummer, burgerservicenummer, voornamen,
                adellijkeTitelPredikaatCode, voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum,
                geboorteGemeenteCode, geboorteLandCode, gemeenteInschrijving, datumInschrijving, straatnaam,
                naamOpenbareRuimte, huisnummer, huisletter, huisnummertoevoeging, aanduidingHuisnummer, postcode,
                woonplaatsnaam, identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding,
                locatieBeschrijving, indicatieGeheimCode);
    }

    public Long getANummer() {
        return aNummer;
    }

    public Long getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public String getVoornamen() {
        return voornamen;
    }

    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    public String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    public Lo3GemeenteCode getGemeenteInschrijving() {
        return gemeenteInschrijving;
    }

    public Lo3Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public Lo3Huisnummer getHuisnummer() {
        return huisnummer;
    }

    public Character getHuisletter() {
        return huisletter;
    }

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public Lo3AanduidingHuisnummer getAanduidingHuisnummer() {
        return aanduidingHuisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    public String getIdentificatiecodeVerblijfplaats() {
        return identificatiecodeVerblijfplaats;
    }

    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    public String getLocatieBeschrijving() {
        return locatieBeschrijving;
    }

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
                .append(burgerservicenummer, castOther.burgerservicenummer).append(voornamen, castOther.voornamen)
                .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                .append(geslachtsnaam, castOther.geslachtsnaam).append(geboortedatum, castOther.geboortedatum)
                .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                .append(geboorteLandCode, castOther.geboorteLandCode)
                .append(gemeenteInschrijving, castOther.gemeenteInschrijving)
                .append(datumInschrijving, castOther.datumInschrijving).append(straatnaam, castOther.straatnaam)
                .append(naamOpenbareRuimte, castOther.naamOpenbareRuimte).append(huisnummer, castOther.huisnummer)
                .append(huisletter, castOther.huisletter)
                .append(huisnummertoevoeging, castOther.huisnummertoevoeging)
                .append(aanduidingHuisnummer, castOther.aanduidingHuisnummer).append(postcode, castOther.postcode)
                .append(woonplaatsnaam, castOther.woonplaatsnaam)
                .append(identificatiecodeVerblijfplaats, castOther.identificatiecodeVerblijfplaats)
                .append(identificatiecodeNummeraanduiding, castOther.identificatiecodeNummeraanduiding)
                .append(locatieBeschrijving, castOther.locatieBeschrijving)
                .append(indicatieGeheimCode, castOther.indicatieGeheimCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aNummer).append(burgerservicenummer).append(voornamen)
                .append(adellijkeTitelPredikaatCode).append(voorvoegselGeslachtsnaam).append(geslachtsnaam)
                .append(geboortedatum).append(geboorteGemeenteCode).append(geboorteLandCode)
                .append(gemeenteInschrijving).append(datumInschrijving).append(straatnaam).append(naamOpenbareRuimte)
                .append(huisnummer).append(huisletter).append(huisnummertoevoeging).append(aanduidingHuisnummer)
                .append(postcode).append(woonplaatsnaam).append(identificatiecodeVerblijfplaats)
                .append(identificatiecodeNummeraanduiding).append(locatieBeschrijving).append(indicatieGeheimCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("aNummer", aNummer).append("burgerservicenummer", burgerservicenummer)
                .append("voornamen", voornamen).append("adellijkeTitelPredikaatCode", adellijkeTitelPredikaatCode)
                .append("voorvoegselGeslachtsnaam", voorvoegselGeslachtsnaam).append("geslachtsnaam", geslachtsnaam)
                .append("geboortedatum", geboortedatum).append("geboorteGemeenteCode", geboorteGemeenteCode)
                .append("geboorteLandCode", geboorteLandCode).append("gemeenteInschrijving", gemeenteInschrijving)
                .append("datumInschrijving", datumInschrijving).append("straatnaam", straatnaam)
                .append("naamOpenbareRuimte", naamOpenbareRuimte).append("huisnummer", huisnummer)
                .append("huisletter", huisletter).append("huisnummertoevoeging", huisnummertoevoeging)
                .append("aanduidingHuisnummer", aanduidingHuisnummer).append("postcode", postcode)
                .append("woonplaatsnaam", woonplaatsnaam)
                .append("identificatiecodeVerblijfplaats", identificatiecodeVerblijfplaats)
                .append("identificatiecodeNummeraanduiding", identificatiecodeNummeraanduiding)
                .append("locatieBeschrijving", locatieBeschrijving)
                .append("indicatieGeheimCode", indicatieGeheimCode).toString();
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
        private Lo3GemeenteCode gemeenteInschrijving;
        private Lo3Datum datumInschrijving;
        private String straatnaam;
        private String naamOpenbareRuimte;
        private Lo3Huisnummer huisnummer;
        private Character huisletter;
        private String huisnummertoevoeging;
        private Lo3AanduidingHuisnummer aanduidingHuisnummer;
        private String postcode;
        private String woonplaatsnaam;
        private String identificatiecodeVerblijfplaats;
        private String identificatiecodeNummeraanduiding;
        private String locatieBeschrijving;
        private Lo3IndicatieGeheimCode indicatieGeheimCode;

        /** Maak een lege builder. */
        public Builder() {
        }

        /**
         * Maak een initieel gevulde builder.
         * 
         * @param inhoud
         *            initiele vulling
         */
        public Builder(final Lo3VerwijzingInhoud inhoud) {
            aNummer = inhoud.aNummer;
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
            straatnaam = inhoud.straatnaam;
            naamOpenbareRuimte = inhoud.naamOpenbareRuimte;
            huisnummer = inhoud.huisnummer;
            huisletter = inhoud.huisletter;
            huisnummertoevoeging = inhoud.huisnummertoevoeging;
            aanduidingHuisnummer = inhoud.aanduidingHuisnummer;
            postcode = inhoud.postcode;
            woonplaatsnaam = inhoud.woonplaatsnaam;
            identificatiecodeVerblijfplaats = inhoud.identificatiecodeVerblijfplaats;
            identificatiecodeNummeraanduiding = inhoud.identificatiecodeNummeraanduiding;
            locatieBeschrijving = inhoud.locatieBeschrijving;
            indicatieGeheimCode = inhoud.indicatieGeheimCode;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3VerwijzingInhoud build() {
            return new Lo3VerwijzingInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                    voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode, geboorteLandCode,
                    gemeenteInschrijving, datumInschrijving, straatnaam, naamOpenbareRuimte, huisnummer, huisletter,
                    huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam,
                    identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding, locatieBeschrijving,
                    indicatieGeheimCode);
        }

        public void setANummer(final Long aNummer) {
            this.aNummer = aNummer;
        }

        public void setBurgerservicenummer(final Long burgerservicenummer) {
            this.burgerservicenummer = burgerservicenummer;
        }

        public void setVoornamen(final String voornamen) {
            this.voornamen = voornamen;
        }

        public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
            this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
        }

        public void setVoorvoegselGeslachtsnaam(final String voorvoegselGeslachtsnaam) {
            this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
        }

        public void setGeslachtsnaam(final String geslachtsnaam) {
            this.geslachtsnaam = geslachtsnaam;
        }

        public void setGeboortedatum(final Lo3Datum geboortedatum) {
            this.geboortedatum = geboortedatum;
        }

        public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
            this.geboorteGemeenteCode = geboorteGemeenteCode;
        }

        public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
            this.geboorteLandCode = geboorteLandCode;
        }

        public void setGemeenteInschrijving(final Lo3GemeenteCode gemeenteInschrijving) {
            this.gemeenteInschrijving = gemeenteInschrijving;
        }

        public void setDatumInschrijving(final Lo3Datum datumInschrijving) {
            this.datumInschrijving = datumInschrijving;
        }

        public void setStraatnaam(final String straatnaam) {
            this.straatnaam = straatnaam;
        }

        public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
            this.naamOpenbareRuimte = naamOpenbareRuimte;
        }

        public void setHuisnummer(final Lo3Huisnummer huisnummer) {
            this.huisnummer = huisnummer;
        }

        public void setHuisletter(final Character huisletter) {
            this.huisletter = huisletter;
        }

        public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
            this.huisnummertoevoeging = huisnummertoevoeging;
        }

        public void setAanduidingHuisnummer(final Lo3AanduidingHuisnummer aanduidingHuisnummer) {
            this.aanduidingHuisnummer = aanduidingHuisnummer;
        }

        public void setPostcode(final String postcode) {
            this.postcode = postcode;
        }

        public void setWoonplaatsnaam(final String woonplaatsnaam) {
            this.woonplaatsnaam = woonplaatsnaam;
        }

        public void setIdentificatiecodeVerblijfplaats(final String identificatiecodeVerblijfplaats) {
            this.identificatiecodeVerblijfplaats = identificatiecodeVerblijfplaats;
        }

        public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
            this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        }

        public void setLocatieBeschrijving(final String locatieBeschrijving) {
            this.locatieBeschrijving = locatieBeschrijving;
        }

        public void setIndicatieGeheimCode(final Lo3IndicatieGeheimCode indicatieGeheimCode) {
            this.indicatieGeheimCode = indicatieGeheimCode;
        }
    }

}

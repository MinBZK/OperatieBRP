/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
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
 * Deze class representeert de inhoud van de LO3 categorie Huwelijk/Geregistreerd partnerschap (05).
 *
 * Deze class is immutable en threadsafe.
 *
 */
public final class Lo3HuwelijkOfGpInhoud implements Lo3CategorieInhoud {

    // 01 Identificatienummers
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0110)
    @Element(name = "aNummer", required = false)
    private final Lo3Long aNummer;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0120)
    @Element(name = "burgerservicenummer", required = false)
    private final Lo3Integer burgerservicenummer;

    // 02 Naam
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

    // 03 Geboorte
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0310)
    @Element(name = "geboortedatum", required = false)
    private final Lo3Datum geboortedatum;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0320)
    @Element(name = "geboorteGemeenteCode", required = false)
    private final Lo3GemeenteCode geboorteGemeenteCode;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0330)
    @Element(name = "geboorteLandCode", required = false)
    private final Lo3LandCode geboorteLandCode;

    // 04 Geslacht
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0410)
    @Element(name = "geslachtsaanduiding", required = false)
    private final Lo3Geslachtsaanduiding geslachtsaanduiding;

    // 06 Huwelijkssluiting/aangaan geregistreerd partnerschap
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0610)
    @Element(name = "datumSluitingHuwelijkOfAangaanGp", required = false)
    private final Lo3Datum datumSluitingHuwelijkOfAangaanGp;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0620)
    @Element(name = "gemeenteCodeSluitingHuwelijkOfAangaanGp", required = false)
    private final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0630)
    @Element(name = "landCodeSluitingHuwelijkOfAangaanGp", required = false)
    private final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp;

    // 07 Ontbinding huwelijk/geregistreerd partnerschap
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0710)
    @Element(name = "datumOntbindingHuwelijkOfGp", required = false)
    private final Lo3Datum datumOntbindingHuwelijkOfGp;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0720)
    @Element(name = "gemeenteCodeOntbindingHuwelijkOfGp", required = false)
    private final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0730)
    @Element(name = "landCodeOntbindingHuwelijkOfGp", required = false)
    private final Lo3LandCode landCodeOntbindingHuwelijkOfGp;
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_0740)
    @Element(name = "redenOntbindingHuwelijkOfGpCode", required = false)
    private final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode;

    // 15 Soort verbintenis
    @Lo3Elementnummer(Lo3ElementEnum.ELEMENT_1510)
    @Element(name = "soortVerbintenis", required = false)
    private final Lo3SoortVerbintenis soortVerbintenis;

    /**
     * Default constructor (alles null).
     */
    public Lo3HuwelijkOfGpInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
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
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     */
    public Lo3HuwelijkOfGpInhoud(
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
        @Element(name = "datumSluitingHuwelijkOfAangaanGp", required = false) final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
        @Element(name = "gemeenteCodeSluitingHuwelijkOfAangaanGp", required = false) final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp,
        @Element(name = "landCodeSluitingHuwelijkOfAangaanGp", required = false) final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp,
        @Element(name = "datumOntbindingHuwelijkOfGp", required = false) final Lo3Datum datumOntbindingHuwelijkOfGp,
        @Element(name = "gemeenteCodeOntbindingHuwelijkOfGp", required = false) final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp,
        @Element(name = "landCodeOntbindingHuwelijkOfGp", required = false) final Lo3LandCode landCodeOntbindingHuwelijkOfGp,
        @Element(name = "redenOntbindingHuwelijkOfGpCode", required = false) final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode,
        @Element(name = "soortVerbintenis", required = false) final Lo3SoortVerbintenis soortVerbintenis)
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
        this.datumSluitingHuwelijkOfAangaanGp = datumSluitingHuwelijkOfAangaanGp;
        this.gemeenteCodeSluitingHuwelijkOfAangaanGp = gemeenteCodeSluitingHuwelijkOfAangaanGp;
        this.landCodeSluitingHuwelijkOfAangaanGp = landCodeSluitingHuwelijkOfAangaanGp;
        this.datumOntbindingHuwelijkOfGp = datumOntbindingHuwelijkOfGp;
        this.gemeenteCodeOntbindingHuwelijkOfGp = gemeenteCodeOntbindingHuwelijkOfGp;
        this.landCodeOntbindingHuwelijkOfGp = landCodeOntbindingHuwelijkOfGp;
        this.redenOntbindingHuwelijkOfGpCode = redenOntbindingHuwelijkOfGpCode;
        this.soortVerbintenis = soortVerbintenis;
    }

    private Lo3HuwelijkOfGpInhoud(final Builder builder) {
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
        datumSluitingHuwelijkOfAangaanGp = builder.datumSluitingHuwelijkOfAangaanGp;
        gemeenteCodeSluitingHuwelijkOfAangaanGp = builder.gemeenteCodeSluitingHuwelijkOfAangaanGp;
        landCodeSluitingHuwelijkOfAangaanGp = builder.landCodeSluitingHuwelijkOfAangaanGp;
        datumOntbindingHuwelijkOfGp = builder.datumOntbindingHuwelijkOfGp;
        gemeenteCodeOntbindingHuwelijkOfGp = builder.gemeenteCodeOntbindingHuwelijkOfGp;
        landCodeOntbindingHuwelijkOfGp = builder.landCodeOntbindingHuwelijkOfGp;
        redenOntbindingHuwelijkOfGpCode = builder.redenOntbindingHuwelijkOfGpCode;
        soortVerbintenis = builder.soortVerbintenis;
    }

    /*
     * Een huwelijk/gp is leeg als zowel groep 06 als groep 07 niet gevuld zijn.
     */
    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !Validatie.isEenParameterGevuld(
            datumSluitingHuwelijkOfAangaanGp,
            gemeenteCodeSluitingHuwelijkOfAangaanGp,
            landCodeSluitingHuwelijkOfAangaanGp,
            datumOntbindingHuwelijkOfGp,
            gemeenteCodeOntbindingHuwelijkOfGp,
            landCodeOntbindingHuwelijkOfGp,
            redenOntbindingHuwelijkOfGpCode);
    }

    /**
     * @return the aNummer, of null
     */
    public Lo3Long getaNummer() {
        return aNummer;
    }

    /**
     * Geef de waarde van burgerservicenummer.
     *
     * @return the burgerservicenummer, of null
     */
    public Lo3Integer getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van voornamen.
     *
     * @return the voornamen, of null
     */
    public Lo3String getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van adellijke titel predikaat code.
     *
     * @return the adellijkeTitelPredikaatCode, of null
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * Geef de waarde van voorvoegsel geslachtsnaam.
     *
     * @return the voorvoegselGeslachtsnaam, of null
     */
    public Lo3String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsnaam.
     *
     * @return the geslachtsnaam, of null
     */
    public Lo3String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Geef de waarde van geboortedatum.
     *
     * @return the geboortedatum, of null
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code.
     *
     * @return the geboorteGemeenteCode, of null
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code.
     *
     * @return the geboorteLandCode, of null
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     *
     * @return the geslachtsaanduiding, of null
     */
    public Lo3Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geef de waarde van datum sluiting huwelijk of aangaan gp.
     *
     * @return the datumSluitingHuwelijkOfAangaanGp, of null
     */
    public Lo3Datum getDatumSluitingHuwelijkOfAangaanGp() {
        return datumSluitingHuwelijkOfAangaanGp;
    }

    /**
     * Geef de waarde van gemeente code sluiting huwelijk of aangaan gp.
     *
     * @return the gemeenteCodeSluitingHuwelijkOfAangaanGp, of null
     */
    public Lo3GemeenteCode getGemeenteCodeSluitingHuwelijkOfAangaanGp() {
        return gemeenteCodeSluitingHuwelijkOfAangaanGp;
    }

    /**
     * Geef de waarde van land code sluiting huwelijk of aangaan gp.
     *
     * @return the landCodeSluitingHuwelijkOfAangaanGp, of null
     */
    public Lo3LandCode getLandCodeSluitingHuwelijkOfAangaanGp() {
        return landCodeSluitingHuwelijkOfAangaanGp;
    }

    /**
     * Geef de waarde van datum ontbinding huwelijk of gp.
     *
     * @return the datumOntbindingHuwelijkOfGp, of null
     */
    public Lo3Datum getDatumOntbindingHuwelijkOfGp() {
        return datumOntbindingHuwelijkOfGp;
    }

    /**
     * Geef de waarde van gemeente code ontbinding huwelijk of gp.
     *
     * @return the gemeenteCodeOntbindingHuwelijkOfGp, of null
     */
    public Lo3GemeenteCode getGemeenteCodeOntbindingHuwelijkOfGp() {
        return gemeenteCodeOntbindingHuwelijkOfGp;
    }

    /**
     * Geef de waarde van land code ontbinding huwelijk of gp.
     *
     * @return the landCodeOntbindingHuwelijkOfGp, of null
     */
    public Lo3LandCode getLandCodeOntbindingHuwelijkOfGp() {
        return landCodeOntbindingHuwelijkOfGp;
    }

    /**
     * Geef de waarde van reden ontbinding huwelijk of gp code.
     *
     * @return the redenOntbindingHuwelijkOfGpCode, of null
     */
    public Lo3RedenOntbindingHuwelijkOfGpCode getRedenOntbindingHuwelijkOfGpCode() {
        return redenOntbindingHuwelijkOfGpCode;
    }

    /**
     * Geef de waarde van soort verbintenis.
     *
     * @return the soortVerbintenis, of null
     */
    public Lo3SoortVerbintenis getSoortVerbintenis() {
        return soortVerbintenis;
    }

    /**
     * Geef de sluiting.
     *
     * @return true als groep 05.06 (sluiting) is gevuld, anders false
     */
    public boolean isSluiting() {
        return Validatie.isEenParameterGevuld(
            datumSluitingHuwelijkOfAangaanGp,
            gemeenteCodeSluitingHuwelijkOfAangaanGp,
            landCodeSluitingHuwelijkOfAangaanGp);
    }

    /**
     * Geef de ontbinding.
     *
     * @return true als groep 05.07 (ontbinding) is gevuld, anders false
     */
    public boolean isOntbinding() {
        return Validatie.isEenParameterGevuld(
            datumOntbindingHuwelijkOfGp,
            gemeenteCodeOntbindingHuwelijkOfGp,
            landCodeOntbindingHuwelijkOfGp,
            redenOntbindingHuwelijkOfGpCode);
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
                                  .append(burgerservicenummer, castOther.burgerservicenummer)
                                  .append(voornamen, castOther.voornamen)
                                  .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                                  .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                                  .append(geslachtsnaam, castOther.geslachtsnaam)
                                  .append(geboortedatum, castOther.geboortedatum)
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
                                  .append(soortVerbintenis, castOther.soortVerbintenis)
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
                                    .append(datumSluitingHuwelijkOfAangaanGp)
                                    .append(gemeenteCodeSluitingHuwelijkOfAangaanGp)
                                    .append(landCodeSluitingHuwelijkOfAangaanGp)
                                    .append(datumOntbindingHuwelijkOfGp)
                                    .append(gemeenteCodeOntbindingHuwelijkOfGp)
                                    .append(landCodeOntbindingHuwelijkOfGp)
                                    .append(redenOntbindingHuwelijkOfGpCode)
                                    .append(soortVerbintenis)
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
                                                                          .append("datumSluitingHuwelijkOfAangaanGp", datumSluitingHuwelijkOfAangaanGp)
                                                                          .append(
                                                                              "gemeenteCodeSluitingHuwelijkOfAangaanGp",
                                                                              gemeenteCodeSluitingHuwelijkOfAangaanGp)
                                                                          .append(
                                                                              "landCodeSluitingHuwelijkOfAangaanGp",
                                                                              landCodeSluitingHuwelijkOfAangaanGp)
                                                                          .append("datumOntbindingHuwelijkOfGp", datumOntbindingHuwelijkOfGp)
                                                                          .append("gemeenteCodeOntbindingHuwelijkOfGp", gemeenteCodeOntbindingHuwelijkOfGp)
                                                                          .append("landCodeOntbindingHuwelijkOfGp", landCodeOntbindingHuwelijkOfGp)
                                                                          .append("redenOntbindingHuwelijkOfGpCode", redenOntbindingHuwelijkOfGpCode)
                                                                          .append("soortVerbintenis", soortVerbintenis)
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
            return new Lo3HuwelijkOfGpInhoud(this);
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
         *            the aNummer to set
         * @return de builder
         */
        public Builder aNummer(final Lo3Long param) {
            aNummer = param;
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

        /**
         * @param param
         *            the geslachtsaanduiding to set
         * @return de builder
         */
        public Builder geslachtsaanduiding(final Lo3Geslachtsaanduiding param) {
            geslachtsaanduiding = param;
            return this;
        }

        /**
         * @param param
         *            the datumSluitingHuwelijkOfAangaanGp to set
         * @return de builder
         */
        public Builder datumSluitingHuwelijkOfAangaanGp(final Lo3Datum param) {
            datumSluitingHuwelijkOfAangaanGp = param;
            return this;
        }

        /**
         * @param param
         *            the gemeenteCodeSluitingHuwelijkOfAangaanGp to set
         * @return de builder
         */
        public Builder gemeenteCodeSluitingHuwelijkOfAangaanGp(final Lo3GemeenteCode param) {
            gemeenteCodeSluitingHuwelijkOfAangaanGp = param;
            return this;
        }

        /**
         * @param param
         *            the landCodeSluitingHuwelijkOfAangaanGp to set
         * @return de builder
         */
        public Builder landCodeSluitingHuwelijkOfAangaanGp(final Lo3LandCode param) {
            landCodeSluitingHuwelijkOfAangaanGp = param;
            return this;
        }

        /**
         * @param param
         *            the datumOntbindingHuwelijkOfGp to set
         * @return de builder
         */
        public Builder datumOntbindingHuwelijkOfGp(final Lo3Datum param) {
            datumOntbindingHuwelijkOfGp = param;
            return this;
        }

        /**
         * @param param
         *            the gemeenteCodeOntbindingHuwelijkOfGp to set
         * @return de builder
         */
        public Builder gemeenteCodeOntbindingHuwelijkOfGp(final Lo3GemeenteCode param) {
            gemeenteCodeOntbindingHuwelijkOfGp = param;
            return this;
        }

        /**
         * @param param
         *            the landCodeOntbindingHuwelijkOfGp to set
         * @return de builder
         */
        public Builder landCodeOntbindingHuwelijkOfGp(final Lo3LandCode param) {
            landCodeOntbindingHuwelijkOfGp = param;
            return this;
        }

        /**
         * @param param
         *            the redenOntbindingHuwelijkOfGpCode to set
         * @return de builder
         */
        public Builder redenOntbindingHuwelijkOfGpCode(final Lo3RedenOntbindingHuwelijkOfGpCode param) {
            redenOntbindingHuwelijkOfGpCode = param;
            return this;
        }

        /**
         * @param param
         *            the soortVerbintenis to set
         * @return de builder
         */
        public Builder soortVerbintenis(final Lo3SoortVerbintenis param) {
            soortVerbintenis = param;
            return this;
        }
    }
}

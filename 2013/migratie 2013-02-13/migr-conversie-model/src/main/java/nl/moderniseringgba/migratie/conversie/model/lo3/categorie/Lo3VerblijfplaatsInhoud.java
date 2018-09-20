/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van een LO3 Verblijfplaats categorie.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class Lo3VerblijfplaatsInhoud implements Lo3CategorieInhoud {

    // 09 Gemeente
    @Element(name = "gemeenteInschrijving", required = false)
    private final Lo3GemeenteCode gemeenteInschrijving;
    @Element(name = "datumInschrijving", required = false)
    private final Lo3Datum datumInschrijving;

    // 10 Adreshouding
    @Element(name = "functieAdres", required = false)
    private final Lo3FunctieAdres functieAdres;
    @Element(name = "gemeenteDeel", required = false)
    private final String gemeenteDeel;
    @Element(name = "aanvangAdreshouding", required = false)
    private final Lo3Datum aanvangAdreshouding;

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

    // 13 Emigratie
    @Element(name = "landWaarnaarVertrokken", required = false)
    private final Lo3LandCode landWaarnaarVertrokken;
    @Element(name = "vertrekUitNederland", required = false)
    private final Lo3Datum vertrekUitNederland;
    @Element(name = "adresBuitenland1", required = false)
    private final String adresBuitenland1;
    @Element(name = "adresBuitenland2", required = false)
    private final String adresBuitenland2;
    @Element(name = "adresBuitenland3", required = false)
    private final String adresBuitenland3;

    // 14 Immigratie
    @Element(name = "landVanwaarIngeschreven", required = false)
    private final Lo3LandCode landVanwaarIngeschreven;
    @Element(name = "vestigingInNederland", required = false)
    private final Lo3Datum vestigingInNederland;

    // 72 Adreaangifte
    @Element(name = "aangifteAdreshouding", required = false)
    private final Lo3AangifteAdreshouding aangifteAdreshouding;

    // 75 Documentindicatie
    @Element(name = "indicatieDocument", required = false)
    private final Lo3IndicatieDocument indicatieDocument;

    /**
     * Default constructor (alles null).
     */
    public Lo3VerblijfplaatsInhoud() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null);
    }

    /**
     * Maakt een Lo3VerblijfplaatsInhoud object.
     * 
     * @param gemeenteInschrijving
     *            de LO3 Gemeente van inschrijving, mag niet null zijn
     * @param datumInschrijving
     *            de LO3 Datum van inschrijving, mag niet null zijn
     * @param functieAdres
     *            de LO3 Functie adres, mag null zijn
     * @param gemeenteDeel
     *            de LO3 Gemeentedeel, mag null zijn
     * @param aanvangAdreshouding
     *            de LO3 Datum aanvang adreshouding, mag null zijn
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
     * @param landWaarnaarVertrokken
     *            het LO3 Land waarnaar vertrokken, mag null zijn
     * @param vertrekUitNederland
     *            de LO3 Datum vertrek uit Nederland, mag null zijn
     * @param adresBuitenland1
     *            het LO3 Adres buitenland waarnaar vertrokken 1, mag null zijn
     * @param adresBuitenland2
     *            het LO3 Adres buitenland waarnaar vertrokken 2, mag null zijn
     * @param adresBuitenland3
     *            het LO3 Adres buitenland waarnaar vertrokken 3, mag null zijn
     * @param landVanwaarIngeschreven
     *            het LO3 Land vanwaar ingeschreven, mag null zijn
     * @param vestigingInNederland
     *            De LO3 Datum vestiging in Nederland, mag null zijn
     * @param aangifteAdreshouding
     *            de LO3 Omschrijving van de aangifte adreshouding, mag null zijn
     * @param indicatieDocument
     *            de LO3 Indicatie document, mag null zijn
     * @throws IllegalArgumentException
     *             als niet aan inhoudelijke voorwaarden is voldaan
     *             {@link Lo3CategorieValidator#valideerCategorie08Verblijfplaats}
     * @throws NullPointerException
     *             als verplichte velden niet aanwezig zijn
     *             {@link Lo3CategorieValidator#valideerCategorie08Verblijfplaats}
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public Lo3VerblijfplaatsInhoud(
            @Element(name = "gemeenteInschrijving", required = false) final Lo3GemeenteCode gemeenteInschrijving,
            @Element(name = "datumInschrijving", required = false) final Lo3Datum datumInschrijving,
            @Element(name = "functieAdres", required = false) final Lo3FunctieAdres functieAdres,
            @Element(name = "gemeenteDeel", required = false) final String gemeenteDeel,
            @Element(name = "aanvangAdreshouding", required = false) final Lo3Datum aanvangAdreshouding,
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
            @Element(name = "landWaarnaarVertrokken", required = false) final Lo3LandCode landWaarnaarVertrokken,
            @Element(name = "vertrekUitNederland", required = false) final Lo3Datum vertrekUitNederland,
            @Element(name = "adresBuitenland1", required = false) final String adresBuitenland1,
            @Element(name = "adresBuitenland2", required = false) final String adresBuitenland2,
            @Element(name = "adresBuitenland3", required = false) final String adresBuitenland3,
            @Element(name = "landVanwaarIngeschreven", required = false) final Lo3LandCode landVanwaarIngeschreven,
            @Element(name = "vestigingInNederland", required = false) final Lo3Datum vestigingInNederland,
            @Element(name = "aangifteAdreshouding", required = false) final Lo3AangifteAdreshouding aangifteAdreshouding,
            @Element(name = "indicatieDocument", required = false) final Lo3IndicatieDocument indicatieDocument) {
        // CHECKSTYLE:ON
        this.gemeenteInschrijving = gemeenteInschrijving;
        this.datumInschrijving = datumInschrijving;
        this.functieAdres = functieAdres;
        this.gemeenteDeel = gemeenteDeel;
        this.aanvangAdreshouding = aanvangAdreshouding;
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
        this.landWaarnaarVertrokken = landWaarnaarVertrokken;
        this.vertrekUitNederland = vertrekUitNederland;
        this.adresBuitenland1 = adresBuitenland1;
        this.adresBuitenland2 = adresBuitenland2;
        this.adresBuitenland3 = adresBuitenland3;
        this.landVanwaarIngeschreven = landVanwaarIngeschreven;
        this.vestigingInNederland = vestigingInNederland;
        this.aangifteAdreshouding = aangifteAdreshouding;
        this.indicatieDocument = indicatieDocument;
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(gemeenteInschrijving, datumInschrijving, functieAdres,
                gemeenteDeel, aanvangAdreshouding, straatnaam, naamOpenbareRuimte, huisnummer, huisletter,
                huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam,
                identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding, locatieBeschrijving,
                landWaarnaarVertrokken, vertrekUitNederland, adresBuitenland1, adresBuitenland2, adresBuitenland3,
                landVanwaarIngeschreven, vestigingInNederland, aangifteAdreshouding, indicatieDocument);
    }

    /**
     * @return the gemeenteInschrijving
     */
    public Lo3GemeenteCode getGemeenteInschrijving() {
        return gemeenteInschrijving;
    }

    /**
     * @return the datumInschrijving
     */
    public Lo3Datum getDatumInschrijving() {
        return datumInschrijving;
    }

    /**
     * @return the functieAdres
     */
    public Lo3FunctieAdres getFunctieAdres() {
        return functieAdres;
    }

    /**
     * @return the gemeenteDeel
     */
    public String getGemeenteDeel() {
        return gemeenteDeel;
    }

    /**
     * @return the aanvangAdreshouding
     */
    public Lo3Datum getAanvangAdreshouding() {
        return aanvangAdreshouding;
    }

    /**
     * @return the straatnaam
     */
    public String getStraatnaam() {
        return straatnaam;
    }

    /**
     * @return the naamOpenbareRuimte
     */
    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * @return the huisnummer
     */
    public Lo3Huisnummer getHuisnummer() {
        return huisnummer;
    }

    /**
     * @return the huisletter
     */
    public Character getHuisletter() {
        return huisletter;
    }

    /**
     * @return the huisnummertoevoeging
     */
    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * @return the aanduidingHuisnummer
     */
    public Lo3AanduidingHuisnummer getAanduidingHuisnummer() {
        return aanduidingHuisnummer;
    }

    /**
     * @return the postcode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * @return the woonplaatsnaam
     */
    public String getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * @return the identificatiecodeVerblijfplaats
     */
    public String getIdentificatiecodeVerblijfplaats() {
        return identificatiecodeVerblijfplaats;
    }

    /**
     * @return the identificatiecodeNummeraanduiding
     */
    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * @return the locatieBeschrijving
     */
    public String getLocatieBeschrijving() {
        return locatieBeschrijving;
    }

    /**
     * @return the landWaarnaarVertrokken
     */
    public Lo3LandCode getLandWaarnaarVertrokken() {
        return landWaarnaarVertrokken;
    }

    /**
     * @return the vertrekUitNederland
     */
    public Lo3Datum getDatumVertrekUitNederland() {
        return vertrekUitNederland;
    }

    /**
     * @return the adresBuitenland1
     */
    public String getAdresBuitenland1() {
        return adresBuitenland1;
    }

    /**
     * @return the adresBuitenland2
     */
    public String getAdresBuitenland2() {
        return adresBuitenland2;
    }

    /**
     * @return the adresBuitenland3
     */
    public String getAdresBuitenland3() {
        return adresBuitenland3;
    }

    /**
     * @return the landVanwaarIngeschreven
     */
    public Lo3LandCode getLandVanwaarIngeschreven() {
        return landVanwaarIngeschreven;
    }

    /**
     * @return the vestigingInNederland
     */
    public Lo3Datum getVestigingInNederland() {
        return vestigingInNederland;
    }

    /**
     * @return the aangifteAdreshouding
     */
    public Lo3AangifteAdreshouding getAangifteAdreshouding() {
        return aangifteAdreshouding;
    }

    /**
     * @return the indicatieDocument
     */
    public Lo3IndicatieDocument getIndicatieDocument() {
        return indicatieDocument;
    }

    /**
     * Bepaal of dit adres een punt adres is.
     * 
     * Een adres is een punt adres als:
     * <ul>
     * <li>08.11.10 Straatnaam de waarde '.' bevat EN</li>
     * <li>08.10.20 Gemeentedeel niet gevuld is EN</li>
     * <li>08.11.15 Naam openbare ruimte niet gevuld is EN</li>
     * <li>08.11.20 Huisnummer niet gevuld is EN</li>
     * <li>08.11.30 Huisletter niet gevuld is EN</li>
     * <li>08.11.40 Huisnummertoevoeging niet gevuld is EN</li>
     * <li>08.11.50 Aanduiding bij huisnummer niet gevuld is EN</li>
     * <li>08.11.60 Postcode niet gevuld is EN</li>
     * <li>08.11.70 Woonplaatsnaam niet gevuld is EN</li>
     * </ul>
     * 
     * @return true als dit adres een punt adres is, anders false
     */
    @Definitie({ Definities.DEF021, Definities.DEF022 })
    public boolean isPuntAdres() {
        return ".".equals(straatnaam)
                && !ValidationUtils.isEenParameterGevuld(gemeenteDeel, naamOpenbareRuimte, huisnummer, huisletter,
                        huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam);
    }

    /**
     * Bepaal of dit adres een Nederlands adres is.
     * 
     * Een adres is een Nederlands adres als:
     * <ul>
     * <li>08.11.10 Straatnaam is gevuld OF</li>
     * <li>08.10.20 Gemeentedeel gevuld is OF</li>
     * <li>08.11.15 Naam openbare ruimte gevuld is OF</li>
     * <li>08.11.20 Huisnummer gevuld is OF</li>
     * <li>08.11.30 Huisletter gevuld is OF</li>
     * <li>08.11.40 Huisnummertoevoeging gevuld is OF</li>
     * <li>08.11.50 Aanduiding bij huisnummer gevuld is OF</li>
     * <li>08.11.60 Postcode gevuld is OF</li>
     * <li>08.11.70 Woonplaatsnaam gevuld is OF</li>
     * <li>08.11.80 Identificatiecode verblijfplaats gevuld is OF</li>
     * <li>08.11.90 Identificatiecode nummeraanduiding gevuld is OF</li>
     * <li>08.12.10 Locatiebeschrijving gevuld is</li>
     * </ul>
     * 
     * @return true als dit adres een Nederlands adres is, anders false
     */
    public boolean isNederlandsAdres() {
        return ValidationUtils.isEenParameterGevuld(straatnaam, gemeenteDeel, naamOpenbareRuimte, huisnummer,
                huisletter, huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam,
                identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding, locatieBeschrijving);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3VerblijfplaatsInhoud)) {
            return false;
        }
        final Lo3VerblijfplaatsInhoud castOther = (Lo3VerblijfplaatsInhoud) other;
        return new EqualsBuilder().append(gemeenteInschrijving, castOther.gemeenteInschrijving)
                .append(datumInschrijving, castOther.datumInschrijving).append(functieAdres, castOther.functieAdres)
                .append(gemeenteDeel, castOther.gemeenteDeel)
                .append(aanvangAdreshouding, castOther.aanvangAdreshouding).append(straatnaam, castOther.straatnaam)
                .append(naamOpenbareRuimte, castOther.naamOpenbareRuimte).append(huisnummer, castOther.huisnummer)
                .append(huisletter, castOther.huisletter)
                .append(huisnummertoevoeging, castOther.huisnummertoevoeging)
                .append(aanduidingHuisnummer, castOther.aanduidingHuisnummer).append(postcode, castOther.postcode)
                .append(woonplaatsnaam, castOther.woonplaatsnaam)
                .append(identificatiecodeVerblijfplaats, castOther.identificatiecodeVerblijfplaats)
                .append(identificatiecodeNummeraanduiding, castOther.identificatiecodeNummeraanduiding)
                .append(locatieBeschrijving, castOther.locatieBeschrijving)
                .append(landWaarnaarVertrokken, castOther.landWaarnaarVertrokken)
                .append(vertrekUitNederland, castOther.vertrekUitNederland)
                .append(adresBuitenland1, castOther.adresBuitenland1)
                .append(adresBuitenland2, castOther.adresBuitenland2)
                .append(adresBuitenland3, castOther.adresBuitenland3)
                .append(landVanwaarIngeschreven, castOther.landVanwaarIngeschreven)
                .append(vestigingInNederland, castOther.vestigingInNederland)
                .append(aangifteAdreshouding, castOther.aangifteAdreshouding)
                .append(indicatieDocument, castOther.indicatieDocument).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(gemeenteInschrijving).append(datumInschrijving).append(functieAdres)
                .append(gemeenteDeel).append(aanvangAdreshouding).append(straatnaam).append(naamOpenbareRuimte)
                .append(huisnummer).append(huisletter).append(huisnummertoevoeging).append(aanduidingHuisnummer)
                .append(postcode).append(woonplaatsnaam).append(identificatiecodeVerblijfplaats)
                .append(identificatiecodeNummeraanduiding).append(locatieBeschrijving).append(landWaarnaarVertrokken)
                .append(vertrekUitNederland).append(adresBuitenland1).append(adresBuitenland2)
                .append(adresBuitenland3).append(landVanwaarIngeschreven).append(vestigingInNederland)
                .append(aangifteAdreshouding).append(indicatieDocument).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("gemeenteInschrijving", gemeenteInschrijving).append("datumInschrijving", datumInschrijving)
                .append("functieAdres", functieAdres).append("gemeenteDeel", gemeenteDeel)
                .append("aanvangAdreshouding", aanvangAdreshouding).append("straatnaam", straatnaam)
                .append("naamOpenbareRuimte", naamOpenbareRuimte).append("huisnummer", huisnummer)
                .append("huisletter", huisletter).append("huisnummertoevoeging", huisnummertoevoeging)
                .append("aanduidingHuisnummer", aanduidingHuisnummer).append("postcode", postcode)
                .append("woonplaatsnaam", woonplaatsnaam)
                .append("identificatiecodeVerblijfplaats", identificatiecodeVerblijfplaats)
                .append("identificatiecodeNummeraanduiding", identificatiecodeNummeraanduiding)
                .append("locatieBeschrijving", locatieBeschrijving)
                .append("landWaarnaarVertrokken", landWaarnaarVertrokken)
                .append("vertrekUitNederland", vertrekUitNederland).append("adresBuitenland1", adresBuitenland1)
                .append("adresBuitenland2", adresBuitenland2).append("adresBuitenland3", adresBuitenland3)
                .append("landVanwaarIngeschreven", landVanwaarIngeschreven)
                .append("vestigingInNederland", vestigingInNederland)
                .append("aangifteAdreshouding", aangifteAdreshouding).append("indicatieDocument", indicatieDocument)
                .toString();
    }

    /** Builder. */
    public static final class Builder {
        private Lo3GemeenteCode gemeenteInschrijving;
        private Lo3Datum datumInschrijving;
        private Lo3FunctieAdres functieAdres;
        private String gemeenteDeel;
        private Lo3Datum aanvangAdreshouding;
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
        private Lo3LandCode landWaarnaarVertrokken;
        private Lo3Datum vertrekUitNederland;
        private String adresBuitenland1;
        private String adresBuitenland2;
        private String adresBuitenland3;
        private Lo3LandCode landVanwaarIngeschreven;
        private Lo3Datum vestigingInNederland;
        private Lo3AangifteAdreshouding aangifteAdreshouding;
        private Lo3IndicatieDocument indicatieDocument;

        /** Maakt een lege builder. */
        public Builder() {
        }

        /**
         * Maakt een initieel gevulde builder.
         * 
         * @param inhoud
         *            initiele inhoud
         */
        public Builder(final Lo3VerblijfplaatsInhoud inhoud) {
            gemeenteInschrijving = inhoud.gemeenteInschrijving;
            datumInschrijving = inhoud.datumInschrijving;
            functieAdres = inhoud.functieAdres;
            gemeenteDeel = inhoud.gemeenteDeel;
            aanvangAdreshouding = inhoud.aanvangAdreshouding;
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
            landWaarnaarVertrokken = inhoud.landWaarnaarVertrokken;
            vertrekUitNederland = inhoud.vertrekUitNederland;
            adresBuitenland1 = inhoud.adresBuitenland1;
            adresBuitenland2 = inhoud.adresBuitenland2;
            adresBuitenland3 = inhoud.adresBuitenland3;
            landVanwaarIngeschreven = inhoud.landVanwaarIngeschreven;
            vestigingInNederland = inhoud.vestigingInNederland;
            aangifteAdreshouding = inhoud.aangifteAdreshouding;
            indicatieDocument = inhoud.indicatieDocument;
        }

        /**
         * Build.
         * 
         * @return inhoud
         */
        public Lo3VerblijfplaatsInhoud build() {
            return new Lo3VerblijfplaatsInhoud(gemeenteInschrijving, datumInschrijving, functieAdres, gemeenteDeel,
                    aanvangAdreshouding, straatnaam, naamOpenbareRuimte, huisnummer, huisletter,
                    huisnummertoevoeging, aanduidingHuisnummer, postcode, woonplaatsnaam,
                    identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding, locatieBeschrijving,
                    landWaarnaarVertrokken, vertrekUitNederland, adresBuitenland1, adresBuitenland2,
                    adresBuitenland3, landVanwaarIngeschreven, vestigingInNederland, aangifteAdreshouding,
                    indicatieDocument);

        }

        /**
         * @param gemeenteInschrijving
         *            the gemeenteInschrijving to set
         */
        public void setGemeenteInschrijving(final Lo3GemeenteCode gemeenteInschrijving) {
            this.gemeenteInschrijving = gemeenteInschrijving;
        }

        /**
         * @param datumInschrijving
         *            the datumInschrijving to set
         */
        public void setDatumInschrijving(final Lo3Datum datumInschrijving) {
            this.datumInschrijving = datumInschrijving;
        }

        /**
         * @param functieAdres
         *            the functieAdres to set
         */
        public void setFunctieAdres(final Lo3FunctieAdres functieAdres) {
            this.functieAdres = functieAdres;
        }

        /**
         * @param gemeenteDeel
         *            the gemeenteDeel to set
         */
        public void setGemeenteDeel(final String gemeenteDeel) {
            this.gemeenteDeel = gemeenteDeel;
        }

        /**
         * @param aanvangAdreshouding
         *            the aanvangAdreshouding to set
         */
        public void setAanvangAdreshouding(final Lo3Datum aanvangAdreshouding) {
            this.aanvangAdreshouding = aanvangAdreshouding;
        }

        /**
         * @param straatnaam
         *            the straatnaam to set
         */
        public void setStraatnaam(final String straatnaam) {
            this.straatnaam = straatnaam;
        }

        /**
         * @param naamOpenbareRuimte
         *            the naamOpenbareRuimte to set
         */
        public void setNaamOpenbareRuimte(final String naamOpenbareRuimte) {
            this.naamOpenbareRuimte = naamOpenbareRuimte;
        }

        /**
         * @param huisnummer
         *            the huisnummer to set
         */
        public void setHuisnummer(final Lo3Huisnummer huisnummer) {
            this.huisnummer = huisnummer;
        }

        /**
         * @param huisletter
         *            the huisletter to set
         */
        public void setHuisletter(final Character huisletter) {
            this.huisletter = huisletter;
        }

        /**
         * @param huisnummertoevoeging
         *            the huisnummertoevoeging to set
         */
        public void setHuisnummertoevoeging(final String huisnummertoevoeging) {
            this.huisnummertoevoeging = huisnummertoevoeging;
        }

        /**
         * @param aanduidingHuisnummer
         *            the aanduidingHuisnummer to set
         */
        public void setAanduidingHuisnummer(final Lo3AanduidingHuisnummer aanduidingHuisnummer) {
            this.aanduidingHuisnummer = aanduidingHuisnummer;
        }

        /**
         * @param postcode
         *            the postcode to set
         */
        public void setPostcode(final String postcode) {
            this.postcode = postcode;
        }

        /**
         * @param woonplaatsnaam
         *            the woonplaatsnaam to set
         */
        public void setWoonplaatsnaam(final String woonplaatsnaam) {
            this.woonplaatsnaam = woonplaatsnaam;
        }

        /**
         * @param identificatiecodeVerblijfplaats
         *            the identificatiecodeVerblijfplaats to set
         */
        public void setIdentificatiecodeVerblijfplaats(final String identificatiecodeVerblijfplaats) {
            this.identificatiecodeVerblijfplaats = identificatiecodeVerblijfplaats;
        }

        /**
         * @param identificatiecodeNummeraanduiding
         *            the identificatiecodeNummeraanduiding to set
         */
        public void setIdentificatiecodeNummeraanduiding(final String identificatiecodeNummeraanduiding) {
            this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        }

        /**
         * @param locatieBeschrijving
         *            the locatieBeschrijving to set
         */
        public void setLocatieBeschrijving(final String locatieBeschrijving) {
            this.locatieBeschrijving = locatieBeschrijving;
        }

        /**
         * @param landWaarnaarVertrokken
         *            the landWaarnaarVertrokken to set
         */
        public void setLandWaarnaarVertrokken(final Lo3LandCode landWaarnaarVertrokken) {
            this.landWaarnaarVertrokken = landWaarnaarVertrokken;
        }

        /**
         * @param vertrekUitNederland
         *            the vertrekUitNederland to set
         */
        public void setVertrekUitNederland(final Lo3Datum vertrekUitNederland) {
            this.vertrekUitNederland = vertrekUitNederland;
        }

        /**
         * @param adresBuitenland1
         *            the adresBuitenland1 to set
         */
        public void setAdresBuitenland1(final String adresBuitenland1) {
            this.adresBuitenland1 = adresBuitenland1;
        }

        /**
         * @param adresBuitenland2
         *            the adresBuitenland2 to set
         */
        public void setAdresBuitenland2(final String adresBuitenland2) {
            this.adresBuitenland2 = adresBuitenland2;
        }

        /**
         * @param adresBuitenland3
         *            the adresBuitenland3 to set
         */
        public void setAdresBuitenland3(final String adresBuitenland3) {
            this.adresBuitenland3 = adresBuitenland3;
        }

        /**
         * @param landVanwaarIngeschreven
         *            the landVanwaarIngeschreven to set
         */
        public void setLandVanwaarIngeschreven(final Lo3LandCode landVanwaarIngeschreven) {
            this.landVanwaarIngeschreven = landVanwaarIngeschreven;
        }

        /**
         * @param vestigingInNederland
         *            the vestigingInNederland to set
         */
        public void setVestigingInNederland(final Lo3Datum vestigingInNederland) {
            this.vestigingInNederland = vestigingInNederland;
        }

        /**
         * @param aangifteAdreshouding
         *            the aangifteAdreshouding to set
         */
        public void setAangifteAdreshouding(final Lo3AangifteAdreshouding aangifteAdreshouding) {
            this.aangifteAdreshouding = aangifteAdreshouding;
        }

        /**
         * @param indicatieDocument
         *            the indicatieDocument to set
         */
        public void setIndicatieDocument(final Lo3IndicatieDocument indicatieDocument) {
            this.indicatieDocument = indicatieDocument;
        }

    }

}

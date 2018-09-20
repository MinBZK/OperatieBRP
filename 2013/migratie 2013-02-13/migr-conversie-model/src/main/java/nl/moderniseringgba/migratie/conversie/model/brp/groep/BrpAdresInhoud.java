/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import nl.moderniseringgba.migratie.Preconditie;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.groep.FoutmeldingUtil;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de BRP groep Adres/Standaard. Deze groep is de samenvoeging van de groepen
 * BrpAdresHouding, BrpNederlandsAdres en BrpBuitenlandsAdres.
 * 
 * Deze class is immutable en threadsafe.
 * 
 */
public final class BrpAdresInhoud extends AbstractBrpGroepInhoud {

    private static final int MAX_BUITENLANDS_ADRES_REGEL_LENGTE = 35;

    @Element(name = "functieAdresCode", required = false)
    private final BrpFunctieAdresCode functieAdresCode;
    @Element(name = "redenWijzigingAdresCode", required = false)
    private final BrpRedenWijzigingAdresCode redenWijzigingAdresCode;
    @Element(name = "aangeverAdreshoudingCode", required = false)
    private final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode;
    @Element(name = "datumAanvangAdreshouding", required = false)
    private final BrpDatum datumAanvangAdreshouding;

    @Element(name = "adresseerbaarObject", required = false)
    private final String adresseerbaarObject; // Maximum lengte 16
    @Element(name = "identificatiecodeNummeraanduiding", required = false)
    private final String identificatiecodeNummeraanduiding; // Maximum lengte 16
    @Element(name = "gemeenteCode", required = false)
    private final BrpGemeenteCode gemeenteCode; // Verplicht
    @Element(name = "naamOpenbareRuimte", required = false)
    private final String naamOpenbareRuimte; // Maximum lengte 80
    @Element(name = "afgekorteNaamOpenbareRuimte", required = false)
    private final String afgekorteNaamOpenbareRuimte; // Maximum lengte 24
    @Element(name = "gemeentedeel", required = false)
    private final String gemeentedeel; // Maximum lengte 24
    @Element(name = "huisnummer", required = false)
    private final Integer huisnummer; // Maximum lengte 5
    @Element(name = "huisletter", required = false)
    private final Character huisletter;
    @Element(name = "huisnummertoevoeging", required = false)
    private final String huisnummertoevoeging; // Maximum lengte 4
    @Element(name = "postcode", required = false)
    private final String postcode; // Maximum lengte 6
    @Element(name = "plaatsCode", required = false)
    private final BrpPlaatsCode plaatsCode;
    @Element(name = "locatieTovAdres", required = false)
    private final BrpAanduidingBijHuisnummerCode locatieTovAdres;
    @Element(name = "locatieOmschrijving", required = false)
    private final String locatieOmschrijving; // Maximum lengte 80

    @Element(name = "buitenlandsAdresRegel1", required = false)
    private final String buitenlandsAdresRegel1; // Maximum lengte 80
    @Element(name = "buitenlandsAdresRegel2", required = false)
    private final String buitenlandsAdresRegel2; // Maximum lengte 80
    @Element(name = "buitenlandsAdresRegel3", required = false)
    private final String buitenlandsAdresRegel3; // Maximum lengte 80
    @Element(name = "buitenlandsAdresRegel4", required = false)
    private final String buitenlandsAdresRegel4; // Maximum lengte 80
    @Element(name = "buitenlandsAdresRegel5", required = false)
    private final String buitenlandsAdresRegel5; // Maximum lengte 80
    @Element(name = "buitenlandsAdresRegel6", required = false)
    private final String buitenlandsAdresRegel6; // Maximum lengte 80
    @Element(name = "landCode", required = false)
    private final BrpLandCode landCode;
    @Element(name = "datumVertrekUitNederland", required = false)
    private final BrpDatum datumVertrekUitNederland;

    /**
     * Maakt een BrpAdresInhoud object.
     * 
     * @param functieAdresCode
     *            functie adres
     * @param redenWijzigingAdresCode
     *            reden wijziging adres
     * @param aangeverAdreshoudingCode
     *            aangever adreshouding
     * @param datumAanvangAdreshouding
     *            datum aanvang adreshouding
     * @param adresseerbaarObject
     *            addressseerbaar object
     * @param identificatiecodeNummeraanduiding
     *            identificatiecode nummeraanduiding
     * @param gemeenteCode
     *            gemeentecode
     * @param naamOpenbareRuimte
     *            naam openbare ruimte
     * @param afgekorteNaamOpenbareRuimte
     *            afgekorte naam openbare ruimte
     * @param gemeentedeel
     *            gemeentedeel
     * @param huisnummer
     *            huisnummer
     * @param huisletter
     *            huisletter
     * @param huisnummertoevoeging
     *            huisnummertoevoeging
     * @param postcode
     *            postcode
     * @param plaatsCode
     *            plaats
     * @param locatieTovAdres
     *            aanduiding bij huisnummer
     * @param locatieOmschrijving
     *            locatie omschrijving
     * @param buitenlandsAdresRegel1
     *            buitenlands adres regel 1
     * @param buitenlandsAdresRegel2
     *            buitenlands adres regel 2
     * @param buitenlandsAdresRegel3
     *            buitenlands adres regel 3
     * @param buitenlandsAdresRegel4
     *            buitenlands adres regel 4
     * @param buitenlandsAdresRegel5
     *            buitenlands adres regel 5
     * @param buitenlandsAdresRegel6
     *            buitenlands adres regel 6
     * @param landCode
     *            land
     * @param datumVertrekUitNederland
     *            datum vertrek uit Nederland
     */
    // CHECKSTYLE:OFF - Meer dan 7 parameters is in constructors van immutable model klassen getolereerd.
    public BrpAdresInhoud(
            @Element(name = "functieAdresCode", required = false) final BrpFunctieAdresCode functieAdresCode,
            @Element(name = "redenWijzigingAdresCode", required = false) final BrpRedenWijzigingAdresCode redenWijzigingAdresCode,
            @Element(name = "aangeverAdreshoudingCode", required = false) final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode,
            @Element(name = "datumAanvangAdreshouding", required = false) final BrpDatum datumAanvangAdreshouding,
            @Element(name = "adresseerbaarObject", required = false) final String adresseerbaarObject,
            @Element(name = "identificatiecodeNummeraanduiding", required = false) final String identificatiecodeNummeraanduiding,
            @Element(name = "gemeenteCode", required = false) final BrpGemeenteCode gemeenteCode,
            @Element(name = "naamOpenbareRuimte", required = false) final String naamOpenbareRuimte,
            @Element(name = "afgekorteNaamOpenbareRuimte", required = false) final String afgekorteNaamOpenbareRuimte,
            @Element(name = "gemeentedeel", required = false) final String gemeentedeel,
            @Element(name = "huisnummer", required = false) final Integer huisnummer,
            @Element(name = "huisletter", required = false) final Character huisletter,
            @Element(name = "huisnummertoevoeging", required = false) final String huisnummertoevoeging,
            @Element(name = "postcode", required = false) final String postcode,
            @Element(name = "plaatsCode", required = false) final BrpPlaatsCode plaatsCode,
            @Element(name = "locatieTovAdres", required = false) final BrpAanduidingBijHuisnummerCode locatieTovAdres,
            @Element(name = "locatieOmschrijving", required = false) final String locatieOmschrijving,
            @Element(name = "buitenlandsAdresRegel1", required = false) final String buitenlandsAdresRegel1,
            @Element(name = "buitenlandsAdresRegel2", required = false) final String buitenlandsAdresRegel2,
            @Element(name = "buitenlandsAdresRegel3", required = false) final String buitenlandsAdresRegel3,
            @Element(name = "buitenlandsAdresRegel4", required = false) final String buitenlandsAdresRegel4,
            @Element(name = "buitenlandsAdresRegel5", required = false) final String buitenlandsAdresRegel5,
            @Element(name = "buitenlandsAdresRegel6", required = false) final String buitenlandsAdresRegel6,
            @Element(name = "landCode", required = false) final BrpLandCode landCode,
            @Element(name = "datumVertrekUitNederland", required = false) final BrpDatum datumVertrekUitNederland) {
        // CHECKSTYLE:ON
        this.functieAdresCode = functieAdresCode;
        this.redenWijzigingAdresCode = redenWijzigingAdresCode;
        this.aangeverAdreshoudingCode = aangeverAdreshoudingCode;
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        this.adresseerbaarObject = adresseerbaarObject;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.gemeenteCode = gemeenteCode;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        this.gemeentedeel = gemeentedeel;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.postcode = postcode;
        this.plaatsCode = plaatsCode;
        this.locatieTovAdres = locatieTovAdres;
        this.locatieOmschrijving = locatieOmschrijving;
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
        this.landCode = landCode;
        this.datumVertrekUitNederland = datumVertrekUitNederland;
    }

    @Override
    @Preconditie({ Precondities.PRE013, Precondities.PRE014 })
    public void valideer() {
        // PRE013
        if (buitenlandsAdresRegel4 != null || buitenlandsAdresRegel5 != null || buitenlandsAdresRegel6 != null) {
            FoutmeldingUtil.gooiValidatieExceptie("Een van de buitenlands adresregels 4, 5 of 6 is gevuld",
                    Precondities.PRE013);
        }
        // PRE014
        valideerTekstLengte(buitenlandsAdresRegel1, 0, MAX_BUITENLANDS_ADRES_REGEL_LENGTE, false,
                "Buitenlands adres regel 1");
        valideerTekstLengte(buitenlandsAdresRegel2, 0, MAX_BUITENLANDS_ADRES_REGEL_LENGTE, false,
                "Buitenlands adres regel 2");
        valideerTekstLengte(buitenlandsAdresRegel3, 0, MAX_BUITENLANDS_ADRES_REGEL_LENGTE, false,
                "Buitenlands adres regel 3");
    }

    private static void valideerTekstLengte(
            final String tekst,
            final int minimumLengte,
            final int maximumLengte,
            final boolean verplicht,
            final String naam) {
        if (tekst == null) {
            if (verplicht) {
                FoutmeldingUtil.gooiVerplichtMaarNietGevuldFoutmelding(naam);
            }
            return;
        }

        if (tekst.length() < minimumLengte || tekst.length() > maximumLengte) {
            throw new IllegalArgumentException(naam + " moet minimaal " + minimumLengte + " lang en maximaal "
                    + maximumLengte + " lang zijn, maar is " + tekst.length() + " lang: " + tekst);
        }
    }

    @Override
    public boolean isLeeg() {
        return !ValidationUtils.isEenParameterGevuld(redenWijzigingAdresCode, aangeverAdreshoudingCode,
                datumAanvangAdreshouding, adresseerbaarObject, identificatiecodeNummeraanduiding, gemeenteCode,
                naamOpenbareRuimte, afgekorteNaamOpenbareRuimte, gemeentedeel, huisnummer, huisletter,
                huisnummertoevoeging, postcode, plaatsCode, locatieTovAdres, locatieOmschrijving,
                buitenlandsAdresRegel1, buitenlandsAdresRegel2, buitenlandsAdresRegel3, buitenlandsAdresRegel4,
                buitenlandsAdresRegel5, buitenlandsAdresRegel6, landCode, datumVertrekUitNederland);
    }

    public BrpFunctieAdresCode getFunctieAdresCode() {
        return functieAdresCode;
    }

    public BrpRedenWijzigingAdresCode getRedenWijzigingAdresCode() {
        return redenWijzigingAdresCode;
    }

    public BrpAangeverAdreshoudingCode getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    public BrpDatum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    public String getAdresseerbaarObject() {
        return adresseerbaarObject;
    }

    public String getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    public String getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    public String getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    public String getGemeentedeel() {
        return gemeentedeel;
    }

    public Integer getHuisnummer() {
        return huisnummer;
    }

    public Character getHuisletter() {
        return huisletter;
    }

    public String getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public BrpPlaatsCode getPlaatsCode() {
        return plaatsCode;
    }

    public BrpAanduidingBijHuisnummerCode getLocatieTovAdres() {
        return locatieTovAdres;
    }

    public String getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    public String getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    public String getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    public String getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    public String getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    public String getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    public String getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    public BrpLandCode getLandCode() {
        return landCode;
    }

    public BrpDatum getDatumVertrekUitNederland() {
        return datumVertrekUitNederland;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpAdresInhoud)) {
            return false;
        }
        final BrpAdresInhoud castOther = (BrpAdresInhoud) other;
        return new EqualsBuilder().append(functieAdresCode, castOther.functieAdresCode)
                .append(redenWijzigingAdresCode, castOther.redenWijzigingAdresCode)
                .append(aangeverAdreshoudingCode, castOther.aangeverAdreshoudingCode)
                .append(datumAanvangAdreshouding, castOther.datumAanvangAdreshouding)
                .append(adresseerbaarObject, castOther.adresseerbaarObject)
                .append(identificatiecodeNummeraanduiding, castOther.identificatiecodeNummeraanduiding)
                .append(gemeenteCode, castOther.gemeenteCode)
                .append(naamOpenbareRuimte, castOther.naamOpenbareRuimte)
                .append(afgekorteNaamOpenbareRuimte, castOther.afgekorteNaamOpenbareRuimte)
                .append(gemeentedeel, castOther.gemeentedeel).append(huisnummer, castOther.huisnummer)
                .append(huisletter, castOther.huisletter)
                .append(huisnummertoevoeging, castOther.huisnummertoevoeging).append(postcode, castOther.postcode)
                .append(plaatsCode, castOther.plaatsCode).append(locatieTovAdres, castOther.locatieTovAdres)
                .append(locatieOmschrijving, castOther.locatieOmschrijving)
                .append(buitenlandsAdresRegel1, castOther.buitenlandsAdresRegel1)
                .append(buitenlandsAdresRegel2, castOther.buitenlandsAdresRegel2)
                .append(buitenlandsAdresRegel3, castOther.buitenlandsAdresRegel3)
                .append(buitenlandsAdresRegel4, castOther.buitenlandsAdresRegel4)
                .append(buitenlandsAdresRegel5, castOther.buitenlandsAdresRegel5)
                .append(buitenlandsAdresRegel6, castOther.buitenlandsAdresRegel6)
                .append(landCode, castOther.landCode)
                .append(datumVertrekUitNederland, castOther.datumVertrekUitNederland).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(functieAdresCode).append(redenWijzigingAdresCode)
                .append(aangeverAdreshoudingCode).append(datumAanvangAdreshouding).append(adresseerbaarObject)
                .append(identificatiecodeNummeraanduiding).append(gemeenteCode).append(naamOpenbareRuimte)
                .append(afgekorteNaamOpenbareRuimte).append(gemeentedeel).append(huisnummer).append(huisletter)
                .append(huisnummertoevoeging).append(postcode).append(plaatsCode).append(locatieTovAdres)
                .append(locatieOmschrijving).append(buitenlandsAdresRegel1).append(buitenlandsAdresRegel2)
                .append(buitenlandsAdresRegel3).append(buitenlandsAdresRegel4).append(buitenlandsAdresRegel5)
                .append(buitenlandsAdresRegel6).append(landCode).append(datumVertrekUitNederland).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("functieAdresCode", functieAdresCode)
                .append("redenWijzigingAdresCode", redenWijzigingAdresCode)
                .append("aangeverAdreshoudingCode", aangeverAdreshoudingCode)
                .append("datumAanvangAdreshouding", datumAanvangAdreshouding)
                .append("adresseerbaarObject", adresseerbaarObject)
                .append("identificatiecodeNummeraanduiding", identificatiecodeNummeraanduiding)
                .append("gemeenteCode", gemeenteCode).append("naamOpenbareRuimte", naamOpenbareRuimte)
                .append("afgekorteNaamOpenbareRuimte", afgekorteNaamOpenbareRuimte)
                .append("gemeentedeel", gemeentedeel).append("huisnummer", huisnummer)
                .append("huisletter", huisletter).append("huisnummertoevoeging", huisnummertoevoeging)
                .append("postcode", postcode).append("plaatsCode", plaatsCode)
                .append("locatietovAdres", locatieTovAdres).append("locatieOmschrijving", locatieOmschrijving)
                .append("buitenlandsAdresRegel1", buitenlandsAdresRegel1)
                .append("buitenlandsAdresRegel2", buitenlandsAdresRegel2)
                .append("buitenlandsAdresRegel3", buitenlandsAdresRegel3)
                .append("buitenlandsAdresRegel4", buitenlandsAdresRegel4)
                .append("buitenlandsAdresRegel5", buitenlandsAdresRegel5)
                .append("buitenlandsAdresRegel6", buitenlandsAdresRegel6).append("landCode", landCode)
                .append("datumVertrekUitNederland", datumVertrekUitNederland).toString();
    }

}

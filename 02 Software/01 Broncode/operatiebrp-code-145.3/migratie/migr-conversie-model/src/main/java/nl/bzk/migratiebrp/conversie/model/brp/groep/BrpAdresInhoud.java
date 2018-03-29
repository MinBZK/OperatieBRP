/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de BRP groep Persoon/Adres
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpAdresInhoud extends AbstractBrpGroepInhoud {

    private static final int MAX_BUITENLANDS_ADRES_REGEL_LENGTE = 35;

    @Element(name = "soortAdresCode", required = true)
    private final BrpSoortAdresCode soortAdresCode;
    @Element(name = "redenWijzigingAdresCode", required = false)
    private final BrpRedenWijzigingVerblijfCode redenWijzigingAdresCode;
    @Element(name = "aangeverAdreshoudingCode", required = false)
    private final BrpAangeverCode aangeverAdreshoudingCode;
    @Element(name = "datumAanvangAdreshouding", required = false)
    private final BrpDatum datumAanvangAdreshouding;

    @Element(name = "identificatiecodeAdresseerbaarObject", required = false)
    private final BrpString identificatiecodeAdresseerbaarObject;
    @Element(name = "identificatiecodeNummeraanduiding", required = false)
    private final BrpString identificatiecodeNummeraanduiding;
    @Element(name = "gemeenteCode", required = false)
    private final BrpGemeenteCode gemeenteCode;
    @Element(name = "naamOpenbareRuimte", required = false)
    private final BrpString naamOpenbareRuimte;
    @Element(name = "afgekorteNaamOpenbareRuimte", required = false)
    private final BrpString afgekorteNaamOpenbareRuimte;
    @Element(name = "gemeentedeel", required = false)
    private final BrpString gemeentedeel;
    @Element(name = "huisnummer", required = false)
    private final BrpInteger huisnummer;
    @Element(name = "huisletter", required = false)
    private final BrpCharacter huisletter;
    @Element(name = "huisnummertoevoeging", required = false)
    private final BrpString huisnummertoevoeging;
    @Element(name = "postcode", required = false)
    private final BrpString postcode;
    @Element(name = "woonplaatsnaam", required = false)
    private final BrpString woonplaatsnaam;
    @Element(name = "locatieTovAdres", required = false)
    private final BrpAanduidingBijHuisnummerCode locatieTovAdres;
    @Element(name = "locatieOmschrijving", required = false)
    private final BrpString locatieOmschrijving;

    @Element(name = "buitenlandsAdresRegel1", required = false)
    private final BrpString buitenlandsAdresRegel1;
    @Element(name = "buitenlandsAdresRegel2", required = false)
    private final BrpString buitenlandsAdresRegel2;
    @Element(name = "buitenlandsAdresRegel3", required = false)
    private final BrpString buitenlandsAdresRegel3;
    @Element(name = "buitenlandsAdresRegel4", required = false)
    private final BrpString buitenlandsAdresRegel4;
    @Element(name = "buitenlandsAdresRegel5", required = false)
    private final BrpString buitenlandsAdresRegel5;
    @Element(name = "buitenlandsAdresRegel6", required = false)
    private final BrpString buitenlandsAdresRegel6;
    @Element(name = "landOfGebiedCode", required = false)
    private final BrpLandOfGebiedCode landOfGebiedCode;
    @Element(name = "indicatiePersoonAangetroffenOpAdres", required = false)
    private final BrpBoolean indicatiePersoonAangetroffenOpAdres;

    /**
     * Maakt een BrpAdresInhoud object.
     * @param soortAdresCode soort adres
     * @param redenWijzigingAdresCode reden wijziging adres
     * @param aangeverAdreshoudingCode aangever adreshouding
     * @param datumAanvangAdreshouding datum aanvang adreshouding
     * @param identificatiecodeAdresseerbaarObject addressseerbaar object
     * @param identificatiecodeNummeraanduiding identificatiecode nummeraanduiding
     * @param gemeenteCode gemeentecode
     * @param naamOpenbareRuimte naam openbare ruimte
     * @param afgekorteNaamOpenbareRuimte afgekorte naam openbare ruimte
     * @param gemeentedeel gemeentedeel
     * @param huisnummer huisnummer
     * @param huisletter huisletter
     * @param huisnummertoevoeging huisnummertoevoeging
     * @param postcode postcode
     * @param woonplaatsnaam plaats
     * @param locatieTovAdres aanduiding bij huisnummer
     * @param locatieOmschrijving locatie omschrijving
     * @param buitenlandsAdresRegel1 buitenlands adres regel 1
     * @param buitenlandsAdresRegel2 buitenlands adres regel 2
     * @param buitenlandsAdresRegel3 buitenlands adres regel 3
     * @param buitenlandsAdresRegel4 buitenlands adres regel 4
     * @param buitenlandsAdresRegel5 buitenlands adres regel 5
     * @param buitenlandsAdresRegel6 buitenlands adres regel 6
     * @param landOfGebiedCode land of gebied code
     * @param indicatiePersoonAangetroffenOpAdres indicatie persoon aangetroffen op adres
     */
    public BrpAdresInhoud(
        /* Meer dan 7 parameters is in constructors van immutable model klassen getolereerd. */
        @Element(name = "soortAdresCode", required = true) final BrpSoortAdresCode soortAdresCode,
        @Element(name = "redenWijzigingAdresCode", required = false) final BrpRedenWijzigingVerblijfCode redenWijzigingAdresCode,
        @Element(name = "aangeverAdreshoudingCode", required = false) final BrpAangeverCode aangeverAdreshoudingCode,
        @Element(name = "datumAanvangAdreshouding", required = false) final BrpDatum datumAanvangAdreshouding,
        @Element(name = "identificatiecodeAdresseerbaarObject", required = false) final BrpString identificatiecodeAdresseerbaarObject,
        @Element(name = "identificatiecodeNummeraanduiding", required = false) final BrpString identificatiecodeNummeraanduiding,
        @Element(name = "gemeenteCode", required = false) final BrpGemeenteCode gemeenteCode,
        @Element(name = "naamOpenbareRuimte", required = false) final BrpString naamOpenbareRuimte,
        @Element(name = "afgekorteNaamOpenbareRuimte", required = false) final BrpString afgekorteNaamOpenbareRuimte,
        @Element(name = "gemeentedeel", required = false) final BrpString gemeentedeel,
        @Element(name = "huisnummer", required = false) final BrpInteger huisnummer,
        @Element(name = "huisletter", required = false) final BrpCharacter huisletter,
        @Element(name = "huisnummertoevoeging", required = false) final BrpString huisnummertoevoeging,
        @Element(name = "postcode", required = false) final BrpString postcode,
        @Element(name = "woonplaatsnaam", required = false) final BrpString woonplaatsnaam,
        @Element(name = "locatieTovAdres", required = false) final BrpAanduidingBijHuisnummerCode locatieTovAdres,
        @Element(name = "locatieOmschrijving", required = false) final BrpString locatieOmschrijving,
        @Element(name = "buitenlandsAdresRegel1", required = false) final BrpString buitenlandsAdresRegel1,
        @Element(name = "buitenlandsAdresRegel2", required = false) final BrpString buitenlandsAdresRegel2,
        @Element(name = "buitenlandsAdresRegel3", required = false) final BrpString buitenlandsAdresRegel3,
        @Element(name = "buitenlandsAdresRegel4", required = false) final BrpString buitenlandsAdresRegel4,
        @Element(name = "buitenlandsAdresRegel5", required = false) final BrpString buitenlandsAdresRegel5,
        @Element(name = "buitenlandsAdresRegel6", required = false) final BrpString buitenlandsAdresRegel6,
        @Element(name = "landOfGebiedCode", required = false) final BrpLandOfGebiedCode landOfGebiedCode,
        @Element(name = "indicatiePersoonAangetroffenOpAdres", required = false) final BrpBoolean indicatiePersoonAangetroffenOpAdres) {
        this.soortAdresCode = soortAdresCode;
        this.redenWijzigingAdresCode = redenWijzigingAdresCode;
        this.aangeverAdreshoudingCode = aangeverAdreshoudingCode;
        this.datumAanvangAdreshouding = datumAanvangAdreshouding;
        this.identificatiecodeAdresseerbaarObject = identificatiecodeAdresseerbaarObject;
        this.identificatiecodeNummeraanduiding = identificatiecodeNummeraanduiding;
        this.gemeenteCode = gemeenteCode;
        this.naamOpenbareRuimte = naamOpenbareRuimte;
        this.afgekorteNaamOpenbareRuimte = afgekorteNaamOpenbareRuimte;
        this.gemeentedeel = gemeentedeel;
        this.huisnummer = huisnummer;
        this.huisletter = huisletter;
        this.huisnummertoevoeging = huisnummertoevoeging;
        this.postcode = postcode;
        this.woonplaatsnaam = woonplaatsnaam;
        this.locatieTovAdres = locatieTovAdres;
        this.locatieOmschrijving = locatieOmschrijving;
        this.buitenlandsAdresRegel1 = buitenlandsAdresRegel1;
        this.buitenlandsAdresRegel2 = buitenlandsAdresRegel2;
        this.buitenlandsAdresRegel3 = buitenlandsAdresRegel3;
        this.buitenlandsAdresRegel4 = buitenlandsAdresRegel4;
        this.buitenlandsAdresRegel5 = buitenlandsAdresRegel5;
        this.buitenlandsAdresRegel6 = buitenlandsAdresRegel6;
        this.landOfGebiedCode = landOfGebiedCode;
        this.indicatiePersoonAangetroffenOpAdres = indicatiePersoonAangetroffenOpAdres;
    }

    private BrpAdresInhoud(final Builder builder) {
        soortAdresCode = builder.soortAdresCode;
        redenWijzigingAdresCode = builder.redenWijzigingAdresCode;
        aangeverAdreshoudingCode = builder.aangeverAdreshoudingCode;
        datumAanvangAdreshouding = builder.datumAanvangAdreshouding;
        identificatiecodeAdresseerbaarObject = builder.identificatiecodeAdresseerbaarObject;
        identificatiecodeNummeraanduiding = builder.identificatiecodeNummeraanduiding;
        gemeenteCode = builder.gemeenteCode;
        naamOpenbareRuimte = builder.naamOpenbareRuimte;
        afgekorteNaamOpenbareRuimte = builder.afgekorteNaamOpenbareRuimte;
        gemeentedeel = builder.gemeentedeel;
        huisnummer = builder.huisnummer;
        huisletter = builder.huisletter;
        huisnummertoevoeging = builder.huisnummertoevoeging;
        postcode = builder.postcode;
        woonplaatsnaam = builder.woonplaatsnaam;
        locatieTovAdres = builder.locatieTovAdres;
        locatieOmschrijving = builder.locatieOmschrijving;
        buitenlandsAdresRegel1 = builder.buitenlandsAdresRegel1;
        buitenlandsAdresRegel2 = builder.buitenlandsAdresRegel2;
        buitenlandsAdresRegel3 = builder.buitenlandsAdresRegel3;
        buitenlandsAdresRegel4 = builder.buitenlandsAdresRegel4;
        buitenlandsAdresRegel5 = builder.buitenlandsAdresRegel5;
        buitenlandsAdresRegel6 = builder.buitenlandsAdresRegel6;
        landOfGebiedCode = builder.landOfGebiedCode;
        indicatiePersoonAangetroffenOpAdres = builder.indicatiePersoonAangetroffenOpAdres;
    }

    @Preconditie({SoortMeldingCode.PRE013, SoortMeldingCode.PRE014})
    @Override
    public void valideer() {
        // PRE013
        if (BrpString.unwrap(buitenlandsAdresRegel4) != null
                || BrpString.unwrap(buitenlandsAdresRegel5) != null
                || BrpString.unwrap(buitenlandsAdresRegel6) != null) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE013, this);
        }
        // PRE014
        valideerTekstLengte(BrpString.unwrap(buitenlandsAdresRegel1));
        valideerTekstLengte(BrpString.unwrap(buitenlandsAdresRegel2));
        valideerTekstLengte(BrpString.unwrap(buitenlandsAdresRegel3));
    }

    private void valideerTekstLengte(final String tekst) {
        if (tekst == null) {
            return;
        }

        if (tekst.length() > MAX_BUITENLANDS_ADRES_REGEL_LENGTE) {
            FoutmeldingUtil.gooiValidatieExceptie(SoortMeldingCode.PRE014, this);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return !BrpValidatie.isEenParameterGevuld(
                redenWijzigingAdresCode,
                aangeverAdreshoudingCode,
                datumAanvangAdreshouding,
                identificatiecodeAdresseerbaarObject,
                identificatiecodeNummeraanduiding,
                gemeenteCode,
                naamOpenbareRuimte,
                afgekorteNaamOpenbareRuimte,
                gemeentedeel,
                huisnummer,
                huisletter,
                huisnummertoevoeging,
                postcode,
                woonplaatsnaam,
                locatieTovAdres,
                locatieOmschrijving,
                buitenlandsAdresRegel1,
                buitenlandsAdresRegel2,
                buitenlandsAdresRegel3,
                buitenlandsAdresRegel4,
                buitenlandsAdresRegel5,
                buitenlandsAdresRegel6,
                landOfGebiedCode);
    }

    /**
     * Geef de waarde van soort adres code van BrpAdresInhoud.
     * @return de waarde van soort adres code van BrpAdresInhoud
     */
    public BrpSoortAdresCode getSoortAdresCode() {
        return soortAdresCode;
    }

    /**
     * Geef de waarde van reden wijziging adres code van BrpAdresInhoud.
     * @return de waarde van reden wijziging adres code van BrpAdresInhoud
     */
    public BrpRedenWijzigingVerblijfCode getRedenWijzigingAdresCode() {
        return redenWijzigingAdresCode;
    }

    /**
     * Geef de waarde van aangever adreshouding code van BrpAdresInhoud.
     * @return de waarde van aangever adreshouding code van BrpAdresInhoud
     */
    public BrpAangeverCode getAangeverAdreshoudingCode() {
        return aangeverAdreshoudingCode;
    }

    /**
     * Geef de waarde van datum aanvang adreshouding van BrpAdresInhoud.
     * @return de waarde van datum aanvang adreshouding van BrpAdresInhoud
     */
    public BrpDatum getDatumAanvangAdreshouding() {
        return datumAanvangAdreshouding;
    }

    /**
     * Geef de waarde van identificatiecode adresseerbaar object van BrpAdresInhoud.
     * @return de waarde van identificatiecode adresseerbaar object van BrpAdresInhoud
     */
    public BrpString getIdentificatiecodeAdresseerbaarObject() {
        return identificatiecodeAdresseerbaarObject;
    }

    /**
     * Geef de waarde van identificatiecode nummeraanduiding van BrpAdresInhoud.
     * @return de waarde van identificatiecode nummeraanduiding van BrpAdresInhoud
     */
    public BrpString getIdentificatiecodeNummeraanduiding() {
        return identificatiecodeNummeraanduiding;
    }

    /**
     * Geef de waarde van gemeente code van BrpAdresInhoud.
     * @return de waarde van gemeente code van BrpAdresInhoud
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van naam openbare ruimte van BrpAdresInhoud.
     * @return de waarde van naam openbare ruimte van BrpAdresInhoud
     */
    public BrpString getNaamOpenbareRuimte() {
        return naamOpenbareRuimte;
    }

    /**
     * Geef de waarde van afgekorte naam openbare ruimte van BrpAdresInhoud.
     * @return de waarde van afgekorte naam openbare ruimte van BrpAdresInhoud
     */
    public BrpString getAfgekorteNaamOpenbareRuimte() {
        return afgekorteNaamOpenbareRuimte;
    }

    /**
     * Geef de waarde van gemeentedeel van BrpAdresInhoud.
     * @return de waarde van gemeentedeel van BrpAdresInhoud
     */
    public BrpString getGemeentedeel() {
        return gemeentedeel;
    }

    /**
     * Geef de waarde van huisnummer van BrpAdresInhoud.
     * @return de waarde van huisnummer van BrpAdresInhoud
     */
    public BrpInteger getHuisnummer() {
        return huisnummer;
    }

    /**
     * Geef de waarde van huisletter van BrpAdresInhoud.
     * @return de waarde van huisletter van BrpAdresInhoud
     */
    public BrpCharacter getHuisletter() {
        return huisletter;
    }

    /**
     * Geef de waarde van huisnummertoevoeging van BrpAdresInhoud.
     * @return de waarde van huisnummertoevoeging van BrpAdresInhoud
     */
    public BrpString getHuisnummertoevoeging() {
        return huisnummertoevoeging;
    }

    /**
     * Geef de waarde van postcode van BrpAdresInhoud.
     * @return de waarde van postcode van BrpAdresInhoud
     */
    public BrpString getPostcode() {
        return postcode;
    }

    /**
     * Geef de waarde van woonplaatsnaam van BrpAdresInhoud.
     * @return de waarde van woonplaatsnaam van BrpAdresInhoud
     */
    public BrpString getWoonplaatsnaam() {
        return woonplaatsnaam;
    }

    /**
     * Geef de waarde van locatie tov adres van BrpAdresInhoud.
     * @return de waarde van locatie tov adres van BrpAdresInhoud
     */
    public BrpAanduidingBijHuisnummerCode getLocatieTovAdres() {
        return locatieTovAdres;
    }

    /**
     * Geef de waarde van locatie omschrijving van BrpAdresInhoud.
     * @return de waarde van locatie omschrijving van BrpAdresInhoud
     */
    public BrpString getLocatieOmschrijving() {
        return locatieOmschrijving;
    }

    /**
     * Geef de waarde van buitenlands adres regel1 van BrpAdresInhoud.
     * @return de waarde van buitenlands adres regel1 van BrpAdresInhoud
     */
    public BrpString getBuitenlandsAdresRegel1() {
        return buitenlandsAdresRegel1;
    }

    /**
     * Geef de waarde van buitenlands adres regel2 van BrpAdresInhoud.
     * @return de waarde van buitenlands adres regel2 van BrpAdresInhoud
     */
    public BrpString getBuitenlandsAdresRegel2() {
        return buitenlandsAdresRegel2;
    }

    /**
     * Geef de waarde van buitenlands adres regel3 van BrpAdresInhoud.
     * @return de waarde van buitenlands adres regel3 van BrpAdresInhoud
     */
    public BrpString getBuitenlandsAdresRegel3() {
        return buitenlandsAdresRegel3;
    }

    /**
     * Geef de waarde van buitenlands adres regel4 van BrpAdresInhoud.
     * @return de waarde van buitenlands adres regel4 van BrpAdresInhoud
     */
    public BrpString getBuitenlandsAdresRegel4() {
        return buitenlandsAdresRegel4;
    }

    /**
     * Geef de waarde van buitenlands adres regel5 van BrpAdresInhoud.
     * @return de waarde van buitenlands adres regel5 van BrpAdresInhoud
     */
    public BrpString getBuitenlandsAdresRegel5() {
        return buitenlandsAdresRegel5;
    }

    /**
     * Geef de waarde van buitenlands adres regel6 van BrpAdresInhoud.
     * @return de waarde van buitenlands adres regel6 van BrpAdresInhoud
     */
    public BrpString getBuitenlandsAdresRegel6() {
        return buitenlandsAdresRegel6;
    }

    /**
     * Geef de waarde van land of gebied code van BrpAdresInhoud.
     * @return de waarde van land of gebied code van BrpAdresInhoud
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }

    /**
     * Geef de waarde van indicatie persoon aangetroffen op adres van BrpAdresInhoud.
     * @return de waarde van indicatie persoon aangetroffen op adres van BrpAdresInhoud
     */
    public BrpBoolean getIndicatiePersoonAangetroffenOpAdres() {
        return indicatiePersoonAangetroffenOpAdres;
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
        return new EqualsBuilder().append(soortAdresCode, castOther.soortAdresCode)
                .append(redenWijzigingAdresCode, castOther.redenWijzigingAdresCode)
                .append(aangeverAdreshoudingCode, castOther.aangeverAdreshoudingCode)
                .append(datumAanvangAdreshouding, castOther.datumAanvangAdreshouding)
                .append(identificatiecodeAdresseerbaarObject, castOther.identificatiecodeAdresseerbaarObject)
                .append(identificatiecodeNummeraanduiding, castOther.identificatiecodeNummeraanduiding)
                .append(gemeenteCode, castOther.gemeenteCode)
                .append(naamOpenbareRuimte, castOther.naamOpenbareRuimte)
                .append(afgekorteNaamOpenbareRuimte, castOther.afgekorteNaamOpenbareRuimte)
                .append(gemeentedeel, castOther.gemeentedeel)
                .append(huisnummer, castOther.huisnummer)
                .append(huisletter, castOther.huisletter)
                .append(huisnummertoevoeging, castOther.huisnummertoevoeging)
                .append(postcode, castOther.postcode)
                .append(woonplaatsnaam, castOther.woonplaatsnaam)
                .append(locatieTovAdres, castOther.locatieTovAdres)
                .append(locatieOmschrijving, castOther.locatieOmschrijving)
                .append(buitenlandsAdresRegel1, castOther.buitenlandsAdresRegel1)
                .append(buitenlandsAdresRegel2, castOther.buitenlandsAdresRegel2)
                .append(buitenlandsAdresRegel3, castOther.buitenlandsAdresRegel3)
                .append(buitenlandsAdresRegel4, castOther.buitenlandsAdresRegel4)
                .append(buitenlandsAdresRegel5, castOther.buitenlandsAdresRegel5)
                .append(buitenlandsAdresRegel6, castOther.buitenlandsAdresRegel6)
                .append(landOfGebiedCode, castOther.landOfGebiedCode)
                .append(indicatiePersoonAangetroffenOpAdres, castOther.indicatiePersoonAangetroffenOpAdres)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortAdresCode)
                .append(redenWijzigingAdresCode)
                .append(aangeverAdreshoudingCode)
                .append(datumAanvangAdreshouding)
                .append(identificatiecodeAdresseerbaarObject)
                .append(identificatiecodeNummeraanduiding)
                .append(gemeenteCode)
                .append(naamOpenbareRuimte)
                .append(afgekorteNaamOpenbareRuimte)
                .append(gemeentedeel)
                .append(huisnummer)
                .append(huisletter)
                .append(huisnummertoevoeging)
                .append(postcode)
                .append(woonplaatsnaam)
                .append(locatieTovAdres)
                .append(locatieOmschrijving)
                .append(buitenlandsAdresRegel1)
                .append(buitenlandsAdresRegel2)
                .append(buitenlandsAdresRegel3)
                .append(buitenlandsAdresRegel4)
                .append(buitenlandsAdresRegel5)
                .append(buitenlandsAdresRegel6)
                .append(landOfGebiedCode)
                .append(indicatiePersoonAangetroffenOpAdres)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("soortAdresCode", soortAdresCode)
                .append("redenWijzigingAdresCode", redenWijzigingAdresCode)
                .append("aangeverAdreshoudingCode", aangeverAdreshoudingCode)
                .append("datumAanvangAdreshouding", datumAanvangAdreshouding)
                .append(
                        "identificatiecodeAdresseerbaarObject",
                        identificatiecodeAdresseerbaarObject)
                .append("identificatiecodeNummeraanduiding", identificatiecodeNummeraanduiding)
                .append("gemeenteCode", gemeenteCode)
                .append("naamOpenbareRuimte", naamOpenbareRuimte)
                .append("afgekorteNaamOpenbareRuimte", afgekorteNaamOpenbareRuimte)
                .append("gemeentedeel", gemeentedeel)
                .append("huisnummer", huisnummer)
                .append("huisletter", huisletter)
                .append("huisnummertoevoeging", huisnummertoevoeging)
                .append("postcode", postcode)
                .append("woonplaatsnaam", woonplaatsnaam)
                .append("locatietovAdres", locatieTovAdres)
                .append("locatieOmschrijving", locatieOmschrijving)
                .append("buitenlandsAdresRegel1", buitenlandsAdresRegel1)
                .append("buitenlandsAdresRegel2", buitenlandsAdresRegel2)
                .append("buitenlandsAdresRegel3", buitenlandsAdresRegel3)
                .append("buitenlandsAdresRegel4", buitenlandsAdresRegel4)
                .append("buitenlandsAdresRegel5", buitenlandsAdresRegel5)
                .append("buitenlandsAdresRegel6", buitenlandsAdresRegel6)
                .append("landOfGebiedCode", landOfGebiedCode)
                .append(
                        "indicatiePersoonAangetroffenOpAdres",
                        indicatiePersoonAangetroffenOpAdres)
                .toString();
    }

    /**
     * Builder object voor BrpAdresGroepInhoud.
     */
    public static final class Builder {
        private final BrpSoortAdresCode soortAdresCode;
        private BrpDatum datumAanvangAdreshouding;
        private BrpRedenWijzigingVerblijfCode redenWijzigingAdresCode;
        private BrpAangeverCode aangeverAdreshoudingCode;
        private BrpString gemeentedeel;
        private BrpGemeenteCode gemeenteCode;
        private BrpString identificatiecodeAdresseerbaarObject;
        private BrpString identificatiecodeNummeraanduiding;
        private BrpString naamOpenbareRuimte;
        private BrpString afgekorteNaamOpenbareRuimte;
        private BrpInteger huisnummer;
        private BrpCharacter huisletter;
        private BrpString huisnummertoevoeging;
        private BrpString postcode;
        private BrpString woonplaatsnaam;
        private BrpAanduidingBijHuisnummerCode locatieTovAdres;
        private BrpString locatieOmschrijving;
        private BrpString buitenlandsAdresRegel1;
        private BrpString buitenlandsAdresRegel2;
        private BrpString buitenlandsAdresRegel3;
        private BrpString buitenlandsAdresRegel4;
        private BrpString buitenlandsAdresRegel5;
        private BrpString buitenlandsAdresRegel6;
        private BrpLandOfGebiedCode landOfGebiedCode;
        private BrpBoolean indicatiePersoonAangetroffenOpAdres;

        /**
         * Constructor met verplicht(e) veld(en).
         * @param soortAdresCode soort adres
         */
        public Builder(final BrpSoortAdresCode soortAdresCode) {
            this.soortAdresCode = soortAdresCode;
        }

        /**
         * Zet datum aanvang adreshouding.
         * @param param de datum aanvang adreshouding
         * @return builder object
         */
        public Builder datumAanvangAdreshouding(final BrpDatum param) {
            datumAanvangAdreshouding = param;
            return this;
        }

        /**
         * Zet de reden wijziging adres.
         * @param param code
         * @return builder object
         */
        public Builder redenWijzigingAdresCode(final BrpRedenWijzigingVerblijfCode param) {
            redenWijzigingAdresCode = param;
            return this;
        }

        /**
         * Zet de aangever adreshouding.
         * @param param code
         * @return builder object
         */
        public Builder aangeverAdreshoudingCode(final BrpAangeverCode param) {
            aangeverAdreshoudingCode = param;
            return this;
        }

        /**
         * Zet het gemeentedeel.
         * @param param gemeentedeel
         * @return builder object
         */
        public Builder gemeentedeel(final BrpString param) {
            gemeentedeel = param;
            return this;
        }

        /**
         * Zet de gemeente.
         * @param param code
         * @return builder object
         */
        public Builder gemeenteCode(final BrpGemeenteCode param) {
            gemeenteCode = param;
            return this;
        }

        /**
         * Zet de identificatiecode voor adresseerbaarobject.
         * @param param identcode
         * @return builder object
         */
        public Builder identificatiecodeAdresseerbaarObject(final BrpString param) {
            identificatiecodeAdresseerbaarObject = param;
            return this;
        }

        /**
         * Zet de identificatiecode voor nummeraanduiding.
         * @param param identcode
         * @return builder object
         */
        public Builder identificatiecodeNummeraanduiding(final BrpString param) {
            identificatiecodeNummeraanduiding = param;
            return this;
        }

        /**
         * Zet de naam openbare ruimte.
         * @param param naam openbare ruimte
         * @return builder object
         */
        public Builder naamOpenbareRuimte(final BrpString param) {
            naamOpenbareRuimte = param;
            return this;
        }

        /**
         * Zet de afgekorte naam openbare ruimte.
         * @param param afgekorte naam openbare ruimte
         * @return builder object
         */
        public Builder afgekorteNaamOpenbareRuimte(final BrpString param) {
            afgekorteNaamOpenbareRuimte = param;
            return this;
        }

        /**
         * Zet het huisnummer.
         * @param param huisnummer
         * @return builder object
         */
        public Builder huisnummer(final BrpInteger param) {
            huisnummer = param;
            return this;
        }

        /**
         * zet het huisletter.
         * @param param huisletter
         * @return builder object
         */
        public Builder huisletter(final BrpCharacter param) {
            huisletter = param;
            return this;
        }

        /**
         * Zet het huisnummertoevoeging.
         * @param param huisnummertoevoeging
         * @return builder object
         */
        public Builder huisnummertoevoeging(final BrpString param) {
            huisnummertoevoeging = param;
            return this;
        }

        /**
         * Zet de postcode.
         * @param param postcode
         * @return builder object
         */
        public Builder postcode(final BrpString param) {
            postcode = param;
            return this;
        }

        /**
         * Zet de woonplaatsnaam.
         * @param param woonplaatsnaam
         * @return builder object
         */
        public Builder woonplaatsnaam(final BrpString param) {
            woonplaatsnaam = param;
            return this;
        }

        /**
         * Zet de locatie tov adres.
         * @param param code
         * @return builder object
         */
        public Builder locatieTovAdres(final BrpAanduidingBijHuisnummerCode param) {
            locatieTovAdres = param;
            return this;
        }

        /**
         * Zet de locatie omschrijving.
         * @param param locatie omschrijving
         * @return builder object
         */
        public Builder locatieOmschrijving(final BrpString param) {
            locatieOmschrijving = param;
            return this;
        }

        /**
         * Zet de buitenlands adres regel 1.
         * @param param regel
         * @return builder object
         */
        public Builder buitenlandsAdresRegel1(final BrpString param) {
            buitenlandsAdresRegel1 = param;
            return this;
        }

        /**
         * Zet de buitenlands adres regel 2.
         * @param param regel
         * @return builder object
         */
        public Builder buitenlandsAdresRegel2(final BrpString param) {
            buitenlandsAdresRegel2 = param;
            return this;
        }

        /**
         * Zet de buitenlands adres regel 3.
         * @param param regel
         * @return builder object
         */
        public Builder buitenlandsAdresRegel3(final BrpString param) {
            buitenlandsAdresRegel3 = param;
            return this;
        }

        /**
         * Zet de buitenlands adres regel 4.
         * @param param regel
         * @return builder object
         */
        public Builder buitenlandsAdresRegel4(final BrpString param) {
            buitenlandsAdresRegel4 = param;
            return this;
        }

        /**
         * Zet de buitenlands adres regel 5.
         * @param param regel
         * @return builder object
         */
        public Builder buitenlandsAdresRegel5(final BrpString param) {
            buitenlandsAdresRegel5 = param;
            return this;
        }

        /**
         * Zet de buitenlands adres regel 6.
         * @param param regel
         * @return builder object
         */
        public Builder buitenlandsAdresRegel6(final BrpString param) {
            buitenlandsAdresRegel6 = param;
            return this;
        }

        /**
         * Zet land/gebied.
         * @param param code
         * @return builder object
         */
        public Builder landOfGebiedCode(final BrpLandOfGebiedCode param) {
            landOfGebiedCode = param;
            return this;
        }

        /**
         * zet indicatie persoon aangetroffen op adres.
         * @param param waarde
         * @return builder object
         */
        public Builder indicatiePersoonAangetroffenOpAdres(final BrpBoolean param) {
            indicatiePersoonAangetroffenOpAdres = param;
            return this;
        }

        /**
         * @return een nieuwe geconstrueerde {@link BrpAdresInhoud}
         */
        public BrpAdresInhoud build() {
            return new BrpAdresInhoud(this);
        }
    }
}

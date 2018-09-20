/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.util.Date;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVerval;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;

/**
 * Gegenereerde Binding util voor attribuuttypen.
 *
 */
public final class BindingUtilAttribuutTypen {

    private static final Integer MIN_LENGTE_LANDGEBIEDCODE = 4;
    private static final Integer MIN_LENGTE_BURGERSERVICENUMMER = 9;
    private static final Integer MIN_LENGTE_ADMINISTRATIENUMMER = 10;
    private static final Integer MIN_LENGTE_GEMEENTECODE = 4;
    private static final Integer MIN_LENGTE_WOONPLAATSCODE = 4;
    private static final Integer MIN_LENGTE_NATIONALITEITCODE = 4;
    private static final Integer MIN_LENGTE_REDENVERKRIJGINGCODE = 3;
    private static final Integer MIN_LENGTE_REDENVERLIESCODE = 3;
    private static final Integer MIN_LENGTE_RECHTSGRONDCODE = 3;
    private static final Integer MIN_LENGTE_AANDUIDINGVERBLIJFSRECHTCODE = 2;
    private static final Integer MIN_LENGTE_PARTIJCODE = 6;
    private static final Integer MIN_LENGTE_LO3RNIDEELNEMER = 4;

    /**
     * Private constructor, want dit is een utility klasse.
     *
     */
    private BindingUtilAttribuutTypen() {
    }

    /**
     * Zet een Land/gebied code om naar een xml string.
     *
     * @param landGebiedCode Om te zetten Land/gebied code
     * @return Xml string representatie van een Land/gebied code.
     */
    public static String landGebiedCodeNaarXml(final Short landGebiedCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(landGebiedCode, MIN_LENGTE_LANDGEBIEDCODE);
    }

    /**
     * Zet een xml string om naar een Land/gebied code.
     *
     * @param xml xml string.
     * @return waarde van een Land/gebied code.
     */
    public static Short xmlNaarLandGebiedCode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Burgerservicenummer om naar een xml string.
     *
     * @param burgerservicenummer Om te zetten Burgerservicenummer
     * @return Xml string representatie van een Burgerservicenummer.
     */
    public static String burgerservicenummerNaarXml(final Integer burgerservicenummer) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(burgerservicenummer, MIN_LENGTE_BURGERSERVICENUMMER);
    }

    /**
     * Zet een xml string om naar een Burgerservicenummer.
     *
     * @param xml xml string.
     * @return waarde van een Burgerservicenummer.
     */
    public static Integer xmlNaarBurgerservicenummer(final String xml) {
        return BindingUtil.stringNaarInteger(xml);
    }

    /**
     * Zet een Administratienummer om naar een xml string.
     *
     * @param administratienummer Om te zetten Administratienummer
     * @return Xml string representatie van een Administratienummer.
     */
    public static String administratienummerNaarXml(final Long administratienummer) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(administratienummer, MIN_LENGTE_ADMINISTRATIENUMMER);
    }

    /**
     * Zet een xml string om naar een Administratienummer.
     *
     * @param xml xml string.
     * @return waarde van een Administratienummer.
     */
    public static Long xmlNaarAdministratienummer(final String xml) {
        return BindingUtil.stringNaarLong(xml);
    }

    /**
     * Zet een Datum (evt. deels onbekend) om naar een xml string.
     *
     * @param datumEvtDeelsOnbekend Om te zetten Datum (evt. deels onbekend)
     * @return Xml string representatie van een Datum (evt. deels onbekend).
     */
    public static String datumEvtDeelsOnbekendNaarXml(final Integer datumEvtDeelsOnbekend) {
        return BindingUtil.datumAlsGetalNaarDatumAlsString(datumEvtDeelsOnbekend);
    }

    /**
     * Zet een xml string om naar een Datum (evt. deels onbekend).
     *
     * @param xml xml string.
     * @return waarde van een Datum (evt. deels onbekend).
     */
    public static Integer xmlNaarDatumEvtDeelsOnbekend(final String xml) {
        return BindingUtil.datumAlsStringNaarDatumAlsGetal(xml);
    }

    /**
     * Zet een Ja/Nee om naar een xml string.
     *
     * @param jaNee Om te zetten Ja/Nee
     * @return Xml string representatie van een Ja/Nee.
     */
    public static String jaNeeNaarXml(final Boolean jaNee) {
        return BindingUtil.booleanNaarString(jaNee);
    }

    /**
     * Zet een xml string om naar een Ja/Nee.
     *
     * @param xml xml string.
     * @return waarde van een Ja/Nee.
     */
    public static Boolean xmlNaarJaNee(final String xml) {
        return BindingUtil.stringNaarBoolean(xml);
    }

    /**
     * Zet een Datum/Tijd om naar een xml string.
     *
     * @param datumTijd Om te zetten Datum/Tijd
     * @return Xml string representatie van een Datum/Tijd.
     */
    public static String datumTijdNaarXml(final Date datumTijd) {
        return BindingUtil.datumTijdNaarString(datumTijd);
    }

    /**
     * Zet een xml string om naar een Datum/Tijd.
     *
     * @param xml xml string.
     * @return waarde van een Datum/Tijd.
     */
    public static Date xmlNaarDatumTijd(final String xml) {
        return BindingUtil.stringNaarDatumTijd(xml);
    }

    /**
     * Zet een Huisnummer om naar een xml string.
     *
     * @param huisnummer Om te zetten Huisnummer
     * @return Xml string representatie van een Huisnummer.
     */
    public static String huisnummerNaarXml(final Integer huisnummer) {
        return BindingUtil.getalNaarString(huisnummer);
    }

    /**
     * Zet een xml string om naar een Huisnummer.
     *
     * @param xml xml string.
     * @return waarde van een Huisnummer.
     */
    public static Integer xmlNaarHuisnummer(final String xml) {
        return BindingUtil.stringNaarInteger(xml);
    }

    /**
     * Zet een Locatie ten opzichte van adres om naar een xml string.
     *
     * @param locatieTenOpzichteVanAdres Om te zetten Locatie ten opzichte van adres
     * @return Xml string representatie van een Locatie ten opzichte van adres.
     */
    public static String locatieTenOpzichteVanAdresNaarXml(final LocatieTenOpzichteVanAdres locatieTenOpzichteVanAdres) {
        return BindingUtil.enumeratiewaardeNaarString(locatieTenOpzichteVanAdres);
    }

    /**
     * Zet een xml string om naar een Locatie ten opzichte van adres.
     *
     * @param xml xml string.
     * @return waarde van een Locatie ten opzichte van adres.
     */
    public static LocatieTenOpzichteVanAdres xmlNaarLocatieTenOpzichteVanAdres(final String xml) {
        return BindingUtil.stringNaarEnumeratiewaarde(xml, LocatieTenOpzichteVanAdres.class);
    }

    /**
     * Zet een Gemeente code om naar een xml string.
     *
     * @param gemeenteCode Om te zetten Gemeente code
     * @return Xml string representatie van een Gemeente code.
     */
    public static String gemeenteCodeNaarXml(final Short gemeenteCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(gemeenteCode, MIN_LENGTE_GEMEENTECODE);
    }

    /**
     * Zet een xml string om naar een Gemeente code.
     *
     * @param xml xml string.
     * @return waarde van een Gemeente code.
     */
    public static Short xmlNaarGemeenteCode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Woonplaatscode om naar een xml string.
     *
     * @param woonplaatscode Om te zetten Woonplaatscode
     * @return Xml string representatie van een Woonplaatscode.
     */
    public static String woonplaatscodeNaarXml(final Short woonplaatscode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(woonplaatscode, MIN_LENGTE_WOONPLAATSCODE);
    }

    /**
     * Zet een xml string om naar een Woonplaatscode.
     *
     * @param xml xml string.
     * @return waarde van een Woonplaatscode.
     */
    public static Short xmlNaarWoonplaatscode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Nationaliteitcode om naar een xml string.
     *
     * @param nationaliteitcode Om te zetten Nationaliteitcode
     * @return Xml string representatie van een Nationaliteitcode.
     */
    public static String nationaliteitcodeNaarXml(final Short nationaliteitcode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(nationaliteitcode, MIN_LENGTE_NATIONALITEITCODE);
    }

    /**
     * Zet een xml string om naar een Nationaliteitcode.
     *
     * @param xml xml string.
     * @return waarde van een Nationaliteitcode.
     */
    public static Short xmlNaarNationaliteitcode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Ja om naar een xml string.
     *
     * @param ja Om te zetten Ja
     * @return Xml string representatie van een Ja.
     */
    public static String jaNaarXml(final Ja ja) {
        return BindingUtil.jaNaarString(ja);
    }

    /**
     * Zet een xml string om naar een Ja.
     *
     * @param xml xml string.
     * @return waarde van een Ja.
     */
    public static Ja xmlNaarJa(final String xml) {
        return BindingUtil.stringNaarJa(xml);
    }

    /**
     * Zet een Reden verkrijging code om naar een xml string.
     *
     * @param redenVerkrijgingCode Om te zetten Reden verkrijging code
     * @return Xml string representatie van een Reden verkrijging code.
     */
    public static String redenVerkrijgingCodeNaarXml(final Short redenVerkrijgingCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(redenVerkrijgingCode, MIN_LENGTE_REDENVERKRIJGINGCODE);
    }

    /**
     * Zet een xml string om naar een Reden verkrijging code.
     *
     * @param xml xml string.
     * @return waarde van een Reden verkrijging code.
     */
    public static Short xmlNaarRedenVerkrijgingCode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Reden verlies code om naar een xml string.
     *
     * @param redenVerliesCode Om te zetten Reden verlies code
     * @return Xml string representatie van een Reden verlies code.
     */
    public static String redenVerliesCodeNaarXml(final Short redenVerliesCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(redenVerliesCode, MIN_LENGTE_REDENVERLIESCODE);
    }

    /**
     * Zet een xml string om naar een Reden verlies code.
     *
     * @param xml xml string.
     * @return waarde van een Reden verlies code.
     */
    public static Short xmlNaarRedenVerliesCode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Rechtsgrond code om naar een xml string.
     *
     * @param rechtsgrondCode Om te zetten Rechtsgrond code
     * @return Xml string representatie van een Rechtsgrond code.
     */
    public static String rechtsgrondCodeNaarXml(final Short rechtsgrondCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(rechtsgrondCode, MIN_LENGTE_RECHTSGRONDCODE);
    }

    /**
     * Zet een xml string om naar een Rechtsgrond code.
     *
     * @param xml xml string.
     * @return waarde van een Rechtsgrond code.
     */
    public static Short xmlNaarRechtsgrondCode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Aanduiding verblijfsrecht code om naar een xml string.
     *
     * @param aanduidingVerblijfsrechtCode Om te zetten Aanduiding verblijfsrecht code
     * @return Xml string representatie van een Aanduiding verblijfsrecht code.
     */
    public static String aanduidingVerblijfsrechtCodeNaarXml(final Short aanduidingVerblijfsrechtCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(aanduidingVerblijfsrechtCode, MIN_LENGTE_AANDUIDINGVERBLIJFSRECHTCODE);
    }

    /**
     * Zet een xml string om naar een Aanduiding verblijfsrecht code.
     *
     * @param xml xml string.
     * @return waarde van een Aanduiding verblijfsrecht code.
     */
    public static Short xmlNaarAanduidingVerblijfsrechtCode(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Partij code om naar een xml string.
     *
     * @param partijCode Om te zetten Partij code
     * @return Xml string representatie van een Partij code.
     */
    public static String partijCodeNaarXml(final Integer partijCode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(partijCode, MIN_LENGTE_PARTIJCODE);
    }

    /**
     * Zet een xml string om naar een Partij code.
     *
     * @param xml xml string.
     * @return waarde van een Partij code.
     */
    public static Integer xmlNaarPartijCode(final String xml) {
        return BindingUtil.stringNaarInteger(xml);
    }

    /**
     * Zet een LO3 selectiesoort om naar een xml string.
     *
     * @param lO3Selectiesoort Om te zetten LO3 selectiesoort
     * @return Xml string representatie van een LO3 selectiesoort.
     */
    public static String lO3SelectiesoortNaarXml(final Short lO3Selectiesoort) {
        return BindingUtil.getalNaarString(lO3Selectiesoort);
    }

    /**
     * Zet een xml string om naar een LO3 selectiesoort.
     *
     * @param xml xml string.
     * @return waarde van een LO3 selectiesoort.
     */
    public static Short xmlNaarLO3Selectiesoort(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een LO3 berichtaanduiding om naar een xml string.
     *
     * @param lO3Berichtaanduiding Om te zetten LO3 berichtaanduiding
     * @return Xml string representatie van een LO3 berichtaanduiding.
     */
    public static String lO3BerichtaanduidingNaarXml(final Short lO3Berichtaanduiding) {
        return BindingUtil.getalNaarString(lO3Berichtaanduiding);
    }

    /**
     * Zet een xml string om naar een LO3 berichtaanduiding.
     *
     * @param xml xml string.
     * @return waarde van een LO3 berichtaanduiding.
     */
    public static Short xmlNaarLO3Berichtaanduiding(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Nee om naar een xml string.
     *
     * @param nee Om te zetten Nee
     * @return Xml string representatie van een Nee.
     */
    public static String neeNaarXml(final Nee nee) {
        return BindingUtil.neeNaarString(nee);
    }

    /**
     * Zet een xml string om naar een Nee.
     *
     * @param xml xml string.
     * @return waarde van een Nee.
     */
    public static Nee xmlNaarNee(final String xml) {
        return BindingUtil.stringNaarNee(xml);
    }

    /**
     * Zet een Nadere aanduiding verval om naar een xml string.
     *
     * @param nadereAanduidingVerval Om te zetten Nadere aanduiding verval
     * @return Xml string representatie van een Nadere aanduiding verval.
     */
    public static String nadereAanduidingVervalNaarXml(final NadereAanduidingVerval nadereAanduidingVerval) {
        return BindingUtil.enumeratiewaardeNaarString(nadereAanduidingVerval);
    }

    /**
     * Zet een xml string om naar een Nadere aanduiding verval.
     *
     * @param xml xml string.
     * @return waarde van een Nadere aanduiding verval.
     */
    public static NadereAanduidingVerval xmlNaarNadereAanduidingVerval(final String xml) {
        return BindingUtil.stringNaarEnumeratiewaarde(xml, NadereAanduidingVerval.class);
    }

    /**
     * Zet een LO3 RNI-deelnemer om naar een xml string.
     *
     * @param lO3RNIDeelnemer Om te zetten LO3 RNI-deelnemer
     * @return Xml string representatie van een LO3 RNI-deelnemer.
     */
    public static String lO3RNIDeelnemerNaarXml(final Short lO3RNIDeelnemer) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(lO3RNIDeelnemer, MIN_LENGTE_LO3RNIDEELNEMER);
    }

    /**
     * Zet een xml string om naar een LO3 RNI-deelnemer.
     *
     * @param xml xml string.
     * @return waarde van een LO3 RNI-deelnemer.
     */
    public static Short xmlNaarLO3RNIDeelnemer(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Verwerkingssoort om naar een xml string.
     *
     * @param verwerkingssoort Om te zetten Verwerkingssoort
     * @return Xml string representatie van een Verwerkingssoort.
     */
    public static String verwerkingssoortNaarXml(final Verwerkingssoort verwerkingssoort) {
        return BindingUtil.enumeratiewaardeNaarString(verwerkingssoort);
    }

    /**
     * Zet een xml string om naar een Verwerkingssoort.
     *
     * @param xml xml string.
     * @return waarde van een Verwerkingssoort.
     */
    public static Verwerkingssoort xmlNaarVerwerkingssoort(final String xml) {
        return BindingUtil.stringNaarEnumeratiewaarde(xml, Verwerkingssoort.class);
    }

    /**
     * Zet een Datum om naar een xml string.
     *
     * @param datum Om te zetten Datum
     * @return Xml string representatie van een Datum.
     */
    public static String datumNaarXml(final Integer datum) {
        return BindingUtil.datumAlsGetalNaarDatumAlsString(datum);
    }

    /**
     * Zet een xml string om naar een Datum.
     *
     * @param xml xml string.
     * @return waarde van een Datum.
     */
    public static Integer xmlNaarDatum(final String xml) {
        return BindingUtil.datumAlsStringNaarDatumAlsGetal(xml);
    }

}

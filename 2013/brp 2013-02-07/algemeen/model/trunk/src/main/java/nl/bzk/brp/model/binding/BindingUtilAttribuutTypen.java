/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.util.Date;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;


/** Gegenereerde Binding util voor attribuuttypen. */
public final class BindingUtilAttribuutTypen {

    private static final Integer MIN_LENGTE_ANUMMER              = 10;
    private static final Integer MIN_LENGTE_BURGERSERVICENUMMER  = 9;
    private static final Integer MIN_LENGTE_CODEVERBLIJFSTITEL   = 2;
    private static final Integer MIN_LENGTE_GEMEENTECODE         = 4;
    private static final Integer MIN_LENGTE_LANDCODE             = 4;
    private static final Integer MIN_LENGTE_NATIONALITEITCODE    = 4;
    private static final Integer MIN_LENGTE_RECHTSGRONDCODE      = 3;
    private static final Integer MIN_LENGTE_REDENVERKRIJGINGCODE = 3;
    private static final Integer MIN_LENGTE_REDENVERLIESCODE     = 3;
    private static final Integer MIN_LENGTE_WOONPLAATSCODE       = 4;

    /** Private constructor, want dit is een utility klasse. */
    private BindingUtilAttribuutTypen() {
    }

    /**
     * Zet een A-nummer om naar een xml string.
     *
     * @param aNummer Om te zetten A-nummer
     * @return Xml string representatie van een A-nummer.
     */
    public static String aNummerNaarXml(final Long aNummer) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(aNummer, MIN_LENGTE_ANUMMER);
    }

    /**
     * Zet een xml string om naar een A-nummer.
     *
     * @param xml xml string.
     * @return waarde van een A-nummer.
     */
    public static Long xmlNaarANummer(final String xml) {
        return BindingUtil.stringNaarLong(xml);
    }

    /**
     * Zet een Aanduiding bij huisnummer om naar een xml string.
     *
     * @param aanduidingBijHuisnummer Om te zetten Aanduiding bij huisnummer
     * @return Xml string representatie van een Aanduiding bij huisnummer.
     */
    public static String aanduidingBijHuisnummerNaarXml(final AanduidingBijHuisnummer aanduidingBijHuisnummer) {
        return BindingUtil.enumeratiewaardeNaarString(aanduidingBijHuisnummer);
    }

    /**
     * Zet een xml string om naar een Aanduiding bij huisnummer.
     *
     * @param xml xml string.
     * @return waarde van een Aanduiding bij huisnummer.
     */
    public static AanduidingBijHuisnummer xmlNaarAanduidingBijHuisnummer(final String xml) {
        return BindingUtil.stringNaarEnumeratiewaarde(xml, AanduidingBijHuisnummer.class);
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
     * Zet een Code verblijfstitel om naar een xml string.
     *
     * @param codeVerblijfstitel Om te zetten Code verblijfstitel
     * @return Xml string representatie van een Code verblijfstitel.
     */
    public static String codeVerblijfstitelNaarXml(final Short codeVerblijfstitel) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(codeVerblijfstitel, MIN_LENGTE_CODEVERBLIJFSTITEL);
    }

    /**
     * Zet een xml string om naar een Code verblijfstitel.
     *
     * @param xml xml string.
     * @return waarde van een Code verblijfstitel.
     */
    public static Short xmlNaarCodeVerblijfstitel(final String xml) {
        return BindingUtil.stringNaarShort(xml);
    }

    /**
     * Zet een Datum (evt. deels onbekend) om naar een xml string.
     *
     * @param datum Om te zetten Datum (evt. deels onbekend)
     * @return Xml string representatie van een Datum (evt. deels onbekend).
     */
    public static String datumNaarXml(final Integer datum) {
        return BindingUtil.datumAlsGetalNaarDatumAlsString(datum);
    }

    /**
     * Zet een xml string om naar een Datum (evt. deels onbekend).
     *
     * @param xml xml string.
     * @return waarde van een Datum (evt. deels onbekend).
     */
    public static Integer xmlNaarDatum(final String xml) {
        return BindingUtil.datumAlsStringNaarDatumAlsGetal(xml);
    }

    /**
     * Zet een Datum \ Tijd om naar een xml string.
     *
     * @param datumTijd Om te zetten Datum \ Tijd
     * @return Xml string representatie van een Datum \ Tijd.
     */
    public static String datumTijdNaarXml(final Date datumTijd) {
        return BindingUtil.javaDateNaarW3cDatumString(datumTijd);
    }

    /**
     * Zet een xml string om naar een Datum \ Tijd.
     *
     * @param xml xml string.
     * @return waarde van een Datum \ Tijd.
     */
    public static Date xmlNaarDatumTijd(final String xml) {
        return BindingUtil.w3cDatumStringNaarJavaDate(xml);
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
     * Zet een Ja \ Nee om naar een xml string.
     *
     * @param jaNee Om te zetten Ja \ Nee
     * @return Xml string representatie van een Ja \ Nee.
     */
    public static String jaNeeNaarXml(final Boolean jaNee) {
        return BindingUtil.booleanNaarString(jaNee);
    }

    /**
     * Zet een xml string om naar een Ja \ Nee.
     *
     * @param xml xml string.
     * @return waarde van een Ja \ Nee.
     */
    public static Boolean xmlNaarJaNee(final String xml) {
        return BindingUtil.stringNaarBoolean(xml);
    }

    /**
     * Zet een Landcode om naar een xml string.
     *
     * @param landcode Om te zetten Landcode
     * @return Xml string representatie van een Landcode.
     */
    public static String landcodeNaarXml(final Short landcode) {
        return BindingUtil.getalNaarStringMetVoorloopnullen(landcode, MIN_LENGTE_LANDCODE);
    }

    /**
     * Zet een xml string om naar een Landcode.
     *
     * @param xml xml string.
     * @return waarde van een Landcode.
     */
    public static Short xmlNaarLandcode(final String xml) {
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
     * Zet een StatusHistorie om naar een xml string.
     *
     * @param statusHistorie Om te zetten StatusHistorie
     * @return Xml string representatie van een StatusHistorie.
     */
    public static String statusHistorieNaarXml(final StatusHistorie statusHistorie) {
        return BindingUtil.enumeratiewaardeNaarString(statusHistorie);
    }

    /**
     * Zet een xml string om naar een StatusHistorie.
     *
     * @param xml xml string.
     * @return waarde van een StatusHistorie.
     */
    public static StatusHistorie xmlNaarStatusHistorie(final String xml) {
        return BindingUtil.stringNaarEnumeratiewaarde(xml, StatusHistorie.class);
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
     * Zet een Verwerkingswijze om naar een xml string.
     *
     * @param verwerkingswijze Om te zetten Verwerkingswijze
     * @return Xml string representatie van een Verwerkingswijze.
     */
    public static String verwerkingswijzeNaarXml(final Verwerkingswijze verwerkingswijze) {
        return BindingUtil.enumeratiewaardeNaarString(verwerkingswijze);
    }

    /**
     * Zet een xml string om naar een Verwerkingswijze.
     *
     * @param xml xml string.
     * @return waarde van een Verwerkingswijze.
     */
    public static Verwerkingswijze xmlNaarVerwerkingswijze(final String xml) {
        return BindingUtil.stringNaarEnumeratiewaarde(xml, Verwerkingswijze.class);
    }
}

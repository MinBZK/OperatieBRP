/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import org.apache.commons.lang.StringUtils;


/**
 * Utility class gebruikt door Jibx databinding.
 */
public final class BindingUtil {

    /**
     * tijd in de formaat yyyyMMddHHmmss[honderdsteseconden].
     */
    private static final String DATUMTIJDSTIP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    /**
     * datum in de formaat yyyyMMdd.
     */
    private static final String DATUM_FORMAT = "yyyy-MM-dd";
    private static final int TIEN = 10;
    private static final int VEERTIEN = 14;
    private static final int ZESTIEN = 16;

    private static final int BSN_LENGTE = 9;
    private static final int CODE_LENGTE = 4;
    private static final int CODE_LENGTE_KORT = 3;

    /**
     * Constructor. Private toegang omdat dit een utility class is.
     */
    private BindingUtil() {
    }

    /**
     * Retourneert een List implementatie die gebruikt wordt door Jibx databinding.
     *
     * @param <T> type van de list
     * @return ArrayList implementatie
     */
    public static <T> List<T> newListInstance() {
        return new ArrayList<T>();
    }

    /**
     * Retourneert een Set implementatie die gebruikt wordt door Jibx databinding.
     *
     * @param <T> type van de set
     * @return HashSet implementatie
     */
    public static <T> Set<T> newSetInstance() {
        return new HashSet<T>();
    }

    /**
     * Retourneert een Set implementatie die gebruikt wordt door Jibx databinding.
     *
     * @param <T> type van de set
     * @return TreeSet implementatie
     */
    public static <T> Set<T> newTreeSetInstance() {
        return new TreeSet<T>();
    }

    /**
     * Retourmeert een nieuwe {@link RelatieBericht} van de soort {@link SoortRelatie#FAMILIERECHTELIJKE_BETREKKING}.
     *
     * @return een nieuwe relatie van de soort familie rechtelijke betrekking.
     */
    public static FamilierechtelijkeBetrekkingBericht newFamilieRechtelijkeBetrekking() {
        FamilierechtelijkeBetrekkingBericht familieRechtelijkeBetrekking = new FamilierechtelijkeBetrekkingBericht();
        return familieRechtelijkeBetrekking;
    }

    /**
     * Retourmeert een nieuwe {@link RelatieBericht} van de soort {@link SoortRelatie#HUWELIJK}.
     *
     * @return een nieuwe relatie van het soort huwelijk.
     */
    public static HuwelijkBericht newHuwelijk() {
        HuwelijkBericht huwelijk = new HuwelijkBericht();
        return huwelijk;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#KIND}.
     *
     * @return een nieuwe betrokkenheid van de soort 'kind'.
     */
    public static KindBericht newKindBetrokkenheid() {
        KindBericht betrokkenheid = new KindBericht();
        return betrokkenheid;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#OUDER}.
     *
     * @return een nieuwe betrokkenheid van de soort 'ouder'.
     */
    public static OuderBericht newOuderBetrokkenheid() {
        OuderBericht betrokkenheid = new OuderBericht();
        return betrokkenheid;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#PARTNER}.
     *
     * @return een nieuwe betrokkenheid van de soort 'ouder'.
     */
    public static PartnerBericht newPartnerBetrokkenheid() {
        PartnerBericht betrokkenheid = new PartnerBericht();
        return betrokkenheid;
    }

    /**
     * Converteer Date objet naar String DatumTijd formaat.
     *
     * @param date datum
     * @return tijd in de formaat yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static String dateNaarDatumTijd(final Date date) {
        String resultaat;

        if (date != null) {
            resultaat = (new SimpleDateFormat(DATUMTIJDSTIP_FORMAT).format(date));
        } else {
            resultaat = "";
        }

        return resultaat;
    }

    /**
     * Converteer DatumTijd naar Date object.
     *
     * @param datumTijd datum tijd in het formaat yyyy-MM-dd'T'HH:mm:ss.SSS
     * @return Date object
     */
    public static Date datumTijdNaarDate(final String datumTijd) {
        Date resultaat;

        if (StringUtils.isNotBlank(datumTijd)) {
            String localDatumTijd = datumTijd.trim();
            try {
                if (datumTijd.length() == DATUM_FORMAT.length()) {
                    SimpleDateFormat formatter = new SimpleDateFormat(DATUM_FORMAT);
                    formatter.setLenient(false);

                    resultaat = formatter.parse(localDatumTijd);
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat(DATUMTIJDSTIP_FORMAT);
                    formatter.setLenient(false);

                    resultaat = formatter.parse(localDatumTijd);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Ongeldige waarde voor datum tijd, verwacht wordt " + DATUM_FORMAT
                                                           + " of " + DATUMTIJDSTIP_FORMAT);
            }

            // Calendar cal = Calendar.getInstance();
            // cal.setTime(date);
            //
            // //Omzetten naar milliseconden
            // String hondersteSeconden = datumTijd.substring(VEERTIEN, ZESTIEN);
            // cal.set(Calendar.MILLISECOND, Integer.parseInt(hondersteSeconden) * TIEN);
            //
            // resultaat = cal.getTime();
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * Converteert een code als {@link java.lang.Short} naar een textuele ({@link java.lang.String}) representatie
     * van minimaal 4 cijfers, waarbij de opgegeven code eventueel wordt aangevuld met voorloopnullen tot 4 cijfers.
     *
     * @param code de code als getal (zonder voorloopnullen)
     * @return String textuele representatie van de code met eventuele voorloopnullen
     */
    public static String shortNaarCode(final Short code) {
        final String resultaat;

        if (code != null) {
            resultaat = StringUtils.leftPad(code.toString(), CODE_LENGTE, '0');
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Converteert een code als {@link java.lang.Short} naar een textuele ({@link java.lang.String}) representatie
     * van minimaal 3 cijfers, waarbij de opgegeven code eventueel wordt aangevuld met voorloopnullen tot 3 cijfers.
     *
     * @param code de code als getal (zonder voorloopnullen)
     * @return String textuele representatie van de code met eventuele voorloopnullen
     */
    public static String shortNaarKorteCode(final Short code) {
        final String resultaat;

        if (code != null) {
            resultaat = StringUtils.leftPad(code.toString(), CODE_LENGTE_KORT, '0');
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Converteert een textuele representatie van een code (welke eventueel voorloopnullen bevat) naar de juist
     * code als getal ({@link Short}).
     *
     * @param code de code die moet worden geconverteerd
     * @return de geconverteerde code
     */
    public static Short codeNaarShort(final String code) {
        final Short resultaat;

        if (StringUtils.isNotBlank(code)) {
            resultaat = Short.parseShort(code);
        } else {
            resultaat = null;
        }
        return resultaat;
    }
    /**
     * Converteert een integer naar een String met de formaat yyyy-MM-dd
     *
     * @param datum
     * @return de geconverteerde getal
     */
    public static String integerNaarDatum(final Integer datum) {
        if (datum == null) {
            return null;
        } else {
            String stringGetal = datum.toString();

            if (stringGetal.length() == 8) {
                return stringGetal.substring(0, 4) + "-" + stringGetal.substring(4, 6) + "-"
                        + stringGetal.substring(6, 8);
            } else {
                throw new IllegalArgumentException(
                        "Ongeldige waarde voor datum, verwacht wordt een Integer van 8 getallen");
            }
        }
    }

    /**
     * Converteert een datum van het formaat yyyy-MM-dd naar een Integer
     *
     * @param datum de datum
     * @return integer representatie van yyyy-MM-dd
     */
    public static Integer datumNaarInteger(final String datum) {
        if (datum == null) {
            return null;
        } else if (datum.length() == DATUM_FORMAT.length() && StringUtils.isNumeric(datum.replace("-", ""))) {
            return Integer.parseInt(datum.replace("-", ""));
        } else {
            throw new IllegalArgumentException("Ongeldige waarde voor datum, verwacht wordt " + DATUM_FORMAT);
        }
    }

    /**
     * Serialisatie functie.
     * Converteert Ja / Nee attribuut naar xml string.
     * @param jaNee De te converteren Ja / Nee.
     * @return String representatie van Ja / Nee.
     */
    public static String jaNeeNaarXmlString(final JaNee jaNee) {
        if (jaNee.getWaarde()) {
            return "J";
        } else {
            return "N";
        }
    }

    /**
     * Deserialisatie functie.
     * Converteert een string naar een Ja / Nee attribuuttype.
     * @param jaNeeString De Ja / Nee String.
     * @return Ja / Nee.
     */
    public static JaNee xmlStringNaarJaNee(final String jaNeeString) {
        if ("J".equals(jaNeeString)) {
            return new JaNee(true);
        } else if ("N".equals(jaNeeString)) {
            return new JaNee(false);
        }
        throw new IllegalArgumentException("Invalide waarde voor Ja / Nee veld: " + jaNeeString + ".");
    }

    /**
     * Serialisatie functie.
     * Converteert AanduidingBijHuisnummer attribuut naar xml string.
     * @param abh De te converteren AanduidingBijHuisnummer.
     * @return String representatie van AanduidingBijHuisnummer.
     */
    public static String aanduidingBijHuisnummerNaarXmlString(final AanduidingBijHuisnummer abh) {
        if (abh == AanduidingBijHuisnummer.BY) {
            return "BY";
        } else if (abh == AanduidingBijHuisnummer.TO) {
            return "TO";
        }
        throw new IllegalArgumentException("Invalide waarde voor AanduidingBijHuisnummer.");
    }

    /**
     * Deserialisatie functie.
     * Converteert een string naar een AanduidingBijHuisnummer attribuuttype.
     * @param abhString De AanduidingBijHuisnummer String.
     * @return AanduidingBijHuisnummer.
     */
    public static AanduidingBijHuisnummer xmlStringNaarAanduidingBijHuisnummer(final String abhString) {
        if ("BY".equals(abhString)) {
            return AanduidingBijHuisnummer.BY;
        } else if ("TO".equals(abhString)) {
            return AanduidingBijHuisnummer.TO;
        }
        throw new IllegalArgumentException("Invalide waarde voor AanduidingBijHuisnummer veld: " + abhString + ".");
    }

    /**
     * Serialisatie functie.
     * Converteert Verwerkingswijze attribuut naar xml string.
     * @param verwerkingswijze De te converteren Verwerkingswijze.
     * @return String representatie van Verwerkingswijze.
     */
    public static String verwerkingswijzeNaarXmlString(final Verwerkingswijze verwerkingswijze) {
        if (Verwerkingswijze.P == verwerkingswijze) {
            return "P";
        } else if (Verwerkingswijze.B == verwerkingswijze) {
            return "B";
        }
        throw new IllegalArgumentException("Invalide waarde voor Verwerkingswijze.");
    }

    /**
     * Deserialisatie functie.
     * Converteert een string naar een Verwerkingswijze attribuuttype.
     * @param verwerkingsWijzeString De Verwerkingswijze String.
     * @return Verwerkingswijze.
     */
    public static Verwerkingswijze xmlStringNaarVerwerkingswijze(final String verwerkingsWijzeString) {
        if ("P".equals(verwerkingsWijzeString)) {
            return Verwerkingswijze.P;
        } else if ("B".equals(verwerkingsWijzeString)) {
            return Verwerkingswijze.B;
        }
        throw new IllegalArgumentException("Invalide waarde voor Verwerkingswijze veld: "
                                                   + verwerkingsWijzeString + ".");
    }

    /**
     * Serialisatie functie.
     * Converteert Ja attribuut naar xml string.
     * @param ja De te converteren Ja.
     * @return String representatie van Ja.
     */
    public static String jaNaarXmlString(final Ja ja) {
        if (ja == Ja.J) {
            return "J";
        }
        throw new IllegalArgumentException("Invalide waarde voor Ja.");
    }

    /**
     * Deserialisatie functie.
     * Converteert een string naar een Ja attribuuttype.
     * @param jaString De Ja String.
     * @return Ja.
     */
    public static Ja xmlStringNaarJa(final String jaString) {
        if ("J".equals(jaString)) {
            return Ja.J;
        }
        throw new IllegalArgumentException("Invalide waarde voor Ja veld: " + jaString + ".");
    }

    /**
     * Deserialisatie functie.
     * Converteert een JaNee object naar een Boolean.
     * @param jaNeeString de waarde
     * @return Boolean.TRUE/False (of null).
     */
    public static Boolean xmlStringNaarBoolean(final String jaNeeString) {
        Boolean retval = null;

        if (StringUtils.isNotBlank(jaNeeString)) {
            if ("J".equals(jaNeeString)) {
                retval = Boolean.TRUE;
            } else {
                retval = Boolean.FALSE;
            }
        }
//        else {
//              throw new IllegalArgumentException("Invalide waarde voor Ja / Nee veld: " + jaNeeString + ".");
//        }
        return retval;
    }

    /**
     * Serialisatie functie.
     * Converteert een Boolean object naar een JaNee.
     * @param bool de waarde .
     * @return JaNee (of null).
     */
    public static String booleanNaarXmlString(final Boolean bool) {
        String retval = null;
        if (bool != null) {
            if (bool) {
                retval = "J";
            } else {
                retval = "N";
            }
        }
        return retval;
    }

    /**
     * Deserialize .
     * @param xmlString .
     * @return .
     */
    public static Burgerservicenummer xmlNaarBurgerservicenummer(final String xmlString) {
//        throw new RuntimeException("xmlNaarBurgerservicenummer");
        return new Burgerservicenummer(xmlString);
    }

    /**
     * Serialize .
     * @param bsn .
     * @return .
     */
    public static String burgerservicenummerNaarString(final Burgerservicenummer bsn) {
//        throw new RuntimeException("burgerservicenummerNaarString");
        if (null == bsn) {
            return null;
        } else {
            return StringUtils.leftPad(bsn.getWaarde().toString(), BSN_LENGTE, '0');
        }
    }
}

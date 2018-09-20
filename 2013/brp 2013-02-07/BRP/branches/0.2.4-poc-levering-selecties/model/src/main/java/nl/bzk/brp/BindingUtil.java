/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.apache.commons.lang.StringUtils;


/** Utility class gebruikt door Jibx databinding. */
public final class BindingUtil {

    /** tijd in de formaat yyyyMMddHHmmss[honderdsteseconden]. */
    private static final String DATUMTIJDSTIP_FORMAT = "yyyyMMddHHmmssSS";
    /** datum in de formaat yyyyMMdd. */
    private static final String DATUM_FORMAT         = "yyyyMMdd";
    private static final int    TIEN                 = 10;
    private static final int    VEERTIEN             = 14;
    private static final int    ZESTIEN              = 16;

    private static final int    CODE_LENGTE          = 4;
    private static final int    CODE_LENGTE_KORT     = 3;

    /** Constructor. Private toegang omdat dit een utility class is. */
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
    public static RelatieBericht newFamilieRechtelijkeBetrekking() {
        RelatieBericht familieRechtelijkeBetrekking = new RelatieBericht();
        familieRechtelijkeBetrekking.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return familieRechtelijkeBetrekking;
    }

    /**
     * Retourmeert een nieuwe {@link RelatieBericht} van de soort {@link SoortRelatie#HUWELIJK}.
     *
     * @return een nieuwe relatie van het soort huwelijk.
     */
    public static RelatieBericht newHuwelijk() {
        RelatieBericht huwelijk = new RelatieBericht();
        huwelijk.setSoort(SoortRelatie.HUWELIJK);
        return huwelijk;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#KIND}.
     *
     * @return een nieuwe betrokkenheid van de soort 'kind'.
     */
    public static BetrokkenheidBericht newKindBetrokkenheid() {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.KIND);
        return betrokkenheid;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#OUDER}.
     *
     * @return een nieuwe betrokkenheid van de soort 'ouder'.
     */
    public static BetrokkenheidBericht newOuderBetrokkenheid() {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.OUDER);
        return betrokkenheid;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#PARTNER}.
     *
     * @return een nieuwe betrokkenheid van de soort 'ouder'.
     */
    public static BetrokkenheidBericht newPartnerBetrokkenheid() {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.PARTNER);
        return betrokkenheid;
    }

    /**
     * Creeer een nieuw actie object van het soort Aangifte geboorte.
     *
     * @return Actie van soort Aangifte geboorte.
     */
    public static ActieBericht newInschrijvingGeboorteActie() {
        return newActieVanSoort(SoortActie.AANGIFTE_GEBOORTE);
    }

    /**
     * Creeer een nieuw actie object van het soort Aangifte geboorte.
     *
     * @return Actie van soort Aangifte geboorte.
     */
    public static ActieBericht newRegistratieOverlijdenActie() {
        return newActieVanSoort(SoortActie.AANGIFTE_OVERLIJDEN);
    }

    /**
     * Creeer een nieuw actie object van het soort Registratie nationaliteit.
     *
     * @return Actie van soort Registratie nationaliteit.
     */
    public static ActieBericht newRegistratieNationaliteitActie() {
        return newActieVanSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
    }

    /**
     * Creeer een nieuw actie object van het soort Verhuizing.
     *
     * @return Actie van soort Verhuizing.
     */
    public static ActieBericht newVerhuizingActie() {
        return newActieVanSoort(SoortActie.VERHUIZING);
    }

    /**
     * Creeer een nieuw actie object van het soort Correctie Adres.
     *
     * @return Actie van soort Verhuizing.
     */
    public static ActieBericht newCorrectieAdresNLActie() {
        return newActieVanSoort(SoortActie.CORRECTIE_ADRES_NL);
    }

    /**
     * Creeer een nieuw actie object van het soort Huwelijk.
     *
     * @return Actie van soort Huwelijk.
     */
    public static ActieBericht newHuwelijkActie() {
        return newActieVanSoort(SoortActie.HUWELIJK);
    }

    /**
     * Creeer een nieuw actie object van het soort WijzigingNaamgebruik.
     *
     * @return Actie van soort
     */
    public static ActieBericht newWijzigingNaamGebruikActie() {
        return newActieVanSoort(SoortActie.WIJZIGING_NAAMGEBRUIK);
    }

    /**
     * Creeer een nieuwe actie object van een bepaald soort.
     *
     * @param srt Soort actie.
     * @return Actie van soort srt.
     */
    private static ActieBericht newActieVanSoort(final SoortActie srt) {
        final ActieBericht actie = new ActieBericht();
        actie.setSoort(srt);
        return actie;
    }

    /**
     * Converteer Date objet naar String DatumTijd formaat.
     *
     * @param date datum
     * @return tijd in de formaat yyyyMMddHHmmss[honderdsteseconden]
     */
    public static String dateNaarDatumTijd(final Date date) {
        String resultaat;

        if (date != null) {
            // Milliseconden verwaarloosbaar

            // bolie:
            // bug in simpleDatFormat met de miliseconds. ".009" met pattern ".SS" wordt geinterpreteerd als "09"
            // ipv. "00" of desnoods "01"
            // dus bouw de string op met .SSS pattern en goii de laatste cifer weg.
            // Als we goed zouden gaan doen, moet je dit eigenlijk afronden.
            resultaat =
                (new SimpleDateFormat(DATUMTIJDSTIP_FORMAT + "S").format(date)).substring(0,
                        DATUMTIJDSTIP_FORMAT.length());
        } else {
            resultaat = "";
        }

        return resultaat;
    }

    /**
     * Converteer DatumTijd naar Date object.
     *
     * @param datumTijd datum tijd in het formaat yyyyMMddHHmmss[honderdsteseconden]
     * @return Date object
     * @throws JiBXParseException
     * @throws ParseException als de invoer niet klopt
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

                    resultaat =
                            formatter.parse(localDatumTijd.substring(0,
                                DATUMTIJDSTIP_FORMAT.length()));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Ongeldige waarde voor datum tijd, verwacht wordt " + DATUM_FORMAT + " of " + DATUMTIJDSTIP_FORMAT);
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

}

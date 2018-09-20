/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.bijhouding.datumutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * Utility class voor datum.
 */
public final class DatumUtil {

    private static final String DATUM_FORMAT = "yyyyMMdd";
    private static final SimpleDateFormat DATUM_FORMAAT = new SimpleDateFormat(DATUM_FORMAT);

    /**
     * Private constructor.
     */
    private DatumUtil() {
    }

    /**
     * Retourneert de huidige datum. De DatumAttribuut van vandaag.
     *
     * @return De huidige datum.
     */
    public static DatumAttribuut vandaag() {
        final String datumVandaagString = DATUM_FORMAAT.format(Calendar.getInstance().getTime());
        return new DatumAttribuut(Integer.parseInt(datumVandaagString));
    }

    /**
     * Retourneert de DatumAttribuut van gisteren.
     *
     * @return De DatumAttribuut van gisteren.
     */
    public static DatumAttribuut gisteren() {
        final Calendar gisteren = Calendar.getInstance();
        gisteren.add(Calendar.DATE, -1);
        final String gisterenString = DATUM_FORMAAT.format(gisteren.getTime());
        return new DatumAttribuut(Integer.parseInt(gisterenString));
    }

    /**
     * Retourneert de DatumAttribuut van morgen.
     *
     * @return De DatumAttribuut van morgen.
     */
    public static DatumAttribuut morgen() {
        final Calendar morgen = Calendar.getInstance();
        morgen.add(Calendar.DATE, 1);
        final String morgenString = DATUM_FORMAAT.format(morgen.getTime());
        return new DatumAttribuut(Integer.parseInt(morgenString));
    }

    public static Integer eenDagVerder(final Integer datum) {
        return voegDagToe(new DatumAttribuut(datum), 1).getWaarde();
    }

    public  static Integer eenDagTerug(final Integer datum) {
        return voegDagToe(new DatumAttribuut(datum), -1).getWaarde();
    }

    /**
     * Voeg n-iets toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param DatumAttribuut        de originele datum.
     * @param calendarType moet zijn Calendar.YEAR, MONTH, DATE zijn.
     * @param aantal       hoeveelheid.
     * @return de neiuwe datum
     */
    private static DatumAttribuut voegIetsToe(final DatumAttribuut datum, final int calendarType, final Integer aantal) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(datumToDate(datum));
        cal.add(calendarType, aantal);
        final String datumString = DATUM_FORMAAT.format(cal.getTime());
        return new DatumAttribuut(Integer.parseInt(datumString));

    }

    /**
     * Voeg een n-dagen toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param DatumAttribuut       de originele datum.
     * @param aantalDagen aantal dagen.
     * @return de nieuwe datum
     */
    public static DatumAttribuut voegDagToe(final DatumAttribuut datum, final Integer aantalDagen) {
        return voegIetsToe(datum, Calendar.DATE, aantalDagen);
    }

    /**
     * Voeg een n-maanden toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param DatumAttribuut         de originele datum.
     * @param aantalMaanden aantal maanden.
     * @return de neiuwe datum
     */
    public static DatumAttribuut voegMaandToe(final DatumAttribuut datum, final Integer aantalMaanden) {
        return voegIetsToe(datum, Calendar.MONTH, aantalMaanden);
    }

    /**
     * Voeg een n-jaren toe (kan ook negatief zijn) aan een bepaald datum; moet wel een volledig DatumAttribuut zijn.
     *
     * @param DatumAttribuut       de originele datum.
     * @param aantalJaren aantal jaren.
     * @return de neiuwe datum
     */
    public static DatumAttribuut voegJaarToe(final DatumAttribuut datum, final Integer aantalJaren) {
        return voegIetsToe(datum, Calendar.YEAR, aantalJaren);
    }

    private static final int MOD_DAG = 100;
    private static final int MOD_MAAND = 100;
    private static final int MOD_JAAR = 10000;

    private static final int LAATSTE_DAG_IN_JAAR = 1231;
    private static final int MAAND_PLUS_DAG = 101;
    private static final int PLUS_DAG = 1;

    private static final int FEB_29 = 229;

    private static final int START_LEAP_YEAR = 1583;
    private static final int LEAP_YEAR = 4;
    private static final int EEUW_IN_JAREN = 100;

    /**
     * Test of de opgegeven DatumAttribuut een volledig gevulde DatumAttribuut is (dus maand <> 0 en dag <> 0).
     * Let op bij een null pointer datum, wordt de waarde als 'GOED" gekeurd, deze validatie dient elders te gebeuren.
     *
     * @param DatumAttribuut de te testen datum
     * @return true als volledig (of null) false als deels onbekend.
     */
    public static boolean isVolledigDatum(final DatumAttribuut datum) {
        boolean retval = true;
        if (null != datum && null != datum.getWaarde()) {
            int waarde = datum.getWaarde();
            if (waarde == 0) {
                retval = false;
            } else {
                int dag = waarde % MOD_DAG;
                int maand = waarde / MOD_DAG % MOD_MAAND;
                int jaar = waarde / MOD_JAAR;
                retval = dag > 0 && maand > 0 && jaar > 0;
            }
        }
        return retval;
    }

    /**
     * Test of de opgegeven DatumAttribuut een geldige kalenderDatumAttribuut is.
     *
     * @param DatumAttribuut de te controleren datum.
     * @return true als het een geldige kalenderdatum.
     */
    public static boolean isGeldigeKalenderdatum(final DatumAttribuut datum) {
        boolean retval = false;

        if (datum != null && datum.getWaarde() != null) {
            if (isVolledigDatum(datum)) {
                int waarde = datum.getWaarde();
                int dag = waarde % MOD_DAG;
                int maand = (waarde / MOD_DAG % MOD_MAAND);
                int jaar = waarde / MOD_JAAR;

                DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                format.setLenient(false);

                try {
                    format.parse(jaar + "/" + maand + "/" + dag);
                    retval = true;
                } catch (ParseException e) {
                }
            }
        }

        return retval;
    }

    /**
     * Geeft jaar terug in de DatumAttribuut of 0 als die niet bepaalt kan worden.
     *
     * @param DatumAttribuut datum
     * @return jaar (0- )
     */
    public static int getJaar(final DatumAttribuut datum) {
        if (datum == null || datum.getWaarde() == null) {
            return 0;
        } else {
            return datum.getWaarde() / MOD_JAAR;
        }
    }

    /**
     * Geeft maand terug in de datum, of 0 als die niet bepaalt kan worden.
     *
     * @param DatumAttribuut datum
     * @return maand (0-12)
     */
    public static int getMaand(final DatumAttribuut datum) {
        if (datum == null || datum.getWaarde() == 0) {
            return 0;
        } else {
            return datum.getWaarde() / MOD_DAG % MOD_MAAND;
        }
    }

    /**
     * Geeft dag terug in de datum, of 0 als die niet bepaalt kan worden.
     *
     * @param DatumAttribuut datum
     * @return dag (0-31)
     */
    public static int getDag(final DatumAttribuut datum) {
        if (datum == null || datum.getWaarde() == 0) {
            return 0;
        } else {
            return datum.getWaarde() % MOD_DAG;
        }
    }

    /**
     * Test of een jaar een schrikkel jaar is.
     *
     * @param year jaar dat getest wordt
     * @return true als schrikkel jaar, false anders
     */
    public static boolean isLeapYear(final int year) {
        // not valid before this date.
        assert year >= START_LEAP_YEAR;
        return year % LEAP_YEAR == 0 && year % EEUW_IN_JAREN != 0 || year % (EEUW_IN_JAREN * LEAP_YEAR) == 0;
    }

    /**
     * Telt aantal jaren op aan de datum. Kan negatief getal zijn.
     *
     * @param DatumAttribuut datum
     * @param jaren jaren dat bij de DatumAttribuut moet worden opgeteld.
     * @return nieuwe datum
     */
    public static DatumAttribuut verhoogMetJaren(final DatumAttribuut datum, final Integer jaren) {
        if (jaren == null) {
            return new DatumAttribuut(datum.getWaarde());
        } else {
            // TODO, optellen/aftrekken van jaren kan eindigen op 29 FEB, niet schrikkeljaar. Corrigeer deze !
            int nieuweWaarde = datum.getWaarde() + jaren * MOD_JAAR;
            if (nieuweWaarde % MOD_JAAR == FEB_29 && !isLeapYear(nieuweWaarde / MOD_JAAR)) {
                // test of dit een schrikkeljaar
                nieuweWaarde--;
            }
            return new DatumAttribuut(nieuweWaarde);
        }
    }

    /**
     * Bepaal de minimale DatumAttribuut bij een deels onbende datum.
     * Merk op: bij volledig DatumAttribuut is dit de DatumAttribuut zelf.
     * Merk op: geen test op null pointer.
     *
     * @param DatumAttribuut de deels onbekende datum
     * @return de minimale geldige datum.
     */
    public static DatumAttribuut minimaalDeelOnbekendDatum(final DatumAttribuut datum) {
        int waarde = datum.getWaarde();
        if (waarde % MOD_JAAR == 0) {
            // maand onbeked, voeg een maand + 1 dag
            waarde += MAAND_PLUS_DAG;
        } else if (waarde % MOD_MAAND == 0) {
            waarde += PLUS_DAG;
        }
        return new DatumAttribuut(waarde);
    }

    /**
     * Bepaal de maximale DatumAttribuut bij een deels onbende datum.
     * Merk op: bij volledig DatumAttribuut is dit de DatumAttribuut zelf.
     * Merk op: geen test op null pointer.
     *
     * @param DatumAttribuut de deels onbekende datum
     * @return de maximale geldige datum.
     */
    public static DatumAttribuut maximaalDeelOnbekendDatum(final DatumAttribuut datum) {
        final int waarde = datum.getWaarde();
        DatumAttribuut maxDatum = datum;
        if (waarde == 0) {
            // jaar is helemaal onbekend
            maxDatum = new DatumAttribuut(LAATSTE_DAG_IN_JAAR);
        } else if (waarde % MOD_JAAR == 0) {
            // maand onbeked, voeg + 1 jaar - 1 dag
            maxDatum = voegDagToe(voegJaarToe(new DatumAttribuut(waarde + MAAND_PLUS_DAG), 1), -1);
        } else if (waarde % MOD_MAAND == 0) {
            // dag onbeked, voeg + 1 maand - 1 dag
            maxDatum = voegDagToe(voegMaandToe(new DatumAttribuut(waarde + PLUS_DAG), 1), -1);
        }
        return maxDatum;
    }

    /**
     * Vergelijk of DatumAttribuut 1 ligt op of voor datum2. Let op dat geen null pointers zijn en 'volledig' datum.
     *
     * @param datum1      datum1.
     * @param datum2      datum2.
     * @param isOpOokGoed dit maakt kleiner of gelijk.
     * @return als datum1 voor of op datum2 ligt.
     */
    private static boolean testDatum1voorDatum2(final DatumAttribuut datum1, final DatumAttribuut datum2, final boolean isOpOokGoed) {
        if (isOpOokGoed) {
            return datum1.getWaarde() <= datum2.getWaarde();
        } else {
            return datum1.getWaarde() < datum2.getWaarde();
        }
    }

    /**
     * Vergelijk of DatumAttribuut 1 ligt op of voor datum2. Beide datums kunnen deels onbekend zijn en er wordt soepel
     * mee gerekend
     * <p/>
     * Let op dat geen null pointers zijn.
     *
     * @param datum1      datum1.
     * @param datum2      datum2.
     * @param isOpOokGoed dit maakt kleiner of gelijk.
     * @return als datum1 voor of op datum2 ligt.
     */
    private static boolean testDatum1VoorDatum2Soepel(final DatumAttribuut datum1, final DatumAttribuut datum2,
                                                      final boolean isOpOokGoed)
    {
        boolean resultaat;

        // TODO null pointer check, weet nog niet over de uitkomst
        final boolean datum1Volledig = isVolledigDatum(datum1);
        final boolean datum2Volledig = isVolledigDatum(datum2);

        if (datum1Volledig && datum2Volledig) {
            // simpelste geval.
            resultaat = testDatum1voorDatum2(datum1, datum2, isOpOokGoed);
        } else if (datum2Volledig && !datum1Volledig) {
            // datum1 is deels onbekend, datum2 volledig. Zoek nu wat de max en min is van datum1 en vergelijk
            // de mindatum1 met datum2
            DatumAttribuut minDatum1 = minimaalDeelOnbekendDatum(datum1);
            resultaat = testDatum1voorDatum2(minDatum1, datum2, isOpOokGoed);
        } else {
            if (0 == datum2.getWaarde()) {
                resultaat = true;
            } else {
                // datum2 is deels onbekend, min datum1 moet of voor min datum2 of voor max datum2 liggen
                DatumAttribuut minDatum1 = minimaalDeelOnbekendDatum(datum1);
                DatumAttribuut minDatum2 = minimaalDeelOnbekendDatum(datum2);
                DatumAttribuut maxDatum2 = maximaalDeelOnbekendDatum(datum2);
                resultaat = testDatum1voorDatum2(minDatum1, minDatum2, isOpOokGoed)
                        || testDatum1voorDatum2(minDatum1, maxDatum2, isOpOokGoed);
            }
        }

        return resultaat;
    }

    /**
     * Vergelijk of DatumAttribuut 1 ligt op of voor datum2. Beide datums kunnen deels onbekend zijn en er wordt soepel
     * mee gerekend
     *
     * Let op dat geen null pointers zijn.
     *
     * @param datum1 datum1.
     * @param datum2 datum2.
     * @return als datum1 voor of op datum2 ligt.
     */
    public static boolean datumVoorOfOpDatumSoepel(final DatumAttribuut datum1, final DatumAttribuut datum2) {
        return testDatum1VoorDatum2Soepel(datum1, datum2, true);
    }

    /**
     * Vergelijk of DatumAttribuut 1 ligt voor datum2. Beide datums kunnen deels onbekend zijn en er wordt soepel
     * mee gerekend
     *
     * Let op dat geen null pointers zijn.
     *
     * @param datum1 datum1.
     * @param datum2 datum2.
     * @return als datum1 voor of op datum2 ligt.
     */
    public static boolean datumVoorDatumSoepel(final DatumAttribuut datum1, final DatumAttribuut datum2) {
        return testDatum1VoorDatum2Soepel(datum1, datum2, false);
    }

    /**
     * Test of de eerste DatumAttribuut voor of op de tweede DatumAttribuut ligt, met de soepel mogelijkste definitie.
     *
     * @param testDatumAttribuut      de DatumAttribuut die willen testen
     * @param vergelijkDatumAttribuut de DatumAttribuut waar de eerste DatumAttribuut voor moet liggen
     * @return true als de testDatumAttribuut voor de vergelijk DatumAttribuut ligt, anders false
     */
    public static boolean voorOfOpSoepeleInterpretatie(final DatumAttribuut testDatum, final DatumAttribuut vergelijkDatum) {
        final boolean resultaat;

        if (testDatum == null
                || vergelijkDatum == null
                || testDatum.getWaarde() == 0
                || vergelijkDatum.getWaarde() == 0
                || DatumUtil.getJaar(testDatum) == 0
                || DatumUtil.getJaar(vergelijkDatum) == 0
                || DatumUtil.getJaar(testDatum) < DatumUtil.getJaar(vergelijkDatum))
        {
            resultaat = true;
        } else {
            // we hebben in ieder geval een volledig jaar
            if (DatumUtil.getJaar(testDatum) > DatumUtil.getJaar(vergelijkDatum)) {
                resultaat = false;
            } else if (DatumUtil.getMaand(testDatum) == 0
                    || DatumUtil.getMaand(vergelijkDatum) == 0
                    || DatumUtil.getMaand(testDatum) < DatumUtil.getMaand(vergelijkDatum))
            {
                // jaar is hetzelfde, nu naar de maand
                resultaat = true;
            } else if (DatumUtil.getMaand(testDatum) > DatumUtil.getMaand(vergelijkDatum)) {
                resultaat = false;
            } else if (DatumUtil.getDag(testDatum) == 0
                    || DatumUtil.getDag(vergelijkDatum) == 0
                    || DatumUtil.getDag(testDatum) < DatumUtil.getDag(vergelijkDatum))
            {
                // maand is ook hetzelfde, nu de dag
                resultaat = true;
            } else {
                // nu wordt het spannend
                resultaat = DatumUtil.getDag(testDatum) <= DatumUtil.getDag(vergelijkDatum);
            }
        }

        return resultaat;
    }

    /**
     * Maximale datum.
     */
    private static final int MAX_DATUM = 99999999;

    /**
     * Minimale datum.
     */
    private static final int MIN_DATUM = 0;

    /**
     * Alleen omdat checkstyle geen inline conditionals toestaat ( (boolean) ? 1 : 2 ).
     *
     * @param DatumAttribuut         de datum
     * @param defaultWaarde de default als null
     * @return de waarde
     */
    private static int getIntValue(final DatumAttribuut datum, final int defaultWaarde) {
        if (datum == null) {
            return defaultWaarde;
        } else {
            return datum.getWaarde();
        }
    }


    /**
     * Test of een peil DatumAttribuut  geldig is binnen een periode.
     *
     * @param datumAanvangPeriode de datum, null betekent oneindig in het verleden.
     * @param datumEindePeriode   de datum, null betekent oneindig in de toekomst.
     * @param peilDatumAttribuut           de datum, mag niet null zijn.
     * @return true als geldig, false anders.
     */
    public static boolean isGeldigOp(final DatumAttribuut datumAanvangPeriode, final DatumAttribuut datumEindePeriode,
                                     final DatumAttribuut peilDatum)
    {
        if (peilDatum == null) {
            throw new IllegalArgumentException("PeilDatumAttribuut kan niet leeg zijn voor geldigheidOp plaats.");
        }
        boolean geldig;
        final int aanvangPeriode = getIntValue(datumAanvangPeriode, MIN_DATUM);
        final int eindePeriode = getIntValue(datumEindePeriode, MAX_DATUM);
        final int peil = getIntValue(peilDatum, MIN_DATUM);
        geldig = peil >= aanvangPeriode && peil < eindePeriode;

        return geldig;
    }

    /**
     * Test of een (werk) periode (begin, eind) geldig is binnen een te testen periode (datumAanvang, datumEinde).
     *
     * @param datumAanvangPeriode begin periode
     * @param datumEindePeriode   einde periode
     * @param datumBegin          begin werk periode, mag niet null zijn, moet kleiner dan einde werk periode
     * @param datumEinde          einde werk periode, mag niet null zijn, moet groter dan einde werk periode
     * @return true als geldig, false anders.
     */
    public static boolean isDatumsGeldigOpPeriode(final DatumAttribuut datumAanvangPeriode, final DatumAttribuut datumEindePeriode,
                                                  final DatumAttribuut datumBegin, final DatumAttribuut datumEinde)
    {

        // aanname: begin == null => ondeindig in het verleden, einde nul == oneindig in de toekomst.
        // variaties:
        // 1) periode geen begin, geen eind => alle combinaties van datums is geldig.
        // 2) periode heeft een einde in het verleden => testen of einde DatumAttribuut ook voor het eindPeriode is.
        //      is de einde DatumAttribuut null, faalt.
        // 3) periode heeft een einde in de toekomst => besproken en afgesproken dat dit niet gaat gebeuren.
        //      reorganisatie van plaats, gemeente landen zal alleen gebeuren in het verleden.
        //      dus is identiek aan 2).
        //      Anders kan je geen verhuizing doorgeven naar die gemeente als e weet dat de gemeente over 3 maanden
        //      wordt opgeheven en de nieuwe gemeente bestaat pas over 3 maanden.
        //      Is het een adres correctie van een gemeente die gisteren is opgeheven, dan moet je het bericht in
        //      2 hakken (tot die DatumAttribuut oude gemeente, op en na die DatumAttribuut nieuwe gemeente).
        // 4) periode heeft begin/einde, datums moeten altijd binnen deze periode liggen.
        //      alle begin DatumAttribuut voor en alle eindDatumAttribuut na faalt.

        boolean geldig;
        final int aanvangPeriode = getIntValue(datumAanvangPeriode, MIN_DATUM);
        final int eindePeriode = getIntValue(datumEindePeriode, MAX_DATUM);
        final int begin = getIntValue(datumBegin, MIN_DATUM);
        final int einde = getIntValue(datumEinde, MAX_DATUM);

        // moeten we niet eerst checken dat aanvangPeriode < eindePeriode en begin < einde ?

        if (datumAanvangPeriode == null && datumEindePeriode == null) {
            // variatie 1 altijd,
            geldig = true;
        } else if (datumAanvangPeriode == null && datumEindePeriode != null) {
            // variatie 2,3
            geldig = einde <= eindePeriode;
            // moet nog wel getest als datumBegin == datumEinde, wat functioneel dit betekent.
        } else {
            // variatie 4)
            geldig = begin < einde && begin >= aanvangPeriode && einde <= eindePeriode;
        }
        return geldig;
    }

    /**
     * Converteer een bestaande java.util.Daten naar een DatumAttribuut object, waarbij de tijdstip weggelaten wordt.
     *
     * @param date de java.util.Date object.
     * @return de geconverteerde DatumAttribuut of null, als date is null.
     */
    public static DatumAttribuut dateToDatum(final Date date) {
        if (null != date) {
            return new DatumAttribuut(Integer.parseInt(DATUM_FORMAAT.format(date)));
        }
        return null;
    }

    /**
     * Converteer volledig (aka. kalender) DatumAttribuut naar een date.
     * LET OP: we ondersteunen geen deels onbekende datum.
     *
     * @param date de datum
     * @return een java.util.date object.
     */
    public static Date datumToDate(final DatumAttribuut date) {
        if (null != date) {
            // wat doen we nu met deels onbekende DatumAttribuut ????
            final int di = date.getWaarde();
            if (di % MOD_DAG == 0) {
                throw new RuntimeException("Kan geen deels onbekende DatumAttribuut '" + di + "' niet converteren naar Date.");
            }
            // TODO, hou rekening mee met '00200831' (jaar 0020 !)
            try {
                return DateUtils.parseDateStrictly(Integer.toString(di), new String[]{DATUM_FORMAT});
            } catch (ParseException e) {
                throw new RuntimeException("Kan geen deels onbekende DatumAttribuut '" + di + "' niet converteren naar Date. "
                        + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Voeg een aantal dagen, maanden, jaren toe/af van een Datum.
     * LET OP: we ondersteune hier geen deels onbekende datums !
     *
     * @param kalenderDatumAttribuut de originele DatumAttribuut (volledig).
     * @param field         type die gebruikt worden in Calendar (bv. Calendar.DATE, Calendar.MONTH, Calendar.YEAR ..)
     * @param aantal        aantal eenheden (kan ook negatief voor terug gaan in het verleden).
     * @return de nieuwe datum
     */
    public static DatumAttribuut voegToeDatum(final DatumAttribuut kalenderDatum, final int field, final int aantal) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(datumToDate(kalenderDatum));
        cal.add(field, aantal);
        return dateToDatum(cal.getTime());
    }

//    /**
//     * Controleert of het stamgegeven geldig is op een bepaalde datum.
//     *
//     * @param bestaansPeriodeStamgegeven het stamgegeven.
//     * @param peilDatumAttribuut                  de peil datum.
//     * @return true indien het stamgegeven geldig is op peilDatum, anders false.
//     */
//    public static boolean isGeldigOp(final BestaansPeriodeStamgegeven bestaansPeriodeStamgegeven,
//                                     final DatumAttribuut peilDatum)
//    {
//        if (peilDatum == null) {
//            throw new IllegalArgumentException("PeilDatumAttribuut kan niet leeg zijn voor geldigheid op plaats.");
//        }
//        return DatumUtil.isGeldigOp(bestaansPeriodeStamgegeven.getDatumAanvangGeldigheid(),
//                bestaansPeriodeStamgegeven.getDatumEindeGeldigheid(), peilDatum);
//    }
//
//    /**
//     * Controleert of het stamgegeven geldig is in een bepaalde periode.
//     *
//     * @param bestaansPeriodeStamgegeven het stamgegeven.
//     * @param beginDatumAttribuut                 periode begin datum.
//     * @param eindDatumAttribuut                  periode einde datum.
//     * @return true indien het stamgegeven geldig is in de periode, anders false.
//     */
//    public static boolean isGeldigInPeriode(final BestaansPeriodeStamgegeven bestaansPeriodeStamgegeven,
//                                            final DatumAttribuut beginDatum, final DatumAttribuut eindDatum)
//    {
//        if (beginDatum != null && eindDatum != null && beginDatum.equals(eindDatum)) {
//            throw new IllegalArgumentException("Begin Datum en eind Datum kunnen niet dezelfde zijn.");
//        }
//        return DatumUtil.isDatumsGeldigOpPeriode(bestaansPeriodeStamgegeven.getDatumAanvangGeldigheid(),
//                bestaansPeriodeStamgegeven.getDatumEindeGeldigheid(),
//                beginDatum, eindDatum);
//    }
}

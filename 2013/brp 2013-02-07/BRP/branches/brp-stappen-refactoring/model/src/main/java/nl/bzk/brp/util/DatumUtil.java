/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

/** Utility class voor datum. */
public final class DatumUtil {
    private static final String         DATUM_FORMAT  = "yyyyMMdd";
    // FastDateFormat gebruikt daar SimpleDateFormat niet thread safe is.
    private static final FastDateFormat DATUM_FORMAAT = FastDateFormat.getInstance(DATUM_FORMAT);

    /** Private constructor. */
    private DatumUtil() {
    }

    /**
     * Retourneert de huidige datum. De datum van vandaag.
     *
     * @return De huidige datum.
     */
    public static Datum vandaag() {
        final String datumVandaagString = DATUM_FORMAAT.format(Calendar.getInstance().getTime());
        return new Datum(Integer.parseInt(datumVandaagString));
    }

    /**
     * Retourneert de datum van gisteren.
     *
     * @return De datum van gisteren.
     */
    public static Datum gisteren() {
        Calendar gisteren = Calendar.getInstance();
        gisteren.add(Calendar.DATE, -1);
        final String gisterenString = DATUM_FORMAAT.format(gisteren.getTime());
        return new Datum(Integer.parseInt(gisterenString));
    }

    /**
     * Retourneert de datum van morgen.
     *
     * @return De datum van morgen.
     */
    public static Datum morgen() {
        Calendar morgen = Calendar.getInstance();
        morgen.add(Calendar.DATE, 1);
        final String morgenString = DATUM_FORMAAT.format(morgen.getTime());
        return new Datum(Integer.parseInt(morgenString));
    }

    private static final int MOD_DAG   = 100;
    private static final int MOD_MAAND = 100;
    private static final int MOD_JAAR  = 10000;

    /**
     * Test of de opgegevn datum een volledig gevulde datum is (dus maan <> 0 en dag <> 0).
     * Let op bij een null pointer datum, wordt de waarde als 'GOED" gekeurd, deze validatie dient elders te gebeuren.
     *
     * @param datum de te testen datum
     * @return true als volledig (of null) false als deels onbekend.
     */
    public static boolean isVolledigDatum(final Datum datum) {
        boolean retval = true;
        if (null != datum && null != datum.getWaarde()) {
            int waarde = datum.getWaarde();
            if (waarde == 0) {
                retval = false;
            } else {
                int dag = waarde % MOD_DAG;
                int maand = (waarde / MOD_DAG) % MOD_MAAND;
                int jaar = (waarde / MOD_JAAR);
                retval = ((dag > 0) && (maand > 0) && (jaar > 0));
            }
        }
        return retval;
    }

    private static final int MAX_DATUM = 99999999;
    private static final int MIN_DATUM = 0;

    /**
     * Alleen omdat checkstyle geen tertiare oprator afraadt ( (boolean) ? 1 : 2 ).
     *
     * @param datum de datum
     * @param defaultWaarde de default als null
     * @return de waarde
     */
    private static int getIntValue(final Datum datum, final int defaultWaarde) {
        if (datum == null) {
            return defaultWaarde;
        } else {
            return datum.getWaarde().intValue();
        }
    }


    /**
     * Test of een peil datum  geldig is binnen een periode.
     *
     * @param datumAanvangPeriode de datum, null betekent oneindig in het verleden.
     * @param datumEindePeriode de datum, null betekent oneindig in de toekomst.
     * @param peilDatum de datum, mag niet null zijn.
     * @return true als geldig, false anders.
     */
    public static boolean isGeldigOp(final Datum datumAanvangPeriode, final Datum datumEindePeriode,
        final Datum peilDatum)
    {
        if (peilDatum == null) {
            throw new IllegalArgumentException("Peildatum kan niet leeg zijn voor geldigheidOp plaats.");
        }
        boolean geldig = false;
        int aanvangPeriode = getIntValue(datumAanvangPeriode, MIN_DATUM);
        int eindePeriode = getIntValue(datumEindePeriode, MAX_DATUM);
        int peil = getIntValue(peilDatum, MIN_DATUM);
        geldig = (peil >= aanvangPeriode && peil < eindePeriode);

        return geldig;
    }

    /**
     * Test of een (werk) periode (begin, eind) geldig is binnen een te testen periode (datumAanvang, datumEinde).
     *
     * @param datumAanvangPeriode begin periode
     * @param datumEindePeriode einde periode
     * @param datumBegin begin werk periode, mag niet null zijn, moet kleiner dan einde werk periode
     * @param datumEinde einde werk periode, mag niet null zijn, moet groter dan einde werk periode
     * @return true als geldig, false anders.
     */
    public static boolean isDatumsGeldigOpPeriode(final Datum datumAanvangPeriode,
        final Datum datumEindePeriode, final Datum datumBegin, final Datum datumEinde)
    {

        // aanname: begin == null => ondeindig in het verleden, einde nul == oneindig in de toekomst.
        // variaties:
        // 1) periode geen begin, geen eind => alle combinaties van datums is geldig.
        // 2) periode heeft een einde in het verleden => testen of einde datum ook voor het eindPeriode is.
        //      is de einde datum null, faalt.
        // 3) periode heeft een einde in de toekomst => besproken en afgesproken dat dit niet gaat gebeuren.
        //      reorganisatie van plaats, gemeente landen zal alleen gebeuren in het verleden.
        //      dus is identiek aan 2).
        //      Anders kan je geen verhuizing doorgeven naar die gemeente als e weet dat de gemeente over 3 maanden
        //      wordt opgeheven en de nieuwe gemeente bestaat pas over 3 maanden.
        //      Is het een adres correctie van een gemeente die gisteren is opgeheven, dan moet je het bericht in
        //      2 hakken (tot die datum oude gemeente, op en na die datum nieuwe gemeente).
        // 4) periode heeft begin/einde, datums moeten altijd binnen deze periode liggen.
        //      alle begin datum voor en alle einddatum na faalt.

        boolean geldig = false;
        int aanvangPeriode = getIntValue(datumAanvangPeriode, MIN_DATUM);
        int eindePeriode = getIntValue(datumEindePeriode, MAX_DATUM);
        int begin = getIntValue(datumBegin, MIN_DATUM);
        int einde = getIntValue(datumEinde, MAX_DATUM);

        // moeten we niet eerst checken dat aanvangPeriode < eindePeriode en begin < einde ?

        if (datumAanvangPeriode == null && datumEindePeriode == null) {
            // variatie 1 altijd,
            geldig = true;
        } else if (datumAanvangPeriode == null && datumEindePeriode != null) {
            // variatie 2,3
            geldig = (einde <= eindePeriode);
            // moet nog wel getest als datumBegin == datumEinde, wat functioneel dit betekent.
        } else {
            // variatie 4)
            geldig = (begin < einde) && (begin >= aanvangPeriode) && (einde <= eindePeriode);
        }
        return geldig;
    }

    /**
     * Converteer een bestaande java.util.Daten naar een Datum object, waarbij de tijdstip weggelaten wordt.
     *
     * @param date de java.util.Date object.
     * @return de geconverteerde Datum of null, als date is null.
     */
    public static Datum dateToDatum(final Date date) {
        if (null != date) {
            return new Datum(Integer.parseInt(DATUM_FORMAAT.format(date)));
        }
        return null;
    }

    /**
     * Converteer volledig (aka. kalender) Datum naar een date.
     * LET OP: we ondersteunen geen deels onbekende datum.
     *
     * @param date de datum
     * @return een java.util.date object.
     */
    public static Date datumToDate(final Datum date) {
        if (null != date) {
            // wat doen we nu met deels onbekende datum ????
            int di = date.getWaarde().intValue();
            if ((di % MOD_DAG) == 0) {
                throw new RuntimeException("Kan geen deels onbekende datum '" + di + "' niet converteren naar Date.");
            }
            // TODO, hou rekening mee met '00200831' (jaar 0020 !)
            try {
                return DateUtils.parseDateStrictly("" + di, new String[]{ DATUM_FORMAT });
            } catch (ParseException e) {
                throw new RuntimeException("Kan geen deels onbekende datum '" + di + "' niet converteren naar Date. "
                    + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Voeg een aantal dagen, maanden, jaren toe/af van een Datum.
     * LET OP: we ondersteune hier geen deels onbekende datums !
     *
     * @param kalenderDatum de originele datum (volledig).
     * @param field type die gebruikt worden in Calendar (bv. Calendar.DATE, Calendar.MONTH, Calendar.YEAR ..)
     * @param aantal aantal eenheden (kan ook negatief voor terug gaan in het verleden).
     * @return de nieuwe datum
     */
    public static Datum voegToeDatum(final Datum kalenderDatum, final int field, final int aantal) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datumToDate(kalenderDatum));
        cal.add(field, aantal);
        return dateToDatum(cal.getTime());
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Embeddable;
import org.apache.commons.lang.time.FastDateFormat;


/**
 * Attribuut wrapper klasse voor Datum (Mag nooit deels onbekend zijn!).
 */
@Embeddable
public class DatumAttribuut extends AbstractDatumAttribuut {

    private static final String DATUM_FORMAT = "yyyyMMdd";
    // FastDateFormat gebruikt daar SimpleDateFormat niet thread safe is.
    private static final FastDateFormat DATUM_FORMAAT = FastDateFormat.getInstance(DATUM_FORMAT);

    private static final int MOD_JAAR  = 10000;

    private static final int FEB_29 = 229;

    private static final int START_LEAP_YEAR = 1583;
    private static final int LEAP_YEAR       = 4;
    private static final int EEUW_IN_JAREN   = 100;

    /**
     * Maximale datum.
     */
    private static final int MAX_DATUM = 99999999;

    /**
     * Minimale datum.
     */
    private static final int MIN_DATUM = 0;

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
     * @param datum datum
     * @param jaren jaren dat bij de DatumAttribuut moet worden opgeteld.
     * @return nieuwe datum
     */
    public static DatumAttribuut verhoogMetJaren(final DatumAttribuut datum, final Integer jaren) {
        if (jaren == null) {
            return new DatumAttribuut(datum.getWaarde());
        } else {
            int nieuweWaarde = datum.getWaarde() + jaren * MOD_JAAR;
            if (nieuweWaarde % MOD_JAAR == FEB_29 && !isLeapYear(nieuweWaarde / MOD_JAAR)) {
                // test of dit een schrikkeljaar
                nieuweWaarde--;
            }
            return new DatumAttribuut(nieuweWaarde);
        }
    }

    /**
     * Vergelijk of DatumAttribuut 1 ligt op of voor datum2. Let op dat geen null pointers zijn en 'volledig' datum.
     *
     * @param datum1      datum1.
     * @param datum2      datum2.
     * @param isOpOokGoed dit maakt kleiner of gelijk.
     * @return als datum1 voor of op datum2 ligt.
     */
    public static boolean testDatum1voorDatum2(final DatumAttribuut datum1, final DatumAttribuut datum2,
        final boolean isOpOokGoed)
    {
        if (isOpOokGoed) {
            return datum1.getWaarde() <= datum2.getWaarde();
        } else {
            return datum1.getWaarde() < datum2.getWaarde();
        }
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
    public static boolean isDatumsGeldigOpPeriode(final DatumAttribuut datumAanvangPeriode,
        final DatumAttribuut datumEindePeriode, final DatumAttribuut datumBegin, final DatumAttribuut datumEinde)
    {

        // aanname: begin == null => ondeindig in het verleden, einde nul == oneindig in de toekomst.
        // variaties:
        // 1) periode geen begin, geen eind => alle combinaties van datums is geldig.
        // 2) periode heeft een einde in het verleden => testen of einde DatumAttribuut ook voor het eindPeriode is.
        // is de einde DatumAttribuut null, faalt.
        // 3) periode heeft een einde in de toekomst => besproken en afgesproken dat dit niet gaat gebeuren.
        // reorganisatie van plaats, gemeente landen zal alleen gebeuren in het verleden.
        // dus is identiek aan 2).
        // Anders kan je geen verhuizing doorgeven naar die gemeente als e weet dat de gemeente over 3 maanden
        // wordt opgeheven en de nieuwe gemeente bestaat pas over 3 maanden.
        // Is het een adres correctie van een gemeente die gisteren is opgeheven, dan moet je het bericht in
        // 2 hakken (tot die DatumAttribuut oude gemeente, op en na die DatumAttribuut nieuwe gemeente).
        // 4) periode heeft begin/einde, datums moeten altijd binnen deze periode liggen.
        // alle begin DatumAttribuut voor en alle eindDatumAttribuut na faalt.

        final boolean geldig;

        final int aanvangPeriode;
        if (datumAanvangPeriode != null) {
            aanvangPeriode = datumAanvangPeriode.getIntWaarde(MIN_DATUM);
        } else {
            aanvangPeriode = MIN_DATUM;
        }

        final int eindePeriode;
        if (datumEindePeriode != null) {
            eindePeriode = datumEindePeriode.getIntWaarde(MAX_DATUM);
        } else {
            eindePeriode = MAX_DATUM;
        }

        final int begin;
        if (datumBegin != null) {
            begin = datumBegin.getIntWaarde(MIN_DATUM);
        } else {
            begin = MIN_DATUM;
        }

        final int einde;
        if (datumEinde != null) {
            einde = datumEinde.getIntWaarde(MAX_DATUM);
        } else {
            einde = MAX_DATUM;
        }

        // moeten we niet eerst checken dat aanvangPeriode < eindePeriode en begin < einde ?

        if (datumAanvangPeriode == null && datumEindePeriode == null) {
            // variatie 1 altijd,
            geldig = true;
        } else if (datumAanvangPeriode == null) {
            // variatie 2,3
            geldig = einde <= eindePeriode;
            // moet nog wel getest als datumBegin == datumEinde, wat functioneel dit betekent.
        } else {
            // variatie 4)
            geldig = begin < einde && begin >= aanvangPeriode && einde <= eindePeriode;
        }
        return geldig;
    }







    public DatumAttribuut() {
        super();
    }

    /**
     * Constructor voor DatumAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public DatumAttribuut(final Integer waarde) {
        super(waarde);
        if (!isVolledigDatumWaarde()) {
            throw new IllegalArgumentException("De waarde van de datum mag niet deels onbekend zijn!");
        }
    }

    /**
     * Constructor die op basis van een deels onbekende datum type een volledige datum type maakt. Dit mag alleen als de datumHelemaalBekend een volledige
     * datum is.
     *
     * @param datumHelemaalBekend een volledige datum.
     */
    public DatumAttribuut(final DatumEvtDeelsOnbekendAttribuut datumHelemaalBekend) {
        this(datumHelemaalBekend.getWaarde());
    }

    /**
     * Constructor voor DatumEvtDeelsOnbekendAttribuut die de waarde toekent via een Date object.
     *
     * @param waarde De waarde van het attribuut.
     */
    public DatumAttribuut(final Date waarde) {
        this(geefIntegerWaardeVanDate(waarde));
    }

    /**
     * Geeft de integer waarde van een Date object.
     *
     * @param waarde the waarde
     * @return integer waarde van de datum
     */
    private static Integer geefIntegerWaardeVanDate(final Date waarde) {
        if (waarde != null) {
            return Integer.parseInt(DATUM_FORMAAT.format(waarde));
        } else {
            throw new IllegalArgumentException("U dient een geldig Date object mee te geven.");
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.apache.commons.lang3.StringUtils;

/**
 * Deze class representeert het BRP datumTijd type.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDatumTijd extends AbstractBrpAttribuutMetOnderzoek implements Comparable<BrpDatumTijd> {

    /**
     * GMT tijdzone wordt gebruikt voor Timestamps.
     */
    public static final TimeZone BRP_TIJDZONE = TimeZone.getTimeZone("UTC");
    /**
     * Leeg BrpDatumTijd object.
     */
    public static final BrpDatumTijd NULL_DATUM_TIJD = new BrpDatumTijd(0L, null);

    private static final long serialVersionUID = 1L;
    private static final long MILLIS_FACTOR = 1000L;
    private static final long EEN_UUR = 10_000L;
    private static final long TIJD_FACTOR = 1_000_000L;

    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmssSSS";
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Maakt een BrpDatumTijd object met onderzoek.
     * @param waarde de datumTijdMillis als long in de vorm van jjjjmmdduummssµµµ.
     * @param onderzoek het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    private BrpDatumTijd(
            @Element(name = "waarde", required = false) final Long waarde,
            @Element(name = "onderzoek", required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }

    /**
     * Maakt een BrpDatumTijd object.
     * @param javaDate De datumtijd, mag niet null zijn.
     */
    public BrpDatumTijd(final Date javaDate) {
        this(javaDate, null);
    }

    /**
     * Maakt een BrpDatumTijd object met onderzoek.
     * @param javaDate De datumtijd, mag niet null zijn.
     * @param onderzoek het onderzoek waar deze datum onder valt. Mag NULL zijn.
     */
    public BrpDatumTijd(final Date javaDate, final Lo3Onderzoek onderzoek) {
        super(convertToLong(javaDate), onderzoek);
    }

    private static Long convertToLong(final Date javaDate) {
        if (javaDate == null) {
            throw new NullPointerException("Argument 'javaDate' is null");
        }
        final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setTimeZone(BRP_TIJDZONE);
        return Long.parseLong(format.format(javaDate));
    }

    /**
     * Maakt een BrpDatumTijd object .
     * @param datumTijdMillis de datumTijdMillis als long in de vorm van jjjjmmdduummssµµµ.
     * @param onderzoek onderzoek voor datum/tijd
     * @return de nieuwe BrpDatumTijd
     */
    public static BrpDatumTijd fromDatumTijdMillis(final Long datumTijdMillis, final Lo3Onderzoek onderzoek) {
        return new BrpDatumTijd(datumTijdMillis, onderzoek);
    }

    /**
     * Maakt een BrpDatumTijd object .
     * @param datumTijd de datumTijd als Long in de vorm van jjjjmmdduummss.
     * @param onderzoek het onderzoek behorende bij deze datum-tijd.
     * @return de nieuwe BrpDatumTijd
     */
    public static BrpDatumTijd fromDatumTijd(final Long datumTijd, final Lo3Onderzoek onderzoek) {
        if (!BrpValidatie.isGeldigDatumFormaatYYYYMMDDHHMMSS(datumTijd)) {
            throw new IllegalArgumentException(String.format("Datum/tijd is niet van het formaat jjjjmmdduummss: %s", datumTijd));
        }
        return BrpDatumTijd.fromDatumTijdMillis(datumTijd * MILLIS_FACTOR, onderzoek);
    }

    /**
     * Maakt een BrpDatumTijd object met de tijd gezet op 01:00:00 met Onderzoek.
     * @param datum de datum als integer in de vorm van jjjjmmdd.
     * @param onderzoek het onderzoek behorende bij deze datum-tijd.
     * @return de nieuwe BrpDatumTijd
     */
    public static BrpDatumTijd fromDatum(final Integer datum, final Lo3Onderzoek onderzoek) {
        if (!BrpValidatie.isGeldigDatumFormaatYYYYMMDD(datum)) {
            throw new IllegalArgumentException(String.format("Datum is niet gevuld of niet van het formaat jjjjmmdd: %s", datum));
        }
        final long datumTijd = datum * TIJD_FACTOR;
        final long datumTijd1Uur = datumTijd + EEN_UUR;
        return BrpDatumTijd.fromDatumTijd(datumTijd1Uur, onderzoek);
    }

    /**
     * @param lo3Datum de LO3 datum
     * @return de BRP datum tijd representatie van deze LO3 datum. De tijd wordt geconverteerd naar 01:00:00
     */
    public static BrpDatumTijd fromLo3Datum(final Lo3Datum lo3Datum) {
        final Integer datum = lo3Datum.getWaarde() == null ? null : Integer.valueOf(lo3Datum.getWaarde());
        return fromDatum(datum, lo3Datum.getOnderzoek());
    }

    /**
     * @param lo3Datumtijdstempel de LO3 datum-tijd
     * @return de BRP datum tijd representatie van deze LO3 datum-tijd.
     */
    public static BrpDatumTijd fromLo3Datumtijdstempel(final Lo3Datumtijdstempel lo3Datumtijdstempel) {
        final Long datum = lo3Datumtijdstempel.getWaarde() == null ? null : Long.valueOf(lo3Datumtijdstempel.getWaarde());
        return fromDatumTijdMillis(datum, lo3Datumtijdstempel.getOnderzoek());
    }

    /**
     * Unwrap een BrpDatumTijd object om de String waarde terug te krijgen.
     * @param attribuut De BrpDatum, mag null zijn.
     * @return Een Long object, of null als de BrpDatumTijd null was.
     */
    public static Long unwrap(final BrpDatumTijd attribuut) {
        return (Long) unwrapImpl(attribuut);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.BrpAttribuutMetOnderzoek#getWaarde()
     */
    @Override
    @Element(name = "waarde", required = false)
    public Long getWaarde() {
        return unwrap(this);
    }

    @Override
    public int compareTo(final BrpDatumTijd andereDatumTijd) {
        if (!BrpValidatie.isAttribuutGevuld(andereDatumTijd)) {
            throw new NullPointerException("Andere datumtijd is null");
        }

        return getWaarde().compareTo(andereDatumTijd.getWaarde());
    }

    /**
     * Converteer naar Lo3 datum en doet daarbij een conversie van UTC naar CET/CEST.
     * @return lo3 datum
     */
    public Lo3Datum converteerNaarLo3Datum() {
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setTimeZone(BRP_TIJDZONE);
        return new Lo3Datum(format.format(getJavaDate()), getOnderzoek());
    }

    /**
     * Converteer naar Lo3 datumtijdstempel.
     * @return lo3 datumtijdstempel
     */
    public Lo3Datumtijdstempel converteerNaarLo3Datumtijdstempel() {
        return new Lo3Datumtijdstempel(getWaarde());
    }

    /**
     * Geef de waarde van datum tijd.
     * @return datum tijd zonder milliseconden
     */
    public long getDatumTijd() {
        return getWaarde() / MILLIS_FACTOR;
    }

    /**
     * Geef de waarde van java date.
     * @return een Java-Date.
     */
    public Date getJavaDate() {
        final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setTimeZone(BRP_TIJDZONE);
        try {
            return format.parse(StringUtils.leftPad(String.valueOf(getWaarde()), DATE_TIME_FORMAT.length(), '0'));
        } catch (final ParseException e) {
            throw new IllegalStateException("Unexpected fout tijdens parsen van datumtijd", e);
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Text;

/**
 * Deze class representeert het BRP datumTijd type.
 * 
 * Deze class is immutable en threadsafe.
 * 
 * 
 * 
 */
public final class BrpDatumTijd implements BrpAttribuut, Comparable<BrpDatumTijd> {

    /**
     * GMT tijdzone wordt gebruikt voor Timestamps.
     */
    public static final TimeZone BRP_TIJDZONE = TimeZone.getTimeZone("GMT+0:00");

    private static final long MILLIS_FACTOR = 1000L;

    private static final long TIJD_FACTOR = 1000000L;

    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmssSSS";

    @Text
    private final long datumTijdMillis;

    /**
     * Maakt een BrpDatumTijd object .
     * 
     * @param datumTijdMillis
     *            de datumTijdMillis als long in de vorm van jjjjmmdduummssµµµ.
     */
    private BrpDatumTijd(@Text final long datumTijdMillis) {
        if (!ValidationUtils.isGeldigDatumFormaatYYYYMMDDHHMMSSMMM(datumTijdMillis)) {
            throw new IllegalArgumentException(String.format(
                    "Datum/tijd is niet van het formaat jjjjmmdduummssµµµ: %s", datumTijdMillis));
        }
        this.datumTijdMillis = datumTijdMillis;
    }

    /**
     * Maakt een BrpDatumTijd object.
     * 
     * @param javaDate
     *            De datumtijd, mag niet null zijn.
     */
    public BrpDatumTijd(final Date javaDate) {
        if (javaDate == null) {
            throw new NullPointerException("Argument 'javaDate' is null");
        }
        final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setTimeZone(BRP_TIJDZONE);
        datumTijdMillis = Long.parseLong(format.format(javaDate));
    }

    /**
     * Maakt een BrpDatumTijd object .
     * 
     * @param datumTijdMillis
     *            de datumTijdMillis als long in de vorm van jjjjmmdduummssµµµ.
     * @return de nieuwe BrpDatumTijd
     */
    public static BrpDatumTijd fromDatumTijdMillis(final long datumTijdMillis) {
        return new BrpDatumTijd(datumTijdMillis);
    }

    /**
     * Maakt een BrpDatumTijd object .
     * 
     * @param datumTijd
     *            de datumTijd als long in de vorm van jjjjmmdduummss.
     * @return de nieuwe BrpDatumTijd
     */
    public static BrpDatumTijd fromDatumTijd(final long datumTijd) {
        if (!ValidationUtils.isGeldigDatumFormaatYYYYMMDDHHMMSS(datumTijd)) {
            throw new IllegalArgumentException(String.format("Datum/tijd is niet van het formaat jjjjmmdduummss: %s",
                    datumTijd));
        }
        return BrpDatumTijd.fromDatumTijdMillis(datumTijd * MILLIS_FACTOR);
    }

    /**
     * Maakt een BrpDatumTijd object met de tijd gezet op 00:00:00.
     * 
     * @param datum
     *            de datum als integer in de vorm van jjjjmmdd.
     * @return de nieuwe BrpDatumTijd
     */
    public static BrpDatumTijd fromDatum(final int datum) {
        if (!ValidationUtils.isGeldigDatumFormaatYYYYMMDD(datum)) {
            throw new IllegalArgumentException(String.format("Datum is niet van het formaat jjjjmmdd: %s", datum));
        }
        final long datumTijd = datum * TIJD_FACTOR;
        return BrpDatumTijd.fromDatumTijd(datumTijd);
    }

    @Override
    public int compareTo(final BrpDatumTijd andereDatumTijd) {
        return Long.valueOf(datumTijdMillis).compareTo(Long.valueOf(andereDatumTijd.datumTijdMillis));
    }

    /**
     * Converteer naar Lo3 datum.
     * 
     * @return lo3 datum
     */
    public Lo3Datum converteerNaarLo3Datum() {
        return new Lo3Datum((int) (datumTijdMillis / (TIJD_FACTOR * MILLIS_FACTOR)));
    }

    /**
     * Converteer naar Lo3 datumtijdstempel.
     * 
     * @return lo3 datumtijdstempel
     */
    public Lo3Datumtijdstempel converteerNaarLo3Datumtijdstempel() {
        return new Lo3Datumtijdstempel(datumTijdMillis);
    }

    /**
     * @return datum tijd met milliseconden
     */
    public long getDatumTijdMillis() {
        return datumTijdMillis;
    }

    /**
     * @return datum tijd zonder milliseconden
     */
    public long getDatumTijd() {
        return datumTijdMillis / MILLIS_FACTOR;
    }

    /**
     * 
     * @return een Java-Date.
     */
    public Date getJavaDate() {
        final SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        format.setTimeZone(BRP_TIJDZONE);
        try {
            return format.parse(StringUtils.leftPad(String.valueOf(datumTijdMillis), DATE_TIME_FORMAT.length(), '0'));
        } catch (final ParseException e) {
            throw new IllegalStateException("Unexpected fout tijdens parsen van datumtijd", e);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDatumTijd)) {
            return false;
        }
        final BrpDatumTijd castOther = (BrpDatumTijd) other;
        return new EqualsBuilder().append(datumTijdMillis, castOther.datumTijdMillis).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumTijdMillis).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("datumTijd", datumTijdMillis).toString();
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import com.google.common.base.MoreObjects;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * DatumFormatterUtil.
 */
public final class DatumFormatterUtil {
    /**
     * Formaat van dates in berichten. Om aan de DatumTijd-s XSD type te voldoen formatteren we de tijd handmatig. Hier liever DateTimeFormatter
     * .ISO_OFFSET_DATE_TIME gebruiken. Dit kan nog niet vanwege het feit dat DatumTijd-s altijd een 3-cijferige milliseconde verwacht en ISO_OFFSET_DATE_TIME
     * ook met 1- of 2-cijferige overweg kan, in geval van 0 waarden.
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")).appendOffsetId().toFormatter()
            .withZone(DatumUtil.BRP_ZONE_ID);
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final int LENGTE_DATUM = 8;
    private static final int LENGTE_JAAR = 4;
    private static final int LENGTE_MAAND = 2;
    private static final String NUL_ALS_STRING = "0";
    private static final String DUBBEL_NUL_ALS_STRING = "00";
    private static final String VIER_NULLEN_ALS_STRING = "0000";
    private static final String DUBBEL_NEGEN_ALS_STRING = "99";
    private static final String VIER_NEGENS_ALS_STRING = "9999";
    private static final String KOPPELTEKEN = "-";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter FORMATTER_JAAR_MND = DateTimeFormatter.ofPattern("yyyyMM");

    private DatumFormatterUtil() {
    }

    /**
     * @param datumTijd datumTijd
     * @return zonedDateTime
     */
    public static ZonedDateTime vanXsdDatumTijdNaarZonedDateTime(final String datumTijd) {
        if (datumTijd == null) {
            return null;
        }
        return ZonedDateTime.parse(datumTijd, DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * @param datum datum
     * @return localDate
     */
    public static LocalDate vanXsdDatumNaarLocalDate(final String datum) {
        if (datum == null) {
            return null;
        }
        return LocalDate.parse(datum, DateTimeFormatter.ISO_DATE);
    }

    /**
     * @param zonedDateTime zonedDateTime
     * @return xsd datetime
     */
    public static String vanZonedDateTimeNaarXsdDateTime(final ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.format(DATE_TIME_FORMATTER);
    }


    /**
     * @param localDate localDate
     * @return xsd date
     */
    public static String vanLocalDateNaarXsdDate(final LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * @param localDate localDate
     * @return xsd date
     */
    public static Integer vanLocalDateNaarInteger(final LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Integer.parseInt(localDate.format(FORMATTER));
    }

    /**
     * Zet een getal representatie van een (deels onbekende) datum om naar een tekstuele representatie dat dezelfde datum representeert.
     * @param datum een getal representatie van een (deels onbekende) datum.
     * @return een tekstuele representatie van een (deels onbekende) datum.
     */
    public static String datumAlsGetalNaarDatumAlsString(final Integer datum) {
        String resultaat = null;
        if (datum != null) {
            resultaat = "";
            String dateString = String.valueOf(datum);
            // Een datum kan 0 zijn. Dit zie je bij stamgegevens.
            if (dateString.length() < LENGTE_DATUM) {
                dateString = StringUtils.rightPad(
                        dateString.length() < LENGTE_JAAR ? StringUtils.leftPad(dateString, LENGTE_JAAR, NUL_ALS_STRING) : dateString,
                        LENGTE_DATUM,
                        NUL_ALS_STRING);
            }
            final String jaar = dateString.substring(0, LENGTE_JAAR);
            final String maand = dateString.substring(LENGTE_JAAR, LENGTE_JAAR + LENGTE_MAAND);
            final String dag = dateString.substring(LENGTE_JAAR + LENGTE_MAAND, LENGTE_DATUM);
            resultaat += jaar;
            if (!DUBBEL_NUL_ALS_STRING.equals(maand)) {
                resultaat += KOPPELTEKEN;
                resultaat += maand;
                if (!DUBBEL_NUL_ALS_STRING.equals(dag)) {
                    resultaat += KOPPELTEKEN;
                    resultaat += dag;
                }
            }
        }
        return resultaat;
    }

    /**
     * Zet een tekstuele representatie van een deels onbekende datum (formaat = yyyy-MM-dd) om naar een getal dat dezelfde datum representeert.
     * @param datum tekstuele representatie van een deels onbekende datum
     * @return getal representatie van een deels onbekende datum, null indien tekstuele representatie (1) null is, (2) leeg is of (3) een invalide formaat heeft
     */
    public static Integer deelsOnbekendeDatumAlsStringNaarGetal(final String datum) {
        try {
            final DeelsOnbekendeDatum deelsOnbekendeDatum = new DeelsOnbekendeDatum(datum);
            if (!deelsOnbekendeDatum.isGeldig()) {
                return null;
            }

            String dateString = deelsOnbekendeDatum.datum.replaceAll("-", "");
            if (dateString.length() < LENGTE_DATUM) {
                dateString = StringUtils.rightPad(dateString, LENGTE_DATUM, NUL_ALS_STRING);
            }
            return Integer.parseInt(dateString);

        } catch (IllegalStateException e) {
            LOG.debug("Fout bij conversie deels onbekende datum naar getal", e);
            return null;
        }
    }

    /**
     * Zet een getals representatie van een deels onbekende datum (formaat = yyyy-MM-dd) om naar een tekstuele representatie die dezelfde datum representeert.
     * @param datum getalsrepresentatie van een deels onbekende datum
     * @return tekstuele representatie van een deels onbekende datum, null indien tekstuele representatie (1) null is, (2) leeg is of (3) een invalide
     * formaat heeft
     */
    public static String deelsOnbekendeDatumAlsGetalNaarString(final Integer datum) {
        final String datumAlsString = String.valueOf(datum);
        if (datum != null) {
            final boolean lengteValide = datum == 0 || datumAlsString.length() >= LENGTE_JAAR+1 && datumAlsString.length() <= LENGTE_DATUM;
            if (lengteValide) {
                final StringBuilder deelsOnbekendeDatum = new StringBuilder(StringUtils.leftPad(datumAlsString, LENGTE_DATUM, NUL_ALS_STRING));
                deelsOnbekendeDatum.insert(LENGTE_JAAR + LENGTE_MAAND, "-").insert(LENGTE_JAAR, "-");
                if (new DeelsOnbekendeDatum(deelsOnbekendeDatum.toString()).isGeldig()) {
                    return deelsOnbekendeDatum.toString();
                }
            }
        }
        return null;
    }

    /**
     * * Geeft de bovengrens voor een rangebepaling op een deels onbekende datum. Vanuit het opgegeven deel dient een bovengrens bepaald te
     * worden door aanvullen tot lengte 8 met negens.
     * Bijv(1): het opgegeven deel is '1900', dan is de bovengrens '19009999'.
     * Bijv(2): het opgegeven deel is '190001', dan is de bovengrens '19000199'.
     * Bijv(3): het opgegeven deel is '0000', dan is de bovengrens '99999999'.
     * @param deelsOnbekendeDatum de deels onbekende datum
     * @return bovengrens van de deelsOnbekendeDatum of null indien deelsOnbekendeDatum ongeldig is
     */
    public static Integer bepaalBovengrensDeelsOnbekendeDatum(final Integer deelsOnbekendeDatum) {
        final String deelsOnbekendeDatumAlsString = deelsOnbekendeDatumAlsGetalNaarString(deelsOnbekendeDatum);
        return deelsOnbekendeDatumAlsString != null ? new DeelsOnbekendeDatum(deelsOnbekendeDatumAlsString).bepaalBovengrens() : null;
    }

    private static final class DeelsOnbekendeDatum {
        private final String datum;
        private final String jaar;
        private final String maand;
        private final String dag;
        private boolean isJaarOnbekend;
        private boolean isMaandOnbekend;
        private boolean isDagOnbekend;

        private DeelsOnbekendeDatum(final String waarde) {
            if (waarde == null || waarde.isEmpty()) {
                throw new IllegalStateException("Datum moet waarde hebben");
            }
            // waarde 19 zien we als 0019
            this.datum = waarde.length() < LENGTE_JAAR ? StringUtils.leftPad(waarde, LENGTE_JAAR, NUL_ALS_STRING) : waarde;

            final String dateString = this.datum.replaceAll("-", "");
            this.jaar = dateString.substring(0, LENGTE_JAAR);
            this.maand = dateString.length() >= LENGTE_JAAR + LENGTE_MAAND ? dateString.substring(LENGTE_JAAR, LENGTE_JAAR + LENGTE_MAAND) : null;
            this.dag = dateString.length() == LENGTE_DATUM ? dateString.substring(LENGTE_JAAR + LENGTE_MAAND, LENGTE_DATUM) : null;

            this.isJaarOnbekend = VIER_NULLEN_ALS_STRING.equals(jaar);
            this.isMaandOnbekend = maand == null || DUBBEL_NUL_ALS_STRING.equals(maand);
            this.isDagOnbekend = dag == null || DUBBEL_NUL_ALS_STRING.equals(dag);
        }

        private boolean isGeldig() {
            return heeftGeldigFormaat() && isGeldigeKalendarDatum();
        }

        private boolean heeftGeldigFormaat() {
            boolean formaatIsGeldig = datum.matches("\\d{4}(-\\d{2})?(-\\d{2})?");
            boolean nulGebruikIsCorrect = !((isJaarOnbekend && !isMaandOnbekend) || (isMaandOnbekend && !isDagOnbekend));
            return formaatIsGeldig && nulGebruikIsCorrect;
        }

        private boolean isGeldigeKalendarDatum() {
            try {
                if (isJaarOnbekend || isMaandOnbekend) {
                    return true;
                } else if (isDagOnbekend) {
                    YearMonth.parse(jaar + maand, FORMATTER_JAAR_MND);
                } else {
                    LocalDate.parse(jaar + maand + dag, DateTimeFormatter.BASIC_ISO_DATE);
                }
            } catch (final DateTimeException e) {
                LOG.trace("Fout bij het parsen van datum " + this, e);
                return false;
            }
            return true;
        }

        private Integer bepaalBovengrens() {
            if (isGeldig()) {
                final String jaarBovengrens = isJaarOnbekend ? VIER_NEGENS_ALS_STRING : this.jaar;
                final String maandBovengrens = isMaandOnbekend ? DUBBEL_NEGEN_ALS_STRING : this.maand;
                final String dagBovengrens = isDagOnbekend ? DUBBEL_NEGEN_ALS_STRING : this.dag;
                return Integer.valueOf(jaarBovengrens + maandBovengrens + dagBovengrens);
            }
            return null;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("datum", datum)
                    .add("jaar", jaar)
                    .add("maand", maand)
                    .add("dag", dag)
                    .add("isJaarOnbekend", isJaarOnbekend)
                    .add("isMaandOnbekend", isMaandOnbekend)
                    .add("isDagOnbekend", isDagOnbekend)
                    .toString();
        }
    }
}

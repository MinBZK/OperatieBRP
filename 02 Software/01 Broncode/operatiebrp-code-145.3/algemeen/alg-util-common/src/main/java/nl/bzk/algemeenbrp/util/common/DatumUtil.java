/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Hulp methodes in het gebruik van datums (timezone UTC) binnen het model van de BRP.
 */
public final class DatumUtil {

    /**
     * String representation of "UTC".
     */
    public static final String UTC = "UTC";
    /**
     * String representation of "Europe/Amsterdam".
     */
    public static final String NL = "Europe/Amsterdam";
    /**
     * UTC tijdzone wordt gebruikt voor datums in de BRP.
     */
    public static final TimeZone BRP_TIJDZONE = TimeZone.getTimeZone(UTC);
    /**
     * NL tijdzone wordt gebruikt voor datums in de BRP.
     */
    public static final TimeZone NL_TIJDZONE = TimeZone.getTimeZone(NL);

    /**
     * UTC Zone Id.
     */
    public static final ZoneId BRP_ZONE_ID = ZoneId.of(DatumUtil.UTC);
    /**
     * NL Zone Id.
     */
    public static final ZoneId NL_ZONE_ID = ZoneId.of(DatumUtil.NL);

    private static final ZoneId ZONE_UTC = ZoneId.of(UTC);
    private static final String DATUM_FORMAT = "yyyyMMdd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATUM_FORMAT);
    private static final int MOD_JAAR = 10000_00_00;
    private static final int MOD_MAAND = 1_00_00;
    private static final int MOD_DAG = 100;
    private static final int MINIMALE_DATUM = 1_01_01;
    private static final int MAXIMALE_MAAND = 1231;
    private static final int MAXIMALE_DATUM = 9999_12_31;
    private static final int MINIMALE_MAAND = 101;

    /*
     * Explicit private constructor voor utility class.
     */
    private DatumUtil() {
        throw new AssertionError("Er mag geen instantie gemaakt worden van DatumUtil.");
    }

    /**
     * Converteert de gegeven datum naar een Integer in het formaat yyyyMMdd.
     * @param datum de datum
     * @return de integer waarde van de gegeven datum
     */
    public static int vanDatumNaarInteger(final Date datum) {
        return Integer.parseInt(vanDatumNaarString(datum));
    }

    /**
     * Converteert de gegeven datum naar een Integer in het formaat yyyyMMdd.
     * @param datum de datum in {@link ZonedDateTime} formaat
     * @return de integer waarde van de gegeven datum
     */
    public static int vanDatumNaarInteger(final LocalDate datum) {
        final Calendar instance = Calendar.getInstance();
        instance.set(datum.getYear(), datum.getMonthValue() - 1, datum.getDayOfMonth());
        return Integer.parseInt(vanDatumNaarString(instance.getTime()));
    }

    /**
     * Geeft de {@link Date} waarde van een {@link LocalDateTime} object.
     * @param datum {@link LocalDateTime} object
     * @return {@link Date} object
     */
    public static Date vanLocalDateTimeNaarDate(final LocalDateTime datum) {
        if (datum != null) {
            return Date.from(datum.toInstant(ZoneOffset.UTC));
        }
        return null;
    }

    /**
     * Geeft de {@link Long} waarde van een {@link LocalDateTime} object.
     * @param datum {@link LocalDateTime} object
     * @return Long waarde
     */
    public static Long vanLocalDateTimeNaarLong(final LocalDateTime datum) {
        if (datum != null) {
            return Date.from(datum.toInstant(ZoneOffset.UTC)).getTime();
        }
        return null;
    }

    /**
     * Geeft de {@link Date} waarde van een {@link ZonedDateTime} object.
     * @param datum {@link ZonedDateTime} object
     * @return {@link Date} object
     */
    public static Date vanDateTimeNaarDate(final ZonedDateTime datum) {
        if (datum != null) {
            return Date.from(datum.toInstant());
        }
        return null;
    }

    /**
     * Geeft de {@link Long} waarde van een {@link ZonedDateTime} object.
     * @param datum {@link ZonedDateTime} object
     * @return Long waarde
     */
    public static Long vanDateTimeNaarLong(final ZonedDateTime datum) {
        if (datum != null) {
            return Date.from(datum.toInstant()).getTime();
        }
        return null;
    }

    /**
     * Van ZonedDateTime naar localdate in nederland.
     * @param datum {@link ZonedDateTime} object
     * @return de localdate in nederland.
     */
    public static LocalDate vanZonedDateTimeNaarLocalDateNederland(final ZonedDateTime datum) {
        return datum.withZoneSameInstant(NL_ZONE_ID).toLocalDate();
    }

    /**
     * Geeft de {@link ZonedDateTime} waarde van een long (sinds epoch).
     * @param tijd tijd
     * @return ZonedDateTime in UTC
     */
    public static ZonedDateTime vanLongNaarZonedDateTime(final long tijd) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(tijd), DatumUtil.BRP_ZONE_ID);
    }

    /**
     * Geeft de {@link ZonedDateTime} waarde van een {@link Timestamp}.
     * @param timestamp timestamp
     * @return timestamp als zonedDateTime
     */
    public static ZonedDateTime vanTimestampNaarZonedDateTime(final Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), DatumUtil.BRP_ZONE_ID);
    }

    /**
     * Geeft de {@link LocalDateTime} waarde van een {@link Date} object.
     * @param datum {@link Date} object
     * @return {@link LocalDateTime} object
     */
    public static ZonedDateTime vanDateNaarZonedDateTime(final Date datum) {
        if (datum != null) {
            return ZonedDateTime.ofInstant(Instant.ofEpochMilli(datum.getTime()), DatumUtil.BRP_ZONE_ID);
        }
        return null;
    }

    /**
     * Geeft de {@link java.sql.Timestamp} waarde van een {@link ZonedDateTime} object.
     * @param zonedDateTime {@link ZonedDateTime} object
     * @return {@link java.sql.Timestamp} object
     */
    public static Timestamp vanZonedDateTimeNaarSqlTimeStamp(final ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            return new Timestamp(zonedDateTime.toInstant().toEpochMilli());
        }
        return null;
    }

    /**
     * Converteert de gegeven datum naar een String in het formaat 'yyyyMMdd'.
     * @param datum de datum
     * @return de string waarde van de gegeven datum
     */
    public static String vanDatumNaarString(final Date datum) {
        if (datum != null) {
            return FORMATTER.format(vanDateNaarZonedDateTime(datum));
        }
        return null;
    }

    /**
     * Maakt van een integer datum een {@link LocalDate}.
     * @param datum de datum als integer
     * @return de {@link LocalDate}
     */
    public static LocalDate vanIntegerNaarLocalDate(final int datum) {
        final String datumString = String.valueOf(datum);
        if (datumString.length() != DATUM_FORMAT.length()) {
            throw new UnsupportedOperationException(String.format("%s is een ongeldige datum.", datum));
        }
        return LocalDate.parse(datumString, FORMATTER);
    }

    /**
     * Maakt van een integer datum een {@link ZonedDateTime}.
     * @param datum de datum als integer
     * @return de {@link ZonedDateTime}
     */
    public static ZonedDateTime vanIntegerNaarZonedDateTime(final int datum) {
        return vanIntegerNaarLocalDate(datum).atStartOfDay(NL_ZONE_ID).withZoneSameInstant(ZONE_UTC);
    }

    /**
     * Geeft de {@link LocalDateTime} waarde van een {@link Date} object.
     * @param datum {@link Date} object
     * @return {@link LocalDateTime} object
     */
    public static LocalDateTime vanDateNaarLocalDateTime(final Date datum) {
        if (datum != null) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(datum.getTime()), ZoneOffset.UTC);
        }
        return null;
    }


    /**
     * Converteert een String van het formaat 'yyyyMMdd' naar een {@link Date} object.
     * @param waarde een string in het formaat 'yyyyMMdd'
     * @return de datum
     */
    public static Date vanStringNaarDatum(final String waarde) {
        if (waarde != null) {
            return Date.from(LocalDate.parse(waarde, FORMATTER).atStartOfDay(ZONE_UTC).toInstant());
        }
        return null;
    }

    /**
     * Geeft de datum+tijd op dit moment.
     * @return de datum+tijd van dit moment
     */
    public static Date nu() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(BRP_TIJDZONE);
        return calendar.getTime();
    }

    /**
     * Geeft de datum+tijd op dit moment als ZonedDateTime.
     * @return nu als zonedDateTime
     */
    public static ZonedDateTime nuAlsZonedDateTime() {
        return ZonedDateTime.now();
    }

    /**
     * Geeft de datum+tijd op dit moment als ZonedDateTime.
     * @return nu als zonedDateTime
     */
    public static ZonedDateTime nuAlsZonedDateTimeInNederland() {
        return ZonedDateTime.now().toInstant().atZone(NL_ZONE_ID);
    }

    /**
     * Geeft de datum van vandaag zonder tijd in Nederland.
     * @return de datum van vandaag
     */
    public static int vandaag() {
        return datumRondVandaag(0);
    }

    /**
     * Retourneert de datum van gisteren als integer.
     * @return datum van gisteren als integer.
     */
    public static Integer gisteren() {
        return datumRondVandaag(1);
    }

    /**
     * Retourneert de datum van morgen als integer.
     * @return datum van morgen als integer.
     */
    public static Integer morgen() {
        return datumRondVandaag(-1);
    }

    /**
     * Retourneert een datum rond vandaag als integer. minusDagen wordt van vandaag afgetrokken. Een
     * negatief aantal dagen wordt opgeteld.
     * @param minusDagen het aantal dagen dat afgetrokken wordt van vandaag
     * @return datum als integer
     */
    public static Integer datumRondVandaag(final int minusDagen) {
        final LocalDate date = LocalDate.now(NL_ZONE_ID).minus(minusDagen, ChronoUnit.DAYS);
        final String formattedDateString = FORMATTER.format(date);
        return Integer.parseInt(formattedDateString);
    }

    /**
     * bepaald de nieuwe datum door van de meegegeven datum het aantal meegegeven dagen af te trekken
     * @param datum peilDatum
     * @param minus aantal dagen voor peildatum
     * @return bepaalde datum
     */
    public static Integer bepaalDatum(final int datum, final int minus) {
        final Integer minimaleDatum = bepaalEindDatumStrengOfBeginDatumSoepel(datum, false);
        final LocalDate minimaleDate = vanIntegerNaarLocalDate(minimaleDatum);
        final LocalDate nieuweDate = minimaleDate.minus(minus, ChronoUnit.DAYS);
        final String formattedDateString = FORMATTER.format(nieuweDate);
        return Integer.parseInt(formattedDateString);
    }

    /**
     * Controleert of de gegeven peildatum binnen de gegeven periode van twee datums valt. Een
     * peildatum gelijk aan de begindatum wordt gezien als binnen de periode (inclusief) en een
     * peildatum gelijk aan de eindatum wordt gezien als buiten de periode (exclusief). De begin en
     * einddatum zijn optioneel, wanneer elk van deze ontbreekt wordt alleen gecontroleerd of de
     * peildatum groter of gelijk is aan de begindatum of andersom. Beiden onbekend zal true terug
     * geven.
     * @param peilDatum de peildatum
     * @param beginDatum de begindatum (inclusief)
     * @param eindDatum de optionele einddatum (exclusief)
     * @return true als de peildatum binnen de gegeven periode valt, anders false
     */
    public static boolean valtDatumBinnenPeriode(final int peilDatum, final Integer beginDatum, final Integer eindDatum) {
        return valtDatumBinnenPeriode(peilDatum, beginDatum, eindDatum, false);
    }

    /**
     * Controleert of de gegeven peildatum binnen de gegeven periode van twee datums valt. Een
     * peildatum gelijk aan de begindatum wordt gezien als binnen de periode (inclusief) en een
     * peildatum gelijk aan de eindatum wordt gezien als buiten de periode (inclusief). De begin en
     * einddatum zijn optioneel, wanneer elk van deze ontbreekt wordt alleen gecontroleerd of de
     * peildatum groter of gelijk is aan de begindatum of andersom. Beiden onbekend zal true terug
     * geven.
     * @param peilDatum de peildatum
     * @param beginDatum de begindatum (inclusief)
     * @param eindDatum de optionele einddatum (inclusief)
     * @return true als de peildatum binnen de gegeven periode valt, anders false
     */
    public static boolean valtDatumBinnenPeriodeInclusief(final int peilDatum, final Integer beginDatum, final Integer eindDatum) {
        return valtDatumBinnenPeriode(peilDatum, beginDatum, eindDatum, true);
    }

    private static boolean valtDatumBinnenPeriode(final int peilDatum, final Integer beginDatum, final Integer eindDatum, final boolean inclusief) {
        if (peilDatum == 0) {
            return true;
        }
        final boolean peilDatumNaBeginDatum = beginDatum == null || beginDatum <= peilDatum;
        final boolean peilDatumVoorEindDatum =
                eindDatum == null || (inclusief ? peilDatum <= bepaalEindDatumSoepel(eindDatum) : peilDatum < bepaalEindDatumSoepel(eindDatum));
        return peilDatumNaBeginDatum && peilDatumVoorEindDatum;
    }

    /**
     * Bepaald of de pijl datum binnen een periode valt waarbij inclusief de beginDatum en exclusief
     * de eindDatum. De vergelijking is "Streng" wat inhoud dat bij een onbekende datum elke
     * onzekerheid wordt afgekeurd. BV: de begin datum van een periode is gedeeltelijk onbekend
     * (20010000 - 20030205) 20010000 word nu de maximale waarde bepaald: 20011231 (20011231 -
     * 20030205); de eind datum is gedeeltelijk onbekend (20010501 - 20030000) 20030000 word nu de
     * minimale waarde 20030101 (20010501 - 20030101) Bij een onbekende pijldatum moet zowel de
     * minimale als maximale waarde binnen de periode vallen: periode 20020215 - 20030615 pijldatum
     * 20030000 (20030101 en 20031231) valt hier binnen. pijldatum 20020600 (20020601 en 20020630)
     * valt hier binnen. pijldatum 20030600 (20030601 en 20030630) valt hier buiten. pijldatum
     * 20020200 (20020201 en 20020228) valt hier buiten.
     * @param peilDatum de peildatum
     * @param beginDatum de begindatum
     * @param eindDatum de einddatum
     * @return true als de peildatum binnen de gegeven periode valt, anders false
     */
    public static boolean valtDatumBinnenPeriodeStreng(final int peilDatum, final Integer beginDatum, final Integer eindDatum) {
        final Integer begin = bepaalBeginDatumStrengOfEindDatumSoepel(beginDatum, true);
        final Integer eind = bepaalEindDatumStrengOfBeginDatumSoepel(eindDatum, true);

        if (peilDatum % MOD_DAG == 0) {
            int minPeilDatum = peilDatum;
            int maxPeilDatum = peilDatum;

            if (peilDatum == 0) {
                minPeilDatum = MINIMALE_DATUM;
                maxPeilDatum = MAXIMALE_DATUM;
            } else if (peilDatum % MOD_MAAND == 0) {
                minPeilDatum += MINIMALE_MAAND;
                maxPeilDatum += MAXIMALE_MAAND;
            } else {
                minPeilDatum += 1;
                maxPeilDatum += haalLaatsteDagVanDeMaandOp(begin);
            }

            return valtDatumBinnenPeriode(minPeilDatum, begin, eind) && valtDatumBinnenPeriode(maxPeilDatum, begin, eind);
        }
        return valtDatumBinnenPeriode(peilDatum, begin, eind);
    }

    /**
     * Bepaalt het aantal jaren tussen twee datums. Deze methode gebruikt de SOEPEL manier van
     * datums vergelijken.
     * @param begin int representatie van begin moment 'yyyyMMdd'
     * @param eind int representatie van eind moment 'yyyyMMdd'
     * @return verschil in jaren, null if begin or eind == null.
     */
    public static Long bepaalJarenTussenDatumsSoepel(final Integer begin, final Integer eind) {
        return bepaalPeriodeTussenDatumsSoepel(begin, eind, ChronoUnit.YEARS);
    }

    /**
     * Bepaalt het aantal dagen tussen twee datums. Deze methode gebruikt de SOEPEL manier van
     * datums vergelijken.
     * @param begin int representatie van begin moment 'yyyyMMdd'
     * @param eind  int representatie van eind moment 'yyyyMMdd'
     * @return verschil in dagen, null if begin or eind == null.
     */
    public static Long bepaalDagenTussenDatumsSoepel(final Integer begin, final Integer eind) {
        return bepaalPeriodeTussenDatumsSoepel(begin, eind, ChronoUnit.DAYS);
    }

    private static Long bepaalPeriodeTussenDatumsSoepel(final Integer begin, final Integer eind, final ChronoUnit periodeEenheid) {
        if (begin == null || eind == null) {
            return null;
        }

        final Integer beginDatumSoepel = bepaalEindDatumStrengOfBeginDatumSoepel(begin, false);
        final Integer eindDatumSoepel = bepaalBeginDatumStrengOfEindDatumSoepel(eind, false);
        final String dateFormat = "%08d";
        final LocalDate van = LocalDate.parse(String.format(dateFormat, beginDatumSoepel), FORMATTER);
        final LocalDate tot = LocalDate.parse(String.format(dateFormat, eindDatumSoepel), FORMATTER);
        return periodeEenheid.between(van, tot);
    }

    /**
     * Haalt de laatste dag van de maand op.
     * @param datum de datum waarvan de laatste wordt opgehaald.
     * @return de laatste dag van de maand.
     */
    public static int haalLaatsteDagVanDeMaandOp(final int datum) {
        final int jaar = datum % MOD_JAAR / MOD_MAAND;
        final int maand = datum % MOD_MAAND / MOD_DAG;
        final LocalDate date = LocalDate.of(jaar, maand, 1);
        return date.lengthOfMonth();
    }

    /**
     * Contoleer of de datum een geldige kalander datum is. Een (deels) onbekende datum is geen
     * geldige kalender datum.
     * @param datum String representatie van de datum 'yyyyMMdd'
     * @return true of false.
     */
    public static boolean isGeldigeKalenderdatum(final Integer datum) {
        boolean geldig = datum != null && datum != 0;
        if (geldig) {
            try {
                final String datumString = datum.toString();
                final int jaar = Integer.parseInt(datumString.substring(0, 4));
                final int maand = Integer.parseInt(datumString.substring(4, 6));
                final int dag = Integer.parseInt(datumString.substring(6, 8));
                final Calendar c = new GregorianCalendar();
                c.setLenient(false);
                c.set(jaar, maand - 1, dag);
                c.getTime();
                geldig = true;
            } catch (final IllegalArgumentException e) {
                return false;
            }
        }
        return geldig;
    }

    /**
     * Converteert een xsd-datum naar een {@link Integer}.
     * @param datum datum
     * @return datum als integer
     */
    public static Integer vanXsdDatumNaarInteger(final String datum) {
        final LocalDate localDate = LocalDate.parse(datum, DateTimeFormatter.ISO_DATE);
        return Integer.parseInt(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    /**
     * Converteert een xsd-datum naar een {@link ZonedDateTime}.
     * @param datumTijd datumTijd
     * @return date
     */
    public static ZonedDateTime vanXsdDatumTijdNaarZonedDateTime(final String datumTijd) {
        if (datumTijd == null || datumTijd.isEmpty()) {
            return null;
        }
        return ZonedDateTime.parse(datumTijd, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneId.of(DatumUtil.UTC));
    }

    /**
     * Converteert een xsd-datum naar een {@link java.util.Date}.
     * @param datumTijd datumTijd
     * @return date
     */
    public static Date vanXsdDatumTijdNaarDate(final String datumTijd) {
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse(datumTijd, DateTimeFormatter.ISO_DATE_TIME).withZoneSameInstant(ZoneId.of(DatumUtil.UTC));
        final LocalDateTime toLocalDateTime = zonedDateTime.toLocalDateTime();
        return Date.from(toLocalDateTime.atZone(ZoneId.of(DatumUtil.UTC)).toInstant());
    }

    /**
     * Controleert of (onbekende)datum groter of gelijk is aan (onbekende)peildatum, bij onbekende datums wordt uit gegaan van minimale datums
     * (20160000 -> 20160101)
     * @param datum te controleren datum.
     * @param peildatum datum waarmee vergeleken wordt.
     * @return true indien datum >= peildatum.
     */
    public static boolean vergelijkOnbekendeDatumsGroterOfGelijkAan(final Integer datum, final Integer peildatum) {
        return bepaalEindDatumStrengOfBeginDatumSoepel(datum, false) >= bepaalEindDatumStrengOfBeginDatumSoepel(peildatum, false);
    }


    private static Integer bepaalEindDatumStrengOfBeginDatumSoepel(final Integer datum, final boolean isEindDatum) {
        Integer resultaat = datum;
        if (datum == null || datum == 0) {
            resultaat = isEindDatum ? MAXIMALE_DATUM : MINIMALE_DATUM;
        } else if (datum % MOD_MAAND == 0) {
            resultaat += MINIMALE_MAAND;
        } else if (datum % MOD_DAG == 0) {
            resultaat += 1;
        }
        return resultaat;
    }

    private static Integer bepaalBeginDatumStrengOfEindDatumSoepel(final Integer datum, final boolean isBeginDatum) {
        Integer resultaat = datum;
        if (datum == null || datum == 0) {
            resultaat = isBeginDatum ? MINIMALE_DATUM : MAXIMALE_DATUM;
        } else if (datum % MOD_MAAND == 0) {
            resultaat += MAXIMALE_MAAND;
        } else if (datum % MOD_DAG == 0) {
            resultaat += haalLaatsteDagVanDeMaandOp(datum);
        }
        return resultaat;
    }

    private static Integer bepaalEindDatumSoepel(final Integer datum) {
        Integer resultaat = datum;
        if (datum == null || datum == 0) {
            resultaat = MAXIMALE_DATUM;
        } else if (datum % MOD_MAAND == 0) {
            resultaat += MAXIMALE_MAAND;
        } else if (datum % MOD_DAG == 0) {
            resultaat += haalLaatsteDagVanDeMaandOp(datum);
        }
        return resultaat;
    }
}

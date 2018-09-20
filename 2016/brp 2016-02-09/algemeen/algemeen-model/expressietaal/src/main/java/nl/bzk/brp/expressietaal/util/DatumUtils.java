/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.util;

import org.joda.time.DateTime;

/**
 * Utility class voor het rekenen met datums.
 */
public final class DatumUtils {

    /**
     * Maandnummer (in datum) van de maand januari.
     */
    public static final int JANUARI = 1;
    /**
     * Maandnummer (in datum) van de maand februari.
     */
    public static final int FEBRUARI = 2;
    /**
     * Maandnummer (in datum) van de maand maart.
     */
    public static final int MAART = 3;
    /**
     * Maandnummer (in datum) van de maand april.
     */
    public static final int APRIL = 4;
    /**
     * Maandnummer (in datum) van de maand mei.
     */
    public static final int MEI = 5;
    /**
     * Maandnummer (in datum) van de maand juni.
     */
    public static final int JUNI = 6;
    /**
     * Maandnummer (in datum) van de maand juli.
     */
    public static final int JULI = 7;
    /**
     * Maandnummer (in datum) van de maand augustus.
     */
    public static final int AUGUSTUS = 8;
    /**
     * Maandnummer (in datum) van de maand september.
     */
    public static final int SEPTEMBER = 9;
    /**
     * Maandnummer (in datum) van de maand oktober.
     */
    public static final int OKTOBER = 10;
    /**
     * Maandnummer (in datum) van de maand november.
     */
    public static final int NOVEMBER = 11;
    /**
     * Maandnummer (in datum) van de maand december.
     */
    public static final int DECEMBER = 12;

    /**
     * Een willekeurige dag die altijd bestaat midden in een maand.
     */
    public static final int DAG_MIDDEN_IN_MAAND = 14;

    /**
     * Het uur midden op de dag.
     */
    public static final int MIDDAGUUR = 12;

    /**
     * Constructor. Private omdat het een utility class is.
     */
    private DatumUtils() {
    }

    /**
     * Berekent het aantal dagen dat in een gegeven jaar zit, rekening houdend met schrikkeljaren.
     *
     * @param jaar Jaartal.
     * @return Aantal dagen in een bepaald jaar.
     */
    public static int dagenInJaar(final int jaar) {
        final DateTime dateTime = new DateTime(jaar, 1, DAG_MIDDEN_IN_MAAND, MIDDAGUUR, 0, 0, 0);
        return dateTime.dayOfYear().getMaximumValue();
    }

    /**
     * Berekent het aantal dagen dat in een maand zit, gegeven een jaartal en een maand en rekening houdend met
     * schrikkeljaren.
     *
     * @param jaar  Jaartal.
     * @param maand Maandnummer [1..12].
     * @return Aantal dagen in een bepaalde maand.
     */
    public static int dagenInMaand(final int jaar, final int maand) {
        final DateTime dateTime = new DateTime(jaar, maand, DAG_MIDDEN_IN_MAAND, MIDDAGUUR, 0, 0, 0);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    /**
     * Geeft de datum van de laatste dag in het gegeven jaar.
     *
     * @param jaar Jaartal.
     * @return Laatste dag van het jaar.
     */
    public static DateTime laatsteDagInJaar(final int jaar) {
        final int dagenInDecember = 31;
        return new DateTime(jaar, DECEMBER, dagenInDecember, 0, 0, 0);
    }

    /**
     * Geeft de datum van de laatste dag in de gegeven maand in het gegeven jaar.
     *
     * @param jaar  Jaartal.
     * @param maand Maandnummer [1..12].
     * @return Laatste dag van de maand.
     */
    public static DateTime laatsteDagInMaand(final int jaar, final int maand) {
        return new DateTime(jaar, maand, dagenInMaand(jaar, maand), 0, 0, 0);
    }
}

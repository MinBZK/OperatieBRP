/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging;

/**
 * Deze class is een handvat voor processen om proces informatie aan de context toe te voegen.
 * 
 */
public final class LoggingContext {

    private static final ThreadLocal<LoggingContext> CONTEXT = new ThreadLocal<LoggingContext>() {
        @Override
        protected LoggingContext initialValue() {
            return new LoggingContext();
        }
    };

    private boolean isKlaarVoorGebruik;
    private Long administratienummer;

    /**
     * Explicit private constructor.
     */
    private LoggingContext() {
    }

    @Override
    public String toString() {
        return "[a-nummer=" + administratienummer + "]";
    }

    /**
     * Registreert het actuele a-nummer voor het huidige proces.
     * 
     * @param administratienummer
     *            het actuele administratienummer
     */
    public static void registreerActueelAdministratienummer(final Long administratienummer) {
        CONTEXT.get().administratienummer = administratienummer;
        CONTEXT.get().isKlaarVoorGebruik = true;
    }

    /**
     * Reset alle waarden van deze context en zet de waarde van {@link #isKlaarVoorGebruik()} op false.
     */
    public static void reset() {
        CONTEXT.get().administratienummer = null;
        CONTEXT.get().isKlaarVoorGebruik = false;
    }

    /**
     * @return is klaar voor gebruik (als a-nummer is geregistreerd)
     */
    public static boolean isKlaarVoorGebruik() {
        return CONTEXT.get().isKlaarVoorGebruik;
    }

    /**
     * @return log prefix
     */
    public static String getLogPrefix() {
        return CONTEXT.get().toString();
    }
}

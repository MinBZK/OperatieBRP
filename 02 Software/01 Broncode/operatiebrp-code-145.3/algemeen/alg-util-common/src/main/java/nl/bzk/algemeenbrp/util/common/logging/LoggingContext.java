/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

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
    private String administratienummer;

    /**
     * Explicit private constructor.
     */
    private LoggingContext() {}

    @Override
    public String toString() {
        return "[a-nummer=" + administratienummer + "]";
    }

    /**
     * Registreert het actuele a-nummer voor het huidige proces.
     *
     * @param administratienummer het actuele administratienummer
     */
    public static void registreerActueelAdministratienummer(final String administratienummer) {
        final LoggingContext threadLoggingContext = CONTEXT.get();
        threadLoggingContext.setAdministratienummer(administratienummer);
        threadLoggingContext.setKlaarVoorGebruik(true);
    }

    /**
     * Reset alle waarden van deze context en zet de waarde van {@link #isKlaarVoorGebruik()} op
     * false.
     */
    public static void reset() {
        final LoggingContext threadLoggingContext = CONTEXT.get();
        threadLoggingContext.setAdministratienummer(null);
        threadLoggingContext.setKlaarVoorGebruik(false);
    }

    /**
     * Geef de klaar voor gebruik.
     *
     * @return is klaar voor gebruik (als a-nummer is geregistreerd)
     */
    public static boolean isKlaarVoorGebruik() {
        final LoggingContext threadLoggingContext = CONTEXT.get();
        return threadLoggingContext.getKlaarVoorGebruik();
    }

    /**
     * Geef de waarde van log prefix.
     *
     * @return log prefix
     */
    public static String getLogPrefix() {
        return CONTEXT.get().toString();
    }

    /**
     * Zet de waarde van administratienummer.
     *
     * @param administratienummer administratienummer
     */
    private void setAdministratienummer(final String administratienummer) {
        this.administratienummer = administratienummer;
    }

    /**
     * Geef de waarde van klaar voor gebruik.
     *
     * @return klaar voor gebruik
     */
    private boolean getKlaarVoorGebruik() {
        return isKlaarVoorGebruik;
    }

    /**
     * Zet de waarde van klaar voor gebruik.
     *
     * @param nieuwIsKlaarVoorGebruik klaar voor gebruik
     */
    private void setKlaarVoorGebruik(final boolean nieuwIsKlaarVoorGebruik) {
        isKlaarVoorGebruik = nieuwIsKlaarVoorGebruik;
    }
}

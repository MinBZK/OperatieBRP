/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.logging;

import java.io.Closeable;
import java.util.Map;
import java.util.Set;


/**
 * Logging context.
 */
public final class MDC {

    private static final String MELDING_CODE = "meldingCode";

    private MDC() {
        // Niet instantieerbaar
    }

    /**
     * Plaats de waarde voor het veld in de MDC.
     * 
     * @param veld Het veld.
     * @param waarde De waarde.
     */
    public static void put(final MDCVeld veld, final String waarde) {
        org.slf4j.MDC.put(veld.getVeld(), waarde);
    }

    /**
     * Verwijder de waarde voor het veld in de MDC.
     * 
     * @param veld Het veld.
     */
    public static void remove(final MDCVeld veld) {
        org.slf4j.MDC.remove(veld.getVeld());
    }

    /**
     * Plaats de melding in de MDC.
     * 
     * @param melding De melding.
     * @return De MDCCloser.
     */
    static MDCCloser putMelding(final FunctioneleMelding melding) {
        org.slf4j.MDC.put(MELDING_CODE, melding.getCode());
        return new MDCSingleCloser(MELDING_CODE);
    }

    /**
     * Plaats data in de MDC.
     * 
     * @param data De te plaatsen data.
     * @return De MDCCloser.
     */
    static MDCCloser putData(final Map<String, String> data) {
        for (final Map.Entry<String, String> entry : data.entrySet()) {
            org.slf4j.MDC.put(entry.getKey(), entry.getValue());
        }
        return new MDCSetCloser(data.keySet());
    }

    /**
     * Interface voor de closer van MDC.
     */
    interface MDCCloser extends Closeable {

        @Override
        void close();
    }

    /**
     * Implementatie van de MDCCloser interface voor een verzameling.
     */
    static class MDCSetCloser implements MDCCloser {

        private final Set<String> keys;

        /**
         * Constructor.
         * 
         * @param keys De sleutels.
         */
        MDCSetCloser(final Set<String> keys) {
            this.keys = keys;
        }

        @Override
        public void close() {
            for (final String key : this.keys) {
                org.slf4j.MDC.remove(key);
            }
        }
    }

    /**
     * Implementatie van de MDCCloser interface voor een sleutel.
     */
    static class MDCSingleCloser implements MDCCloser {

        private final String key;

        /**
         * Constructor.
         * 
         * @param key De sleutel.
         */
        MDCSingleCloser(final String key) {
            this.key = key;
        }

        @Override
        public void close() {
            org.slf4j.MDC.remove(this.key);
        }
    }
}

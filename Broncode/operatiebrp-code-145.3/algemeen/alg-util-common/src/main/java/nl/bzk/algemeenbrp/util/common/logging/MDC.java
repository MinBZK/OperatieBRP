/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

import java.io.Closeable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Logging context.
 */
public final class MDC {

    /**
     * verwerkings_code die via de header in jms doorgegeven wordt aan services.
     */
    public static final String JMS_VERWERKING_CODE = "verwerking_code";
    /**
     * verwerkingscode in de logging.
     */
    static final String VERWERKING_CODE = "verwerking";
    private static final String MELDING_CODE = "meldingCode";

    private MDC() {
        // Niet instantieerbaar
    }

    /**
     * Plaats de waarde voor het veld in de MDC.
     *
     * @param veld Het veld.
     * @param waarde De waarde.
     *
     * @return De MDCCloser.
     */
    public static MDCCloser put(final MDCVeld veld, final Object waarde) {
        org.slf4j.MDC.put(veld.getVeld(), waarde == null ? null : String.valueOf(waarde));
        return new MDCSingleCloser(veld.getVeld());
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
     * Geef verwerkingscode zodat deze in het jms bericht kan worden geplaatst.
     *
     * @return waarde van de verwerkingscode
     */
    public static String getVerwerkingsCode() {
        return org.slf4j.MDC.get(VERWERKING_CODE);
    }

    /**
     * Plaats de melding in de MDC.
     *
     * @param melding De melding.
     * @return De MDCCloser.
     */
    static MDCCloser putMelding(final FunctioneleMelding melding) {
        org.slf4j.MDC.put(MELDING_CODE, melding == null ? "" : melding.getCode());
        return new MDCSingleCloser(MELDING_CODE);
    }

    /**
     * Start een verwerking; registreer hiervoor een random (uuid) als verwerkings tag op de MDC.
     *
     * @return MDCCloser
     */
    public static MDCCloser startVerwerking() {
        return startVerwerking(null);
    }

    /**
     * Start een verwerking; registreer hiervoor een bestaande verwerkings tag op de MDC.
     *
     * @param verwerkingsCode Bestaande verwerkingscode, mag null zijn
     * @return MDCCloser
     */
    public static MDCCloser startVerwerking(final String verwerkingsCode) {
        if (verwerkingsCode != null) {
            org.slf4j.MDC.put(VERWERKING_CODE, verwerkingsCode);
        } else {
            org.slf4j.MDC.put(VERWERKING_CODE, UUID.randomUUID().toString());
        }
        return new MDCSingleCloser(VERWERKING_CODE);
    }

    /**
     * Plaats data in de MDC.
     *
     * @param data De te plaatsen data.
     * @return De MDCCloser.
     */
    public static MDCCloser putData(final Map<String, String> data) {
        for (final Map.Entry<String, String> entry : data.entrySet()) {
            org.slf4j.MDC.put(entry.getKey(), entry.getValue());
        }
        return new MDCSetCloser(data.keySet());
    }

    /**
     * Voer {@link Runnable} implementatie uit met gebruik van {@link MDCCloser}.
     * @param mdcMap mdc waarden map
     * @param r {@link Runnable} implementatie
     */
    public static void voerUit(Map<String, String> mdcMap, Runnable r) {
        try (MDC.MDCCloser closer = putData(mdcMap)) {
            r.run();
        }
    }

    /**
     * Interface voor de closer van MDC.
     */
    public interface MDCCloser extends Closeable {

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
            for (final String key : keys) {
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
            org.slf4j.MDC.remove(key);
        }
    }
}

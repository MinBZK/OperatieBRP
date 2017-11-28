/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * MDC - Logging context.
 */
public final class MDCProcessor {

    private static final String JMS_VERWERKING_CODE = "verwerking_code";
    private static final String VERWERKING_CODE = "verwerking";
    private static final String MELDING_CODE = "meldingCode";

    private final List<MDCData> data = new ArrayList<>();

    private MDCProcessor() {
        // Instantiate via MDCProcessor.start methods
    }

    /**
     * Start verwerking (genereer een nieuwe verwerkingscode).
     * @return MDCProcessor.
     */
    public static MDCProcessor startVerwerking() {
        return startVerwerking(UUID.randomUUID().toString());
    }

    /**
     * Start verwerking.
     * @param verwerkingsCode verwerkingscode
     * @return MDCProcessor
     */
    public static MDCProcessor startVerwerking(String verwerkingsCode) {
        if (verwerkingsCode == null || "".equals(verwerkingsCode)) {
            return startVerwerking();
        } else {
            MDCProcessor mdcProcessor = new MDCProcessor();
            mdcProcessor.metData(VERWERKING_CODE, verwerkingsCode);
            return mdcProcessor;
        }
    }

    /**
     * Start verwerking.
     * @param message JMS message waar de verwerkingscode uit gehaald dient te worden.
     * @return MDCProcessor
     * @throws JMSException bij JMS fouten
     */
    public static MDCProcessor startVerwerking(Message message) throws JMSException {
        return startVerwerking(message.getStringProperty(JMS_VERWERKING_CODE));
    }

    /**
     * Start zonder verwerking om een extra veld toe te voegen.
     * @param veld veld
     * @param waarde waarde
     * @return MDCProcessor
     */
    public static MDCProcessor extra(MDCVeld veld, final Object waarde) {
        MDCProcessor mdcProcessor = new MDCProcessor();
        mdcProcessor.metVeld(veld, waarde);
        return mdcProcessor;
    }

    /**
     * Start zonder verwerking om extra data toe te voegen.
     * @param naam naam
     * @param waarde waarde
     * @return MDCProcessor
     */
    public static MDCProcessor extra(String naam, final Object waarde) {
        MDCProcessor mdcProcessor = new MDCProcessor();
        mdcProcessor.metData(naam, waarde);
        return mdcProcessor;
    }

    /**
     * Start zonder verwerking om een functionele melding toe te voegen.
     * @param melding melding
     * @return MDCProcessor
     */
    public static MDCProcessor extra(FunctioneleMelding melding) {
        MDCProcessor mdcProcessor = new MDCProcessor();
        mdcProcessor.metMelding(melding);
        return mdcProcessor;
    }

    /**
     * Registreer de verwerkingscode op een JMS message.
     * @param message JMS message
     * @throws JMSException bij JMS fouten
     */
    public static void registreerVerwerkingsCode(Message message) throws JMSException {
        if(getVerwerkingsCode() != null) {
            message.setStringProperty(JMS_VERWERKING_CODE, getVerwerkingsCode());
        }
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
     * Voeg een veld toe.
     * @param veld veld
     * @param waarde waarde
     * @return MDCProcessor
     */
    public MDCProcessor metVeld(final MDCVeld veld, final Object waarde) {
        return metData(veld.getVeld(), waarde);
    }

    /**
     * Voeg data toe.
     * @param naam naam
     * @param waarde waarde
     * @return MDCProcessor
     */
    public MDCProcessor metData(final String naam, final Object waarde) {
        data.add(new MDCData(naam, waarde));
        return this;
    }

    /**
     * Voeg een functionel melding toe.
     * @param melding melding
     * @return MDCProcessor
     */
    public MDCProcessor metMelding(FunctioneleMelding melding) {
        return metData(MELDING_CODE, melding == null ? "" : melding.getCode());
    }

    /**
     * Voer code uit waarbij de gegevens in de MDC worden gezet (en verwijderd).
     * @param code uit te voeren code
     */
    public <E extends Exception> void run(final MDCRunnable<E> code) throws E {
        for (MDCData entry : data) {
            org.slf4j.MDC.put(entry.getNaam(), entry.getWaarde());
        }

        try {
            code.run();
        } finally {
            for (MDCData entry : data) {
                org.slf4j.MDC.remove(entry.getNaam());
            }
        }
    }

    /**
     * MDC Runnable code.
     *
     * @param <E> exception type
     */
    @FunctionalInterface
    public interface MDCRunnable<E extends Exception> {

        /**
         * Voer code uit.
         * @throws E bij fouten
         */
        void run() throws E;
    }

    /**
     * MDC Data.
     */
    private static final class MDCData {
        private final String naam;
        private final Object waarde;

        MDCData(final String naam, final Object waarde) {
            this.naam = naam;
            this.waarde = waarde;
        }

        String getNaam() {
            return naam;
        }

        String getWaarde() {
            return waarde == null ? null : String.valueOf(waarde);
        }
    }
}

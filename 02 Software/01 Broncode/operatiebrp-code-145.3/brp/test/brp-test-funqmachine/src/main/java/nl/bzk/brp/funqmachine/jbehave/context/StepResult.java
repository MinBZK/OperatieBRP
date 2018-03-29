/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

import org.jbehave.core.steps.Step;

/**
 * Resultaat van een {@link Step}.
 */
public final class StepResult {
    private final Soort soort;
    private String request;
    private String response;
    private String expected;
    private String anummer;
    private Number id;

    /**
     * De soorten van het resultaat dat kan worden vastgelegd.
     */
    public enum Soort {
        /**
         * Data.
         */
        DATA,
        /**
         * Soap.
         */
        SOAP,
        /**
         * Values.
         */
        VALUES,
        /**
         * Levering.
         */
        LEVERING,
        /**
         * BRP PL.
         */
        BRP_PL,
        /**
         * Blob.
         */
        BLOB,
        /**
         * Json.
         */
        JSON,
        /**
         * Error.
         */
        ERROR,
        /**
         * Notificatie.
         */
        NOTIFICATIE
    }

    /**
     * Constructor.
     *
     * @param soort het soort resultaat
     */
    public StepResult(final Soort soort) {
        this.soort = soort;
    }

    /**
     * Geeft het soort resultaat terug.
     * 
     * @return het {@link Soort} resultaat.
     */
    public Soort getSoort() {
        return soort;
    }

    /**
     * Geeft het request terug.
     * 
     * @return request wat opgeslagen is.
     */
    public String getRequest() {
        return request;
    }

    /**
     * Zet het request voor deze stap.
     * 
     * @param request het request
     */
    public void setRequest(final String request) {
        this.request = request;
    }

    /**
     * Geeft het response voor deze stap.
     * 
     * @return het response
     */
    public String getResponse() {
        return response;
    }

    /**
     * Zet het response voor deze stap.
     * 
     * @param response het response
     */
    public void setResponse(final String response) {
        this.response = response;
    }

    /**
     * Geeft het expected resultaat terug.
     * 
     * @return het expected resultaat
     */
    public String getExpected() {
        return expected;
    }

    /**
     * Zet het expected resultaat.
     * 
     * @param expected het expected resultaat
     */
    public void setExpected(final String expected) {
        this.expected = expected;
    }

    /**
     * Geeft het a-nummer terug.
     * 
     * @return het a-nummer
     */
    public String getAnummer() {
        return anummer;
    }

    /**
     * Zet het a-nummer.
     * 
     * @param anummer het a-nummer
     */
    public void setAnummer(final String anummer) {
        this.anummer = anummer;
    }

    /**
     * Geeft het ID terug.
     * 
     * @return het ID.
     */
    public Number getId() {
        return id;
    }

    /**
     * Zet het ID.
     * 
     * @param id het ID
     */
    public void setId(final Number id) {
        this.id = id;
    }
}

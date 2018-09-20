/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Klasse om een server fout mee terug te geven.
 */
public class ExceptionResponse {

    @JsonProperty
    private final String type;
    @JsonProperty
    private final String message;

    /**
     * Constructor.
     *
     * @param e exception
     */
    public ExceptionResponse(final Exception e) {
        type = e.getClass().getName();
        message = e.getMessage();
    }

    /**
     * Constructor.
     * @param type type
     * @param message message
     */
    public ExceptionResponse(final String type, final String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Geef type.
     *
     * @return type
     */
    public final String getType() {
        return type;
    }

    /**
     * Geef melding.
     *
     * @return melding
     */
    public final String getMessage() {
        return message;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.messages;

/**
 * Bericht voor op het scherm.
 *
 */
public class Message {

    private final MessageSeverity severity;

    private final String          value;

    /**
     * Constructor.
     *
     * @param severity de severity
     * @param value bericht
     */
    public Message(final MessageSeverity severity, final String value) {
        this.severity = severity;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public MessageSeverity getSeverity() {
        return severity;
    }
}

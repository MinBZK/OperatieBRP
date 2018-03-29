/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.neoload;

/**
 * Bericht.
 */
public final class BerichtFacade {

    private String verzendendeMailbox;
    private String ontvangendeMailbox;
    private String messageId;
    private String correlationId;
    private String inhoud;

    public String getVerzendendeMailbox() {
        return verzendendeMailbox;
    }

    public void setVerzendendeMailbox(final String verzendendeMailbox) {
        this.verzendendeMailbox = verzendendeMailbox;
    }

    public String getOntvangendeMailbox() {
        return ontvangendeMailbox;
    }

    public void setOntvangendeMailbox(final String ontvangendeMailbox) {
        this.ontvangendeMailbox = ontvangendeMailbox;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    public String getInhoud() {
        return inhoud;
    }

    public void setInhoud(final String inhoud) {
        this.inhoud = inhoud;
    }
}

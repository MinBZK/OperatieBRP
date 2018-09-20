/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.sql.Timestamp;

public final class Bericht {

    private Long id;
    private Timestamp tijdstip;
    private String kanaal;
    private Direction richting;
    private String messageId;
    private String correlationId;
    private String bericht;
    private String naam;
    private Long processInstanceId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Timestamp getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getKanaal() {
        return kanaal;
    }

    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    public Direction getRichting() {
        return richting;
    }

    public void setRichting(final Direction richting) {
        this.richting = richting;
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

    public String getBericht() {
        return bericht;
    }

    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(final Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public static enum Direction {
        /** Inkomend. */
        INKOMEND("I"),
        /** Uitgaand. */
        UITGAAND("U");

        private final String code;

        /**
         * Constructor.
         * 
         * @param code
         *            code
         */
        Direction(final String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        /**
         * Zoek de richting obv de code.
         * 
         * @param code
         *            code
         * @return richting, of null als niet gevonden
         */
        public static Direction valueOfCode(final String code) {
            for (final Direction direction : values()) {
                if (code.equals(direction.getCode())) {
                    return direction;
                }
            }

            return null;
        }
    }

}

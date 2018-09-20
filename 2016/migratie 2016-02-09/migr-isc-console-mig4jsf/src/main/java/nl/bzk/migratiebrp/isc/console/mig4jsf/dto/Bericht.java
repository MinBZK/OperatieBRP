/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.dto;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * DTO Bericht.
 */
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
    private String bronGemeente;
    private String doelGemeente;
    private String msSequenceNumber;

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van tijdstip.
     *
     * @return tijdstip
     */
    public Timestamp getTijdstip() {
        return Kopieer.timestamp(tijdstip);
    }

    /**
     * Zet de waarde van tijdstip.
     *
     * @param tijdstip
     *            tijdstip
     */
    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = Kopieer.timestamp(tijdstip);
    }

    /**
     * Geef de waarde van kanaal.
     *
     * @return kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Zet de waarde van kanaal.
     *
     * @param kanaal
     *            kanaal
     */
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Geef de waarde van richting.
     *
     * @return richting
     */
    public Direction getRichting() {
        return richting;
    }

    /**
     * Zet de waarde van richting.
     *
     * @param richting
     *            richting
     */
    public void setRichting(final Direction richting) {
        this.richting = richting;
    }

    /**
     * Geef de waarde van message id.
     *
     * @return message id
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Zet de waarde van message id.
     *
     * @param messageId
     *            message id
     */
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * Geef de waarde van correlation id.
     *
     * @return correlation id
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Zet de waarde van correlation id.
     *
     * @param correlationId
     *            correlation id
     */
    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Geef de waarde van bericht.
     *
     * @return bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Zet de waarde van bericht.
     *
     * @param bericht
     *            bericht
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Geef de waarde van process instance id.
     *
     * @return process instance id
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Zet de waarde van process instance id.
     *
     * @param processInstanceId
     *            process instance id
     */
    public void setProcessInstanceId(final Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    /**
     * Geef de waarde van bron gemeente.
     *
     * @return bron gemeente
     */
    public String getBronGemeente() {
        return bronGemeente;
    }

    /**
     * Zet de waarde van bron gemeente.
     *
     * @param bronGemeente
     *            bron gemeente
     */
    public void setBronGemeente(final String bronGemeente) {
        this.bronGemeente = bronGemeente;
    }

    /**
     * Geef de waarde van doel gemeente.
     *
     * @return doel gemeente
     */
    public String getDoelGemeente() {
        return doelGemeente;
    }

    /**
     * Zet de waarde van doel gemeente.
     *
     * @param doelGemeente
     *            doel gemeente
     */
    public void setDoelGemeente(final String doelGemeente) {
        this.doelGemeente = doelGemeente;
    }

    /**
     * Zet de waarde van ms sequence number.
     *
     * @param msSequenceNumber
     *            ms sequence number
     */
    public void setMsSequenceNumber(final String msSequenceNumber) {
        this.msSequenceNumber = msSequenceNumber;
    }

    /**
     * Geef de waarde van ms sequence number.
     *
     * @return ms sequence number
     */
    public String getMsSequenceNumber() {
        return msSequenceNumber;
    }

    /**
     * Richting.
     */
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

        /**
         * Geef de waarde van code.
         *
         * @return code
         */
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
            if (code != null) {
                for (final Direction direction : values()) {
                    if (code.equals(direction.getCode())) {
                        return direction;
                    }
                }
            }

            return null;
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Metadata voor berichten.
 */
public final class BerichtMetaData {

    private Long id;
    private Timestamp tijdstip;
    private String kanaal;
    private String richting;
    private String messageId;
    private String correlationId;
    private String berichtType;

    /**
     * Geef de waarde van id.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     * @param id id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van tijdstip.
     * @return tijdstip
     */
    public Timestamp getTijdstip() {
        return Kopieer.timestamp(tijdstip);
    }

    /**
     * Zet de waarde van tijdstip.
     * @param tijdstip tijdstip
     */
    public void setTijdstip(final Timestamp tijdstip) {
        this.tijdstip = Kopieer.timestamp(tijdstip);
    }

    /**
     * Geef de waarde van kanaal.
     * @return kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Zet de waarde van kanaal.
     * @param kanaal kanaal
     */
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Geef de waarde van richting.
     * @return richting
     */
    public String getRichting() {
        return richting;
    }

    /**
     * Zet de waarde van richting.
     * @param richting richting
     */
    public void setRichting(final String richting) {
        this.richting = richting;
    }

    /**
     * Geef de waarde van message id.
     * @return message id
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Zet de waarde van message id.
     * @param messageId message id
     */
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * Geef de waarde van correlation id.
     * @return correlation id
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Zet de waarde van correlation id.
     * @param correlationId correlation id
     */
    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Geef de waarde van bericht type.
     * @return bericht type
     */
    public String getBerichtType() {
        return berichtType;
    }

    /**
     * Zet de waarde van bericht type.
     * @param berichtType bericht type
     */
    public void setBerichtType(final String berichtType) {
        this.berichtType = berichtType;
    }

}

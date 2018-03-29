/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.domein;

import java.sql.Timestamp;

import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Representatie klasse van de tabel ProefSynchronisatieBericht.
 */
public final class ProefSynchronisatieBericht {

    private String afzender;
    private Long berichtId;
    private Timestamp berichtDatum;
    private String bericht;
    private Long id;
    private Long msSequenceNumber;
    private Boolean verwerkt;
    private String mailbox;

    /**
     * Geeft de afzender terug.
     * @return De afzender.
     */
    public String getAfzender() {
        return afzender;
    }

    /**
     * Zet de afzender.
     * @param afzender De te zetten afzender.
     */
    public void setAfzender(final String afzender) {
        this.afzender = afzender;
    }

    /**
     * Geeft de datum van het bericht terug.
     * @return De bericht datum.
     */
    public Timestamp getBerichtDatum() {
        return Kopieer.timestamp(berichtDatum);
    }

    /**
     * Zet de datum van het bericht.
     * @param berichtDatum De te zetten datum van het bericht.
     */
    public void setBerichtDatum(final Timestamp berichtDatum) {
        this.berichtDatum = Kopieer.timestamp(berichtDatum);
    }

    /**
     * Geeft het bericht terug.
     * @return Het bericht.
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Zet het bericht.
     * @param bericht Het te zetten bericht.
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    /**
     * Geeft het id van het bericht terug.
     * @return Het id van het bericht.
     */
    public Long getBerichtId() {
        return berichtId;
    }

    /**
     * Zet het id van het bericht.
     * @param berichtId Het te zetten id van het bericht.
     */
    public void setBerichtId(final Long berichtId) {
        this.berichtId = berichtId;
    }

    /**
     * Geeft het id van het proefsyncbericht.
     * @return Het id van het proefsyncbericht.
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het id van het proefsyncbericht.
     * @param id Het te zetten id.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft het MSSequenceNumber van het bericht.
     * @return Het MSSequenceNumber van het bericht.
     */
    public Long getMsSequenceNumber() {
        return msSequenceNumber;
    }

    /**
     * Zet het MSSequenceNumber.
     * @param msSequenceNumber Het te zetten MSSequenceNumber.
     */
    public void setMsSequenceNumber(final Long msSequenceNumber) {
        this.msSequenceNumber = msSequenceNumber;
    }

    /**
     * Geeft aan of het bericht al is verwerkt.
     * @return True indien het bericht al is verwerkt, false in andere gevallen.
     */
    public Boolean getVerwerkt() {
        return verwerkt;
    }

    /**
     * Zet de indicatie of het bericht is verwerkt.
     * @param verwerkt De te zetten waarde voor verwerkt.
     */
    public void setVerwerkt(final Boolean verwerkt) {
        this.verwerkt = verwerkt;
    }

    /**
     * Geeft de (centrale) mailbox van het bericht.
     * @return De (centrale) mailbox
     */
    public String getMailbox() {
        return mailbox;
    }

    /**
     * Zet de (centrale) mailbox van het bericht.
     * @param mailbox De te zetten (centrale) mailbox
     */
    public void setMailbox(final String mailbox) {
        this.mailbox = mailbox;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Bericht.
 */
@Entity
@Table(name = "bericht", schema = "voisc")
public class Bericht implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "berichtIdSequence", sequenceName = "voisc.bericht_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "berichtIdSequence")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "originator")
    private String originator;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name = "message_id")
    private String messageId;

    @Column(name = "correlation_id")
    private String correlationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tijdstip_ontvangst", updatable = false, nullable = false)
    private Date tijdstipOntvangst;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tijdstip_mailbox")
    private Date tijdstipMailbox;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tijdstip_in_verwerking")
    private Date tijdstipInVerwerking;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tijdstip_verzonden")
    private Date tijdstipVerzonden;

    @Column(name = "dispatch_sequence_number")
    private Integer dispatchSequenceNumber;

    @Column(name = "bericht_data")
    private String berichtInhoud;

    @Column(name = "non_delivery_reason")
    private String nonDeliveryReason;

    @Column(name = "notification_type")
    private String notificationType;

    @Version
    @Column(name = "version")
    private Long version;

    /**
     * Geef id.
     *
     * @return id
     */
    public final Long getId() {
        return id;
    }

    /**
     * Zet id.
     *
     * @param id
     *            id
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef originator.
     *
     * @return originator
     */
    public final String getOriginator() {
        return originator;
    }

    /**
     * Zet originator.
     *
     * @param originator
     *            originator
     */
    public final void setOriginator(final String originator) {
        this.originator = originator;
    }

    /**
     * Geef de waarde van recipient.
     *
     * @return recipient
     */
    public final String getRecipient() {
        return recipient;
    }

    /**
     * Zet recipient.
     *
     * @param recipient
     *            recipient
     */
    public final void setRecipient(final String recipient) {
        this.recipient = recipient;
    }

    /**
     * Geef status.
     *
     * @return status
     */
    public final StatusEnum getStatus() {
        return status;
    }

    /**
     * Zet status.
     *
     * @param status
     *            status
     */
    public final void setStatus(final StatusEnum status) {
        this.status = status;
    }

    /**
     * Geef message-id.
     *
     * @return message id
     */
    public final String getMessageId() {
        return messageId;
    }

    /**
     * Zet message-id.
     *
     * @param messageId
     *            message id
     */
    public final void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * Geef correlatie id.
     *
     * @return correlatie id
     */
    public final String getCorrelationId() {
        return correlationId;
    }

    /**
     * Zet correlatie id.
     *
     * @param correlationId
     *            correlatie id
     */
    public final void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Geef tijdstip ontvangst.
     *
     * @return tijdstip ontvangst
     */

    public final Date getTijdstipOntvangst() {
        return Kopieer.utilDate(tijdstipOntvangst);
    }

    /**
     * Zet tijdstip ontvangst.
     *
     * @param tijdstipOntvangst
     *            tijdstip ontvangst
     */
    public final void setTijdstipOntvangst(final Date tijdstipOntvangst) {
        this.tijdstipOntvangst = Kopieer.utilDate(tijdstipOntvangst);
    }

    /**
     * Geef tijdstip mailbox.
     *
     * @return tijdstip mailbox
     */
    public final Date getTijdstipMailbox() {
        return Kopieer.utilDate(tijdstipMailbox);
    }

    /**
     * Zet tijdstip mailbox.
     *
     * @param tijdstipMailbox
     *            tijdstip mailbox
     */
    public final void setTijdstipMailbox(final Date tijdstipMailbox) {
        this.tijdstipMailbox = Kopieer.utilDate(tijdstipMailbox);
    }

    /**
     * Geef tijdstip in verwerking.
     *
     * @return tijdstip in verwerking
     */
    public final Date getTijdstipInVerwerking() {
        return Kopieer.utilDate(tijdstipInVerwerking);
    }

    /**
     * Zet tijdstip in verwerking.
     *
     * @param tijdstipInVerwerking
     *            tijdstip in verwerking
     */
    public final void setTijdstipInVerwerking(final Date tijdstipInVerwerking) {
        this.tijdstipInVerwerking = Kopieer.utilDate(tijdstipInVerwerking);
    }

    /**
     * Geef tijdstip verzonden.
     *
     * @return tijdstip verzonden
     */
    public final Date getTijdstipVerzonden() {
        return Kopieer.utilDate(tijdstipVerzonden);
    }

    /**
     * Zet tijdstip verzonden.
     *
     * @param tijdstipVerzonden
     *            tijdstip verzonden
     */
    public final void setTijdstipVerzonden(final Date tijdstipVerzonden) {
        this.tijdstipVerzonden = Kopieer.utilDate(tijdstipVerzonden);
    }

    /**
     * Geef dispatch sequence number.
     * 
     * @return dispatch sequence number
     */
    public final Integer getDispatchSequenceNumber() {
        return dispatchSequenceNumber;
    }

    /**
     * Zet dispatch sequence number.
     * 
     * @param dispatchSequenceNumber
     *            dispatch sequence number
     */
    public final void setDispatchSequenceNumber(final Integer dispatchSequenceNumber) {
        this.dispatchSequenceNumber = dispatchSequenceNumber;
    }

    /**
     * Geef bericht inhoud.
     * 
     * @return bericht inhoud
     */
    public final String getBerichtInhoud() {
        return berichtInhoud;
    }

    /**
     * Zet bericht inhoud.
     * 
     * @param berichtInhoud
     *            bericht inhoud
     */
    public final void setBerichtInhoud(final String berichtInhoud) {
        this.berichtInhoud = berichtInhoud;
    }

    /**
     * Geef non delivery reason.
     * 
     * @return non delivery reason
     */
    public final String getNonDeliveryReason() {
        return nonDeliveryReason;
    }

    /**
     * Zet non delivery reason.
     * 
     * @param nonDeliveryReason
     *            non delivery reason
     */
    public final void setNonDeliveryReason(final String nonDeliveryReason) {
        this.nonDeliveryReason = nonDeliveryReason;
    }

    /**
     * Geef notification type.
     * 
     * @return notification type
     */
    public final String getNotificationType() {
        return notificationType;
    }

    /**
     * Zet notification type.
     * 
     * @param notificationType
     *            notification type
     */
    public final void setNotificationType(final String notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * Geef versie.
     * 
     * @return versie
     */
    public final Long getVersion() {
        return version;
    }

    @Override
    public final String toString() {
        return "Bericht [id="
               + id
               + ", originator="
               + originator
               + ", recipient="
               + recipient
               + ", status="
               + status
               + ", messageId="
               + messageId
               + ", correlationId="
               + correlationId
               + ", tijdstipOntvangst="
               + tijdstipOntvangst
               + ", tijdstipMailbox="
               + tijdstipMailbox
               + ", tijdstipInVerwerking="
               + tijdstipInVerwerking
               + ", tijdstipVerzonden="
               + tijdstipVerzonden
               + ", dispatchSequenceNumber="
               + dispatchSequenceNumber
               + ", berichtInhoud="
               + berichtInhoud
               + ", nonDeliveryReason="
               + nonDeliveryReason
               + ", notificationType="
               + notificationType
               + ", version="
               + version
               + "]";
    }

}

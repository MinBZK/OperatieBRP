/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.entities;

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
import javax.persistence.Transient;

/**
 * Bericht.
 */
@Entity
@Table(name = "bericht", schema = "voisc")
public class Bericht implements Serializable {

    /** Aanduiding in. */
    public static final String AANDUIDING_IN_UIT_IN = "I";
    /** Aanduiding uit. */
    public static final String AANDUIDING_IN_UIT_UIT = "U";
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "berichtIdSequence", sequenceName = "voisc.bericht_id_sequence", allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "berichtIdSequence")
    @Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "aanduiding_in_uit", insertable = true, updatable = false, nullable = false)
    private String aanduidingInUit;

    @Column(name = "originator", insertable = true, updatable = false, nullable = false)
    private String originator;

    @Column(name = "recipient", insertable = true, updatable = false, nullable = false)
    private String recipient;

    @Column(name = "eref", insertable = true, updatable = true, nullable = true)
    private String eref;

    @Column(name = "bref", insertable = true, updatable = true, nullable = true)
    private String bref;

    @Column(name = "eref2", insertable = true, updatable = false, nullable = true)
    private String eref2;

    @Column(name = "esbCorrelationId", insertable = true, updatable = true, nullable = true)
    private String esbCorrelationId;

    @Column(name = "esbMessageId", insertable = true, updatable = true, nullable = true)
    private String esbMessageId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tijdstip_verzending_ontvangst", insertable = true, updatable = true, nullable = true)
    private Date tijdstipVerzendingOntvangst;

    @Column(name = "dispatch_sequence_number", insertable = true, updatable = true, nullable = true)
    private Integer dispatchSequenceNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "report_delivery_time", insertable = true, updatable = true, nullable = true)
    private Date reportDeliveryTime;

    @Column(name = "non_delivery_reason", insertable = true, updatable = true, nullable = true)
    private String nonDeliveryReason;

    @Column(name = "non_receipt_reason", insertable = true, updatable = true, nullable = true)
    private String nonReceiptReason;

    @Column(name = "bericht_data", insertable = true, updatable = false, nullable = false)
    private String lo3Berichtinhoud;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creatie_dt", insertable = true, updatable = false, nullable = true)
    private Date registratieDt;

    @Column(name = "status", insertable = true, updatable = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Transient
    private boolean isReport;

    public final Long getId() {
        return id;
    }

    public final void setId(final long id) {
        this.id = id;
    }

    public final String getAanduidingInUit() {
        return aanduidingInUit;
    }

    public final void setAanduidingInUit(final String aanduidingInUit) {
        this.aanduidingInUit = aanduidingInUit;
    }

    public final String getOriginator() {
        return originator;
    }

    public final void setOriginator(final String originator) {
        this.originator = originator;
    }

    public final String getRecipient() {
        return recipient;
    }

    public final void setRecipient(final String recipient) {
        this.recipient = recipient;
    }

    public final String getEref() {
        return eref;
    }

    public final void setEref(final String eref) {
        this.eref = eref;
    }

    public final String getBref() {
        return bref;
    }

    public final void setBref(final String bref) {
        this.bref = bref;
    }

    public final String getEref2() {
        return eref2;
    }

    public final void setEref2(final String eref2) {
        this.eref2 = eref2;
    }

    public final Date getTijdstipVerzendingOntvangst() {
        return tijdstipVerzendingOntvangst;
    }

    public final void setTijdstipVerzendingOntvangst(final Date tijdstipVerzendingOntvangst) {
        this.tijdstipVerzendingOntvangst = tijdstipVerzendingOntvangst;
    }

    public final Integer getDispatchSequenceNumber() {
        return dispatchSequenceNumber;
    }

    public final void setDispatchSequenceNumber(final Integer dispatchSequenceNumber) {
        this.dispatchSequenceNumber = dispatchSequenceNumber;
    }

    public final Date getReportDeliveryTime() {
        return reportDeliveryTime;
    }

    public final void setReportDeliveryTime(final Date reportDeliveryTime) {
        this.reportDeliveryTime = reportDeliveryTime;
    }

    public final String getNonDeliveryReason() {
        return nonDeliveryReason;
    }

    public final void setNonDeliveryReason(final String nonDeliveryReason) {
        this.nonDeliveryReason = nonDeliveryReason;
    }

    public final String getNonReceiptReason() {
        return nonReceiptReason;
    }

    public final void setNonReceiptReason(final String nonReceiptReason) {
        this.nonReceiptReason = nonReceiptReason;
    }

    public final String getBerichtInhoud() {
        return lo3Berichtinhoud;
    }

    public final void setBerichtInhoud(final String lo3Berichtinhoud) {
        this.lo3Berichtinhoud = lo3Berichtinhoud;
    }

    public final Date getRegistratieDt() {
        return registratieDt;
    }

    public final void setRegistratieDt(final Date registratieDt) {
        this.registratieDt = registratieDt;
    }

    public final void setEsbCorrelationId(final String esbCorrelationId) {
        this.esbCorrelationId = esbCorrelationId;
    }

    public final String getEsbCorrelationId() {
        return esbCorrelationId;
    }

    public final StatusEnum getStatus() {
        return status;
    }

    public final void setStatus(final StatusEnum status) {
        this.status = status;
    }

    public final String getEsbMessageId() {
        return esbMessageId;
    }

    public final void setEsbMessageId(final String esbMessageId) {
        this.esbMessageId = esbMessageId;
    }

    public final boolean isReport() {
        return isReport;
    }

    public final void setReport(final boolean isReport) {
        this.isReport = isReport;
    }

    /**
     * Maak herhaal bericht.
     * 
     * @return herhaal bericht
     */
    public final Bericht createHerhaalBericht() {
        final Bericht herhaalBericht = new Bericht();
        herhaalBericht.setAanduidingInUit(Bericht.AANDUIDING_IN_UIT_UIT);
        herhaalBericht.setBerichtInhoud(lo3Berichtinhoud);
        herhaalBericht.setBref(bref);
        herhaalBericht.setEref(eref);
        herhaalBericht.setEsbCorrelationId(esbCorrelationId);
        herhaalBericht.setEsbMessageId(esbMessageId);
        herhaalBericht.setOriginator(originator);
        herhaalBericht.setRecipient(recipient);
        herhaalBericht.setStatus(StatusEnum.QUEUE_RECEIVED);

        return herhaalBericht;
    }

}

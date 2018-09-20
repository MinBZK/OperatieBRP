/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractUnparsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;

/**
 * Persistent klasse voor de berichten database tabel in GBA-V.
 *
 */
@Entity
@Table(name = "lo3_bericht", schema = "public")
public class GbaBericht implements Bericht, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Lo3BerichtFactory LO3_BERICHT_FACTORY = new Lo3BerichtFactory();

    @Id
    @Column(name = "lo3_bericht_id", nullable = false, insertable = false, updatable = false)
    private Long lo3BerichtId;
    @Column(name = "aanduiding_in_uit", nullable = false, insertable = false, updatable = false)
    private Character aanduidingInUit;
    @Column(name = "bericht_activiteit_id", insertable = false, updatable = false)
    private Long berichtActiviteitId;
    @Column(name = "medium", nullable = false, insertable = false, updatable = false)
    private Character medium;
    @Column(name = "originator_or_recipient", length = 7, insertable = false, updatable = false)
    private String originatorOrRecipient;
    @Column(name = "spg_mailbox_instantie", insertable = false, updatable = false)
    private Integer spgMailboxInstantie;
    @Column(name = "eref", length = 12, insertable = false, updatable = false)
    private String eref;
    @Column(name = "bref", length = 12, insertable = false, updatable = false)
    private String bref;
    @Column(name = "eref2", length = 12, insertable = false, updatable = false)
    private String eref2;
    @Column(name = "berichtcyclus_id", insertable = false, updatable = false)
    private Long berichtcyclusId;
    @Column(name = "tijdstip_verzending_ontvangst", insertable = false, updatable = false)
    private Timestamp tijdstipVerzendingOntvangst;
    @Column(name = "dispatch_sequence_number", insertable = false, updatable = false)
    private Integer dispatchSequenceNumber;
    @Column(name = "report_delivery_time", insertable = false, updatable = false)
    private Timestamp reportDeliveryTime;
    @Column(name = "non_delivery_reason", length = 4, insertable = false, updatable = false)
    private String nonDeliveryReason;
    @Column(name = "non_receipt_reason", length = 4, insertable = false, updatable = false)
    private String nonReceiptReason;
    @Column(name = "bericht_data", insertable = false, updatable = false)
    private String berichtData;
    @Column(name = "kop_random_key", insertable = false, updatable = false)
    private Integer kopRandomKey;
    @Column(name = "kop_berichtsoort_nummer", length = 4, insertable = false, updatable = false)
    private String kopBerichtsoortNummer;
    @Column(name = "kop_a_nummer", insertable = false, updatable = false)
    private Long kopAnummer;
    @Column(name = "kop_oud_a_nummer", insertable = false, updatable = false)
    private Long kopOudAnummer;
    @Column(name = "kop_herhaling", insertable = false, updatable = false)
    private Character kopHerhaling;
    @Column(name = "kop_foutreden", insertable = false, updatable = false)
    private Character kopFoutreden;
    @Column(name = "kop_datum_tijd", insertable = false, updatable = false)
    private Long kopDatumTijd;

    /**
     * Geef de waarde van lo3 bericht id.
     *
     * @return lo3 bericht id
     */
    public final Long getLo3BerichtId() {
        return lo3BerichtId;
    }

    /**
     * Geef de waarde van aanduiding in uit.
     *
     * @return aanduiding in uit
     */
    public final Character getAanduidingInUit() {
        return aanduidingInUit;
    }

    /**
     * Geef de waarde van bericht activiteit id.
     *
     * @return bericht activiteit id
     */
    public final Long getBerichtActiviteitId() {
        return berichtActiviteitId;
    }

    /**
     * Geef de waarde van medium.
     *
     * @return medium
     */
    public final Character getMedium() {
        return medium;
    }

    /**
     * Geef de waarde van originator or recipient.
     *
     * @return originator or recipient
     */
    public final String getOriginatorOrRecipient() {
        return originatorOrRecipient;
    }

    /**
     * Geef de waarde van spg mailbox instantie.
     *
     * @return spg mailbox instantie
     */
    public final Integer getSpgMailboxInstantie() {
        return spgMailboxInstantie;
    }

    /**
     * Geef de waarde van eref.
     *
     * @return eref
     */
    public final String getEref() {
        return eref;
    }

    /**
     * Geef de waarde van bref.
     *
     * @return bref
     */
    public final String getBref() {
        return bref;
    }

    /**
     * Geef de waarde van eref2.
     *
     * @return eref2
     */
    public final String getEref2() {
        return eref2;
    }

    /**
     * Geef de waarde van berichtcyclus id.
     *
     * @return berichtcyclus id
     */
    public final Long getBerichtcyclusId() {
        return berichtcyclusId;
    }

    /**
     * Geef de waarde van tijdstip verzending ontvangst.
     *
     * @return tijdstip verzending ontvangst
     */
    public final Timestamp getTijdstipVerzendingOntvangst() {
        return tijdstipVerzendingOntvangst;
    }

    /**
     * Geef de waarde van dispatch sequence number.
     *
     * @return dispatch sequence number
     */
    public final Integer getDispatchSequenceNumber() {
        return dispatchSequenceNumber;
    }

    /**
     * Geef de waarde van report delivery time.
     *
     * @return report delivery time
     */
    public final Timestamp getReportDeliveryTime() {
        return reportDeliveryTime;
    }

    /**
     * Geef de waarde van non delivery reason.
     *
     * @return non delivery reason
     */
    public final String getNonDeliveryReason() {
        return nonDeliveryReason;
    }

    /**
     * Geef de waarde van non receipt reason.
     *
     * @return non receipt reason
     */
    public final String getNonReceiptReason() {
        return nonReceiptReason;
    }

    /**
     * Geef de waarde van bericht data.
     *
     * @return bericht data
     */
    public final String getBerichtData() {
        return berichtData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.levering.vergelijker.entity.Bericht#getBerichtInhoud()
     */
    @Override
    public final String getBerichtInhoud() {
        final Lo3Bericht lo3Bericht = LO3_BERICHT_FACTORY.getBericht(berichtData);
        if (lo3Bericht instanceof AbstractUnparsedLo3Bericht) {
            return ((AbstractUnparsedLo3Bericht) lo3Bericht).getInhoud();
        }
        return berichtData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.levering.vergelijker.entity.Bericht#getBericht()
     */
    @Override
    public final String getBericht() {
        return berichtData;
    }

    /**
     * Geef de waarde van kop random key.
     *
     * @return kop random key
     */
    public final Integer getKopRandomKey() {
        return kopRandomKey;
    }

    /**
     * Geef de waarde van kop berichtsoort nummer.
     *
     * @return kop berichtsoort nummer
     */
    public final String getKopBerichtsoortNummer() {
        return kopBerichtsoortNummer;
    }

    /**
     * Geef de waarde van kop anummer.
     *
     * @return kop anummer
     */
    public final Long getKopAnummer() {
        return kopAnummer;
    }

    /**
     * Geef de waarde van kop oud anummer.
     *
     * @return kop oud anummer
     */
    public final Long getKopOudAnummer() {
        return kopOudAnummer;
    }

    /**
     * Geef de waarde van kop herhaling.
     *
     * @return kop herhaling
     */
    public final Character getKopHerhaling() {
        return kopHerhaling;
    }

    /**
     * Geef de waarde van kop foutreden.
     *
     * @return kop foutreden
     */
    public final Character getKopFoutreden() {
        return kopFoutreden;
    }

    /**
     * Geef de waarde van kop datum tijd.
     *
     * @return kop datum tijd
     */
    public final Long getKopDatumTijd() {
        return kopDatumTijd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.tools.levering.vergelijker.entity.Bericht#getBerichtType()
     */
    @Override
    public final String getBerichtType() {
        return kopBerichtsoortNummer;
    }

}

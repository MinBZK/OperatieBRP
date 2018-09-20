/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Logboek regel.
 */
@Entity
@Table(name = "logboek", schema = "voisc")
public class LogboekRegel {

    @Id
    @SequenceGenerator(name = "logboekIdSequence", sequenceName = "voisc.logboek_id_sequence", allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logboekIdSequence")
    @Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne
    private Mailbox mailbox;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_dt", insertable = true, updatable = false, nullable = false)
    private Date startDatumTijd;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "eind_dt", insertable = true, updatable = false, nullable = false)
    private Date eindDatumTijd;
    @Column(name = "aantalVerzonden", insertable = true, updatable = false, nullable = false)
    private int aantalVerzonden;
    @Column(name = "aantalOntvangen", insertable = true, updatable = false, nullable = false)
    private int aantalOntvangen;
    @Column(name = "aantalVerzondenOK", insertable = true, updatable = false, nullable = false)
    private int aantalVerzondenOK;
    @Column(name = "aantalVerzondenNOK", insertable = true, updatable = false, nullable = false)
    private int aantalVerzondenNOK;
    @Column(name = "aantalOntvangenOK", insertable = true, updatable = false, nullable = false)
    private int aantalOntvangenOK;
    @Column(name = "aantalOntvangenNOK", insertable = true, updatable = false, nullable = false)
    private int aantalOntvangenNOK;
    @Column(name = "foutmelding", insertable = true, updatable = false)
    private String foutmelding;

    public final Long getId() {
        return id;
    }

    public final void setId(final long id) {
        this.id = id;
    }

    public final Mailbox getMailbox() {
        return mailbox;
    }

    public final void setMailbox(final Mailbox mailbox) {
        this.mailbox = mailbox;
    }

    public final Date getStartDatumTijd() {
        return startDatumTijd;
    }

    public final void setStartDatumTijd(final Date startDatumTijd) {
        this.startDatumTijd = startDatumTijd;
    }

    public final Date getEindDatumTijd() {
        return eindDatumTijd;
    }

    public final void setEindDatumTijd(final Date eindDatumTijd) {
        this.eindDatumTijd = eindDatumTijd;
    }

    public final int getAantalVerzonden() {
        return aantalVerzonden;
    }

    public final void setAantalVerzonden(final int aantalVerzonden) {
        this.aantalVerzonden = aantalVerzonden;
    }

    public final int getAantalOntvangen() {
        return aantalOntvangen;
    }

    public final void setAantalOntvangen(final int aantalOntvangen) {
        this.aantalOntvangen = aantalOntvangen;
    }

    public final int getAantalVerzondenOK() {
        return aantalVerzondenOK;
    }

    public final void setAantalVerzondenOK(final int aantalVerzondenOK) {
        this.aantalVerzondenOK = aantalVerzondenOK;
    }

    public final int getAantalVerzondenNOK() {
        return aantalVerzondenNOK;
    }

    public final void setAantalVerzondenNOK(final int aantalVerzondenNOK) {
        this.aantalVerzondenNOK = aantalVerzondenNOK;
    }

    public final int getAantalOntvangenOK() {
        return aantalOntvangenOK;
    }

    public final void setAantalOntvangenOK(final int aantalOntvangenOK) {
        this.aantalOntvangenOK = aantalOntvangenOK;
    }

    public final int getAantalOntvangenNOK() {
        return aantalOntvangenNOK;
    }

    public final void setAantalOntvangenNOK(final int aantalOntvangenNOK) {
        this.aantalOntvangenNOK = aantalOntvangenNOK;
    }

    public final String getFoutmelding() {
        return foutmelding;
    }

    public final void setFoutmelding(final String foutmelding) {
        this.foutmelding = foutmelding;
    }

    /**
     * @return heeft foutmelding.
     */
    public final boolean hasFoutmelding() {
        return foutmelding != null && !foutmelding.isEmpty();
    }

}

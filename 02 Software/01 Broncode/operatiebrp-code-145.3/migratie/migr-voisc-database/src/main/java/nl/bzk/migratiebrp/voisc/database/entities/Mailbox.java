/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Mailbox.
 */
@Entity
@Table(name = "lo3_mailbox", schema = "voisc")
public class Mailbox implements Comparable<Mailbox> {

    @Id
    @SequenceGenerator(name = "mailboxIdSequence", sequenceName = "voisc.lo3_mailbox_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailboxIdSequence")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "mailboxnr", updatable = false, nullable = false)
    private String mailboxnr;

    @Column(name = "verzender", updatable = false)
    private String verzender;

    @Column(name = "partijcode", updatable = false, nullable = false)
    private String partijcode;

    @Column(name = "blokkering_start_dt")
    private Timestamp startBlokkering;

    @Column(name = "blokkering_eind_dt")
    private Timestamp eindeBlokkering;

    @Column(name = "mailboxpwd")
    private String mailboxpwd;

    @Column(name = "limitNumber", nullable = false)
    private int limitNumber;

    @Column(name = "laatste_wijziging_pwd_dt")
    private Timestamp laatsteWijzigingPwd;

    @Column(name = "laatste_msseqnumber")
    private Integer laatsteMsSequenceNumber;

    /**
     * Geef id.
     * @return id
     */
    public final Long getId() {
        return id;
    }

    /**
     * Zet id.
     * @param id id
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef mailbox nummer.
     * @return mailbox nummer
     */
    public final String getMailboxnr() {
        return mailboxnr;
    }

    /**
     * Zet mailbox nummer.
     * @param mailboxnr mailbox nummer
     */
    public final void setMailboxnr(final String mailboxnr) {
        this.mailboxnr = mailboxnr;
    }


    /**
     * Geef verzendende mailbox nummer.
     * @return verzendende mailbox nummer
     */
    public final String getVerzender() {
        return verzender;
    }

    /**
     * Zet verzendende mailbox nummer.
     * @param verzender verzendende mailbox nummer
     */
    public final void setVerzender(final String verzender) {
        this.verzender = verzender;
    }

    /**
     * Geef partij code.
     * @return partij code
     */
    public final String getPartijcode() {
        return partijcode;
    }

    /**
     * Zet partij code.
     * @param partijcode partij code
     */
    public final void setPartijcode(final String partijcode) {
        this.partijcode = partijcode;
    }


    /**
     * Geef start blokkering.
     * @return start blokkering
     */
    public final Timestamp getStartBlokkering() {
        return Kopieer.timestamp(startBlokkering);
    }

    /**
     * Zet start blokkering.
     * @param startBlokkering start blokkering
     */
    public final void setStartBlokkering(final Timestamp startBlokkering) {
        this.startBlokkering = Kopieer.timestamp(startBlokkering);
    }

    /**
     * Geef eind blokkering.
     * @return eind blokkering
     */
    public final Timestamp getEindeBlokkering() {
        return Kopieer.timestamp(eindeBlokkering);
    }

    /**
     * Zet eind blokkering.
     * @param eindeBlokkering eind blokkering
     */
    public final void setEindeBlokkering(final Timestamp eindeBlokkering) {
        this.eindeBlokkering = Kopieer.timestamp(eindeBlokkering);
    }

    /**
     * Geef mailbox wachtwoord.
     * @return mailbox wachtwoord
     */
    public final String getMailboxpwd() {
        return mailboxpwd;
    }

    /**
     * Zet mailbox wachtwoord.
     * @param mailboxpwd mailbox wachtwoord
     */
    public final void setMailboxpwd(final String mailboxpwd) {
        this.mailboxpwd = mailboxpwd;
    }

    /**
     * Geef mailbox limiet.
     * @return mailbox limiet
     */
    public final int getLimitNumber() {
        return limitNumber;
    }

    /**
     * Zet mailbox limiet.
     * @param limitNumber mailbox limiet
     */
    public final void setLimitNumber(final int limitNumber) {
        this.limitNumber = limitNumber;
    }


    /**
     * Geef datum laatste wijziging.
     * @return datum laatste wijziging
     */
    public final Timestamp getLaatsteWijzigingPwd() {
        return Kopieer.timestamp(laatsteWijzigingPwd);
    }

    /**
     * Zet datum laatste wijziging.
     * @param laatsteWijzigingPwd datum laatste wijziging
     */
    public final void setLaatsteWijzigingPwd(final Timestamp laatsteWijzigingPwd) {
        this.laatsteWijzigingPwd = Kopieer.timestamp(laatsteWijzigingPwd);
    }

    /**
     * Geef het laatst gelezen sequence number.
     * @return het opgeslagen sequence number (of null)
     */
    public final Integer getLaatsteMsSequenceNumber() {
        return laatsteMsSequenceNumber;
    }

    /**
     * Zet laatst gelezen sequence number.
     * @param laatsteMsSequenceNumber laatst gelezen sequence number
     */
    public final void setLaatsteMsSequenceNumber(final Integer laatsteMsSequenceNumber) {
        this.laatsteMsSequenceNumber = laatsteMsSequenceNumber;
    }

    @Override
    public final int compareTo(final Mailbox o) {
        return mailboxnr.compareTo(o.mailboxnr);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */


    @Override
    public final boolean equals(final Object obj) {
        final boolean result;
        if (obj == this) {
            result = true;
        } else if (obj == null || obj.getClass() != getClass()) {
            result = false;
        } else {
            final Mailbox rhs = (Mailbox) obj;
            result = new EqualsBuilder().append(mailboxnr, rhs.mailboxnr).isEquals();
        }
        return result;
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(mailboxnr).toHashCode();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.entities;

import java.text.DecimalFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Mailbox.
 */
@Entity
@Table(name = "lo3_mailbox", schema = "voisc")
public class Mailbox implements Comparable<Mailbox> {

    /** Instantietype: afnemer. */
    public static final String INSTANTIETYPE_AFNEMER = "A";
    /** Instantietype: gemeente. */
    public static final String INSTANTIETYPE_GEMEENTE = "G";
    /** Instantietype: centrale voorziening. */
    public static final String INSTANTIETYPE_CENTRALE_VOORZIENING = "C";

    private static final DecimalFormat AFNEMER_FORMAT = new DecimalFormat("000000");
    private static final DecimalFormat GEMEENTE_FORMAT = new DecimalFormat("0000");

    @Id
    @SequenceGenerator(name = "mailboxIdSequence", sequenceName = "voisc.lo3_mailbox_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailboxIdSequence")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "instantiecode", updatable = false, nullable = false)
    private Integer instantiecode;

    @Column(name = "instantietype", updatable = false, nullable = false)
    private String instantietype;

    @Column(name = "mailboxnr", updatable = false, nullable = false)
    private String mailboxnr;

    @Column(name = "mailboxpwd")
    private String mailboxpwd;

    @Column(name = "limitNumber", nullable = false)
    private int limitNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blokkering_start_dt")
    private Date startBlokkering;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blokkering_eind_dt")
    private Date eindeBlokkering;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "laatste_wijziging_pwd_dt")
    private Date laatsteWijzigingPwd;

    @Column(name = "laatste_msseqnumber")
    private Integer laatsteMsSequenceNumber;

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
     * Geef instantie code.
     * 
     * @return instantie code
     */
    public final Integer getInstantiecode() {
        return instantiecode;
    }

    /**
     * Zet instantie code.
     * 
     * @param instantiecode
     *            instantie code
     */
    public final void setInstantiecode(final Integer instantiecode) {
        this.instantiecode = instantiecode;
    }

    /**
     * Geef instantie type.
     * 
     * @return instantie type
     */
    public final String getInstantietype() {
        return instantietype;
    }

    /**
     * Zet instantie type.
     * 
     * @param instantietype
     *            instantie type
     */
    public final void setInstantietype(final String instantietype) {
        this.instantietype = instantietype;
    }

    /**
     * Geef mailbox nummer.
     * 
     * @return mailbox nummer
     */
    public final String getMailboxnr() {
        return mailboxnr;
    }

    /**
     * Zet mailbox nummer.
     * 
     * @param mailboxnr
     *            mailbox nummer
     */
    public final void setMailboxnr(final String mailboxnr) {
        this.mailboxnr = mailboxnr;
    }

    /**
     * Geef mailbox wachtwoord.
     * 
     * @return mailbox wachtwoord
     */
    public final String getMailboxpwd() {
        return mailboxpwd;
    }

    /**
     * Zet mailbox wachtwoord.
     * 
     * @param mailboxpwd
     *            mailbox wachtwoord
     */
    public final void setMailboxpwd(final String mailboxpwd) {
        this.mailboxpwd = mailboxpwd;
    }

    /**
     * Geef mailbox limiet.
     * 
     * @return mailbox limiet
     */
    public final int getLimitNumber() {
        return limitNumber;
    }

    /**
     * Zet mailbox limiet.
     * 
     * @param limitNumber
     *            mailbox limiet
     */
    public final void setLimitNumber(final int limitNumber) {
        this.limitNumber = limitNumber;
    }

    /**
     * Geef start blokkering.
     * 
     * @return start blokkering
     */
    public final Date getStartBlokkering() {
        return Kopieer.utilDate(startBlokkering);
    }

    /**
     * Zet start blokkering.
     * 
     * @param startBlokkering
     *            start blokkering
     */
    public final void setStartBlokkering(final Date startBlokkering) {
        this.startBlokkering = Kopieer.utilDate(startBlokkering);
    }

    /**
     * Geef eind blokkering.
     * 
     * @return eind blokkering
     */
    public final Date getEindeBlokkering() {
        return Kopieer.utilDate(eindeBlokkering);
    }

    /**
     * Zet eind blokkering.
     * 
     * @param eindeBlokkering
     *            eind blokkering
     */
    public final void setEindeBlokkering(final Date eindeBlokkering) {
        this.eindeBlokkering = Kopieer.utilDate(eindeBlokkering);
    }

    /**
     * Geef datum laatste wijziging.
     * 
     * @return datum laatste wijziging
     */
    public final Date getLaatsteWijzigingPwd() {
        return Kopieer.utilDate(laatsteWijzigingPwd);
    }

    /**
     * Zet datum laatste wijziging.
     * 
     * @param laatsteWijzigingPwd
     *            datum laatste wijziging
     */
    public final void setLaatsteWijzigingPwd(final Date laatsteWijzigingPwd) {
        this.laatsteWijzigingPwd = Kopieer.utilDate(laatsteWijzigingPwd);
    }

    /**
     * Geef het laatst gelezen sequence number.
     *
     * @return het opgeslagen sequence number (of null)
     */
    public final Integer getLaatsteMsSequenceNumber() {
        return laatsteMsSequenceNumber;
    }

    /**
     * Zet laatst gelezen sequence number.
     * 
     * @param laatsteMsSequenceNumber
     *            laatst gelezen sequence number
     */
    public final void setLaatsteMsSequenceNumber(final Integer laatsteMsSequenceNumber) {
        this.laatsteMsSequenceNumber = laatsteMsSequenceNumber;
    }

    @Override
    public final int compareTo(final Mailbox o) {
        return instantiecode.compareTo(o.instantiecode);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Geef de geformatteerde instantiecode terug (4 lang voor gemeente, 6 lang voor afnemer).
     *
     * @return instantiecode
     */
    public final String getFormattedInstantiecode() {
        if (instantietype == null || instantiecode == null) {
            return null;
        }

        final String result;
        switch (instantietype) {
            case INSTANTIETYPE_AFNEMER:
                result = AFNEMER_FORMAT.format(instantiecode);
                break;
            case INSTANTIETYPE_GEMEENTE:
                result = GEMEENTE_FORMAT.format(instantiecode);
                break;
            default:
                result = instantiecode.toString();
                break;
        }
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final Mailbox rhs = (Mailbox) obj;
        return new EqualsBuilder().append(instantiecode, rhs.instantiecode).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(instantiecode).toHashCode();
    }
}

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mailbox.
 */
@Entity
@Table(name = "lo3_mailbox", schema = "voisc")
public class Mailbox {

    @Id
    @SequenceGenerator(name = "mailboxIdSequence", sequenceName = "voisc.lo3_mailbox_id_sequence",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailboxIdSequence")
    @Column(name = "id", insertable = false, updatable = false, unique = true, nullable = false)
    private Long id;
    @Column(name = "gemeentecode", insertable = true, updatable = false, nullable = false, unique = true)
    private String gemeentecode;
    @Column(name = "mailboxnr", insertable = true, updatable = false, nullable = false)
    private String mailboxnr;
    @Column(name = "mailboxpwd", insertable = true, updatable = false, nullable = false)
    private String mailboxpwd;
    @Column(name = "limitNumber", insertable = true, updatable = false, nullable = false)
    private int limitNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blokkering_start_dt", insertable = true, updatable = false, nullable = false)
    private Date startBlokkering;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "blokkering_eind_dt", insertable = true, updatable = false, nullable = false)
    private Date eindeBlokkering;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "laatste_wijziging_pwd_dt", insertable = true, updatable = true, nullable = false)
    private Date laatsteWijzigingPwd;

    public final Long getId() {
        return id;
    }

    public final void setId(final long id) {
        this.id = id;
    }

    public final String getGemeentecode() {
        return gemeentecode;
    }

    public final void setGemeentecode(final String gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public final String getMailboxnr() {
        return mailboxnr;
    }

    public final void setMailboxnr(final String mailboxnr) {
        this.mailboxnr = mailboxnr;
    }

    public final String getMailboxpwd() {
        return mailboxpwd;
    }

    public final void setMailboxpwd(final String mailboxpwd) {
        this.mailboxpwd = mailboxpwd;
    }

    public final int getLimitNumber() {
        return limitNumber;
    }

    public final void setLimitNumber(final int limitNumber) {
        this.limitNumber = limitNumber;
    }

    public final Date getEindeBlokkering() {
        return eindeBlokkering;
    }

    public final void setEindeBlokkering(final Date eindeBlokkering) {
        this.eindeBlokkering = eindeBlokkering;
    }

    public final Date getStartBlokkering() {
        return startBlokkering;
    }

    public final void setStartBlokkering(final Date startBlokkering) {
        this.startBlokkering = startBlokkering;
    }

    public final Date getLaatsteWijzigingPwd() {
        return laatsteWijzigingPwd;
    }

    public final void setLaatsteWijzigingPwd(final Date laatsteWijziging) {
        laatsteWijzigingPwd = laatsteWijziging;
    }
}

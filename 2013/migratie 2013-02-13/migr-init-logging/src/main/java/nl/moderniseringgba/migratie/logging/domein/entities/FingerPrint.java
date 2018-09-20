/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.domein.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the sync log/result database table.
 */
@Entity
@Table(name = "fingerprint", schema = "public")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd t.b.v. het datamodel en bevat daarom geen javadoc, daarnaast mogen entities
 * en de methoden van entities niet final zijn.
 */
public class FingerPrint implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "FINGERPRINT_ID_GENERATOR", sequenceName = "SEQ_FINGERPRINT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FINGERPRINT_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "voorkomen_verschil", insertable = true, updatable = true)
    private String voorkomenVerschil;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gbav_pl_id")
    private InitVullingLog log;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the voorkomenVerschil
     */
    public String getVoorkomenVerschil() {
        return voorkomenVerschil;
    }

    /**
     * @param voorkomenVerschil
     *            the voorkomenVerschil to set
     */
    public void setVoorkomenVerschil(final String voorkomenVerschil) {
        this.voorkomenVerschil = voorkomenVerschil;
    }

    /**
     * @return the log
     */
    public InitVullingLog getLog() {
        return log;
    }

    /**
     * @param log
     *            the log to set
     */
    void setLog(final InitVullingLog log) {
        this.log = log;
    }

}

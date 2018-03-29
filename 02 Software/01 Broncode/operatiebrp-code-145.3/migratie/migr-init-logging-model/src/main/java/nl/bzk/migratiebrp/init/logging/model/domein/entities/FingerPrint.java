/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.model.domein.entities;

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
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The persistent class for the sync log/result database table.
 */
@Entity
@Table(name = "fingerprint", schema = "initvul")
public class FingerPrint implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "FINGERPRINT_ID_GENERATOR", sequenceName = "initvul.seq_fingerprint", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FINGERPRINT_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "voorkomen_verschil", insertable = true, updatable = true, nullable = false)
    private String voorkomenVerschil;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "gbav_pl_id", nullable = false)
    private InitVullingLog log;

    /**
     * Default JPA constructor.
     */
    protected FingerPrint() {
    }

    /**
     * Maakt een FingerPrint object.
     * @param voorkomenVerschil het voorkomen verschil
     */
    public FingerPrint(final String voorkomenVerschil) {
        setVoorkomenVerschil(voorkomenVerschil);
    }

    /**
     * Geef de waarde van id.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van voorkomen verschil.
     * @return the voorkomenVerschil
     */
    public String getVoorkomenVerschil() {
        return voorkomenVerschil;
    }

    /**
     * Zet de waarde van voorkomen verschil.
     * @param voorkomenVerschil the voorkomenVerschil to set
     */
    public void setVoorkomenVerschil(final String voorkomenVerschil) {
        ValidationUtils.controleerOpNullWaarden("voorkomenVerschil mag niet null zijn", voorkomenVerschil);
        this.voorkomenVerschil = voorkomenVerschil;
    }

    /**
     * Geef de waarde van log.
     * @return the log
     */
    public InitVullingLog getLog() {
        return log;
    }

    /**
     * Zet de waarde van log.
     * @param log the log to set
     */
    void setLog(final InitVullingLog log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("voorkomenVerschil", voorkomenVerschil)
                .toString();
    }
}

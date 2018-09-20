/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.entiteit;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import nl.bzk.migratiebrp.util.common.Kopieer;

/**
 * Persistent klasse voor de processen extractie database tabel.
 * 
 */
@Entity
@Table(name = "jbpm_processInstance")
public class ProcesInstantie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_", nullable = false, insertable = true, updatable = true, unique = true)
    private Long id;

    @Column(name = "version_", nullable = false, insertable = true, updatable = true)
    private Integer version;

    @Column(name = "key_", nullable = false, insertable = true, updatable = true, length = 30)
    private String key;

    @Column(name = "start_", nullable = false, insertable = true, updatable = true, length = 60)
    private Timestamp start;

    @Column(name = "end_", nullable = true, insertable = true, updatable = true, length = 255)
    private Timestamp end;

    @Column(name = "issuspended_", nullable = true, insertable = true, updatable = true)
    private Boolean issuspended;

    @Column(name = "processdefinition_", nullable = true, insertable = true, updatable = true)
    private Long processdefinition;

    @Column(name = "roottoken_", nullable = true, insertable = true, updatable = true)
    private Long roottoken;

    @Column(name = "superprocesstoken_", nullable = true, insertable = true, updatable = true)
    private Long superprocesstoken;

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public final Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van version.
     *
     * @return version
     */
    public final Integer getVersion() {
        return version;
    }

    /**
     * Geef de waarde van key.
     *
     * @return key
     */
    public final String getKey() {
        return key;
    }

    /**
     * Geef de waarde van start.
     *
     * @return start
     */
    public final Timestamp getStart() {
        return Kopieer.timestamp(start);
    }

    /**
     * Geef de waarde van end.
     *
     * @return end
     */
    public final Timestamp getEnd() {
        return Kopieer.timestamp(end);
    }

    /**
     * Geef de waarde van issuspended.
     *
     * @return issuspended
     */
    public final Boolean getIssuspended() {
        return issuspended;
    }

    /**
     * Geef de waarde van processdefinition.
     *
     * @return processdefinition
     */
    public final Long getProcessdefinition() {
        return processdefinition;
    }

    /**
     * Geef de waarde van roottoken.
     *
     * @return roottoken
     */
    public final Long getRoottoken() {
        return roottoken;
    }

    /**
     * Geef de waarde van superprocesstoken.
     *
     * @return superprocesstoken
     */
    public final Long getSuperprocesstoken() {
        return superprocesstoken;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Scope patroon element entiteit uit het prot-schema.
 */
@Entity
@Table(name = "scopepatroonelement", schema = "prot")
public class ScopePatroonElement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "scopepatroonelement_id_generator", sequenceName = "prot.seq_scopepatroonelement", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scopepatroonelement_id_generator")
    @Column(updatable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "scopepatroon")
    private ScopePatroon scopePatroon;
    @Column(name = "element")
    private int elementId;

    /**
     * JPA Default constructor.
     */
    public ScopePatroonElement() {
        // default jpa constructor
    }

    /**
     * @return het ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet het ID.
     *
     * @param id het ID
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van scopePatroon.
     *
     * @return scopePatroon
     */
    public ScopePatroon getScopePatroon() {
        return scopePatroon;
    }

    /**
     * Zet de waarde van scopePatroon.
     *
     * @param scopePatroon scopePatroon
     */
    public void setScopePatroon(final ScopePatroon scopePatroon) {
        this.scopePatroon = scopePatroon;
    }

    /**
     * Geet de waarde van elementId.
     *
     * @return elementId
     */
    public int getElementId() {
        return elementId;
    }

    /**
     * Zet de waarde van elementId.
     *
     * @param elementId elementId
     */
    public void setElementId(final int elementId) {
        this.elementId = elementId;
    }
}

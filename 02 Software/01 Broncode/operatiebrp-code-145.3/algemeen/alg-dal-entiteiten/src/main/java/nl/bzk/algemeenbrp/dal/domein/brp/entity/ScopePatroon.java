/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Scope patroon entiteit uit het prot-schema.
 */
@Entity
@Table(name = "scopepatroon", schema = "prot")
public class ScopePatroon implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "scopepatroon_id_generator", sequenceName = "prot.seq_scopepatroon", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scopepatroon_id_generator")
    @Column(updatable = false)
    private Integer id;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scopePatroon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScopePatroonElement> scopePatroonElementSet = new LinkedHashSet<>(0);

    /**
     * JPA Default constructor.
     */
    public ScopePatroon() {
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
     * Geef de waarde van scopePatroonElementSet.
     *
     * @return scopePatroonElementSet
     */
    public Set<ScopePatroonElement> getScopePatroonElementSet() {
        return scopePatroonElementSet;
    }

    /**
     * Zet de waarde van scopePatroonElementSet.
     *
     * @param scopePatroonElementSet scopePatroonElementSet
     */
    public void setScopePatroonElementSet(final Set<ScopePatroonElement> scopePatroonElementSet) {
        this.scopePatroonElementSet = scopePatroonElementSet;
    }

    /**
     * Voegt een scopePatroonElement toe.
     *
     * @param scopePatroonElement scopePatroonElement
     */
    public void addScopePatroonElement(final ScopePatroonElement scopePatroonElement) {
        scopePatroonElement.setScopePatroon(this);
        scopePatroonElementSet.add(scopePatroonElement);
    }

    /**
     * Verwijderd een scopePatroonElement.
     *
     * @param scopePatroonElement scopePatroonElement
     * @return true wanneer dit scope patroon het element bevatte anders false
     */
    public boolean removeScopePatroonElement(final ScopePatroonElement scopePatroonElement) {
        return scopePatroonElementSet.remove(scopePatroonElement);
    }
}

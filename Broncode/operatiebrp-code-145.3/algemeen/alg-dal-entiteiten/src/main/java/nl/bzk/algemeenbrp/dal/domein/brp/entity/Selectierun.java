/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the selrun database table.
 */
@Entity
@Table(name = "selrun", schema = "autaut")
public class Selectierun extends AbstractEntiteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "selrun_id_generator", sequenceName = "autaut.seq_selrun", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selrun_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "TsStart", nullable = false)
    private Timestamp tijdstipStart;

    @Column(name = "TsGereed")
    private Timestamp tijdstipGereed;

    // bi-directional many-to-one association to selectietaken
    @OneToMany(mappedBy = "uitgevoerdIn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Selectietaak> selectieTaken = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Selectierun() {
    }

    /**
     * Maak een nieuwe Selectierun.
     * @param tijdstipstart dienst
     */
    public Selectierun(final Timestamp tijdstipstart) {
        setTijdstipStart(tijdstipstart);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Verblijfsrecht.
     * @param id de nieuwe waarde voor id van Verblijfsrecht
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return tijdstipstart
     */
    public Timestamp getTijdstipStart() {
        return tijdstipStart;
    }

    /**
     * Zet tijdstipStart.
     * @param tijdstipStart tijdstipStart
     */
    public void setTijdstipStart(final Timestamp tijdstipStart) {
        ValidationUtils.controleerOpNullWaarden("tijdstipStart mag niet null zijn", tijdstipStart);
        this.tijdstipStart = tijdstipStart;
    }

    /**
     * @return tijdstipGereed
     */
    public Timestamp getTijdstipGereed() {
        return tijdstipGereed;
    }

    /**
     * Zet tijdstipGereed.
     * @param tijdstipGereed tijdstipGereed
     */
    public void setTijdstipGereed(final Timestamp tijdstipGereed) {
        this.tijdstipGereed = tijdstipGereed;
    }

    /**
     * @return selectieTaken
     */
    public Set<Selectietaak> getSelectieTaken() {
        return selectieTaken;
    }

    /**
     * @param selectieTaken selectieTaken
     */
    public void setSelectieTaken(Set<Selectietaak> selectieTaken) {
        this.selectieTaken = selectieTaken;
    }
}

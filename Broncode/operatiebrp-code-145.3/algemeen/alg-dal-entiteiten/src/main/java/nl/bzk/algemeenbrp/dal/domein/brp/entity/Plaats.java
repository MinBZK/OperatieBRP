/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the plaats database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "plaats", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = "naam"))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "Plaats" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Plaats")
public class Plaats extends AbstractEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "plaats_id_generator", sequenceName = "kern.seq_plaats", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plaats_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String naam;

    /**
     * JPA default constructor.
     */
    protected Plaats() {}

    /**
     * Maak een nieuwe plaats.
     *
     * @param naam naam
     */
    public Plaats(final String naam) {
        setNaam(naam);
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
     * Zet de waarden voor id van Plaats.
     *
     * @param id de nieuwe waarde voor id van Plaats
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van Plaats.
     *
     * @return de waarde van naam van Plaats
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Plaats.
     *
     * @param naam de nieuwe waarde voor naam van Plaats
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Wanneer de code van twee woonplaatsen gelijk zijn dan worden ze beschouwd als inhoudelijk
     * gelijk.
     *
     * @param andereWoonplaats de andere woonplaats waarmee vergeleken wordt
     * @return true als de code gelijk is aan de code van de andere woonplaats, anders false
     */
    public boolean isInhoudelijkGelijkAan(final Plaats andereWoonplaats) {
        return this == andereWoonplaats || andereWoonplaats != null;
    }
}

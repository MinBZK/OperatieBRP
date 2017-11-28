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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the rdnwijzverblijf database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "rdnwijzverblijf", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "RedenWijzigingVerblijf" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from RedenWijzigingVerblijf")
public class RedenWijzigingVerblijf extends AbstractEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    public static final char INFRASTRUCTURELE_WIJZIGING = 'I';
    public static final char AMBTSHALVE = 'A';

    @Id
    @SequenceGenerator(name = "rdnwijzverblijf_id_generator", sequenceName = "kern.seq_rdnwijzverblijf", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rdnwijzverblijf_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(nullable = false, length = 1, unique = true)
    private char code;

    @Column(nullable = false, length = 40, unique = true)
    private String naam;

    /**
     * JPA default constructor.
     */
    protected RedenWijzigingVerblijf() {}

    /**
     * Maak een nieuwe reden wijziging verblijf.
     *
     * @param code code
     * @param naam naam
     */
    public RedenWijzigingVerblijf(final char code, final String naam) {
        this.code = code;
        setNaam(naam);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.DynamischeStamtabel#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RedenWijzigingVerblijf.
     *
     * @param id de nieuwe waarde voor id van RedenWijzigingVerblijf
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van RedenWijzigingVerblijf.
     *
     * @return de waarde van code van RedenWijzigingVerblijf
     */
    public char getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van RedenWijzigingVerblijf.
     *
     * @param code de nieuwe waarde voor code van RedenWijzigingVerblijf
     */
    public void setCode(final char code) {
        this.code = code;
    }

    /**
     * Geef de waarde van naam van RedenWijzigingVerblijf.
     *
     * @return de waarde van naam van RedenWijzigingVerblijf
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van RedenWijzigingVerblijf.
     *
     * @param naam de nieuwe waarde voor naam van RedenWijzigingVerblijf
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }
}

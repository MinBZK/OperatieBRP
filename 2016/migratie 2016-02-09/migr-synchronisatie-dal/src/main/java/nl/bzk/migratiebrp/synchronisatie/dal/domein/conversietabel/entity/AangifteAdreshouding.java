/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the aangifteadreshouding database table.
 *
 */
@Entity
@Table(name = "convaangifteadresh", schema = "conv", uniqueConstraints = @UniqueConstraint(columnNames = {"aang", "rdnwijzverblijf" }))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class AangifteAdreshouding implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr7210omsvandeaangifteadre", insertable = false, updatable = false, length = 1, nullable = false, unique = true)
    private char lo3OmschrijvingAangifteAdreshouding;

    @ManyToOne
    @JoinColumn(name = "aang")
    private Aangever aangever;

    @ManyToOne
    @JoinColumn(name = "rdnwijzverblijf")
    private RedenWijzigingVerblijf redenWijzigingVerblijf;

    /**
     * JPA default constructor.
     */
    protected AangifteAdreshouding() {
    }

    /**
     * Default constructor.
     *
     * @param lo3OmschrijvingAangifteAdreshouding
     *            De LO3 omschrijving van de aangifte adreshouding.
     */
    public AangifteAdreshouding(final char lo3OmschrijvingAangifteAdreshouding) {
        this.lo3OmschrijvingAangifteAdreshouding = lo3OmschrijvingAangifteAdreshouding;
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 omschrijving aangifte adreshouding.
     *
     * @return lo3 omschrijving aangifte adreshouding
     */
    public char getLo3OmschrijvingAangifteAdreshouding() {
        return lo3OmschrijvingAangifteAdreshouding;
    }

    /**
     * Zet de waarde van lo3 omschrijving aangifte adreshouding.
     *
     * @param lo3OmschrijvingAangifteAdreshouding
     *            lo3 omschrijving aangifte adreshouding
     */
    public void setLo3OmschrijvingAangifteAdreshouding(final char lo3OmschrijvingAangifteAdreshouding) {
        this.lo3OmschrijvingAangifteAdreshouding = lo3OmschrijvingAangifteAdreshouding;
    }

    /**
     * Geef de waarde van aangever.
     *
     * @return aangever
     */
    public Aangever getAangever() {
        return aangever;
    }

    /**
     * Zet de waarde van aangever.
     *
     * @param aangever
     *            aangever
     */
    public void setAangever(final Aangever aangever) {
        this.aangever = aangever;
    }

    /**
     * Geef de waarde van reden wijziging verblijf.
     *
     * @return reden wijziging verblijf
     */
    public RedenWijzigingVerblijf getRedenWijzigingVerblijf() {
        return redenWijzigingVerblijf;
    }

    /**
     * Zet de waarde van reden wijziging verblijf.
     *
     * @param redenWijzigingVerblijf
     *            reden wijziging verblijf
     */
    public void setRedenWijzigingVerblijf(final RedenWijzigingVerblijf redenWijzigingVerblijf) {
        this.redenWijzigingVerblijf = redenWijzigingVerblijf;
    }
}

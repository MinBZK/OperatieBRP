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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the aangifteadreshouding database table.
 */
@Entity
@Table(name = "convaangifteadresh", schema = "conv", uniqueConstraints = @UniqueConstraint(columnNames = {"aang", "rdnwijzverblijf"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "AangifteAdreshouding" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "from AangifteAdreshouding a left join fetch a.redenWijzigingVerblijf left join fetch a.aangever")
public class AangifteAdreshouding implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "convaangifteadres_id_generator", sequenceName = "conv.seq_convaangifteadresh", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convaangifteadres_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr7210omsvandeaangifteadre", length = 1, nullable = false, unique = true)
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
    protected AangifteAdreshouding() {}

    /**
     * Default constructor.
     *
     * @param lo3OmschrijvingAangifteAdreshouding De LO3 omschrijving van de aangifte adreshouding.
     */
    public AangifteAdreshouding(final char lo3OmschrijvingAangifteAdreshouding) {
        this.lo3OmschrijvingAangifteAdreshouding = lo3OmschrijvingAangifteAdreshouding;
    }

    /**
     * Geef de waarde van id van AangifteAdreshouding.
     *
     * @return de waarde van id van AangifteAdreshouding
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van AangifteAdreshouding.
     *
     * @param id de nieuwe waarde voor id van AangifteAdreshouding
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 omschrijving aangifte adreshouding van AangifteAdreshouding.
     *
     * @return de waarde van lo3 omschrijving aangifte adreshouding van AangifteAdreshouding
     */
    public char getLo3OmschrijvingAangifteAdreshouding() {
        return lo3OmschrijvingAangifteAdreshouding;
    }

    /**
     * Zet de waarden voor lo3 omschrijving aangifte adreshouding van AangifteAdreshouding.
     *
     * @param lo3OmschrijvingAangifteAdreshouding de nieuwe waarde voor lo3 omschrijving aangifte
     *        adreshouding van AangifteAdreshouding
     */
    public void setLo3OmschrijvingAangifteAdreshouding(final char lo3OmschrijvingAangifteAdreshouding) {
        this.lo3OmschrijvingAangifteAdreshouding = lo3OmschrijvingAangifteAdreshouding;
    }

    /**
     * Geef de waarde van aangever van AangifteAdreshouding.
     *
     * @return de waarde van aangever van AangifteAdreshouding
     */
    public Aangever getAangever() {
        return aangever;
    }

    /**
     * Zet de waarden voor aangever van AangifteAdreshouding.
     *
     * @param aangever de nieuwe waarde voor aangever van AangifteAdreshouding
     */
    public void setAangever(final Aangever aangever) {
        this.aangever = aangever;
    }

    /**
     * Geef de waarde van reden wijziging verblijf van AangifteAdreshouding.
     *
     * @return de waarde van reden wijziging verblijf van AangifteAdreshouding
     */
    public RedenWijzigingVerblijf getRedenWijzigingVerblijf() {
        return redenWijzigingVerblijf;
    }

    /**
     * Zet de waarden voor reden wijziging verblijf van AangifteAdreshouding.
     *
     * @param redenWijzigingVerblijf de nieuwe waarde voor reden wijziging verblijf van
     *        AangifteAdreshouding
     */
    public void setRedenWijzigingVerblijf(final RedenWijzigingVerblijf redenWijzigingVerblijf) {
        this.redenWijzigingVerblijf = redenWijzigingVerblijf;
    }
}

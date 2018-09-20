/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the partijonderzoek database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "partijonderzoek", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"onderzoek", "partij" }))
@Cacheable
@SuppressWarnings("checkstyle:designforextension")
public class PartijOnderzoek extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "partijonderzoek_id_generator", sequenceName = "kern.seq_partijonderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partijonderzoek_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    // bi-directional many-to-one association to PartijOnderzoekHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partijOnderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PartijOnderzoekHistorie> partijOnderzoekHistorieSet = new LinkedHashSet<>(0);

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    @Column(name = "rol", nullable = true)
    private Short soortPartijOnderzoekId;

    /**
     * JPA default constructor.
     */
    protected PartijOnderzoek() {
    }

    /**
     * Maak een nieuwe partij onderzoek.
     *
     * @param partij
     *            partij
     * @param onderzoek
     *            onderzoek
     */
    public PartijOnderzoek(final Partij partij, final Onderzoek onderzoek) {
        setPartij(partij);
        setOnderzoek(onderzoek);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     * 
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van partij onderzoek historie set.
     *
     * @return partij onderzoek historie set
     */
    public Set<PartijOnderzoekHistorie> getPartijOnderzoekHistorieSet() {
        return partijOnderzoekHistorieSet;
    }

    /**
     * Toevoegen van een persoon onderzoek historie.
     *
     * @param partijOnderzoekHistorie
     *            partij onderzoek historie
     */
    public void addPartijOnderzoekHistorie(final PartijOnderzoekHistorie partijOnderzoekHistorie) {
        partijOnderzoekHistorie.setPartijOnderzoek(this);
        partijOnderzoekHistorieSet.add(partijOnderzoekHistorie);
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarde van partij.
     *
     * @param partij
     *            partij
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van onderzoek.
     *
     * @return onderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarde van onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
    }

    /**
     * Geef de waarde van soort partij onderzoek.
     *
     * @return soort partij onderzoek
     */
    public SoortPartijOnderzoek getSoortPartijOnderzoek() {
        return SoortPartijOnderzoek.parseId(soortPartijOnderzoekId);
    }

    /**
     * Zet de waarde van soort partij onderzoek.
     *
     * @param soortPartijOnderzoek
     *            soort partij onderzoek
     */
    public void setSoortPartijOnderzoek(final SoortPartijOnderzoek soortPartijOnderzoek) {
        soortPartijOnderzoekId = soortPartijOnderzoek == null ? null :  soortPartijOnderzoek.getId();
    }
}

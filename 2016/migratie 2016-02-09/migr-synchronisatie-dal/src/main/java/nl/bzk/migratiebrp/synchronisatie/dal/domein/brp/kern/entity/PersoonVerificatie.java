/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
 * The persistent class for the persverificatie database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persverificatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonVerificatie extends AbstractDeltaEntiteit implements DeltaSubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persverificatie_id_generator", sequenceName = "kern.seq_persverificatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persverificatie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dat")
    private Integer datum;

    // bi-directional many-to-one association to PersoonVerificatieHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVerificatie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonVerificatieHistorie> persoonVerificatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "geverifieerde", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "srt")
    private String soortVerificatie;

    /**
     * JPA default constructor.
     */
    protected PersoonVerificatie() {
    }

    /**
     * Maak een nieuwe persoon verificatie.
     *
     * @param persoon
     *            persoon
     * @param partij
     *            partij
     */
    public PersoonVerificatie(final Persoon persoon, final Partij partij) {
        setPersoon(persoon);
        setPartij(partij);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum.
     *
     * @return datum
     */
    public Integer getDatum() {
        return datum;
    }

    /**
     * Zet de waarde van datum.
     *
     * @param datum
     *            datum
     */
    public void setDatum(final Integer datum) {
        this.datum = datum;
    }

    /**
     * Geef de waarde van persoon verificatie historie set.
     *
     * @return persoon verificatie historie set
     */
    public Set<PersoonVerificatieHistorie> getPersoonVerificatieHistorieSet() {
        return persoonVerificatieHistorieSet;
    }

    /**
     * Toevoegen van een persoon verificatie historie.
     *
     * @param persoonVerificatieHistorie
     *            persoon verificatie historie
     */
    public void addPersoonVerificatieHistorie(final PersoonVerificatieHistorie persoonVerificatieHistorie) {
        persoonVerificatieHistorie.setPersoonVerificatie(this);
        persoonVerificatieHistorieSet.add(persoonVerificatieHistorie);
    }

    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            persoon
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
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
     * Geef de waarde van soort verificatie.
     *
     * @return soort verificatie
     */
    public String getSoortVerificatie() {
        return soortVerificatie;
    }

    /**
     * Zet de waarde van soort verificatie.
     *
     * @param soortVerificatie
     *            soort verificatie
     */
    public void setSoortVerificatie(final String soortVerificatie) {
        this.soortVerificatie = soortVerificatie;
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();
        result.put("persoonVerificatieHistorieSet", Collections.unmodifiableSet(persoonVerificatieHistorieSet));
        return result;
    }
}

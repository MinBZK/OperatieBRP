/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persverificatie database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persverificatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class PersoonVerificatie extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persverificatie_id_generator", sequenceName = "kern.seq_persverificatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persverificatie_id_generator")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dat")
    private Integer datum;

    // bi-directional many-to-one association to PersoonVerificatieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVerificatie", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonVerificatieHistorie> persoonVerificatieHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "srt", nullable = false)
    private String soortVerificatie;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    /**
     * JPA default constructor.
     */
    protected PersoonVerificatie() {}

    /**
     * Maak een nieuwe persoon verificatie.
     *
     * @param persoon persoon
     * @param partij partij
     * @param soortVerificatie de soort verificatie
     */
    public PersoonVerificatie(final Persoon persoon, final Partij partij, final String soortVerificatie) {
        setPersoon(persoon);
        setPartij(partij);
        setSoortVerificatie(soortVerificatie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonVerificatie.
     *
     * @param id de nieuwe waarde voor id van PersoonVerificatie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum van PersoonVerificatie.
     *
     * @return de waarde van datum van PersoonVerificatie
     */
    public Integer getDatum() {
        return datum;
    }

    /**
     * Zet de waarden voor datum van PersoonVerificatie.
     *
     * @param datum de nieuwe waarde voor datum van PersoonVerificatie
     */
    public void setDatum(final Integer datum) {
        this.datum = datum;
    }

    /**
     * Geef de waarde van persoon verificatie historie set van PersoonVerificatie.
     *
     * @return de waarde van persoon verificatie historie set van PersoonVerificatie
     */
    public Set<PersoonVerificatieHistorie> getPersoonVerificatieHistorieSet() {
        return persoonVerificatieHistorieSet;
    }

    /**
     * Toevoegen van een persoon verificatie historie.
     *
     * @param persoonVerificatieHistorie persoon verificatie historie
     */
    public void addPersoonVerificatieHistorie(final PersoonVerificatieHistorie persoonVerificatieHistorie) {
        persoonVerificatieHistorie.setPersoonVerificatie(this);
        persoonVerificatieHistorieSet.add(persoonVerificatieHistorie);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaSubRootEntiteit#getPersoon()
     */
    @Override
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonVerificatie.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonVerificatie
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van partij van PersoonVerificatie.
     *
     * @return de waarde van partij van PersoonVerificatie
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van PersoonVerificatie.
     *
     * @param partij de nieuwe waarde voor partij van PersoonVerificatie
     */
    public void setPartij(final Partij partij) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
    }

    /**
     * Geef de waarde van soort verificatie van PersoonVerificatie.
     *
     * @return de waarde van soort verificatie van PersoonVerificatie
     */
    public String getSoortVerificatie() {
        return soortVerificatie;
    }

    /**
     * Zet de waarden voor soort verificatie van PersoonVerificatie.
     *
     * @param soortVerificatie de nieuwe waarde voor soort verificatie van PersoonVerificatie
     */
    public void setSoortVerificatie(final String soortVerificatie) {
        ValidationUtils.controleerOpNullWaarden("Soort verificatie mag niet null zijn", soortVerificatie);
        ValidationUtils.controleerOpLegeWaarden("Soort verificatie mag niet leeg zijn", soortVerificatie);
        this.soortVerificatie = soortVerificatie;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("persoonVerificatieHistorieSet", Collections.unmodifiableSet(persoonVerificatieHistorieSet));
        return result;
    }
}

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
 * The persistent class for the persverstrbeperking database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persverstrbeperking", schema = "kern",
        uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "partij", "omsderde", "gemverordening" }))

public class PersoonVerstrekkingsbeperking extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persverstrbeperking_id_generator", sequenceName = "kern.seq_persverstrbeperking", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persverstrbeperking_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to PersoonVerstrekkingsbeperkingHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVerstrekkingsbeperking", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private final Set<PersoonVerstrekkingsbeperkingHistorie> persoonVerstrekkingsbeperkingHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partij")
    private Partij partij;

    @Column(name = "omsderde")
    private String omschrijvingDerde;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gemverordening")
    private Partij gemeenteVerordening;

    /**
     * JPA default constructor.
     */
    protected PersoonVerstrekkingsbeperking() {
    }

    /**
     * Maak een nieuwe persoon verstrekkingsbeperking.
     *
     * @param persoon
     *            persoon
     */
    public PersoonVerstrekkingsbeperking(final Persoon persoon) {
        setPersoon(persoon);
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
     * Zet de waarden voor id van PersoonVerstrekkingsbeperking.
     *
     * @param id
     *            de nieuwe waarde voor id van PersoonVerstrekkingsbeperking
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoon verstrekkingsbeperking historie set van PersoonVerstrekkingsbeperking.
     *
     * @return de waarde van persoon verstrekkingsbeperking historie set van PersoonVerstrekkingsbeperking
     */
    public Set<PersoonVerstrekkingsbeperkingHistorie> getPersoonVerstrekkingsbeperkingHistorieSet() {
        return persoonVerstrekkingsbeperkingHistorieSet;
    }

    /**
     * Toevoegen van een persoon verstrekkingsbeperking historie.
     *
     * @param persoonVerstrekkingsbeperkingHistorie
     *            persoon verstrekkingsbeperking historie
     */
    public void addPersoonVerstrekkingsbeperkingHistorie(final PersoonVerstrekkingsbeperkingHistorie persoonVerstrekkingsbeperkingHistorie) {
        persoonVerstrekkingsbeperkingHistorie.setPersoonVerstrekkingsbeperking(this);
        persoonVerstrekkingsbeperkingHistorieSet.add(persoonVerstrekkingsbeperkingHistorie);
    }

    /**
     * Geef de waarde van persoon van PersoonVerstrekkingsbeperking.
     *
     * @return de waarde van persoon van PersoonVerstrekkingsbeperking
     */
    public Persoon getPersoon() {
        return persoon;
    }

    /**
     * Zet de waarden voor persoon van PersoonVerstrekkingsbeperking.
     *
     * @param persoon
     *            de nieuwe waarde voor persoon van PersoonVerstrekkingsbeperking
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van partij van PersoonVerstrekkingsbeperking.
     *
     * @return de waarde van partij van PersoonVerstrekkingsbeperking
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet de waarden voor partij van PersoonVerstrekkingsbeperking.
     *
     * @param partij
     *            de nieuwe waarde voor partij van PersoonVerstrekkingsbeperking
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    /**
     * Geef de waarde van gemeente verordening van PersoonVerstrekkingsbeperking.
     *
     * @return de waarde van gemeente verordening van PersoonVerstrekkingsbeperking
     */
    public Partij getGemeenteVerordening() {
        return gemeenteVerordening;
    }

    /**
     * Zet de waarden voor gemeente verordening van PersoonVerstrekkingsbeperking.
     *
     * @param gemeenteVerordening
     *            de nieuwe waarde voor gemeente verordening van PersoonVerstrekkingsbeperking
     */
    public void setGemeenteVerordening(final Partij gemeenteVerordening) {
        this.gemeenteVerordening = gemeenteVerordening;
    }

    /**
     * Geef de waarde van omschrijving derde van PersoonVerstrekkingsbeperking.
     *
     * @return de waarde van omschrijving derde van PersoonVerstrekkingsbeperking
     */
    public String getOmschrijvingDerde() {
        return omschrijvingDerde;
    }

    /**
     * Zet de waarden voor omschrijving derde van PersoonVerstrekkingsbeperking.
     *
     * @param soortVerificatie
     *            de nieuwe waarde voor omschrijving derde van PersoonVerstrekkingsbeperking
     */
    public void setOmschrijvingDerde(final String soortVerificatie) {
        omschrijvingDerde = soortVerificatie;
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
     * @param actueelEnGeldig
     *            isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("persoonVerstrekkingsbeperkingHistorieSet", Collections.unmodifiableSet(persoonVerstrekkingsbeperkingHistorieSet));
        return result;
    }
}

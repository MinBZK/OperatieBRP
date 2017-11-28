/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
 * The persistent class for the persvoornaam database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persvoornaam", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "volgnr"}))
public class PersoonVoornaam extends AbstractEntiteit implements SubRootEntiteit, Serializable {

    /**
     * Sorteert de lijst van Voornamen o.b.v. de datumtijd registratie van laag naar hoog.
     */
    static final Comparator<PersoonVoornaam> COMPARATOR =
            (persoonVoornaam1, persoonVoornaam2) -> persoonVoornaam1.getVolgnummer() - persoonVoornaam2.getVolgnummer();

    /**
     * Melding indien een persoon null is.
     */
    private static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    private static final long serialVersionUID = 1L;
    // bi-directional many-to-one association to PersoonVoornaamHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVoornaam", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonVoornaamHistorie> persoonVoornaamHistorieSet = new LinkedHashSet<>(0);
    @Id
    @SequenceGenerator(name = "persvoornaam_id_generator", sequenceName = "kern.seq_persvoornaam", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persvoornaam_id_generator")
    @Column(nullable = false)
    private Long id;
    @Column(length = 200)
    private String naam;
    @Column(name = "volgnr", nullable = false)
    private int volgnummer;
    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;
    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonVoornaam() {}

    /**
     * Maak een nieuwe persoon voornaam.
     *
     * @param persoon persoon
     * @param volgnummer volgnummer
     */
    public PersoonVoornaam(final Persoon persoon, final int volgnummer) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
        this.volgnummer = volgnummer;
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
     * Zet de waarden voor id van PersoonVoornaam.
     *
     * @param id de nieuwe waarde voor id van PersoonVoornaam
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van PersoonVoornaam.
     *
     * @return de waarde van naam van PersoonVoornaam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van PersoonVoornaam.
     *
     * @param naam de nieuwe waarde voor naam van PersoonVoornaam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van volgnummer van PersoonVoornaam.
     *
     * @return de waarde van volgnummer van PersoonVoornaam
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarden voor volgnummer van PersoonVoornaam.
     *
     * @param volgnummer de nieuwe waarde voor volgnummer van PersoonVoornaam
     */
    public void setVolgnummer(final int volgnummer) {
        this.volgnummer = volgnummer;
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

    /**
     * Geef de waarde van persoon voornaam historie set van PersoonVoornaam.
     *
     * @return de waarde van persoon voornaam historie set van PersoonVoornaam
     */
    public Set<PersoonVoornaamHistorie> getPersoonVoornaamHistorieSet() {
        return persoonVoornaamHistorieSet;
    }

    /**
     * Toevoegen van een persoon voornaam historie.
     *
     * @param persoonVoornaamHistorie persoon voornaam historie
     */
    public void addPersoonVoornaamHistorie(final PersoonVoornaamHistorie persoonVoornaamHistorie) {
        persoonVoornaamHistorie.setPersoonVoornaam(this);
        persoonVoornaamHistorieSet.add(persoonVoornaamHistorie);
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
     * Zet de waarden voor persoon van PersoonVoornaam.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonVoornaam
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<FormeleHistorie>> result = new HashMap<>();
        result.put("persoonVoornaamHistorieSet", Collections.unmodifiableSet(persoonVoornaamHistorieSet));
        return result;
    }
}

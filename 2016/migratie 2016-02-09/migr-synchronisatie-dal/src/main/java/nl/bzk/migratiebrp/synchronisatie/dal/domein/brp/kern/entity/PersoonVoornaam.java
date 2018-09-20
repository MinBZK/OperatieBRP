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

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the persvoornaam database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persvoornaam", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "volgnr" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonVoornaam extends AbstractDeltaEntiteit implements DeltaSubRootEntiteit, Serializable {

    /**
     * Melding indien een persoon null is.
     */
    public static final String PERSOON_MAG_NIET_NULL_ZIJN = "persoon mag niet null zijn";

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persvoornaam_id_generator", sequenceName = "kern.seq_persvoornaam", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persvoornaam_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(length = 200)
    private String naam;

    @Column(name = "volgnr", nullable = false)
    private int volgnummer;

    // bi-directional many-to-one association to PersoonVoornaamHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVoornaam", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private final Set<PersoonVoornaamHistorie> persoonVoornaamHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    /**
     * JPA default constructor.
     */
    protected PersoonVoornaam() {
    }

    /**
     * Maak een nieuwe persoon voornaam.
     *
     * @param persoon
     *            persoon
     * @param volgnummer
     *            volgnummer
     */
    public PersoonVoornaam(final Persoon persoon, final int volgnummer) {
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
        this.volgnummer = volgnummer;
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van volgnummer.
     *
     * @return volgnummer
     */
    public int getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarde van volgnummer.
     *
     * @param volgnummer
     *            volgnummer
     */
    public void setVolgnummer(final int volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef de waarde van persoon voornaam historie set.
     *
     * @return persoon voornaam historie set
     */
    public Set<PersoonVoornaamHistorie> getPersoonVoornaamHistorieSet() {
        return persoonVoornaamHistorieSet;
    }

    /**
     * Toevoegen van een persoon voornaam historie.
     *
     * @param persoonVoornaamHistorie
     *            persoon voornaam historie
     */
    public void addPersoonVoornaamHistorie(final PersoonVoornaamHistorie persoonVoornaamHistorie) {
        persoonVoornaamHistorie.setPersoonVoornaam(this);
        persoonVoornaamHistorieSet.add(persoonVoornaamHistorie);
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
        ValidationUtils.controleerOpNullWaarden(PERSOON_MAG_NIET_NULL_ZIJN, persoon);
        this.persoon = persoon;
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();
        result.put("persoonVoornaamHistorieSet", Collections.unmodifiableSet(persoonVoornaamHistorieSet));
        return result;
    }
}

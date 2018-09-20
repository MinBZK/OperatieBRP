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
 * The persistent class for the persgeslnaamcomp database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persgeslnaamcomp", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "volgnr" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonGeslachtsnaamcomponent extends AbstractDeltaEntiteit implements DeltaSubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persgeslnaamcomp_id_generator", sequenceName = "kern.seq_persgeslnaamcomp", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persgeslnaamcomp_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "stam", length = 200)
    private String stam;

    @Column(length = 1)
    private Character scheidingsteken;

    @Column(name = "volgnr", nullable = false)
    private int volgnummer;

    @Column(length = 10)
    private String voorvoegsel;

    // bi-directional many-to-one association to PersoonGeslachtsnaamcomponentHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonGeslachtsnaamcomponent",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, orphanRemoval = true)
    private Set<PersoonGeslachtsnaamcomponentHistorie> persoonGeslachtsnaamcomponentHistorieSet = new LinkedHashSet<>(0);

    @Column(name = "adellijketitel")
    private Short adellijkeTitelId;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predicaat")
    private Short predicaatId;

    /**
     * JPA default constructor.
     */
    protected PersoonGeslachtsnaamcomponent() {
    }

    /**
     * Maak een nieuwe persoon geslachtsnaamcomponent.
     *
     * @param persoon
     *            persoon
     * @param volgnummer
     *            volgnummer
     */
    public PersoonGeslachtsnaamcomponent(final Persoon persoon, final int volgnummer) {
        setPersoon(persoon);
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
     * Geef de waarde van stam.
     *
     * @return stam
     */
    public String getStam() {
        return stam;
    }

    /**
     * Zet de waarde van stam.
     *
     * @param stam
     *            stam
     */
    public void setStam(final String stam) {
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", stam);
        this.stam = stam;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarde van scheidingsteken.
     *
     * @param scheidingsteken
     *            scheidingsteken
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van volgnummer.
     *
     * @return volgnummer
     */
    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarde van volgnummer.
     *
     * @param volgnummer
     *            volgnummer
     */
    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarde van voorvoegsel.
     *
     * @param voorvoegsel
     *            voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        Validatie.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van persoon geslachtsnaamcomponent historie set.
     *
     * @return persoon geslachtsnaamcomponent historie set
     */
    public Set<PersoonGeslachtsnaamcomponentHistorie> getPersoonGeslachtsnaamcomponentHistorieSet() {
        return persoonGeslachtsnaamcomponentHistorieSet;
    }

    /**
     * Toevoegen van een persoon geslachtsnaamcomponent historie.
     *
     * @param persoonGeslachtsnaamcomponentHistorie
     *            historie die toegevoegd wordt
     */
    public void addPersoonGeslachtsnaamcomponentHistorie(final PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie) {
        persoonGeslachtsnaamcomponentHistorie.setPersoonGeslachtsnaamcomponent(this);
        persoonGeslachtsnaamcomponentHistorieSet.add(persoonGeslachtsnaamcomponentHistorie);
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarde van adellijke titel.
     *
     * @param adellijkeTitel
     *            adellijke titel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
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
     * Geef de waarde van predicaat.
     *
     * @return predicaat
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarde van predicaat.
     *
     * @param predicaat
     *            predicaat
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
    }

    @Override
    public Map<String, Collection<? extends FormeleHistorie>> verzamelHistorie() {
        final Map<String, Collection<? extends FormeleHistorie>> result = new HashMap<>();
        result.put("persoonGeslachtsnaamcomponentHistorieSet", Collections.unmodifiableSet(persoonGeslachtsnaamcomponentHistorieSet));
        return result;
    }
}

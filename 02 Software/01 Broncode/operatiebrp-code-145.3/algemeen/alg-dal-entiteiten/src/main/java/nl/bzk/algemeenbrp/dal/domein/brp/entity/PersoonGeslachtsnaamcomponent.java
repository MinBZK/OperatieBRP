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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the persgeslnaamcomp database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "persgeslnaamcomp", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"pers", "volgnr"}))
public class PersoonGeslachtsnaamcomponent extends AbstractEntiteit implements SubRootEntiteit, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "persgeslnaamcomp_id_generator", sequenceName = "kern.seq_persgeslnaamcomp", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persgeslnaamcomp_id_generator")
    @Column(nullable = false)
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
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonGeslachtsnaamcomponent", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<PersoonGeslachtsnaamcomponentHistorie> persoonGeslachtsnaamcomponentHistorieSet = new LinkedHashSet<>(0);

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predicaat")
    private Integer predicaatId;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    /**
     * JPA default constructor.
     */
    protected PersoonGeslachtsnaamcomponent() {}

    /**
     * Maak een nieuwe persoon geslachtsnaamcomponent.
     *
     * @param persoon persoon
     * @param volgnummer volgnummer
     */
    public PersoonGeslachtsnaamcomponent(final Persoon persoon, final int volgnummer) {
        setPersoon(persoon);
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
     * Zet de waarden voor id van PersoonGeslachtsnaamcomponent.
     *
     * @param id de nieuwe waarde voor id van PersoonGeslachtsnaamcomponent
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van stam van PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van stam van PersoonGeslachtsnaamcomponent
     */
    public String getStam() {
        return stam;
    }

    /**
     * Zet de waarden voor stam van PersoonGeslachtsnaamcomponent.
     *
     * @param stam de nieuwe waarde voor stam van PersoonGeslachtsnaamcomponent
     */
    public void setStam(final String stam) {
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", stam);
        this.stam = stam;
    }

    /**
     * Geef de waarde van scheidingsteken van PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van scheidingsteken van PersoonGeslachtsnaamcomponent
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarden voor scheidingsteken van PersoonGeslachtsnaamcomponent.
     *
     * @param scheidingsteken de nieuwe waarde voor scheidingsteken van
     *        PersoonGeslachtsnaamcomponent
     */
    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van volgnummer van PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van volgnummer van PersoonGeslachtsnaamcomponent
     */
    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet de waarden voor volgnummer van PersoonGeslachtsnaamcomponent.
     *
     * @param volgnummer de nieuwe waarde voor volgnummer van PersoonGeslachtsnaamcomponent
     */
    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef de waarde van voorvoegsel van PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van voorvoegsel van PersoonGeslachtsnaamcomponent
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarden voor voorvoegsel van PersoonGeslachtsnaamcomponent.
     *
     * @param voorvoegsel de nieuwe waarde voor voorvoegsel van PersoonGeslachtsnaamcomponent
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van persoon geslachtsnaamcomponent historie set van
     * PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van persoon geslachtsnaamcomponent historie set van
     *         PersoonGeslachtsnaamcomponent
     */
    public Set<PersoonGeslachtsnaamcomponentHistorie> getPersoonGeslachtsnaamcomponentHistorieSet() {
        return persoonGeslachtsnaamcomponentHistorieSet;
    }

    /**
     * Toevoegen van een persoon geslachtsnaamcomponent historie.
     *
     * @param persoonGeslachtsnaamcomponentHistorie historie die toegevoegd wordt
     */
    public void addPersoonGeslachtsnaamcomponentHistorie(final PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie) {
        persoonGeslachtsnaamcomponentHistorie.setPersoonGeslachtsnaamcomponent(this);
        persoonGeslachtsnaamcomponentHistorieSet.add(persoonGeslachtsnaamcomponentHistorie);
    }

    /**
     * Geef de waarde van adellijke titel van PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van adellijke titel van PersoonGeslachtsnaamcomponent
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarden voor adellijke titel van PersoonGeslachtsnaamcomponent.
     *
     * @param adellijkeTitel de nieuwe waarde voor adellijke titel van PersoonGeslachtsnaamcomponent
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
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
     * Zet de waarden voor persoon van PersoonGeslachtsnaamcomponent.
     *
     * @param persoon de nieuwe waarde voor persoon van PersoonGeslachtsnaamcomponent
     */
    public void setPersoon(final Persoon persoon) {
        ValidationUtils.controleerOpNullWaarden("persoon mag niet null zijn", persoon);
        this.persoon = persoon;
    }

    /**
     * Geef de waarde van predicaat van PersoonGeslachtsnaamcomponent.
     *
     * @return de waarde van predicaat van PersoonGeslachtsnaamcomponent
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarden voor predicaat van PersoonGeslachtsnaamcomponent.
     *
     * @param predicaat de nieuwe waarde voor predicaat van PersoonGeslachtsnaamcomponent
     */
    public void setPredicaat(final Predicaat predicaat) {
        if (predicaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predicaat.getId();
        }
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
        result.put("persoonGeslachtsnaamcomponentHistorieSet", Collections.unmodifiableSet(persoonGeslachtsnaamcomponentHistorieSet));
        return result;
    }
}

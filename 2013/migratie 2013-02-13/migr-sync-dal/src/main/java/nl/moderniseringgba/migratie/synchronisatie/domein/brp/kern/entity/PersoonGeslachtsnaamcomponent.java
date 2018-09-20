/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the persgeslnaamcomp database table.
 * 
 */
@Entity
@Table(name = "persgeslnaamcomp", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonGeslachtsnaamcomponent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSGESLNAAMCOMP_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSGESLNAAMCOMP",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSGESLNAAMCOMP_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(length = 200)
    private String naam;

    @Enumerated(EnumType.STRING)
    @Column(name = "persgeslnaamcompstatushis", nullable = false, length = 1)
    private HistorieStatus persoonGeslachtsnaamcomponentStatusHistorie = HistorieStatus.X;

    @Column(length = 1)
    private Character scheidingsteken;

    @Column(name = "volgnr", nullable = false)
    private Integer volgnummer;

    @Column(length = 10)
    private String voorvoegsel;

    // bi-directional many-to-one association to PersoonGeslachtsnaamcomponentHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonGeslachtsnaamcomponent", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonGeslachtsnaamcomponentHistorie> persoonGeslachtsnaamcomponentHistorieSet =
            new LinkedHashSet<PersoonGeslachtsnaamcomponentHistorie>(0);

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "predikaat")
    private Integer predikaatId;

    public PersoonGeslachtsnaamcomponent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public HistorieStatus getPersoonGeslachtsnaamcomponentStatusHistorie() {
        return persoonGeslachtsnaamcomponentStatusHistorie;
    }

    public void setPersoonGeslachtsnaamcomponentStatusHistorie(
            final HistorieStatus persoonGeslachtsnaamcomponentStatusHistorie) {
        this.persoonGeslachtsnaamcomponentStatusHistorie = persoonGeslachtsnaamcomponentStatusHistorie;
    }

    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    public void setScheidingsteken(final Character scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public Set<PersoonGeslachtsnaamcomponentHistorie> getPersoonGeslachtsnaamcomponentHistorieSet() {
        return persoonGeslachtsnaamcomponentHistorieSet;
    }

    /**
     * @param persoonGeslachtsnaamcomponentHistorie
     */
    public void addPersoonGeslachtsnaamcomponentHistorie(
            final PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie) {
        persoonGeslachtsnaamcomponentHistorie.setPersoonGeslachtsnaamcomponent(this);
        persoonGeslachtsnaamcomponentHistorieSet.add(persoonGeslachtsnaamcomponentHistorie);
    }

    /**
     * @return
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * @param adellijkeTitel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    /**
     * @return
     */
    public Predikaat getPredikaat() {
        return Predikaat.parseId(predikaatId);
    }

    /**
     * @param predikaat
     */
    public void setPredikaat(final Predikaat predikaat) {
        if (predikaat == null) {
            predikaatId = null;
        } else {
            predikaatId = predikaat.getId();
        }
    }
}

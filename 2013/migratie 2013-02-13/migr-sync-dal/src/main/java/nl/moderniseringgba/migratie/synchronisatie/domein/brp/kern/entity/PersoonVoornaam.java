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
 * The persistent class for the persvoornaam database table.
 * 
 */
@Entity
@Table(name = "persvoornaam", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonVoornaam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSVOORNAAM_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSVOORNAAM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSVOORNAAM_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(length = 40)
    private String naam;

    @Enumerated(EnumType.STRING)
    @Column(name = "persvoornaamstatushis", nullable = false, length = 1)
    private HistorieStatus persoonVoornaamStatusHistorie = HistorieStatus.X;

    @Column(name = "volgnr", nullable = false)
    private Integer volgnummer;

    // bi-directional many-to-one association to PersoonVoornaamHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVoornaam", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonVoornaamHistorie> persoonVoornaamHistorieSet =
            new LinkedHashSet<PersoonVoornaamHistorie>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    public PersoonVoornaam() {
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

    public HistorieStatus getPersoonVoornaamStatusHistorie() {
        return persoonVoornaamStatusHistorie;
    }

    public void setPersoonVoornaamStatusHistorie(final HistorieStatus persoonVoornaamStatusHistorie) {
        this.persoonVoornaamStatusHistorie = persoonVoornaamStatusHistorie;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    public Set<PersoonVoornaamHistorie> getPersoonVoornaamHistorieSet() {
        return persoonVoornaamHistorieSet;
    }

    /**
     * @param persoonVoornaamHistorie
     */
    public void addPersoonVoornaamHistorie(final PersoonVoornaamHistorie persoonVoornaamHistorie) {
        persoonVoornaamHistorie.setPersoonVoornaam(this);
        persoonVoornaamHistorieSet.add(persoonVoornaamHistorie);
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }
}

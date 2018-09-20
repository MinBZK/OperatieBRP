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
 * The persistent class for the persnation database table.
 * 
 */
@Entity
@Table(name = "persnation", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonNationaliteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSNATION_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSNATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSNATION_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "persnationstatushis", nullable = false, length = 1)
    private HistorieStatus persoonNationaliteitStatusHistorie = HistorieStatus.X;

    // bi-directional many-to-one association to PersoonNationaliteitHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonNationaliteit", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonNationaliteitHistorie> persoonNationaliteitHistorieSet =
            new LinkedHashSet<PersoonNationaliteitHistorie>(0);

    // bi-directional many-to-one association to Nationaliteit
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "nation", nullable = false)
    private Nationaliteit nationaliteit;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to RedenVerkrijgingNLNationaliteit
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnverk")
    private RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit;

    // bi-directional many-to-one association to RedenVerliesNLNationaliteit
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnverlies")
    private RedenVerliesNLNationaliteit redenVerliesNLNationaliteit;

    public PersoonNationaliteit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public HistorieStatus getPersoonNationaliteitStatusHistorie() {
        return persoonNationaliteitStatusHistorie;
    }

    public void setPersoonNationaliteitStatusHistorie(final HistorieStatus persoonNationaliteitStatusHistorie) {
        this.persoonNationaliteitStatusHistorie = persoonNationaliteitStatusHistorie;
    }

    public Set<PersoonNationaliteitHistorie> getPersoonNationaliteitHistorieSet() {
        return persoonNationaliteitHistorieSet;
    }

    /**
     * @param persoonNationaliteitHistorie
     */
    public void addPersoonNationaliteitHistorie(final PersoonNationaliteitHistorie persoonNationaliteitHistorie) {
        persoonNationaliteitHistorie.setPersoonNationaliteit(this);
        persoonNationaliteitHistorieSet.add(persoonNationaliteitHistorie);
    }

    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    public void setNationaliteit(final Nationaliteit nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return redenVerkrijgingNLNationaliteit;
    }

    /**
     * @param redenVerkrijgingNLNationaliteit
     */
    public void setRedenVerkrijgingNLNationaliteit(
            final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        this.redenVerkrijgingNLNationaliteit = redenVerkrijgingNLNationaliteit;
    }

    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return redenVerliesNLNationaliteit;
    }

    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        this.redenVerliesNLNationaliteit = redenVerliesNLNationaliteit;
    }
}

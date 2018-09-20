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
 * The persistent class for the persindicatie database table.
 * 
 */
@Entity
@Table(name = "persindicatie", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonIndicatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSINDICATIE_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSINDICATIE",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSINDICATIE_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "persindicatiestatushis", nullable = false, length = 1)
    private HistorieStatus persoonIndicatieStatusHistorie = HistorieStatus.X;

    private Boolean waarde;

    // bi-directional many-to-one association to PersoonIndicatieHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonIndicatie", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonIndicatieHistorie> persoonIndicatieHistorieSet =
            new LinkedHashSet<PersoonIndicatieHistorie>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers", nullable = false)
    private Persoon persoon;

    @Column(name = "srt", nullable = false)
    private Integer soortIndicatieId;

    public PersoonIndicatie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public HistorieStatus getPersoonIndicatieStatusHistorie() {
        return persoonIndicatieStatusHistorie;
    }

    public void setPersoonIndicatieStatusHistorie(final HistorieStatus persoonIndicatieStatusHistorie) {
        this.persoonIndicatieStatusHistorie = persoonIndicatieStatusHistorie;
    }

    public Boolean getWaarde() {
        return waarde;
    }

    public void setWaarde(final Boolean waarde) {
        this.waarde = waarde;
    }

    public Set<PersoonIndicatieHistorie> getPersoonIndicatieHistorieSet() {
        return persoonIndicatieHistorieSet;
    }

    /**
     * @param persoonIndicatieHistorie
     */
    public void addPersoonIndicatieHistorie(final PersoonIndicatieHistorie persoonIndicatieHistorie) {
        persoonIndicatieHistorie.setPersoonIndicatie(this);
        persoonIndicatieHistorieSet.add(persoonIndicatieHistorie);
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
    public SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.parseId(soortIndicatieId);
    }

    /**
     * @param soortIndicatie
     */
    public void setSoortIndicatie(final SoortIndicatie soortIndicatie) {
        if (soortIndicatie == null) {
            soortIndicatieId = null;
        } else {
            soortIndicatieId = soortIndicatie.getId();
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * The persistent class for the persverificatie database table.
 * 
 */
@Entity
@Table(name = "persverificatie", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonVerificatie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PERSVERIFICATIE_ID_GENERATOR", sequenceName = "KERN.SEQ_PERSVERIFICATIE",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSVERIFICATIE_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dat", precision = 8)
    private BigDecimal datum;

    @Column(name = "persverificatiestatushis", nullable = false, length = 1)
    private String persoonVerificatieStatusHistorie;

    // bi-directional many-to-one association to PersoonVerificatieHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "persoonVerificatie", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REMOVE })
    private final Set<PersoonVerificatieHistorie> persoonVerificatieHistorieSet =
            new LinkedHashSet<PersoonVerificatieHistorie>(0);

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "geverifieerde", nullable = false)
    private Persoon persoon;

    // bi-directional many-to-one association to SoortVerificatie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "srt")
    private SoortVerificatie soortVerificatie;

    public PersoonVerificatie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatum() {
        return datum;
    }

    public void setDatum(final BigDecimal datum) {
        this.datum = datum;
    }

    public String getPersoonVerificatieStatusHistorie() {
        return persoonVerificatieStatusHistorie;
    }

    public void setPersoonVerificatieStatusHistorie(final String persoonVerificatieStatusHistorie) {
        this.persoonVerificatieStatusHistorie = persoonVerificatieStatusHistorie;
    }

    public Set<PersoonVerificatieHistorie> getPersoonVerificatieHistorieSet() {
        return persoonVerificatieHistorieSet;
    }

    /**
     * @param persoonVerificatieHistorie
     */
    public void addPersoonVerificatieHistorie(final PersoonVerificatieHistorie persoonVerificatieHistorie) {
        persoonVerificatieHistorie.setPersoonVerificatie(this);
        persoonVerificatieHistorieSet.add(persoonVerificatieHistorie);
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public SoortVerificatie getSoortVerificatie() {
        return soortVerificatie;
    }

    public void setSoortVerificatie(final SoortVerificatie soortVerificatie) {
        this.soortVerificatie = soortVerificatie;
    }
}

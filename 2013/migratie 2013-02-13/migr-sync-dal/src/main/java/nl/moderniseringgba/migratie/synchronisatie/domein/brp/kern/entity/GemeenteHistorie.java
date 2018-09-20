/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the his_gem database table.
 * 
 */
@Entity
@Table(name = "his_gem", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class GemeenteHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_GEM_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_GEM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_GEM_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "gemcode", nullable = false, length = 4)
    private String gemeentecode;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieverval")
    private BRPActie actieVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieinh")
    private BRPActie actieInhoud;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderdeelvan")
    private Partij onderdeelVan;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "voortzettendegem")
    private Partij voortzettendeGemeente;

    // bi-directional many-to-one association to Partij
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij")
    private Partij partij;

    public GemeenteHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getGemeentecode() {
        return gemeentecode;
    }

    public void setGemeentecode(final String gemeentecode) {
        this.gemeentecode = gemeentecode;
    }

    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    public Timestamp getDatumTijdVerval() {
        return datumTijdVerval;
    }

    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    public BRPActie getActieVerval() {
        return actieVerval;
    }

    public void setActieVerval(final BRPActie actieVerval) {
        this.actieVerval = actieVerval;
    }

    public BRPActie getActieInhoud() {
        return actieInhoud;
    }

    public void setActieInhoud(final BRPActie actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    public Partij getOnderdeelVan() {
        return onderdeelVan;
    }

    public void setOnderdeelVan(final Partij onderdeelVan) {
        this.onderdeelVan = onderdeelVan;
    }

    public Partij getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    public void setVoortzettendeGemeente(final Partij voortzettendeGemeente) {
        this.voortzettendeGemeente = voortzettendeGemeente;
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the his_partij database table.
 * 
 */
@Entity
@Table(name = "his_partij", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PartijHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_PARTIJ_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_PARTIJ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_PARTIJ_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dataanv", nullable = false, precision = 8)
    private BigDecimal datumAanvang;

    @Column(name = "dateinde", precision = 8)
    private BigDecimal datumEinde;

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
    @JoinColumn(name = "partij")
    private Partij partij;

    // bi-directional many-to-one association to Sector
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "sector")
    private Sector sector;

    public PartijHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final BigDecimal datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public BigDecimal getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final BigDecimal datumEinde) {
        this.datumEinde = datumEinde;
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

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(final Sector sector) {
        this.sector = sector;
    }
}

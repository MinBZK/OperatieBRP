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
 * The persistent class for the his_persinschr database table.
 * 
 */
@Entity
@Table(name = "his_persinschr", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonInschrijvingHistorie implements Serializable, FormeleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_PERSINSCHR_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_PERSINSCHR",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_PERSINSCHR_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "datinschr", nullable = false, precision = 8)
    private BigDecimal datumInschrijving;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    @Column(name = "versienr", nullable = false)
    private Long versienummer;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieverval")
    private BRPActie actieVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieinh")
    private BRPActie actieInhoud;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "volgendepers")
    private Persoon volgendePersoon;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "vorigepers")
    private Persoon vorigePersoon;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "pers")
    private Persoon persoon;

    public PersoonInschrijvingHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatumInschrijving() {
        return datumInschrijving;
    }

    public void setDatumInschrijving(final BigDecimal datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }

    @Override
    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    @Override
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    @Override
    public Timestamp getDatumTijdVerval() {
        return datumTijdVerval;
    }

    @Override
    public void setDatumTijdVerval(final Timestamp datumTijdVerval) {
        this.datumTijdVerval = datumTijdVerval;
    }

    public Long getVersienummer() {
        return versienummer;
    }

    public void setVersienummer(final Long versienummer) {
        this.versienummer = versienummer;
    }

    @Override
    public BRPActie getActieVerval() {
        return actieVerval;
    }

    @Override
    public void setActieVerval(final BRPActie actieVerval) {
        this.actieVerval = actieVerval;
    }

    @Override
    public BRPActie getActieInhoud() {
        return actieInhoud;
    }

    @Override
    public void setActieInhoud(final BRPActie actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    public Persoon getVolgendePersoon() {
        return volgendePersoon;
    }

    public void setVolgendePersoon(final Persoon volgendePersoon) {
        this.volgendePersoon = volgendePersoon;
    }

    public Persoon getVorigePersoon() {
        return vorigePersoon;
    }

    public void setVorigePersoon(final Persoon vorigePersoon) {
        this.vorigePersoon = vorigePersoon;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }
}

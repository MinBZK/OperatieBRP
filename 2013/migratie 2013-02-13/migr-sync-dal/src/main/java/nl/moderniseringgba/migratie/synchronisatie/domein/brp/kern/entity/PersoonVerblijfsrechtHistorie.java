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
 * The persistent class for the his_persverblijfsr database table.
 * 
 */
@Entity
@Table(name = "his_persverblijfsr", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonVerblijfsrechtHistorie implements Serializable, FormeleHistorie, MaterieleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_PERSVERBLIJFSR_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_PERSVERBLIJFSR",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_PERSVERBLIJFSR_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dataanvaaneenslverblijfsr", precision = 8)
    private BigDecimal datumAanvangAaneensluitendVerblijfsrecht;

    @Column(name = "dataanvgel", precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dataanvverblijfsr", nullable = false, precision = 8)
    private BigDecimal datumAanvangVerblijfsrecht;

    @Column(name = "dateindegel", precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(name = "datvoorzeindeverblijfsr", precision = 8)
    private BigDecimal datumVoorzienEindeVerblijfsrecht;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    // bi-directional many-to-one association to BRPActie
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "actieaanpgel")
    private BRPActie actieAanpassingGeldigheid;

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
    @JoinColumn(name = "pers")
    private Persoon persoon;

    // bi-directional many-to-one association to Verblijfsrecht
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "verblijfsr", nullable = false)
    private Verblijfsrecht verblijfsrecht;

    public PersoonVerblijfsrechtHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatumAanvangAaneensluitendVerblijfsrecht() {
        return datumAanvangAaneensluitendVerblijfsrecht;
    }

    public void
            setDatumAanvangAaneensluitendVerblijfsrecht(final BigDecimal datumAanvangAaneensluitendVerblijfsrecht) {
        this.datumAanvangAaneensluitendVerblijfsrecht = datumAanvangAaneensluitendVerblijfsrecht;
    }

    @Override
    public BigDecimal getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public void setDatumAanvangGeldigheid(final BigDecimal datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    public BigDecimal getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    public void setDatumAanvangVerblijfsrecht(final BigDecimal datumAanvangVerblijfsrecht) {
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
    }

    @Override
    public BigDecimal getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public void setDatumEindeGeldigheid(final BigDecimal datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public BigDecimal getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    public void setDatumVoorzienEindeVerblijfsrecht(final BigDecimal datumVoorzienEindeVerblijfsrecht) {
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;
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

    @Override
    public BRPActie getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    @Override
    public void setActieAanpassingGeldigheid(final BRPActie actieAanpassingGeldigheid) {
        this.actieAanpassingGeldigheid = actieAanpassingGeldigheid;
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

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Verblijfsrecht getVerblijfsrecht() {
        return verblijfsrecht;
    }

    public void setVerblijfsrecht(final Verblijfsrecht verblijfsrecht) {
        this.verblijfsrecht = verblijfsrecht;
    }
}

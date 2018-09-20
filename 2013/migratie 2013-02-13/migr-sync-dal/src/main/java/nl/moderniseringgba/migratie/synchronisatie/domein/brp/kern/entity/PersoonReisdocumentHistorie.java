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
 * The persistent class for the his_persreisdoc database table.
 * 
 */
@Entity
@Table(name = "his_persreisdoc", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonReisdocumentHistorie implements Serializable, FormeleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_PERSREISDOC_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_PERSREISDOC",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_PERSREISDOC_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "datinhingvermissing", precision = 8)
    private BigDecimal datumInhoudingVermissing;

    @Column(name = "datingangdoc", precision = 8)
    private BigDecimal datumIngang;

    @Column(name = "datuitgifte", nullable = false, precision = 8)
    private BigDecimal datumUitgifte;

    @Column(name = "datvoorzeeindegel", nullable = false, precision = 8)
    private BigDecimal datumVoorzieneEindeGeldigheid;

    @Column(precision = 3)
    private BigDecimal lengteHouder;

    @Column(name = "nr", nullable = false, length = 9)
    private String nummer;

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

    // bi-directional many-to-one association to AutoriteitVanAfgifteReisdocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "autvanafgifte", nullable = false)
    private AutoriteitVanAfgifteReisdocument autoriteitVanAfgifteReisdocument;

    // bi-directional many-to-one association to PersoonReisdocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persreisdoc")
    private PersoonReisdocument persoonReisdocument;

    // bi-directional many-to-one association to RedenVervallenReisdocument
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdnvervallen")
    private RedenVervallenReisdocument redenVervallenReisdocument;

    public PersoonReisdocumentHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    public void setDatumInhoudingVermissing(final BigDecimal datumInhoudingVermissing) {
        this.datumInhoudingVermissing = datumInhoudingVermissing;
    }

    public BigDecimal getDatumIngang() {
        return datumIngang;
    }

    public void setDatumIngang(final BigDecimal datumIngang) {
        this.datumIngang = datumIngang;
    }

    public BigDecimal getDatumUitgifte() {
        return datumUitgifte;
    }

    public void setDatumUitgifte(final BigDecimal datumUitgifte) {
        this.datumUitgifte = datumUitgifte;
    }

    public BigDecimal getDatumVoorzieneEindeGeldigheid() {
        return datumVoorzieneEindeGeldigheid;
    }

    public void setDatumVoorzieneEindeGeldigheid(final BigDecimal datumVoorzieneEindeGeldigheid) {
        this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
    }

    public BigDecimal getLengteHouder() {
        return lengteHouder;
    }

    public void setLengteHouder(final BigDecimal lengteHouder) {
        this.lengteHouder = lengteHouder;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(final String nummer) {
        this.nummer = nummer;
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

    public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifteReisdocument() {
        return autoriteitVanAfgifteReisdocument;
    }

    public void setAutoriteitVanAfgifteReisdocument(
            final AutoriteitVanAfgifteReisdocument autoriteitVanAfgifteReisdocument) {
        this.autoriteitVanAfgifteReisdocument = autoriteitVanAfgifteReisdocument;
    }

    public PersoonReisdocument getPersoonReisdocument() {
        return persoonReisdocument;
    }

    public void setPersoonReisdocument(final PersoonReisdocument persoonReisdocument) {
        this.persoonReisdocument = persoonReisdocument;
    }

    public RedenVervallenReisdocument getRedenVervallenReisdocument() {
        return redenVervallenReisdocument;
    }

    public void setRedenVervallenReisdocument(final RedenVervallenReisdocument redenVervallenReisdocument) {
        this.redenVervallenReisdocument = redenVervallenReisdocument;
    }
}

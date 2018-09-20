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
 * The persistent class for the his_persaanschr database table.
 * 
 */
@Entity
@Table(name = "his_persaanschr", schema = "kern")
/*
 * CHECKSTYLE:OFF Deze class is gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc, daarnaast mogen
 * entities en de methoden van entities niet final zijn.
 */
public class PersoonAanschrijvingHistorie implements Serializable, MaterieleHistorie {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "HIS_PERSAANSCHR_ID_GENERATOR", sequenceName = "KERN.SEQ_HIS_PERSAANSCHR",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_PERSAANSCHR_ID_GENERATOR")
    @Column(nullable = false)
    private Long id;

    @Column(name = "dataanvgel", precision = 8)
    private BigDecimal datumAanvangGeldigheid;

    @Column(name = "dateindegel", precision = 8)
    private BigDecimal datumEindeGeldigheid;

    @Column(name = "geslnaamaanschr", nullable = false, length = 200)
    private String geslachtsnaamAanschrijving;

    @Column(name = "indaanschralgoritmischafgele", nullable = false)
    private Boolean indicatieAanschrijvingAlgoritmischAfgeleid;

    @Column(name = "indaanschrmetadellijketitels")
    private Boolean indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;

    @Column(name = "scheidingstekenaanschr", length = 1)
    private Character scheidingstekenAanschrijving;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tsverval")
    private Timestamp datumTijdVerval;

    @Column(name = "voornamenaanschr", length = 200)
    private String voornamenAanschrijving;

    @Column(name = "voorvoegselaanschr", length = 10)
    private String voorvoegselAanschrijving;

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

    @Column(name = "predikaataanschr")
    private Integer predikaatId;

    @Column(name = "gebrgeslnaamegp")
    private Integer wijzeGebruikGeslachtsnaamId;

    public PersoonAanschrijvingHistorie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public BigDecimal getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public void setDatumAanvangGeldigheid(final BigDecimal datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    @Override
    public BigDecimal getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public void setDatumEindeGeldigheid(final BigDecimal datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public String getGeslachtsnaamAanschrijving() {
        return geslachtsnaamAanschrijving;
    }

    public void setGeslachtsnaamAanschrijving(final String geslachtsnaamAanschrijving) {
        this.geslachtsnaamAanschrijving = geslachtsnaamAanschrijving;
    }

    public Boolean getIndicatieAanschrijvingAlgoritmischAfgeleid() {
        return indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    public void
            setIndicatieAanschrijvingAlgoritmischAfgeleid(final Boolean indicatieAanschrijvingAlgoritmischAfgeleid) {
        this.indicatieAanschrijvingAlgoritmischAfgeleid = indicatieAanschrijvingAlgoritmischAfgeleid;
    }

    public Boolean getIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten() {
        return indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;
    }

    public void setIndicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten(
            final Boolean indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten) {
        this.indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten =
                indicatieAanschrijvenMetAdellijkeTitelsEnOfPredikaten;
    }

    public Character getScheidingstekenAanschrijving() {
        return scheidingstekenAanschrijving;
    }

    public void setScheidingstekenAanschrijving(final Character scheidingstekenAanschrijving) {
        this.scheidingstekenAanschrijving = scheidingstekenAanschrijving;
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

    public String getVoornamenAanschrijving() {
        return voornamenAanschrijving;
    }

    public void setVoornamenAanschrijving(final String voornamenAanschrijving) {
        this.voornamenAanschrijving = voornamenAanschrijving;
    }

    public String getVoorvoegselAanschrijving() {
        return voorvoegselAanschrijving;
    }

    public void setVoorvoegselAanschrijving(final String voorvoegselAanschrijving) {
        this.voorvoegselAanschrijving = voorvoegselAanschrijving;
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

    /**
     * @return
     */
    public WijzeGebruikGeslachtsnaam getWijzeGebruikGeslachtsnaam() {
        return WijzeGebruikGeslachtsnaam.parseId(wijzeGebruikGeslachtsnaamId);
    }

    /**
     * @param wijzeGebruikGeslachtsnaam
     */
    public void setWijzeGebruikGeslachtsnaam(final WijzeGebruikGeslachtsnaam wijzeGebruikGeslachtsnaam) {
        if (wijzeGebruikGeslachtsnaam == null) {
            wijzeGebruikGeslachtsnaamId = null;
        } else {
            wijzeGebruikGeslachtsnaamId = wijzeGebruikGeslachtsnaam.getId();
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;

/**
 * The persistent class for the his_dienstbundel database table.
 */
@Entity
@Table(name = "his_dienstbundel", schema = "autaut")
@NamedQuery(name = "DienstbundelHistorie.findAll", query = "SELECT d FROM DienstbundelHistorie d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstbundel_id_generator", sequenceName = "autaut.seq_his_dienstbundel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstbundel_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indnaderepopbeperkingvolconv")
    private Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd;

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    @Column(name = "toelichting")
    private String toelichting;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    /**
     * JPA no-args constructor.
     */
    DienstbundelHistorie() {
    }

    /**
     * Maakt een nieuw DienstbundelHistorie object.
     *
     * @param dienstbundel
     *            dienstbundel
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     * @param naam
     *            naam
     * @param datumIngang
     *            datumIngang
     */
    public DienstbundelHistorie(final Dienstbundel dienstbundel, final Timestamp datumTijdRegistratie, final String naam, final int datumIngang) {
        setDienstbundel(dienstbundel);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setNaam(naam);
        setDatumIngang(datumIngang);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datumEinde.
     *
     * @return datumEinde
     */
    public Integer getDatumEinde() {
        return this.datumEinde;
    }

    /**
     * Zet de waarde van datumEinde.
     *
     * @param datumEinde
     *            datumEinde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang
     */
    public int getDatumIngang() {
        return this.datumIngang;
    }

    /**
     * Zet de waarde van datumIngang.
     *
     * @param datumIngang
     *            datumIngang
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public Boolean getIndicatieGeblokkeerd() {
        return this.indicatieGeblokkeerd;
    }

    /**
     * Zet de waarde van indicatieGeblokkeerd.
     *
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van indicatieNaderePopulatiebeperkingVolledigGeconverteerd.
     *
     * @return indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     */
    public Boolean getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet de waarde van indicatieNaderePopulatiebeperkingVolledigGeconverteerd.
     *
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     *            indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     */
    public void setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(final Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd) {
        if (Boolean.TRUE.equals(indicatieNaderePopulatiebeperkingVolledigGeconverteerd)) {
            throw new IllegalArgumentException("indicatieNaderePopulatiebeperkingVolledigGeconverteerd moet null of FALSE zijn");
        }
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return this.naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        if (naam == null || "".equals(naam)) {
            throw new IllegalArgumentException("naam moet gevuld zijn en minimaal lengte 1 hebben.");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van naderePopulatiebeperking.
     *
     * @return naderePopulatiebeperking
     */
    public String getNaderePopulatiebeperking() {
        return this.naderePopulatiebeperking;
    }

    /**
     * Zet de waarde van naderePopulatiebeperking.
     *
     * @param naderePopulatiebeperking
     *            naderePopulatiebeperking
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van toelichting.
     *
     * @return toelichting
     */
    public String getToelichting() {
        return this.toelichting;
    }

    /**
     * Zet de waarde van toelichting.
     *
     * @param toelichting
     *            toelichting
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van dienstbundel.
     *
     * @return dienstbundel
     */
    public Dienstbundel getDienstbundel() {
        return this.dienstbundel;
    }

    /**
     * Zet de waarde van dienstbundel.
     *
     * @param dienstbundel
     *            dienstbundel
     */
    public void setDienstbundel(final Dienstbundel dienstbundel) {
        ValidationUtils.controleerOpNullWaarden("dienstbundel mag niet null zijn", dienstbundel);
        this.dienstbundel = dienstbundel;
    }
}

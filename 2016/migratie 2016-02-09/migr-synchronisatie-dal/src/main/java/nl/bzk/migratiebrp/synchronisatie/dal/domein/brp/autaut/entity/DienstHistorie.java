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
 * The persistent class for the his_dienst database table.
 *
 */
@Entity
@Table(name = "his_dienst", schema = "autaut")
@NamedQuery(name = "DienstHistorie.findAll", query = "SELECT d FROM DienstHistorie d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienst_id_generator", sequenceName = "autaut.seq_his_dienst", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienst_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    // bi-directional many-to-one association to Dienst
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstHistorie() {
    }

    /**
     * Maakt een nieuw DienstHistorie object.
     *
     * @param dienst
     *            dienst
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     * @param datumIngang
     *            datumIngang
     */
    public DienstHistorie(final Dienst dienst, final Timestamp datumTijdRegistratie, final int datumIngang) {
        setDienst(dienst);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setDatumIngang(datumIngang);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    @Override
    public Integer getId() {
        return id;
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
        return datumEinde;
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
        return datumIngang;
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
        return indicatieGeblokkeerd;
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
     * Geef de waarde van dienst.
     *
     * @return dienst
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Zet de waarde van dienst.
     *
     * @param dienst
     *            dienst
     */
    public void setDienst(final Dienst dienst) {
        ValidationUtils.controleerOpNullWaarden("dienst mag niet null zijn", dienst);
        this.dienst = dienst;
    }

}

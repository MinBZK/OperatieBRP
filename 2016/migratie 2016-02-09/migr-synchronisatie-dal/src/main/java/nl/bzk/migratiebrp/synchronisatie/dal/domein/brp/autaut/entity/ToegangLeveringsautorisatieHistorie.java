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
 * The persistent class for the his_toeganglevsautorisatie database table.
 * 
 */
@Entity
@Table(name = "his_toeganglevsautorisatie", schema = "autaut")
@NamedQuery(name = "ToegangLeveringsautorisatieHistorie.findAll", query = "SELECT t FROM ToegangLeveringsautorisatieHistorie t")
@SuppressWarnings("checkstyle:designforextension")
public class ToegangLeveringsautorisatieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_toeganglevsautorisatie_id_generator", sequenceName = "autaut.seq_his_toeganglevsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_toeganglevsautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "afleverpunt")
    private String afleverpunt;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    // bi-directional many-to-one association to ToegangLeveringsAutorisatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "toeganglevsautorisatie", nullable = false)
    private ToegangLeveringsAutorisatie toegangLeveringsautorisatie;

    /**
     * JPA no-args constructor.
     */
    ToegangLeveringsautorisatieHistorie() {
    }

    /**
     * Maakt een nieuw ToegangLeveringsautorisatieHistorie object.
     *
     * @param toegangLeveringsautorisatie
     *            toegangLeveringsautorisatie
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     * @param datumIngang
     *            datumIngang
     */
    public ToegangLeveringsautorisatieHistorie(
        final ToegangLeveringsAutorisatie toegangLeveringsautorisatie,
        final Timestamp datumTijdRegistratie,
        final int datumIngang)
    {
        setToegangLeveringsautorisatie(toegangLeveringsautorisatie);
        setDatumTijdRegistratie(datumTijdRegistratie);
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
     * Geef de waarde van afleverpunt.
     *
     * @return afleverpunt
     */
    public String getAfleverpunt() {
        return this.afleverpunt;
    }

    /**
     * Zet de waarde van afleverpunt.
     *
     * @param afleverpunt
     *            afleverpunt
     */
    public void setAfleverpunt(final String afleverpunt) {
        this.afleverpunt = afleverpunt;
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
     * Geef de waarde van toegangLeveringsautorisatie.
     *
     * @return toegangLeveringsautorisatie
     */
    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return this.toegangLeveringsautorisatie;
    }

    /**
     * Zet de waarde van toegangLeveringsautorisatie.
     *
     * @param toegangLeveringsautorisatie
     *            toegangLeveringsautorisatie
     */
    public void setToegangLeveringsautorisatie(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("toegangLeveringsautorisatie mag niet null zijn", toegangLeveringsautorisatie);
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
    }
}

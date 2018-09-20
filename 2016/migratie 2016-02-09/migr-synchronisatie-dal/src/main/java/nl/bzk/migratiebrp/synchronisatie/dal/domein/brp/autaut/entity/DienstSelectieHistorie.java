/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;

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
 * The persistent class for the his_dienstselectie database table.
 *
 */
@Entity
@Table(name = "his_dienstselectie", schema = "autaut")
@NamedQuery(name = "DienstSelectieHistorie.findAll", query = "SELECT d FROM DienstSelectieHistorie d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstSelectieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstselectie_id_generator", sequenceName = "autaut.seq_his_dienstselectie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstselectie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "eersteselectiedat")
    private Integer eersteSelectieDatum;

    @Column(name = "selectieperiodeinmaanden")
    private Short selectiePeriodeInMaanden;

    // bi-directional many-to-one association to Dienst
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstSelectieHistorie() {
    }

    /**
     * Maakt een nieuw DienstSelectieHistorie object.
     *
     * @param dienst
     *            dienst
     */
    public DienstSelectieHistorie(final Dienst dienst) {
        setDienst(dienst);
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
     * Geef de waarde van eersteSelectieDatum.
     *
     * @return eersteSelectieDatum
     */
    public Integer getEersteSelectieDatum() {
        return eersteSelectieDatum;
    }

    /**
     * Zet de waarde van eersteSelectieDatum.
     *
     * @param eersteSelectieDatum
     *            eersteSelectieDatum
     */
    public void setEersteSelectieDatum(final Integer eersteSelectieDatum) {
        this.eersteSelectieDatum = eersteSelectieDatum;
    }

    /**
     * Geef de waarde van selectiePeriodeInMaanden.
     *
     * @return selectiePeriodeInMaanden
     */
    public Short getSelectiePeriodeInMaanden() {
        return selectiePeriodeInMaanden;
    }

    /**
     * Zet de waarde van selectiePeriodeInMaanden.
     *
     * @param selectiePeriodeInMaanden
     *            selectiePeriodeInMaanden
     */
    public void setSelectiePeriodeInMaanden(final Short selectiePeriodeInMaanden) {
        this.selectiePeriodeInMaanden = selectiePeriodeInMaanden;
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

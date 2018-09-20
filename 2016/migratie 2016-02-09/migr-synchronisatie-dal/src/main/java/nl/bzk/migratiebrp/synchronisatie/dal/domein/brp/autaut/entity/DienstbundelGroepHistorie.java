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
 * The persistent class for the his_dienstbundelgroep database table.
 *
 */
@Entity
@Table(name = "his_dienstbundelgroep", schema = "autaut")
@NamedQuery(name = "DienstbundelGroepHistorie.findAll", query = "SELECT d FROM DienstbundelGroepHistorie d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelGroepHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstbundelgroep_id_generator", sequenceName = "autaut.seq_his_dienstbundelgroep", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstbundelgroep_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "indformelehistorie", nullable = false)
    private boolean indicatieFormeleHistorie;

    @Column(name = "indmaterielehistorie", nullable = false)
    private boolean indicatieMaterieleHistorie;

    @Column(name = "indverantwoording", nullable = false)
    private boolean indicatieVerantwoording;

    // bi-directional many-to-one association to DienstbundelGroep
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundelgroep")
    private DienstbundelGroep dienstbundelGroep;

    /**
     * JPA no-args constructor.
     */
    DienstbundelGroepHistorie() {
    }

    /**
     * Maakt een nieuw DienstbundelGroepHistorie object.
     *
     * @param dienstbundelGroep
     *            dienstbundelGroep
     */
    public DienstbundelGroepHistorie(final DienstbundelGroep dienstbundelGroep) {
        setDienstbundelGroep(dienstbundelGroep);
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
     * Geef de waarde van indicatieFormeleHistorie.
     *
     * @return indicatieFormeleHistorie
     */
    public boolean getIndicatieFormeleHistorie() {
        return indicatieFormeleHistorie;
    }

    /**
     * Zet de waarde van indicatieFormeleHistorie.
     *
     * @param indicatieFormeleHistorie
     *            indicatieFormeleHistorie
     */
    public void setIndicatieFormeleHistorie(final boolean indicatieFormeleHistorie) {
        this.indicatieFormeleHistorie = indicatieFormeleHistorie;
    }

    /**
     * Geef de waarde van indicatieMaterieleHistorie.
     *
     * @return indicatieMaterieleHistorie
     */
    public boolean getIndicatieMaterieleHistorie() {
        return indicatieMaterieleHistorie;
    }

    /**
     * Zet de waarde van indicatieMaterieleHistorie.
     *
     * @param indicatieMaterieleHistorie
     *            indicatieMaterieleHistorie
     */
    public void setIndicatieMaterieleHistorie(final boolean indicatieMaterieleHistorie) {
        this.indicatieMaterieleHistorie = indicatieMaterieleHistorie;
    }

    /**
     * Geef de waarde van indicatieVerantwoording.
     *
     * @return indicatieVerantwoording
     */
    public boolean getIndicatieVerantwoording() {
        return indicatieVerantwoording;
    }

    /**
     * Zet de waarde van indicatieVerantwoording.
     *
     * @param indicatieVerantwoording
     *            indicatieVerantwoording
     */
    public void setIndicatieVerantwoording(final boolean indicatieVerantwoording) {
        this.indicatieVerantwoording = indicatieVerantwoording;
    }

    /**
     * Geef de waarde van dienstbundelGroep.
     *
     * @return dienstbundelGroep
     */
    public DienstbundelGroep getDienstbundelGroep() {
        return dienstbundelGroep;
    }

    /**
     * Zet de waarde van dienstbundelGroep.
     *
     * @param dienstbundelGroep
     *            dienstbundelGroep
     */
    public void setDienstbundelGroep(final DienstbundelGroep dienstbundelGroep) {
        ValidationUtils.controleerOpNullWaarden("dienstbundelGroep mag niet null zijn", dienstbundelGroep);
        this.dienstbundelGroep = dienstbundelGroep;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
 * The persistent class for the his_dienstbundelgroepattr database table.
 *
 */
@Entity
@Table(name = "his_dienstbundelgroepattr", schema = "autaut")
@NamedQuery(name = "DienstbundelGroepAttribuutHistorie.findAll", query = "SELECT h FROM DienstbundelGroepAttribuutHistorie h")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelGroepAttribuutHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstbundelgroepattr_id_generator", sequenceName = "autaut.seq_his_dienstbundelgroepattr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstbundelgroepattr_id_generator")
    private Integer id;

    // bi-directional many-to-one association to DienstbundelGroepAttribuut
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundelgroepattr")
    private DienstbundelGroepAttribuut dienstbundelGroepAttribuut;

    /**
     * JPA no-args constructor.
     */
    DienstbundelGroepAttribuutHistorie() {
    }

    /**
     * Maakt een nieuw DienstbundelGroepAttribuutHistorie object.
     *
     * @param dienstbundelGroepAttribuut
     *            dienstbundelGroepAttribuut
     */
    public DienstbundelGroepAttribuutHistorie(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut) {
        setDienstbundelGroepAttribuut(dienstbundelGroepAttribuut);
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
     * Geef de waarde van dienstbundelGroepAttribuut.
     *
     * @return dienstbundelGroepAttribuut
     */
    public DienstbundelGroepAttribuut getDienstbundelGroepAttribuut() {
        return dienstbundelGroepAttribuut;
    }

    /**
     * Zet de waarde van dienstbundelGroepAttribuut.
     *
     * @param dienstbundelGroepAttribuut
     *            dienstbundelGroepAttribuut
     */
    public void setDienstbundelGroepAttribuut(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut) {
        ValidationUtils.controleerOpNullWaarden("dienstbundelGroepAttribuut mag niet null zijn", dienstbundelGroepAttribuut);
        this.dienstbundelGroepAttribuut = dienstbundelGroepAttribuut;
    }
}

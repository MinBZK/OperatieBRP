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
 * The persistent class for the his_dienstbundello3rubriek database table.
 *
 */
@Entity
@Table(name = "his_dienstbundello3rubriek", schema = "autaut")
@NamedQuery(name = "DienstbundelLo3RubriekHistorie.findAll", query = "SELECT d FROM DienstbundelLo3RubriekHistorie d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelLo3RubriekHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstbundello3rubriek_id_generator", sequenceName = "autaut.seq_his_dienstbundello3rubriek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstbundello3rubriek_id_generator")
    private Integer id;

    // bi-directional many-to-one association to DienstbundelLo3Rubriek
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundello3rubriek", nullable = false)
    private DienstbundelLo3Rubriek dienstbundelLo3Rubriek;

    /**
     * JPA no-args constructor.
     */
    DienstbundelLo3RubriekHistorie() {
    }

    /**
     * Maakt een nieuw DienstbundelLo3RubriekHistorie object.
     *
     * @param dienstbundelLo3Rubriek
     *            dienstbundelLo3Rubriek
     */
    public DienstbundelLo3RubriekHistorie(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        setDienstbundelLo3Rubriek(dienstbundelLo3Rubriek);
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
     * Geef de waarde van dienstbundelLo3Rubriek.
     *
     * @return dienstbundelLo3Rubriek
     */
    public DienstbundelLo3Rubriek getDienstbundelLo3Rubriek() {
        return dienstbundelLo3Rubriek;
    }

    /**
     * Zet de waarde van dienstbundelLo3Rubriek.
     *
     * @param dienstbundelLo3Rubriek
     *            dienstbundelLo3Rubriek
     */
    public void setDienstbundelLo3Rubriek(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        ValidationUtils.controleerOpNullWaarden("dienstbundelLo3Rubriek mag niet null zijn", dienstbundelLo3Rubriek);
        this.dienstbundelLo3Rubriek = dienstbundelLo3Rubriek;
    }
}

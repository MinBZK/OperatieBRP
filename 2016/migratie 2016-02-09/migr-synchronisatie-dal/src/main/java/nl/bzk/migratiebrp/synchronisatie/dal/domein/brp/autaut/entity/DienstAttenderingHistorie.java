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
 * The persistent class for the his_dienstattendering database table.
 *
 */
@Entity
@Table(name = "his_dienstattendering", schema = "autaut")
@NamedQuery(name = "DienstAttenderingHistorie.findAll", query = "SELECT d FROM DienstAttenderingHistorie d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstAttenderingHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstattendering_id_generator", sequenceName = "autaut.seq_his_dienstattendering", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstattendering_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "attenderingscriterium")
    private String attenderingscriterium;

    // bi-directional many-to-one association to Dienst
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstAttenderingHistorie() {
    }

    /**
     * Maakt een nieuw DienstAttenderingHistorie object.
     *
     * @param dienst
     *            dienst
     */
    public DienstAttenderingHistorie(final Dienst dienst) {
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
     * Geef de waarde van attenderingscriterium.
     *
     * @return attenderingscriterium
     */
    public String getAttenderingscriterium() {
        return attenderingscriterium;
    }

    /**
     * Zet de waarde van attenderingscriterium.
     *
     * @param attenderingscriterium
     *            attenderingscriterium
     */
    public void setAttenderingscriterium(final String attenderingscriterium) {
        this.attenderingscriterium = attenderingscriterium;
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

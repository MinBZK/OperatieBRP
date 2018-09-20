/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractDeltaEntiteit;

/**
 * The persistent class for the ConvLO3Rubriek database table.
 *
 */
@Entity
@Table(name = "convlo3rubriek", schema = "conv")
@SuppressWarnings("checkstyle:designforextension")
public class Lo3Rubriek extends AbstractDeltaEntiteit implements DynamischeStamtabel, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "naam", insertable = false, updatable = false, length = 2, nullable = false, unique = true)
    private String naam;

    /**
     * JPA default constructor.
     */
    protected Lo3Rubriek() {
    }

    /**
     * Maak een nieuwe lo3 rubriek.
     *
     * @param naam
     *            naam
     */
    public Lo3Rubriek(final String naam) {
        setNaam(naam);
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
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }
}

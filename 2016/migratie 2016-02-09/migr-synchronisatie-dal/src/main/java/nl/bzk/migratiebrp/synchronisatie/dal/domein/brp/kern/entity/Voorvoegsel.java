/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;

/**
 * The persistent class for the voorvoegsel database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "voorvoegsel", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"voorvoegsel", "scheidingsteken" }))
@Cacheable
@SuppressWarnings("checkstyle:designforextension")
public class Voorvoegsel extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;
    private static final String VOORVOEGSEL_MAG_NIET_NULL_ZIJN = "voorvoegsel mag niet null zijn";
    private static final String VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN = "voorvoegsel mag geen lege string zijn";

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Short id;

    @Column(insertable = false, updatable = false, nullable = false)
    private char scheidingsteken;

    @Column(insertable = false, updatable = false, nullable = false)
    private String voorvoegsel;

    /**
     * JPA default constructor.
     */
    protected Voorvoegsel() {
    }

    /**
     * Maak een nieuwe voorvoegsel.
     *
     * @param scheidingsteken
     *            scheidingsteken
     * @param voorvoegsel
     *            voorvoegsel
     */
    public Voorvoegsel(final char scheidingsteken, final String voorvoegsel) {
        this.scheidingsteken = scheidingsteken;
        ValidationUtils.controleerOpNullWaarden(VOORVOEGSEL_MAG_NIET_NULL_ZIJN, voorvoegsel);
        Validatie.controleerOpLegeWaarden(VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public char getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarde van scheidingsteken.
     *
     * @param scheidingsteken
     *            scheidingsteken
     */
    public void setScheidingsteken(final char scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van voorvoegsel.
     *
     * @return voorvoegsel
     */
    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Zet de waarde van voorvoegsel.
     *
     * @param voorvoegsel
     *            voorvoegsel
     */
    public void setVoorvoegsel(final String voorvoegsel) {
        ValidationUtils.controleerOpNullWaarden(VOORVOEGSEL_MAG_NIET_NULL_ZIJN, voorvoegsel);
        Validatie.controleerOpLegeWaarden(VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

}

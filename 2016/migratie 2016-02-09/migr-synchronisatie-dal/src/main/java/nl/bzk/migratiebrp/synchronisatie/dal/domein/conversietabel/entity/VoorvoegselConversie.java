/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the soortnlreisdocument database table.
 * 
 */
@Entity
@Table(name = "convvoorvoegsel", schema = "conv")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class VoorvoegselConversie implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LO3_VOORVOEGSEL_MAG_NIET_NULL_ZIJN = "lo3Voorvoegsel mag niet null zijn";
    private static final String LO3_VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN = "lo3Voorvoegsel mag geen lege string zijn";
    private static final String VOORVOEGSEL_MAG_NIET_NULL_ZIJN = "voorvoegsel mag niet null zijn";
    private static final String VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN = "voorvoegsel mag geen lege string zijn";

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr0231voorvoegsel", insertable = false, updatable = false, length = 10, nullable = false)
    private String lo3Voorvoegsel;

    @Column(name = "voorvoegsel", insertable = false, updatable = false, length = 80, nullable = false)
    private String voorvoegsel;

    @Column(name = "scheidingsteken", insertable = false, updatable = false, length = 1, nullable = false)
    private char scheidingsteken;

    /**
     * JPA default constructor.
     */
    protected VoorvoegselConversie() {
    }

    /**
     * Maak een nieuwe voorvoegsel conversie.
     *
     * @param lo3Voorvoegsel
     *            lo3 voorvoegsel
     * @param voorvoegsel
     *            voorvoegsel
     * @param scheidingsteken
     *            scheidingsteken
     */
    public VoorvoegselConversie(final String lo3Voorvoegsel, final String voorvoegsel, final char scheidingsteken) {
        ValidationUtils.controleerOpNullWaarden(LO3_VOORVOEGSEL_MAG_NIET_NULL_ZIJN, lo3Voorvoegsel);
        Validatie.controleerOpLegeWaarden(LO3_VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, lo3Voorvoegsel);
        this.lo3Voorvoegsel = lo3Voorvoegsel;
        ValidationUtils.controleerOpNullWaarden(VOORVOEGSEL_MAG_NIET_NULL_ZIJN, voorvoegsel);
        Validatie.controleerOpLegeWaarden(VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, voorvoegsel);
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
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
     * Geef de waarde van lo3 voorvoegsel.
     *
     * @return lo3 voorvoegsel
     */
    public String getLo3Voorvoegsel() {
        return lo3Voorvoegsel;
    }

    /**
     * Zet de waarde van lo3 voorvoegsel.
     *
     * @param lo3Voorvoegsel
     *            lo3 voorvoegsel
     */
    public void setLo3Voorvoegsel(final String lo3Voorvoegsel) {
        ValidationUtils.controleerOpNullWaarden(LO3_VOORVOEGSEL_MAG_NIET_NULL_ZIJN, lo3Voorvoegsel);
        Validatie.controleerOpLegeWaarden(LO3_VOORVOEGSEL_MAG_GEEN_LEGE_STRING_ZIJN, lo3Voorvoegsel);
        this.lo3Voorvoegsel = lo3Voorvoegsel;
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
}

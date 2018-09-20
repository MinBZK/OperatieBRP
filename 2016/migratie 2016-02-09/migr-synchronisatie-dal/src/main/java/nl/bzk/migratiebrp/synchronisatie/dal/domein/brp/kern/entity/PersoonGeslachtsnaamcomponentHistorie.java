/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_persgeslnaamcomp database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persgeslnaamcomp", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"persgeslnaamcomp", "tsreg", "dataanvgel" }))
@SuppressWarnings("checkstyle:designforextension")
public class PersoonGeslachtsnaamcomponentHistorie extends AbstractMaterieleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persgeslnaamcomp_id_generator", sequenceName = "kern.seq_his_persgeslnaamcomp", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persgeslnaamcomp_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "stam", nullable = false, length = 200)
    private String stam;

    @Column(length = 1)
    private Character scheidingsteken;

    @Column(length = 10)
    private String voorvoegsel;

    @Column(name = "adellijketitel")
    private Short adellijkeTitelId;

    // bi-directional many-to-one association to PersoonGeslachtsnaamcomponent
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "persgeslnaamcomp", nullable = false)
    private PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent;

    @Column(name = "predicaat")
    private Short predicaatId;

    /**
     * JPA default constructor.
     */
    protected PersoonGeslachtsnaamcomponentHistorie() {
    }

    /**
     * Maak een nieuwe persoon geslachtsnaamcomponent historie.
     *
     * @param persoonGeslachtsnaamcomponent
     *            persoon geslachtsnaamcomponent
     * @param stam
     *            stam
     */
    public PersoonGeslachtsnaamcomponentHistorie(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent, final String stam) {
        setPersoonGeslachtsnaamcomponent(persoonGeslachtsnaamcomponent);
        setStam(stam);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van stam.
     *
     * @return stam
     */
    public String getStam() {
        return stam;
    }

    /**
     * Zet de waarde van stam.
     *
     * @param stam
     *            stam
     */
    public void setStam(final String stam) {
        ValidationUtils.controleerOpNullWaarden("stam mag niet null zijn", stam);
        Validatie.controleerOpLegeWaarden("stam mag geen lege string zijn", stam);
        this.stam = stam;
    }

    /**
     * Geef de waarde van scheidingsteken.
     *
     * @return scheidingsteken
     */
    public Character getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet de waarde van scheidingsteken.
     *
     * @param scheidingsteken
     *            scheidingsteken
     */
    public void setScheidingsteken(final Character scheidingsteken) {
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
        Validatie.controleerOpLegeWaarden("voorvoegsel mag geen lege string zijn", voorvoegsel);
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarde van adellijke titel.
     *
     * @param adellijkeTitel
     *            adellijke titel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van persoon geslachtsnaamcomponent.
     *
     * @return persoon geslachtsnaamcomponent
     */
    public PersoonGeslachtsnaamcomponent getPersoonGeslachtsnaamcomponent() {
        return persoonGeslachtsnaamcomponent;
    }

    /**
     * Zet de waarde van persoon geslachtsnaamcomponent.
     *
     * @param persoonGeslachtsnaamcomponent
     *            persoon geslachtsnaamcomponent
     */
    public void setPersoonGeslachtsnaamcomponent(final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent) {
        ValidationUtils.controleerOpNullWaarden("persoonGeslachtsnaamcomponent mag niet null zijn", persoonGeslachtsnaamcomponent);
        this.persoonGeslachtsnaamcomponent = persoonGeslachtsnaamcomponent;
    }

    /**
     * Geef de waarde van predicaat.
     *
     * @return predicaat
     */
    public Predicaat getPredicaat() {
        return Predicaat.parseId(predicaatId);
    }

    /**
     * Zet de waarde van predicaat.
     *
     * @param predikaat
     *            predicaat
     */
    public void setPredicaat(final Predicaat predikaat) {
        if (predikaat == null) {
            predicaatId = null;
        } else {
            predicaatId = predikaat.getId();
        }
    }
}

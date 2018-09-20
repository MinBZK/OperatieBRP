/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.Lo3Rubriek;

/**
 * The persistent class for the dienstbundello3rubriek database table.
 * 
 */
@Entity
@Table(name = "dienstbundello3rubriek", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienstbundel", "rubr" }))
@NamedQuery(name = "DienstbundelLo3Rubriek.findAll", query = "SELECT d FROM DienstbundelLo3Rubriek d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelLo3Rubriek implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundello3rubriek_id_generator", sequenceName = "autaut.seq_dienstbundello3rubriek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundello3rubriek_id_generator")
    @Column(updatable = false)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rubr", nullable = false)
    private Lo3Rubriek lo3Rubriek;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    // bi-directional many-to-one association to DienstbundelLo3RubriekHistorie
    @OneToMany(mappedBy = "dienstbundelLo3Rubriek", cascade = CascadeType.ALL)
    private Set<DienstbundelLo3RubriekHistorie> dienstbundelLo3RubriekHistorieSet;

    /**
     * JPA no-args constructor.
     */
    DienstbundelLo3Rubriek() {
    }

    /**
     * Maakt een nieuw DienstbundelLo3Rubriek object.
     *
     * @param dienstbundel
     *            dienstbundel
     * @param lo3Rubriek
     *            lo3rubriek
     */
    public DienstbundelLo3Rubriek(final Dienstbundel dienstbundel, final Lo3Rubriek lo3Rubriek) {
        setDienstbundel(dienstbundel);
        setLo3Rubriek(lo3Rubriek);
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
     * Geef de waarde van dienstbundel.
     *
     * @return dienstbundel
     */
    public Dienstbundel getDienstbundel() {
        return this.dienstbundel;
    }

    /**
     * Zet de waarde van dienstbundel.
     *
     * @param dienstbundel
     *            dienstbundel
     */
    public void setDienstbundel(final Dienstbundel dienstbundel) {
        ValidationUtils.controleerOpNullWaarden("dienstbundel mag niet null zijn", dienstbundel);
        this.dienstbundel = dienstbundel;
    }

    /**
     * Geef de waarde van dienstbundelLo3RubriekHistorieSet.
     *
     * @return dienstbundelLo3RubriekHistorieSet
     */
    public Set<DienstbundelLo3RubriekHistorie> getDienstbundelLo3RubriekHistorieSet() {
        return this.dienstbundelLo3RubriekHistorieSet;
    }

    /**
     * Zet de waarde van dienstbundelLo3RubriekHistorieSet.
     *
     * @param dienstbundelLo3RubriekHistorieSet
     *            dienstbundelLo3RubriekHistorieSet
     */
    public void setDienstbundelLo3RubriekHistorieSet(final Set<DienstbundelLo3RubriekHistorie> dienstbundelLo3RubriekHistorieSet) {
        this.dienstbundelLo3RubriekHistorieSet = dienstbundelLo3RubriekHistorieSet;
    }

    /**
     * Voeg de waarde van dienstbundelLo3RubriekHistorie toe aan dienstbundelLo3RubriekHistorieSet.
     *
     * @param dienstbundelLo3RubriekHistorie
     *            dienstbundelLo3RubriekHistorie
     */
    public void addDienstbundelLo3RubriekHistorieSet(final DienstbundelLo3RubriekHistorie dienstbundelLo3RubriekHistorie) {
        getDienstbundelLo3RubriekHistorieSet().add(dienstbundelLo3RubriekHistorie);
        dienstbundelLo3RubriekHistorie.setDienstbundelLo3Rubriek(this);
    }

    /**
     * Verwijderd een dienstbundelLo3RubriekHistorie uit de dienstbundelLo3RubriekHistorieSet.
     *
     * @param dienstbundelLo3RubriekHistorie
     *            dienstbundelLo3RubriekHistorie
     * @return true als dienstbundelLo3RubriekHistorie is verwijderd, anders false
     */
    public boolean removeDienstbundelLo3RubriekHistorieSet(final DienstbundelLo3RubriekHistorie dienstbundelLo3RubriekHistorie) {
        final boolean result = getDienstbundelLo3RubriekHistorieSet().remove(dienstbundelLo3RubriekHistorie);
        dienstbundelLo3RubriekHistorie.setDienstbundelLo3Rubriek(null);
        return result;
    }

    /**
     * Geef de waarde van lo3Rubriek.
     *
     * @return lo3Rubriek
     */
    public Lo3Rubriek getLo3Rubriek() {
        return lo3Rubriek;
    }

    /**
     * Zet de waarde van lo3Rubriek.
     *
     * @param lo3Rubriek
     *            lo3Rubriek
     */
    public void setLo3Rubriek(final Lo3Rubriek lo3Rubriek) {
        ValidationUtils.controleerOpNullWaarden("lo3Rubriek mag niet null zijn", lo3Rubriek);
        this.lo3Rubriek = lo3Rubriek;
    }
}

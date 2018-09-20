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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;

/**
 * The persistent class for the dienstbundelgroepattr database table.
 * 
 */
@Entity
@Table(name = "dienstbundelgroepattr", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienstbundelgroep", "attr" }))
@NamedQuery(name = "DienstbundelGroepAttribuut.findAll", query = "SELECT d FROM DienstbundelGroepAttribuut d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelGroepAttribuut implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundelgroepattr_id_generator", sequenceName = "autaut.seq_dienstbundelgroepattr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundelgroepattr_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "attr", nullable = false)
    private int elementId;

    // bi-directional many-to-one association to DienstbundelGroep
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundelgroep", nullable = false)
    private DienstbundelGroep dienstbundelGroep;

    // bi-directional many-to-one association to DienstbundelGroepAttribuutHistorie
    @OneToMany(mappedBy = "dienstbundelGroepAttribuut", cascade = CascadeType.ALL)
    private Set<DienstbundelGroepAttribuutHistorie> dienstbundelGroepAttribuutHistorieSet;

    /**
     * JPA no-args constructor.
     */
    DienstbundelGroepAttribuut() {
    }

    /**
     * Maak een nieuw DienstbundelGroepAttribuut object.
     *
     * @param dienstbundelGroep
     *            dienstbundelGroep
     * @param attribuut
     *            attribuut
     */
    public DienstbundelGroepAttribuut(final DienstbundelGroep dienstbundelGroep, final Element attribuut) {
        setDienstbundelGroep(dienstbundelGroep);
        setAttribuut(attribuut);
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
     * Geef de waarde van attribuut.
     *
     * @return attribuut
     */
    public Element getAttribuut() {
        return Element.parseId((short) elementId);
    }

    /**
     * Zet de waarde van attribuut.
     *
     * @param attribuut
     *            attribuut
     */
    public void setAttribuut(final Element attribuut) {
        ValidationUtils.controleerOpNullWaarden("attribuut mag niet null zijn", attribuut);
        elementId = attribuut.getId();
    }

    /**
     * Geef de waarde van dienstbundelGroep.
     *
     * @return dienstbundelGroep
     */
    public DienstbundelGroep getDienstbundelGroep() {
        return this.dienstbundelGroep;
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

    /**
     * Geef de waarde van dienstbundelGroepAttribuutHistorieSet.
     *
     * @return dienstbundelGroepAttribuutHistorieSet
     */
    public Set<DienstbundelGroepAttribuutHistorie> getDienstbundelGroepAttribuutHistorieSet() {
        return this.dienstbundelGroepAttribuutHistorieSet;
    }

    /**
     * Zet de waarde van dienstbundelGroepAttribuutHistorieSet.
     *
     * @param dienstbundelGroepAttribuutHistorieSet
     *            dienstbundelGroepAttribuutHistorieSet
     */
    public void setDienstbundelGroepAttribuutHistorieSet(final Set<DienstbundelGroepAttribuutHistorie> dienstbundelGroepAttribuutHistorieSet) {
        this.dienstbundelGroepAttribuutHistorieSet = dienstbundelGroepAttribuutHistorieSet;
    }

    /**
     * Voeg een dienstbundelGroepAttribuutHistorie toe aan dienstbundelGroepAttribuutHistorieSet.
     *
     * @param dienstbundelGroepAttribuutHistorie
     *            dienstbundelGroepAttribuutHistorie
     */
    public void addDienstbundelGroepAttribuutHistorieSet(final DienstbundelGroepAttribuutHistorie dienstbundelGroepAttribuutHistorie) {
        getDienstbundelGroepAttribuutHistorieSet().add(dienstbundelGroepAttribuutHistorie);
        dienstbundelGroepAttribuutHistorie.setDienstbundelGroepAttribuut(this);
    }

    /**
     * Verwijderd dienstbundelGroepAttribuutHistorie uit dienstbundelGroepAttribuutHistorieSet.
     *
     * @param dienstbundelGroepAttribuutHistorie
     *            dienstbundelGroepAttribuutHistorie
     * @return true als dienstbundelGroepAttribuutHistorie is verwijderd anders false
     */
    public boolean removeDienstbundelGroepAttribuutHistorieSet(final DienstbundelGroepAttribuutHistorie dienstbundelGroepAttribuutHistorie) {
        final boolean result = getDienstbundelGroepAttribuutHistorieSet().remove(dienstbundelGroepAttribuutHistorie);
        dienstbundelGroepAttribuutHistorie.setDienstbundelGroepAttribuut(null);
        return result;
    }
}

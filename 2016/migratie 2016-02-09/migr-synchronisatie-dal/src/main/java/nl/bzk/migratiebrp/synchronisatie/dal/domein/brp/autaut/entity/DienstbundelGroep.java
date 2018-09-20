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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;

/**
 * The persistent class for the dienstbundelgroep database table.
 * 
 */
@Entity
@NamedQuery(name = "DienstbundelGroep.findAll", query = "SELECT d FROM DienstbundelGroep d")
@SuppressWarnings("checkstyle:designforextension")
public class DienstbundelGroep implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundelgroep_id_generator", sequenceName = "autaut.seq_dienstbundelgroep", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundelgroep_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "groep", nullable = false)
    private int elementId;

    @Column(name = "indformelehistorie")
    private Boolean indicatieFormeleHistorie;

    @Column(name = "indmaterielehistorie")
    private Boolean indicatieMaterieleHistorie;

    @Column(name = "indverantwoording")
    private Boolean indicatieVerantwoording;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    // bi-directional many-to-one association to DienstbundelGroepAttribuut
    @OneToMany(mappedBy = "dienstbundelGroep", cascade = {CascadeType.ALL })
    private Set<DienstbundelGroepAttribuut> dienstbundelGroepAttribuutSet;

    // bi-directional many-to-one association to DienstbundelGroepHistorie
    @OneToMany(mappedBy = "dienstbundelGroep", cascade = CascadeType.ALL)
    private Set<DienstbundelGroepHistorie> dienstbundelGroepHistorieSet;

    /**
     * JPA no-args constructor.
     */
    DienstbundelGroep() {
    }

    /**
     * Maakt een DienstbundelGroep object.
     *
     * @param dienstbundel
     *            dienstbundel
     * @param groep
     *            groep
     */
    public DienstbundelGroep(final Dienstbundel dienstbundel, final Element groep) {
        setDienstbundel(dienstbundel);
        setGroep(groep);
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
     * Geef de waarde van groep.
     *
     * @return groep
     */
    public Element getGroep() {
        return Element.parseId((short) elementId);
    }

    /**
     * Zet de waarde van groep.
     *
     * @param groep
     *            groep
     */
    public void setGroep(final Element groep) {
        ValidationUtils.controleerOpNullWaarden("groep mag niet null zijn", groep);
        elementId = groep.getId();
    }

    /**
     * Geef de waarde van indicatieFormeleHistorie.
     *
     * @return indicatieFormeleHistorie
     */
    public Boolean getIndicatieFormeleHistorie() {
        return this.indicatieFormeleHistorie;
    }

    /**
     * Zet de waarde van indicatieFormeleHistorie.
     *
     * @param indicatieFormeleHistorie
     *            indicatieFormeleHistorie
     */
    public void setIndicatieFormeleHistorie(final Boolean indicatieFormeleHistorie) {
        this.indicatieFormeleHistorie = indicatieFormeleHistorie;
    }

    /**
     * Geef de waarde van indicatieMaterieleHistorie.
     *
     * @return indicatieMaterieleHistorie
     */
    public Boolean getIndicatieMaterieleHistorie() {
        return this.indicatieMaterieleHistorie;
    }

    /**
     * Zet de waarde van indicatieMaterieleHistorie.
     *
     * @param indicatieMaterieleHistorie
     *            indicatieMaterieleHistorie
     */
    public void setIndicatieMaterieleHistorie(final Boolean indicatieMaterieleHistorie) {
        this.indicatieMaterieleHistorie = indicatieMaterieleHistorie;
    }

    /**
     * Geef de waarde van indicatieVerantwoording.
     *
     * @return indicatieVerantwoording
     */
    public Boolean getIndicatieVerantwoording() {
        return this.indicatieVerantwoording;
    }

    /**
     * Zet de waarde van indicatieVerantwoording.
     *
     * @param indicatieVerantwoording
     *            indicatieVerantwoording
     */
    public void setIndicatieVerantwoording(final Boolean indicatieVerantwoording) {
        this.indicatieVerantwoording = indicatieVerantwoording;
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
     * Geef de waarde van dienstbundelGroepAttribuutSet.
     *
     * @return dienstbundelGroepAttribuutSet
     */
    public Set<DienstbundelGroepAttribuut> getDienstbundelGroepAttribuutSet() {
        return this.dienstbundelGroepAttribuutSet;
    }

    /**
     * Zet de waarde van dienstbundelGroepAttribuutSet.
     *
     * @param dienstbundelGroepAttribuutSet
     *            dienstbundelGroepAttribuutSet
     */
    public void setDienstbundelGroepAttribuutSet(final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttribuutSet) {
        this.dienstbundelGroepAttribuutSet = dienstbundelGroepAttribuutSet;
    }

    /**
     * Voegt een dienstbundelGroepAttribuut toe aan dienstbundelGroepAttribuutSet.
     *
     * @param dienstbundelGroepAttribuut
     *            dienstbundelGroepAttribuut
     */
    public void addDienstbundelGroepAttribuutSet(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut) {
        getDienstbundelGroepAttribuutSet().add(dienstbundelGroepAttribuut);
        dienstbundelGroepAttribuut.setDienstbundelGroep(this);
    }

    /**
     * Verwijderd een dienstbundelGroepAttribuut uit dienstbundelGroepAttribuutSet.
     *
     * @param dienstbundelGroepAttribuut
     *            dienstbundelGroepAttribuut
     * @return true als de dienstbundelGroepAttribuut is verwijderd, anders false
     */
    public boolean removeDienstbundelGroepAttribuutSet(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut) {
        final boolean result = getDienstbundelGroepAttribuutSet().remove(dienstbundelGroepAttribuut);
        dienstbundelGroepAttribuut.setDienstbundelGroep(null);
        return result;
    }

    /**
     * Geef de waarde van dienstbundelGroepHistorieSet.
     * 
     * @return dienstbundelGroepHistorieSet
     */
    public Set<DienstbundelGroepHistorie> getDienstbundelGroepHistorieSet() {
        return this.dienstbundelGroepHistorieSet;
    }

    /**
     * Zet de waarde van dienstbundelGroepHistorieSet.
     *
     * @param dienstbundelGroepHistorieSet
     *            dienstbundelGroepHistorieSet
     */
    public void setDienstbundelGroepHistorieSet(final Set<DienstbundelGroepHistorie> dienstbundelGroepHistorieSet) {
        this.dienstbundelGroepHistorieSet = dienstbundelGroepHistorieSet;
    }

    /**
     * Voeg een dienstbundelGroepHistorie toe aan dienstbundelGroepHistorieSet.
     *
     * @param dienstbundelGroepHistorie
     *            dienstbundelGroepHistorie
     */
    public void addDienstbundelGroepHistorieSet(final DienstbundelGroepHistorie dienstbundelGroepHistorie) {
        getDienstbundelGroepHistorieSet().add(dienstbundelGroepHistorie);
        dienstbundelGroepHistorie.setDienstbundelGroep(this);
    }

    /**
     * Verwijder een dienstbundelGroepHistorie uit dienstbundelGroepHistorieSet.
     *
     * @param dienstbundelGroepHistorie
     *            dienstbundelGroepHistorie
     * @return true als dienstbundelGroepHistorie is verwijderd, anders false
     */
    public boolean removeDienstbundelGroepHistorieSet(final DienstbundelGroepHistorie dienstbundelGroepHistorie) {
        final boolean result = getDienstbundelGroepHistorieSet().remove(dienstbundelGroepHistorie);
        dienstbundelGroepHistorie.setDienstbundelGroep(null);
        return result;
    }
}

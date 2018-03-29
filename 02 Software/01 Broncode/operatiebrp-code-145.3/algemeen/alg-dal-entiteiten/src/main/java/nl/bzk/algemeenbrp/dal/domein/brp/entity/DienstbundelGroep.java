/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the dienstbundelgroep database table.
 *
 */
@Entity
@Table(name = "dienstbundelgroep", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienstbundel", "groep"}))
@NamedQuery(name = "DienstbundelGroep" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstbundelGroep d")
@Access(AccessType.FIELD)
public class DienstbundelGroep implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundelgroep_id_generator", sequenceName = "autaut.seq_dienstbundelgroep", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundelgroep_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "groep", nullable = false)
    private int elementId;

    @Column(name = "indformelehistorie", nullable = false)
    private boolean indicatieFormeleHistorie;

    @Column(name = "indmaterielehistorie", nullable = false)
    private boolean indicatieMaterieleHistorie;

    @Column(name = "indverantwoording", nullable = false)
    private boolean indicatieVerantwoording;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    // bi-directional many-to-one association to DienstbundelGroepAttribuut
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundelGroep", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelGroepAttribuut> dienstbundelGroepAttribuutSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    DienstbundelGroep() {}

    /**
     * Maakt een DienstbundelGroep object.
     *
     * @param dienstbundel dienstbundel
     * @param groep groep
     * @param indicatieFormeleHistorie indicatieFormeleHistorie
     * @param indicatieMaterieleHistorie indicatieMaterieleHistorie
     * @param indicatieVerantwoording indicatieVerantwoording
     */
    public DienstbundelGroep(final Dienstbundel dienstbundel, final Element groep, final boolean indicatieFormeleHistorie,
            final boolean indicatieMaterieleHistorie, final boolean indicatieVerantwoording) {
        setDienstbundel(dienstbundel);
        setGroep(groep);
        setIndicatieFormeleHistorie(indicatieFormeleHistorie);
        setIndicatieMaterieleHistorie(indicatieMaterieleHistorie);
        setIndicatieVerantwoording(indicatieVerantwoording);
    }

    /**
     * Geef de waarde van id van DienstbundelGroep.
     *
     * @return de waarde van id van DienstbundelGroep
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van DienstbundelGroep.
     *
     * @param id de nieuwe waarde voor id van DienstbundelGroep
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van groep van DienstbundelGroep.
     *
     * @return de waarde van groep van DienstbundelGroep
     */
    public Element getGroep() {
        return Element.parseId(elementId);
    }

    /**
     * Zet de waarden voor groep van DienstbundelGroep.
     *
     * @param groep de nieuwe waarde voor groep van DienstbundelGroep
     */
    public void setGroep(final Element groep) {
        ValidationUtils.controleerOpNullWaarden("groep mag niet null zijn", groep);
        elementId = groep.getId();
    }

    /**
     * Geef de waarde van indicatie formele historie van DienstbundelGroep.
     *
     * @return de waarde van indicatie formele historie van DienstbundelGroep
     */
    public boolean getIndicatieFormeleHistorie() {
        return indicatieFormeleHistorie;
    }

    /**
     * Zet de waarden voor indicatie formele historie van DienstbundelGroep.
     *
     * @param indicatieFormeleHistorie de nieuwe waarde voor indicatie formele historie van
     *        DienstbundelGroep
     */
    public void setIndicatieFormeleHistorie(final boolean indicatieFormeleHistorie) {
        this.indicatieFormeleHistorie = indicatieFormeleHistorie;
    }

    /**
     * Geef de waarde van indicatie materiele historie van DienstbundelGroep.
     *
     * @return de waarde van indicatie materiele historie van DienstbundelGroep
     */
    public boolean getIndicatieMaterieleHistorie() {
        return indicatieMaterieleHistorie;
    }

    /**
     * Zet de waarden voor indicatie materiele historie van DienstbundelGroep.
     *
     * @param indicatieMaterieleHistorie de nieuwe waarde voor indicatie materiele historie van
     *        DienstbundelGroep
     */
    public void setIndicatieMaterieleHistorie(final boolean indicatieMaterieleHistorie) {
        this.indicatieMaterieleHistorie = indicatieMaterieleHistorie;
    }

    /**
     * Geef de waarde van indicatie verantwoording van DienstbundelGroep.
     *
     * @return de waarde van indicatie verantwoording van DienstbundelGroep
     */
    public boolean getIndicatieVerantwoording() {
        return indicatieVerantwoording;
    }

    /**
     * Zet de waarden voor indicatie verantwoording van DienstbundelGroep.
     *
     * @param indicatieVerantwoording de nieuwe waarde voor indicatie verantwoording van
     *        DienstbundelGroep
     */
    public void setIndicatieVerantwoording(final boolean indicatieVerantwoording) {
        this.indicatieVerantwoording = indicatieVerantwoording;
    }

    /**
     * Geef de waarde van dienstbundel van DienstbundelGroep.
     *
     * @return de waarde van dienstbundel van DienstbundelGroep
     */
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Zet de waarden voor dienstbundel van DienstbundelGroep.
     *
     * @param dienstbundel de nieuwe waarde voor dienstbundel van DienstbundelGroep
     */
    public void setDienstbundel(final Dienstbundel dienstbundel) {
        ValidationUtils.controleerOpNullWaarden("dienstbundel mag niet null zijn", dienstbundel);
        this.dienstbundel = dienstbundel;
    }

    /**
     * Geef de waarde van dienstbundel groep attribuut set van DienstbundelGroep.
     *
     * @return de waarde van dienstbundel groep attribuut set van DienstbundelGroep
     */
    public Set<DienstbundelGroepAttribuut> getDienstbundelGroepAttribuutSet() {
        return dienstbundelGroepAttribuutSet;
    }

    /**
     * Zet de waarden voor dienstbundel groep attribuut set van DienstbundelGroep.
     *
     * @param dienstbundelGroepAttribuutSet de nieuwe waarde voor dienstbundel groep attribuut set
     *        van DienstbundelGroep
     */
    public void setDienstbundelGroepAttribuutSet(final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttribuutSet) {
        this.dienstbundelGroepAttribuutSet = dienstbundelGroepAttribuutSet;
    }

    /**
     * Voegt een dienstbundelGroepAttribuut toe aan dienstbundelGroepAttribuutSet.
     *
     * @param dienstbundelGroepAttribuut dienstbundelGroepAttribuut
     */
    public void addDienstbundelGroepAttribuutSet(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut) {
        getDienstbundelGroepAttribuutSet().add(dienstbundelGroepAttribuut);
        dienstbundelGroepAttribuut.setDienstbundelGroep(this);
    }

    /**
     * Verwijderd een dienstbundelGroepAttribuut uit dienstbundelGroepAttribuutSet.
     *
     * @param dienstbundelGroepAttribuut dienstbundelGroepAttribuut
     * @return true als de dienstbundelGroepAttribuut is verwijderd, anders false
     */
    public boolean removeDienstbundelGroepAttribuutSet(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut) {
        final boolean result = getDienstbundelGroepAttribuutSet().remove(dienstbundelGroepAttribuut);
        dienstbundelGroepAttribuut.setDienstbundelGroep(null);
        return result;
    }
}

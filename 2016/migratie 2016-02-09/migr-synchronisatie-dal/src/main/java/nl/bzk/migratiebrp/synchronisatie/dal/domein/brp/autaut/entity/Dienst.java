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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the dienst database table.
 * 
 */
@Entity
@Table(name = "dienst", schema = "autaut")
@NamedQuery(name = "Dienst.findAll", query = "SELECT d FROM Dienst d")
@SuppressWarnings("checkstyle:designforextension")
public class Dienst implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienst_id_generator", sequenceName = "autaut.seq_dienst", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienst_id_generator")
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name = "attenderingscriterium")
    private String attenderingscriterium;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "eersteselectiedat")
    private Integer eersteSelectieDatum;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "selectieperiodeinmaanden")
    private Integer selectiePeriodeInMaanden;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    @Column(name = "effectafnemerindicaties")
    private Short effectAfnemerindicatiesId;

    @Column(name = "srt", nullable = false)
    private short soortDienstId;

    // bi-directional many-to-one association to DienstHistorie
    @OneToMany(mappedBy = "dienst", cascade = CascadeType.ALL)
    private Set<DienstHistorie> dienstHistorieSet;

    // bi-directional many-to-one association to DienstAttenderingHistorie
    @OneToMany(mappedBy = "dienst", cascade = CascadeType.ALL)
    private Set<DienstAttenderingHistorie> dienstAttenderingHistorieSet;

    // bi-directional many-to-one association to DienstSelectieHistorie
    @OneToMany(mappedBy = "dienst", cascade = CascadeType.ALL)
    private Set<DienstSelectieHistorie> dienstSelectieHistorieSet;

    /**
     * JPA No-args constructor.
     */
    Dienst() {
    }

    /**
     * Maakt een Dienst object.
     *
     * @param dienstbundel
     *            dienstbundel
     * @param soortDienst
     *            soort dienst
     */
    public Dienst(final Dienstbundel dienstbundel, final SoortDienst soortDienst) {
        setDienstbundel(dienstbundel);
        setSoortDienst(soortDienst);
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
     * Geef de waarde van attenderingscriterium.
     *
     * @return attenderingscriterium
     */
    public String getAttenderingscriterium() {
        return this.attenderingscriterium;
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
     * Geeft de waarde van datumEinde.
     *
     * @return datumEinde
     */
    public Integer getDatumEinde() {
        return this.datumEinde;
    }

    /**
     * Zet de waarde van datumEinde.
     *
     * @param datumEinde
     *            datumEinde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geeft de waarde van datumIngang.
     *
     * @return datumIngang
     */
    public Integer getDatumIngang() {
        return this.datumIngang;
    }

    /**
     * Zet de waarde van datumIngang.
     *
     * @param datumIngang
     *            datumIngang
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geeft de waarde van eersteSelectieDatum.
     *
     * @return eersteSelectieDatum
     */
    public Integer getEersteSelectieDatum() {
        return this.eersteSelectieDatum;
    }

    /**
     * Zet de waarde van eersteSelectieDatum.
     *
     * @param eersteSelectieDatum
     *            eersteSelectieDatum
     */
    public void setEersteSelectieDatum(final Integer eersteSelectieDatum) {
        this.eersteSelectieDatum = eersteSelectieDatum;
    }

    /**
     * Geeft de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public Boolean getIndicatieGeblokkeerd() {
        return this.indicatieGeblokkeerd;
    }

    /**
     * Zet de waarde van indicatieGeblokkeerd.
     *
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geeft de waarde van selectiePeriodeInMaanden.
     *
     * @return selectiePeriodeInMaanden
     */
    public Integer getSelectiePeriodeInMaanden() {
        return this.selectiePeriodeInMaanden;
    }

    /**
     * Zet de waarde van selectiePeriodeInMaanden.
     *
     * @param selectiePeriodeInMaanden
     *            selectiePeriodeInMaanden
     */
    public void setSelectiePeriodeInMaanden(final Integer selectiePeriodeInMaanden) {
        this.selectiePeriodeInMaanden = selectiePeriodeInMaanden;
    }

    /**
     * Geeft de waarde van dienstbundel.
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
     * Geef de waarde van effectAfnemerindicaties.
     *
     * @return effectAfnemerindicaties
     */
    public EffectAfnemerindicaties getEffectAfnemerindicaties() {
        if (effectAfnemerindicatiesId == null) {
            return null;
        } else {
            return EffectAfnemerindicaties.parseId(effectAfnemerindicatiesId);
        }
    }

    /**
     * Zet de waarde van effectAfnemerindicaties.
     *
     * @param effectAfnemerindicaties
     *            effectAfnemerindicaties
     */
    public void setEffectAfnemerindicaties(final EffectAfnemerindicaties effectAfnemerindicaties) {
        if (effectAfnemerindicaties == null) {
            effectAfnemerindicatiesId = null;
        } else {
            effectAfnemerindicatiesId = effectAfnemerindicaties.getId();
        }
    }

    /**
     * Geeft de waarde van soortDienst.
     *
     * @return soortDienst
     */
    public SoortDienst getSoortDienst() {
        return SoortDienst.parseId(soortDienstId);
    }

    /**
     * Zet de waarde van soortDienst.
     *
     * @param soortDienst
     *            soortDienst
     */
    public void setSoortDienst(final SoortDienst soortDienst) {
        ValidationUtils.controleerOpNullWaarden("soortDienst mag niet null zijn", soortDienst);
        soortDienstId = soortDienst.getId();
    }

    /**
     * Geeft de waarde van dienstHistorieSet.
     *
     * @return dienstHistorieSet
     */
    public Set<DienstHistorie> getDienstHistorieSet() {
        return this.dienstHistorieSet;
    }

    /**
     * Zet de waarde van dienstHistorieSet.
     *
     * @param dienstHistorieSet
     *            dienstHistorieSet
     */
    public void setDienstHistorieSet(final Set<DienstHistorie> dienstHistorieSet) {
        this.dienstHistorieSet = dienstHistorieSet;
    }

    /**
     * Voegt een dienstHistorie toe aan dienstHistorieSet.
     *
     * @param dienstHistorie
     *            dienstHistorie
     */
    public void addDienstHistorieSet(final DienstHistorie dienstHistorie) {
        getDienstHistorieSet().add(dienstHistorie);
        dienstHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstHistorie uit dienstHistorieSet.
     *
     * @param dienstHistorie
     *            de te verwijderen dienstHistorie
     * @return true als dit element uit dienstHistorieSet verwijderd is, anders false
     */
    public boolean removeDienstHistorieSet(final DienstHistorie dienstHistorie) {
        final boolean result = getDienstHistorieSet().remove(dienstHistorie);
        dienstHistorie.setDienst(null);

        return result;
    }

    /**
     * Geef de waarde van dienstAttenderingHistorieSet.
     *
     * @return dienstAttenderingHistorieSet
     */
    public Set<DienstAttenderingHistorie> getDienstAttenderingHistorieSet() {
        return this.dienstAttenderingHistorieSet;
    }

    /**
     * Zet de waarde van dienstAttenderingHistorieSet.
     *
     * @param dienstAttenderingHistorieSet
     *            dienstAttenderingHistorieSet
     */
    public void setDienstAttenderingHistorieSet(final Set<DienstAttenderingHistorie> dienstAttenderingHistorieSet) {
        this.dienstAttenderingHistorieSet = dienstAttenderingHistorieSet;
    }

    /**
     * Voegt een dienstAttenderingHistorie toe aan dienstAttenderingHistorieSet.
     *
     * @param dienstAttenderingHistorie
     *            dienstAttenderingHistorie
     */
    public void addDienstAttenderingHistorieSet(final DienstAttenderingHistorie dienstAttenderingHistorie) {
        getDienstAttenderingHistorieSet().add(dienstAttenderingHistorie);
        dienstAttenderingHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstAttenderingHistorie uit dienstAttenderingHistorieSet.
     *
     * @param dienstAttenderingHistorie
     *            dienstAttenderingHistorie
     * @return true als de gegeven dienstAttenderingHistorie verwijderd is uit dienstAttenderingHistorieSet, anders
     *         false
     */
    public boolean removeDienstAttenderingHistorieSet(final DienstAttenderingHistorie dienstAttenderingHistorie) {
        final boolean result = getDienstAttenderingHistorieSet().remove(dienstAttenderingHistorie);
        dienstAttenderingHistorie.setDienst(null);
        return result;
    }

    /**
     * Geeft de waarde van dienstSelectieHistorieSet.
     *
     * @return dienstSelectieHistorieSet
     */
    public Set<DienstSelectieHistorie> getDienstSelectieHistorieSet() {
        return this.dienstSelectieHistorieSet;
    }

    /**
     * Zet de waarde van dienstSelectieHistorieSet.
     *
     * @param dienstSelectieHistorieSet
     *            dienstSelectieHistorieSet
     */
    public void setDienstSelectieHistorieSet(final Set<DienstSelectieHistorie> dienstSelectieHistorieSet) {
        this.dienstSelectieHistorieSet = dienstSelectieHistorieSet;
    }

    /**
     * Voegt een dienstSelectieHistorie toe aan de dienstSelectieHistorieSet.
     *
     * @param dienstSelectieHistorie
     *            dienstSelectieHistorie
     */
    public void addDienstSelectieHistorieSet(final DienstSelectieHistorie dienstSelectieHistorie) {
        getDienstSelectieHistorieSet().add(dienstSelectieHistorie);
        dienstSelectieHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstSelectieHistorie uit de dienstSelectieHistorieSet.
     * 
     * @param dienstSelectieHistorie
     *            dienstSelectieHistorie
     * @return true als de gegeven dienstSelectieHistorie verwijderd is uit dienstSelectieHistorieSet, anders false
     */
    public boolean removeDienstSelectieHistorieSet(final DienstSelectieHistorie dienstSelectieHistorie) {
        final boolean result = getDienstSelectieHistorieSet().remove(dienstSelectieHistorie);
        dienstSelectieHistorie.setDienst(null);
        return result;
    }

}

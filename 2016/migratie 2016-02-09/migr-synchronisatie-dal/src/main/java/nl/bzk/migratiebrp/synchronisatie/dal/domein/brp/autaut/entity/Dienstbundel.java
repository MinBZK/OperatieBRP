/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
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
 * The persistent class for the dienstbundel database table.
 */
@Entity
@Table(name = "dienstbundel", schema = "autaut")
@NamedQuery(name = "Dienstbundel.findAll", query = "SELECT d FROM Dienstbundel d")
@SuppressWarnings("checkstyle:designforextension")
public class Dienstbundel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundel_id_generator", sequenceName = "autaut.seq_dienstbundel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundel_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indnaderepopbeperkingvolconv")
    private Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd;

    @Column(name = "naam")
    private String naam;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    @Column(name = "toelichting")
    private String toelichting;

    // bi-directional many-to-one association to Dienst
    @OneToMany(mappedBy = "dienstbundel", cascade = CascadeType.ALL)
    private Set<Dienst> dienstSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    // bi-directional many-to-one association to DienstbundelGroep
    @OneToMany(mappedBy = "dienstbundel", cascade = CascadeType.ALL)
    private Set<DienstbundelGroep> dienstbundelGroepSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to DienstbundelLo3Rubriek
    @OneToMany(mappedBy = "dienstbundel", cascade = CascadeType.ALL)
    private Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to DienstbundelHistorie
    @OneToMany(mappedBy = "dienstbundel", cascade = CascadeType.ALL)
    private Set<DienstbundelHistorie> dienstbundelHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    Dienstbundel() {
    }

    /**
     * Maakt een DienstBundel object.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public Dienstbundel(final Leveringsautorisatie leveringsautorisatie) {
        setLeveringsautorisatie(leveringsautorisatie);
    }

    /**
     * Geeft de waarde van id.
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
     * Geef de waarde van datumEinde.
     *
     * @return datumEinde
     */
    public Integer getDatumEinde() {
        return datumEinde;
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
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang
     */
    public Integer getDatumIngang() {
        return datumIngang;
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
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
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
     * Geef de waarde van indicatieNaderePopulatiebeperkingVolledigGeconverteerd.
     *
     * @return indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     */
    public Boolean getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet de waarde van indicatieNaderePopulatiebeperkingVolledigGeconverteerd.
     *
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     *            indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     */
    public void setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(final Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd) {
        if (Boolean.TRUE.equals(indicatieNaderePopulatiebeperkingVolledigGeconverteerd)) {
            throw new IllegalArgumentException("indicatieNaderePopulatiebeperkingVolledigGeconverteerd moet null of FALSE zijn");
        }
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
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
        if ("".equals(naam)) {
            throw new IllegalArgumentException("naam moet een string van minimaal lengte 1 of null zijn");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van naderePopulatiebeperking.
     *
     * @return naderePopulatiebeperking
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Zet de waarde van naderePopulatiebeperking.
     *
     * @param naderePopulatiebeperking
     *            naderePopulatiebeperking
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van toelichting.
     *
     * @return toelichting
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Zet de waarde toelichting.
     *
     * @param toelichting
     *            toelichting
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van dienstSet.
     *
     * @return dienstSet
     */
    public Set<Dienst> getDienstSet() {
        return dienstSet;
    }

    /**
     * Zet de waarde van dienstSet.
     *
     * @param dienstSet
     *            dienstSet
     */
    public void setDienstSet(final Set<Dienst> dienstSet) {
        this.dienstSet = dienstSet;
    }

    /**
     * Voeg dienst toe aan dienstSet.
     *
     * @param dienst
     *            dienst
     */
    public void addDienstSet(final Dienst dienst) {
        getDienstSet().add(dienst);
        dienst.setDienstbundel(this);
    }

    /**
     * Verwijderd de gegeven dienst uit dienstSet.
     *
     * @param dienst
     *            de te verwijderen dienst
     * @return true als deze dienst uit de dienstSet is verwijderd, anders false
     */
    public boolean removeDienstSet(final Dienst dienst) {
        final boolean result = getDienstSet().remove(dienst);
        dienst.setDienstbundel(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie.
     *
     * @return leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarde van leveringsautorisatie.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie mag niet null zijn", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Geef de waarde van dienstbundelGroepSet.
     *
     * @return dienstbundelGroepSet
     */
    public Set<DienstbundelGroep> getDienstbundelGroepSet() {
        return dienstbundelGroepSet;
    }

    /**
     * Zet de waarde van dienstbundelGroepSet.
     *
     * @param dienstbundelGroepSet
     *            dienstbundelGroepSet
     */
    public void setDienstbundelGroepSet(final Set<DienstbundelGroep> dienstbundelGroepSet) {
        this.dienstbundelGroepSet = dienstbundelGroepSet;
    }

    /**
     * Voeg dienstbundelGroep toe aan dienstbundelGroepSet.
     *
     * @param dienstbundelGroep
     *            dienstbundelGroep
     */
    public void addDienstbundelGroepSet(final DienstbundelGroep dienstbundelGroep) {
        getDienstbundelGroepSet().add(dienstbundelGroep);
        dienstbundelGroep.setDienstbundel(this);
    }

    /**
     * Verwijderd een dienstbundelGroep uit de dienstbundelGroepSet.
     *
     * @param dienstbundelGroep
     *            de te verwijderen dienstbundelGroep
     * @return true als de gegeven dienstbundelGroep verwijderd is, anders false
     */
    public boolean removeDienstbundelGroepSet(final DienstbundelGroep dienstbundelGroep) {
        final boolean result = getDienstbundelGroepSet().remove(dienstbundelGroep);
        dienstbundelGroep.setDienstbundel(null);
        return result;
    }

    /**
     * Geef de waarde van dienstbundelLo3RubriekSet.
     *
     * @return dienstbundelLo3RubriekSet
     */
    public Set<DienstbundelLo3Rubriek> getDienstbundelLo3RubriekSet() {
        return dienstbundelLo3RubriekSet;
    }

    /**
     * Zet de waarde van dienstbundelLo3RubriekSet.
     *
     * @param dienstbundelLo3RubriekSet
     *            dienstbundelLo3RubriekSet
     */
    public void setDienstbundelLo3RubriekSet(final Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet) {
        this.dienstbundelLo3RubriekSet = dienstbundelLo3RubriekSet;
    }

    /**
     * Voeg dienstbundelLo3Rubriek toe aan dienstbundelLo3RubriekSet.
     *
     * @param dienstbundelLo3Rubriek
     *            dienstbundelLo3Rubriek
     */
    public void addDienstbundelLo3RubriekSet(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        getDienstbundelLo3RubriekSet().add(dienstbundelLo3Rubriek);
        dienstbundelLo3Rubriek.setDienstbundel(this);
    }

    /**
     * Verwijderd de gegeven dienstbundelLo3Rubriek uit dienstbundelLo3RubriekSet.
     *
     * @param dienstbundelLo3Rubriek
     *            dienstbundelLo3Rubriek
     * @return true als de gegeven dienstbundelLo3Rubriek is verwijderd, anders false
     */
    public boolean removeDienstbundelLo3RubriekSet(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        final boolean result = getDienstbundelLo3RubriekSet().remove(dienstbundelLo3Rubriek);
        dienstbundelLo3Rubriek.setDienstbundel(null);
        return result;
    }

    /**
     * Geef de waarde van dienstbundelHistorieSet.
     *
     * @return dienstbundelHistorieSet
     */
    public Set<DienstbundelHistorie> getDienstbundelHistorieSet() {
        return dienstbundelHistorieSet;
    }

    /**
     * Zet de waarde van dienstbundelHistorieSet.
     *
     * @param dienstbundelHistorieSet
     *            dienstbundelHistorieSet
     */
    public void setDienstbundelHistorieSet(final Set<DienstbundelHistorie> dienstbundelHistorieSet) {
        this.dienstbundelHistorieSet = dienstbundelHistorieSet;
    }

    /**
     * Voeg dienstbundelHistorie toe aan de dienstbundelHistorieSet.
     *
     * @param dienstbundelHistorie
     *            dienstbundelHistorie
     */
    public void addDienstbundelHistorieSet(final DienstbundelHistorie dienstbundelHistorie) {
        getDienstbundelHistorieSet().add(dienstbundelHistorie);
        dienstbundelHistorie.setDienstbundel(this);
    }

    /**
     * Verwijder dienstbundelHistorie uit dienstbundelHistorieSet.
     *
     * @param dienstbundelHistorie
     *            de te verwijderen dienstbundelHistorie
     * @return true als de dienstbundelHistorie is verwijderd anders false
     */
    public boolean removeDienstbundelHistorieSet(final DienstbundelHistorie dienstbundelHistorie) {
        final boolean result = getDienstbundelHistorieSet().remove(dienstbundelHistorie);
        dienstbundelHistorie.setDienstbundel(null);
        return result;
    }
}

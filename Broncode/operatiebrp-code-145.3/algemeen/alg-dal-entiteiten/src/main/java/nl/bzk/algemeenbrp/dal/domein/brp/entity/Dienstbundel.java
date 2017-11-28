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
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the dienstbundel database table.
 */
@Entity
@Table(name = "dienstbundel", schema = "autaut")
@NamedQuery(name = "Dienstbundel" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM Dienstbundel d")
@Access(AccessType.FIELD)
public class Dienstbundel implements Afleidbaar, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundel_id_generator", sequenceName = "autaut.seq_dienstbundel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundel_id_generator")
    @Column(updatable = false)
    @Access(AccessType.PROPERTY)
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

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    // bi-directional many-to-one association to Dienst
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundel", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<Dienst> dienstSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    // bi-directional many-to-one association to DienstbundelGroep
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelGroep> dienstbundelGroepSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to DienstbundelLo3Rubriek
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to DienstbundelHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundel", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelHistorie> dienstbundelHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA no-args constructor.
     */
    Dienstbundel() {}

    /**
     * Maakt een DienstBundel object.
     *
     * @param leveringsautorisatie leveringsautorisatie
     */
    public Dienstbundel(final Leveringsautorisatie leveringsautorisatie) {
        setLeveringsautorisatie(leveringsautorisatie);
    }

    /**
     * Geef de waarde van id van Dienstbundel.
     *
     * @return de waarde van id van Dienstbundel
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Dienstbundel.
     *
     * @param id de nieuwe waarde voor id van Dienstbundel
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van Dienstbundel.
     *
     * @return de waarde van datum einde van Dienstbundel
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Dienstbundel.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van Dienstbundel
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van Dienstbundel.
     *
     * @return de waarde van datum ingang van Dienstbundel
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van Dienstbundel.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van Dienstbundel
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van Dienstbundel.
     *
     * @return de waarde van indicatie geblokkeerd van Dienstbundel
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van Dienstbundel.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van Dienstbundel
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van indicatie nadere populatiebeperking volledig geconverteerd van
     * Dienstbundel.
     *
     * @return de waarde van indicatie nadere populatiebeperking volledig geconverteerd van
     *         Dienstbundel
     */
    public Boolean getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet de waarden voor indicatie nadere populatiebeperking volledig geconverteerd van
     * Dienstbundel.
     *
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd de nieuwe waarde voor indicatie
     *        nadere populatiebeperking volledig geconverteerd van Dienstbundel
     */
    public void setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(final Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd) {
        if (Boolean.TRUE.equals(indicatieNaderePopulatiebeperkingVolledigGeconverteerd)) {
            throw new IllegalArgumentException("indicatieNaderePopulatiebeperkingVolledigGeconverteerd moet null of FALSE zijn");
        }
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van naam van Dienstbundel.
     *
     * @return de waarde van naam van Dienstbundel
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Dienstbundel.
     *
     * @param naam de nieuwe waarde voor naam van Dienstbundel
     */
    public void setNaam(final String naam) {
        if ("".equals(naam)) {
            throw new IllegalArgumentException("naam moet een string van minimaal lengte 1 of null zijn");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van nadere populatiebeperking van Dienstbundel.
     *
     * @return de waarde van nadere populatiebeperking van Dienstbundel
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Zet de waarden voor nadere populatiebeperking van Dienstbundel.
     *
     * @param naderePopulatiebeperking de nieuwe waarde voor nadere populatiebeperking van
     *        Dienstbundel
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van toelichting van Dienstbundel.
     *
     * @return de waarde van toelichting van Dienstbundel
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Zet de waarden voor toelichting van Dienstbundel.
     *
     * @param toelichting de nieuwe waarde voor toelichting van Dienstbundel
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     *
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     *
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * Geef de waarde van dienst set van Dienstbundel.
     *
     * @return de waarde van dienst set van Dienstbundel
     */
    public Set<Dienst> getDienstSet() {
        return dienstSet;
    }

    /**
     * Zet de waarden voor dienst set van Dienstbundel.
     *
     * @param dienstSet de nieuwe waarde voor dienst set van Dienstbundel
     */
    public void setDienstSet(final Set<Dienst> dienstSet) {
        this.dienstSet = dienstSet;
    }

    /**
     * Voeg dienst toe aan dienstSet.
     *
     * @param dienst dienst
     */
    public void addDienstSet(final Dienst dienst) {
        getDienstSet().add(dienst);
        dienst.setDienstbundel(this);
    }

    /**
     * Verwijderd de gegeven dienst uit dienstSet.
     *
     * @param dienst de te verwijderen dienst
     * @return true als deze dienst uit de dienstSet is verwijderd, anders false
     */
    public boolean removeDienstSet(final Dienst dienst) {
        final boolean result = getDienstSet().remove(dienst);
        dienst.setDienstbundel(null);
        return result;
    }

    /**
     * Geef de waarde van leveringsautorisatie van Dienstbundel.
     *
     * @return de waarde van leveringsautorisatie van Dienstbundel
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarden voor leveringsautorisatie van Dienstbundel.
     *
     * @param leveringsautorisatie de nieuwe waarde voor leveringsautorisatie van Dienstbundel
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie mag niet null zijn", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Geef de waarde van dienstbundel groep set van Dienstbundel.
     *
     * @return de waarde van dienstbundel groep set van Dienstbundel
     */
    public Set<DienstbundelGroep> getDienstbundelGroepSet() {
        return dienstbundelGroepSet;
    }

    /**
     * Zet de waarden voor dienstbundel groep set van Dienstbundel.
     *
     * @param dienstbundelGroepSet de nieuwe waarde voor dienstbundel groep set van Dienstbundel
     */
    public void setDienstbundelGroepSet(final Set<DienstbundelGroep> dienstbundelGroepSet) {
        this.dienstbundelGroepSet = dienstbundelGroepSet;
    }

    /**
     * Voeg dienstbundelGroep toe aan dienstbundelGroepSet.
     *
     * @param dienstbundelGroep dienstbundelGroep
     */
    public void addDienstbundelGroepSet(final DienstbundelGroep dienstbundelGroep) {
        getDienstbundelGroepSet().add(dienstbundelGroep);
        dienstbundelGroep.setDienstbundel(this);
    }

    /**
     * Verwijderd een dienstbundelGroep uit de dienstbundelGroepSet.
     *
     * @param dienstbundelGroep de te verwijderen dienstbundelGroep
     * @return true als de gegeven dienstbundelGroep verwijderd is, anders false
     */
    public boolean removeDienstbundelGroepSet(final DienstbundelGroep dienstbundelGroep) {
        final boolean result = getDienstbundelGroepSet().remove(dienstbundelGroep);
        dienstbundelGroep.setDienstbundel(null);
        return result;
    }

    /**
     * Geef de waarde van dienstbundel lo3 rubriek set van Dienstbundel.
     *
     * @return de waarde van dienstbundel lo3 rubriek set van Dienstbundel
     */
    public Set<DienstbundelLo3Rubriek> getDienstbundelLo3RubriekSet() {
        return dienstbundelLo3RubriekSet;
    }

    /**
     * Zet de waarden voor dienstbundel lo3 rubriek set van Dienstbundel.
     *
     * @param dienstbundelLo3RubriekSet de nieuwe waarde voor dienstbundel lo3 rubriek set van
     *        Dienstbundel
     */
    public void setDienstbundelLo3RubriekSet(final Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet) {
        this.dienstbundelLo3RubriekSet = dienstbundelLo3RubriekSet;
    }

    /**
     * Voeg dienstbundelLo3Rubriek toe aan dienstbundelLo3RubriekSet.
     *
     * @param dienstbundelLo3Rubriek dienstbundelLo3Rubriek
     */
    public void addDienstbundelLo3RubriekSet(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        getDienstbundelLo3RubriekSet().add(dienstbundelLo3Rubriek);
        dienstbundelLo3Rubriek.setDienstbundel(this);
    }

    /**
     * Verwijderd de gegeven dienstbundelLo3Rubriek uit dienstbundelLo3RubriekSet.
     *
     * @param dienstbundelLo3Rubriek dienstbundelLo3Rubriek
     * @return true als de gegeven dienstbundelLo3Rubriek is verwijderd, anders false
     */
    public boolean removeDienstbundelLo3RubriekSet(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek) {
        final boolean result = getDienstbundelLo3RubriekSet().remove(dienstbundelLo3Rubriek);
        dienstbundelLo3Rubriek.setDienstbundel(null);
        return result;
    }

    /**
     * Geef de waarde van dienstbundel historie set van Dienstbundel.
     *
     * @return de waarde van dienstbundel historie set van Dienstbundel
     */
    public Set<DienstbundelHistorie> getDienstbundelHistorieSet() {
        return dienstbundelHistorieSet;
    }

    /**
     * Zet de waarden voor dienstbundel historie set van Dienstbundel.
     *
     * @param dienstbundelHistorieSet de nieuwe waarde voor dienstbundel historie set van
     *        Dienstbundel
     */
    public void setDienstbundelHistorieSet(final Set<DienstbundelHistorie> dienstbundelHistorieSet) {
        this.dienstbundelHistorieSet = dienstbundelHistorieSet;
    }

    /**
     * Voeg dienstbundelHistorie toe aan de dienstbundelHistorieSet.
     *
     * @param dienstbundelHistorie dienstbundelHistorie
     */
    public void addDienstbundelHistorieSet(final DienstbundelHistorie dienstbundelHistorie) {
        getDienstbundelHistorieSet().add(dienstbundelHistorie);
        dienstbundelHistorie.setDienstbundel(this);
    }

    /**
     * Verwijder dienstbundelHistorie uit dienstbundelHistorieSet.
     *
     * @param dienstbundelHistorie de te verwijderen dienstbundelHistorie
     * @return true als de dienstbundelHistorie is verwijderd anders false
     */
    public boolean removeDienstbundelHistorieSet(final DienstbundelHistorie dienstbundelHistorie) {
        final boolean result = getDienstbundelHistorieSet().remove(dienstbundelHistorie);
        dienstbundelHistorie.setDienstbundel(null);
        return result;
    }
}

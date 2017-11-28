/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the dienst database table.
 *
 */
@Entity
@Table(name = "dienst", schema = "autaut")
@NamedQuery(name = "Dienst" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM Dienst d")
@Access(AccessType.FIELD)
public class Dienst implements Afleidbaar, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienst_id_generator", sequenceName = "autaut.seq_dienst", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienst_id_generator")
    @Column(nullable = false, updatable = false)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(name = "attenderingscriterium")
    private String attenderingscriterium;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "eersteseldat")
    private Integer eersteSelectieDatum;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "maxaantalzoekresultaten")
    private Integer maximumAantalZoekresultaten;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    @Column(name = "effectafnemerindicaties")
    private Integer effectAfnemerindicatiesId;

    @Column(name = "srt", nullable = false)
    private int soortDienstId;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @Column(name = "indagattendering", nullable = false)
    private boolean isActueelEnGeldigVoorAttendering;

    @Column(name = "indagsel", nullable = false)
    private boolean isActueelEnGeldigVoorSelectie;

    @Column(name = "indagzoeken", nullable = false)
    private boolean isActueelEnGeldigVoorZoeken;

    @Column(name = "srtsel")
    private Integer soortSelectie;

    @Column(name = "Selinterval")
    private Integer selectieInterval;

    @Column(name = "EenheidSelinterval")
    private Integer eenheidSelectieInterval;

    @Column(name = "NadereSelcriterium")
    private String nadereSelectieCriterium;

    @Column(name = "SelPeilmomMaterieelResultaat")
    private Integer selectiePeilmomentMaterieelResultaat;

    @Column(name = "SelPeilmomFormeelResultaat")
    private Timestamp selectiePeilmomentFormeelResultaat;

    @Column(name = "HistorievormSel")
    private Integer historievormSelectie;

    @Column(name = "IndSelresultaatControleren")
    private Boolean indicatieSelectieresultaatControleren;
    
    @Column(name = "MaxAantalPersPerSelbestand")
    private Integer maxAantalPersonenPerSelectiebestand;
    
    @Column(name = "MaxGrootteSelbestand")
    private Integer maxGrootteSelectiebestand;
    
    @Column(name = "IndVerzVolBerBijWijzAfniNaSe")
    private Boolean indVerzVolBerBijWijzAfniNaSelectie;

    @Column(name = "LeverwijzeSel")
    private Integer leverwijzeSelectie;

    // bi-directional many-to-one association to DienstHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstHistorie> dienstHistorieSet = new LinkedHashSet<>();

    // bi-directional many-to-one association to DienstAttenderingHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorAttendering")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstAttenderingHistorie> dienstAttenderingHistorieSet = new LinkedHashSet<>();

    // bi-directional many-to-one association to DienstSelectieHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorSelectie")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstSelectieHistorie> dienstSelectieHistorieSet = new LinkedHashSet<>();

    // bi-directional many-to-one association to DienstZoekenHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldigVoorZoeken")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstZoekenHistorie> dienstZoekenHistorieSet = new LinkedHashSet<>();

    /**
     * JPA No-args constructor.
     */
    Dienst() {}

    /**
     * Maakt een Dienst object.
     *
     * @param dienstbundel dienstbundel
     * @param soortDienst soort dienst
     */
    public Dienst(final Dienstbundel dienstbundel, final SoortDienst soortDienst) {
        setDienstbundel(dienstbundel);
        setSoortDienst(soortDienst);
    }

    /**
     * Geef de waarde van id van Dienst.
     *
     * @return de waarde van id van Dienst
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Dienst.
     *
     * @param id de nieuwe waarde voor id van Dienst
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van attenderingscriterium van Dienst.
     *
     * @return de waarde van attenderingscriterium van Dienst
     */
    public String getAttenderingscriterium() {
        return attenderingscriterium;
    }

    /**
     * Zet de waarden voor attenderingscriterium van Dienst.
     *
     * @param attenderingscriterium de nieuwe waarde voor attenderingscriterium van Dienst
     */
    public void setAttenderingscriterium(final String attenderingscriterium) {
        this.attenderingscriterium = attenderingscriterium;
    }

    /**
     * Geef de waarde van datum einde van Dienst.
     *
     * @return de waarde van datum einde van Dienst
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van Dienst.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van Dienst
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van Dienst.
     *
     * @return de waarde van datum ingang van Dienst
     */
    public Integer getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van Dienst.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van Dienst
     */
    public void setDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van eerste selectie datum van Dienst.
     *
     * @return de waarde van eerste selectie datum van Dienst
     */
    public Integer getEersteSelectieDatum() {
        return eersteSelectieDatum;
    }

    /**
     * Zet de waarden voor eerste selectie datum van Dienst.
     *
     * @param eersteSelectieDatum de nieuwe waarde voor eerste selectie datum van Dienst
     */
    public void setEersteSelectieDatum(final Integer eersteSelectieDatum) {
        this.eersteSelectieDatum = eersteSelectieDatum;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van Dienst.
     *
     * @return de waarde van indicatie geblokkeerd van Dienst
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van Dienst.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van Dienst
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van maximumAantalZoekresultaten.
     *
     * @return maximumAantalZoekresultaten
     */
    public Integer getMaximumAantalZoekresultaten() {
        return maximumAantalZoekresultaten;
    }

    /**
     * Zet de waarde van maximumAantalZoekresultaten.
     *
     * @param maximumAantalZoekresultaten maximumAantalZoekresultaten
     */
    public void setMaximumAantalZoekresultaten(final Integer maximumAantalZoekresultaten) {
        this.maximumAantalZoekresultaten = maximumAantalZoekresultaten;
    }

    /**
     * Geef de waarde van dienstbundel van Dienst.
     *
     * @return de waarde van dienstbundel van Dienst
     */
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Zet de waarden voor dienstbundel van Dienst.
     *
     * @param dienstbundel de nieuwe waarde voor dienstbundel van Dienst
     */
    public void setDienstbundel(final Dienstbundel dienstbundel) {
        ValidationUtils.controleerOpNullWaarden("dienstbundel mag niet null zijn", dienstbundel);
        this.dienstbundel = dienstbundel;
    }

    /**
     * Geef de waarde van effect afnemerindicaties van Dienst.
     *
     * @return de waarde van effect afnemerindicaties van Dienst
     */
    public EffectAfnemerindicaties getEffectAfnemerindicaties() {
        if (effectAfnemerindicatiesId == null) {
            return null;
        } else {
            return EffectAfnemerindicaties.parseId(effectAfnemerindicatiesId);
        }
    }

    /**
     * Zet de waarden voor effect afnemerindicaties van Dienst.
     *
     * @param effectAfnemerindicaties de nieuwe waarde voor effect afnemerindicaties van Dienst
     */
    public void setEffectAfnemerindicaties(final EffectAfnemerindicaties effectAfnemerindicaties) {
        if (effectAfnemerindicaties == null) {
            effectAfnemerindicatiesId = null;
        } else {
            effectAfnemerindicatiesId = effectAfnemerindicaties.getId();
        }
    }

    /**
     * Geef de waarde van soort dienst van Dienst.
     *
     * @return de waarde van soort dienst van Dienst
     */
    public SoortDienst getSoortDienst() {
        return SoortDienst.parseId(soortDienstId);
    }

    /**
     * Zet de waarden voor soort dienst van Dienst.
     *
     * @param soortDienst de nieuwe waarde voor soort dienst van Dienst
     */
    public void setSoortDienst(final SoortDienst soortDienst) {
        ValidationUtils.controleerOpNullWaarden("soortDienst mag niet null zijn", soortDienst);
        soortDienstId = soortDienst.getId();
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
     * Geef de waarde isActueelEnGeldigVoorAttendering.
     *
     * @return isActueelEnGeldigVoorAttendering
     */
    public boolean isActueelEnGeldigVoorAttendering() {
        return isActueelEnGeldigVoorAttendering;
    }

    /**
     * Zet de waarde van isActueelEnGeldigVoorAttendering.
     *
     * @param actueelEnGeldigVoorAttendering isActueelEnGeldigVoorAttendering
     */
    public void setActueelEnGeldigVoorAttendering(final boolean actueelEnGeldigVoorAttendering) {
        isActueelEnGeldigVoorAttendering = actueelEnGeldigVoorAttendering;
    }

    /**
     * Geef de waarde van isActueelEnGeldigVoorSelectie.
     *
     * @return isActueelEnGeldigVoorSelectie
     */
    public boolean isActueelEnGeldigVoorSelectie() {
        return isActueelEnGeldigVoorSelectie;
    }

    /**
     * Zet de waarde van isActueelEnGeldigVoorSelectie.
     *
     * @param actueelEnGeldigVoorSelectie isActueelEnGeldigVoorSelectie
     */
    public void setActueelEnGeldigVoorSelectie(final boolean actueelEnGeldigVoorSelectie) {
        isActueelEnGeldigVoorSelectie = actueelEnGeldigVoorSelectie;
    }

    /**
     * Geef de waarde van isActueelEnGeldigVoorZoeken.
     *
     * @return isActueelEnGeldigVoorZoeken
     */
    public boolean isActueelEnGeldigVoorZoeken() {
        return isActueelEnGeldigVoorZoeken;
    }

    /**
     * Zet de waarde van isActueelEnGeldigVoorZoeken.
     *
     * @param actueelEnGeldigVoorZoeken isActueelEnGeldigVoorZoeken
     */
    public void setActueelEnGeldigVoorZoeken(final boolean actueelEnGeldigVoorZoeken) {
        isActueelEnGeldigVoorZoeken = actueelEnGeldigVoorZoeken;
    }

    /**
     * Geef de waarde van dienst historie set van Dienst.
     *
     * @return de waarde van dienst historie set van Dienst
     */
    public Set<DienstHistorie> getDienstHistorieSet() {
        return dienstHistorieSet;
    }

    /**
     * Zet de waarden voor dienst historie set van Dienst.
     *
     * @param dienstHistorieSet de nieuwe waarde voor dienst historie set van Dienst
     */
    public void setDienstHistorieSet(final Set<DienstHistorie> dienstHistorieSet) {
        this.dienstHistorieSet = dienstHistorieSet;
    }

    /**
     * Voegt een dienstHistorie toe aan dienstHistorieSet.
     *
     * @param dienstHistorie dienstHistorie
     */
    public void addDienstHistorieSet(final DienstHistorie dienstHistorie) {
        getDienstHistorieSet().add(dienstHistorie);
        dienstHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstHistorie uit dienstHistorieSet.
     *
     * @param dienstHistorie de te verwijderen dienstHistorie
     * @return true als dit element uit dienstHistorieSet verwijderd is, anders false
     */
    public boolean removeDienstHistorieSet(final DienstHistorie dienstHistorie) {
        final boolean result = getDienstHistorieSet().remove(dienstHistorie);
        dienstHistorie.setDienst(null);

        return result;
    }

    /**
     * Geef de waarde van dienst attendering historie set van Dienst.
     *
     * @return de waarde van dienst attendering historie set van Dienst
     */
    public Set<DienstAttenderingHistorie> getDienstAttenderingHistorieSet() {
        return dienstAttenderingHistorieSet;
    }

    /**
     * Zet de waarden voor dienst attendering historie set van Dienst.
     *
     * @param dienstAttenderingHistorieSet de nieuwe waarde voor dienst attendering historie set van
     *        Dienst
     */
    public void setDienstAttenderingHistorieSet(final Set<DienstAttenderingHistorie> dienstAttenderingHistorieSet) {
        this.dienstAttenderingHistorieSet = dienstAttenderingHistorieSet;
    }

    /**
     * Voegt een dienstAttenderingHistorie toe aan dienstAttenderingHistorieSet.
     *
     * @param dienstAttenderingHistorie dienstAttenderingHistorie
     */
    public void addDienstAttenderingHistorieSet(final DienstAttenderingHistorie dienstAttenderingHistorie) {
        getDienstAttenderingHistorieSet().add(dienstAttenderingHistorie);
        dienstAttenderingHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstAttenderingHistorie uit dienstAttenderingHistorieSet.
     *
     * @param dienstAttenderingHistorie dienstAttenderingHistorie
     * @return true als de gegeven dienstAttenderingHistorie verwijderd is uit
     *         dienstAttenderingHistorieSet, anders false
     */
    public boolean removeDienstAttenderingHistorieSet(final DienstAttenderingHistorie dienstAttenderingHistorie) {
        final boolean result = getDienstAttenderingHistorieSet().remove(dienstAttenderingHistorie);
        dienstAttenderingHistorie.setDienst(null);
        return result;
    }

    /**
     * Geef de waarde van dienst selectie historie set van Dienst.
     *
     * @return de waarde van dienst selectie historie set van Dienst
     */
    public Set<DienstSelectieHistorie> getDienstSelectieHistorieSet() {
        return dienstSelectieHistorieSet;
    }

    /**
     * Zet de waarden voor dienst selectie historie set van Dienst.
     *
     * @param dienstSelectieHistorieSet de nieuwe waarde voor dienst selectie historie set van
     *        Dienst
     */
    public void setDienstSelectieHistorieSet(final Set<DienstSelectieHistorie> dienstSelectieHistorieSet) {
        this.dienstSelectieHistorieSet = dienstSelectieHistorieSet;
    }

    /**
     * Voegt een dienstSelectieHistorie toe aan de dienstSelectieHistorieSet.
     *
     * @param dienstSelectieHistorie dienstSelectieHistorie
     */
    public void addDienstSelectieHistorieSet(final DienstSelectieHistorie dienstSelectieHistorie) {
        getDienstSelectieHistorieSet().add(dienstSelectieHistorie);
        dienstSelectieHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstSelectieHistorie uit de dienstSelectieHistorieSet.
     *
     * @param dienstSelectieHistorie dienstSelectieHistorie
     * @return true als de gegeven dienstSelectieHistorie verwijderd is uit
     *         dienstSelectieHistorieSet, anders false
     */
    public boolean removeDienstSelectieHistorieSet(final DienstSelectieHistorie dienstSelectieHistorie) {
        return getDienstSelectieHistorieSet().remove(dienstSelectieHistorie);
    }

    /**
     * Geef de waarde van dienst zoeken historie set van Dienst.
     *
     * @return de waarde van dienst zoeken historie set van Dienst
     */
    public Set<DienstZoekenHistorie> getDienstZoekenHistorieSet() {
        return dienstZoekenHistorieSet;
    }

    /**
     * Zet de waarden voor dienst zoeken historie set van Dienst.
     *
     * @param dienstZoekenHistorieSet de nieuwe waarde voor dienst zoeken historie set van Dienst
     */
    public void setDienstZoekenHistorieSet(final Set<DienstZoekenHistorie> dienstZoekenHistorieSet) {
        this.dienstZoekenHistorieSet = dienstZoekenHistorieSet;
    }

    /**
     * Voegt een dienstZoekenHistorie toe aan de dienstZoekenHistorieSet.
     *
     * @param dienstZoekenHistorie dienstZoekenHistorie
     */
    public void addDienstZoekenHistorieSet(final DienstZoekenHistorie dienstZoekenHistorie) {
        dienstZoekenHistorieSet.add(dienstZoekenHistorie);
        dienstZoekenHistorie.setDienst(this);
    }

    /**
     * Verwijderd een dienstZoekenHistorie uit de dienstZoekenHistorieSet.
     *
     * @param dienstZoekenHistorie dienstZoekenHistorie
     * @return true als de gegeven dienstZoekenHistorie verwijderd is uit dienstZoekenHistorieSet,
     *         anders false
     */
    public boolean removeDienstZoekenHistorieSet(final DienstZoekenHistorie dienstZoekenHistorie) {
        return dienstZoekenHistorieSet.remove(dienstZoekenHistorie);
    }

    /**
     * Geef de waarde van soortSelectie.
     *
     * @return soortSelectie
     */
    public Integer getSoortSelectie() {
        return soortSelectie;
    }

    /**
     * Zet de waarde van soortSelectie.
     *
     * @param soortSelectie soortSelectie
     */
    public void setSoortSelectie(final Integer soortSelectie) {
        this.soortSelectie = soortSelectie;
    }

    /**
     * Geef de waarde van selectieInterval.
     *
     * @return selectieInterval
     */
    public Integer getSelectieInterval() {
        return selectieInterval;
    }

    /**
     * Zet de waarde van selectieInterval.
     *
     * @param selectieInterval selectieInterval
     */
    public void setSelectieInterval(final Integer selectieInterval) {
        this.selectieInterval = selectieInterval;
    }

    /**
     * Geef de waarde van eenheidSelectieInterval.
     *
     * @return eenheidSelectieInterval
     */
    public Integer getEenheidSelectieInterval() {
        return eenheidSelectieInterval;
    }

    /**
     * Zet de waarde van eenheidSelectieInterval.
     *
     * @param eenheidSelectieInterval eenheidSelectieInterval
     */
    public void setEenheidSelectieInterval(final Integer eenheidSelectieInterval) {
        this.eenheidSelectieInterval = eenheidSelectieInterval;
    }

    /**
     * Geef de waarde van nadereSelectieCriterium.
     *
     * @return nadereSelectieCriterium
     */
    public String getNadereSelectieCriterium() {
        return nadereSelectieCriterium;
    }

    /**
     * Zet de waarde van nadereSelectieCriterium.
     *
     * @param nadereSelectieCriterium nadereSelectieCriterium
     */
    public void setNadereSelectieCriterium(final String nadereSelectieCriterium) {
        this.nadereSelectieCriterium = nadereSelectieCriterium;
    }

    /**
     * Geef de waarde van selectiePeilmomentMaterieelResultaat.
     *
     * @return selectiePeilmomentMaterieelResultaat
     */
    public Integer getSelectiePeilmomentMaterieelResultaat() {
        return selectiePeilmomentMaterieelResultaat;
    }

    /**
     * Zet de waarde van selectiePeilmomentMaterieelResultaat.
     *
     * @param selectiePeilmomentMaterieelResultaat selectiePeilmomentMaterieelResultaat
     */
    public void setSelectiePeilmomentMaterieelResultaat(final Integer selectiePeilmomentMaterieelResultaat) {
        this.selectiePeilmomentMaterieelResultaat = selectiePeilmomentMaterieelResultaat;
    }

    /**
     * Geef de waarde van selectiePeilmomentFormeelResultaat.
     *
     * @return selectiePeilmomentFormeelResultaat
     */
    public Timestamp getSelectiePeilmomentFormeelResultaat() {
        return selectiePeilmomentFormeelResultaat;
    }

    /**
     * Zet de waarde van selectiePeilmomentFormeelResultaat.
     *
     * @param selectiePeilmomentFormeelResultaat selectiePeilmomentFormeelResultaat
     */
    public void setSelectiePeilmomentFormeelResultaat(final Timestamp selectiePeilmomentFormeelResultaat) {
        this.selectiePeilmomentFormeelResultaat = selectiePeilmomentFormeelResultaat;
    }

    /**
     * Geef de waarde van historievormSelectie.
     *
     * @return historievormSelectie
     */
    public Integer getHistorievormSelectie() {
        return historievormSelectie;
    }

    /**
     * Zet de waarde van historievormSelectie.
     *
     * @param historievormSelectie historievormSelectie
     */
    public void setHistorievormSelectie(final Integer historievormSelectie) {
        this.historievormSelectie = historievormSelectie;
    }

    /**
     * Geef de waarde van indicatieSelectieresultaatControleren.
     *
     * @return indicatieSelectieresultaatControleren
     */
    public Boolean getIndicatieSelectieresultaatControleren() {
        return indicatieSelectieresultaatControleren;
    }

    /**
     * Zet de waarde van indicatieSelectieresultaatControleren.
     *
     * @param indicatieSelectieresultaatControleren indicatieSelectieresultaatControleren
     */
    public void setIndicatieSelectieresultaatControleren(final Boolean indicatieSelectieresultaatControleren) {
        this.indicatieSelectieresultaatControleren = indicatieSelectieresultaatControleren;
    }

    /**
     * Geef de waarde van maxAantalPersonenPerSelectiebestand.
     *
     * @return maxAantalPersonenPerSelectiebestand
     */
    public Integer getMaxAantalPersonenPerSelectiebestand() {
        return maxAantalPersonenPerSelectiebestand;
    }

    /**
     * Zet de waarde van maxAantalPersonenPerSelectiebestand.
     *
     * @param maxAantalPersonenPerSelectiebestand maxAantalPersonenPerSelectiebestand
     */
    public void setMaxAantalPersonenPerSelectiebestand(final Integer maxAantalPersonenPerSelectiebestand) {
        this.maxAantalPersonenPerSelectiebestand = maxAantalPersonenPerSelectiebestand;
    }

    /**
     * Geef de waarde van maxGrootteSelectiebestand.
     *
     * @return maxGrootteSelectiebestand
     */
    public Integer getMaxGrootteSelectiebestand() {
        return maxGrootteSelectiebestand;
    }

    /**
     * Zet de waarde van maxGrootteSelectiebestand.
     *
     * @param maxGrootteSelectiebestand maxGrootteSelectiebestand
     */
    public void setMaxGrootteSelectiebestand(final Integer maxGrootteSelectiebestand) {
        this.maxGrootteSelectiebestand = maxGrootteSelectiebestand;
    }

    /**
     * Geef de waarde van indVerzVolBerBijWijzAfniNaSelectie.
     *
     * @return indVerzVolBerBijWijzAfniNaSelectie
     */
    public Boolean getIndVerzVolBerBijWijzAfniNaSelectie() {
        return indVerzVolBerBijWijzAfniNaSelectie;
    }

    /**
     * Zet de waarde van indVerzVolBerBijWijzAfniNaSelectie.
     *
     * @param indVerzVolBerBijWijzAfniNaSelectie indVerzVolBerBijWijzAfniNaSelectie
     */
    public void setIndVerzVolBerBijWijzAfniNaSelectie(final Boolean indVerzVolBerBijWijzAfniNaSelectie) {
        this.indVerzVolBerBijWijzAfniNaSelectie = indVerzVolBerBijWijzAfniNaSelectie;
    }

    /**
     * Geef de waarde van leverwijzeSelectie.
     *
     * @return leverwijzeSelectie
     */
    public Integer getLeverwijzeSelectie() {
        return leverwijzeSelectie;
    }

    /**
     * Zet de waarde van leverwijzeSelectie.
     *
     * @param leverwijzeSelectie leverwijzeSelectie
     */
    public void setLeverwijzeSelectie(final Integer leverwijzeSelectie) {
        this.leverwijzeSelectie = leverwijzeSelectie;
    }
}

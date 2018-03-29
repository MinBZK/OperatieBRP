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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.annotation.IndicatieActueelEnGeldig;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the seltaak database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "seltaak", schema = "autaut")
public class Selectietaak extends AbstractEntiteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seltaak_id_generator", sequenceName = "autaut.seq_seltaak", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seltaak_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "toeganglevsautorisatie", nullable = false)
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    @Column(name = "datplanning")
    private Integer datumPlanning;

    @Column(name = "datuitvoer")
    private Integer datumUitvoer;

    @Column(name = "peilmommaterieel")
    private Integer peilmomentMaterieel;

    @Column(name = "peilmommaterieelresultaat")
    private Integer peilmomentMaterieelResultaat;

    @Column(name = "peilmomformeelresultaat")
    private Timestamp peilmomentFormeelResultaat;

    @Column(name = "status")
    private Short status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uitgevoerdin")
    private Selectierun uitgevoerdIn;

    @Column(name = "indag", nullable = false)
    private boolean isActueelEnGeldig;

    @Column(name = "indSelLijstGebruiken")
    private Boolean indicatieSelectielijstGebruiken;

    @Column(name = "volgnr", nullable = false)
    private Integer volgnummer;

    @Column(name = "statustoelichting")
    private String statusToelichting;

    @Column(name = "indagstatus", nullable = false)
    private boolean isActueelEnGeldigStatus;

    // bi-directional many-to-one association to SelectietaakHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = "selectietaak", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SelectietaakHistorie> selectietaakHistorieSet = new LinkedHashSet<>(0);

    // bi-directional many-to-one association to SelectietaakStatusHistorie
    @IndicatieActueelEnGeldig(naam = "isActueelEnGeldig")
    @OneToMany(mappedBy = "selectietaak", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SelectietaakStatusHistorie> selectietaakStatusHistorieSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected Selectietaak() {
    }

    /**
     * Maak een nieuwe Selectietaak.
     * @param dienst dienst
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param volgnummer volgnummer
     */
    public Selectietaak(final Dienst dienst, final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, final Integer volgnummer) {
        setDienst(dienst);
        setToegangLeveringsAutorisatie(toegangLeveringsAutorisatie);
        setVolgnummer(volgnummer);
    }

    /**
     * @return het ID.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Verblijfsrecht.
     * @param id de nieuwe waarde voor id van Verblijfsrecht
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van isActueelEnGeldig.
     * @return isActueelEnGeldig
     */
    public boolean isActueelEnGeldig() {
        return isActueelEnGeldig;
    }

    /**
     * Zet de waarde van isActueelEnGeldig.
     * @param actueelEnGeldig isActueelEnGeldig
     */
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        isActueelEnGeldig = actueelEnGeldig;
    }

    /**
     * dienst
     * @param dienst de nieuwe dienst
     */
    public void setDienst(final Dienst dienst) {
        ValidationUtils.controleerOpNullWaarden("dienst mag niet null zijn", dienst);
        this.dienst = dienst;
    }

    /**
     * Geef de waarde van dienst.
     * @return dienst
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * toegangLeveringsAutorisatie
     * @param toegangLeveringsAutorisatie de nieuwe toegangLeveringsAutorisatie
     */
    public void setToegangLeveringsAutorisatie(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        ValidationUtils.controleerOpNullWaarden("toegangLeveringsAutorisatie mag niet null zijn", toegangLeveringsAutorisatie);
        this.toegangLeveringsAutorisatie = toegangLeveringsAutorisatie;
    }

    /**
     * Geef de waarde van toegangLeveringsAutorisatie.
     * @return toegangLeveringsAutorisatie
     */
    public ToegangLeveringsAutorisatie getToegangLeveringsAutorisatie() {
        return toegangLeveringsAutorisatie;
    }

    /**
     * Geef de waarde van datumPlanning.
     * @return datumPlanning
     */
    public Integer getDatumPlanning() {
        return datumPlanning;
    }

    /**
     * Zet de waarde van datumPlanning.
     * @param datumPlanning datumPlanning
     */
    public void setDatumPlanning(final Integer datumPlanning) {
        this.datumPlanning = datumPlanning;
    }

    /**
     * Geef de waarde van datumUitvoer.
     * @return datumUitvoer
     */
    public Integer getDatumUitvoer() {
        return datumUitvoer;
    }

    /**
     * Zet de waarde van datumUitvoer.
     * @param datumUitvoer datumUitvoer
     */
    public void setDatumUitvoer(final Integer datumUitvoer) {
        this.datumUitvoer = datumUitvoer;
    }

    /**
     * Geef de waarde van peilmomentMaterieel.
     * @return peilmomentMaterieel
     */
    public Integer getPeilmomentMaterieel() {
        return peilmomentMaterieel;
    }

    /**
     * Zet de waarde van peilmomentMaterieel.
     * @param peilmomentMaterieel peilmomentMaterieel
     */
    public void setPeilmomentMaterieel(final Integer peilmomentMaterieel) {
        this.peilmomentMaterieel = peilmomentMaterieel;
    }

    /**
     * Geef de waarde van peilmomentMaterieelResultaat.
     * @return peilmomentMaterieelResultaat
     */
    public Integer getPeilmomentMaterieelResultaat() {
        return peilmomentMaterieelResultaat;
    }

    /**
     * Zet de waarde van peilmomentMaterieelResultaat.
     * @param peilmomentMaterieelResultaat peilmomentMaterieelResultaat
     */
    public void setPeilmomentMaterieelResultaat(final Integer peilmomentMaterieelResultaat) {
        this.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
    }

    /**
     * Geef de waarde van peilmomentFormeelResultaat.
     * @return peilmomentFormeelResultaat
     */
    public Timestamp getPeilmomentFormeelResultaat() {
        return peilmomentFormeelResultaat;
    }

    /**
     * Zet de waarde van peilmomentFormeelResultaat.
     * @param peilmomentFormeelResultaat peilmomentFormeelResultaat
     */
    public void setPeilmomentFormeelResultaat(final Timestamp peilmomentFormeelResultaat) {
        this.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
    }

    /**
     * Geef de waarde van status.
     * @return status
     */
    public Short getStatus() {
        return status;
    }


    /**
     * Zet de waarde van status.
     * @param status status
     */
    public void setStatus(final Short status) {
        this.status = status;
    }

    /**
     * Geef de waarde van uitgevoerdIn.
     * @return uitgevoerdIn
     */
    public Selectierun getUitgevoerdIn() {
        return uitgevoerdIn;
    }

    /**
     * Zet de waarde van uitgevoerdIn.
     * @param uitgevoerdIn uitgevoerdIn
     */
    public void setUitgevoerdIn(final Selectierun uitgevoerdIn) {
        this.uitgevoerdIn = uitgevoerdIn;
    }

    /**
     * @return historieset
     */
    public Set<SelectietaakHistorie> getSelectietaakHistorieSet() {
        return selectietaakHistorieSet;
    }

    /**
     * @param selectietaakHistorieSet selectietaakHistorieSet
     */
    public void setSelectietaakHistorieSet(Set<SelectietaakHistorie> selectietaakHistorieSet) {
        this.selectietaakHistorieSet = selectietaakHistorieSet;
    }

    /**
     * Voeg SelectietaakHistorie toe aan selectietaakHistorieSet.
     * @param selectietaakHistorie selectietaakHistorie
     */
    public void addSelectietaakHistorieSet(final SelectietaakHistorie selectietaakHistorie) {
        getSelectietaakHistorieSet().add(selectietaakHistorie);
        selectietaakHistorie.setSelectietaak(this);
    }

    /**
     * @return historieset
     */
    public Set<SelectietaakStatusHistorie> getSelectietaakStatusHistorieSet() {
        return selectietaakStatusHistorieSet;
    }

    /**
     * @param selectietaakStatusHistorieSet selectietaakStatusHistorieSet
     */
    public void setSelectietaakStatusHistorieSet(Set<SelectietaakStatusHistorie> selectietaakStatusHistorieSet) {
        this.selectietaakStatusHistorieSet = selectietaakStatusHistorieSet;
    }

    /**
     * Voeg SelectietaakStatusHistorie toe aan selectietaakStatusHistorieSet.
     * @param selectietaakStatusHistorie selectietaakStatusHistorieistorie
     */
    public void addSelectietaakStatusHistorieSet(final SelectietaakStatusHistorie selectietaakStatusHistorie) {
        getSelectietaakStatusHistorieSet().add(selectietaakStatusHistorie);
        selectietaakStatusHistorie.setSelectietaak(this);
    }

    /**
     * @return volgnummer
     */
    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * @param volgnummer volgnummer
     */
    public void setVolgnummer(Integer volgnummer) {
        ValidationUtils.controleerOpNullWaarden("volgnummer mag niet null zijn", dienst);
        this.volgnummer = volgnummer;
    }

    /**
     * @return indicatieSelectielijstGebruiken
     */
    public Boolean isIndicatieSelectielijstGebruiken() {
        return indicatieSelectielijstGebruiken;
    }

    /**
     * @param indicatieSelectielijstGebruiken indicatieSelectielijstGebruiken
     */
    public void setIndicatieSelectielijstGebruiken(Boolean indicatieSelectielijstGebruiken) {
        this.indicatieSelectielijstGebruiken = indicatieSelectielijstGebruiken;
    }

    /**
     * @return statustoelichting
     */
    public String getStatusToelichting() {
        return statusToelichting;
    }

    /**
     * Zet de statustoelichting.
     * @param statusToelichting de statustoelichting
     */
    public void setStatusToelichting(String statusToelichting) {
        this.statusToelichting = statusToelichting;
    }

    /**
     *@return indicatie actueel en gelig status
     */
    public boolean isActueelEnGeldigStatus() {
        return isActueelEnGeldigStatus;
    }

    /**
     * Zet de indicatie actueel en geldig status.
     * @param actueelEnGeldigStatus de indicatie
     */
    public void setActueelEnGeldigStatus(boolean actueelEnGeldigStatus) {
        isActueelEnGeldigStatus = actueelEnGeldigStatus;
    }
}

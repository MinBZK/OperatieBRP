/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * De persistent class for the his_seltaak database table.
 */
@Entity
@Table(name = "his_seltaak", schema = "autaut")
public class SelectietaakHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_seltaak_id_generator", sequenceName = "autaut.seq_his_seltaak", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_seltaak_id_generator")
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seltaak", nullable = false)
    private Selectietaak selectietaak;

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

    @Column(name = "indSelLijstGebruiken", nullable = false)
    private boolean indicatieSelectielijstGebruiken;

    /**
     * JPA default constructor.
     */
    protected SelectietaakHistorie() {
    }

    /**
     * Kopie constructor. Maakt een nieuw object op basis van het gegeven bron object.
     * @param ander het te kopieren object
     */
    public SelectietaakHistorie(final SelectietaakHistorie ander) {
        this.selectietaak = ander.selectietaak;
        this.datumPlanning = ander.datumPlanning;
        this.datumUitvoer = ander.datumUitvoer;
        this.peilmomentMaterieel = ander.peilmomentMaterieel;
        this.peilmomentMaterieelResultaat = ander.peilmomentMaterieelResultaat;
        this.peilmomentFormeelResultaat = ander.peilmomentFormeelResultaat;
    }

    /**
     * Maak een nieuwe SelectietaakHistorie.
     * @param selectietaak selectietaak
     */
    public SelectietaakHistorie(final Selectietaak selectietaak) {
        setSelectietaak(selectietaak);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
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
     * selectietaak
     * @param selectietaak de nieuwe selectietaak
     */
    public void setSelectietaak(final Selectietaak selectietaak) {
        ValidationUtils.controleerOpNullWaarden("selectietaak mag niet null zijn", selectietaak);
        this.selectietaak = selectietaak;
    }

    /**
     * Geef de waarde van selectietaak.
     * @return selectietaak
     */
    public Selectietaak getSelectietaak() {
        return selectietaak;
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
     * @return indicatieSelectielijstGebruiken
     */
    public boolean isIndicatieSelectielijstGebruiken() {
        return indicatieSelectielijstGebruiken;
    }

    /**
     * @param indicatieSelectielijstGebruiken indicatieSelectielijstGebruiken
     */
    public void setIndicatieSelectielijstGebruiken(boolean indicatieSelectielijstGebruiken) {
        this.indicatieSelectielijstGebruiken = indicatieSelectielijstGebruiken;
    }
}

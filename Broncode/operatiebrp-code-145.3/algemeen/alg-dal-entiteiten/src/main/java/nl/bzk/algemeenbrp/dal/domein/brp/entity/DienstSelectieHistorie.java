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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_dienstsel database table.
 */
@Entity
@Table(name = "his_dienstsel", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienst", "tsreg"}))
@NamedQuery(name = "DienstSelectieHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstSelectieHistorie d")
public class DienstSelectieHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstsel_id_generator", sequenceName = "autaut.seq_his_dienstsel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstsel_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "eersteseldat")
    private Integer eersteSelectieDatum;

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

    // bi-directional many-to-one association to Dienst
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstSelectieHistorie() {
    }

    /**
     * Maakt een nieuw DienstSelectieHistorie object.
     * @param dienst dienst
     */
    public DienstSelectieHistorie(final Dienst dienst) {
        setDienst(dienst);
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
     * Zet de waarden voor id van DienstSelectieHistorie.
     * @param id de nieuwe waarde voor id van DienstSelectieHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van eerste selectie datum van DienstSelectieHistorie.
     * @return de waarde van eerste selectie datum van DienstSelectieHistorie
     */
    public Integer getEersteSelectieDatum() {
        return eersteSelectieDatum;
    }

    /**
     * Zet de waarden voor eerste selectie datum van DienstSelectieHistorie.
     * @param eersteSelectieDatum de nieuwe waarde voor eerste selectie datum van DienstSelectieHistorie
     */
    public void setEersteSelectieDatum(final Integer eersteSelectieDatum) {
        this.eersteSelectieDatum = eersteSelectieDatum;
    }

    /**
     * Geef de waarde van soortSelectie.
     * @return soortSelectie
     */
    public Integer getSoortSelectie() {
        return soortSelectie;
    }

    /**
     * Zet de waarde van soortSelectie.
     * @param soortSelectie soortSelectie
     */
    public void setSoortSelectie(final Integer soortSelectie) {
        this.soortSelectie = soortSelectie;
    }

    /**
     * Geef de waarde van selectieInterval.
     * @return selectieInterval
     */
    public Integer getSelectieInterval() {
        return selectieInterval;
    }

    /**
     * Zet de waarde van selectieInterval.
     * @param selectieInterval selectieInterval
     */
    public void setSelectieInterval(final Integer selectieInterval) {
        this.selectieInterval = selectieInterval;
    }

    /**
     * Geef de waarde van eenheidSelectieInterval.
     * @return eenheidSelectieInterval
     */
    public Integer getEenheidSelectieInterval() {
        return eenheidSelectieInterval;
    }

    /**
     * Zet de waarde van eenheidSelectieInterval.
     * @param eenheidSelectieInterval eenheidSelectieInterval
     */
    public void setEenheidSelectieInterval(final Integer eenheidSelectieInterval) {
        this.eenheidSelectieInterval = eenheidSelectieInterval;
    }

    /**
     * Geef de waarde van nadereSelectieCriterium.
     * @return nadereSelectieCriterium
     */
    public String getNadereSelectieCriterium() {
        return nadereSelectieCriterium;
    }

    /**
     * Zet de waarde van nadereSelectieCriterium.
     * @param nadereSelectieCriterium nadereSelectieCriterium
     */
    public void setNadereSelectieCriterium(final String nadereSelectieCriterium) {
        this.nadereSelectieCriterium = nadereSelectieCriterium;
    }

    /**
     * Geef de waarde van selectiePeilmomentMaterieelResultaat.
     * @return selectiePeilmomentMaterieelResultaat
     */
    public Integer getSelectiePeilmomentMaterieelResultaat() {
        return selectiePeilmomentMaterieelResultaat;
    }

    /**
     * Zet de waarde van selectiePeilmomentMaterieelResultaat.
     * @param selectiePeilmomentMaterieelResultaat selectiePeilmomentMaterieelResultaat
     */
    public void setSelectiePeilmomentMaterieelResultaat(final Integer selectiePeilmomentMaterieelResultaat) {
        this.selectiePeilmomentMaterieelResultaat = selectiePeilmomentMaterieelResultaat;
    }

    /**
     * Geef de waarde van selectiePeilmomentFormeelResultaat.
     * @return selectiePeilmomentFormeelResultaat
     */
    public Timestamp getSelectiePeilmomentFormeelResultaat() {
        return selectiePeilmomentFormeelResultaat;
    }

    /**
     * Zet de waarde van selectiePeilmomentFormeelResultaat.
     * @param selectiePeilmomentFormeelResultaat selectiePeilmomentFormeelResultaat
     */
    public void setSelectiePeilmomentFormeelResultaat(final Timestamp selectiePeilmomentFormeelResultaat) {
        this.selectiePeilmomentFormeelResultaat = selectiePeilmomentFormeelResultaat;
    }

    /**
     * Geef de waarde van historievormSelectie.
     * @return historievormSelectie
     */
    public Integer getHistorievormSelectie() {
        return historievormSelectie;
    }

    /**
     * Zet de waarde van historievormSelectie.
     * @param historievormSelectie historievormSelectie
     */
    public void setHistorievormSelectie(final Integer historievormSelectie) {
        this.historievormSelectie = historievormSelectie;
    }

    /**
     * Geef de waarde van indicatieSelectieresultaatControleren.
     * @return indicatieSelectieresultaatControleren
     */
    public Boolean getIndicatieSelectieresultaatControleren() {
        return indicatieSelectieresultaatControleren;
    }

    /**
     * Zet de waarde van indicatieSelectieresultaatControleren.
     * @param indicatieSelectieresultaatControleren indicatieSelectieresultaatControleren
     */
    public void setIndicatieSelectieresultaatControleren(final Boolean indicatieSelectieresultaatControleren) {
        this.indicatieSelectieresultaatControleren = indicatieSelectieresultaatControleren;
    }

    /**
     * Geef de waarde van maxAantalPersonenPerSelectiebestand.
     * @return maxAantalPersonenPerSelectiebestand
     */
    public Integer getMaxAantalPersonenPerSelectiebestand() {
        return maxAantalPersonenPerSelectiebestand;
    }

    /**
     * Zet de waarde van maxAantalPersonenPerSelectiebestand.
     * @param maxAantalPersonenPerSelectiebestand maxAantalPersonenPerSelectiebestand
     */
    public void setMaxAantalPersonenPerSelectiebestand(final Integer maxAantalPersonenPerSelectiebestand) {
        this.maxAantalPersonenPerSelectiebestand = maxAantalPersonenPerSelectiebestand;
    }

    /**
     * Geef de waarde van maxGrootteSelectiebestand.
     * @return maxGrootteSelectiebestand
     */
    public Integer getMaxGrootteSelectiebestand() {
        return maxGrootteSelectiebestand;
    }

    /**
     * Zet de waarde van maxGrootteSelectiebestand.
     * @param maxGrootteSelectiebestand maxGrootteSelectiebestand
     */
    public void setMaxGrootteSelectiebestand(final Integer maxGrootteSelectiebestand) {
        this.maxGrootteSelectiebestand = maxGrootteSelectiebestand;
    }

    /**
     * Geef de waarde van indVerzVolBerBijWijzAfniNaSelectie.
     * @return indVerzVolBerBijWijzAfniNaSelectie
     */
    public Boolean getIndVerzVolBerBijWijzAfniNaSelectie() {
        return indVerzVolBerBijWijzAfniNaSelectie;
    }

    /**
     * Zet de waarde van indVerzVolBerBijWijzAfniNaSelectie.
     * @param indVerzVolBerBijWijzAfniNaSelectie indVerzVolBerBijWijzAfniNaSelectie
     */
    public void setIndVerzVolBerBijWijzAfniNaSelectie(final Boolean indVerzVolBerBijWijzAfniNaSelectie) {
        this.indVerzVolBerBijWijzAfniNaSelectie = indVerzVolBerBijWijzAfniNaSelectie;
    }

    /**
     * Geef de waarde van leverwijzeSelectie.
     * @return leverwijzeSelectie
     */
    public Integer getLeverwijzeSelectie() {
        return leverwijzeSelectie;
    }

    /**
     * Zet de waarde van leverwijzeSelectie.
     * @param leverwijzeSelectie leverwijzeSelectie
     */
    public void setLeverwijzeSelectie(final Integer leverwijzeSelectie) {
        this.leverwijzeSelectie = leverwijzeSelectie;
    }

    /**
     * Geef de waarde van dienst van DienstSelectieHistorie.
     * @return de waarde van dienst van DienstSelectieHistorie
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Zet de waarden voor dienst van DienstSelectieHistorie.
     * @param dienst de nieuwe waarde voor dienst van DienstSelectieHistorie
     */
    public void setDienst(final Dienst dienst) {
        ValidationUtils.controleerOpNullWaarden("dienst mag niet null zijn", dienst);
        this.dienst = dienst;
    }
}

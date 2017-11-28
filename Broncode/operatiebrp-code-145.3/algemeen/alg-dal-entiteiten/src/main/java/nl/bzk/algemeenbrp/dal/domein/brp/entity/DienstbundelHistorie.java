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
 * The persistent class for the his_dienstbundel database table.
 */
@Entity
@Table(name = "his_dienstbundel", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienstbundel", "tsreg"}))
@NamedQuery(name = "DienstbundelHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstbundelHistorie d")
public class DienstbundelHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstbundel_id_generator", sequenceName = "autaut.seq_his_dienstbundel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstbundel_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indnaderepopbeperkingvolconv")
    private Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd;

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    @Column(name = "toelichting")
    private String toelichting;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    /**
     * JPA no-args constructor.
     */
    DienstbundelHistorie() {}

    /**
     * Maakt een nieuw DienstbundelHistorie object.
     *
     * @param dienstbundel dienstbundel
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param naam naam
     * @param datumIngang datumIngang
     */
    public DienstbundelHistorie(final Dienstbundel dienstbundel, final Timestamp datumTijdRegistratie, final String naam, final int datumIngang) {
        setDienstbundel(dienstbundel);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setNaam(naam);
        setDatumIngang(datumIngang);
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
     * Zet de waarden voor id van DienstbundelHistorie.
     *
     * @param id de nieuwe waarde voor id van DienstbundelHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van DienstbundelHistorie.
     *
     * @return de waarde van datum einde van DienstbundelHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van DienstbundelHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van DienstbundelHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van DienstbundelHistorie.
     *
     * @return de waarde van datum ingang van DienstbundelHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van DienstbundelHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van DienstbundelHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van DienstbundelHistorie.
     *
     * @return de waarde van indicatie geblokkeerd van DienstbundelHistorie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van DienstbundelHistorie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        DienstbundelHistorie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van indicatie nadere populatiebeperking volledig geconverteerd van
     * DienstbundelHistorie.
     *
     * @return de waarde van indicatie nadere populatiebeperking volledig geconverteerd van
     *         DienstbundelHistorie
     */
    public Boolean getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet de waarden voor indicatie nadere populatiebeperking volledig geconverteerd van
     * DienstbundelHistorie.
     *
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd de nieuwe waarde voor indicatie
     *        nadere populatiebeperking volledig geconverteerd van DienstbundelHistorie
     */
    public void setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(final Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd) {
        if (Boolean.TRUE.equals(indicatieNaderePopulatiebeperkingVolledigGeconverteerd)) {
            throw new IllegalArgumentException("indicatieNaderePopulatiebeperkingVolledigGeconverteerd moet null of FALSE zijn");
        }
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van naam van DienstbundelHistorie.
     *
     * @return de waarde van naam van DienstbundelHistorie
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van DienstbundelHistorie.
     *
     * @param naam de nieuwe waarde voor naam van DienstbundelHistorie
     */
    public void setNaam(final String naam) {
        if (naam == null || "".equals(naam)) {
            throw new IllegalArgumentException("naam moet gevuld zijn en minimaal lengte 1 hebben.");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van nadere populatiebeperking van DienstbundelHistorie.
     *
     * @return de waarde van nadere populatiebeperking van DienstbundelHistorie
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Zet de waarden voor nadere populatiebeperking van DienstbundelHistorie.
     *
     * @param naderePopulatiebeperking de nieuwe waarde voor nadere populatiebeperking van
     *        DienstbundelHistorie
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van toelichting van DienstbundelHistorie.
     *
     * @return de waarde van toelichting van DienstbundelHistorie
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Zet de waarden voor toelichting van DienstbundelHistorie.
     *
     * @param toelichting de nieuwe waarde voor toelichting van DienstbundelHistorie
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van dienstbundel van DienstbundelHistorie.
     *
     * @return de waarde van dienstbundel van DienstbundelHistorie
     */
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Zet de waarden voor dienstbundel van DienstbundelHistorie.
     *
     * @param dienstbundel de nieuwe waarde voor dienstbundel van DienstbundelHistorie
     */
    public void setDienstbundel(final Dienstbundel dienstbundel) {
        ValidationUtils.controleerOpNullWaarden("dienstbundel mag niet null zijn", dienstbundel);
        this.dienstbundel = dienstbundel;
    }
}

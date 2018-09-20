/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;

/**
 * The persistent class for the his_levsautorisatie database table.
 * 
 */
@Entity
@Table(name = "his_levsautorisatie", schema = "autaut")
@NamedQuery(name = "LeveringsautorisatieHistorie.findAll", query = "SELECT l FROM LeveringsautorisatieHistorie l")
@SuppressWarnings("checkstyle:designforextension")
public class LeveringsautorisatieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_levsautorisatie_id_generator", sequenceName = "autaut.seq_his_levsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_levsautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indaliassrtadmhndleveren", nullable = false)
    private boolean indicatieAliasSoortAdministratieveHandelingLeveren;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "indpopbeperkingvolconv")
    private Boolean indicatiePopulatiebeperkingVolledigGeconverteerd;

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "populatiebeperking")
    private String populatiebeperking;

    @Column(name = "toelichting")
    private String toelichting;

    // bi-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    @Column(name = "protocolleringsniveau", nullable = false)
    private short protocolleringsniveauId;

    /**
     * JPA no-args constructor.
     */
    LeveringsautorisatieHistorie() {
    }

    /**
     * Maakt een nieuw LeveringsautorisatieHistorie object.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     * @param datumTijdRegistratie
     *            datumTijdRegistratie
     * @param naam
     *            naam
     * @param protocolleringsniveau
     *            protocolleringsniveau
     * @param indicatieAliasSoortAdministratieveHandelingLeveren
     *            indicatieAliasSoortAdministratieveHandelingLeveren
     * @param datumIngang
     *            datumIngang
     */
    public LeveringsautorisatieHistorie(
        final Leveringsautorisatie leveringsautorisatie,
        final Timestamp datumTijdRegistratie,
        final String naam,
        final Protocolleringsniveau protocolleringsniveau,
        final boolean indicatieAliasSoortAdministratieveHandelingLeveren,
        final int datumIngang)
    {
        setLeveringsautorisatie(leveringsautorisatie);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setNaam(naam);
        setProtocolleringsniveau(protocolleringsniveau);
        setIndicatieAliasSoortAdministratieveHandelingLeveren(indicatieAliasSoortAdministratieveHandelingLeveren);
        setDatumIngang(datumIngang);
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
     * Geef de waarde van datumEinde.
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
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang
     */
    public int getDatumIngang() {
        return this.datumIngang;
    }

    /**
     * Zet de waarde van datumIngang.
     *
     * @param datumIngang
     *            datumIngang
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatieAliasSoortAdministratieveHandelingLeveren.
     *
     * @return indicatieAliasSoortAdministratieveHandelingLeveren
     */
    public boolean getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return this.indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Zet de waarde van indicatieAliasSoortAdministratieveHandelingLeveren.
     *
     * @param indicatieAliasSoortAdministratieveHandelingLeveren
     *            indicatieAliasSoortAdministratieveHandelingLeveren
     */
    public void setIndicatieAliasSoortAdministratieveHandelingLeveren(final boolean indicatieAliasSoortAdministratieveHandelingLeveren) {
        this.indicatieAliasSoortAdministratieveHandelingLeveren = indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
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
     * Geef de waarde van indicatiePopulatiebeperkingVolledigGeconverteerd.
     *
     * @return indicatiePopulatiebeperkingVolledigGeconverteerd
     */
    public Boolean getIndicatiePopulatiebeperkingVolledigGeconverteerd() {
        return this.indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet de waarde van indicatiePopulatiebeperkingVolledigGeconverteerd.
     *
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd
     *            indicatiePopulatiebeperkingVolledigGeconverteerd
     */
    public void setIndicatiePopulatiebeperkingVolledigGeconverteerd(final Boolean indicatiePopulatiebeperkingVolledigGeconverteerd) {
        if (Boolean.TRUE.equals(indicatiePopulatiebeperkingVolledigGeconverteerd)) {
            throw new IllegalArgumentException("indicatiePopulatiebeperkingVolledigGeconverteerd moet null of FALSE zijn");
        }
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return this.naam;
    }

    /**
     * Zet de waarde van naam.
     *
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        if (naam == null || "".equals(naam)) {
            throw new IllegalArgumentException("naam moet gevuld zijn en mimimaal lengte 1 hebben.");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van populatiebeperking.
     *
     * @return populatiebeperking
     */
    public String getPopulatiebeperking() {
        return this.populatiebeperking;
    }

    /**
     * Zet de waarde van populatiebeperking.
     *
     * @param populatiebeperking
     *            populatiebeperking
     */
    public void setPopulatiebeperking(final String populatiebeperking) {
        this.populatiebeperking = populatiebeperking;
    }

    /**
     * Geef de waarde van toelichting.
     *
     * @return toelichting
     */
    public String getToelichting() {
        return this.toelichting;
    }

    /**
     * Zet de waarde van toelichting.
     *
     * @param toelichting
     *            toelichting
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van leveringsautorisatie.
     *
     * @return leveringsautorisatie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return this.leveringsautorisatie;
    }

    /**
     * Zet de waarde van leveringsautorisatie.
     *
     * @param leveringsautorisatie
     *            leveringsautorisatie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Geef de waarde van protocolleringsniveau.
     *
     * @return protocolleringsniveau
     */
    public Protocolleringsniveau getProtocolleringsniveau() {
        return Protocolleringsniveau.parseId(protocolleringsniveauId);
    }

    /**
     * Zet de waarde van protocolleringsniveau.
     *
     * @param protocolleringsniveau
     *            protocolleringsniveau
     */
    public void setProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
        ValidationUtils.controleerOpNullWaarden("protocolleringsniveau mag niet null zijn", protocolleringsniveau);
        protocolleringsniveauId = protocolleringsniveau.getId();
    }
}

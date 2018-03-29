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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_levsautorisatie database table.
 *
 */
@Entity
@Table(name = "his_levsautorisatie", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"levsautorisatie", "tsreg"}))
@NamedQuery(name = "LeveringsautorisatieHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT l FROM LeveringsautorisatieHistorie l")
public class LeveringsautorisatieHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
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

    @Column(name = "naam", nullable = false)
    private String naam;

    @Column(name = "populatiebeperking")
    private String populatiebeperking;

    @Column(name = "toelichting")
    private String toelichting;

    // bi-directional many-to-one association to Leveringsautorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levsautorisatie", nullable = false)
    private Leveringsautorisatie leveringsautorisatie;

    @Column(name = "protocolleringsniveau", nullable = false)
    private int protocolleringsniveauId;

    /**
     * JPA no-args constructor.
     */
    LeveringsautorisatieHistorie() {}

    /**
     * Maakt een nieuw LeveringsautorisatieHistorie object.
     *
     * @param leveringsautorisatie leveringsautorisatie
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param naam naam
     * @param protocolleringsniveau protocolleringsniveau
     * @param indicatieAliasSoortAdministratieveHandelingLeveren
     *        indicatieAliasSoortAdministratieveHandelingLeveren
     * @param datumIngang datumIngang
     */
    public LeveringsautorisatieHistorie(final Leveringsautorisatie leveringsautorisatie, final Timestamp datumTijdRegistratie, final String naam,
            final Protocolleringsniveau protocolleringsniveau, final boolean indicatieAliasSoortAdministratieveHandelingLeveren, final int datumIngang) {
        setLeveringsautorisatie(leveringsautorisatie);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setNaam(naam);
        setProtocolleringsniveau(protocolleringsniveau);
        setIndicatieAliasSoortAdministratieveHandelingLeveren(indicatieAliasSoortAdministratieveHandelingLeveren);
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
     * Zet de waarden voor id van LeveringsautorisatieHistorie.
     *
     * @param id de nieuwe waarde voor id van LeveringsautorisatieHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van LeveringsautorisatieHistorie.
     *
     * @return de waarde van datum einde van LeveringsautorisatieHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van LeveringsautorisatieHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van LeveringsautorisatieHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van LeveringsautorisatieHistorie.
     *
     * @return de waarde van datum ingang van LeveringsautorisatieHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van LeveringsautorisatieHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van LeveringsautorisatieHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie alias soort administratieve handeling leveren van
     * LeveringsautorisatieHistorie.
     *
     * @return de waarde van indicatie alias soort administratieve handeling leveren van
     *         LeveringsautorisatieHistorie
     */
    public boolean getIndicatieAliasSoortAdministratieveHandelingLeveren() {
        return indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Zet de waarden voor indicatie alias soort administratieve handeling leveren van
     * LeveringsautorisatieHistorie.
     *
     * @param indicatieAliasSoortAdministratieveHandelingLeveren de nieuwe waarde voor indicatie
     *        alias soort administratieve handeling leveren van LeveringsautorisatieHistorie
     */
    public void setIndicatieAliasSoortAdministratieveHandelingLeveren(final boolean indicatieAliasSoortAdministratieveHandelingLeveren) {
        this.indicatieAliasSoortAdministratieveHandelingLeveren = indicatieAliasSoortAdministratieveHandelingLeveren;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van LeveringsautorisatieHistorie.
     *
     * @return de waarde van indicatie geblokkeerd van LeveringsautorisatieHistorie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van LeveringsautorisatieHistorie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        LeveringsautorisatieHistorie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van naam van LeveringsautorisatieHistorie.
     *
     * @return de waarde van naam van LeveringsautorisatieHistorie
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van LeveringsautorisatieHistorie.
     *
     * @param naam de nieuwe waarde voor naam van LeveringsautorisatieHistorie
     */
    public void setNaam(final String naam) {
        if (naam == null || "".equals(naam)) {
            throw new IllegalArgumentException("naam moet gevuld zijn en mimimaal lengte 1 hebben.");
        }
        this.naam = naam;
    }

    /**
     * Geef de waarde van populatiebeperking van LeveringsautorisatieHistorie.
     *
     * @return de waarde van populatiebeperking van LeveringsautorisatieHistorie
     */
    public String getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Zet de waarden voor populatiebeperking van LeveringsautorisatieHistorie.
     *
     * @param populatiebeperking de nieuwe waarde voor populatiebeperking van
     *        LeveringsautorisatieHistorie
     */
    public void setPopulatiebeperking(final String populatiebeperking) {
        this.populatiebeperking = populatiebeperking;
    }

    /**
     * Geef de waarde van toelichting van LeveringsautorisatieHistorie.
     *
     * @return de waarde van toelichting van LeveringsautorisatieHistorie
     */
    public String getToelichting() {
        return toelichting;
    }

    /**
     * Zet de waarden voor toelichting van LeveringsautorisatieHistorie.
     *
     * @param toelichting de nieuwe waarde voor toelichting van LeveringsautorisatieHistorie
     */
    public void setToelichting(final String toelichting) {
        this.toelichting = toelichting;
    }

    /**
     * Geef de waarde van leveringsautorisatie van LeveringsautorisatieHistorie.
     *
     * @return de waarde van leveringsautorisatie van LeveringsautorisatieHistorie
     */
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Zet de waarden voor leveringsautorisatie van LeveringsautorisatieHistorie.
     *
     * @param leveringsautorisatie de nieuwe waarde voor leveringsautorisatie van
     *        LeveringsautorisatieHistorie
     */
    public void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("leveringsautorisatie", leveringsautorisatie);
        this.leveringsautorisatie = leveringsautorisatie;
    }

    /**
     * Geef de waarde van protocolleringsniveau van LeveringsautorisatieHistorie.
     *
     * @return de waarde van protocolleringsniveau van LeveringsautorisatieHistorie
     */
    public Protocolleringsniveau getProtocolleringsniveau() {
        return Protocolleringsniveau.parseId(protocolleringsniveauId);
    }

    /**
     * Zet de waarden voor protocolleringsniveau van LeveringsautorisatieHistorie.
     *
     * @param protocolleringsniveau de nieuwe waarde voor protocolleringsniveau van
     *        LeveringsautorisatieHistorie
     */
    public void setProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
        ValidationUtils.controleerOpNullWaarden("protocolleringsniveau mag niet null zijn", protocolleringsniveau);
        protocolleringsniveauId = protocolleringsniveau.getId();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_dienst database table.
 *
 */
@Entity
@Table(name = "his_bijhouderfiatuitz", schema = "autaut")
@NamedQuery(name = "BijhouderFiatteringsuitzonderingHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE,
        query = "SELECT b FROM BijhouderFiatteringsuitzonderingHistorie b")
public class BijhouderFiatteringsuitzonderingHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_bijhouderfiatuitz_id_generator", sequenceName = "autaut.seq_his_bijhouderfiatuitz", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_bijhouderfiatuitz_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bijhouderbijhvoorstel")
    private PartijRol bijhouderBijhoudingsvoorstel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "srtdoc")
    private SoortDocument soortDocument;

    @Column(name = "srtadmhnd")
    private Integer soortAdministratieveHandelingId;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    // bi-directional many-to-one association to BijhouderFiatteringsuitzondering
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bijhouderfiatuitz", nullable = false)
    private BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering;

    /**
     * JPA no-args constructor.
     */
    BijhouderFiatteringsuitzonderingHistorie() {}

    /**
     * Maakt een nieuw DienstHistorie object.
     *
     * @param bijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param datumIngang datumIngang
     */
    public BijhouderFiatteringsuitzonderingHistorie(final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering,
            final Timestamp datumTijdRegistratie, final int datumIngang) {
        setBijhouderFiatteringsuitzondering(bijhouderFiatteringsuitzondering);
        setDatumTijdRegistratie(datumTijdRegistratie);
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
     * Zet de waarden voor id van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param id de nieuwe waarde voor id van BijhouderFiatteringsuitzonderingHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van datum einde van BijhouderFiatteringsuitzonderingHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van
     *        BijhouderFiatteringsuitzonderingHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van datum ingang van BijhouderFiatteringsuitzonderingHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van
     *        BijhouderFiatteringsuitzonderingHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van bijhouder bijhoudingsvoorstel van
     * BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van bijhouder bijhoudingsvoorstel van
     *         BijhouderFiatteringsuitzonderingHistorie
     */
    public PartijRol getBijhouderBijhoudingsvoorstel() {
        return bijhouderBijhoudingsvoorstel;
    }

    /**
     * Zet de waarden voor bijhouder bijhoudingsvoorstel van
     * BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param bijhouderBijhoudingsvoorstel de nieuwe waarde voor bijhouder bijhoudingsvoorstel van
     *        BijhouderFiatteringsuitzonderingHistorie
     */
    public void setBijhouderBijhoudingsvoorstel(final PartijRol bijhouderBijhoudingsvoorstel) {
        this.bijhouderBijhoudingsvoorstel = bijhouderBijhoudingsvoorstel;
    }

    /**
     * Geef de waarde van soort document van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van soort document van BijhouderFiatteringsuitzonderingHistorie
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Zet de waarden voor soort document van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param soortDocument de nieuwe waarde voor soort document van
     *        BijhouderFiatteringsuitzonderingHistorie
     */
    public void setSoortDocument(final SoortDocument soortDocument) {
        this.soortDocument = soortDocument;
    }

    /**
     * Geef de waarde van soort administratieve handeling id van
     * BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van soort administratieve handeling id van
     *         BijhouderFiatteringsuitzonderingHistorie
     */
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return SoortAdministratieveHandeling.parseId(soortAdministratieveHandelingId);
    }

    /**
     * Zet de waarden voor soort administratieve handeling van
     * BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param soortAdministratieveHandeling de nieuwe waarde voor soort administratieve handeling
     *        van BijhouderFiatteringsuitzonderingHistorie
     */
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        if (soortAdministratieveHandeling == null) {
            soortAdministratieveHandelingId = null;
        } else {
            soortAdministratieveHandelingId = soortAdministratieveHandeling.getId();
        }
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van indicatie geblokkeerd van BijhouderFiatteringsuitzonderingHistorie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        BijhouderFiatteringsuitzonderingHistorie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van bijhouder fiatteringsuitzondering van
     * BijhouderFiatteringsuitzonderingHistorie.
     *
     * @return de waarde van bijhouder fiatteringsuitzondering van
     *         BijhouderFiatteringsuitzonderingHistorie
     */
    public BijhouderFiatteringsuitzondering getBijhouderFiatteringsuitzondering() {
        return bijhouderFiatteringsuitzondering;
    }

    /**
     * Zet de waarden voor bijhouder fiatteringsuitzondering van
     * BijhouderFiatteringsuitzonderingHistorie.
     *
     * @param bijhouderFiatteringsuitzondering de nieuwe waarde voor bijhouder
     *        fiatteringsuitzondering van BijhouderFiatteringsuitzonderingHistorie
     */
    public void setBijhouderFiatteringsuitzondering(final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering) {
        ValidationUtils.controleerOpNullWaarden("bijhouderFiatteringsuitzondering mag niet null zijn", bijhouderFiatteringsuitzondering);
        this.bijhouderFiatteringsuitzondering = bijhouderFiatteringsuitzondering;
    }

}

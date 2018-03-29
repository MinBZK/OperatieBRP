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
 * The persistent class for the his_bijhautorisatie database table.
 *
 */
@Entity
@Table(name = "his_bijhautorisatie", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"bijhautorisatie", "tsreg"}))
@NamedQuery(name = "BijhoudingsautorisatieHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT b FROM BijhoudingsautorisatieHistorie b")
public class BijhoudingsautorisatieHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_bijhautorisatie_id_generator", sequenceName = "autaut.seq_his_bijhautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_bijhautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "naam", length = 80)
    private String naam;

    @Column(name = "datingang")
    private Integer datumIngang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    // bi-directional many-to-one association to Bijhoudingsautorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bijhautorisatie", nullable = false)
    private Bijhoudingsautorisatie bijhoudingsautorisatie;

    /**
     * JPA no-args constructor.
     */
    BijhoudingsautorisatieHistorie() {}

    /**
     * Maakt een nieuw BijhoudingsautorisatieHistorie object.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie, mag niet null zijn
     * @param datumTijdRegistratie datumTijdRegistratie, mag niet null zijn
     * @param datumIngang datumIngang, mag niet null zijn
     * @param naam naam, mag niet null zijn
     */
    public BijhoudingsautorisatieHistorie(final Bijhoudingsautorisatie bijhoudingsautorisatie, final Timestamp datumTijdRegistratie, final int datumIngang,
            final String naam) {
        setBijhoudingsautorisatie(bijhoudingsautorisatie);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setDatumIngang(datumIngang);
        setNaam(naam);
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
     * Zet de waarden voor id van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param id de nieuwe waarde voor id van ToegangBijhoudingsautorisatieHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
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
     * @param naam naam, mag niet null zijn
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van datum einde van ToegangBijhoudingsautorisatieHistorie.
     *
     * @return de waarde van datum einde van ToegangBijhoudingsautorisatieHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van ToegangBijhoudingsautorisatieHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van ToegangBijhoudingsautorisatieHistorie.
     *
     * @return de waarde van datum ingang van ToegangBijhoudingsautorisatieHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van
     *        ToegangBijhoudingsautorisatieHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        ValidationUtils.controleerOpNullWaarden("datumIngang mag niet null zijn", datumIngang);
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van ToegangBijhoudingsautorisatieHistorie.
     *
     * @return de waarde van indicatie geblokkeerd van ToegangBijhoudingsautorisatieHistorie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        ToegangBijhoudingsautorisatieHistorie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van toegang bijhoudingsautorisatie van ToegangBijhoudingsautorisatieHistorie.
     *
     * @return de waarde van toegang bijhoudingsautorisatie van
     *         ToegangBijhoudingsautorisatieHistorie
     */
    public Bijhoudingsautorisatie getToegangBijhoudingsautorisatie() {
        return bijhoudingsautorisatie;
    }

    /**
     * Zet de waarden voor toegang bijhoudingsautorisatie van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param bijhoudingsautorisatie de nieuwe waarde voor bijhoudingsautorisatie van
     *        BijhoudingsautorisatieHistorie
     */
    public void setBijhoudingsautorisatie(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("bijhoudingsautorisatie mag niet null zijn", bijhoudingsautorisatie);
        this.bijhoudingsautorisatie = bijhoudingsautorisatie;
    }
}

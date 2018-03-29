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
 * The persistent class for the his_toegangbijhautorisatie database table.
 *
 */
@Entity
@Table(name = "his_toegangbijhautorisatie", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"toegangbijhautorisatie", "tsreg"}))
@NamedQuery(name = "ToegangBijhoudingsautorisatieHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT t FROM ToegangBijhoudingsautorisatieHistorie t")
public class ToegangBijhoudingsautorisatieHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_toegangbijhautorisatie_id_generator", sequenceName = "autaut.seq_his_toegangbijhautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_toegangbijhautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "afleverpunt")
    private String afleverpunt;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    // bi-directional many-to-one association to ToegangBijhoudingsautorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toegangbijhautorisatie", nullable = false)
    private ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie;

    /**
     * JPA no-args constructor.
     */
    ToegangBijhoudingsautorisatieHistorie() {}

    /**
     * Maakt een nieuw ToegangBijhoudingsautorisatieHistorie object.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param datumIngang datumIngang
     */
    public ToegangBijhoudingsautorisatieHistorie(final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie, final Timestamp datumTijdRegistratie,
            final int datumIngang) {
        setToegangBijhoudingsautorisatie(toegangBijhoudingsautorisatie);
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
     * Zet de waarden voor id van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param id de nieuwe waarde voor id van ToegangBijhoudingsautorisatieHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
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
     * Geef de waarde van afleverpunt van ToegangBijhoudingsautorisatieHistorie.
     *
     * @return de waarde van afleverpunt van ToegangBijhoudingsautorisatieHistorie
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Zet de waarden voor afleverpunt van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param afleverpunt de nieuwe waarde voor afleverpunt van
     *        ToegangBijhoudingsautorisatieHistorie
     */
    public void setAfleverpunt(final String afleverpunt) {
        this.afleverpunt = afleverpunt;
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
    public ToegangBijhoudingsautorisatie getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * Zet de waarden voor toegang bijhoudingsautorisatie van ToegangBijhoudingsautorisatieHistorie.
     *
     * @param toegangBijhoudingsautorisatie de nieuwe waarde voor toegang bijhoudingsautorisatie van
     *        ToegangBijhoudingsautorisatieHistorie
     */
    public void setToegangBijhoudingsautorisatie(final ToegangBijhoudingsautorisatie toegangBijhoudingsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("toegangBijhoudingsautorisatie mag niet null zijn", toegangBijhoudingsautorisatie);
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
    }
}

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
 * The persistent class for the his_toeganglevsautorisatie database table.
 *
 */
@Entity
@Table(name = "his_toeganglevsautorisatie", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"toeganglevsautorisatie", "tsreg"}))
@NamedQuery(name = "ToegangLeveringsautorisatieHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT t FROM ToegangLeveringsautorisatieHistorie t")
public class ToegangLeveringsautorisatieHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_toeganglevsautorisatie_id_generator", sequenceName = "autaut.seq_his_toeganglevsautorisatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_toeganglevsautorisatie_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "afleverpunt")
    private String afleverpunt;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    @Column(name = "naderepopulatiebeperking")
    private String naderePopulatiebeperking;

    // bi-directional many-to-one association to ToegangLeveringsAutorisatie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toeganglevsautorisatie", nullable = false)
    private ToegangLeveringsAutorisatie toegangLeveringsautorisatie;

    /**
     * JPA no-args constructor.
     */
    ToegangLeveringsautorisatieHistorie() {}

    /**
     * Maakt een nieuw ToegangLeveringsautorisatieHistorie object.
     *
     * @param toegangLeveringsautorisatie toegangLeveringsautorisatie
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param datumIngang datumIngang
     */
    public ToegangLeveringsautorisatieHistorie(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie, final Timestamp datumTijdRegistratie,
            final int datumIngang) {
        setToegangLeveringsautorisatie(toegangLeveringsautorisatie);
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
     * Zet de waarden voor id van ToegangLeveringsautorisatieHistorie.
     *
     * @param id de nieuwe waarde voor id van ToegangLeveringsautorisatieHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van ToegangLeveringsautorisatieHistorie.
     *
     * @return de waarde van datum einde van ToegangLeveringsautorisatieHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van ToegangLeveringsautorisatieHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van ToegangLeveringsautorisatieHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van ToegangLeveringsautorisatieHistorie.
     *
     * @return de waarde van datum ingang van ToegangLeveringsautorisatieHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van ToegangLeveringsautorisatieHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van ToegangLeveringsautorisatieHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van afleverpunt van ToegangLeveringsautorisatieHistorie.
     *
     * @return de waarde van afleverpunt van ToegangLeveringsautorisatieHistorie
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Zet de waarden voor afleverpunt van ToegangLeveringsautorisatieHistorie.
     *
     * @param afleverpunt de nieuwe waarde voor afleverpunt van ToegangLeveringsautorisatieHistorie
     */
    public void setAfleverpunt(final String afleverpunt) {
        this.afleverpunt = afleverpunt;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van ToegangLeveringsautorisatieHistorie.
     *
     * @return de waarde van indicatie geblokkeerd van ToegangLeveringsautorisatieHistorie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van ToegangLeveringsautorisatieHistorie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van
     *        ToegangLeveringsautorisatieHistorie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van nadere populatiebeperking van ToegangLeveringsautorisatieHistorie.
     *
     * @return de waarde van nadere populatiebeperking van ToegangLeveringsautorisatieHistorie
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Zet de waarden voor nadere populatiebeperking van ToegangLeveringsautorisatieHistorie.
     *
     * @param naderePopulatiebeperking de nieuwe waarde voor nadere populatiebeperking van
     *        ToegangLeveringsautorisatieHistorie
     */
    public void setNaderePopulatiebeperking(final String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van toegang leveringsautorisatie van ToegangLeveringsautorisatieHistorie.
     *
     * @return de waarde van toegang leveringsautorisatie van ToegangLeveringsautorisatieHistorie
     */
    public ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    /**
     * Zet de waarden voor toegang leveringsautorisatie van ToegangLeveringsautorisatieHistorie.
     *
     * @param toegangLeveringsautorisatie de nieuwe waarde voor toegang leveringsautorisatie van
     *        ToegangLeveringsautorisatieHistorie
     */
    public void setToegangLeveringsautorisatie(final ToegangLeveringsAutorisatie toegangLeveringsautorisatie) {
        ValidationUtils.controleerOpNullWaarden("toegangLeveringsautorisatie mag niet null zijn", toegangLeveringsautorisatie);
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
    }
}

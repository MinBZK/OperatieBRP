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
 * The persistent class for the his_dienst database table.
 *
 */
@Entity
@Table(name = "his_dienst", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienst", "tsreg"}))
@NamedQuery(name = "DienstHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstHistorie d")
public class DienstHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienst_id_generator", sequenceName = "autaut.seq_his_dienst", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienst_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "datingang", nullable = false)
    private int datumIngang;

    @Column(name = "indblok")
    private Boolean indicatieGeblokkeerd;

    // bi-directional many-to-one association to Dienst
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstHistorie() {}

    /**
     * Maakt een nieuw DienstHistorie object.
     *
     * @param dienst dienst
     * @param datumTijdRegistratie datumTijdRegistratie
     * @param datumIngang datumIngang
     */
    public DienstHistorie(final Dienst dienst, final Timestamp datumTijdRegistratie, final int datumIngang) {
        setDienst(dienst);
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
     * Zet de waarden voor id van DienstHistorie.
     *
     * @param id de nieuwe waarde voor id van DienstHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van datum einde van DienstHistorie.
     *
     * @return de waarde van datum einde van DienstHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van DienstHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van DienstHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van datum ingang van DienstHistorie.
     *
     * @return de waarde van datum ingang van DienstHistorie
     */
    public int getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarden voor datum ingang van DienstHistorie.
     *
     * @param datumIngang de nieuwe waarde voor datum ingang van DienstHistorie
     */
    public void setDatumIngang(final int datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van indicatie geblokkeerd van DienstHistorie.
     *
     * @return de waarde van indicatie geblokkeerd van DienstHistorie
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Zet de waarden voor indicatie geblokkeerd van DienstHistorie.
     *
     * @param indicatieGeblokkeerd de nieuwe waarde voor indicatie geblokkeerd van DienstHistorie
     */
    public void setIndicatieGeblokkeerd(final Boolean indicatieGeblokkeerd) {
        if (Boolean.FALSE.equals(indicatieGeblokkeerd)) {
            throw new IllegalArgumentException("indicatieGeblokkeerd moet null of TRUE zijn");
        }
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van dienst van DienstHistorie.
     *
     * @return de waarde van dienst van DienstHistorie
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Zet de waarden voor dienst van DienstHistorie.
     *
     * @param dienst de nieuwe waarde voor dienst van DienstHistorie
     */
    public void setDienst(final Dienst dienst) {
        ValidationUtils.controleerOpNullWaarden("dienst mag niet null zijn", dienst);
        this.dienst = dienst;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
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
 * The persistent class for the his_dienstattendering database table.
 *
 */
@Entity
@Table(name = "his_dienstattendering", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienst", "tsreg"}))
@NamedQuery(name = "DienstAttenderingHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstAttenderingHistorie d")
public class DienstAttenderingHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstattendering_id_generator", sequenceName = "autaut.seq_his_dienstattendering", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstattendering_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "attenderingscriterium")
    private String attenderingscriterium;

    // bi-directional many-to-one association to Dienst
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstAttenderingHistorie() {}

    /**
     * Maakt een nieuw DienstAttenderingHistorie object.
     *
     * @param dienst dienst
     */
    public DienstAttenderingHistorie(final Dienst dienst) {
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
     * Zet de waarden voor id van DienstAttenderingHistorie.
     *
     * @param id de nieuwe waarde voor id van DienstAttenderingHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van attenderingscriterium van DienstAttenderingHistorie.
     *
     * @return de waarde van attenderingscriterium van DienstAttenderingHistorie
     */
    public String getAttenderingscriterium() {
        return attenderingscriterium;
    }

    /**
     * Zet de waarden voor attenderingscriterium van DienstAttenderingHistorie.
     *
     * @param attenderingscriterium de nieuwe waarde voor attenderingscriterium van
     *        DienstAttenderingHistorie
     */
    public void setAttenderingscriterium(final String attenderingscriterium) {
        this.attenderingscriterium = attenderingscriterium;
    }

    /**
     * Geef de waarde van dienst van DienstAttenderingHistorie.
     *
     * @return de waarde van dienst van DienstAttenderingHistorie
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Zet de waarden voor dienst van DienstAttenderingHistorie.
     *
     * @param dienst de nieuwe waarde voor dienst van DienstAttenderingHistorie
     */
    public void setDienst(final Dienst dienst) {
        ValidationUtils.controleerOpNullWaarden("dienst mag niet null zijn", dienst);
        this.dienst = dienst;
    }
}

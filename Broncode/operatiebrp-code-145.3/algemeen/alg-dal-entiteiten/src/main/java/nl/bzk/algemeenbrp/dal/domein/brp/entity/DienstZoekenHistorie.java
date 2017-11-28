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
 * The persistent class for the his_dienstzoeken database table.
 *
 */
@Entity
@Table(name = "his_dienstzoeken", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienst", "tsreg"}))
@NamedQuery(name = "DienstZoekenHistorie" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstZoekenHistorie d")
public class DienstZoekenHistorie extends AbstractFormeleHistorieZonderVerantwoording implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_dienstzoeken_id_generator", sequenceName = "autaut.seq_his_dienstzoeken", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_dienstzoeken_id_generator")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "maxaantalzoekresultaten")
    private int maximumAantalZoekresultaten;

    // bi-directional many-to-one association to Dienst
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienst", nullable = false)
    private Dienst dienst;

    /**
     * JPA no-args constructor.
     */
    DienstZoekenHistorie() {}

    /**
     * Maakt een nieuw DienstSelectieHistorie object.
     *
     * @param dienst dienst
     * @param maximumAantalZoekresultaten maximumAantalZoekresultaten
     */
    public DienstZoekenHistorie(final Dienst dienst, final int maximumAantalZoekresultaten) {
        setDienst(dienst);
        this.maximumAantalZoekresultaten = maximumAantalZoekresultaten;
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
     *
     * @param id de nieuwe waarde voor id van DienstSelectieHistorie
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van maximumAantalZoekresultaten.
     *
     * @return maximumAantalZoekresultaten
     */
    public int getMaximumAantalZoekresultaten() {
        return maximumAantalZoekresultaten;
    }

    /**
     * Zet de waarde van maximumAantalZoekresultaten.
     *
     * @param maximumAantalZoekresultaten maximumAantalZoekresultaten
     */
    public void setMaximumAantalZoekresultaten(final int maximumAantalZoekresultaten) {
        this.maximumAantalZoekresultaten = maximumAantalZoekresultaten;
    }

    /**
     * Geef de waarde van dienst van DienstSelectieHistorie.
     *
     * @return de waarde van dienst van DienstSelectieHistorie
     */
    public Dienst getDienst() {
        return dienst;
    }

    /**
     * Zet de waarden voor dienst van DienstSelectieHistorie.
     *
     * @param dienst de nieuwe waarde voor dienst van DienstSelectieHistorie
     */
    public void setDienst(final Dienst dienst) {
        ValidationUtils.controleerOpNullWaarden("dienst mag niet null zijn", dienst);
        this.dienst = dienst;
    }
}

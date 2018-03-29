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
 * The persistent class for the dienstbundello3rubriek database table.
 *
 */
@Entity
@Table(name = "dienstbundello3rubriek", schema = "autaut", uniqueConstraints = @UniqueConstraint(columnNames = {"dienstbundel", "rubr"}))
@NamedQuery(name = "DienstbundelLo3Rubriek" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "SELECT d FROM DienstbundelLo3Rubriek d")
public class DienstbundelLo3Rubriek implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "dienstbundello3rubriek_id_generator", sequenceName = "autaut.seq_dienstbundello3rubriek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dienstbundello3rubriek_id_generator")
    @Column(updatable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rubr", nullable = false)
    private Lo3Rubriek lo3Rubriek;

    // bi-directional many-to-one association to Dienstbundel
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstbundel", nullable = false)
    private Dienstbundel dienstbundel;

    /**
     * JPA no-args constructor.
     */
    DienstbundelLo3Rubriek() {}

    /**
     * Maakt een nieuw DienstbundelLo3Rubriek object.
     *
     * @param dienstbundel dienstbundel
     * @param lo3Rubriek lo3rubriek
     */
    public DienstbundelLo3Rubriek(final Dienstbundel dienstbundel, final Lo3Rubriek lo3Rubriek) {
        setDienstbundel(dienstbundel);
        setLo3Rubriek(lo3Rubriek);
    }

    /**
     * Geef de waarde van id van DienstbundelLo3Rubriek.
     *
     * @return de waarde van id van DienstbundelLo3Rubriek
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van DienstbundelLo3Rubriek.
     *
     * @param id de nieuwe waarde voor id van DienstbundelLo3Rubriek
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van dienstbundel van DienstbundelLo3Rubriek.
     *
     * @return de waarde van dienstbundel van DienstbundelLo3Rubriek
     */
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Zet de waarden voor dienstbundel van DienstbundelLo3Rubriek.
     *
     * @param dienstbundel de nieuwe waarde voor dienstbundel van DienstbundelLo3Rubriek
     */
    public void setDienstbundel(final Dienstbundel dienstbundel) {
        ValidationUtils.controleerOpNullWaarden("dienstbundel mag niet null zijn", dienstbundel);
        this.dienstbundel = dienstbundel;
    }

    /**
     * Geef de waarde van lo3 rubriek van DienstbundelLo3Rubriek.
     *
     * @return de waarde van lo3 rubriek van DienstbundelLo3Rubriek
     */
    public Lo3Rubriek getLo3Rubriek() {
        return lo3Rubriek;
    }

    /**
     * Zet de waarden voor lo3 rubriek van DienstbundelLo3Rubriek.
     *
     * @param lo3Rubriek de nieuwe waarde voor lo3 rubriek van DienstbundelLo3Rubriek
     */
    public void setLo3Rubriek(final Lo3Rubriek lo3Rubriek) {
        ValidationUtils.controleerOpNullWaarden("lo3Rubriek mag niet null zijn", lo3Rubriek);
        this.lo3Rubriek = lo3Rubriek;
    }
}

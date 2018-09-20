/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the AdellijkeTitelPredikaat database table.
 * 
 */
@Entity
@Table(name = "convadellijketitelpredikaat", schema = "conv", uniqueConstraints = @UniqueConstraint(columnNames = {"adellijketitel",
                                                                                                                   "predicaat",
                                                                                                                   "geslachtsaand" }))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class AdellijkeTitelPredikaat implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_NIET_NULL_ZIJN = "lo3AdellijkeTitelPredikaat mag niet null zijn";
    private static final String LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_GEEN_LEGE_STRING_ZIJN = "lo3AdellijkeTitelPredikaat mag geen lege string zijn";

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr0221adellijketitelpredik", insertable = false, updatable = false, nullable = false, length = 2, unique = true)
    private String lo3AdellijkeTitelPredikaat;

    @Column(name = "geslachtsaand", nullable = false)
    private short geslachtsaanduidingId;

    @Column(name = "adellijketitel")
    private Short adellijkeTitelId;

    @Column(name = "predicaat")
    private Short predikaatId;

    /**
     * JPA default constructor.
     */
    protected AdellijkeTitelPredikaat() {
    }

    /**
     * Maak een nieuwe adellijke titel predikaat.
     *
     * @param lo3AdellijkeTitelPredikaat
     *            lo3 adellijke titel predikaat
     * @param geslachtsaanduiding
     *            geslachtsaanduiding
     */
    public AdellijkeTitelPredikaat(final String lo3AdellijkeTitelPredikaat, final Geslachtsaanduiding geslachtsaanduiding) {
        ValidationUtils.controleerOpNullWaarden(LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_NIET_NULL_ZIJN, lo3AdellijkeTitelPredikaat);
        Validatie.controleerOpLegeWaarden(LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_GEEN_LEGE_STRING_ZIJN, lo3AdellijkeTitelPredikaat);
        this.lo3AdellijkeTitelPredikaat = lo3AdellijkeTitelPredikaat;
        setGeslachtsaanduiding(geslachtsaanduiding);
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Integer getId() {
        return id;
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
     * Geef de waarde van lo3 adellijke titel predikaat.
     *
     * @return lo3 adellijke titel predikaat
     */
    public String getLo3AdellijkeTitelPredikaat() {
        return lo3AdellijkeTitelPredikaat;
    }

    /**
     * Zet de waarde van lo3 adellijke titel predikaat.
     *
     * @param lo3AdellijkeTitelPredikaat
     *            lo3 adellijke titel predikaat
     */
    public void setLo3AdellijkeTitelPredikaat(final String lo3AdellijkeTitelPredikaat) {
        ValidationUtils.controleerOpNullWaarden(LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_NIET_NULL_ZIJN, lo3AdellijkeTitelPredikaat);
        Validatie.controleerOpLegeWaarden(LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_GEEN_LEGE_STRING_ZIJN, lo3AdellijkeTitelPredikaat);
        this.lo3AdellijkeTitelPredikaat = lo3AdellijkeTitelPredikaat;
    }

    /**
     * Geef de waarde van geslachtsaanduiding.
     *
     * @return geslachtsaanduiding
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarde van geslachtsaanduiding.
     *
     * @param geslachtsaanduiding
     *            geslachtsaanduiding
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        if (geslachtsaanduiding == null) {
            throw new NullPointerException("geslachtsaanduiding mag niet null zijn.");
        } else {
            ValidationUtils.controleerOpNullWaarden("geslachtsaanduiding mag niet null zijn", geslachtsaanduiding);
            this.geslachtsaanduidingId = geslachtsaanduiding.getId();
        }
    }

    /**
     * Geef de waarde van adellijke titel.
     *
     * @return adellijke titel
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarde van adellijke titel.
     *
     * @param adellijkeTitel
     *            adellijke titel
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van predikaat.
     *
     * @return predikaat
     */
    public Predicaat getPredikaat() {
        return Predicaat.parseId(predikaatId);
    }

    /**
     * Zet de waarde van predikaat.
     *
     * @param predikaat
     *            predikaat
     */
    public void setPredikaat(final Predicaat predikaat) {
        if (predikaat == null) {
            predikaatId = null;
        } else {
            predikaatId = predikaat.getId();
        }
    }
}

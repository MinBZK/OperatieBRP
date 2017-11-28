/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the AdellijkeTitelPredikaat database table.
 */
@Entity
@Table(name = "convadellijketitelpredikaat", schema = "conv",
        uniqueConstraints = @UniqueConstraint(columnNames = {"adellijketitel", "predicaat", "geslachtsaand"}))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "AdellijkeTitelPredikaat" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from AdellijkeTitelPredikaat")
public class AdellijkeTitelPredikaat implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_NIET_NULL_ZIJN = "lo3AdellijkeTitelPredikaat mag niet null zijn";
    private static final String LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_GEEN_LEGE_STRING_ZIJN = "lo3AdellijkeTitelPredikaat mag geen lege string zijn";

    @Id
    @SequenceGenerator(name = "convadellijketitelpredikaat_id_generator", sequenceName = "conv.seq_convadellijketitelpredikaat", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convadellijketitelpredikaat_id_generator")
    @Column(nullable = false)
    private Integer id;

    @Column(name = "rubr0221adellijketitelpredik", nullable = false, length = 2, unique = true)
    private String lo3AdellijkeTitelPredikaat;

    @Column(name = "geslachtsaand", nullable = false)
    private int geslachtsaanduidingId;

    @Column(name = "adellijketitel")
    private Integer adellijkeTitelId;

    @Column(name = "predicaat")
    private Integer predikaatId;

    /**
     * JPA default constructor.
     */
    protected AdellijkeTitelPredikaat() {}

    /**
     * Maak een nieuwe adellijke titel predikaat.
     * 
     * @param lo3AdellijkeTitelPredikaat lo3 adellijke titel predikaat
     * @param geslachtsaanduiding geslachtsaanduiding
     */
    public AdellijkeTitelPredikaat(final String lo3AdellijkeTitelPredikaat, final Geslachtsaanduiding geslachtsaanduiding) {
        setLo3AdellijkeTitelPredikaat(lo3AdellijkeTitelPredikaat);
        setGeslachtsaanduiding(geslachtsaanduiding);
    }

    /**
     * Geef de waarde van id van AdellijkeTitelPredikaat.
     * 
     * @return de waarde van id van AdellijkeTitelPredikaat
     */
    public Integer getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van AdellijkeTitelPredikaat.
     * 
     * @param id de nieuwe waarde voor id van AdellijkeTitelPredikaat
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van lo3 adellijke titel predikaat van AdellijkeTitelPredikaat.
     * 
     * @return de waarde van lo3 adellijke titel predikaat van AdellijkeTitelPredikaat
     */
    public String getLo3AdellijkeTitelPredikaat() {
        return lo3AdellijkeTitelPredikaat;
    }

    /**
     * Zet de waarden voor lo3 adellijke titel predikaat van AdellijkeTitelPredikaat.
     * 
     * @param lo3AdellijkeTitelPredikaat de nieuwe waarde voor lo3 adellijke titel predikaat van
     *        AdellijkeTitelPredikaat
     */
    public void setLo3AdellijkeTitelPredikaat(final String lo3AdellijkeTitelPredikaat) {
        ValidationUtils.controleerOpNullWaarden(LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_NIET_NULL_ZIJN, lo3AdellijkeTitelPredikaat);
        ValidationUtils.controleerOpLegeWaarden(LO3_ADELLIJKE_TITEL_PREDIKAAT_MAG_GEEN_LEGE_STRING_ZIJN, lo3AdellijkeTitelPredikaat);
        this.lo3AdellijkeTitelPredikaat = lo3AdellijkeTitelPredikaat;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van AdellijkeTitelPredikaat.
     * 
     * @return de waarde van geslachtsaanduiding van AdellijkeTitelPredikaat
     */
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return Geslachtsaanduiding.parseId(geslachtsaanduidingId);
    }

    /**
     * Zet de waarden voor geslachtsaanduiding van AdellijkeTitelPredikaat.
     * 
     * @param geslachtsaanduiding de nieuwe waarde voor geslachtsaanduiding van
     *        AdellijkeTitelPredikaat
     */
    public void setGeslachtsaanduiding(final Geslachtsaanduiding geslachtsaanduiding) {
        ValidationUtils.controleerOpNullWaarden("geslachtsaanduiding mag niet null zijn.", geslachtsaanduiding);
        geslachtsaanduidingId = geslachtsaanduiding.getId();
    }

    /**
     * Geef de waarde van adellijke titel van AdellijkeTitelPredikaat.
     * 
     * @return de waarde van adellijke titel van AdellijkeTitelPredikaat
     */
    public AdellijkeTitel getAdellijkeTitel() {
        return AdellijkeTitel.parseId(adellijkeTitelId);
    }

    /**
     * Zet de waarden voor adellijke titel van AdellijkeTitelPredikaat.
     * 
     * @param adellijkeTitel de nieuwe waarde voor adellijke titel van AdellijkeTitelPredikaat
     */
    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        if (adellijkeTitel == null) {
            adellijkeTitelId = null;
        } else {
            adellijkeTitelId = adellijkeTitel.getId();
        }
    }

    /**
     * Geef de waarde van predikaat van AdellijkeTitelPredikaat.
     * 
     * @return de waarde van predikaat van AdellijkeTitelPredikaat
     */
    public Predicaat getPredikaat() {
        return Predicaat.parseId(predikaatId);
    }

    /**
     * Zet de waarden voor predikaat van AdellijkeTitelPredikaat.
     * 
     * @param predikaat de nieuwe waarde voor predikaat van AdellijkeTitelPredikaat
     */
    public void setPredikaat(final Predicaat predikaat) {
        if (predikaat == null) {
            predikaatId = null;
        } else {
            predikaatId = predikaat.getId();
        }
    }
}

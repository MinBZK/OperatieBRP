/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the srtdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "srtdoc", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQuery(name = "SoortDocument" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from SoortDocument")
public class SoortDocument extends AbstractEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "srtdoc_id_generator", sequenceName = "kern.seq_srtdoc", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "srtdoc_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Column(nullable = false, length = 80, unique = true)
    private String naam;

    @Column(name = "oms", nullable = false, length = 250)
    private String omschrijving;

    @Column(name = "rangorde")
    private Integer rangorde;

    @Column(name = "registersrt", length = 1)
    private Character registersoort;

    /**
     * JPA default constructor.
     */
    protected SoortDocument() {}

    /**
     * Maak een nieuwe soort document.
     *
     * @param naam naam
     * @param omschrijving omschrijving
     */
    public SoortDocument(final String naam, final String omschrijving) {
        setNaam(naam);
        setOmschrijving(omschrijving);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van SoortDocument.
     *
     * @param id de nieuwe waarde voor id van SoortDocument
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van omschrijving van SoortDocument.
     *
     * @return de waarde van omschrijving van SoortDocument
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van SoortDocument.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van SoortDocument
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden("omschrijving mag niet null zijn", omschrijving);
        ValidationUtils.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van naam van SoortDocument.
     *
     * @return de waarde van naam van SoortDocument
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van SoortDocument.
     *
     * @param naam de nieuwe waarde voor naam van SoortDocument
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van rangorde van SoortDocument.
     *
     * @return de waarde van rangorde van SoortDocument
     */
    public Integer getRangorde() {
        return rangorde;
    }

    /**
     * Zet de waarden voor rangorde van SoortDocument.
     *
     * @param rangorde de nieuwe waarde voor rangorde van SoortDocument
     */
    public void setRangorde(final Integer rangorde) {
        this.rangorde = rangorde;
    }

    /**
     * Geef de waarde van registersoort van SoortDocument.
     *
     * @return de waarde van registersoort van SoortDocument
     */
    public Character getRegistersoort() {
        return registersoort;
    }

    /**
     * Zet de waarden voor registersoort van SoortDocument.
     *
     * @param registersoort de nieuwe waarde voor registersoort van SoortDocument
     */
    public void setRegistersoort(final Character registersoort) {
        this.registersoort = registersoort;
    }

    /**
     * Bepaal of een ander soort document inhoudelijk gelijk is.
     *
     * @param soortDocument soort document
     * @return true, als het andere soort document inhoudelijk gelijk is, anders false
     */
    public final boolean isInhoudelijkGelijkAan(final SoortDocument soortDocument) {
        return soortDocument != null && Objects.equals(naam, soortDocument.naam);
    }
}

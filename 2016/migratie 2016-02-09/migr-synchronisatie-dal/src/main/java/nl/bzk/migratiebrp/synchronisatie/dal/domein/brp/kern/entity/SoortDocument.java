/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.DynamischeStamtabel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the srtdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "srtdoc", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class SoortDocument extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;
    private static final String NAAM_MAG_NIET_NULL_ZIJN = "naam mag niet null zijn";
    private static final String NAAM_MAG_GEEN_LEGE_STRING_ZIJN = "naam mag geen lege string zijn";
    private static final String OMSCHRIJVING_MAG_NIET_NULL_ZIJN = "omschrijving mag niet null zijn";
    private static final String OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN = "omschrijving mag geen lege string zijn";

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Short id;

    @Column(insertable = false, updatable = false, nullable = false, length = 80, unique = true)
    private String naam;

    @Column(name = "oms", insertable = false, updatable = false, nullable = false, length = 250)
    private String omschrijving;

    @Column(name = "rangorde", insertable = false, updatable = false)
    private Integer rangorde;

    /**
     * JPA default constructor.
     */
    protected SoortDocument() {
    }

    /**
     * Maak een nieuwe soort document.
     *
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     */
    public SoortDocument(final String naam, final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        Validatie.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        Validatie.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
    }

    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarde van omschrijving.
     *
     * @param omschrijving
     *            omschrijving
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden(OMSCHRIJVING_MAG_NIET_NULL_ZIJN, omschrijving);
        Validatie.controleerOpLegeWaarden(OMSCHRIJVING_MAG_GEEN_LEGE_STRING_ZIJN, omschrijving);
        this.omschrijving = omschrijving;
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
     * @param naam
     *            naam
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden(NAAM_MAG_NIET_NULL_ZIJN, naam);
        Validatie.controleerOpLegeWaarden(NAAM_MAG_GEEN_LEGE_STRING_ZIJN, naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van rangorde.
     *
     * @return rangorde
     */
    public Integer getRangorde() {
        return rangorde;
    }

    /**
     * Zet de waarde van rangorde.
     *
     * @param rangorde
     *            rangorde
     */
    public void setRangorde(final Integer rangorde) {
        this.rangorde = rangorde;
    }

    /**
     * Bepaal of een ander soort document inhoudelijk gelijk is.
     *
     * @param soortDocument
     *            soort document
     * @return true, als het andere soort document inhoudelijk gelijk is, anders false
     */
    boolean isInhoudelijkGelijkAan(final SoortDocument soortDocument) {
        if (this == soortDocument) {
            return true;
        }
        if (soortDocument == null) {
            return false;
        }
        if (getNaam() == null) {
            if (soortDocument.getNaam() != null) {
                return false;
            }
        } else if (!getNaam().equals(soortDocument.getNaam())) {
            return false;
        }
        return true;
    }
}

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
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the aang database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "aang", schema = "kern")
@Cacheable
@NamedQuery(name = "Aangever" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Aangever")
public class Aangever extends AbstractEntiteit implements Serializable, DynamischeStamtabel {
    /**
     * Ingeschrevene.
     */
    public static final char INGESCHREVENE = 'I';
    /**
     * Partner.
     */
    public static final char PARTNER = 'P';
    /**
     * Meerderjarig inwonend kind voor ouder.
     */
    public static final char MEERDERJARIG_INWONEND_KIND = 'K';
    /**
     * Ouder voor meerderjarig kind.
     */
    public static final char OUDER = 'O';
    /**
     * gezaghouder
     */
    public static final char GEZAGHOUDER = 'G';

    private static final long serialVersionUID = 1L;


    @Id
    @SequenceGenerator(name = "aang_id_generator", sequenceName = "kern.seq_aang", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aang_id_generator")
    @Column(nullable = false, updatable = false)
    private Short id;

    @Column(length = 1, unique = true)
    private char code;

    @Column(nullable = false, length = 80, unique = true)
    private String naam;

    @Column(name = "oms", nullable = false, length = 250)
    private String omschrijving;

    /**
     * JPA default constructor.
     */
    protected Aangever() {}

    /**
     * Default constructor.
     *
     * @param code De code.
     * @param naam De naam.
     * @param omschrijving De omschrijving.
     */
    public Aangever(final char code, final String naam, final String omschrijving) {
        this.code = code;
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
     * Zet de waarden voor id van Aangever.
     *
     * @param id de nieuwe waarde voor id van Aangever
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * Geef de waarde van code van Aangever.
     *
     * @return de waarde van code van Aangever
     */
    public char getCode() {
        return code;
    }

    /**
     * Zet de waarden voor code van Aangever.
     *
     * @param code de nieuwe waarde voor code van Aangever
     */
    public void setCode(final char code) {
        this.code = code;
    }

    /**
     * Geef de waarde van naam van Aangever.
     *
     * @return de waarde van naam van Aangever
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Aangever.
     *
     * @param naam de nieuwe waarde voor naam van Aangever
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }

    /**
     * Geef de waarde van omschrijving van Aangever.
     *
     * @return de waarde van omschrijving van Aangever
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarden voor omschrijving van Aangever.
     *
     * @param omschrijving de nieuwe waarde voor omschrijving van Aangever
     */
    public void setOmschrijving(final String omschrijving) {
        ValidationUtils.controleerOpNullWaarden("omschrijving mag niet null zijn", omschrijving);
        ValidationUtils.controleerOpLegeWaarden("omschrijving mag geen lege string zijn", omschrijving);
        this.omschrijving = omschrijving;
    }
}

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
 * The persistent class for the rdnvervallenreisdoc database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "aandinhingvermissingreisdoc", schema = "kern")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SuppressWarnings("checkstyle:designforextension")
public class AanduidingInhoudingOfVermissingReisdocument extends AbstractDeltaEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false, updatable = false, nullable = false)
    private Short id;

    @Column(insertable = false, updatable = false, nullable = false, length = 1, unique = true)
    private char code;

    @Column(insertable = false, updatable = false, nullable = false, length = 80, unique = true)
    private String naam;

    /**
     * JPA default constructor.
     */
    protected AanduidingInhoudingOfVermissingReisdocument() {
    }

    /**
     * Maak een nieuwe aanduiding inhouding of vermissing reisdocument.
     *
     * @param code
     *            code
     * @param naam
     *            naam
     */
    public AanduidingInhoudingOfVermissingReisdocument(final char code, final String naam) {
        setCode(code);
        setNaam(naam);
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
     * Geef de waarde van code.
     *
     * @return code
     */
    public char getCode() {
        return code;
    }

    /**
     * Zet de waarde van code.
     *
     * @param code
     *            code
     */
    public void setCode(final char code) {
        this.code = code;
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
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        Validatie.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }
}

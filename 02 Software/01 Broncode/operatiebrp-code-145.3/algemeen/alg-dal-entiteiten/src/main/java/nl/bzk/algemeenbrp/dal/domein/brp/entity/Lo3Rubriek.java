/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the ConvLO3Rubriek database table.
 */
@Entity
@Table(name = "convlo3rubriek", schema = "conv")
@NamedQuery(name = "Lo3Rubriek" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "select l from Lo3Rubriek l")
public class Lo3Rubriek extends AbstractEntiteit implements DynamischeStamtabel, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "convlo3rubriek_id_generator", sequenceName = "conv.seq_convlo3rubriek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "convlo3rubriek_id_generator")
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(name = "naam", length = 2, nullable = false, unique = true)
    private String naam;

    /**
     * JPA default constructor.
     */
    protected Lo3Rubriek() {
    }

    /**
     * Maak een nieuwe lo3 rubriek.
     * @param naam naam
     */
    public Lo3Rubriek(final String naam) {
        setNaam(naam);
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
     * Zet de waarden voor id van Lo3Rubriek.
     * @param id de nieuwe waarde voor id van Lo3Rubriek
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Geef de waarde van naam van Lo3Rubriek.
     * @return de waarde van naam van Lo3Rubriek
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de waarden voor naam van Lo3Rubriek.
     * @param naam de nieuwe waarde voor naam van Lo3Rubriek
     */
    public void setNaam(final String naam) {
        ValidationUtils.controleerOpNullWaarden("naam mag niet null zijn", naam);
        ValidationUtils.controleerOpLegeWaarden("naam mag geen lege string zijn", naam);
        this.naam = naam;
    }
}

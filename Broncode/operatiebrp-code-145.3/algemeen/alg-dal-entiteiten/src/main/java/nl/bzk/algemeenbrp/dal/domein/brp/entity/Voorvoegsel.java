/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.NamedQuery;

/**
 * The persistent class for the voorvoegsel database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "voorvoegsel", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"voorvoegsel", "scheidingsteken"}))
@Cacheable
@NamedQuery(name = "Voorvoegsel" + Constanten.ZOEK_ALLES_VOOR_CACHE, query = "from Voorvoegsel")
public class Voorvoegsel extends AbstractEntiteit implements Serializable, DynamischeStamtabel {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "voorvoegsel_id_generator", sequenceName = "kern.seq_voorvoegsel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voorvoegsel_id_generator")
    @Column(updatable = false, nullable = false)
    private Short id;

    @Embedded
    private VoorvoegselSleutel voorvoegselSleutel;

    /**
     * JPA default constructor.
     */
    protected Voorvoegsel() {}

    /**
     * Maak een nieuwe voorvoegsel.
     *
     * @param voorvoegselSleutel de samengestelde sleutel uit de database voor deze entiteit
     */
    public Voorvoegsel(final VoorvoegselSleutel voorvoegselSleutel) {
        setVoorvoegselSleutel(voorvoegselSleutel);
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.algemeen.dal.domein.brp.DynamischeStamtabel#getId()
     */
    @Override
    public Short getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van Voorvoegsel.
     *
     * @param id de nieuwe waarde voor id van Voorvoegsel
     */
    public void setId(final Short id) {
        this.id = id;
    }

    /**
     * @return Geeft de samengestelde sleutel voor deze entiteit terug.
     */
    public VoorvoegselSleutel getVoorvoegselSleutel() {
        return voorvoegselSleutel;
    }

    /**
     * Zet de samengestelde sleutel voor deze entiteit.
     *
     * @param voorvoegselSleutel de samengestelde sleutel
     */
    public void setVoorvoegselSleutel(final VoorvoegselSleutel voorvoegselSleutel) {
        ValidationUtils.controleerOpNullWaarden("VoorvoegselScheidingstekenPaar mag niet null zijn", voorvoegselSleutel);
        this.voorvoegselSleutel = voorvoegselSleutel;
    }
}

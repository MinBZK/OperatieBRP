/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Soort actie waarmee een bijhouding wordt aangeduid.
 */
@Entity
@Table(name = "srtactie", schema = "kern")
@Access(value = javax.persistence.AccessType.FIELD)
public class SoortActie {

    @Id
    private Long id;
    @Column(name = "naam")
    private String naam;
    @Column(name = "categorieactie")
    private Long categorieActie;

    /**
     * Standaard constructor.
     */
    public SoortActie() {
    }

    /**
     * Constructor met initialisatie van velden.
     *
     * @param id Id van de soort actie;.
     * @param naam Naam van de actie.
     * @param categorieActie Categorie waar de actie onder valt.
     */
    public SoortActie(final Long id, final String naam, final Long categorieActie) {
        this.id = id;
        this.naam = naam;
        this.categorieActie = categorieActie;
    }

    public Long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Long getCategorieActie() {
        return categorieActie;
    }
}

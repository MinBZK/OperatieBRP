/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Database object.
 */
@Entity
@Table(name = "DbObject", schema = "Kern")
@Access(AccessType.FIELD)
public class GegevensElement {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Ouder")
    private GegevensElement ouder;

    @Column(name = "JavaIdentifier")
    private String javaIdentifier;

    /**
     * No-arg constructor voor JPA.
     */
    protected GegevensElement() {
    }

    /**
     * Constructor voor nieuwe GegevensElement instanties, waarbij de 'ouder' en de 'identifier' direct worden
     * geinitialiseerd.
     *
     * @param ouder de entiteit vaarvan dit object een attribuut is of null als dit object zelf een entiteit is
     * @param javaIdentifier de java class- of veldnaam
     */
    public GegevensElement(final GegevensElement ouder, final String javaIdentifier) {
        this.ouder = ouder;
        this.javaIdentifier = javaIdentifier;
    }

    /**
     * @return primaire sleutel
     */
    public Long getId() {
        return id;
    }

    public GegevensElement getOuder() {
        return ouder;
    }

    public String getJavaIdentifier() {
        return javaIdentifier;
    }

}

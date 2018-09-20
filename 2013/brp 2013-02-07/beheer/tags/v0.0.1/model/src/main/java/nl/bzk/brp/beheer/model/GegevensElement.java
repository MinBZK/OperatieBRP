/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    @Column(name = "JavaIdentifier")
    private String javaIdentifier;

    /**
     * No-arg constructor voor JPA.
     */
    protected GegevensElement() {
    }

    /**
     * @param javaIdentifier de java class- of veldnaam maar dan beginnend met een hoofdletter
     */
    public GegevensElement(final String javaIdentifier) {
        this.javaIdentifier = javaIdentifier;
    }

    /**
     * @return primaire sleutel
     */
    public Long getId() {
        return id;
    }

    public String getJavaIdentifier() {
        return javaIdentifier;
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class ModelElement {

    @Id
    private Integer id;

    public Integer getId() {
        return id;
    }

    public String getIdAsString() {
        return String.valueOf(id);
    }

    public void setIdAsString(final String id) {
        this.id = Integer.valueOf(id);
    }
}

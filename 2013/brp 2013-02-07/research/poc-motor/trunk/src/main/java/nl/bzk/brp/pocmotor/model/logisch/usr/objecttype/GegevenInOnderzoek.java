/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model.logisch.usr.objecttype;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.pocmotor.model.logisch.gen.objecttype.AbstractGegevenInOnderzoek;

/**
 * Gegeven in onderzoek
  */
@Entity
@Table(schema = "Kern", name = "GegevenInOnderzoek")
public class GegevenInOnderzoek extends AbstractGegevenInOnderzoek {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

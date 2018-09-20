/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.SoortPersoon;


/**
 * Persoon.Identiteit.
 */
public class PersoonIdentiteit {

    private Long         id;

    private SoortPersoon soort;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SoortPersoon getSoort() {
        return soort;
    }

    public void setSoort(final SoortPersoon soort) {
        this.soort = soort;
    }
}

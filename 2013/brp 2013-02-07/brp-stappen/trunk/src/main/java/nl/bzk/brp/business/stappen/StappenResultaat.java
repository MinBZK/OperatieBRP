/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import java.util.List;

import nl.bzk.brp.model.validatie.Melding;

/**
 * TODO: Add documentation
 */
public interface StappenResultaat {

    /**
     * Methode die nagaat of het resultaat van de stap fouten bevat.
     * @return
     */
    boolean isFoutief();

    /**
     * Methode die nagaat of het resultaat van de stap succesvol is.
     * @return
     */
    boolean isSuccesvol();

    /**
     * Ga na of het resultaat fouten bevat die het proces van verwerken moet stoppen
     * @return
     */
    boolean bevatStoppendeFouten();
}

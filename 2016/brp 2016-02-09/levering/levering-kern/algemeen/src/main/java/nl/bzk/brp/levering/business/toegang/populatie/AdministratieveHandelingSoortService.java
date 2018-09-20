/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;


/**
 * Deze service bepaalt of een administratieve handeling van een bepaald soort is.
 */
public interface AdministratieveHandelingSoortService {

    /**
     * Bepaalt of de administratieve handeling het plaatsen van een afnemerindicatie is.
     *
     * @param administratieveHandeling De administratieve handeling.
     * @return Boolean true als de administratieve handeling het plaatsen van een afnemerindicatie was, anders false.
     */
    boolean isPlaatsingAfnemerIndicatie(AdministratieveHandeling administratieveHandeling);

    /**
     * Bepaalt of de administratieve handeling het verwijderen van een afnemerindicatie is.
     *
     * @param administratieveHandeling De administratieve handeling.
     * @return Boolean true als de administratieve handeling het verwijderen van een afnemerindicatie was, anders false.
     */

    boolean isVerwijderingAfnemerIndicatie(AdministratieveHandeling administratieveHandeling);
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.webservice;

import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;


/**
 * De Synchronisatie webservice interface voor het synchroniseren van personen en stamtabellen van de BRP.
 */
public interface OnderhoudAfnemerindicatiesService {

    /**
     * Dit is de webservice methode voor het registreren van een afnemer indicatie.
     *
     * @param bericht Het bijhoudings bericht met de afnemer indicatie registrate wat ontvangen is.
     * @return Het resultaat van de registratie.
     */
    RegistreerAfnemerindicatieAntwoordBericht registreerAfnemerindicatie(RegistreerAfnemerindicatieBericht bericht);

}

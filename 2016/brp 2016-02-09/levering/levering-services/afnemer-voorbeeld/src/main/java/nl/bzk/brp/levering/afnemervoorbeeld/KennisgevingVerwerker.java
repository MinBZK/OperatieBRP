/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemervoorbeeld;

import nl.bzk.brp.brp0200.SynchronisatieVerwerkPersoon;

/**
 * De verwerking van een kennisgevings bericht van de BRP.
 */
public interface KennisgevingVerwerker {

    /**
     * Verwerkt een bericht van het type Administratieve handeling.
     *
     * @param synchronisatieVerwerkPersoon het bericht
     */
    void verwerkKennisgeving(SynchronisatieVerwerkPersoon synchronisatieVerwerkPersoon);
}

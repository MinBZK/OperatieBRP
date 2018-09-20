/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.levering;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;


/**
 * Deze klasse is de container voor het uitgaande kennisgeving bericht naar de afnemers.
 */
public class MutatieBericht extends AbstractSynchronisatieBericht {

    /**
     * Instantieert een nieuw Kennisgeving bericht.
     *
     * @param administratieveHandelingSynchronisatie
     *         administratieve handeling kennisgeving
     */
    public MutatieBericht(final AdministratieveHandelingSynchronisatie administratieveHandelingSynchronisatie) {
        super(administratieveHandelingSynchronisatie);
    }

    @Override
    public SoortSynchronisatie geefSoortSynchronisatie() {
        return SoortSynchronisatie.MUTATIEBERICHT;
    }
}

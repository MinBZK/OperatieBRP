/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber;


/**
 * De voor een Administratieve handeling gebruikte bronnen.
 * <p/>
 * Om een administratieve handeling te kunnen verantwoorden, kent de BRP een mechanisme waarin Acties via een koppeltabel worden verantwoord door
 * Documenten. Hierbij is het wenselijk dat eventuele nieuwe documenten kunnen worden gescand. Om deze reden is er een vehikel nodig om de (technische)
 * id's van de Documenten terug te leveren, op het niveau van het bericht c.q. de Administratieve handeling, in plaats van het niveau waarin het is
 * vastgelegd, zijnde de Actie. Hierbij zijn de koppelingen tussen Administratieve handeling enerzijds, en Document anderzijds, afleidbaar uit de (wel
 * geadministreerde) koppeltabel tussen Actie en Document.
 */
public interface AdministratieveHandelingBron extends AdministratieveHandelingBronBasis {

}

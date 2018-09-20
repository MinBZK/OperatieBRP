/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.ber.GedeblokkeerdeMelding;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;


/**
 * Het door middel van een bericht deblokkeren of gedeblokkkerd hebben van een (deblokkeerbare) fout.
 *
 * Een bijhoudingsbericht kan aanleiding zijn tot ��n of meer deblokkeerbare fouten. Een deblokkeerbare fout kan worden
 * gedeblokkeerd door in een bijhoudingsbericht expliciet de (deblokkeerbare) fout te de-blokkeren. Een gedeblokkeerde
 * fout wordt twee keer gekoppeld aan een bericht: enerzijds door een koppeling tussen het inkomende bijhoudingsbericht
 * en de deblokkage; anderzijds door het uitgaand bericht waarin wordt medegedeeld welke deblokkeringen zijn verwerkt.
 *
 *
 *
 */
public interface AdministratieveHandelingGedeblokkeerdeMeldingBasis extends ObjectType {

    /**
     * Retourneert Administratieve handeling van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Administratieve handeling.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Retourneert Gedeblokkeerde melding van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return Gedeblokkeerde melding.
     */
    GedeblokkeerdeMelding getGedeblokkeerdeMelding();

}

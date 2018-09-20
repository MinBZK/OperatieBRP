/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.service;

/**
 * Deze service zorgt voor de verwerking van mutaties/administratieve handelingen.
 */
public interface AdministratieveHandelingVerwerkerService {

    /**
     * Verwerkt een administratieve handeling met het opgegeven id. Dit is het startpunt voor de verwerking van een
     * mutatie.
     * @param administratieveHandelingId Het id van de administratieve handeling.
     */
    void verwerkAdministratieveHandeling(Long administratieveHandelingId);

}

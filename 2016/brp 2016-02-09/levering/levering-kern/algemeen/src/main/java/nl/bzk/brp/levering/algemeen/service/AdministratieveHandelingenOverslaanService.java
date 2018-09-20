/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Deze service biedt een lijst van administratieve handelingen aan die niet verwerkt moeten worden (zoals initiele
 * vullingen vanuit de migratie).
 */
public interface AdministratieveHandelingenOverslaanService {

    /**
     * Geeft een lijst van administratieve handelingen die overgeslagen moeten worden bij leveren.
     *
     * @return De lijst van soorten administratieve handelingen.
     */
    List<SoortAdministratieveHandeling> geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden();

}

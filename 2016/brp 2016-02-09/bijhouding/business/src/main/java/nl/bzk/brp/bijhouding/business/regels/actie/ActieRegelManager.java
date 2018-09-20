/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.List;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

/**
 * Kan de juiste actie-gerelateerde regels vinden op basis van een SoortAdministratieveHandeling.
 */
public interface ActieRegelManager {

    /**
     * Geeft de regels die voor een actie moeten worden uitgevoerd.
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @return een lijst van VoorActieRegel types
     */
    List<Class<? extends VoorActieRegel>> getVoorActieRegels(SoortAdministratieveHandeling soortAdministratieveHandeling);

    /**
     * Geeft de regels die na een actie moeten worden uitgevoerd.
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @return een lijst van NaActieRegel types
     */
    List<Class<? extends NaActieRegel>> getNaActieRegels(SoortAdministratieveHandeling soortAdministratieveHandeling);
}

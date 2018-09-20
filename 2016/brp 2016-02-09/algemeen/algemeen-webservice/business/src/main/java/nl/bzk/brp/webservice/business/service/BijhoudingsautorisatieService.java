/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.util.AutorisatieOffloadGegevens;

/**
 * Service voor authenticatie en autorisatie binnen Bijhouding.
 */
public interface BijhoudingsautorisatieService {

    /**
     * Verifieer of een client is geautoriseerd op basis van de geleverde partij en opgeslagen gegevens voor deze partij.
     *
     * @param waarde
     * @param partijcode de partij welke geautoriseerd dient te worden
     * @param offloadGegevens de gegevens welke door de offloader zijn verstrekt
     * @return true als geautoriseerd, false zo niet
     */
    void controleerAutorisatie(final SoortAdministratieveHandeling waarde, final String partijcode, AutorisatieOffloadGegevens offloadGegevens) throws AutorisatieException;
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling;

import java.util.List;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;

/**
 * Bepaalt voor welke autorisaties, welke personen geleverd moeten worden.
 */
@FunctionalInterface
public interface MutatieleveringService {

    /**
     * @param mutatiehandeling de handeling
     * @return een lijst met MutatieAutorisatie objecten
     */
    List<Mutatielevering> bepaalLeveringen(Mutatiehandeling mutatiehandeling);


}

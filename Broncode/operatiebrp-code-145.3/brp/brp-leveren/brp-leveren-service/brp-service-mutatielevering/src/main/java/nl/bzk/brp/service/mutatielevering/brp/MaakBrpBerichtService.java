/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import java.util.List;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiebericht;
import nl.bzk.brp.service.mutatielevering.dto.Mutatiehandeling;
import nl.bzk.brp.service.mutatielevering.dto.Mutatielevering;

/**
 * Maakt de berichten voor het BRP stelsel.
 */
@FunctionalInterface
public interface MaakBrpBerichtService {


    /**
     * @param mutatieleveringen mutatieleveringen
     * @param mutatiehandeling mutatiehandeling
     * @return lijst met mutatieberichten
     * @throws StapException stap fout
     */
    List<Mutatiebericht> maakBerichten(List<Mutatielevering> mutatieleveringen, final Mutatiehandeling mutatiehandeling) throws StapException;

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels;

import java.util.List;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.regels.RegelInterface;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;

/**
 * Extentie van de bedrijfsregelmanager interface voor synchronisatie.
 */
public interface SynchronisatieBedrijfsregelManager extends BedrijfsregelManager {

    /**
     * Geeft de uit te voeren regels voor verwerking.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels voor verwerking
     */
    List<RegelInterface> getUitTeVoerenRegelsVoorVerwerking(SoortBericht soortBericht);

    /**
     * Geeft de uit te voeren regels na verwerking.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels na verwerking
     */
    List<RegelInterface> getUitTeVoerenRegelsNaVerwerking(SoortBericht soortBericht);

    /**
     * Geeft de uit te voeren regels voor bericht.
     *
     * @param soortBericht soort bericht
     * @return uit te voeren regels voor bericht
     */
    List<RegelInterface> getUitTeVoerenRegelsVoorBericht(SoortBericht soortBericht);

}

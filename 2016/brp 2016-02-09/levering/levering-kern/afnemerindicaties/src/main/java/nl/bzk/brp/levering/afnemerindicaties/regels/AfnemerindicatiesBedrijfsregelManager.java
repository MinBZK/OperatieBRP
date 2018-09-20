/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.regels;

import java.util.List;
import nl.bzk.brp.business.regels.Bedrijfsregel;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;

/**
 * Bedrijfsregelmanager voor de afnemerindicaties.
 */
public interface AfnemerindicatiesBedrijfsregelManager extends BedrijfsregelManager {

    /**
     * Geeft een lijst van uit te voeren bedrijfsregels voor een gegeven effect afnemerindicatie (plaatsen/verwijderen).
     *
     * @param effectAfnemerindicaties Het effect afnemerindicaties.
     * @return De lijst van bedrijfsregels.
     */
    List<? extends Bedrijfsregel> getUitTeVoerenRegelsVoorVerwerking(EffectAfnemerindicaties effectAfnemerindicaties);

}

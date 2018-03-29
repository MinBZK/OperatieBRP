/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.repository;

import java.util.List;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieGbav;

/**
 * DAO interface voor de berichten voor de leveringvergelijking.
 */
public interface LeveringsVergelijkingBerichtCorrelatieGbavRepository {

    /**
     * Haal de berichten voor de leveringvergelijkingen uit GBA-V op.
     * @return Lijst met de berichten voor de leveringvergelijkingen.
     */
    List<LeveringsVergelijkingBerichtCorrelatieGbav> haalLeveringVergelijkingBerichtenGbavOp();

}

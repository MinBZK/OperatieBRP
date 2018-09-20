/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.repository;

import nl.bzk.brp.model.operationeel.lev.LeveringPersoonModel;

/**
 * Repository voor leveringPersoon entiteiten, gebruikt voor protocollering.
 */
public interface LeveringPersoonRepository {

    /**
     * Slaat een nieuw leveringPersoonModel op.
     *
     * @param leveringPersoonModel Het nieuwe leveringPersoonModel.
     * @return De nieuw opgeslagen entiteit.
     */
    LeveringPersoonModel opslaanNieuweLeveringPersoon(LeveringPersoonModel leveringPersoonModel);

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.logisch.kern.basis.PersoonOpschortingGroepBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOpschortingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 * Repository voor historie opschorting.
 */
public interface HistoriePersoonOpschortingRepository extends
        GroepMaterieleHistorieRepository<PersoonModel, PersoonOpschortingGroepBasis, HisPersoonOpschortingModel>
{

    /**
     * Haalt de actuele datum van opschorting op.
     *
     * @param persoon Persoon waarvoor de opschortingsdatum opgehaald moet worden.
     * @return datum
     */
    Datum haalOpActueleDatumOpschorting(final PersoonModel persoon);
}

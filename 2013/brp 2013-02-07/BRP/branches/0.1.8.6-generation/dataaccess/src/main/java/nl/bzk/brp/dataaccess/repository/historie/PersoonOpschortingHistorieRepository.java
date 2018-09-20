/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.historie;

import nl.bzk.brp.dataaccess.repository.jpa.historie.GroepHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOpschortingHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * Repository voor historie opschorting.
 */
public interface PersoonOpschortingHistorieRepository extends
    GroepHistorieRepository<PersoonModel, PersoonOpschortingHisModel>
{

    /**
     * Haalt de actuele datum van opschorting op.
     *
     * @param persoon Persoon waarvoor de opschortingsdatum opgehaald moet worden.
     * @return datum
     */
    Datum haalOpActueleDatumOpschorting(final PersoonModel persoon);
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import java.util.Set;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;

/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.autaut.HisPersoonAfnemerindicatieModel} tabel.
 */
public interface HisAfnemerindicatieBlobRepository {

    /**
     * Leest afnemerindicaties direct uit de database. Deze set kan gebruikt worden voor het maken van nieuwe blobs.
     *
     * @param technischId id van de persoon voor wie we indicaties ophalen
     * @return set van afnemerindicaties, deze kan leeg zijn
     */
    Set<PersoonAfnemerindicatieHisVolledigImpl> leesGenormaliseerdModelVoorNieuweBlob(final Integer technischId);

    /**
     * Leest afnemerindicaties direct uit de database. Deze set kan gebruikt worden voor het maken van in-memory blobs.
     *
     * @param technischId id van de persoon voor wie we indicaties ophalen
     * @return set van afnemerindicaties, deze kan leeg zijn
     */
    Set<PersoonAfnemerindicatieHisVolledigImpl> leesGenormaliseerdModelVoorInMemoryBlob(final Integer technischId);

}

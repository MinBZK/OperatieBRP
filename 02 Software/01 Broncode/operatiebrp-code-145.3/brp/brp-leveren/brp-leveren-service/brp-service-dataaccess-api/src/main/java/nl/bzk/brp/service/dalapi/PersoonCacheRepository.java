/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.services.blobber.BlobException;

/**
 * Repository voor de {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache} class.
 */
public interface PersoonCacheRepository {

    /**
     * Haal persooncache op ahv persoonId.
     * @param persoonId het persoonId.
     * @return persooncache
     */
    PersoonCache haalPersoonCacheOp(long persoonId);

    /**
     * Haal persooncaches op voor een reeks persoonId's.
     * @param ids lijst met persoonId's
     * @return lijst met persooncaches.
     */
    List<PersoonCache> haalPersoonCachesOp(Set<Long> ids);

    /**
     * Maakt een nieuwe afnemerindicatie blob.
     * @param persoonId id van de persoon
     * @param lockVersiePersoon lock versie van de persoon
     * @param afnemerindicatieLockVersie afnemerindicatieLockVersie
     * @throws BlobException als de blob niet gemaakt kan worden
     */
    void updateAfnemerindicatieBlob(long persoonId, final Long lockVersiePersoon, final Long afnemerindicatieLockVersie) throws BlobException;
}

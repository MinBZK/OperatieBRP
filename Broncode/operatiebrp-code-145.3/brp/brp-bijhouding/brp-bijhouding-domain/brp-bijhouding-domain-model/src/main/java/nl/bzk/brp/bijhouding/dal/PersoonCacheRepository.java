/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.dal.repositories.BasisRepository;

/**
 * CRUD functionaliteit voor de {@link PersoonCache} entiteit.
 */
public interface PersoonCacheRepository extends BasisRepository<PersoonCache, Long> {

    /**
     * Maakt een persooncache entiteit van depersoon en slaat deze als blob op de in de database.
     * @param persoon Persoon.
     */
    void slaPersoonCacheOp(final Persoon persoon);
}

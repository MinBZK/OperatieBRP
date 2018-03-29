/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;


import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;

/**
 * PartijCache. Cache voor partij tabel.
 */
public interface PartijCache {


    /**
     * Herlaadt de partijen.
     * @return de cache entry
     */
    CacheEntry herlaad();

    /**
     * Geeft de partij met code.
     * @param code de partij code
     * @return de partij
     */
    Partij geefPartij(String code);

    /**
     * Geeft de partij obv een oin.
     * @param oin het OIN
     * @return de partij
     */
    Partij geefPartijMetOin(String oin);

    /**
     * Geeft de partij obv een id.
     * @param id id van de partij
     * @return de partij
     */
    Partij geefPartijMetId(short id);

    /**
     * Geeft de partij obv een partijRolId.
     * @param partijRolId het id van de partij rol
     * @return de partij
     */
    PartijRol geefPartijRol(int partijRolId);
}

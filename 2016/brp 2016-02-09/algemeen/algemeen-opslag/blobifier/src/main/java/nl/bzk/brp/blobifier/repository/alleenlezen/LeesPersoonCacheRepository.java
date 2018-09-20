/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;

/**
 * Repository waarmee de persoon caches gelezen worden.
 */
public interface LeesPersoonCacheRepository {

    /**
     * Haalt voor een lijst van persoon id's de persoon caches op met de persoonHisVolledigGegevens gevuld.
     * @param ids De id's van de personen.
     * @return Een map met als key het id van de persoon en als value de cache.
     */
    Map<Integer, PersoonCacheModel> haalPersoonCachesOpMetPersoonHisVolledigGegevens(List<Integer> ids);

    /**
     * Haalt voor een persoon id de persoon cache op met de persoonHisVolledigGegevens gevuld.
     * @param id Het id van de persoon.
     * @return De persoon cache.
     */
    PersoonCacheModel haalPersoonCacheOpMetPersoonHisVolledigGegevens(Integer id);

    /**
     * Haalt voor een lijst van persoon id's de persoon caches op met de administratieveHandelingenGegevens gevuld.
     * @param ids De id's van de personen.
     * @return Een map met als key het id van de persoon en als value de cache.
     */
    Map<Integer, PersoonCacheModel> haalPersoonCachesOpMetAdministratieveHandelingenGegevens(List<Integer> ids);

    /**
     * Haalt voor een persoon id de persoon cache op met de administratieveHandelingenGegevens gevuld.
     * @param id Het id van de persoon.
     * @return De persoon cache.
     */
    PersoonCacheModel haalPersoonCacheOpMetAdministratieveHandelingenGegevens(Integer id);

    /**
     * Haalt een persoon cache op. <strong>Let op: de gegevens attributen zijn lazy!</strong>
     * @param id Het id van de persoon.
     * @return De persoon cache.
     */
    PersoonCacheModel haalPersoonCacheOp(Integer id);

}

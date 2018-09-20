/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.lezenenschrijven;

import java.util.List;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;

/**
 * Repository voor het schrijven van persoon caches.
 */
public interface SchrijfPersoonCacheRepository {
    /**
     * Slaat een nieuwe persoon cache op.
     *
     * @param persoonCache De persoon cache.
     */
    void opslaanNieuwePersoonCache(PersoonCacheModel persoonCache);

    /**
     * Haalt de persooncache op uit de master database. LET OP! Deze methode alleen gebruiken als de persooncache
     * gewijzigd moet worden. Gebruik anders de LeesPersoonCacheRepository.
     *
     * @param id Het id van de cache.
     * @return De persoon cache.
     */
    PersoonCacheModel haalPersoonCacheOpUitMasterDatabase(Integer id);

    /**
     * Haalt de administratieve handelingen op uit de master database.
     *
     * @param persoonId ID van de persoon om de handelingen voor op te halen
     * @return een lijst van administratieveHandelingen, of een lege lijst
     */
    List<AdministratieveHandelingModel> haalHandelingenOp(Integer persoonId);

    /**
     * Haalt de administratieve handelingen op uit de master database.
     *
     * @param persoonId ID van de persoon om de handelingen voor op te halen
     * @return een lijst van administratieveHandelingenHisVolledig, of een lege lijst
     */
    List<AdministratieveHandelingHisVolledigImpl> haalVerantwoordingOp(Integer persoonId);
}

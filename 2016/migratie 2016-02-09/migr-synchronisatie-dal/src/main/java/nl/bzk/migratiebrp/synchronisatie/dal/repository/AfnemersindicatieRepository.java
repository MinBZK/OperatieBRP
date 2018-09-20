/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.List;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

/**
 * CRUD-functionaliteit voor PersoonAfnemerindicatie-entity.
 */
public interface AfnemersindicatieRepository {

    /**
     * Zoek afnemerindicaties op o.b.v. de persoon.
     * 
     * @param persoon
     *            de Persoon
     * @return afnemersindicaties
     */
    List<PersoonAfnemerindicatie> findByPersoon(final Persoon persoon);

    /**
     * Slaat de meegegeven afnemerindicatie op de database.
     * 
     * @param afnemerindicatie
     *            de afnemerindicatie entiteit die moet worden opgeslagen in de database
     * @return de afnemerindicatie entiteit die opgeslagen is in de database
     */
    PersoonAfnemerindicatie save(PersoonAfnemerindicatie afnemerindicatie);
}

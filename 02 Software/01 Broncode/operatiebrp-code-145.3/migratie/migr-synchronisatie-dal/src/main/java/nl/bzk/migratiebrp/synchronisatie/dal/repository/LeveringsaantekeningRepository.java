/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;

/**
 * CRUD-functionaliteit voor Leveringsaantekening-entity.
 */
public interface LeveringsaantekeningRepository {

    /**
     * Slaat de meegegeven leveringsaantekening op in de database.
     * @param leveringsaantekening de leveringsaantekening entiteit die moet worden opgeslagen in de database
     * @return de leveringsaantekening entiteit die opgeslagen is in de database
     */
    Leveringsaantekening save(Leveringsaantekening leveringsaantekening);

    /**
     * Haal de leveringsaantekening op aan de hand van de id.
     * @param id het id van de leveringsaantekening
     * @return leveringsaantekening
     */
    Leveringsaantekening find(Long id);
}

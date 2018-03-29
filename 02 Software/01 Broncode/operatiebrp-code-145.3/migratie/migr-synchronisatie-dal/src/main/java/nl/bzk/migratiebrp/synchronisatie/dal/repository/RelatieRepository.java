/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;

/**
 * CRUD functionaliteit voor Relatie entities.
 */
public interface RelatieRepository {

    /**
     * Verwijderd de meegegeven Relatie entiteit. Betrokkenheid entities die zijn geassocieerd met deze Relatie
     * entiteit.
     * <p/>
     * Het is mogelijk om niet-gemanagde entities mee te geven. Wanneer de id van een entity null is worden wel de
     * associaties verwijderd maar wordt er geen echte delete uitgevoerd.
     * @param relatie de te verwijderen Relatie entiteit
     */
    void removeRelatie(Relatie relatie);
}

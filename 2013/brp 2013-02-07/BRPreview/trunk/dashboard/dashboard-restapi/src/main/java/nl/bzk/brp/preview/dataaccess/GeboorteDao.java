/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.List;

import nl.bzk.brp.preview.model.Persoon;

/**
 * Interface voor de DAO voor toegang tot geboorte data (BRP).
 */
public interface GeboorteDao {

    /**
     * Haal op geboortenieuw geborene.
     *
     * @param handelingId de handeling id
     * @return de persoon
     */
    Persoon haalOpGeboorteNieuwGeborene(Long handelingId);

    /**
     * Haalt voor een geboorte de ouders op.
     *
     * @param handelingId het handeling id.
     * @return de ouders.
     */
    List<Persoon> haalOpGeboorteOuders(Long handelingId);

}

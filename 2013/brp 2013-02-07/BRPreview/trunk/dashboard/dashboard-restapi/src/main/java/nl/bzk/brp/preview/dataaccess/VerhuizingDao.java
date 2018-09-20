/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Persoon;

/**
 * Interface voor de DAO voor toegang tot verhuizing data (BRP).
 */
public interface VerhuizingDao {

    /**
     * Haalt voor een verhuizing de persoon op.
     *
     * @param handelingId de handeling id
     * @return de persoon
     */
    Persoon haalOpPersoon(Long handelingId);

    /**
     * Haalt voor een verhuizing het oude adres op.
     *
     * @param handelingId de handeling id
     * @return de adres
     */
    Adres haalOpOudAdres(Long handelingId);

    /**
     * Haal op verhuizing nieuw adres.
     *
     * @param handelingId de handeling id
     * @return de adres
     */
    Adres haalOpNieuwAdres(Long handelingId);

}

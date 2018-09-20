/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.List;

import nl.bzk.brp.preview.model.Adres;
import nl.bzk.brp.preview.model.Persoon;

/**
 * Interface voor de correctie adres DAO voor toegang tot administratieve handeling data (BRP).
 */
public interface CorrectieAdresDao {

    /**
     * Haalt voor een correctie adres de persoon op.
     *
     * @param handelingId de handeling id
     * @return de persoon
     */
    Persoon haalOpPersoon(Long handelingId);

    /**
     * Haalt voor een correctie adres de adressen op.
     *
     * @param handelingId de handeling id
     * @return de adressen
     */
    List<Adres> haalOpAdressen(Long handelingId);

}

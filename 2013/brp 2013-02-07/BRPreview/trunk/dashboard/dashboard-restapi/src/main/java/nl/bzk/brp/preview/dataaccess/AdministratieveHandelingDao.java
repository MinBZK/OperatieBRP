/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.List;

import nl.bzk.brp.preview.model.AdministratieveHandeling;
import nl.bzk.brp.preview.model.Melding;

/**
 * Interface voor de DAO voor toegang tot administratieve handeling data (BRP).
 */
public interface AdministratieveHandelingDao {

    /**
     * Haalt een administratieve handeling op.
     *
     * @param id de id
     * @return de administratieve handeling
     */
    AdministratieveHandeling haalOp(Long id);

    /**
     * Haalt de meldingen op voor een administratieve handeling.
     *
     * @param id het id van de administratieve handeling.
     * @return Een lijst van meldingen als die er zijn.
     */
    List<Melding> haalMeldingenOp(Long id);

}

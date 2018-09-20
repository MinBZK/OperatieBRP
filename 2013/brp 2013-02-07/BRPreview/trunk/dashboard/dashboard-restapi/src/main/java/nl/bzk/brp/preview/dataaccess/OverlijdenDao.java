/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import nl.bzk.brp.preview.model.Overlijden;

/**
 * Interface voor de DAO voor toegang tot overlijden data (BRP).
 */
public interface OverlijdenDao {

    /**
     * Haal op geboortenieuw geborene.
     *
     * @param handelingId de handeling id
     * @return het Overlijden object.
     */
    Overlijden haalOpOverlijden(Long handelingId);

}

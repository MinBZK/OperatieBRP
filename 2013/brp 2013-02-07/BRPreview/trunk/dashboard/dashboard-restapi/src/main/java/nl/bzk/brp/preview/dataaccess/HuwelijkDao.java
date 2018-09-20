/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.dataaccess;

import java.util.List;

import nl.bzk.brp.preview.model.HuwelijkDatumAanvangEnPlaats;
import nl.bzk.brp.preview.model.Persoon;

/**
 * Interface voor de DAO voor toegang tot huwelijk data (BRP).
 */
public interface HuwelijkDao {

    /**
     * Haal voor een huwelijk de betrokken personen op.
     *
     * @param handelingId de handeling id
     * @return de lijst van personen
     */
    List<Persoon> haalOpHuwelijkPersonen(Long handelingId);

    /**
     * Haal voor een huwelijk de aanvangdatum en de plaats op.
     *
     * @param handelingId de handeling id
     * @return De aanvang datum en plaats in een wrapper object.
     */
    HuwelijkDatumAanvangEnPlaats haalOpHuwelijkDatumAanvangEnPlaats(final Long handelingId);

}

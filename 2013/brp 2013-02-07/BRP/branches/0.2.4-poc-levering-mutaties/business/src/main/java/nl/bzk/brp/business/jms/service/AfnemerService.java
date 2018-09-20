/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.service;

import java.util.List;

/**
 *
 */
public interface AfnemerService {

    /**
     * Geeft een lijst van {@link nl.bzk.brp.model.objecttype.operationeel.statisch.Partij} ID's die
     * interesse hebben in de personen die geraakt zijn door de Administratieve Handeling geidentificeerd door
     * <code>adminstratieveHandelingId</code>.
     *
     * @param adminstratieveHandelingId
     * @return
     */
    List<Short> getGeinteresseerdeAfnemers(Long adminstratieveHandelingId);
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.verwerking.service;

import nl.bzk.brp.model.internbericht.ProtocolleringOpdracht;

/**
 * De interface voor de service die de protocollering verwerkt.
 */
public interface ProtocolleringVerwerkingService {

    /**
     * Sla de protocollering gegevens op in de database.
     *
     * @param protocolleringOpdracht de gegevens zoals die van de queue zijn verkregen.
     */
    void slaProtocolleringOp(final ProtocolleringOpdracht protocolleringOpdracht);

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.repository;

import java.util.List;
import nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity.Protocollering;

/**
 * Repository voor protocolleringen.
 */
public interface ProtocolleringRepository {

    /**
     * Slaat de meegegeven protocollering op in de database.
     * @param protocollering de Protocollering entiteit die moet worden opgeslagen in de database
     * @return de Protocollering entiteit die opgeslagen is in de database
     */
    Protocollering save(Protocollering protocollering);

    /**
     * Vraagt de protocollering op bij een anummer.
     * @param aNummer waarvoor de protocollering opgehaald moet worden.
     * @return Lijst met gevonden protocolleringen.
     */
    List<Protocollering> findProtocolleringVoorANummer(String aNummer);

}

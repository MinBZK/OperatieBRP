/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;

/**
 * CRUD-functionaliteit voor PartijRol-entity.
 */
public interface PartijRolRepository {

    /**
     * Haalt alle partijrollen op voor een partij.
     * @param partij De partij waarvoor de partijrollen worden opgehaald
     * @return De lijst met gevonden partijrollen
     */
    List<PartijRol> getPartijRollenByPartij(Partij partij);

    /**
     * Haalt de partij rol van een partij op.
     * @param partij De partij waarvoor de partijrol wordt opgehaald.
     * @param rol de rol
     * @return De gevonden partijrol indien gevonden, anders null.
     */
    PartijRol getPartijRolByPartij(Partij partij, Rol rol);


    /**
     * @param partijRol De partijRol die opgeslagen moet worden.
     * @return De opgeslagen partijRol.
     */
    PartijRol savePartijRol(PartijRol partijRol);
}

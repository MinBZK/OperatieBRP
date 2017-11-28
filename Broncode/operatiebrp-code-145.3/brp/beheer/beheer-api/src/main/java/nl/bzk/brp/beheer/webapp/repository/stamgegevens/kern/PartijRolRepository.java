/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;

/**
 * PartijRol repository.
 */
@Master
public interface PartijRolRepository extends ReadWriteRepository<PartijRol, Integer> {

    /**
     * Vindt de partij rol op basis van de partij en de rol.
     *
     * @param partij
     *            De partij
     * @param rolId
     *            Het id van de rol
     * @return De gevonden partij rol of null indien deze niet gevonden kan worden.
     */
    PartijRol findByPartijAndRolId(Partij partij, short rolId);
}

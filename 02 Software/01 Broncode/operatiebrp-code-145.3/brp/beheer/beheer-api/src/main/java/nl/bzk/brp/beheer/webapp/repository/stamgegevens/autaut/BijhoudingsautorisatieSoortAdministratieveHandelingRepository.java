/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhoudingsautorisatieSoortAdministratieveHandeling;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Bijhoudingsautorisatie soort administratievehandeling repository.
 */
@Master
@Transactional(propagation = Propagation.MANDATORY)
public interface BijhoudingsautorisatieSoortAdministratieveHandelingRepository
        extends ReadWriteRepository<BijhoudingsautorisatieSoortAdministratieveHandeling, Integer> {
    /**
     * Zoekt de bijhoudingsautorisatieSoortAdministratieveHandeling op basis van de Bijhoudingsautorisatie.
     * @param bijhoudingsautorisatie De bijhoudingsautorisatie waarop wordt gezocht
     * @param soortAdministratievehandelingId Het id van de soort administratieve handeling
     * @return De gevonden bijhoudingsautorisatieSoortAdministratieveHandeling of null indien deze niet gevonden wordt
     */
    BijhoudingsautorisatieSoortAdministratieveHandeling findByBijhoudingsautorisatieAndSoortAdministratievehandelingId(
            Bijhoudingsautorisatie bijhoudingsautorisatie,
            Integer soortAdministratievehandelingId);

    /**
     * Zoekt de bijhoudingsautorisatieSoortAdministratieveHandeling op basis van de Bijhoudingsautorisatie.
     * @param bijhoudingsautorisatie De bijhoudingsautorisatie waarop wordt gezocht
     * @return De lijst van gevonden bijhoudingsautorisatieSoortAdministratieveHandelingen of null indien deze niet gevonden wordt
     */
    List<BijhoudingsautorisatieSoortAdministratieveHandeling> findByBijhoudingsautorisatie(Bijhoudingsautorisatie bijhoudingsautorisatie);
}

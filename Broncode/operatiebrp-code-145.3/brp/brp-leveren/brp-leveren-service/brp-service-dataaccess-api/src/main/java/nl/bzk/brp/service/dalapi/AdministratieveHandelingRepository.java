/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.dalapi;

import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;


/**
 * Repository voor de {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling} class.
 */
public interface AdministratieveHandelingRepository {

    /**
     * Haalt de acties op op basis van het administratieve handeling id.
     * @param administratieveHandelingId Het id van de handeling
     * @return De acties voor de administratievehandeling
     */
    List<BRPActie> haalAdministratieveHandelingOp(Long administratieveHandelingId);

    /**
     * Markeert de administratieve handeling als verwerkt, zodat deze niet nogmaals verwerkt zal worden.
     * @param administratieveHandelingId Het id van de administratieve handeling.
     * @return update count
     */
    int markeerAdministratieveHandelingAlsVerwerkt(Long administratieveHandelingId);

    /**
     * @param administratieveHandelingId administratieveHandelingId
     * @return update count
     */
    int markeerAdministratieveHandelingAlsFout(Long administratieveHandelingId);

    /**
     * @return handelingen voor publicatie.
     */
    List<TeLeverenHandelingDTO> geefHandelingenVoorAdmhndPublicatie();

    /**
     * @param administatieveHandelingIds administatieveHandelingIds
     * @return aantal records geupdate
     */
    int zetHandelingenStatusInLevering(Set<Long> administatieveHandelingIds);
}

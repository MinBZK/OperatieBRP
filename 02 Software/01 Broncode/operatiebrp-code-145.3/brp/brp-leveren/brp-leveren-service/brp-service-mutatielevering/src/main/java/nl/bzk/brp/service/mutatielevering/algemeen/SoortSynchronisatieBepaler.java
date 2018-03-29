/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.Populatie;

/**
 * Bepaalt de soort synchronisatie op basis van de populatie, categorie dienst en de soort administratieve handeling.
 */
@FunctionalInterface
public interface SoortSynchronisatieBepaler {

    /**
     * Bepaalt de soort synchronisatie op basis van de populatie, categorie dienst en de soort administratieve handeling.
     * @param populatie de populatie
     * @param soortDienst de categorie dienst
     * @param soortAdmHandeling de soort adm handeling
     * @return de soort synchronisatie
     */
    SoortSynchronisatie bepaalSoortSynchronisatie(
            final Populatie populatie, final SoortDienst soortDienst, final SoortAdministratieveHandeling
            soortAdmHandeling);
}

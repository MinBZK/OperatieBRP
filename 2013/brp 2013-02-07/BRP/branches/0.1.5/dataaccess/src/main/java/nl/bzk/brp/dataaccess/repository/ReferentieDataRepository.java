/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de Referentie data class.
 */
@Repository
public interface ReferentieDataRepository {

    /**
     * Zoek een woonplaats met behulp van de woonplaatscode.
     *
     * @param code woonplaatscode
     * @return de woonplaats met de gegeven code
     */
    Plaats findWoonplaatsOpCode(final String code);

    /**
     * Zoek een gemeente met behulp van de gemeentecode.
     *
     * @param code gemeentecode
     * @return de gemeente met de gegeven code
     */
    Partij vindGemeenteOpCode(final String code);

    /**
     * Zoek een land met behulp van de landcode.
     *
     * @param code landcode
     * @return het land met de gegeven code
     */
    Land vindLandOpCode(final String code);

    /**
     * Zoek een nationaliteit op basis van een nationaliteit code.
     * @param code De code waarop gezocht wordt.
     * @return De nationaliteit behorende bij code.
     */
    Nationaliteit vindNationaliteitOpCode(final String code);
}

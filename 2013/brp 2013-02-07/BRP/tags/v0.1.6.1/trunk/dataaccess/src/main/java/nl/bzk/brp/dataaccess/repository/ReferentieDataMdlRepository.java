/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.attribuuttype.GemeenteCode;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.objecttype.statisch.AangeverAdreshouding;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;
import nl.bzk.brp.model.objecttype.statisch.RedenWijzigingAdres;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de Referentie data class.
 */
@Repository
public interface ReferentieDataMdlRepository {

    /**
     * Zoek een woonplaats met behulp van de woonplaatscode.
     *
     * @param code woonplaatscode
     * @return de woonplaats met de gegeven code
     */
    Plaats vindWoonplaatsOpCode(final PlaatsCode code);

    /**
     * Zoek een gemeente met behulp van de gemeentecode.
     *
     * @param code gemeentecode
     * @return de gemeente met de gegeven code
     */
    Partij vindGemeenteOpCode(final GemeenteCode code);

    /**
     * Zoek een land met behulp van de landcode.
     *
     * @param code landcode
     * @return het land met de gegeven code
     */
    Land vindLandOpCode(final LandCode code);

    /**
     * Zoek een nationaliteit op basis van een nationaliteit code.
     * @param code De code waarop gezocht wordt.
     * @return De nationaliteit behorende bij code.
     */
    Nationaliteit vindNationaliteitOpCode(final NationaliteitCode code);

    /**
     * Zoek een 'reden wijziging adres'  op basis van de code.
     * @param code De code waarop gezocht wordt.
     * @return De 'reden wijziging adres' behorende bij code.
     */
    RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode code);

    /**
     * Zoek een 'aangever adreshouding'  op basis van de code.
     * @param code De code waarop gezocht wordt.
     * @return De 'aangever adreshouding' behorende bij code.
     */
    AangeverAdreshouding vindAangeverAdreshoudingOpCode(final AangeverAdreshoudingCode code);


}

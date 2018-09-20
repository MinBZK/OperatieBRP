/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;

/**
 * Interface voor standaar groep relatie.
 */
public interface RelatieStandaardGroepBasis extends Groep {

    /**
     * Retourneert datum aanvang relatie.
     * @return Datum aanvang.
     */
    Datum getDatumAanvang();

    /**
     * Retourneert datum einde relatie.
     * @return Datum einde.
     */
    Datum getDatumEinde();

    /**
     * Retourneert land aanvang relatie.
     * @return Land aanvang.
     */
    Land getLandAanvang();

    /**
     * Retourneert land einde relatie.
     * @return Land einde.
     */
    Land getLandEinde();

    /**
     * Retourneert gemeente aanvang relatie.
     * @return Gemeente aanvang.
     */
    Partij getGemeenteAanvang();

    /**
     * Retourneert gemeente einde relatie.
     * @return Gemeente einde.
     */
    Partij getGemeenteEinde();

    /**
     * Retourneert reden beeindinging relatie.
     * @return Reden beeindiging.
     */
    RedenBeeindigingRelatie getRedenBeeindigingRelatie();

    /**
     * Retourneert omschrijving locatie aanvang.
     * @return Omschrijving locatie.
     */
    OmschrijvingEnumeratiewaarde getOmschrijvingLocatieAanvang();

    /**
     * Retourneert omschrijving einde relatie.
     * @return Omschrijving einde.
     */
    OmschrijvingEnumeratiewaarde getOmschrijvingLocatieEinde();

    /**
     * Retourneert plaats aanvang relatie.
     * @return Plaats aanvang.
     */
    Plaats getWoonPlaatsAanvang();

    /**
     * Retourneert plaats einde relatie.
     * @return Plaats einde.
     */
    Plaats getWoonPlaatsEinde();

    /**
     * Retourneert buitenlandse plaats aanvang relatie.
     * @return Buitenlandse plaats aanvang.
     */
    BuitenlandsePlaats getBuitenlandsePlaatsAanvang();

    /**
     * Retourneert buitenlandse plaats einde relatie.
     * @return Buitenlandse plaats einde.
     */
    BuitenlandsePlaats getBuitenlandsePlaatsEinde();

    /**
     * Retourneert buitenlandse regio aanvang relatie.
     * @return Buitenlandse regio.
     */
    BuitenlandseRegio getBuitenlandseRegioAanvang();

    /**
     * Retourneert buitenlandse regio einde relatie.
     * @return Buitenlandse regio.
     */
    BuitenlandseRegio getBuitenlandseRegioEinde();
}

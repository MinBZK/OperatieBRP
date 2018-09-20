/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaats;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegio;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.basis.Groep;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * g��n sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y ;-0
 * RvdP 9 jan 2012.
 *
 *
 *
 */
public interface PersoonOverlijdenGroepBasis extends Groep {

    /**
     * Retourneert Datum overlijden van Overlijden.
     *
     * @return Datum overlijden.
     */
    Datum getDatumOverlijden();

    /**
     * Retourneert Gemeente overlijden van Overlijden.
     *
     * @return Gemeente overlijden.
     */
    Partij getGemeenteOverlijden();

    /**
     * Retourneert Woonplaats overlijden van Overlijden.
     *
     * @return Woonplaats overlijden.
     */
    Plaats getWoonplaatsOverlijden();

    /**
     * Retourneert Buitenlandse plaats overlijden van Overlijden.
     *
     * @return Buitenlandse plaats overlijden.
     */
    BuitenlandsePlaats getBuitenlandsePlaatsOverlijden();

    /**
     * Retourneert Buitenlandse regio overlijden van Overlijden.
     *
     * @return Buitenlandse regio overlijden.
     */
    BuitenlandseRegio getBuitenlandseRegioOverlijden();

    /**
     * Retourneert Omschrijving locatie overlijden van Overlijden.
     *
     * @return Omschrijving locatie overlijden.
     */
    LocatieOmschrijving getOmschrijvingLocatieOverlijden();

    /**
     * Retourneert Land overlijden van Overlijden.
     *
     * @return Land overlijden.
     */
    Land getLandOverlijden();

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * géén sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonOverlijdenGroepBasis extends Groep {

    /**
     * Retourneert Datum overlijden van Overlijden.
     *
     * @return Datum overlijden.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumOverlijden();

    /**
     * Retourneert Gemeente overlijden van Overlijden.
     *
     * @return Gemeente overlijden.
     */
    GemeenteAttribuut getGemeenteOverlijden();

    /**
     * Retourneert Woonplaatsnaam overlijden van Overlijden.
     *
     * @return Woonplaatsnaam overlijden.
     */
    NaamEnumeratiewaardeAttribuut getWoonplaatsnaamOverlijden();

    /**
     * Retourneert Buitenlandse plaats overlijden van Overlijden.
     *
     * @return Buitenlandse plaats overlijden.
     */
    BuitenlandsePlaatsAttribuut getBuitenlandsePlaatsOverlijden();

    /**
     * Retourneert Buitenlandse regio overlijden van Overlijden.
     *
     * @return Buitenlandse regio overlijden.
     */
    BuitenlandseRegioAttribuut getBuitenlandseRegioOverlijden();

    /**
     * Retourneert Omschrijving locatie overlijden van Overlijden.
     *
     * @return Omschrijving locatie overlijden.
     */
    LocatieomschrijvingAttribuut getOmschrijvingLocatieOverlijden();

    /**
     * Retourneert Land/gebied overlijden van Overlijden.
     *
     * @return Land/gebied overlijden.
     */
    LandGebiedAttribuut getLandGebiedOverlijden();

}

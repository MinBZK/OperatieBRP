/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.logisch.basis;


import nl.bzk.copy.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.copy.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.copy.model.basis.Groep;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Land;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Plaats;

/**
 * .
 */

public interface PersoonOverlijdenGroepBasis extends Groep {
    /**
     * .
     *
     * @return .
     */
    Datum getDatumOverlijden();

    /**
     * .
     *
     * @return .
     */
    Partij getOverlijdenGemeente();

    /**
     * .
     *
     * @return .
     */
    Plaats getWoonplaatsOverlijden();

    /**
     * .
     *
     * @return .
     */
    BuitenlandsePlaats getBuitenlandsePlaatsOverlijden();

    /**
     * .
     *
     * @return .
     */
    BuitenlandseRegio getBuitenlandseRegioOverlijden();

    /**
     * .
     *
     * @return .
     */
    Land getLandOverlijden();

    /**
     * .
     *
     * @return .
     */
    LocatieOmschrijving getOmschrijvingLocatieOverlijden();

}

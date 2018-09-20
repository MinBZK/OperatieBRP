/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.interfaces.gen;

import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.statisch.Land;
import nl.bzk.brp.model.objecttype.statisch.Partij;
import nl.bzk.brp.model.objecttype.statisch.Plaats;

/**
 * .
 */

public interface PersoonGeboorteGroepBasis extends Groep {
    /**
     * .
     * @return .
     */
    Datum                   getDatumGeboorte();
    /**
     * .
     * @return .
     */
    Partij                  getGemeenteGeboorte();
    /**
     * .
     * @return .
     */
    Plaats                  getWoonplaatsGeboorte();
    /**
     * .
     * @return .
     */
    BuitenlandsePlaats      getBuitenlandseGeboortePlaats();
    /**
     * .
     * @return .
     */
    BuitenlandseRegio       getBuitenlandseRegioGeboorte();
    /**
     * .
     * @return .
     */
    Land                    getLandGeboorte();
    /**
     * .
     * @return .
     */
    Omschrijving            getOmschrijvingGeboorteLocatie();

    // technisch
    // AttribuutType<Integer>  getStatus();
}

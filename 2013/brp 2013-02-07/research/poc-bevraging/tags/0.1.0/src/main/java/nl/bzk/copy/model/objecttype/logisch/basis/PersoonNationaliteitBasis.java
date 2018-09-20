/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.logisch.basis;

import nl.bzk.copy.model.basis.ObjectType;
import nl.bzk.copy.model.groep.logisch.PersoonNationaliteitStandaardGroep;
import nl.bzk.copy.model.objecttype.logisch.Persoon;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Nationaliteit;

/**
 * Interface voor objecttype Persoon Nationaliteit.
 */
public interface PersoonNationaliteitBasis extends ObjectType {

    /**
     * Retourneert de persoon waar de nationaliteit van is.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert de nationaliteit.
     *
     * @return Nationaliteit.
     */
    Nationaliteit getNationaliteit();

    /**
     * Standaard groep van objecttype Persoon nationaliteit.
     *
     * @return Standaard groep.
     */
    PersoonNationaliteitStandaardGroep getGegevens();
}

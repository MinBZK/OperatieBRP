/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.interfaces.gen;

import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.objecttype.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.statisch.RedenVerliesNLNationaliteit;

/**
 * De standaard groep van object type persoonnationaliteit.
 */
public interface PersoonNationaliteitStandaardGroepBasis extends Groep {

    /**
     * Retourneert de reden verkrijging van de persoon nationaliteit.
     * @return Reden verkrijging nationaliteit.
     */
    RedenVerkrijgingNLNationaliteit getRedenVerkregenNlNationaliteit();

    /**
     * Retourneert de reden verlies van de persoon nationaliteit.
     * @return Reden verlies nationaliteit.
     */
    RedenVerliesNLNationaliteit getRedenVerliesNlNationaliteit();

}

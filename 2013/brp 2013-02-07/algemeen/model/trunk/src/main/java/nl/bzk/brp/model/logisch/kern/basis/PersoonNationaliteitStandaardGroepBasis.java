/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.basis.Groep;


/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast
 * een formele historie ('wat stond geregistreerd') is dus ook materi�le historie denkbaar ('over welke periode
 * beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, ��k omdat dit van oudsher gebeurde vanuit de
 * GBA.
 * RvdP 17 jan 2012.
 *
 *
 *
 */
public interface PersoonNationaliteitStandaardGroepBasis extends Groep {

    /**
     * Retourneert Reden verkrijging van Standaard.
     *
     * @return Reden verkrijging.
     */
    RedenVerkrijgingNLNationaliteit getRedenVerkrijging();

    /**
     * Retourneert Reden verlies van Standaard.
     *
     * @return Reden verlies.
     */
    RedenVerliesNLNationaliteit getRedenVerlies();

}

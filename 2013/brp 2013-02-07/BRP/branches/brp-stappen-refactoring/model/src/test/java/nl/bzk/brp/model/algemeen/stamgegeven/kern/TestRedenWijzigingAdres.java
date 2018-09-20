/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;

/** RedenWijzigingAdres tbv unittest. */
public class TestRedenWijzigingAdres extends RedenWijzigingAdres {
    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenWijzigingAdres.
     * @param naam naam van RedenWijzigingAdres.
     */
    public TestRedenWijzigingAdres(final RedenWijzigingAdresCode code, final NaamEnumeratiewaarde naam) {
        super(code, naam);
    }
}

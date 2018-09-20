/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;

/** Partij ten behoeve van unittest. */
public class TestPartij extends Partij {
    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van Partij.
     * @param soort soort van Partij.
     * @param code code van Partij.
     * @param datumEinde datumEinde van Partij.
     * @param datumAanvang datumAanvang van Partij.
     * @param sector sector van Partij.
     * @param voortzettendeGemeente voortzettendeGemeente van Partij.
     * @param onderdeelVan onderdeelVan van Partij.
     * @param gemeenteStatusHis gemeenteStatusHis van Partij.
     * @param partijStatusHis partijStatusHis van Partij.
     */
    public TestPartij(final NaamEnumeratiewaarde naam, final SoortPartij soort, final GemeenteCode code,
        final Datum datumEinde, final Datum datumAanvang, final Sector sector, final Partij voortzettendeGemeente,
        final Partij onderdeelVan, final StatusHistorie gemeenteStatusHis, final StatusHistorie partijStatusHis)
    {
        super(naam, soort, code, datumEinde, datumAanvang, sector, voortzettendeGemeente, onderdeelVan,
            gemeenteStatusHis, partijStatusHis);
    }
}

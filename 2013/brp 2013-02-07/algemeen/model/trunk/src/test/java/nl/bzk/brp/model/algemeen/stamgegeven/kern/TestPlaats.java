/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;


public class TestPlaats extends Plaats {

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Plaats.
     * @param naam naam van Plaats.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Plaats.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Plaats.
     */
    public TestPlaats(final Woonplaatscode code, final LangeNaamEnumeratiewaarde naam,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }
}

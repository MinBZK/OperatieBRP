/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;

/** Nationaliteit ten behoeve van unittest. */
public class TestNationaliteit extends Nationaliteit {

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Nationaliteit.
     * @param naam naam van Nationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Nationaliteit.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Nationaliteit.
     */
    public TestNationaliteit(final Nationaliteitcode code, final NaamEnumeratiewaarde naam,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }
}

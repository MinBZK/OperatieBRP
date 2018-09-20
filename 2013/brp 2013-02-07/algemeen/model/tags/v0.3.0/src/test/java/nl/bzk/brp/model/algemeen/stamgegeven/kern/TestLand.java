/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;

/** Land ten behoeve van unittest. */
public class TestLand extends Land {

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Land.
     * @param naam naam van Land.
     * @param iSO31661Alpha2 iSO31661Alpha2 van Land.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Land.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Land.
     */
    public TestLand(final Landcode code, final NaamEnumeratiewaarde naam, final ISO31661Alpha2 iSO31661Alpha2,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        super(code, naam, iSO31661Alpha2, datumAanvangGeldigheid, datumEindeGeldigheid);
    }
}

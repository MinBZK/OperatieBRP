/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverAdreshoudingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;


/** AangeverAdreshouding tbv unittest. */
public class TestAangeverAdreshouding extends AangeverAdreshouding {

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AangeverAdreshouding.
     * @param naam naam van AangeverAdreshouding.
     * @param omschrijving omschrijving van AangeverAdreshouding.
     */
    public TestAangeverAdreshouding(final AangeverAdreshoudingCode code, final NaamEnumeratiewaarde naam,
        final OmschrijvingEnumeratiewaarde omschrijving)
    {
        super(code, naam, omschrijving);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;


/** AdellijkeTitel ten behoeve van unnitest. */
public class TestAdellijkeTitel extends AdellijkeTitel {

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AdellijkeTitel.
     * @param naamMannelijk naamMannelijk van AdellijkeTitel.
     * @param naamVrouwelijk naamVrouwelijk van AdellijkeTitel.
     */
    public TestAdellijkeTitel(final AdellijkeTitelCode code, final NaamEnumeratiewaarde naamMannelijk,
        final NaamEnumeratiewaarde naamVrouwelijk)
    {
        super(code, naamMannelijk, naamVrouwelijk);
    }
}

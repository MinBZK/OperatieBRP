/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonIndicatieStandaardGroepBasis extends Groep {

    /**
     * Retourneert Waarde van Standaard.
     *
     * @return Waarde.
     */
    JaAttribuut getWaarde();

    /**
     * Retourneert Migratie Reden opname nationaliteit van Standaard.
     *
     * @return Migratie Reden opname nationaliteit.
     */
    LO3RedenOpnameNationaliteitAttribuut getMigratieRedenOpnameNationaliteit();

    /**
     * Retourneert Migratie Reden beeindigen nationaliteit van Standaard.
     *
     * @return Migratie Reden beeindigen nationaliteit.
     */
    ConversieRedenBeeindigenNationaliteitAttribuut getMigratieRedenBeeindigenNationaliteit();

}

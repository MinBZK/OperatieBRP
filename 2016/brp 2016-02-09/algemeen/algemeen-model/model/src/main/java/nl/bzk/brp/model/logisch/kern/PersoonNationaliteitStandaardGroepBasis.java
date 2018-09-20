/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast
 * een formele historie ('wat stond geregistreerd') is dus ook materiële historie denkbaar ('over welke periode
 * beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, óók omdat dit van oudsher gebeurde vanuit de
 * GBA.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonNationaliteitStandaardGroepBasis extends Groep {

    /**
     * Retourneert Reden verkrijging van Standaard.
     *
     * @return Reden verkrijging.
     */
    RedenVerkrijgingNLNationaliteitAttribuut getRedenVerkrijging();

    /**
     * Retourneert Reden verlies van Standaard.
     *
     * @return Reden verlies.
     */
    RedenVerliesNLNationaliteitAttribuut getRedenVerlies();

    /**
     * Retourneert Bijhouding beeindigd? van Standaard.
     *
     * @return Bijhouding beeindigd?.
     */
    JaAttribuut getIndicatieBijhoudingBeeindigd();

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

    /**
     * Retourneert Migratie Datum einde bijhouding van Standaard.
     *
     * @return Migratie Datum einde bijhouding.
     */
    DatumEvtDeelsOnbekendAttribuut getMigratieDatumEindeBijhouding();

}

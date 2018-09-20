/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface ToegangBijhoudingsautorisatieStandaardGroepBasis extends Groep {

    /**
     * Retourneert Datum ingang van Standaard.
     *
     * @return Datum ingang.
     */
    DatumAttribuut getDatumIngang();

    /**
     * Retourneert Datum einde van Standaard.
     *
     * @return Datum einde.
     */
    DatumAttribuut getDatumEinde();

    /**
     * Retourneert Geblokkeerd? van Standaard.
     *
     * @return Geblokkeerd?.
     */
    JaAttribuut getIndicatieGeblokkeerd();

}

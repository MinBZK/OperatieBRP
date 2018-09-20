/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.basis.Groep;


/**
 *
 *
 */
public interface BerichtResultaatGroepBasis extends Groep {

    /**
     * Retourneert Verwerking van Resultaat.
     *
     * @return Verwerking.
     */
    Verwerkingsresultaat getVerwerking();

    /**
     * Retourneert Bijhouding van Resultaat.
     *
     * @return Bijhouding.
     */
    Bijhoudingsresultaat getBijhouding();

    /**
     * Retourneert Hoogste meldingsniveau van Resultaat.
     *
     * @return Hoogste meldingsniveau.
     */
    SoortMelding getHoogsteMeldingsniveau();

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.logisch.basis;

import nl.bzk.copy.model.basis.Groep;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Bijhoudingsresultaat;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verwerkingsresultaat;

/**
 * .
 */
public interface BerichtResultaatGroepBasis extends Groep {

    /**
     * Het verwerkingsresultaat.
     *
     * @return Het verwerkingsresultaat.
     */
    Verwerkingsresultaat getVerwerkingsresultaat();

    /**
     * Bijhoudingsresultaat.
     *
     * @return Bijhoudingsresultaat.
     */
    Bijhoudingsresultaat getBijhoudingsresultaat();

    /**
     * Soort melding.
     *
     * @return Soort melding.
     */
    Soortmelding getHoogsteMeldingNiveau();

}

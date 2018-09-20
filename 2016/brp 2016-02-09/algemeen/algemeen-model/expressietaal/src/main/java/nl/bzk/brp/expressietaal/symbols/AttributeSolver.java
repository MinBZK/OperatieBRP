/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Vertaalt een attribuut uit de expressietaal naar en concreet attribuut of de waarde daarvan gegeven een bepaald (root)object.
 */
public interface AttributeSolver {

    /**
     * Bepaalt de waarde van een attribuut voor een gegeven BRP-object.
     *
     * @param brpObject Het BRP-object.
     * @param attribute Het te bepalen attribuut.
     * @return Het evaluatieresultaat: de waarde of fout.
     */
    Expressie bepaalWaarde(BrpObject brpObject, ExpressieAttribuut attribute);

    /**
     * Bepaalt een attribuut voor een gegeven BRP-object.
     *
     * @param brpObject Het BRP-object.
     * @param attribute Het te bepalen attribuut.
     * @return Het evaluatieresultaat: het attribuut of NULL.
     */
    Expressie bepaalAttribuut(BrpObject brpObject, ExpressieAttribuut attribute);
}

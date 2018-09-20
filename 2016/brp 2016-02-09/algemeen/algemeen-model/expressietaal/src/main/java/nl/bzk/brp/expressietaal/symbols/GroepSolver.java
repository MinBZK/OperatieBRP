/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Vertaalt een groep uit de expressietaal naar een concrete groep of de waarde daarvan gegeven een bepaald (root)object.
 */
public interface GroepSolver {

    /**
     * Bepaalt een groep voor een gegeven BRP-object.
     *
     * @param brpObject Het BRP-object.
     * @param attribute De te bepalen groep.
     * @return Het evaluatieresultaat: de groep of NULL.
     */
    Expressie bepaalGroep(BrpObject brpObject, ExpressieGroep attribute);
}

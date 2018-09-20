/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.model.RootObject;

/**
 * Vertaalt een attribuut naar een waarde gegeven een bepaald (root)object.
 */
public interface AttributeSolver {

    /**
     * Bepaalt de waarde van een attribuut voor een gegeven (root)object.
     *
     * @param rootObject Het (root)object.
     * @param attribute  Het te bepalen attribuut.
     * @return Het evaluatieresultaat: de waarde of fouten.
     */
    EvaluatieResultaat solve(RootObject rootObject, Attributes attribute);

    /**
     * Bepaalt de waarde van een attribuut dat behoort tot een geïndiceerd attribuut (bijvoorbeeld adressen of
     * voornamen) voor een gegeven (root)object.
     *
     * @param rootObject Het (root)object.
     * @param attribute  Het te bepalen attribuut.
     * @param index      De index van het attribuut.
     * @return Het evaluatieresultaat: de waarde of fouten.
     */
    EvaluatieResultaat solve(RootObject rootObject, Attributes attribute, int index);

    /**
     * Bepaalt de maximale index van een geïndiceerd attribuut (oftewel het aantal elementen) van een object.
     *
     * @param rootObject Het (root)object.
     * @param attribute  Het attribuut waarvoor de maximale index bepaald moet worden.
     * @return Maximale index of de gevonden fouten.
     */
    EvaluatieResultaat getMaxIndex(RootObject rootObject, Attributes attribute);
}

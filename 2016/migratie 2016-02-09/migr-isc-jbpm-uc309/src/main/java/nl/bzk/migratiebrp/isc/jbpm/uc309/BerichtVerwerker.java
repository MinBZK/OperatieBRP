/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

/**
 * Interface die voor een specifiek Akte moet worden geïmplementeerd.
 * @param <T> output bericht
 * @param <U> input bericht
 */
interface BerichtVerwerker<T, U> {

    /**
     * Verwerkt het input tot een bericht.
     * @param input bericht
     * @return bericht T
     */
    T verwerkInput(U input);
}

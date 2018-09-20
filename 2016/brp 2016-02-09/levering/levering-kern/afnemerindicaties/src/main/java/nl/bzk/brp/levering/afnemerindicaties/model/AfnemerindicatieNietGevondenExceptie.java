/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.model;

/**
 * Exceptie die aangeeft dat de afnemerindicatie niet bestaat.
 */
public class AfnemerindicatieNietGevondenExceptie extends RuntimeException {

    /**
     * Standaard constructor die het bericht zet van de exceptie.
     *
     * @param message het fout bericht.
     */
    public AfnemerindicatieNietGevondenExceptie(final String message) {
        super(message);
    }

}

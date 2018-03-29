/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

/**
 * Een conversietabel converteert een LO3 object naar een BrpObject en vice versa, door gebruikt te maken van een
 * conversietabel.
 * @param <L> het LO3 Object type
 * @param <B> het BRP Object type
 */
public interface Conversietabel<L, B> {

    /**
     * Converteert de LO3 input naar BRP output.
     * @param input de LO3 input
     * @return de corresponderen BRP waarde, null als input null was.
     * @throws IllegalArgumentException als de gegeven LO3 code niet bestaat.
     */
    B converteerNaarBrp(L input);

    /**
     * Valideer een LO3 input als geldige code.
     * @param input de LO3 input
     * @return true, als de input een geldige code is
     */
    boolean valideerLo3(L input);

    /**
     * Converteert de BRP input naar LO3 output.
     * @param input de BRP input
     * @return de corresponderende LO3 waarde, null als input null was.
     * @throws IllegalArgumentException als de gegeven BRP code niet bestaat.
     */
    L converteerNaarLo3(B input);

    /**
     * Valideer een BRP input als geldige code.
     * @param input de BRP input
     * @return true, als de input een geldige code is
     */
    boolean valideerBrp(B input);

}

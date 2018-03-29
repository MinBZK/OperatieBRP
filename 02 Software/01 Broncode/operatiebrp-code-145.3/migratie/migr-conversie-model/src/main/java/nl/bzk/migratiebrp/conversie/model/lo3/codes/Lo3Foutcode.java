/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.codes;

/**
 * Lo3 Foutcodes.
 */
public enum Lo3Foutcode {
    GEEN,

    B,

    // Protocol fout (Pf03)
    F,

    G,

    H,

    I,

    N,

    O,

    P,

    R,

    U,

    X,

    Z;

    /**
     * Converteert een enum waarde naar een character (behalve voor GEEN).
     * @return character waarde van de enum waarde
     */
    public Character code() {
        return this == Lo3Foutcode.GEEN ? null : name().charAt(0);
    }
}

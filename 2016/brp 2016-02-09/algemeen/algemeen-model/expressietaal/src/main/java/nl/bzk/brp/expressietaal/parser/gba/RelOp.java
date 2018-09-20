/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.gba;

/**
 * Vergelijkingsoperatoren uit GBA-voorwaarderegels.
 */
public enum RelOp {
    /**
     * Geen vergelijkingsoperator.
     */
    NONE,
    /**
     * Vergelijkingsoperator GD1.
     */
    GD1,
    /**
     * Vergelijkingsoperator GDA.
     */
    GDA,
    /**
     * Vergelijkingsoperator KD1.
     */
    KD1,
    /**
     * Vergelijkingsoperator KDA.
     */
    KDA,
    /**
     * Vergelijkingsoperator GDOG1.
     */
    GDOG1,
    /**
     * Vergelijkingsoperator GDOGA.
     */
    GDOGA,
    /**
     * Vergelijkingsoperator KDOG1.
     */
    KDOG1,
    /**
     * Vergelijkingsoperator KDOGA.
     */
    KDOGA
}

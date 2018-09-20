/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.gba;

/**
 * Vergelijkingsoperatoren voor test op (on)gelijkheid uit GBA-voorwaarderegels.
 */
public enum TxtOp {
    /**
     * Geen vergelijkingsoperator.
     */
    NONE,
    /**
     * Vergelijkings operator GA1.
     */
    GA1,
    /**
     * Vergelijkings operator GAA.
     */
    GAA,
    /**
     * Vergelijkings operator OGA1.
     */
    OGA1,
    /**
     * Vergelijkings operator OGAA.
     */
    OGAA
}

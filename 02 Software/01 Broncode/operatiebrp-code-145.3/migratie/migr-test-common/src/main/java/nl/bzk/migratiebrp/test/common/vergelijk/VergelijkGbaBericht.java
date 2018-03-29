/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.vergelijk;

/**
 * Util om Gba berichten te vergelijken.
 */
public interface VergelijkGbaBericht {

    /**
     * Vergelijk berichten.
     * @param verwacht verwachte bericht
     * @param actueel actuele bericht
     * @return true, als het bericht vergelijkbaar is, anders false
     */
    static boolean vergelijk(final String verwacht, final String actueel) {
        return Vergelijk.vergelijk(GbaBerichtSorteerder.sorteer(verwacht), GbaBerichtSorteerder.sorteer(actueel));
    }

}

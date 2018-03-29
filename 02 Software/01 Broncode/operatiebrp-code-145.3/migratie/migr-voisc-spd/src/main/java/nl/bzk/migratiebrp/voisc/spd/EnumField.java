/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.util.Optional;

interface EnumField<T> {
    /**
     * Returns the default enum value or null
     * @return default enum value or null
     */
    T defaultEnum();

    /**
     * Returns the enum value for the raw string value
     * @param raw raw string value
     * @return enum value
     */
    Optional<T> enumForCode(String raw);
}

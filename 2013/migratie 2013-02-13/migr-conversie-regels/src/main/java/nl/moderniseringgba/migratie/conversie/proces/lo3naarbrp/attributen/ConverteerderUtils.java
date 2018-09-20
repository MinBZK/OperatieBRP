/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;

/**
 * Converteerder utilities.
 */
public final class ConverteerderUtils {

    private ConverteerderUtils() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Bepaal of deze lijst alleen inhoudelijk lege Migratiegroepen bevat.
     * 
     * @param <T>
     *            MigratieGroep type
     * @param lijst
     *            van Migratiegroepen
     * @return true als er tenminste 1 element in de lijst niet inhoudelijk leeg is.
     */
    static <T extends BrpGroepInhoud> boolean isLijstMetAlleenLegeInhoud(final List<MigratieGroep<T>> lijst) {
        assert lijst != null;

        for (final MigratieGroep<T> groep : lijst) {
            if (!groep.isInhoudelijkLeeg()) {
                return false;
            }
        }
        return true;
    }
}

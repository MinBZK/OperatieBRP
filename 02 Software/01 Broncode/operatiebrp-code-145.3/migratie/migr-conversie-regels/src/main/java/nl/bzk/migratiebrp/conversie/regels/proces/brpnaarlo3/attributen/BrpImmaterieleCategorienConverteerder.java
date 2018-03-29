/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;

/**
 * Interface voor de converteerders van immateriele categorieen binnen BRP naar LO3.
 * @param <L> {@link Lo3CategorieInhoud}
 */
@FunctionalInterface
public interface BrpImmaterieleCategorienConverteerder<L extends Lo3CategorieInhoud> {
    /**
     * Coverteerd BRPStapels.
     * @param brpStapels de te converteren stapel
     * @return Lo3Stapel
     */
    Lo3Stapel<L> converteer(BrpStapel<? extends BrpGroepInhoud>... brpStapels);
}

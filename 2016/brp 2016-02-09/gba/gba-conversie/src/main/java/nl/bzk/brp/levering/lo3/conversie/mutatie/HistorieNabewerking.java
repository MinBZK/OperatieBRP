/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;

/**
 * Historie nabewerking.
 *
 * @param <G> soort groep
 */
public interface HistorieNabewerking<G extends BrpGroepInhoud> {

    /**
     * Bewerk de gegeven historie op basis van de gegeven inhoud.
     *
     * @param brpGroep inhoud
     * @param historie historie
     * @return bewerkte historie
     */
    Lo3Historie bewerk(BrpGroep<G> brpGroep, Lo3Historie historie);
}

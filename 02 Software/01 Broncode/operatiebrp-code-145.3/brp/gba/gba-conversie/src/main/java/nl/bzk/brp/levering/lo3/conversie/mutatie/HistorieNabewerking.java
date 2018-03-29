/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;

/**
 * Historie nabewerking.
 * @param <G> soort groep
 * @param <L> soort categorie inhoud
 */
@FunctionalInterface
public interface HistorieNabewerking<L extends Lo3CategorieInhoud, G extends BrpGroepInhoud> {

    /**
     * Bewerk de gegeven historie op basis van de gegeven inhoud.
     * @param categorie oude gegevens
     * @param brpGroep inhoud
     * @param historie historie
     * @return bewerkte historie
     */
    Lo3Historie bewerk(Lo3Categorie<L> categorie, BrpGroep<G> brpGroep, Lo3Historie historie);
}

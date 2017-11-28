/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import org.springframework.stereotype.Component;

/**
 * Voor datum inschrijving geen historie doen.
 */
@Component
public final class PersoonBijhoudingVerblijfplaatsHistorieNabewerking implements HistorieNabewerking<Lo3VerblijfplaatsInhoud, BrpBijhoudingInhoud> {

    @Override
    public Lo3Historie bewerk(final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie, final BrpGroep<BrpBijhoudingInhoud> brpGroep, final Lo3Historie historie) {
        return categorie == null ? historie : categorie.getHistorie();
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import org.springframework.stereotype.Component;

/**
 * Nabewerking voor persoon/verblijfsrecht (kopieer de datum mededeling naar de ingangsdatum geldigheid).
 */

@Component
public final class PersoonVerblijfsrechtHistorieNabewerking implements HistorieNabewerking<BrpVerblijfsrechtInhoud> {

    @Override
    public Lo3Historie bewerk(final BrpGroep<BrpVerblijfsrechtInhoud> brpGroep, final Lo3Historie historie) {
        final Lo3Datum ingangsdatumGeldigheid = brpGroep.getInhoud().getDatumMededelingVerblijfsrecht().converteerNaarLo3Datum();
        return new Lo3Historie(historie.getIndicatieOnjuist(), ingangsdatumGeldigheid, historie.getDatumVanOpneming());
    }
}

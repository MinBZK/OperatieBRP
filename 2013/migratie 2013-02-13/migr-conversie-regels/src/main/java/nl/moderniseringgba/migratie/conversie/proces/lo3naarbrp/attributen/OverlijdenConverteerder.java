/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de conversie logica om de LO3 Categorie Overlijden te converteren naar BRP.
 * 
 * 
 * 
 */
@Component
@Requirement({ Requirements.CGR08_LB01, Requirements.CGR08_LB02, Requirements.CGR08_LB03 })
public class OverlijdenConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Overlijden categorie naar het BRP model en vult hiermee de migratiePersoonslijstBuilder aan.
     * 
     * @param lo3OverlijdenStapel
     *            de overlijden stapel, mag null zijn
     * @param migratiePersoonslijstBuilder
     *            de migratie builder
     */
    public final void converteer(
            final Lo3Stapel<Lo3OverlijdenInhoud> lo3OverlijdenStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        if (lo3OverlijdenStapel == null) {
            return;
        }
        final List<MigratieGroep<BrpOverlijdenInhoud>> overlijdenGroepen =
                new ArrayList<MigratieGroep<BrpOverlijdenInhoud>>();
        for (final Lo3Categorie<Lo3OverlijdenInhoud> lo3Overlijden : lo3OverlijdenStapel) {
            final BrpPlaatsLand brpPlaatsLand =
                    new Lo3PlaatsLandConversieHelper(lo3Overlijden.getInhoud().getGemeenteCode(), lo3Overlijden
                            .getInhoud().getLandCode(), converteerder).converteerNaarBrp();
            overlijdenGroepen.add(new MigratieGroep<BrpOverlijdenInhoud>(new BrpOverlijdenInhoud(converteerder
                    .converteerDatum(lo3Overlijden.getInhoud().getDatum()), brpPlaatsLand.getBrpGemeenteCode(),
                    brpPlaatsLand.getBrpPlaatsCode(), brpPlaatsLand.getBrpBuitenlandsePlaats(), brpPlaatsLand
                            .getBrpBuitenlandseRegio(), brpPlaatsLand.getBrpLandCode(), brpPlaatsLand
                            .getBrpOmschrijvingLocatie()), lo3Overlijden.getHistorie(), lo3Overlijden
                    .getDocumentatie(), lo3Overlijden.getLo3Herkomst()));
        }
        final MigratieStapel<BrpOverlijdenInhoud> migratieOverlijdenStapel =
                new MigratieStapel<BrpOverlijdenInhoud>(overlijdenGroepen);
        migratiePersoonslijstBuilder.overlijdenStapel(migratieOverlijdenStapel);
    }
}

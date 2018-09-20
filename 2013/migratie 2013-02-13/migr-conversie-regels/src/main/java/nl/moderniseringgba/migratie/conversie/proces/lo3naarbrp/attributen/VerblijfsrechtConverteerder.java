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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.springframework.stereotype.Component;

/**
 * Deze class implementeert de logica om een LO3 verblijfstitel te converteren naar een BRP verblijfsrecht.
 * 
 */
@Component
@Requirement(Requirements.CCA10)
public class VerblijfsrechtConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Verblijfstitel categorie naar het BRP model en vult hiermee de migratiePersoonslijstBuilder
     * aan.
     * 
     * @param lo3VerblijfstitelStapel
     *            de verblijfstitel stapel, mag null zijn
     * @param migratiePersoonslijstBuilder
     *            de migratie builder
     */
    public final void converteer(
            final Lo3Stapel<Lo3VerblijfstitelInhoud> lo3VerblijfstitelStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        if (lo3VerblijfstitelStapel == null) {
            return;
        }
        final List<MigratieGroep<BrpVerblijfsrechtInhoud>> verblijfsrechtGroepen =
                new ArrayList<MigratieGroep<BrpVerblijfsrechtInhoud>>();

        for (final Lo3Categorie<Lo3VerblijfstitelInhoud> lo3VerblijfstitelCategorie : lo3VerblijfstitelStapel) {
            final Lo3VerblijfstitelInhoud lo3Verblijfstitel = lo3VerblijfstitelCategorie.getInhoud();
            verblijfsrechtGroepen.add(new MigratieGroep<BrpVerblijfsrechtInhoud>(new BrpVerblijfsrechtInhoud(
                    converteerder.converteerLo3AanduidingVerblijfstitelCode(lo3Verblijfstitel
                            .getAanduidingVerblijfstitelCode()), converteerder.converteerDatum(lo3Verblijfstitel
                            .getIngangsdatumVerblijfstitel()), converteerder.converteerDatum(lo3Verblijfstitel
                            .getDatumEindeVerblijfstitel()), null), lo3VerblijfstitelCategorie.getHistorie(),
                    lo3VerblijfstitelCategorie.getDocumentatie(), lo3VerblijfstitelCategorie.getLo3Herkomst()));
        }
        final MigratieStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel =
                new MigratieStapel<BrpVerblijfsrechtInhoud>(verblijfsrechtGroepen);
        migratiePersoonslijstBuilder.verblijfsrechtStapel(verblijfsrechtStapel);
    }
}

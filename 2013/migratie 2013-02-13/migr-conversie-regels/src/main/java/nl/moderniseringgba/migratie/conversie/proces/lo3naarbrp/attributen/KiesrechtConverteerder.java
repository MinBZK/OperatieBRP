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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.springframework.stereotype.Component;

/**
 * Deze service levert de functionaliteit om de LO3 categorie inhoud Kiesrecht naar BRP inhoud te converteren.
 * 
 */
@Component
@Requirement({ Requirements.CCA13, Requirements.CCA13_LB01, Requirements.CCA13_LB02 })
public class KiesrechtConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteer kiesrecht.
     * 
     * @param kiesrechtStapel
     *            de lo3 kiesrecht stapel
     * @param lo3InschrijvingStapel
     *            de lo3 inschrijving stapel (voor datumtijdstempel)
     * @param migratiePersoonslijstBuilder
     *            migratie persoonlijst builder
     * @throws NullPointerException
     *             als migratiePersoonslijstBuilder null is
     */
    public final void converteer(
            final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel,
            final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        if (migratiePersoonslijstBuilder == null) {
            throw new NullPointerException(
                    "migratiePersoonslijstBuilder mag niet null zijn voor KiesrechtConverteerder.converteer");
        }

        if (kiesrechtStapel == null) {
            return;
        }

        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving = lo3InschrijvingStapel.getMeestRecenteElement();
        final Lo3Datum nuDatum =
                lo3Inschrijving.getInhoud().getDatumtijdstempel().converteerNaarBrpDatumTijd()
                        .converteerNaarLo3Datum();
        final Lo3Historie nuOpgenomen = new Lo3Historie(null, new Lo3Datum(0), nuDatum);

        // Deze LO3 stapel converteert naar 2 stapel in het BRP model
        final List<MigratieGroep<BrpUitsluitingNederlandsKiesrechtInhoud>> migratieUitsluitingNederlandsKiesrechtList =
                new ArrayList<MigratieGroep<BrpUitsluitingNederlandsKiesrechtInhoud>>();
        final List<MigratieGroep<BrpEuropeseVerkiezingenInhoud>> migratieEuropeseVerkiezingenList =
                new ArrayList<MigratieGroep<BrpEuropeseVerkiezingenInhoud>>();

        for (final Lo3Categorie<Lo3KiesrechtInhoud> lo3KiesrechtCategorie : kiesrechtStapel) {
            final Lo3KiesrechtInhoud lo3Kiesrecht = lo3KiesrechtCategorie.getInhoud();
            migratieUitsluitingNederlandsKiesrechtList
                    .add(new MigratieGroep<BrpUitsluitingNederlandsKiesrechtInhoud>(
                            new BrpUitsluitingNederlandsKiesrechtInhoud(converteerder
                                    .converteerLo3AanduidingUitgeslotenKiesrecht(lo3Kiesrecht
                                            .getAanduidingUitgeslotenKiesrecht()), converteerder
                                    .converteerDatum(lo3Kiesrecht.getEinddatumUitsluitingKiesrecht())), nuOpgenomen,
                            lo3KiesrechtCategorie.getDocumentatie(), lo3KiesrechtCategorie.getLo3Herkomst()));

            migratieEuropeseVerkiezingenList.add(new MigratieGroep<BrpEuropeseVerkiezingenInhoud>(
                    new BrpEuropeseVerkiezingenInhoud(converteerder
                            .converteerLo3AanduidingEuropeesKiesrecht(lo3Kiesrecht.getAanduidingEuropeesKiesrecht()),
                            converteerder.converteerDatum(lo3Kiesrecht.getDatumEuropeesKiesrecht()), converteerder
                                    .converteerDatum(lo3Kiesrecht.getEinddatumUitsluitingEuropeesKiesrecht())),
                    nuOpgenomen, lo3KiesrechtCategorie.getDocumentatie(), lo3KiesrechtCategorie.getLo3Herkomst()));
        }

        if (!ConverteerderUtils.isLijstMetAlleenLegeInhoud(migratieUitsluitingNederlandsKiesrechtList)) {
            migratiePersoonslijstBuilder
                    .uitsluitingNederlandsKiesrecht(new MigratieStapel<BrpUitsluitingNederlandsKiesrechtInhoud>(
                            migratieUitsluitingNederlandsKiesrechtList));
        }
        if (!ConverteerderUtils.isLijstMetAlleenLegeInhoud(migratieEuropeseVerkiezingenList)) {
            migratiePersoonslijstBuilder.europeseVerkiezingen(new MigratieStapel<BrpEuropeseVerkiezingenInhoud>(
                    migratieEuropeseVerkiezingenList));
        }
    }
}

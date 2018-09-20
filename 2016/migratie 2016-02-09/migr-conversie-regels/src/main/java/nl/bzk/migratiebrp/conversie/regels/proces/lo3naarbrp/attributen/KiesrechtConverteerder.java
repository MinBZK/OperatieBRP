/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Deze service levert de functionaliteit om de LO3 categorie inhoud Kiesrecht naar BRP inhoud te converteren.
 * 
 */
@Component
@Requirement({Requirements.CCA13, Requirements.CCA13_LB01, Requirements.CCA13_LB02 })
public class KiesrechtConverteerder extends AbstractConverteerder {

    /**
     * Converteer kiesrecht.
     * 
     * @param kiesrechtStapel
     *            de lo3 kiesrecht stapel
     * @param lo3InschrijvingStapel
     *            de lo3 inschrijving stapel (voor datumtijdstempel)
     * @param tussenPersoonslijstBuilder
     *            migratie persoonlijst builder
     * @throws NullPointerException
     *             als tussenPersoonslijstBuilder null is
     */
    public final void converteer(
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel,
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel,
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder)
    {
        if (tussenPersoonslijstBuilder == null) {
            throw new NullPointerException("tussenPersoonslijstBuilder mag niet null zijn voor KiesrechtConverteerder.converteer");
        }

        if (kiesrechtStapel == null) {
            return;
        }

        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving = lo3InschrijvingStapel.getLaatsteElement();
        final Lo3Datum nuDatum = BrpDatumTijd.fromLo3Datumtijdstempel(lo3Inschrijving.getInhoud().getDatumtijdstempel()).converteerNaarLo3Datum();
        final Lo3Historie nuOpgenomen = new Lo3Historie(null, Lo3Datum.NULL_DATUM, nuDatum);

        // Deze LO3 stapel converteert naar 2 stapel in het BRP model
        final List<TussenGroep<BrpUitsluitingKiesrechtInhoud>> migratieUitsluitingKiesrechtList = new ArrayList<>();
        final List<TussenGroep<BrpDeelnameEuVerkiezingenInhoud>> migratieDeelnameEuVerkiezingenList = new ArrayList<>();

        final Lo3AttribuutConverteerder converteerder = getLo3AttribuutConverteerder();

        for (final Lo3Categorie<Lo3KiesrechtInhoud> lo3KiesrechtCategorie : kiesrechtStapel) {
            final Lo3KiesrechtInhoud lo3Kiesrecht = lo3KiesrechtCategorie.getInhoud();

            final Lo3AanduidingUitgeslotenKiesrecht lo3AanduidingUitgeslotenKiesrecht = lo3Kiesrecht.getAanduidingUitgeslotenKiesrecht();
            final BrpBoolean indicatieUitsluitingKiesrecht = converteerder.converteerLo3AanduidingUitgeslotenKiesrecht(lo3AanduidingUitgeslotenKiesrecht);
            final BrpDatum voorzienEindeUitsluitingKiesrecht = converteerder.converteerDatum(lo3Kiesrecht.getEinddatumUitsluitingKiesrecht());
            final BrpUitsluitingKiesrechtInhoud uitsluitingKiesrechtInhoud =
                    new BrpUitsluitingKiesrechtInhoud(indicatieUitsluitingKiesrecht, voorzienEindeUitsluitingKiesrecht);
            migratieUitsluitingKiesrechtList.add(new TussenGroep<>(
                uitsluitingKiesrechtInhoud,
                nuOpgenomen,
                lo3KiesrechtCategorie.getDocumentatie(),
                lo3KiesrechtCategorie.getLo3Herkomst()));

            final Lo3AanduidingEuropeesKiesrecht lo3AanduidingEuropeesKiesrecht = lo3Kiesrecht.getAanduidingEuropeesKiesrecht();
            final BrpBoolean indicatieDeelnameEuVerkiezingen = converteerder.converteerLo3AanduidingEuropeesKiesrecht(lo3AanduidingEuropeesKiesrecht);
            final BrpDatum datumAanleidingAanpassingDeelnameEuVerkiezingen = converteerder.converteerDatum(lo3Kiesrecht.getDatumEuropeesKiesrecht());
            final BrpDatum voorzienEindeUitsluitingEuVerkiezingen = converteerder.converteerDatum(lo3Kiesrecht.getEinddatumUitsluitingEuropeesKiesrecht());
            final BrpDeelnameEuVerkiezingenInhoud deelnameEuVerkiezingenInhoud;
            deelnameEuVerkiezingenInhoud =
                    new BrpDeelnameEuVerkiezingenInhoud(
                        indicatieDeelnameEuVerkiezingen,
                        datumAanleidingAanpassingDeelnameEuVerkiezingen,
                        voorzienEindeUitsluitingEuVerkiezingen);
            migratieDeelnameEuVerkiezingenList.add(new TussenGroep<>(
                deelnameEuVerkiezingenInhoud,
                nuOpgenomen,
                lo3KiesrechtCategorie.getDocumentatie(),
                lo3KiesrechtCategorie.getLo3Herkomst()));
        }

        boolean kiesrechtToegevoegd = false;
        if (!getUtils().isLijstMetAlleenLegeInhoud(migratieUitsluitingKiesrechtList)) {
            tussenPersoonslijstBuilder.uitsluitingKiesrecht(new TussenStapel<>(migratieUitsluitingKiesrechtList));
            kiesrechtToegevoegd = true;
        }
        if (!getUtils().isLijstMetAlleenLegeInhoud(migratieDeelnameEuVerkiezingenList)) {
            tussenPersoonslijstBuilder.deelnameEuVerkiezingen(new TussenStapel<>(migratieDeelnameEuVerkiezingenList));
            kiesrechtToegevoegd = true;
        }

        if (!kiesrechtToegevoegd) {
            logBijzondereSituatie027(kiesrechtStapel);
        }
    }

    private void logBijzondereSituatie027(final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel) {
        for (final Lo3Categorie<Lo3KiesrechtInhoud> lo3Categorie : kiesrechtStapel) {
            Foutmelding.logMeldingFoutInfo(lo3Categorie.getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB027, null);
        }
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

/**
 * Deze class bevat de conversie logica om een LO3 Persoon en LO3 Inschrijving te converteren naar een BRP groep
 * Inschrijving.
 */
@Requirement({Requirements.CCA07,
        Requirements.CCA07_LB01,
        Requirements.CCA07_LB02,
        Requirements.CCA07_LB03,
        Requirements.CCA07_LB04,
        Requirements.CCA07_LB05,
        Requirements.CCA08_LB05})
public class InschrijvingConverteerder {

    private final Lo3AttribuutConverteerder converteerder;


    /**
     * Constructor voor deze converteerder.
     * @param converteerder een {@link Lo3AttribuutConverteerder}. Mag NIET null zijn.
     * @throws NullPointerException als de meegegeven converteerder null is
     */
    @Inject
    public InschrijvingConverteerder(final Lo3AttribuutConverteerder converteerder) {
        if (converteerder == null) {
            throw new NullPointerException("Parameter converteerder mag niet null zijn");
        }
        this.converteerder = converteerder;
    }

    /**
     * Converteert de LO3 inschrijving stapel naar een BRP inschrijving stapel. Ook gegevens van de LO3 verblijfsplaats
     * stapel zijn hierbij nodig. Als isDummyPL true is, dan worden alleen de Inschrijving- en Bijhoudingstapel
     * aangemaakt.
     * @param lo3VerblijfplaatsStapel de LO3 verblijfplaats stapel
     * @param lo3InschrijvingStapel de LO3 inschrijving stapel
     * @param isDummyPL geeft aan of de PL een dummy PL is
     * @param tussenPersoonslijstBuilder de migratie persoonslijstbuilder
     */
    public final void converteer(
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel,
            final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel,
            final boolean isDummyPL,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {

        // Lo3Historie obv datumtijdstempel
        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving = lo3InschrijvingStapel.getLaatsteElement();
        final Lo3InschrijvingInhoud inhoud = lo3Inschrijving.getInhoud();
        final Lo3Datum lo3Datumstempel = BrpDatumTijd.fromLo3Datumtijdstempel(inhoud.getDatumtijdstempel()).converteerNaarLo3Datum();
        final Lo3Historie inschrijvingFormeleHistorie = new Lo3Historie(null, null, lo3Datumstempel);

        converteerInschrijving(tussenPersoonslijstBuilder, lo3Inschrijving, inhoud, inschrijvingFormeleHistorie);
        converteerBijhouding(lo3Inschrijving, lo3VerblijfplaatsStapel, tussenPersoonslijstBuilder, inschrijvingFormeleHistorie);
        converteerPersoonAfgeleidAdministratief(tussenPersoonslijstBuilder, lo3Inschrijving, inschrijvingFormeleHistorie);

        if (!isDummyPL) {
            converteerPersoonskaart(tussenPersoonslijstBuilder, lo3Inschrijving, inhoud, inschrijvingFormeleHistorie);
            converteerVerstrekkingsbeperking(tussenPersoonslijstBuilder, lo3Inschrijving, inhoud, inschrijvingFormeleHistorie);
            converteerVerificatie(tussenPersoonslijstBuilder, lo3Inschrijving, inhoud, inschrijvingFormeleHistorie);
        }
    }

    private void converteerPersoonskaart(
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3InschrijvingInhoud inhoud,
            final Lo3Historie inschrijvingFormeleHistorie) {
        // Persoonskaart
        final BrpPartijCode gemeentePK = converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(inhoud.getGemeentePKCode());
        final BrpBoolean indicatie = converteerder.converteerLo3IndicatiePKVolledigGeconverteerdCode(inhoud.getIndicatiePKVolledigGeconverteerdCode());
        final BrpPersoonskaartInhoud pkKaartInhoud = new BrpPersoonskaartInhoud(gemeentePK, indicatie);
        final TussenGroep<BrpPersoonskaartInhoud> migratiePersoonskaart =
                new TussenGroep<>(pkKaartInhoud, inschrijvingFormeleHistorie, lo3Inschrijving.getDocumentatie(), lo3Inschrijving.getLo3Herkomst());
        if (!migratiePersoonskaart.isInhoudelijkLeeg()) {
            final List<TussenGroep<BrpPersoonskaartInhoud>> groepen = new ArrayList<>();
            groepen.add(migratiePersoonskaart);
            tussenPersoonslijstBuilder.persoonskaartStapel(new TussenStapel<>(groepen));
        }
    }

    private void converteerVerstrekkingsbeperking(
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3InschrijvingInhoud inschrijvingInhoud,
            final Lo3Historie inschrijvingFormeleHistorie) {
        // Verstrekkingsbeperking
        final BrpBoolean indicatieGeheimCode = converteerder.converteerLo3IndicatieGeheimCode(inschrijvingInhoud.getIndicatieGeheimCode());
        final BrpVerstrekkingsbeperkingIndicatieInhoud inhoud = new BrpVerstrekkingsbeperkingIndicatieInhoud(indicatieGeheimCode, null, null);
        final TussenGroep<BrpVerstrekkingsbeperkingIndicatieInhoud> migratieVerstrekkingsbeperking =
                new TussenGroep<>(inhoud, inschrijvingFormeleHistorie, lo3Inschrijving.getDocumentatie(), lo3Inschrijving.getLo3Herkomst());

        if (!migratieVerstrekkingsbeperking.isInhoudelijkLeeg()) {
            final List<TussenGroep<BrpVerstrekkingsbeperkingIndicatieInhoud>> groepen = new ArrayList<>();
            groepen.add(migratieVerstrekkingsbeperking);
            tussenPersoonslijstBuilder.verstrekkingsbeperkingIndicatieStapel(new TussenStapel<>(groepen));
        }
    }

    private void converteerPersoonAfgeleidAdministratief(
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3Historie inschrijvingFormeleHistorie) {
        // Persoon Afgeleid administratief
        final BrpPersoonAfgeleidAdministratiefInhoud afgeleidAdministratiefInhoud = new BrpPersoonAfgeleidAdministratiefInhoud();

        final List<TussenGroep<BrpPersoonAfgeleidAdministratiefInhoud>> persoonAfgeleidAdministratiefGroepen = new ArrayList<>();
        persoonAfgeleidAdministratiefGroepen.add(new TussenGroep<>(afgeleidAdministratiefInhoud, inschrijvingFormeleHistorie, lo3Inschrijving.getDocumentatie(),
                lo3Inschrijving.getLo3Herkomst()));
        final TussenStapel<BrpPersoonAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel = new TussenStapel<>(persoonAfgeleidAdministratiefGroepen);
        tussenPersoonslijstBuilder.persoonAfgeleidAdministratiefStapel(afgeleidAdministratiefStapel);
    }

    private void converteerVerificatie(
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3InschrijvingInhoud inhoud,
            final Lo3Historie inschrijvingFormeleHistorie) {
        // Verificatie
        if (!inhoud.isGroep71Leeg()) {
            final BrpPartijCode partijVerificatie;
            if (lo3Inschrijving.getDocumentatie().getRniDeelnemerCode() != null) {
                partijVerificatie = converteerder.converteerRNIDeelnemer(lo3Inschrijving.getDocumentatie().getRniDeelnemerCode());
            } else {
                partijVerificatie = BrpPartijCode.ONBEKEND;
            }
            final Lo3String omschrijvingVerificatie = inhoud.getOmschrijvingVerificatie();
            final BrpDatum datumVerificatie = converteerder.converteerDatum(inhoud.getDatumVerificatie());
            final List<TussenGroep<BrpVerificatieInhoud>> verificatieGroepen = new ArrayList<>();
            verificatieGroepen.add(
                    new TussenGroep<>(
                            new BrpVerificatieInhoud(partijVerificatie, converteerder.converteerString(omschrijvingVerificatie), datumVerificatie),
                            inschrijvingFormeleHistorie,
                            lo3Inschrijving.getDocumentatie(),
                            lo3Inschrijving.getLo3Herkomst()));
            final TussenStapel<BrpVerificatieInhoud> verificatieStapel = new TussenStapel<>(verificatieGroepen);
            tussenPersoonslijstBuilder.verificatieStapel(verificatieStapel);
        }
    }

    private void converteerInschrijving(
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3InschrijvingInhoud inhoud,
            final Lo3Historie inschrijvingFormeleHistorie) {
        // Inschrijving
        final List<TussenGroep<BrpInschrijvingInhoud>> brpInschrijvingGroepen = new ArrayList<>();
        brpInschrijvingGroepen.add(
                new TussenGroep<>(
                        new BrpInschrijvingInhoud(
                                BrpDatum.fromLo3Datum(inhoud.getDatumEersteInschrijving()),
                                converteerder.converteerLong(inhoud.getVersienummer()),
                                BrpDatumTijd.fromLo3Datumtijdstempel(inhoud.getDatumtijdstempel())),
                        inschrijvingFormeleHistorie,
                        lo3Inschrijving.getDocumentatie(),
                        lo3Inschrijving.getLo3Herkomst()));
        tussenPersoonslijstBuilder.inschrijvingStapel(new TussenStapel<>(brpInschrijvingGroepen));

    }

    @Definitie({Definities.DEF040, Definities.DEF079, Definities.DEF092, Definities.DEF093, Definities.DEF094})
    private void converteerBijhouding(
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Historie inschrijvingFormeleHistorie) {
        final Lo3InschrijvingInhoud inschrijvingInhoud = lo3Inschrijving.getInhoud();
        final Lo3VerblijfplaatsInhoud actueelVerblijfplaatsInhoud = lo3VerblijfplaatsStapel.getLo3ActueelVoorkomen().getInhoud();
        final Lo3Datum actueelDatumInschrijvingGemeente = actueelVerblijfplaatsInhoud.getDatumInschrijving();

        final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode;
        final Lo3Datum datumAanvangGeldigheidBijhouding;
        final boolean isOpgeschort = inschrijvingInhoud.isOpgeschort();

        if (isOpgeschort) {
            // DEF079
            nadereBijhoudingsaardCode = converteerder.converteerLo3RedenOpschortingBijhoudingCode(inschrijvingInhoud.getRedenOpschortingBijhoudingCode());
            datumAanvangGeldigheidBijhouding = inschrijvingInhoud.getDatumOpschortingBijhouding();
        } else {
            // DEF040
            nadereBijhoudingsaardCode = BrpNadereBijhoudingsaardCode.ACTUEEL;
            datumAanvangGeldigheidBijhouding = actueelDatumInschrijvingGemeente;
        }

        final List<TussenGroep<BrpBijhoudingInhoud>> bijhoudingGroepen = new ArrayList<>();

        // TussenGroepen voor bijhouding maken vanuit Cat08
        Lo3VerblijfplaatsInhoud verblijfplaatsInhoud =
                maakBijhoudingUitVerblijfplaats(
                        lo3VerblijfplaatsStapel,
                        actueelDatumInschrijvingGemeente,
                        nadereBijhoudingsaardCode,
                        datumAanvangGeldigheidBijhouding,
                        bijhoudingGroepen,
                        isOpgeschort);
        if (isOpgeschort) {
            // Alleen TussenGroep maken voor Cat07 als er ook een opschorting is
            if (verblijfplaatsInhoud == null) {
                verblijfplaatsInhoud = actueelVerblijfplaatsInhoud;
            }

            final BrpPartijCode bijhoudingsPartij = converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(verblijfplaatsInhoud.getGemeenteInschrijving());

            bijhoudingGroepen.add(
                    maakBrpBijhoudingGroep(
                            bijhoudingsPartij,
                            bepaalBijhoudingsaard(verblijfplaatsInhoud),
                            nadereBijhoudingsaardCode,
                            datumAanvangGeldigheidBijhouding,
                            inschrijvingFormeleHistorie,
                            lo3Inschrijving.getDocumentatie(),
                            lo3Inschrijving.getLo3Herkomst()));
        }

        tussenPersoonslijstBuilder.bijhoudingStapel(new TussenStapel<>(bijhoudingGroepen));
    }

    private Lo3VerblijfplaatsInhoud maakBijhoudingUitVerblijfplaats(
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel,
            final Lo3Datum actueelDatumInschrijvingGemeente,
            final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode,
            final Lo3Datum datumAanvangGeldigheidBijhouding,
            final List<TussenGroep<BrpBijhoudingInhoud>> bijhoudingGroepen,
            final boolean isOpgeschort) {
        Lo3VerblijfplaatsInhoud verblijfplaatsInhoud = null;

        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen : lo3VerblijfplaatsStapel.getCategorieen()) {
            final Lo3VerblijfplaatsInhoud inhoud = voorkomen.getInhoud();
            final Lo3Historie historie = voorkomen.getHistorie();
            final BrpPartijCode bijhoudingsPartijCode = converteerder.converteerLo3GemeenteCodeNaarBrpPartijCode(inhoud.getGemeenteInschrijving());
            final Lo3Datum datumInschrijvingGemeente = inhoud.getDatumInschrijving();

            final boolean isDatumInschrijvingGroterDanVorigeDatumInschrijving = verblijfplaatsInhoud == null ||
                    datumInschrijvingGemeente.compareTo(verblijfplaatsInhoud.getDatumInschrijving()) > 0;

            if (datumAanvangGeldigheidBijhouding.compareTo(actueelDatumInschrijvingGemeente) < 0
                    && !historie.isOnjuist()
                    && datumInschrijvingGemeente.compareTo(datumAanvangGeldigheidBijhouding) < 0
                    && isDatumInschrijvingGroterDanVorigeDatumInschrijving) {
                verblijfplaatsInhoud = inhoud;
            }
            bijhoudingGroepen.add(
                    maakBrpBijhoudingGroep(
                            bijhoudingsPartijCode,
                            bepaalBijhoudingsaard(voorkomen.getInhoud()),
                            bepaalTeGebruikenNadereBijhoudingsaard(isOpgeschort, voorkomen, datumAanvangGeldigheidBijhouding, nadereBijhoudingsaardCode),
                            datumInschrijvingGemeente,
                            historie,
                            voorkomen.getDocumentatie(),
                            voorkomen.getLo3Herkomst()));

        }
        return verblijfplaatsInhoud;
    }

    private BrpBijhoudingsaardCode bepaalBijhoudingsaard(final Lo3VerblijfplaatsInhoud voorkomenInhoud) {
        final BrpBijhoudingsaardCode bijhoudingsaardCode;
        switch (voorkomenInhoud.getGemeenteInschrijving().getWaarde()) {
            case "0000":
                // DEF094
                bijhoudingsaardCode = BrpBijhoudingsaardCode.ONBEKEND;
                break;
            case "1999":
                // DEF093
                bijhoudingsaardCode = BrpBijhoudingsaardCode.NIET_INGEZETENE;
                break;
            default:
                // DEF092
                bijhoudingsaardCode = BrpBijhoudingsaardCode.INGEZETENE;
        }
        return bijhoudingsaardCode;
    }

    private BrpNadereBijhoudingsaardCode bepaalTeGebruikenNadereBijhoudingsaard(
            final boolean isOpgeschort,
            final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen,
            final Lo3Datum datumAanvangGeldigheidBijhouding,
            final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode) {
        final BrpNadereBijhoudingsaardCode result;
        if ((isOpgeschort && voorkomen.getInhoud().getDatumInschrijving().compareTo(datumAanvangGeldigheidBijhouding) > 0)
                || (!isOpgeschort && voorkomen.getLo3Herkomst().isLo3ActueelVoorkomen())) {
            result = nadereBijhoudingsaardCode;
        } else {
            result = BrpNadereBijhoudingsaardCode.ONBEKEND;
        }

        return result;
    }

    /* meer dan 7 parameters. Deze 8 zijn nodig om de TussenGroep aan te maken voor Bijhouding */
    private TussenGroep<BrpBijhoudingInhoud> maakBrpBijhoudingGroep(
            final BrpPartijCode bijhoudingspartijCode,
            final BrpBijhoudingsaardCode bijhoudingsaardCode,
            final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode,
            final Lo3Datum datumAanvangGeldigheid,
            final Lo3Historie historie,
            final Lo3Documentatie lo3Documentatie,
            final Lo3Herkomst lo3Herkomst) {
        final BrpBijhoudingInhoud bijhoudingInhoud = new BrpBijhoudingInhoud(bijhoudingspartijCode, bijhoudingsaardCode, nadereBijhoudingsaardCode);

        final Lo3Historie bijhoudingHistorie = new Lo3Historie(historie.getIndicatieOnjuist(), datumAanvangGeldigheid, historie.getDatumVanOpneming());

        return new TussenGroep<>(bijhoudingInhoud, bijhoudingHistorie, lo3Documentatie, lo3Herkomst);
    }
}

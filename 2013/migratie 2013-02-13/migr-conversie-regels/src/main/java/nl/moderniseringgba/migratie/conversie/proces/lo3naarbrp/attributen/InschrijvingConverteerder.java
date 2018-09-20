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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerantwoordelijkeCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerstrekkingsbeperkingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de conversie logica om een LO3 Persoon en LO3 Inschrijving te converteren naar een BRP groep
 * Inschrijving.
 * 
 */
// CHECKSTYLE:OFF - Fan-out complexity - geaccepteerd
@Component
@Requirement({ Requirements.CCA07, Requirements.CCA07_LB01, Requirements.CCA07_LB02, Requirements.CCA07_LB03,
        Requirements.CCA07_LB04, Requirements.CCA07_LB05 })
public class InschrijvingConverteerder {
    // CHECKSTYLE:ON

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 persoon en LO3 inschrijving stapel naar een BRP inschrijving stapel. De LO3 inschrijving
     * stapel heeft geen historie en dus is de historie van de LO3 Persoon leidend voor de BRP historie.
     * 
     * @param lo3PersoonStapel
     *            de LO3 persoon stapel
     * @param lo3VerblijfplaatsStapel
     *            de LO3 verblijfplaats stapel
     * @param lo3InschrijvingStapel
     *            de LO3 inschrijving stapel
     * @param migratiePersoonslijstBuilder
     *            de migratie persoonslijstbuilder
     */
    public final void converteer(
            final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel,
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel,
            final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        controleerPrecondities(lo3PersoonStapel, lo3InschrijvingStapel, migratiePersoonslijstBuilder);

        // Inschrijving
        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving = lo3InschrijvingStapel.getMeestRecenteElement();
        final List<MigratieGroep<BrpInschrijvingInhoud>> brpInschrijvingGroepen =
                new ArrayList<MigratieGroep<BrpInschrijvingInhoud>>();
        for (final Lo3Categorie<Lo3PersoonInhoud> lo3Persoon : lo3PersoonStapel) {
            brpInschrijvingGroepen.add(new MigratieGroep<BrpInschrijvingInhoud>(new BrpInschrijvingInhoud(lo3Persoon
                    .getInhoud().getVorigANummer(), lo3Persoon.getInhoud().getVolgendANummer(), lo3Inschrijving
                    .getInhoud().getDatumEersteInschrijving().converteerNaarBrpDatum(), lo3Inschrijving.getInhoud()
                    .getVersienummer()), lo3Persoon.getHistorie(), lo3Persoon.getDocumentatie(), lo3Inschrijving
                    .getLo3Herkomst()));
        }
        migratiePersoonslijstBuilder.inschrijvingStapel(new MigratieStapel<BrpInschrijvingInhoud>(
                brpInschrijvingGroepen));

        final Lo3Datum nuDatum =
                lo3Inschrijving.getInhoud().getDatumtijdstempel().converteerNaarBrpDatumTijd()
                        .converteerNaarLo3Datum();
        final Lo3Historie nuOpgenomen = new Lo3Historie(null, new Lo3Datum(0), nuDatum);

        // Opschorting
        converteerOpschorting(lo3Inschrijving, lo3VerblijfplaatsStapel, migratiePersoonslijstBuilder, nuOpgenomen);

        // Persoonskaart
        final MigratieGroep<BrpPersoonskaartInhoud> migratiePersoonskaart =
                new MigratieGroep<BrpPersoonskaartInhoud>(new BrpPersoonskaartInhoud(
                        converteerder.converteerLo3GemeenteCode(lo3Inschrijving.getInhoud().getGemeentePKCode()),
                        converteerder.converteerLo3IndicatiePKVolledigGeconverteerdCode(lo3Inschrijving.getInhoud()
                                .getIndicatiePKVolledigGeconverteerdCode())), nuOpgenomen,
                        lo3Inschrijving.getDocumentatie(), lo3Inschrijving.getLo3Herkomst());

        if (!migratiePersoonskaart.isInhoudelijkLeeg()) {
            final List<MigratieGroep<BrpPersoonskaartInhoud>> groepen =
                    new ArrayList<MigratieGroep<BrpPersoonskaartInhoud>>();
            groepen.add(migratiePersoonskaart);
            migratiePersoonslijstBuilder.persoonskaartStapel(new MigratieStapel<BrpPersoonskaartInhoud>(groepen));
        }

        // Verstrekkingsbeperking
        final MigratieGroep<BrpVerstrekkingsbeperkingInhoud> migratieVerstrekkingsbeperking =
                new MigratieGroep<BrpVerstrekkingsbeperkingInhoud>(new BrpVerstrekkingsbeperkingInhoud(
                        converteerder.converteerLo3IndicatieGeheimCode(lo3Inschrijving.getInhoud()
                                .getIndicatieGeheimCode())), nuOpgenomen, lo3Inschrijving.getDocumentatie(),
                        lo3Inschrijving.getLo3Herkomst());

        if (!migratieVerstrekkingsbeperking.isInhoudelijkLeeg()) {
            final List<MigratieGroep<BrpVerstrekkingsbeperkingInhoud>> groepen =
                    new ArrayList<MigratieGroep<BrpVerstrekkingsbeperkingInhoud>>();
            groepen.add(migratieVerstrekkingsbeperking);
            migratiePersoonslijstBuilder
                    .verstrekkingsbeperkingStapel(new MigratieStapel<BrpVerstrekkingsbeperkingInhoud>(groepen));
        }

        // Afgeleid administratief
        final List<MigratieGroep<BrpAfgeleidAdministratiefInhoud>> afgeleidAdministratiefGroepen =
                new ArrayList<MigratieGroep<BrpAfgeleidAdministratiefInhoud>>();

        final Lo3Datumtijdstempel laatsteWijziging = lo3Inschrijving.getInhoud().getDatumtijdstempel();
        afgeleidAdministratiefGroepen.add(new MigratieGroep<BrpAfgeleidAdministratiefInhoud>(
                new BrpAfgeleidAdministratiefInhoud(
                        laatsteWijziging == null ? /* null; resulteert in een xml error?!?!? */BrpDatumTijd
                                .fromDatumTijd(0L) : laatsteWijziging.converteerNaarBrpDatumTijd(), null),
                nuOpgenomen, lo3Inschrijving.getDocumentatie(), lo3Inschrijving.getLo3Herkomst()));
        final MigratieStapel<BrpAfgeleidAdministratiefInhoud> afgeleidAdministratiefStapel =
                new MigratieStapel<BrpAfgeleidAdministratiefInhoud>(afgeleidAdministratiefGroepen);
        migratiePersoonslijstBuilder.afgeleidAdministratiefStapel(afgeleidAdministratiefStapel);
    }

    private void converteerOpschorting(
            final Lo3Categorie<Lo3InschrijvingInhoud> lo3Inschrijving,
            final Lo3Stapel<Lo3VerblijfplaatsInhoud> lo3VerblijfplaatsStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder,
            final Lo3Historie nuOpgenomen) {
        final Lo3RedenOpschortingBijhoudingCode lo3RedenOpschorting =
                lo3Inschrijving.getInhoud().getRedenOpschortingBijhoudingCode();
        final Lo3Datum lo3DatumOpschorting = lo3Inschrijving.getInhoud().getDatumOpschortingBijhouding();

        final BrpRedenOpschortingBijhoudingCode brpRedenOpschorting =
                converteerder.converteerLo3RedenOpschortingBijhoudingCode(lo3RedenOpschorting);
        final BrpDatum brpDatumOpschorting =
                brpRedenOpschorting == null ? null : converteerder.converteerDatum(lo3DatumOpschorting);

        final BrpOpschortingInhoud opschortingInhoud =
                new BrpOpschortingInhoud(brpDatumOpschorting, brpRedenOpschorting);

        if (!opschortingInhoud.isLeeg()) {
            // In BRP is de datum aanvang van de opschorting de datum opschorting
            final Lo3Historie opschortingHistorie =
                    new Lo3Historie(null, lo3DatumOpschorting, nuOpgenomen.getDatumVanOpneming());

            final MigratieGroep<BrpOpschortingInhoud> migratieOpschorting =
                    new MigratieGroep<BrpOpschortingInhoud>(opschortingInhoud, opschortingHistorie,
                            lo3Inschrijving.getDocumentatie(), lo3Inschrijving.getLo3Herkomst());

            if (!migratieOpschorting.isInhoudelijkLeeg()) {
                final List<MigratieGroep<BrpOpschortingInhoud>> groepen =
                        new ArrayList<MigratieGroep<BrpOpschortingInhoud>>();
                groepen.add(migratieOpschorting);
                migratiePersoonslijstBuilder.opschortingStapel(new MigratieStapel<BrpOpschortingInhoud>(groepen));
            }
        }

        // Bijhoudingsverantwoordelijkheid
        if (lo3RedenOpschorting != null) {
            final BrpBijhoudingsverantwoordelijkheidInhoud verantwoordelijkheid;

            if (Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.equalsElement(lo3RedenOpschorting)
                    || Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.equalsElement(lo3RedenOpschorting)
                    || Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.equalsElement(lo3RedenOpschorting)) {
                // Verantwoordelijkheid bij de gemeente
                final Lo3Datum datum;
                if (lo3VerblijfplaatsStapel != null) {
                    datum = lo3VerblijfplaatsStapel.getMeestRecenteElement().getInhoud().getDatumInschrijving();
                } else {
                    throw new IllegalStateException("Bijhoudingsverantwoordelijkheid ligt bij de gemeente, "
                            + "maar geen verblijfsplaats beschikbaar.");
                }

                verantwoordelijkheid =
                        new BrpBijhoudingsverantwoordelijkheidInhoud(BrpVerantwoordelijkeCode.COLLEGE_B_EN_W,
                                datum.converteerNaarBrpDatum());
            } else {
                // Verantwoordelijkheid bij de minister
                verantwoordelijkheid =
                        new BrpBijhoudingsverantwoordelijkheidInhoud(BrpVerantwoordelijkeCode.MINISTER,
                                lo3DatumOpschorting.converteerNaarBrpDatum());
            }

            // In BRP is de datum aanvang van de opschorting de datum bijhoudingsverantwoordelijkheid
            final Lo3Historie bijhoudingHistorie =
                    new Lo3Historie(null, verantwoordelijkheid.getDatumBijhoudingsverantwoordelijkheid()
                            .converteerNaarLo3Datum(), nuOpgenomen.getDatumVanOpneming());

            final List<MigratieGroep<BrpBijhoudingsverantwoordelijkheidInhoud>> bijhoudingverantwoordelijkheidGroepen =
                    new ArrayList<MigratieGroep<BrpBijhoudingsverantwoordelijkheidInhoud>>();
            bijhoudingverantwoordelijkheidGroepen.add(new MigratieGroep<BrpBijhoudingsverantwoordelijkheidInhoud>(
                    verantwoordelijkheid, bijhoudingHistorie, lo3Inschrijving.getDocumentatie(), lo3Inschrijving
                            .getLo3Herkomst()));

            migratiePersoonslijstBuilder
                    .bijhoudingsverantwoordelijkheidStapel(new MigratieStapel<BrpBijhoudingsverantwoordelijkheidInhoud>(
                            bijhoudingverantwoordelijkheidGroepen));
        }

    }

    private static void controleerPrecondities(
            final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel,
            final Lo3Stapel<Lo3InschrijvingInhoud> lo3InschrijvingStapel,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        if (lo3PersoonStapel == null) {
            throw new NullPointerException("lo3PersoonStapel mag niet null zijn");
        }
        if (lo3InschrijvingStapel == null) {
            throw new NullPointerException("lo3InschrijvingStapel mag niet null zijn");
        }
        if (migratiePersoonslijstBuilder == null) {
            throw new NullPointerException("migratiePersoonslijstBuilder mag niet null zijn");
        }
        if (lo3InschrijvingStapel.size() != 1) {
            throw new IllegalArgumentException("de inschrijving stapel mag geen historie hebben en dus moet de "
                    + "lengte 1 zijn. De lengte is echter: " + lo3InschrijvingStapel.size());
        }
        if (!lo3InschrijvingStapel.getMeestRecenteElement().getHistorie().isNullHistorie()) {
            throw new IllegalArgumentException("de inschrijving stapel mag geen historie hebben, toch bevat de "
                    + "inschrijving stapel historie die ongelijk is aan de NULL historie");
        }
    }
}

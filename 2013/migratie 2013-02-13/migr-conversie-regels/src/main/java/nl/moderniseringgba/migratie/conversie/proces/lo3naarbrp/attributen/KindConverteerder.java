/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieRelatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 Kind te converteren naar BRP relaties, betrokkenen en gerelateerde personen.
 */
// CHECKSTYLE:OFF - Class fan out complexity
@Component
@Requirement({ Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR001 })
public class KindConverteerder {
    // CHECKSTYLE:ON

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Kinder stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * 
     * @param kindStapels
     *            de lijst met stapels voor Kind, mag niet null zijn, maar wel leeg
     * @param migratiePersoonslijstBuilder
     *            de migratie persoonslijst builder
     */
    public final void converteer(
            final List<Lo3Stapel<Lo3KindInhoud>> kindStapels,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        for (final Lo3Stapel<Lo3KindInhoud> kindStapel : kindStapels) {

            final List<MigratieGroep<BrpIdentificatienummersInhoud>> persoonIdentificatienummersGroepen =
                    new ArrayList<MigratieGroep<BrpIdentificatienummersInhoud>>();
            final List<MigratieGroep<BrpGeboorteInhoud>> geboorteGroepen =
                    new ArrayList<MigratieGroep<BrpGeboorteInhoud>>();
            final List<MigratieGroep<BrpSamengesteldeNaamInhoud>> naamGroepen =
                    new ArrayList<MigratieGroep<BrpSamengesteldeNaamInhoud>>();
            final List<MigratieGroep<BrpOuderInhoud>> ouderGroepen = new ArrayList<MigratieGroep<BrpOuderInhoud>>();

            for (final Lo3Categorie<Lo3KindInhoud> kind : kindStapel) {
                persoonIdentificatienummersGroepen.add(migreerIdentificatienummers(kind));
                geboorteGroepen.add(migreerGeboorte(kind));

                naamGroepen.add(migreerNaam(kind));
                ouderGroepen.add(migreerOuder(kind));

            }

            final MigratieBetrokkenheid betrokkenheidStapelKind =
                    new MigratieBetrokkenheid(BrpSoortBetrokkenheidCode.KIND,
                            new MigratieStapel<BrpIdentificatienummersInhoud>(persoonIdentificatienummersGroepen),
                            null, new MigratieStapel<BrpGeboorteInhoud>(geboorteGroepen), null,
                            new MigratieStapel<BrpSamengesteldeNaamInhoud>(naamGroepen),
                            new MigratieStapel<BrpOuderInhoud>(ouderGroepen));

            final List<MigratieBetrokkenheid> betrokkenheidStapels = Arrays.asList(betrokkenheidStapelKind);
            final MigratieRelatie relatie =
                    new MigratieRelatie(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
                            BrpSoortBetrokkenheidCode.OUDER, betrokkenheidStapels, null);

            migratiePersoonslijstBuilder.relatie(relatie);
        }

    }

    /* Private methods */

    private MigratieGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(
            final Lo3Categorie<Lo3KindInhoud> kind) {
        return new MigratieGroep<BrpIdentificatienummersInhoud>(new BrpIdentificatienummersInhoud(kind.getInhoud()
                .getaNummer(), kind.getInhoud().getBurgerservicenummer()), kind.getHistorie(),
                kind.getDocumentatie(), kind.getLo3Herkomst());
    }

    private MigratieGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3KindInhoud> kind) {
        final BrpPlaatsLand brpPlaatsLand =
                new Lo3PlaatsLandConversieHelper(kind.getInhoud().getGeboorteGemeenteCode(), kind.getInhoud()
                        .getGeboorteLandCode(), converteerder).converteerNaarBrp();

        final BrpDatum brpGeboortedatum = converteerder.converteerDatum(kind.getInhoud().getGeboortedatum());

        final MigratieGroep<BrpGeboorteInhoud> migratieGeboorte =
                new MigratieGroep<BrpGeboorteInhoud>(new BrpGeboorteInhoud(brpGeboortedatum,
                        brpPlaatsLand.getBrpGemeenteCode(), brpPlaatsLand.getBrpPlaatsCode(),
                        brpPlaatsLand.getBrpBuitenlandsePlaats(), brpPlaatsLand.getBrpBuitenlandseRegio(),
                        brpPlaatsLand.getBrpLandCode(), brpPlaatsLand.getBrpOmschrijvingLocatie()),
                        kind.getHistorie(), kind.getDocumentatie(), kind.getLo3Herkomst());
        return migratieGeboorte;
    }

    private MigratieGroep<BrpSamengesteldeNaamInhoud> migreerNaam(final Lo3Categorie<Lo3KindInhoud> kind) {
        final Lo3KindInhoud inhoud = kind.getInhoud();

        final BrpPredikaatCode predikaatCode =
                converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpPredikaatCode(inhoud
                        .getAdellijkeTitelPredikaatCode());
        final String voornamen = inhoud.getVoornamen();
        final VoorvoegselScheidingstekenPaar voorvoegselPaar =
                converteerder.converteerLo3VoorvoegselNaarVoorvoegselScheidingstekenPaar(inhoud
                        .getVoorvoegselGeslachtsnaam());
        final BrpAdellijkeTitelCode adellijkeTitelCode =
                converteerder.converteerLo3AdellijkeTitelPredikaatCodeNaarBrpAdellijkeTitelCode(inhoud
                        .getAdellijkeTitelPredikaatCode());
        final String geslachtsnaam = inhoud.getGeslachtsnaam();

        return new MigratieGroep<BrpSamengesteldeNaamInhoud>(new BrpSamengesteldeNaamInhoud(predikaatCode, voornamen,
                voorvoegselPaar.getVoorvoegsel(), voorvoegselPaar.getScheidingsteken(), adellijkeTitelCode,
                geslachtsnaam, false, false), kind.getHistorie(), kind.getDocumentatie(), kind.getLo3Herkomst());
    }

    private MigratieGroep<BrpOuderInhoud> migreerOuder(final Lo3Categorie<Lo3KindInhoud> kind) {
        final Lo3KindInhoud inhoud = kind.getInhoud();

        final Boolean indicatieKind = !inhoud.isLeeg();
        final BrpDatum aanvang = converteerder.converteerDatum(kind.getHistorie().getIngangsdatumGeldigheid());

        return new MigratieGroep<BrpOuderInhoud>(new BrpOuderInhoud(indicatieKind, aanvang), kind.getHistorie(),
                kind.getDocumentatie(), kind.getLo3Herkomst());
    }
}

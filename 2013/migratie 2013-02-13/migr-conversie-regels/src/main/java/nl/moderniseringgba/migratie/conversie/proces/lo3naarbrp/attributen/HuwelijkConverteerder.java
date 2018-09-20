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
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieRelatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;
import nl.moderniseringgba.migratie.conversie.validatie.ValidationUtils;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 huwelijk te converteren naar BRP relaties, betrokkenen en gerelateerde
 * personen.
 * 
 * 
 * 
 */
// CHECKSTYLE:OFF - Fan-out complexity - geaccepteerd
@Component
@Requirement({ Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR002 })
public class HuwelijkConverteerder {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Huwelijk stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * 
     * @param huwelijkOfGpStapels
     *            de lijst met huwelijkOfGp stapels
     * @param migratiePersoonslijstBuilder
     *            de migratie persoonslijst builder
     */
    public final void converteer(
            final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        LOG.debug("converteer(huwelijkOfGpStapels={},builder=<notshown>)", huwelijkOfGpStapels);
        if (huwelijkOfGpStapels.isEmpty()) {
            return;
        }
        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel : huwelijkOfGpStapels) {
            LOG.debug("Converteer stapel: {}", huwelijkOfGpStapel);

            final List<MigratieGroep<BrpRelatieInhoud>> relatieGroepen =
                    new ArrayList<MigratieGroep<BrpRelatieInhoud>>();
            final List<MigratieGroep<BrpIdentificatienummersInhoud>> persoonIdentificatienummersGroepen =
                    new ArrayList<MigratieGroep<BrpIdentificatienummersInhoud>>();
            final List<MigratieGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepen =
                    new ArrayList<MigratieGroep<BrpGeslachtsaanduidingInhoud>>();
            final List<MigratieGroep<BrpGeboorteInhoud>> geboorteGroepen =
                    new ArrayList<MigratieGroep<BrpGeboorteInhoud>>();
            final List<MigratieGroep<BrpSamengesteldeNaamInhoud>> naamGroepen =
                    new ArrayList<MigratieGroep<BrpSamengesteldeNaamInhoud>>();

            for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp : huwelijkOfGpStapel) {
                LOG.debug("Converteer categorie: {}", huwelijkOfGp);
                final boolean isCorrectie = isCorrectie(huwelijkOfGp.getInhoud());

                LOG.debug("Migreer relatie");
                if (isRelatieGerelateerd(huwelijkOfGp) || isCorrectie) {
                    relatieGroepen.add(migreerRelatie(huwelijkOfGp));
                }

                // Als het gaat om een omzetting dan alleen de relatie converteren
                if (Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS.equals(huwelijkOfGp.getInhoud()
                        .getRedenOntbindingHuwelijkOfGpCode())) {
                    continue;
                }
                if (isCorrectie) {
                    continue;
                }

                LOG.debug("Migreer identificatienummers");
                persoonIdentificatienummersGroepen.add(migreerIdentificatienummers(huwelijkOfGp));
                LOG.debug("Migreer geslachtsaanduiding");
                geslachtsaanduidingGroepen.add(migreerGeslachsaanduiding(huwelijkOfGp));
                LOG.debug("Migreer geboorte");
                geboorteGroepen.add(migreerGeboorte(huwelijkOfGp));
                LOG.debug("Migreer samengestelde naam");
                naamGroepen.add(migreerSamengesteldeNaam(huwelijkOfGp));
            }

            LOG.debug("Maak betrokkenheid stapel");
            final MigratieBetrokkenheid betrokkenheid =
                    new MigratieBetrokkenheid(BrpSoortBetrokkenheidCode.PARTNER,
                            new MigratieStapel<BrpIdentificatienummersInhoud>(persoonIdentificatienummersGroepen),
                            new MigratieStapel<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingGroepen),
                            new MigratieStapel<BrpGeboorteInhoud>(geboorteGroepen), null,
                            new MigratieStapel<BrpSamengesteldeNaamInhoud>(naamGroepen), null);

            LOG.debug("Maak relatie stapel");
            migratiePersoonslijstBuilder.relatie(new MigratieRelatie(converteerder
                    .converteerLo3SoortVerbintenis(bepaalSoortVerbintenis(huwelijkOfGpStapel)),
                    BrpSoortBetrokkenheidCode.PARTNER, Arrays.asList(betrokkenheid),
                    new MigratieStapel<BrpRelatieInhoud>(relatieGroepen)));
        }
    }

    private boolean isRelatieGerelateerd(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        // Dit record is alleen bruikbaar voor de relatie stapel als de ingangsdatum van de geldigheid
        // hetzelfde is als de datum sluiting of datum ontbinding van de inhoud. Anders gaat het record
        // over een verandering in de persoonsgegevens.
        final Lo3Datum ingangsdatum = huwelijkOfGp.getHistorie().getIngangsdatumGeldigheid();
        final Lo3HuwelijkOfGpInhoud inhoud = huwelijkOfGp.getInhoud();
        return ingangsdatum.equals(inhoud.getDatumSluitingHuwelijkOfAangaanGp())
                || ingangsdatum.equals(inhoud.getDatumOntbindingHuwelijkOfGp());

    }

    private boolean isCorrectie(final Lo3HuwelijkOfGpInhoud inhoud) {
        return !ValidationUtils.isEenParameterGevuld(inhoud.getaNummer(), inhoud.getBurgerservicenummer(),
                inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(), inhoud.getVoorvoegselGeslachtsnaam(),
                inhoud.getGeslachtsnaam(), inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                inhoud.getGeboorteLandCode(), inhoud.getGeslachtsaanduiding(),
                inhoud.getDatumSluitingHuwelijkOfAangaanGp(), inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(), inhoud.getDatumOntbindingHuwelijkOfGp(),
                inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(), inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                inhoud.getRedenOntbindingHuwelijkOfGpCode(), inhoud.getSoortVerbintenis());
    }

    /* Private methods */
    /**
     * Kijkt in de stapel naar de meest actuele categorie waarvoor de soort verbintenis is gevuld.
     * 
     * @param huwelijkOfGpStapel
     *            de stapel waarin moet worden gezocht naar de soort verbintenis
     * @return de soort verbintenis
     */
    private Lo3SoortVerbintenis bepaalSoortVerbintenis(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp = huwelijkOfGpStapel.getMeestRecenteElement();
        while (huwelijkOfGp != null) {
            if (huwelijkOfGp.getInhoud().getSoortVerbintenis() != null) {
                return huwelijkOfGp.getInhoud().getSoortVerbintenis();
            }
            huwelijkOfGp = huwelijkOfGpStapel.getVorigElement(huwelijkOfGp);
        }
        return null;
    }

    private static MigratieGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        return new MigratieGroep<BrpIdentificatienummersInhoud>(new BrpIdentificatienummersInhoud(huwelijkOfGp
                .getInhoud().getaNummer(), huwelijkOfGp.getInhoud().getBurgerservicenummer()),
                huwelijkOfGp.getHistorie(), huwelijkOfGp.getDocumentatie(), huwelijkOfGp.getLo3Herkomst());
    }

    private MigratieGroep<BrpGeslachtsaanduidingInhoud> migreerGeslachsaanduiding(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> migratieGeslachtsaanduiding =
                new MigratieGroep<BrpGeslachtsaanduidingInhoud>(new BrpGeslachtsaanduidingInhoud(
                        converteerder.converteerLo3Geslachtsaanduiding(huwelijkOfGp.getInhoud()
                                .getGeslachtsaanduiding())), huwelijkOfGp.getHistorie(),
                        huwelijkOfGp.getDocumentatie(), huwelijkOfGp.getLo3Herkomst());
        return migratieGeslachtsaanduiding;
    }

    private MigratieGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final BrpPlaatsLand brpPlaatsLand =
                new Lo3PlaatsLandConversieHelper(huwelijkOfGp.getInhoud().getGeboorteGemeenteCode(), huwelijkOfGp
                        .getInhoud().getGeboorteLandCode(), converteerder).converteerNaarBrp();

        final BrpDatum brpGeboortedatum =
                huwelijkOfGp.getInhoud().getGeboortedatum() == null ? null : huwelijkOfGp.getInhoud()
                        .getGeboortedatum().converteerNaarBrpDatum();

        final MigratieGroep<BrpGeboorteInhoud> migratieGeboorte =
                new MigratieGroep<BrpGeboorteInhoud>(new BrpGeboorteInhoud(brpGeboortedatum,
                        brpPlaatsLand.getBrpGemeenteCode(), brpPlaatsLand.getBrpPlaatsCode(),
                        brpPlaatsLand.getBrpBuitenlandsePlaats(), brpPlaatsLand.getBrpBuitenlandseRegio(),
                        brpPlaatsLand.getBrpLandCode(), brpPlaatsLand.getBrpOmschrijvingLocatie()),
                        huwelijkOfGp.getHistorie(), huwelijkOfGp.getDocumentatie(), huwelijkOfGp.getLo3Herkomst());
        return migratieGeboorte;
    }

    private MigratieGroep<BrpSamengesteldeNaamInhoud> migreerSamengesteldeNaam(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final Lo3HuwelijkOfGpInhoud inhoud = huwelijkOfGp.getInhoud();

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
                geslachtsnaam, false, false), huwelijkOfGp.getHistorie(), huwelijkOfGp.getDocumentatie(),
                huwelijkOfGp.getLo3Herkomst());
    }

    private MigratieGroep<BrpRelatieInhoud> migreerRelatie(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {

        // Bepaal aanvang
        final BrpPlaatsLand brpAanvangRelatiePlaatsLand =
                new Lo3PlaatsLandConversieHelper(huwelijkOfGp.getInhoud()
                        .getGemeenteCodeSluitingHuwelijkOfAangaanGp(), huwelijkOfGp.getInhoud()
                        .getLandCodeSluitingHuwelijkOfAangaanGp(), converteerder).converteerNaarBrp();
        final BrpDatum datumAanvang =
                converteerder.converteerDatum(huwelijkOfGp.getInhoud().getDatumSluitingHuwelijkOfAangaanGp());
        final BrpGemeenteCode gemeenteCodeAanvang = brpAanvangRelatiePlaatsLand.getBrpGemeenteCode();
        final BrpPlaatsCode plaatsCodeAanvang = brpAanvangRelatiePlaatsLand.getBrpPlaatsCode();
        final String buitenlandsePlaatsAanvang = brpAanvangRelatiePlaatsLand.getBrpBuitenlandsePlaats();
        final String buitenlandseRegioAanvang = brpAanvangRelatiePlaatsLand.getBrpBuitenlandseRegio();
        final BrpLandCode landCodeAanvang = brpAanvangRelatiePlaatsLand.getBrpLandCode();
        final String omschrijvingLocatieAanvang = brpAanvangRelatiePlaatsLand.getBrpOmschrijvingLocatie();

        // Bepaal einde
        final BrpPlaatsLand brpEindeRelatiePlaatsLand =
                new Lo3PlaatsLandConversieHelper(huwelijkOfGp.getInhoud().getGemeenteCodeOntbindingHuwelijkOfGp(),
                        huwelijkOfGp.getInhoud().getLandCodeOntbindingHuwelijkOfGp(), converteerder)
                        .converteerNaarBrp();
        final BrpRedenEindeRelatieCode redenEinde =
                converteerRedenEinde(huwelijkOfGp.getInhoud().getRedenOntbindingHuwelijkOfGpCode());
        final BrpDatum datumEinde =
                converteerder.converteerDatum(huwelijkOfGp.getInhoud().getDatumOntbindingHuwelijkOfGp());
        final BrpGemeenteCode gemeenteCodeEinde = brpEindeRelatiePlaatsLand.getBrpGemeenteCode();
        final BrpPlaatsCode plaatsCodeEinde = brpEindeRelatiePlaatsLand.getBrpPlaatsCode();
        final String buitenlandsePlaatsEinde = brpEindeRelatiePlaatsLand.getBrpBuitenlandsePlaats();
        final String buitenlandseRegioEinde = brpEindeRelatiePlaatsLand.getBrpBuitenlandseRegio();
        final BrpLandCode landCodeEinde = brpEindeRelatiePlaatsLand.getBrpLandCode();
        final String omschrijvingLocatieEinde = brpEindeRelatiePlaatsLand.getBrpOmschrijvingLocatie();

        return new MigratieGroep<BrpRelatieInhoud>(new BrpRelatieInhoud(datumAanvang, gemeenteCodeAanvang,
                plaatsCodeAanvang, buitenlandsePlaatsAanvang, buitenlandseRegioAanvang, landCodeAanvang,
                omschrijvingLocatieAanvang, redenEinde, datumEinde, gemeenteCodeEinde, plaatsCodeEinde,
                buitenlandsePlaatsEinde, buitenlandseRegioEinde, landCodeEinde, omschrijvingLocatieEinde),
                huwelijkOfGp.getHistorie(), huwelijkOfGp.getDocumentatie(), huwelijkOfGp.getLo3Herkomst());
    }

    private BrpRedenEindeRelatieCode converteerRedenEinde(final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbinding) {
        if (Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS.equals(redenOntbinding)) {
            return BrpRedenEindeRelatieCode.OMZETTING;
        } else {
            return converteerder.converteerLo3RedenOntbindingHuwelijkOfGpCode(redenOntbinding);
        }

    }
}

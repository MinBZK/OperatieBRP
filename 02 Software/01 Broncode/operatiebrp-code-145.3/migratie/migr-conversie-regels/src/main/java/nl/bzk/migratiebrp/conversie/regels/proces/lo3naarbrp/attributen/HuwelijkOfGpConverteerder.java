/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstHuwelijkOfGpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.util.Lo3VerbintenisUtil;

/**
 * Deze class bevat de logica om een LO3 huwelijk te converteren naar BRP relaties, betrokkenen en gerelateerde
 * personen.
 */
@Requirement({Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR002})
public class HuwelijkOfGpConverteerder extends AbstractRelatieConverteerder {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Lo3HuwelijkOfGpInhoud STANDAARD_SLUITING =
            new Lo3HuwelijkOfGpInhoud.Builder().datumSluitingHuwelijkOfAangaanGp(new Lo3Datum(0))
                    .landCodeSluitingHuwelijkOfAangaanGp(Lo3LandCode.STANDAARD)
                    .build();

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder Lo3AttribuutConverteerder
     */
    public HuwelijkOfGpConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        super(lo3AttribuutConverteerder);
    }

    /**
     * Converteert de LO3 Huwelijk stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * @param huwelijkOfGpStapels de lijst met huwelijkOfGp stapels
     * @param tussenPersoonslijstBuilder de migratie persoonslijst builder
     * @param overlijdenStapel de overlijden stapel
     */
    public final void converteer(
            final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel) {
        LOG.debug("converteer(huwelijkOfGpStapels=<notshown>,builder=<notshown>)");
        if (huwelijkOfGpStapels.isEmpty()) {
            return;
        }

        final Lo3Categorie<Lo3OverlijdenInhoud> actueelOverlijden = bepaalActueelOverlijden(overlijdenStapel);

        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel : huwelijkOfGpStapels) {
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleGerelateerde = huwelijkOfGpStapel.getLo3ActueelVoorkomen();
            final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istGegevens = converteerIst(huwelijkOfGpStapel);

            // Controle of actueel leeg is. Zo ja, dan geen relatie aanmaken
            if (!actueleGerelateerde.getInhoud().isLeeg()) {
                final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> gesplitsteStapel = Lo3VerbintenisUtil.splitsEnZetVerbintenissenOm(huwelijkOfGpStapel);

                for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> enkeleStapel : gesplitsteStapel) {
                    converteerEnkeleStapel(tussenPersoonslijstBuilder, enkeleStapel, actueleGerelateerde, istGegevens, actueelOverlijden);
                }
            }
            tussenPersoonslijstBuilder.istHuwelijkOfGpStapel(istGegevens);
        }
    }

    private TussenBetrokkenheid converteerPartnerNaarBetrokkenheid(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleGerelateerde) {
        final List<TussenGroep<BrpIdentificatienummersInhoud>> persoonIdentificatienummersGroepen = new ArrayList<>();
        final List<TussenGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepen = new ArrayList<>();
        final List<TussenGroep<BrpGeboorteInhoud>> geboorteGroepen = new ArrayList<>();
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> naamGroepen = new ArrayList<>();
        if (actueleGerelateerde != null) {
            LOG.debug("Migreer gerelateerde gegevens");
            persoonIdentificatienummersGroepen.add(migreerIdentificatienummers(actueleGerelateerde));
            geslachtsaanduidingGroepen.add(migreerGeslachsaanduiding(actueleGerelateerde));
            geboorteGroepen.add(migreerGeboorte(actueleGerelateerde));
            naamGroepen.add(migreerSamengesteldeNaam(actueleGerelateerde));
        }
        LOG.debug("Maak betrokkenheid stapel");
        return new TussenBetrokkenheid(
                BrpSoortBetrokkenheidCode.PARTNER,
                new TussenStapel<>(persoonIdentificatienummersGroepen),
                new TussenStapel<>(geslachtsaanduidingGroepen),
                new TussenStapel<>(geboorteGroepen),
                null,
                new TussenStapel<>(naamGroepen),
                null);
    }

    private void converteerEnkeleStapel(
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel,
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleGerelateerde,
            final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istGegevens,
            final Lo3Categorie<Lo3OverlijdenInhoud> actueelOverlijden) {
        LOG.debug("Converteer stapel: <notshown>");

        final List<TussenGroep<BrpRelatieInhoud>> relatieGroepen = new ArrayList<>();

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> sluitingVoorkomen = Lo3VerbintenisUtil.zoekTeConverterenRij(huwelijkOfGpStapel.getCategorieen(), true);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> ontbindingVoorkomen = bepaalOntbindingVoorkomen(huwelijkOfGpStapel, actueleGerelateerde, actueelOverlijden);

        BrpSoortRelatieCode soortRelatieCode = null;
        if (sluitingVoorkomen != null) {
            LOG.debug("Migreer relatie sluiting");
            final Lo3HuwelijkOfGpInhoud basis = bepaalBasis(ontbindingVoorkomen, sluitingVoorkomen);
            relatieGroepen.add(
                    getUtils().migreerRelatie(
                            sluitingVoorkomen.getInhoud(),
                            basis,
                            sluitingVoorkomen.getDocumentatie(),
                            sluitingVoorkomen.getHistorie(),
                            sluitingVoorkomen.getLo3Herkomst()));
            soortRelatieCode = getLo3AttribuutConverteerder().converteerLo3SoortVerbintenis(sluitingVoorkomen.getInhoud().getSoortVerbintenis());
        }

        if (ontbindingVoorkomen != null) {
            LOG.debug("Migreer relatie ontbinding");
            final Lo3HuwelijkOfGpInhoud basis = bepaalBasis(sluitingVoorkomen, ontbindingVoorkomen);
            relatieGroepen.add(
                    getUtils().migreerRelatie(
                            ontbindingVoorkomen.getInhoud(),
                            basis != null ? basis : STANDAARD_SLUITING,
                            ontbindingVoorkomen.getDocumentatie(),
                            ontbindingVoorkomen.getHistorie(),
                            ontbindingVoorkomen.getLo3Herkomst()));
            soortRelatieCode =
                    soortRelatieCode != null ? soortRelatieCode
                            : getLo3AttribuutConverteerder().converteerLo3SoortVerbintenis(ontbindingVoorkomen.getInhoud().getSoortVerbintenis());

        }

        if (!relatieGroepen.isEmpty()) {
            LOG.debug("Maak relatie stapel");
            final TussenBetrokkenheid betrokkenheid = converteerPartnerNaarBetrokkenheid(actueleGerelateerde);
            final TussenRelatie.Builder relatieBuilder = new TussenRelatie.Builder(soortRelatieCode, BrpSoortBetrokkenheidCode.PARTNER);
            relatieBuilder.betrokkenheden(Collections.singletonList(betrokkenheid));
            relatieBuilder.relatieStapel(new TussenStapel<>(relatieGroepen));
            relatieBuilder.istHuwelijkOfGp(istGegevens);

            tussenPersoonslijstBuilder.relatie(relatieBuilder.build());
        }
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> bepaalOntbindingVoorkomen(
            final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel,
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleGerelateerde,
            final Lo3Categorie<Lo3OverlijdenInhoud> actueelOverlijden) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> ontbindingVoorkomen = Lo3VerbintenisUtil.zoekTeConverterenRij(huwelijkOfGpStapel.getCategorieen(), false);

        // Alleen als de stapel de actuele categorie bevat en de actuele categorie is een sluiting,
        // dan moeten evt. ontbinding categorieen niet worden geconverteerd.
        // (Uitzondering is de even hiervoor toegevoegde reden einde 'Z'.)
        if (ontbindingVoorkomen != null) {
            final Lo3RedenOntbindingHuwelijkOfGpCode code = ontbindingVoorkomen.getInhoud().getRedenOntbindingHuwelijkOfGpCode();

            if (huwelijkOfGpStapel.bevatLo3ActueelVoorkomen()
                    && actueleGerelateerde.getInhoud().isSluiting()
                    && !AbstractLo3Element.equalsWaarde(code, Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS)) {
                ontbindingVoorkomen = null;
            }
        }

        if (ontbindingVoorkomen == null && actueelOverlijden != null) {
            LOG.debug("Maak relatie ontbinding o.b.v. actueel overlijden");
            ontbindingVoorkomen = maakOverlijdenOntbindingVoorkomen(actueelOverlijden);
        }
        return ontbindingVoorkomen;
    }

    /**
     * De ontbinding wordt aangevuld met gegevens uit de sluiting (reguliere scenario); wanneer de volgorde van
     * datumOpneming 'verkeerd om' is, wordt de sluiting aangevuld met de gegevens uit de ontbinding. Deze functie
     * bepaalt voor beide scenario's of er sprake is van zo'n basisrecord, door te kijken naar de volgorde van
     * datumOpneming. Zo niet is het resultaat null.
     * @param mogelijkeBasis De kandidaat-basisrij.
     * @param inhoud Het record dat we willen aanvullen.
     * @return Het basisrecord (de meegegeven mogelijkeBasis), of null als dit niet van toepassing is.
     */
    private Lo3HuwelijkOfGpInhoud bepaalBasis(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> mogelijkeBasis, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> inhoud) {
        Lo3HuwelijkOfGpInhoud result = null;

        if (mogelijkeBasis != null) {
            final Lo3Datum datumOpnemingA = mogelijkeBasis.getHistorie().getDatumVanOpneming();
            final Lo3Datum datumOpnemingB = inhoud.getHistorie().getDatumVanOpneming();
            final int compareResultaatOpneming = datumOpnemingA.compareTo(datumOpnemingB);

            if (compareResultaatOpneming < 0) {
                result = mogelijkeBasis.getInhoud();
            } else if (compareResultaatOpneming == 0) {
                final boolean mogelijkBasisHerkomstGroter = mogelijkeBasis.getLo3Herkomst().getVoorkomen() > inhoud.getLo3Herkomst().getVoorkomen();
                if (!inhoud.getLo3Herkomst().isLo3ActueelVoorkomen() && !mogelijkeBasis.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                    result = bepaalTeGebruikenBasis(mogelijkeBasis, inhoud, mogelijkBasisHerkomstGroter);
                } else if (mogelijkBasisHerkomstGroter) {
                    result = mogelijkeBasis.getInhoud();
                }
            }
        }
        return result;
    }

    private Lo3HuwelijkOfGpInhoud bepaalTeGebruikenBasis(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> mogelijkeBasis,
                                                         final Lo3Categorie<Lo3HuwelijkOfGpInhoud> inhoud,
                                                         final boolean mogelijkBasisHerkomstGroter) {
        Lo3HuwelijkOfGpInhoud result = null;
        final Lo3Datum ingangsdatumGeldigheidA = mogelijkeBasis.getHistorie().getIngangsdatumGeldigheid();
        final Lo3Datum ingangsdatumGeldigheidB = inhoud.getHistorie().getIngangsdatumGeldigheid();

        final int compareResultaatGeldigheid = ingangsdatumGeldigheidA.compareTo(ingangsdatumGeldigheidB);
        if (compareResultaatGeldigheid < 0 || (compareResultaatGeldigheid == 0 && mogelijkBasisHerkomstGroter)) {
            result = mogelijkeBasis.getInhoud();
        }
        return result;
    }

    /**
     * Converteer huwelijk of GP stapel naar ist groepen. Is protected voor unit-test toegang.
     * @param huwelijkOfGpStapel de huwelijk of gp stapel
     * @return de ist groepen
     */
    final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> converteerIst(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        final List<TussenGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : huwelijkOfGpStapel.getCategorieen()) {
            final Lo3HuwelijkOfGpInhoud lo3Inhoud = categorie.getInhoud();
            final Lo3Documentatie lo3Documentatie = categorie.getDocumentatie();
            final Lo3Onderzoek lo3Onderzoek = categorie.getOnderzoek();
            final Lo3Historie lo3Historie = categorie.getHistorie();
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();

            final BrpIstHuwelijkOfGpGroepInhoud inhoud = maakIstHuwelijkOfGpGroepInhoud(lo3Inhoud, lo3Documentatie, lo3Onderzoek, lo3Historie, lo3Herkomst);
            groepen.add(maakMigratieGroep(inhoud));
        }

        return new TussenStapel<>(groepen);
    }

    private TussenGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final Lo3HuwelijkOfGpInhoud inhoud = huwelijkOfGp.getInhoud();
        return getUtils().maakIdentificatieGroep(
                inhoud.getaNummer(),
                inhoud.getBurgerservicenummer(),
                huwelijkOfGp.getHistorie(),
                huwelijkOfGp.getDocumentatie(),
                huwelijkOfGp.getLo3Herkomst());
    }

    private TussenGroep<BrpGeslachtsaanduidingInhoud> migreerGeslachsaanduiding(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final Lo3HuwelijkOfGpInhoud inhoud = huwelijkOfGp.getInhoud();
        return getUtils().maakGeslachtsaanduidingInhoud(
                inhoud.getGeslachtsaanduiding(),
                huwelijkOfGp.getHistorie(),
                huwelijkOfGp.getDocumentatie(),
                huwelijkOfGp.getLo3Herkomst());
    }

    private TussenGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final Lo3HuwelijkOfGpInhoud inhoud = huwelijkOfGp.getInhoud();
        return getUtils().maakGeboorteGroep(
                inhoud.getGeboorteGemeenteCode(),
                inhoud.getGeboorteLandCode(),
                inhoud.getGeboortedatum(),
                huwelijkOfGp.getHistorie(),
                huwelijkOfGp.getDocumentatie(),
                huwelijkOfGp.getLo3Herkomst());
    }

    private TussenGroep<BrpSamengesteldeNaamInhoud> migreerSamengesteldeNaam(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp) {
        final Lo3HuwelijkOfGpInhoud inhoud = huwelijkOfGp.getInhoud();

        return getUtils().maakSamengesteldeNaamGroep(
                inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoornamen(),
                inhoud.getVoorvoegselGeslachtsnaam(),
                inhoud.getGeslachtsnaam(),
                huwelijkOfGp.getHistorie(),
                huwelijkOfGp.getDocumentatie(),
                huwelijkOfGp.getLo3Herkomst());
    }

    private Lo3Categorie<Lo3OverlijdenInhoud> bepaalActueelOverlijden(final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel) {
        final Lo3Categorie<Lo3OverlijdenInhoud> result;
        if (overlijdenStapel == null || overlijdenStapel.isEmpty() || overlijdenStapel.getLo3ActueelVoorkomen().getInhoud().isLeeg()) {
            result = null;
        } else {
            result = overlijdenStapel.getLo3ActueelVoorkomen();
        }
        return result;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> maakOverlijdenOntbindingVoorkomen(final Lo3Categorie<Lo3OverlijdenInhoud> overlijdenVoorkomen) {
        final Lo3OverlijdenInhoud overlijdenInhoud = overlijdenVoorkomen.getInhoud();

        final Lo3HuwelijkOfGpInhoud ontbindingInhoud = new Lo3HuwelijkOfGpInhoud.Builder().datumOntbindingHuwelijkOfGp(overlijdenInhoud.getDatum())
                .gemeenteCodeOntbindingHuwelijkOfGp(
                        overlijdenInhoud.getGemeenteCode())
                .landCodeOntbindingHuwelijkOfGp(overlijdenInhoud.getLandCode())
                .redenOntbindingHuwelijkOfGpCode(
                        Lo3RedenOntbindingHuwelijkOfGpCode.OVERLIJDEN)
                .build();
        return new Lo3Categorie<>(
                ontbindingInhoud,
                overlijdenVoorkomen.getDocumentatie(),
                overlijdenVoorkomen.getOnderzoek(),
                overlijdenVoorkomen.getHistorie(),
                overlijdenVoorkomen.getLo3Herkomst(),
                overlijdenVoorkomen.isAfsluitendVoorkomen());
    }
}

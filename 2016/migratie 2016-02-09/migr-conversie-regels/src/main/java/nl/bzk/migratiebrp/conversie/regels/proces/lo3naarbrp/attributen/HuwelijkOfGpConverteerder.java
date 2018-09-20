/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 huwelijk te converteren naar BRP relaties, betrokkenen en gerelateerde
 * personen.
 *
 *
 *
 */
@Component
@Requirement({Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR002 })
public class HuwelijkOfGpConverteerder extends AbstractRelatieConverteerder {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Lo3HuwelijkOfGpInhoud STANDAARD_SLUITING = new Lo3HuwelijkOfGpInhoud.Builder().datumSluitingHuwelijkOfAangaanGp(
                                                                                                           Lo3Datum.NULL_DATUM)
                                                                                                       .landCodeSluitingHuwelijkOfAangaanGp(
                                                                                                           Lo3LandCode.STANDAARD)
                                                                                                       .build();

    /**
     * Converteert de LO3 Huwelijk stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     *
     * @param huwelijkOfGpStapels
     *            de lijst met huwelijkOfGp stapels
     * @param tussenPersoonslijstBuilder
     *            de migratie persoonslijst builder
     * @param overlijdenStapel
     *            de overlijden stapel
     */
    public final void converteer(
        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpStapels,
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
        final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel)
    {
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
                final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> gesplitsteStapel = Lo3SplitsenVerbintenis.splitsVerbintenissen(huwelijkOfGpStapel);

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
        return new TussenBetrokkenheid(BrpSoortBetrokkenheidCode.PARTNER, new TussenStapel<>(persoonIdentificatienummersGroepen), new TussenStapel<>(
            geslachtsaanduidingGroepen), new TussenStapel<>(geboorteGroepen), null, new TussenStapel<>(naamGroepen), null);
    }

    private void converteerEnkeleStapel(
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder,
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel,
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleGerelateerde,
        final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> istGegevens,
        final Lo3Categorie<Lo3OverlijdenInhoud> actueelOverlijden)
    {
        LOG.debug("Converteer stapel: <notshown>");

        final List<TussenGroep<BrpRelatieInhoud>> relatieGroepen = new ArrayList<>();

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> sluitingVoorkomen = zoekTeConverterenRij(huwelijkOfGpStapel, true);
        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> ontbindingVoorkomen =
                bepaalOntbindingVoorkomen(huwelijkOfGpStapel, actueleGerelateerde, actueelOverlijden);

        if (sluitingVoorkomen != null) {
            LOG.debug("Migreer relatie sluiting");
            final Lo3HuwelijkOfGpInhoud basis = bepaalBasis(ontbindingVoorkomen, sluitingVoorkomen);
            relatieGroepen.add(getUtils().migreerRelatie(
                sluitingVoorkomen.getInhoud(),
                basis,
                sluitingVoorkomen.getDocumentatie(),
                sluitingVoorkomen.getHistorie(),
                sluitingVoorkomen.getLo3Herkomst()));
        }

        if (ontbindingVoorkomen != null) {
            LOG.debug("Migreer relatie ontbinding");
            final Lo3HuwelijkOfGpInhoud basis = bepaalBasis(sluitingVoorkomen, ontbindingVoorkomen);
            relatieGroepen.add(getUtils().migreerRelatie(
                ontbindingVoorkomen.getInhoud(),
                basis != null ? basis : STANDAARD_SLUITING,
                ontbindingVoorkomen.getDocumentatie(),
                ontbindingVoorkomen.getHistorie(),
                ontbindingVoorkomen.getLo3Herkomst()));
        }

        if (relatieGroepen.size() > 0) {
            LOG.debug("Maak relatie stapel");
            final TussenBetrokkenheid betrokkenheid = converteerPartnerNaarBetrokkenheid(actueleGerelateerde);
            final BrpSoortRelatieCode soortRelatieCode =
                    getLo3AttribuutConverteerder().converteerLo3SoortVerbintenis(bepaalSoortVerbintenis(huwelijkOfGpStapel));
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
        final Lo3Categorie<Lo3OverlijdenInhoud> actueelOverlijden)
    {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> ontbindingVoorkomen = zoekTeConverterenRij(huwelijkOfGpStapel, false);

        // Alleen als de stapel de actuele categorie bevat en de actuele categorie is een sluiting,
        // dan moeten evt. ontbinding categorieen niet worden geconverteerd.
        // (Uitzondering is de even hiervoor toegevoegde reden einde 'Z'.)
        if (ontbindingVoorkomen != null) {
            final Lo3RedenOntbindingHuwelijkOfGpCode code = ontbindingVoorkomen.getInhoud().getRedenOntbindingHuwelijkOfGpCode();

            if (huwelijkOfGpStapel.bevatLo3ActueelVoorkomen()
                && actueleGerelateerde.getInhoud().isSluiting()
                && !AbstractLo3Element.equalsWaarde(code, Lo3RedenOntbindingHuwelijkOfGpCode.OMZETTING_VERBINTENIS))
            {
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
     *
     * @param mogelijkeBasis
     *            De kandidaat-basisrij.
     * @param inhoud
     *            Het record dat we willen aanvullen.
     * @return Het basisrecord (de meegegeven mogelijkeBasis), of null als dit niet van toepassing is.
     */
    private Lo3HuwelijkOfGpInhoud bepaalBasis(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> mogelijkeBasis, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> inhoud) {

        if (mogelijkeBasis == null) {
            return null;
        }

        Lo3HuwelijkOfGpInhoud result = null;

        final Lo3Datum datumOpnemingA = mogelijkeBasis.getHistorie().getDatumVanOpneming();
        final Lo3Datum datumOpnemingB = inhoud.getHistorie().getDatumVanOpneming();
        final int compareResultaatOpneming = datumOpnemingA.compareTo(datumOpnemingB);

        if (compareResultaatOpneming < 0) {
            result = mogelijkeBasis.getInhoud();
        } else if (compareResultaatOpneming == 0) {
            final boolean mogelijkBasisHerkomstGroter = mogelijkeBasis.getLo3Herkomst().getVoorkomen() > inhoud.getLo3Herkomst().getVoorkomen();
            if (!inhoud.getLo3Herkomst().isLo3ActueelVoorkomen() && !mogelijkeBasis.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                final Lo3Datum ingangsdatumGeldigheidA = mogelijkeBasis.getHistorie().getIngangsdatumGeldigheid();
                final Lo3Datum ingangsdatumGeldigheidB = inhoud.getHistorie().getIngangsdatumGeldigheid();

                final int compareResultaatGeldigheid = ingangsdatumGeldigheidA.compareTo(ingangsdatumGeldigheidB);
                if (compareResultaatGeldigheid < 0) {
                    result = mogelijkeBasis.getInhoud();
                } else if (compareResultaatGeldigheid == 0 && mogelijkBasisHerkomstGroter) {
                    result = mogelijkeBasis.getInhoud();
                }
            } else {
                if (mogelijkBasisHerkomstGroter) {
                    result = mogelijkeBasis.getInhoud();
                }
            }
        }

        return result;
    }

    /**
     * Converteer huwelijk of GP stapel naar ist groepen. Is protected voor unit-test toegang.
     *
     * @param huwelijkOfGpStapel
     *            de huwelijk of gp stapel
     * @return de ist groepen
     */
    protected final TussenStapel<BrpIstHuwelijkOfGpGroepInhoud> converteerIst(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        final List<TussenGroep<BrpIstHuwelijkOfGpGroepInhoud>> groepen = new ArrayList<>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : huwelijkOfGpStapel.getCategorieen()) {
            final Lo3HuwelijkOfGpInhoud lo3Inhoud = categorie.getInhoud();
            final Lo3Documentatie lo3Documentatie = categorie.getDocumentatie();
            final Lo3Onderzoek lo3Onderzoek = categorie.getOnderzoek();
            final Lo3Historie lo3Historie = categorie.getHistorie();
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();

            final BrpIstHuwelijkOfGpGroepInhoud inhoud =
                    maakIstHuwelijkOfGpGroepInhoud(lo3Inhoud, lo3Documentatie, lo3Onderzoek, lo3Historie, lo3Herkomst);
            groepen.add(maakMigratieGroep(inhoud));
        }

        return new TussenStapel<>(groepen);
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekTeConverterenRij(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel, final boolean zoekSluiting)
    {
        // Maak en nieuwe lijst aan en loop door de relatiestapel en voeg alleen de juiste rijen aan de nieuwe lijst
        // toe
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpRijen = new ArrayList<>();
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpRij : huwelijkOfGpStapel) {
            // Als de rij onjuist is, dan wordt deze niet geconverteerd
            if (huwelijkOfGpRij.getHistorie().isOnjuist()) {
                continue;
            }

            if (zoekSluiting && huwelijkOfGpRij.getInhoud().isSluiting() || !zoekSluiting && huwelijkOfGpRij.getInhoud().isOntbinding()) {
                huwelijkOfGpRijen.add(huwelijkOfGpRij);
            }
        }

        return bepaalActueleRij(zoekSluiting, huwelijkOfGpRijen);
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> bepaalActueleRij(
        final boolean zoekSluiting,
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpRijen)
    {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleRij = null;

        // Zoek de te gebruiken actuele rij voor de conversie
        if (huwelijkOfGpRijen.size() == 0) {
            actueleRij = null;
        } else if (huwelijkOfGpRijen.size() == 1) {
            // 1. Er is maar 1 juiste rij. Gebruik die.
            actueleRij = huwelijkOfGpRijen.get(0);
        } else if (zoekSluiting && isSluitingsGegevensIdentiek(huwelijkOfGpRijen) || !zoekSluiting && isOntbindingsGegevensIdentiek(huwelijkOfGpRijen)) {
            // 2. Bij consistente gegevens, zoek naar een unieke rij met geldigheid==sluitingsdatum
            final Lo3Datum zoekDatum =
                    zoekSluiting ? huwelijkOfGpRijen.get(0).getInhoud().getDatumSluitingHuwelijkOfAangaanGp()
                                : huwelijkOfGpRijen.get(0).getInhoud().getDatumOntbindingHuwelijkOfGp();
            actueleRij = zoekUniekeGeldigheidsWaarde(zoekDatum, huwelijkOfGpRijen);

            // 3. Bij consistente gegevens en geen geen unieke rij met geldigheid==sluitingsdatum, zoek
            // een unieke rij met geldigheid==Standaardwaarde==00000000
            if (actueleRij == null) {
                actueleRij = zoekUniekeGeldigheidsWaarde(Lo3Datum.NULL_DATUM, huwelijkOfGpRijen);
            }
        }

        // 4. In andere gevallen, zoek rij met meest recente geldigheid.
        if (actueleRij == null) {
            // Als de lijst met rijen de actuele rij bevat (voorkomen=0), dan deze gebruiken.
            actueleRij = zoekActueleRij(huwelijkOfGpRijen);
            if (actueleRij == null) {
                // Anders de rij met de meeste actuele/recente geldigheid zoeken
                actueleRij = zoekMeestRecenteGeldigheid(huwelijkOfGpRijen);
            }
        }
        return actueleRij;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekActueleRij(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpRijen) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> actueleRij = null;
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpRij : huwelijkOfGpRijen) {
            if (huwelijkOfGpRij.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                actueleRij = huwelijkOfGpRij;
                break;
            }
        }

        return actueleRij;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekUniekeGeldigheidsWaarde(
        final Lo3Datum zoekDatum,
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpRijen)
    {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> result = null;

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> rij : huwelijkOfGpRijen) {
            if (zoekDatum.equals(rij.getHistorie().getIngangsdatumGeldigheid())) {
                if (result == null) {
                    result = rij;
                } else {
                    // Meerdere rijen met zoekdatum gevonden. Resultaat wordt null omdat de rij niet uniek is.
                    result = null;
                    break;
                }
            }
        }

        return result;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> zoekMeestRecenteGeldigheid(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpRijen) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> result = null;

        if (huwelijkOfGpRijen.size() > 0) {
            Collections.sort(huwelijkOfGpRijen, Lo3Categorie.DATUM_GELDIGHEID);
            result = huwelijkOfGpRijen.get(0);
        }

        return result;
    }

    private boolean isSluitingsGegevensIdentiek(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> sluitingHuwelijkOfGpRijen) {
        boolean result = true;

        if (sluitingHuwelijkOfGpRijen.size() > 1) {
            final Iterator<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> iterator = sluitingHuwelijkOfGpRijen.iterator();
            final Lo3HuwelijkOfGpInhoud eersteRij = iterator.next().getInhoud();
            final Lo3Datum eersteSluitingsDatum = eersteRij.getDatumSluitingHuwelijkOfAangaanGp();
            final Lo3GemeenteCode eersteSluitingsGemeente = eersteRij.getGemeenteCodeSluitingHuwelijkOfAangaanGp();
            final Lo3LandCode eersteSluitingsLand = eersteRij.getLandCodeSluitingHuwelijkOfAangaanGp();

            while (iterator.hasNext()) {
                final Lo3HuwelijkOfGpInhoud volgendeRij = iterator.next().getInhoud();
                if (!isEqualNullable(eersteSluitingsDatum, volgendeRij.getDatumSluitingHuwelijkOfAangaanGp())
                    || !isEqualNullable(eersteSluitingsGemeente, volgendeRij.getGemeenteCodeSluitingHuwelijkOfAangaanGp())
                    || !isEqualNullable(eersteSluitingsLand, volgendeRij.getLandCodeSluitingHuwelijkOfAangaanGp()))
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private boolean isOntbindingsGegevensIdentiek(final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> ontbindingHuwelijkOfGpRijen) {
        boolean result = true;

        if (ontbindingHuwelijkOfGpRijen.size() > 1) {
            final Iterator<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> iterator = ontbindingHuwelijkOfGpRijen.iterator();
            final Lo3HuwelijkOfGpInhoud eersteRij = iterator.next().getInhoud();
            final Lo3Datum eersteOntbindingsDatum = eersteRij.getDatumOntbindingHuwelijkOfGp();
            final Lo3GemeenteCode eersteOntbindingsGemeente = eersteRij.getGemeenteCodeOntbindingHuwelijkOfGp();
            final Lo3LandCode eersteOntbindingsLand = eersteRij.getLandCodeOntbindingHuwelijkOfGp();
            final Lo3RedenOntbindingHuwelijkOfGpCode eersteOntbindingsReden = eersteRij.getRedenOntbindingHuwelijkOfGpCode();

            while (iterator.hasNext()) {
                final Lo3HuwelijkOfGpInhoud volgendeRij = iterator.next().getInhoud();
                if (!isEqualNullable(eersteOntbindingsDatum, volgendeRij.getDatumOntbindingHuwelijkOfGp())
                    || !isEqualNullable(eersteOntbindingsGemeente, volgendeRij.getGemeenteCodeOntbindingHuwelijkOfGp())
                    || !isEqualNullable(eersteOntbindingsLand, volgendeRij.getLandCodeOntbindingHuwelijkOfGp())
                    || !isEqualNullable(eersteOntbindingsReden, volgendeRij.getRedenOntbindingHuwelijkOfGpCode()))
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private boolean isEqualNullable(final Object eersteSluitingsDatum, final Object datumSluitingHuwelijkOfAangaanGp) {
        return eersteSluitingsDatum == null
               && datumSluitingHuwelijkOfAangaanGp == null
               || eersteSluitingsDatum != null
               && eersteSluitingsDatum.equals(datumSluitingHuwelijkOfAangaanGp);
    }

    /**
     * Kijkt in de stapel naar de meest actuele categorie waarvoor de soort verbintenis is gevuld.
     *
     * @param huwelijkOfGpStapel
     *            de stapel waarin moet worden gezocht naar de soort verbintenis
     * @return de soort verbintenis
     */
    private Lo3SoortVerbintenis bepaalSoortVerbintenis(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel) {
        Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGp = huwelijkOfGpStapel.getLaatsteElement();
        while (huwelijkOfGp != null) {
            if (huwelijkOfGp.getInhoud().getSoortVerbintenis() != null) {
                return huwelijkOfGp.getInhoud().getSoortVerbintenis();
            }
            huwelijkOfGp = huwelijkOfGpStapel.getVorigElement(huwelijkOfGp);
        }
        return null;
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

        final Lo3HuwelijkOfGpInhoud ontbindingInhoud =
                new Lo3HuwelijkOfGpInhoud.Builder().datumOntbindingHuwelijkOfGp(overlijdenInhoud.getDatum())
                                                   .gemeenteCodeOntbindingHuwelijkOfGp(overlijdenInhoud.getGemeenteCode())
                                                   .landCodeOntbindingHuwelijkOfGp(overlijdenInhoud.getLandCode())
                                                   .redenOntbindingHuwelijkOfGpCode(Lo3RedenOntbindingHuwelijkOfGpCode.OVERLIJDEN)
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

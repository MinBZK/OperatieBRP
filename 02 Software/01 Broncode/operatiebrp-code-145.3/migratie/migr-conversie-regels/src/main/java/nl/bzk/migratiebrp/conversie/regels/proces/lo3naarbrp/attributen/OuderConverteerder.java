/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGezagsVerhoudingGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Deze class bevat de logica om een LO3 Ouder (zowel Ouder1 als Ouder2) te converteren naar BRP relaties, betrokkenen
 * en gerelateerde personen.
 */
@Requirement({Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR001})
public class OuderConverteerder extends AbstractRelatieConverteerder {

    /**
     * Constructor voor deze converteerder
     * @param lo3AttribuutConverteerder de lo3 attributen converteerder
     */
    public OuderConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        super(lo3AttribuutConverteerder);
    }

    /**
     * Converteert de LO3 Ouder stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * Als isDummyPL true is, dan wordt er niets geconverteerd.
     * @param ouder1Stapel de stapels voor Ouder 1, mag niet null en niet leeg zijn
     * @param ouder2Stapel de stapels voor Ouder 2, mag niet null en niet leeg zijn
     * @param gezagsverhoudingStapel de stapel voor Gezagsverhouding, mag null zijn
     * @param isDummyPL geeft aan of de PL een dummy PL is
     * @param tussenPersoonslijstBuilder de migratie persoonslijst builder
     */
    public final void converteer(
            final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel,
            final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel,
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final boolean isDummyPL,
            final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        if (isDummyPL) {
            return;
        }

        final List<TussenBetrokkenheid> betrokkenheidStapels = new LinkedList<>();
        // Inhoudelijke BRP gegevens
        betrokkenheidStapels.addAll(maakBetrokkenhedenVoorOuders(ouder1Stapel, gezagsverhoudingStapel, OuderType.OUDER_1));
        betrokkenheidStapels.addAll(maakBetrokkenhedenVoorOuders(ouder2Stapel, gezagsverhoudingStapel, OuderType.OUDER_2));

        controleerBijzondereSituatieLB042(gezagsverhoudingStapel);

        // IST gegevens
        final TussenStapel<BrpIstRelatieGroepInhoud> istOuder1Stapel = converteerOuderStapel(ouder1Stapel);
        final TussenStapel<BrpIstRelatieGroepInhoud> istOuder2Stapel = converteerOuderStapel(ouder2Stapel);
        final TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> istGezagsVerhoudingStapels = converteerGezagsVerhoudingStapel(gezagsverhoudingStapel);

        final TussenRelatie.Builder relatieBuilder =
                new TussenRelatie.Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND);

        relatieBuilder.betrokkenheden(betrokkenheidStapels);
        relatieBuilder.istOuder1(istOuder1Stapel);
        relatieBuilder.istOuder2(istOuder2Stapel);
        relatieBuilder.istGezagsverhouding(istGezagsVerhoudingStapels);

        tussenPersoonslijstBuilder.relatie(relatieBuilder.build());
        tussenPersoonslijstBuilder.istOuder1(istOuder1Stapel);
        tussenPersoonslijstBuilder.istOuder2(istOuder2Stapel);
        tussenPersoonslijstBuilder.istGezagsverhouding(istGezagsVerhoudingStapels);
    }

    private List<TussenBetrokkenheid> maakBetrokkenhedenVoorOuders(
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel,
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final OuderType ouderType) {
        final List<TussenBetrokkenheid> betrokkenheidStapels = new LinkedList<>();
        for (final OuderRelatie ouderRelatie : Lo3SplitsenGerelateerdeOuders.splitsOuders(ouderStapel)) {
            if (!ouderRelatie.isJuridischGeenOuder()) {
                betrokkenheidStapels.add(maakMigratieBetrokkenheidStapel(ouderRelatie, ouderType, gezagsverhoudingStapel));
            }
        }
        return betrokkenheidStapels;
    }

    private TussenBetrokkenheid maakMigratieBetrokkenheidStapel(
            final OuderRelatie ouderRelatie,
            final OuderType ouderType,
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel) {
        if (!ouderRelatie.moetOpgenomenWorden()) {
            // formeel beeindigde relatie of geen gerelateerde gegevens. Niet opnemen.
            return null;
        }

        return converteerRelatie(ouderType, gezagsverhoudingStapel, ouderRelatie);
    }

    private TussenBetrokkenheid converteerRelatie(
            final OuderType ouderType,
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final OuderRelatie ouderRelatie) {
        // GROEPEN
        final Lo3Categorie<Lo3OuderInhoud> gerelateerdeGegevens = ouderRelatie.getGerelateerdeGegevens();
        final List<TussenGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepenOuder = migreerIdentificatienummersGroep(gerelateerdeGegevens);
        final List<TussenGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepenOuder = migreerGeslachtsaanduidingGroep(gerelateerdeGegevens);
        final List<TussenGroep<BrpGeboorteInhoud>> geboorteGroepenOuder = migreerGeboorteGroep(gerelateerdeGegevens);
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamGroepenOuder = migreerSamengesteldeNaamGroep(gerelateerdeGegevens);

        final List<TussenGroep<BrpOuderInhoud>> ouderGroepenOuder = migreerOuderGroep(ouderRelatie);
        final List<TussenGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezag =
                migreerOuderlijkGezagStapel(
                        gezagsverhoudingStapel,
                        ouderType,
                        ouderRelatie.getTeGebruikenRecord().getInhoud().getFamilierechtelijkeBetrekking(),
                        ouderRelatie.getBeeindigingsRecord() != null ? ouderRelatie.getBeeindigingsRecord().getHistorie().getIngangsdatumGeldigheid() : null);

        // STAPELS
        final TussenStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
        if (identificatienummersGroepenOuder.isEmpty()) {
            identificatienummersStapel = null;
        } else {
            identificatienummersStapel = new TussenStapel<>(identificatienummersGroepenOuder);
        }
        final TussenStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel;
        if (geslachtsaanduidingGroepenOuder.isEmpty()) {
            geslachtsaanduidingStapel = null;
        } else {
            geslachtsaanduidingStapel = new TussenStapel<>(geslachtsaanduidingGroepenOuder);
        }
        final TussenStapel<BrpGeboorteInhoud> geboorteStapel;
        if (geboorteGroepenOuder.isEmpty()) {
            geboorteStapel = null;
        } else {
            geboorteStapel = new TussenStapel<>(geboorteGroepenOuder);
        }
        final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
        if (samengesteldeNaamGroepenOuder.isEmpty()) {
            samengesteldeNaamStapel = null;
        } else {
            samengesteldeNaamStapel = new TussenStapel<>(samengesteldeNaamGroepenOuder);
        }
        final TussenStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel;
        if (ouderlijkGezag.isEmpty()) {
            ouderlijkGezagStapel = null;
        } else {
            ouderlijkGezagStapel = new TussenStapel<>(ouderlijkGezag);
        }
        final TussenStapel<BrpOuderInhoud> ouderStapel2 = ouderGroepenOuder.isEmpty() ? null : new TussenStapel<>(ouderGroepenOuder);

        // BETROKKENHEID
        return new TussenBetrokkenheid(
                BrpSoortBetrokkenheidCode.OUDER,
                identificatienummersStapel,
                geslachtsaanduidingStapel,
                geboorteStapel,
                ouderlijkGezagStapel,
                samengesteldeNaamStapel,
                ouderStapel2);
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB042)
    private void controleerBijzondereSituatieLB042(final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel) {
        if (gezagsverhoudingStapel != null) {
            gezagsverhoudingStapel.forEach(voorkomen -> {
                if (!voorkomen.getHistorie().isOnjuist() && (!voorkomen.getInhoud().isVoorkomenGebruiktVoorOuder1() || !voorkomen.getInhoud()
                        .isVoorkomenGebruiktVoorOuder2())) {
                    Foutmelding.logMeldingFout(voorkomen.getLo3Herkomst(), LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB042, null);
                }
            });
        }
    }

    @Definitie({Definities.DEF027, Definities.DEF028, Definities.DEF029, Definities.DEF030, Definities.DEF031, Definities.DEF032})
    private List<TussenGroep<BrpOuderlijkGezagInhoud>> migreerOuderlijkGezagStapel(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final OuderType ouderType,
            final Lo3Datum ingangsdatumOuderrelatie,
            final Lo3Datum einddatumOuderrelatie) {
        if (gezagsverhoudingStapel == null) {
            return Collections.emptyList();
        }
        final List<TussenGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezagLijst = new ArrayList<>();

        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding : gezagsverhoudingStapel) {
            if (!gezagsverhouding.getHistorie().isOnjuist()) {
                migreerOuderlijkGezagVoorkomen(gezagsverhouding, ouderlijkGezagLijst, ouderType, ingangsdatumOuderrelatie, einddatumOuderrelatie);
            }
        }
        return ouderlijkGezagLijst;
    }

    private void migreerOuderlijkGezagVoorkomen(final Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding,
                                                List<TussenGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezagLijst, final OuderType ouderType,
                                                final Lo3Datum ingangsdatumOuderrelatie, final Lo3Datum einddatumOuderrelatie) {
        final Lo3Datum gezagDatum = gezagsverhouding.getHistorie().getIngangsdatumGeldigheid();
        final boolean isNaIngang = isNaIngang(ingangsdatumOuderrelatie, gezagDatum);
        final boolean isVoorEinde = isVoorEinde(einddatumOuderrelatie, gezagDatum);
        final Lo3GezagsverhoudingInhoud inhoud = gezagsverhouding.getInhoud();

        final BrpBoolean ouderHeeftGezag = bepaalOuderHeeftGezag(inhoud, ouderType);
        if (isNaIngang && isVoorEinde) {
            ouderlijkGezagLijst.add(
                    new TussenGroep<>(
                            new BrpOuderlijkGezagInhoud(ouderHeeftGezag),
                            gezagsverhouding.getHistorie(),
                            gezagsverhouding.getDocumentatie(),
                            gezagsverhouding.getLo3Herkomst()));
            setVoorkomenIsGebruikt(inhoud, ouderType);
        } else if (!ouderlijkGezagLijst.isEmpty() && (isNaIngang || (gezagsverhouding.getLo3Herkomst().isLo3ActueelVoorkomen() && gezagDatum
                .equalsWaarde(new Lo3Datum(0))))) {
            // maak afsluitende rij voor de stapel als er al een ouderlijk gezag is EN
            // - de gezagdatum na de ingangsdatum ouder ligt OF
            // - Het de actuele rij betreft en de gezagdatum onbekend is
            ouderlijkGezagLijst.add(
                    new TussenGroep<>(
                            new BrpOuderlijkGezagInhoud(null),
                            gezagsverhouding.getHistorie(),
                            gezagsverhouding.getDocumentatie(),
                            gezagsverhouding.getLo3Herkomst()));
            if (ouderHeeftGezag == null) {
                setVoorkomenIsGebruikt(inhoud, ouderType);
            }
        }
    }

    private void setVoorkomenIsGebruikt(final Lo3GezagsverhoudingInhoud inhoud, final OuderType ouderType) {
        if (ouderType == OuderType.OUDER_1) {
            inhoud.setVoorkomenGebruiktVoorOuder1();
        } else {
            inhoud.setVoorkomenGebruiktVoorOuder2();
        }
    }

    private boolean isVoorEinde(final Lo3Datum einddatumOuderrelatie, final Lo3Datum gezagDatum) {
        return !Lo3Validatie.isElementGevuld(einddatumOuderrelatie)
                || Lo3Validatie.isElementGevuld(gezagDatum) && einddatumOuderrelatie.compareTo(gezagDatum) > 0;
    }

    private boolean isNaIngang(final Lo3Datum ingangsdatumOuderrelatie, final Lo3Datum gezagDatum) {
        return Lo3Validatie.isElementGevuld(ingangsdatumOuderrelatie)
                && Lo3Validatie.isElementGevuld(gezagDatum)
                && ingangsdatumOuderrelatie.compareTo(gezagDatum) <= 0;
    }

    private BrpBoolean bepaalOuderHeeftGezag(final Lo3GezagsverhoudingInhoud inhoud, final OuderType ouderType) {
        final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige = inhoud.getIndicatieGezagMinderjarige();
        final BrpBoolean ouderHeeftGezag;

        if (ouderType == OuderType.OUDER_1) {
            ouderHeeftGezag = getLo3AttribuutConverteerder().converteerOuder1HeeftGezag(indicatieGezagMinderjarige);
        } else {
            ouderHeeftGezag = getLo3AttribuutConverteerder().converteerOuder2HeeftGezag(indicatieGezagMinderjarige);
        }
        return ouderHeeftGezag;
    }

    private List<TussenGroep<BrpGeboorteInhoud>> migreerGeboorteGroep(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final List<TussenGroep<BrpGeboorteInhoud>> result = new ArrayList<>();

        if (!ouder.getInhoud().isOnbekendeOuder()) {
            final Lo3OuderInhoud inhoud = ouder.getInhoud();
            result.add(
                    getUtils().maakGeboorteGroep(
                            inhoud.getGeboorteGemeenteCode(),
                            inhoud.getGeboorteLandCode(),
                            inhoud.getGeboortedatum(),
                            ouder.getHistorie(),
                            ouder.getDocumentatie(),
                            ouder.getLo3Herkomst()));
        }

        return result;
    }

    private List<TussenGroep<BrpGeslachtsaanduidingInhoud>> migreerGeslachtsaanduidingGroep(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final List<TussenGroep<BrpGeslachtsaanduidingInhoud>> result = new ArrayList<>();

        if (!ouder.getInhoud().isOnbekendeOuder()) {
            final Lo3OuderInhoud inhoud = ouder.getInhoud();

            result.add(
                    getUtils().maakGeslachtsaanduidingInhoud(
                            inhoud.getGeslachtsaanduiding(),
                            ouder.getHistorie(),
                            ouder.getDocumentatie(),
                            ouder.getLo3Herkomst()));
        }

        return result;
    }

    private List<TussenGroep<BrpIdentificatienummersInhoud>> migreerIdentificatienummersGroep(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final List<TussenGroep<BrpIdentificatienummersInhoud>> result = new ArrayList<>();

        if (!ouder.getInhoud().isOnbekendeOuder()) {
            final Lo3OuderInhoud inhoud = ouder.getInhoud();
            result.add(
                    getUtils().maakIdentificatieGroep(
                            inhoud.getaNummer(),
                            inhoud.getBurgerservicenummer(),
                            ouder.getHistorie(),
                            ouder.getDocumentatie(),
                            ouder.getLo3Herkomst()));
        }

        return result;
    }

    private List<TussenGroep<BrpSamengesteldeNaamInhoud>> migreerSamengesteldeNaamGroep(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> result = new ArrayList<>();

        if (!ouder.getInhoud().isOnbekendeOuder()) {
            final Lo3OuderInhoud inhoud = ouder.getInhoud();
            result.add(
                    getUtils().maakSamengesteldeNaamGroep(
                            inhoud.getAdellijkeTitelPredikaatCode(),
                            inhoud.getVoornamen(),
                            inhoud.getVoorvoegselGeslachtsnaam(),
                            inhoud.getGeslachtsnaam(),
                            ouder.getHistorie(),
                            ouder.getDocumentatie(),
                            ouder.getLo3Herkomst()));
        }

        return result;
    }

    private static List<TussenGroep<BrpOuderInhoud>> migreerOuderGroep(final OuderRelatie ouderRelatie) {
        final List<TussenGroep<BrpOuderInhoud>> result = new ArrayList<>();

        final Lo3Categorie<Lo3OuderInhoud> ouderCategorie = ouderRelatie.getTeGebruikenRecord();

        // Migreer 62.10 'Datum familierechtelijke betrekking' naar historie als datum aanvang.
        final Lo3Datum datumFamilierechtelijkeBetrekking = ouderCategorie.getInhoud().getFamilierechtelijkeBetrekking();
        final Lo3Historie aanvangHistorie =
                new Lo3Historie(
                        ouderCategorie.getHistorie().getIndicatieOnjuist(),
                        datumFamilierechtelijkeBetrekking,
                        ouderCategorie.getHistorie().getDatumVanOpneming());

        result.add(new TussenGroep<>(new BrpOuderInhoud(null), aanvangHistorie, ouderCategorie.getDocumentatie(), ouderCategorie.getLo3Herkomst()));

        final Lo3Categorie<Lo3OuderInhoud> eindeCategorie = ouderRelatie.getBeeindigingsRecord();
        if (eindeCategorie != null) {
            // Geen migratie van 62.10 nodig, dat is al gebeurd bij het splitsen
            result.add(
                    new TussenGroep<>(
                            BrpOuderInhoud.maakLegeInhoud(),
                            eindeCategorie.getHistorie(),
                            eindeCategorie.getDocumentatie(),
                            eindeCategorie.getLo3Herkomst(),
                            eindeCategorie.isAfsluitendVoorkomen(),
                            false));
        }

        return result;
    }

    private TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> converteerGezagsVerhoudingStapel(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsVerhoudingStapel) {
        if (gezagsVerhoudingStapel == null) {
            return null;
        }
        final List<TussenGroep<BrpIstGezagsVerhoudingGroepInhoud>> istGezagsVerhoudingGroepen = new ArrayList<>();
        for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorie : gezagsVerhoudingStapel.getCategorieen()) {
            final Lo3GezagsverhoudingInhoud lo3Inhoud = categorie.getInhoud();
            final Lo3Documentatie lo3Documentatie = categorie.getDocumentatie();
            final Lo3Onderzoek lo3Onderzoek = categorie.getOnderzoek();
            final Lo3Historie lo3Historie = categorie.getHistorie();
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();

            final BrpIstGezagsVerhoudingGroepInhoud istGezagsVerhoudingGroepInhoud =
                    maakIstGezagsVerhoudingGroepInhoud(lo3Inhoud, lo3Documentatie, lo3Onderzoek, lo3Historie, lo3Herkomst);
            istGezagsVerhoudingGroepen.add(maakMigratieGroep(istGezagsVerhoudingGroepInhoud));

        }
        return new TussenStapel<>(istGezagsVerhoudingGroepen);
    }

    private TussenStapel<BrpIstRelatieGroepInhoud> converteerOuderStapel(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        if (ouderStapel == null) {
            return null;
        }

        final List<TussenGroep<BrpIstRelatieGroepInhoud>> istOuderGroepen = new ArrayList<>();

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : ouderStapel.getCategorieen()) {
            final Lo3OuderInhoud lo3Inhoud = categorie.getInhoud();
            final Lo3Documentatie lo3Documentatie = categorie.getDocumentatie();
            final Lo3Onderzoek lo3Onderzoek = categorie.getOnderzoek();
            final Lo3Historie lo3Historie = categorie.getHistorie();
            final Lo3Herkomst lo3Herkomst = categorie.getLo3Herkomst();

            final BrpIstRelatieGroepInhoud istOuderGroepInhoud =
                    maakIstRelatieGroepInhoud(
                            lo3Inhoud.getAdellijkeTitelPredikaatCode(),
                            lo3Inhoud.getVoornamen(),
                            lo3Inhoud.getVoorvoegselGeslachtsnaam(),
                            lo3Inhoud.getGeslachtsnaam(),
                            lo3Inhoud.getGeboorteGemeenteCode(),
                            lo3Inhoud.getGeboorteLandCode(),
                            lo3Inhoud.getGeboortedatum(),
                            lo3Inhoud.getGeslachtsaanduiding(),
                            lo3Inhoud.getaNummer(),
                            lo3Inhoud.getBurgerservicenummer(),
                            lo3Inhoud.getFamilierechtelijkeBetrekking(),
                            lo3Documentatie,
                            lo3Onderzoek,
                            lo3Historie,
                            lo3Herkomst);
            istOuderGroepen.add(maakMigratieGroep(istOuderGroepInhoud));
        }

        return new TussenStapel<>(istOuderGroepen);
    }

    /**
     * Enum type voor de soorten Ouders (Ouder1 en Ouder2).
     */
    private enum OuderType {
        OUDER_1, OUDER_2
    }
}

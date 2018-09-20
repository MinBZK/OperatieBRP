/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGezagMinderjarigeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieRelatie;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.Lo3PlaatsLandConversieHelper.BrpPlaatsLand;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 Ouder (zowel Ouder1 als Ouder2) te converteren naar BRP relaties, betrokkenen
 * en gerelateerde personen.
 */
// CHECKSTYLE:OFF - Fan-out complexity - geaccepteerd
@Component
@Requirement({ Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR001 })
public class OuderConverteerder {
    // CHECKSTYLE:ON

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Ouder stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * 
     * @param ouder1Stapels
     *            de stapels voor Ouder 1, mag niet null en niet leeg zijn
     * @param ouder2Stapels
     *            de stapels voor Ouder 2, mag niet null en niet leeg zijn
     * @param gezagsverhoudingStapel
     *            de stapel voor Gezagsverhouding, mag null zijn
     * @param persoon
     *            de Lo3Persoon die geconverteerd wordt, mag niet null en niet leeg zijn
     * @param migratiePersoonslijstBuilder
     *            de migratie persoonslijst builder
     */
    public final void converteer(
            final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels,
            final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels,
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final Lo3Categorie<Lo3PersoonInhoud> persoon,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {

        final List<MigratieBetrokkenheid> betrokkenheidStapels = new ArrayList<MigratieBetrokkenheid>();

        for (final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel : ouder1Stapels) {
            // ouder1 betrokkenheid
            if (!bevatAlleenJuridischGeenOuder(ouder1Stapel)) {
                betrokkenheidStapels.add(maakMigratieBetrokkenheidStapel(ouder1Stapel, OuderType.OUDER_1,
                        gezagsverhoudingStapel));
            }
        }

        for (final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel : ouder2Stapels) {
            // ouder 2 betrokkenheid
            if (!bevatAlleenJuridischGeenOuder(ouder2Stapel)) {
                betrokkenheidStapels.add(maakMigratieBetrokkenheidStapel(ouder2Stapel, OuderType.OUDER_2,
                        gezagsverhoudingStapel));
            }
        }

        if (!betrokkenheidStapels.isEmpty()) {
            migratiePersoonslijstBuilder.relatie(new MigratieRelatie(
                    BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.KIND,
                    betrokkenheidStapels, null));
        }
    }

    private MigratieBetrokkenheid maakMigratieBetrokkenheidStapel(
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel,
            final OuderType ouderType,
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel) {
        // GROEPEN
        final List<MigratieGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepenOuder =
                migreerIdentificatienummersGroepen(ouderStapel);
        final List<MigratieGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepenOuder =
                migreerGeslachtsaanduidingGroepen(ouderStapel);
        final List<MigratieGroep<BrpGeboorteInhoud>> geboorteGroepenOuder = migreerGeboorteGroepen(ouderStapel);
        final List<MigratieGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamGroepenOuder =
                migreerSamengesteldeNaamGroepen(ouderStapel);
        final List<MigratieGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezag =
                migreerOuderlijkGezag(gezagsverhoudingStapel, ouderStapel, ouderType);
        final List<MigratieGroep<BrpOuderInhoud>> ouderGroepenOuder = migreerOuderGroepen(ouderStapel);

        // STAPELS
        final MigratieStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                identificatienummersGroepenOuder.isEmpty() ? null
                        : new MigratieStapel<BrpIdentificatienummersInhoud>(identificatienummersGroepenOuder);
        final MigratieStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel =
                geslachtsaanduidingGroepenOuder.isEmpty() ? null : new MigratieStapel<BrpGeslachtsaanduidingInhoud>(
                        geslachtsaanduidingGroepenOuder);
        final MigratieStapel<BrpGeboorteInhoud> geboorteStapel =
                geboorteGroepenOuder.isEmpty() ? null : new MigratieStapel<BrpGeboorteInhoud>(geboorteGroepenOuder);
        final MigratieStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel =
                samengesteldeNaamGroepenOuder.isEmpty() ? null : new MigratieStapel<BrpSamengesteldeNaamInhoud>(
                        samengesteldeNaamGroepenOuder);
        final MigratieStapel<BrpOuderlijkGezagInhoud> ouderlijkGezagStapel =
                ouderlijkGezag.isEmpty() ? null : new MigratieStapel<BrpOuderlijkGezagInhoud>(ouderlijkGezag);
        final MigratieStapel<BrpOuderInhoud> ouderStapel2 =
                ouderGroepenOuder.isEmpty() ? null : new MigratieStapel<BrpOuderInhoud>(ouderGroepenOuder);

        // BETROKKENHEID
        return new MigratieBetrokkenheid(BrpSoortBetrokkenheidCode.OUDER, identificatienummersStapel,
                geslachtsaanduidingStapel, geboorteStapel, ouderlijkGezagStapel, samengesteldeNaamStapel,
                ouderStapel2);
    }

    @Definitie({ Definities.DEF027, Definities.DEF028, Definities.DEF029, Definities.DEF030, Definities.DEF031,
            Definities.DEF032 })
    private static List<MigratieGroep<BrpOuderlijkGezagInhoud>> migreerOuderlijkGezag(
            final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel,
            final OuderType ouderType) {
        final List<MigratieGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezagLijst =
                new ArrayList<MigratieGroep<BrpOuderlijkGezagInhoud>>();
        if (gezagsverhoudingStapel != null) {
            for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding : gezagsverhoudingStapel) {
                ouderlijkGezagLijst.add(new MigratieGroep<BrpOuderlijkGezagInhoud>(new BrpOuderlijkGezagInhoud(
                        ouderType.heeftOuderlijkGezag(gezagsverhouding.getInhoud().getIndicatieGezagMinderjarige())),
                        gezagsverhouding.getHistorie(), gezagsverhouding.getDocumentatie(), gezagsverhouding
                                .getLo3Herkomst()));
            }
        }
        return ouderlijkGezagLijst;
    }

    /* Private methods */

    private List<MigratieGroep<BrpGeboorteInhoud>>
            migreerGeboorteGroepen(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        final List<MigratieGroep<BrpGeboorteInhoud>> geboorteGroepen =
                new ArrayList<MigratieGroep<BrpGeboorteInhoud>>();
        for (final Lo3Categorie<Lo3OuderInhoud> ouder : ouderStapel) {
            if (!ouder.getInhoud().isJurischeGeenOuder() && !ouder.getInhoud().isOnbekendeOuder()) {
                geboorteGroepen.add(migreerGeboorte(ouder));
            }
        }
        return geboorteGroepen;
    }

    private List<MigratieGroep<BrpGeslachtsaanduidingInhoud>> migreerGeslachtsaanduidingGroepen(
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        final List<MigratieGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepen =
                new ArrayList<MigratieGroep<BrpGeslachtsaanduidingInhoud>>();
        for (final Lo3Categorie<Lo3OuderInhoud> ouder : ouderStapel) {
            if (!ouder.getInhoud().isJurischeGeenOuder() && !ouder.getInhoud().isOnbekendeOuder()) {
                geslachtsaanduidingGroepen.add(migreerGeslachsaanduiding(ouder));
            }
        }
        return geslachtsaanduidingGroepen;
    }

    private static List<MigratieGroep<BrpIdentificatienummersInhoud>> migreerIdentificatienummersGroepen(
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        final List<MigratieGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepen =
                new ArrayList<MigratieGroep<BrpIdentificatienummersInhoud>>();
        for (final Lo3Categorie<Lo3OuderInhoud> ouder : ouderStapel) {
            if (!ouder.getInhoud().isJurischeGeenOuder() && !ouder.getInhoud().isOnbekendeOuder()) {
                identificatienummersGroepen.add(migreerIdentificatienummers(ouder));
            }
        }
        return identificatienummersGroepen;
    }

    private List<MigratieGroep<BrpSamengesteldeNaamInhoud>> migreerSamengesteldeNaamGroepen(
            final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        final List<MigratieGroep<BrpSamengesteldeNaamInhoud>> groepen =
                new ArrayList<MigratieGroep<BrpSamengesteldeNaamInhoud>>();
        for (final Lo3Categorie<Lo3OuderInhoud> ouder : ouderStapel) {
            if (!ouder.getInhoud().isJurischeGeenOuder() && !ouder.getInhoud().isOnbekendeOuder()) {
                groepen.add(migreerSamengesteldeNaam(ouder));
            }
        }
        return groepen;
    }

    private static List<MigratieGroep<BrpOuderInhoud>>
            migreerOuderGroepen(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        final List<MigratieGroep<BrpOuderInhoud>> betrokkenheidGroepen =
                new ArrayList<MigratieGroep<BrpOuderInhoud>>();
        if (!bevatAlleenJuridischGeenOuder(ouderStapel)) {
            for (final Lo3Categorie<Lo3OuderInhoud> ouder : ouderStapel) {
                final MigratieGroep<BrpOuderInhoud> migratieOuder = migreerOuder(ouder);
                if (migratieOuder != null) {
                    betrokkenheidGroepen.add(migratieOuder);
                }
            }
        }
        return betrokkenheidGroepen;
    }

    private static MigratieGroep<BrpOuderInhoud> migreerOuder(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final Lo3OuderInhoud inhoud = ouder.getInhoud();

        final boolean isEinde = inhoud.getFamilierechtelijkeBetrekking() == null;

        final MigratieGroep<BrpOuderInhoud> result;
        if (isEinde) {
            result =
                    new MigratieGroep<BrpOuderInhoud>(new BrpOuderInhoud(null, null), ouder.getHistorie(),
                            ouder.getDocumentatie(), ouder.getLo3Herkomst());
        } else {
            final Lo3Datum datumFamilierechtelijkeBetrekking = inhoud.getFamilierechtelijkeBetrekking();
            final Lo3Datum datumIngang = ouder.getHistorie().getIngangsdatumGeldigheid();

            // Als we de null datum niet vergelijken gaat een hoop fout. Zie MIG-716
            if (datumIngang.equals(datumFamilierechtelijkeBetrekking) || datumIngang.equals(Lo3Datum.NULL_DATUM)) {
                final Lo3Historie historie =
                        new Lo3Historie(ouder.getHistorie().getIndicatieOnjuist(), datumFamilierechtelijkeBetrekking,
                                ouder.getHistorie().getDatumVanOpneming());

                result =
                        new MigratieGroep<BrpOuderInhoud>(new BrpOuderInhoud(true,
                                datumFamilierechtelijkeBetrekking.converteerNaarBrpDatum()), historie,
                                ouder.getDocumentatie(), ouder.getLo3Herkomst());
            } else {
                // TODO mogelijk liever melden dat sanity check faalde ipv gewoon verder gaan met een null result.
                // Zie issue ORANJE-242
                result = null;
            }
        }
        return result;

    }

    private static boolean bevatAlleenJuridischGeenOuder(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        for (final Lo3Categorie<Lo3OuderInhoud> ouder : ouderStapel) {
            if (!ouder.getInhoud().isJurischeGeenOuder()) {
                return false;
            }
        }
        return true;
    }

    private static MigratieGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(
            final Lo3Categorie<Lo3OuderInhoud> ouder) {
        return new MigratieGroep<BrpIdentificatienummersInhoud>(new BrpIdentificatienummersInhoud(ouder.getInhoud()
                .getaNummer(), ouder.getInhoud().getBurgerservicenummer()), ouder.getHistorie(),
                ouder.getDocumentatie(), ouder.getLo3Herkomst());
    }

    private MigratieGroep<BrpSamengesteldeNaamInhoud> migreerSamengesteldeNaam(
            final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final Lo3OuderInhoud inhoud = ouder.getInhoud();

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
                geslachtsnaam, false, false), ouder.getHistorie(), ouder.getDocumentatie(), ouder.getLo3Herkomst());
    }

    private MigratieGroep<BrpGeslachtsaanduidingInhoud> migreerGeslachsaanduiding(
            final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final MigratieGroep<BrpGeslachtsaanduidingInhoud> migratieGeslachtsaanduiding =
                new MigratieGroep<BrpGeslachtsaanduidingInhoud>(new BrpGeslachtsaanduidingInhoud(
                        converteerder.converteerLo3Geslachtsaanduiding(ouder.getInhoud().getGeslachtsaanduiding())),
                        ouder.getHistorie(), ouder.getDocumentatie(), ouder.getLo3Herkomst());
        return migratieGeslachtsaanduiding;
    }

    private MigratieGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final BrpPlaatsLand brpPlaatsLand =
                new Lo3PlaatsLandConversieHelper(ouder.getInhoud().getGeboorteGemeenteCode(), ouder.getInhoud()
                        .getGeboorteLandCode(), converteerder).converteerNaarBrp();

        final BrpDatum brpGeboortedatum = converteerder.converteerDatum(ouder.getInhoud().getGeboortedatum());

        final MigratieGroep<BrpGeboorteInhoud> migratieGeboorte =
                new MigratieGroep<BrpGeboorteInhoud>(new BrpGeboorteInhoud(brpGeboortedatum,
                        brpPlaatsLand.getBrpGemeenteCode(), brpPlaatsLand.getBrpPlaatsCode(),
                        brpPlaatsLand.getBrpBuitenlandsePlaats(), brpPlaatsLand.getBrpBuitenlandseRegio(),
                        brpPlaatsLand.getBrpLandCode(), brpPlaatsLand.getBrpOmschrijvingLocatie()),
                        ouder.getHistorie(), ouder.getDocumentatie(), ouder.getLo3Herkomst());
        return migratieGeboorte;
    }

    /**
     * Enum type voor de soorten Ouders (Ouder1 en Ouder2).
     */
    private enum OuderType {
        OUDER_1 {
            @Override
            public Boolean heeftOuderlijkGezag(final Lo3IndicatieGezagMinderjarige lo3IndicatieGezagMinderjarige) {
                if (lo3IndicatieGezagMinderjarige == null) {
                    return null;
                }

                return Lo3IndicatieGezagMinderjarigeEnum.OUDER_1.equalsElement(lo3IndicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDER_1_EN_DERDE
                                .equalsElement(lo3IndicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2
                                .equalsElement(lo3IndicatieGezagMinderjarige);
            }
        },
        OUDER_2 {
            @Override
            public Boolean heeftOuderlijkGezag(final Lo3IndicatieGezagMinderjarige lo3IndicatieGezagMinderjarige) {
                if (lo3IndicatieGezagMinderjarige == null) {
                    return null;
                }

                return Lo3IndicatieGezagMinderjarigeEnum.OUDER_2.equalsElement(lo3IndicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDER_2_EN_DERDE
                                .equalsElement(lo3IndicatieGezagMinderjarige)
                        || Lo3IndicatieGezagMinderjarigeEnum.OUDERS_1_EN_2
                                .equalsElement(lo3IndicatieGezagMinderjarige);
            }
        };

        /**
         * Bepaal op basis van de {@link Lo3IndicatieGezagMinderjarige} of deze ouder ouderlijk gezag heeft.
         * 
         * @param lo3IndicatieGezagMinderjarige
         *            de
         * @return true als de ouder Ouderlijk gezag heeft
         */
        abstract Boolean heeftOuderlijkGezag(Lo3IndicatieGezagMinderjarige lo3IndicatieGezagMinderjarige);
    }
}

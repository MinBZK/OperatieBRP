/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 Ouder (zowel Ouder1 als Ouder2) te converteren naar BRP relaties, betrokkenen
 * en gerelateerde personen.
 */
@Component
@Requirement({Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR001 })
public class OuderConverteerder extends AbstractRelatieConverteerder {

    /**
     * Converteert de LO3 Ouder stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * Als isDummyPL true is, dan wordt er niets geconverteerd.
     *
     * @param ouder1Stapel
     *            de stapels voor Ouder 1, mag niet null en niet leeg zijn
     * @param ouder2Stapel
     *            de stapels voor Ouder 2, mag niet null en niet leeg zijn
     * @param gezagsverhoudingStapel
     *            de stapel voor Gezagsverhouding, mag null zijn
     * @param isDummyPL
     *            geeft aan of de PL een dummy PL is
     * @param tussenPersoonslijstBuilder
     *            de migratie persoonslijst builder
     */
    public final void converteer(
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel,
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel,
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
        final boolean isDummyPL,
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder)
    {
        if (isDummyPL) {
            return;
        }

        final List<TussenBetrokkenheid> betrokkenheidStapels = new ArrayList<>();

        for (final Lo3Stapel<Lo3OuderInhoud> ouder1 : Lo3SplitsenGerelateerdeOuders.splitsOuders(ouder1Stapel)) {
            // ouder1 betrokkenheid
            if (!bevatAlleenJuridischGeenOuder(ouder1)) {
                betrokkenheidStapels.add(maakMigratieBetrokkenheidStapel(ouder1, OuderType.OUDER_1, gezagsverhoudingStapel));
            }
        }

        for (final Lo3Stapel<Lo3OuderInhoud> ouder2 : Lo3SplitsenGerelateerdeOuders.splitsOuders(ouder2Stapel)) {
            // ouder 2 betrokkenheid
            if (!bevatAlleenJuridischGeenOuder(ouder2)) {
                betrokkenheidStapels.add(maakMigratieBetrokkenheidStapel(ouder2, OuderType.OUDER_2, gezagsverhoudingStapel));
            }
        }

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

    private TussenBetrokkenheid maakMigratieBetrokkenheidStapel(
        final Lo3Stapel<Lo3OuderInhoud> ouderStapel,
        final OuderType ouderType,
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel)
    {

        final Lo3Categorie<Lo3OuderInhoud> gerelateerdeGegevens = bepaalGerelateerdeGegevensCategorie(ouderStapel);

        final Lo3Categorie<Lo3OuderInhoud> relatieGegevens = bepaalRelatieGegevensCategorie(ouderStapel);
        final Lo3Categorie<Lo3OuderInhoud> relatieEinde = bepaalRelatieEindeGegevensCategorie(ouderStapel);

        if (relatieGegevens == null || gerelateerdeGegevens == null) {
            // formeel beeindigde relatie of geen gerelateerde gegevens. Niet opnemen.
            return null;
        }

        return converteerRelatie(ouderType, gezagsverhoudingStapel, relatieGegevens, relatieEinde, gerelateerdeGegevens);
    }

    private TussenBetrokkenheid converteerRelatie(
        final OuderType ouderType,
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
        final Lo3Categorie<Lo3OuderInhoud> relatieGegevens,
        final Lo3Categorie<Lo3OuderInhoud> relatieEinde,
        final Lo3Categorie<Lo3OuderInhoud> gerelateerdeGegevens)
    {
        // GROEPEN
        final List<TussenGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepenOuder = migreerIdentificatienummersGroep(gerelateerdeGegevens);
        final List<TussenGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepenOuder = migreerGeslachtsaanduidingGroep(gerelateerdeGegevens);
        final List<TussenGroep<BrpGeboorteInhoud>> geboorteGroepenOuder = migreerGeboorteGroep(gerelateerdeGegevens);
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamGroepenOuder = migreerSamengesteldeNaamGroep(gerelateerdeGegevens);

        final List<TussenGroep<BrpOuderInhoud>> ouderGroepenOuder = migreerOuderGroep(relatieGegevens, relatieEinde);
        final List<TussenGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezag =
                migreerOuderlijkGezag(
                    gezagsverhoudingStapel,
                    ouderType,
                    relatieGegevens.getHistorie().getIngangsdatumGeldigheid(),
                    relatieEinde != null ? relatieEinde.getHistorie().getIngangsdatumGeldigheid() : null);

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

    private Lo3Categorie<Lo3OuderInhoud> bepaalRelatieGegevensCategorie(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        final List<Lo3Categorie<Lo3OuderInhoud>> gevuldeRijen = new ArrayList<>();
        for (final Lo3Categorie<Lo3OuderInhoud> ouderRij : ouderStapel) {
            // Als de rij onjuist of leeg is, dan wordt deze niet gebruikt voor relatiegegevens
            if (ouderRij.getHistorie().isOnjuist() || ouderRij.getInhoud().isLeeg()) {
                continue;
            }

            gevuldeRijen.add(ouderRij);
        }

        Lo3Categorie<Lo3OuderInhoud> relatieGegevensRij;

        // Zoek de te gebruiken actuele rij voor de conversie
        if (gevuldeRijen.size() == 0) {
            relatieGegevensRij = null;
        } else if (gevuldeRijen.size() == 1) {
            // 1. Er is maar 1 juiste rij. Gebruik die.
            relatieGegevensRij = gevuldeRijen.get(0);
        } else {
            // 2. zoek naar een unieke rij met geldigheid==sluitingsdatum
            relatieGegevensRij = zoekUniekeRij(gevuldeRijen);
        }

        // 4. In andere gevallen, zoek rij met meest recente geldigheid.
        if (relatieGegevensRij == null) {
            relatieGegevensRij = ouderStapel.getLo3ActueelVoorkomen();
            if (relatieGegevensRij == null) {
                relatieGegevensRij = zoekMeestRecenteRij(gevuldeRijen);
            }
        }
        return relatieGegevensRij;
    }

    private Lo3Categorie<Lo3OuderInhoud> zoekUniekeRij(final List<Lo3Categorie<Lo3OuderInhoud>> gevuldeRijen) {
        Lo3Categorie<Lo3OuderInhoud> relatieGegevensRij = null;
        Lo3Datum zoekDatum = null;
        for (final Lo3Categorie<Lo3OuderInhoud> ouderRij : gevuldeRijen) {
            if (!isLeegOfStandaard(ouderRij.getInhoud().getFamilierechtelijkeBetrekking())) {
                zoekDatum = ouderRij.getInhoud().getFamilierechtelijkeBetrekking();
                break;
            }
        }

        if (zoekDatum != null) {
            relatieGegevensRij = zoekUniekeGeldigheidsWaarde(zoekDatum, gevuldeRijen);
        }

        // 3. bij geen unieke rij met geldigheid==sluitingsdatum, zoek
        // een unieke rij met geldigheid==Standaardwaarde==00000000
        if (relatieGegevensRij == null) {
            relatieGegevensRij = zoekUniekeGeldigheidsWaarde(Lo3Datum.NULL_DATUM, gevuldeRijen);
        }
        return relatieGegevensRij;
    }

    private Lo3Categorie<Lo3OuderInhoud> bepaalRelatieEindeGegevensCategorie(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        // Als actueel voorkomen in de stapel voorkomt (actueelVoorkomen niet null) en deze is een niet-leeg voorkomen,
        // dan geen gegevens mbt materieel einde overnemen
        final Lo3Categorie<Lo3OuderInhoud> actueelVoorkomen = ouderStapel.getLo3ActueelVoorkomen();
        if (actueelVoorkomen != null && !actueelVoorkomen.getInhoud().isLeeg()) {
            return null;
        }

        final List<Lo3Categorie<Lo3OuderInhoud>> legeRijen = new ArrayList<>();
        for (final Lo3Categorie<Lo3OuderInhoud> ouderRij : ouderStapel) {
            // Als de rij onjuist of niet leeg is, dan wordt deze niet gebruikt voor relatie einde gegevens
            if (ouderRij.getHistorie().isOnjuist() || !ouderRij.getInhoud().isLeeg()) {
                continue;
            }

            legeRijen.add(ouderRij);
        }

        final Lo3Categorie<Lo3OuderInhoud> relatieGegevensRij;

        if (legeRijen.size() == 0) {
            relatieGegevensRij = null;
        } else if (legeRijen.size() == 1) {
            // Er is maar 1 juiste rij. Gebruik die.
            relatieGegevensRij = legeRijen.get(0);
        } else {
            // In andere gevallen, zoek rij met oudste geldigheid.
            relatieGegevensRij = zoekOudsteRij(legeRijen);
        }

        return relatieGegevensRij;
    }

    private Lo3Categorie<Lo3OuderInhoud> bepaalGerelateerdeGegevensCategorie(final Lo3Stapel<Lo3OuderInhoud> ouderStapel) {
        // Als actueel in de stapel voorkomt, dan is actueelVoorkomen niet null
        Lo3Categorie<Lo3OuderInhoud> gerelateerde = ouderStapel.getLo3ActueelVoorkomen();
        if (gerelateerde == null) {
            final List<Lo3Categorie<Lo3OuderInhoud>> gevuldeRijen = new ArrayList<>();
            for (final Lo3Categorie<Lo3OuderInhoud> ouderRij : ouderStapel) {
                // Als de rij onjuist of leeg is, dan wordt deze niet gebruikt voor gerelateerde gegevens
                if (ouderRij.getHistorie().isOnjuist() || ouderRij.getInhoud().isLeeg()) {
                    continue;
                }

                gevuldeRijen.add(ouderRij);
            }

            gerelateerde = zoekMeestRecenteRij(gevuldeRijen);
        }
        return gerelateerde;
    }

    private static boolean isLeegOfStandaard(final Lo3Datum datum) {
        return datum == null || datum.equals(Lo3Datum.NULL_DATUM);
    }

    private Lo3Categorie<Lo3OuderInhoud> zoekUniekeGeldigheidsWaarde(final Lo3Datum zoekDatum, final List<Lo3Categorie<Lo3OuderInhoud>> ouderRijen) {
        Lo3Categorie<Lo3OuderInhoud> result = null;

        for (final Lo3Categorie<Lo3OuderInhoud> rij : ouderRijen) {
            if (zoekDatum.equalsWaarde(rij.getHistorie().getIngangsdatumGeldigheid())) {
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

    private Lo3Categorie<Lo3OuderInhoud> zoekMeestRecenteRij(final List<Lo3Categorie<Lo3OuderInhoud>> ouderRijen) {
        Lo3Categorie<Lo3OuderInhoud> result = null;

        if (ouderRijen.size() > 0) {
            Collections.sort(ouderRijen, Lo3Categorie.DATUM_GELDIGHEID);
            result = ouderRijen.get(0);
        }

        return result;
    }

    private Lo3Categorie<Lo3OuderInhoud> zoekOudsteRij(final List<Lo3Categorie<Lo3OuderInhoud>> ouderRijen) {
        Lo3Categorie<Lo3OuderInhoud> result = null;

        if (ouderRijen.size() > 0) {
            Collections.sort(ouderRijen, Lo3Categorie.DATUM_GELDIGHEID);
            result = ouderRijen.get(ouderRijen.size() - 1);
        }

        return result;
    }

    @Definitie({Definities.DEF027, Definities.DEF028, Definities.DEF029, Definities.DEF030, Definities.DEF031, Definities.DEF032 })
    private List<TussenGroep<BrpOuderlijkGezagInhoud>> migreerOuderlijkGezag(
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsverhoudingStapel,
        final OuderType ouderType,
        final Lo3Datum ingangsdatumOuderrelatie,
        final Lo3Datum einddatumOuderrelatie)
    {
        final List<TussenGroep<BrpOuderlijkGezagInhoud>> ouderlijkGezagLijst = new ArrayList<>();

        if (gezagsverhoudingStapel != null) {
            for (final Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding : gezagsverhoudingStapel) {

                if (gezagsverhouding.getHistorie().isOnjuist()) {
                    // Onjuist wordt niet geconverteerd
                    continue;
                }

                final Lo3Datum gezagDatum = gezagsverhouding.getHistorie().getIngangsdatumGeldigheid();
                final boolean isNaIngang = isNaIngang(ingangsdatumOuderrelatie, gezagDatum);
                final boolean isVoorEinde = isVoorEinde(einddatumOuderrelatie, gezagDatum);

                if (isNaIngang && isVoorEinde) {
                    final BrpBoolean ouderHeeftGezag = bepaalOuderHeeftGezag(gezagsverhouding, ouderType);
                    ouderlijkGezagLijst.add(new TussenGroep<>(
                        new BrpOuderlijkGezagInhoud(ouderHeeftGezag),
                        gezagsverhouding.getHistorie(),
                        gezagsverhouding.getDocumentatie(),
                        gezagsverhouding.getLo3Herkomst()));
                } else if (isNaIngang || (gezagsverhouding.getLo3Herkomst().isLo3ActueelVoorkomen() && gezagDatum.equalsWaarde(Lo3Datum.NULL_DATUM))) {
                    // maak afsluitende rij voor stapel
                    ouderlijkGezagLijst.add(new TussenGroep<>(
                        new BrpOuderlijkGezagInhoud(null),
                        gezagsverhouding.getHistorie(),
                        gezagsverhouding.getDocumentatie(),
                        gezagsverhouding.getLo3Herkomst()));
                    // einde loop na afsluiten stapel
                    break;
                }
            }
        }
        return ouderlijkGezagLijst;
    }

    private boolean isVoorEinde(final Lo3Datum einddatumOuderrelatie, final Lo3Datum gezagDatum) {
        return !Validatie.isElementGevuld(einddatumOuderrelatie)
               || Validatie.isElementGevuld(gezagDatum)
               && einddatumOuderrelatie.compareTo(gezagDatum) > 0;
    }

    private boolean isNaIngang(final Lo3Datum ingangsdatumOuderrelatie, final Lo3Datum gezagDatum) {
        return Validatie.isElementGevuld(ingangsdatumOuderrelatie)
               && Validatie.isElementGevuld(gezagDatum)
               && ingangsdatumOuderrelatie.compareTo(gezagDatum) <= 0;
    }

    private BrpBoolean bepaalOuderHeeftGezag(final Lo3Categorie<Lo3GezagsverhoudingInhoud> gezagsverhouding, final OuderType ouderType) {
        final Lo3IndicatieGezagMinderjarige indicatieGezagMinderjarige = gezagsverhouding.getInhoud().getIndicatieGezagMinderjarige();
        final BrpBoolean ouderHeeftGezag;
        switch (ouderType) {
            case OUDER_1:
                ouderHeeftGezag = getLo3AttribuutConverteerder().converteerOuder1HeeftGezag(indicatieGezagMinderjarige);
                break;
            case OUDER_2:
                ouderHeeftGezag = getLo3AttribuutConverteerder().converteerOuder2HeeftGezag(indicatieGezagMinderjarige);
                break;
            default:
                ouderHeeftGezag = null;
        }
        return ouderHeeftGezag;
    }

    private List<TussenGroep<BrpGeboorteInhoud>> migreerGeboorteGroep(final Lo3Categorie<Lo3OuderInhoud> ouder) {
        final List<TussenGroep<BrpGeboorteInhoud>> result = new ArrayList<>();

        if (!ouder.getInhoud().isOnbekendeOuder()) {
            final Lo3OuderInhoud inhoud = ouder.getInhoud();
            result.add(getUtils().maakGeboorteGroep(
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

            result.add(getUtils().maakGeslachtsaanduidingInhoud(
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
            result.add(getUtils().maakIdentificatieGroep(
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
            result.add(getUtils().maakSamengesteldeNaamGroep(
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

    private static List<TussenGroep<BrpOuderInhoud>> migreerOuderGroep(
        final Lo3Categorie<Lo3OuderInhoud> ouderCategorie,
        final Lo3Categorie<Lo3OuderInhoud> eindeCategorie)
    {
        final List<TussenGroep<BrpOuderInhoud>> result = new ArrayList<>();

        // Migreer 62.10 'Datum familierechtelijke betrekking' naar historie als datum aanvang.
        final Lo3Datum datumFamilierechtelijkeBetrekking = ouderCategorie.getInhoud().getFamilierechtelijkeBetrekking();
        final Lo3Historie aanvangHistorie =
                new Lo3Historie(
                    ouderCategorie.getHistorie().getIndicatieOnjuist(),
                    datumFamilierechtelijkeBetrekking,
                    ouderCategorie.getHistorie().getDatumVanOpneming());

        result.add(new TussenGroep<>(
            new BrpOuderInhoud(new BrpBoolean(true, null), null),
            aanvangHistorie,
            ouderCategorie.getDocumentatie(),
            ouderCategorie.getLo3Herkomst()));

        if (eindeCategorie != null) {
            // Geen migratie van 62.10 nodig, dat is al gebeurd bij het splitsen
            result.add(new TussenGroep<>(
                new BrpOuderInhoud(null, null),
                eindeCategorie.getHistorie(),
                eindeCategorie.getDocumentatie(),
                eindeCategorie.getLo3Herkomst(),
                eindeCategorie.isAfsluitendVoorkomen(),
                false));
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

    private TussenStapel<BrpIstGezagsVerhoudingGroepInhoud> converteerGezagsVerhoudingStapel(
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagsVerhoudingStapel)
    {
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

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenRelatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de logica om een LO3 Kind te converteren naar BRP relaties, betrokkenen en gerelateerde personen.
 */
@Component
@Requirement({Requirements.CRP001, Requirements.CRP001_LB01, Requirements.CR001 })
public class KindConverteerder extends AbstractRelatieConverteerder {

    /**
     * Converteert de LO3 Kinder stapels naar de corresponderende BRP groepen en vult hiermee de migratie builder aan.
     * 
     * @param kindStapels
     *            de lijst met stapels voor Kind, mag niet null zijn, maar wel leeg
     * @param tussenPersoonslijstBuilder
     *            de migratie persoonslijst builder
     */
    public final void converteer(final List<Lo3Stapel<Lo3KindInhoud>> kindStapels, final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {

        for (final Lo3Stapel<Lo3KindInhoud> kindStapel : kindStapels) {
            final List<TussenGroep<BrpIstRelatieGroepInhoud>> istTussenGroepen = new ArrayList<>();

            // Loop elke categorie langs, maak een IST-object aan en voeg toe aan juisteKindRij als de categorie niet
            // onjuist is verklaard
            final List<Lo3Categorie<Lo3KindInhoud>> juisteKindRijen = new ArrayList<>();
            for (final Lo3Categorie<Lo3KindInhoud> kind : kindStapel) {
                vulIstGegevens(istTussenGroepen, kind);
                voegNietOnjuisteKindToe(juisteKindRijen, kind);
            }

            final TussenStapel<BrpIstRelatieGroepInhoud> istStapel = new TussenStapel<>(istTussenGroepen);
            tussenPersoonslijstBuilder.istKindStapel(istStapel);

            Lo3Categorie<Lo3KindInhoud> betrokkenKindRij = null;
            final List<Lo3Categorie<Lo3KindInhoud>> ouderKindRij = new ArrayList<>();

            // Als er maar 1 rij overblijft, doorgaan met converteren
            // Er moet minimaal 1 juiste rij zijn.
            // De actuele rij mag niet onjuist (zie hierboven) of leeg zijn in LO3
            if (juisteKindRijen.size() == 1) {
                if (juisteKindRijen.get(0).getInhoud().isLeeg()) {
                    continue;
                } else {
                    betrokkenKindRij = juisteKindRijen.get(0);
                    ouderKindRij.add(juisteKindRijen.get(0));
                }
            } else {
                if (!bevatAlleenLegeRijen(juisteKindRijen)) {
                    // Haal actueel uit de lijst en sorteer de rest op 85.10 waarna de actuele als eerste wordt
                    // toegevoegd.
                    final List<Lo3Categorie<Lo3KindInhoud>> gesorteerdeRijen = sorteerKindRijen(juisteKindRijen);

                    betrokkenKindRij = bepaalGerelateerdeKindInhoudRijen(gesorteerdeRijen);
                    ouderKindRij.addAll(bepaalOuderKindInhoudRijen(gesorteerdeRijen));
                }
            }

            final List<TussenGroep<BrpIdentificatienummersInhoud>> persoonIdentificatienummersGroepen = new ArrayList<>();
            final List<TussenGroep<BrpGeboorteInhoud>> geboorteGroepen = new ArrayList<>();
            final List<TussenGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamGroepen = new ArrayList<>();
            final List<TussenGroep<BrpOuderInhoud>> ouderGroepen = new ArrayList<>();

            if (betrokkenKindRij != null) {
                persoonIdentificatienummersGroepen.add(migreerIdentificatienummers(betrokkenKindRij));
                geboorteGroepen.add(migreerGeboorte(betrokkenKindRij));
                samengesteldeNaamGroepen.add(migreerNaam(betrokkenKindRij));
            }

            for (final Lo3Categorie<Lo3KindInhoud> rij : ouderKindRij) {
                ouderGroepen.add(migreerOuder(rij));
            }

            if (betrokkenKindRij != null && !ouderGroepen.isEmpty()) {
                final List<TussenBetrokkenheid> betrokkenheidStapels =
                        maakBetrokkenheidStapels(persoonIdentificatienummersGroepen, geboorteGroepen, samengesteldeNaamGroepen, ouderGroepen);
                tussenPersoonslijstBuilder.relatie(maakMigratieRelatie(istStapel, betrokkenheidStapels));
            }
        }
    }

    private boolean bevatAlleenLegeRijen(final List<Lo3Categorie<Lo3KindInhoud>> rijen) {
        boolean bevatAlleenLegeRijen = true;
        for (final Lo3Categorie<Lo3KindInhoud> rij : rijen) {
            if (!rij.getInhoud().isLeeg()) {
                bevatAlleenLegeRijen = false;
                break;
            }
        }
        return bevatAlleenLegeRijen;
    }

    private List<Lo3Categorie<Lo3KindInhoud>> sorteerKindRijen(final List<Lo3Categorie<Lo3KindInhoud>> juisteKindRijen) {
        final List<Lo3Categorie<Lo3KindInhoud>> result = new ArrayList<>();
        Lo3Categorie<Lo3KindInhoud> actueelVoorkomen = null;
        for (final Lo3Categorie<Lo3KindInhoud> voorkomen : juisteKindRijen) {
            if (voorkomen.getLo3Herkomst().isLo3ActueelVoorkomen()) {
                actueelVoorkomen = voorkomen;
                continue;
            }
            result.add(voorkomen);
        }

        // Sorteren op 85.10 nieuw naar oud
        Collections.sort(result, Lo3Categorie.DATUM_GELDIGHEID);
        // Actueel voorkomen als eerste in de lijst toevoegen
        result.add(0, actueelVoorkomen);

        return result;
    }

    private TussenRelatie maakMigratieRelatie(final TussenStapel<BrpIstRelatieGroepInhoud> istStapel, final List<TussenBetrokkenheid> betrokkenheidStapels)
    {
        final TussenRelatie.Builder relatieBuilder =
                new TussenRelatie.Builder(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, BrpSoortBetrokkenheidCode.OUDER);
        relatieBuilder.betrokkenheden(betrokkenheidStapels);
        relatieBuilder.istKind(istStapel);
        return relatieBuilder.build();
    }

    private void voegNietOnjuisteKindToe(final List<Lo3Categorie<Lo3KindInhoud>> juisteKindRijen, final Lo3Categorie<Lo3KindInhoud> kind) {
        // Als de rij onjuist is, dan wordt deze niet geconverteerd
        if (!kind.getHistorie().isOnjuist()) {
            juisteKindRijen.add(kind);
        }
    }

    private List<TussenBetrokkenheid> maakBetrokkenheidStapels(
        final List<TussenGroep<BrpIdentificatienummersInhoud>> persoonIdentificatienummersGroepen,
        final List<TussenGroep<BrpGeboorteInhoud>> geboorteGroepen,
        final List<TussenGroep<BrpSamengesteldeNaamInhoud>> samengesteldeNaamGroepen,
        final List<TussenGroep<BrpOuderInhoud>> ouderGroepen)
    {

        // STAPELS
        final TussenStapel<BrpIdentificatienummersInhoud> identificatienummersStapel;
        if (persoonIdentificatienummersGroepen.isEmpty()) {
            identificatienummersStapel = null;
        } else {
            identificatienummersStapel = new TussenStapel<>(persoonIdentificatienummersGroepen);
        }
        final TussenStapel<BrpGeboorteInhoud> geboorteStapel = geboorteGroepen.isEmpty() ? null : new TussenStapel<>(geboorteGroepen);
        final TussenStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamStapel;
        if (samengesteldeNaamGroepen.isEmpty()) {
            samengesteldeNaamStapel = null;
        } else {
            samengesteldeNaamStapel = new TussenStapel<>(samengesteldeNaamGroepen);
        }
        final TussenStapel<BrpOuderInhoud> ouderStapel = ouderGroepen.isEmpty() ? null : new TussenStapel<>(ouderGroepen);

        final TussenBetrokkenheid betrokkenheidStapelKind =
                new TussenBetrokkenheid(
                    BrpSoortBetrokkenheidCode.KIND,
                    identificatienummersStapel,
                    null,
                    geboorteStapel,
                    null,
                    samengesteldeNaamStapel,
                    ouderStapel);

        return Collections.singletonList(betrokkenheidStapelKind);
    }

    private void vulIstGegevens(final List<TussenGroep<BrpIstRelatieGroepInhoud>> istTussenGroepen, final Lo3Categorie<Lo3KindInhoud> kind) {
        // Vul IST gegevens
        final Lo3KindInhoud lo3Inhoud = kind.getInhoud();
        final Lo3Documentatie lo3Documentatie = kind.getDocumentatie();
        final Lo3Onderzoek lo3Onderzoek = kind.getOnderzoek();
        final Lo3Historie lo3Historie = kind.getHistorie();
        final Lo3Herkomst lo3Herkomst = kind.getLo3Herkomst();

        final BrpIstRelatieGroepInhoud istKindGroepInhoud =
                maakIstRelatieGroepInhoud(
                    lo3Inhoud.getAdellijkeTitelPredikaatCode(),
                    lo3Inhoud.getVoornamen(),
                    lo3Inhoud.getVoorvoegselGeslachtsnaam(),
                    lo3Inhoud.getGeslachtsnaam(),
                    lo3Inhoud.getGeboorteGemeenteCode(),
                    lo3Inhoud.getGeboorteLandCode(),
                    lo3Inhoud.getGeboortedatum(),
                    null,
                    lo3Inhoud.getaNummer(),
                    lo3Inhoud.getBurgerservicenummer(),
                    null,
                    lo3Documentatie,
                    lo3Onderzoek,
                    lo3Historie,
                    lo3Herkomst);
        istTussenGroepen.add(maakMigratieGroep(istKindGroepInhoud));
    }

    /* Private methods */

    private Lo3Categorie<Lo3KindInhoud> bepaalGerelateerdeKindInhoudRijen(final List<Lo3Categorie<Lo3KindInhoud>> juisteKindRijen) {
        Lo3Categorie<Lo3KindInhoud> rij = null;

        // Als de eerste rij gevuld is -> klaar, dit is de actuele!
        // Als de eerste rij leeg is, bepaal dan de eerst volgende gevulde rij met de meest recente geldigheid
        final Lo3Categorie<Lo3KindInhoud> actueleRij = juisteKindRijen.get(0);
        if (!actueleRij.getInhoud().isLeeg()) {
            rij = actueleRij;
        } else {
            // Actueel is leeg.
            // Bepaal de gevulde rij met de meest recente geldigheid
            for (int index = 1; index < juisteKindRijen.size(); index++) {
                final Lo3Categorie<Lo3KindInhoud> juisteRij = juisteKindRijen.get(index);
                if (!juisteRij.getInhoud().isLeeg()) {
                    // Rij is gevuld. Aangezien de rijen gesorteerd zijn van nieuw
                    // naar oud, is het meest recente gevulde rij gelijk ook gevonden.
                    rij = juisteRij;
                    break;
                }
            }
        }
        return rij;
    }

    private List<Lo3Categorie<Lo3KindInhoud>> bepaalOuderKindInhoudRijen(final List<Lo3Categorie<Lo3KindInhoud>> juisteKindRijen) {
        final List<Lo3Categorie<Lo3KindInhoud>> rijen = new ArrayList<>();
        // Eerste item in de lijst is de actuele
        final Lo3Categorie<Lo3KindInhoud> actueelVoorkomen = juisteKindRijen.get(0);
        final Integer actueelDatum = actueelVoorkomen.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
        final boolean actueelGevuld = !actueelVoorkomen.getInhoud().isLeeg();

        // Loop door de rijen heen en bepaal per periode (een aaneengesloten reeks van lege of gevulde rijen) de oudste
        // rij
        Lo3Categorie<Lo3KindInhoud> vorigeRij = null;
        boolean eersteRijGevuld = false;
        for (final Lo3Categorie<Lo3KindInhoud> rij : juisteKindRijen) {
            if (vorigeRij == null) {
                vorigeRij = rij;
                eersteRijGevuld = !rij.getInhoud().isLeeg();
                continue;
            }
            if (vorigeRij.getInhoud().isLeeg() ^ rij.getInhoud().isLeeg()) {
                // vorigeRij is leeg en rij is niet leeg OF vorigeRij is niet leeg en rij is leeg
                rijen.add(vorigeRij);
            }
            vorigeRij = rij;
        }

        /*
         * Alle rijen zijn langs gelopen. Voeg de vorigeRij niet toe als deze leeg is en de eerste rij wel gevuld is.
         */
        if (!(eersteRijGevuld && vorigeRij.getInhoud().isLeeg())) {
            rijen.add(vorigeRij);
        }

        // Controle of er geen overlappen setjes leeg/gevuld zijn tov actuele record indien deze gevuld is
        if (actueelGevuld) {
            contoleLeegGevuld(rijen, actueelDatum);

        }
        return rijen;
    }

    /**
     * Eerste rij is altijd gevuld als actueel gevuld is. De volgende rij is dus een lege rij. index altijd +2 omdat er
     * koppeltjes gevormd worden leeg, gevuld.
     * 
     * @param rijen
     *            kind rijen
     * @param actueelDatum
     *            actueel datum
     */
    final void contoleLeegGevuld(final List<Lo3Categorie<Lo3KindInhoud>> rijen, final Integer actueelDatum) {
        boolean rijenVerwijderd = false;
        for (int legeRijIndex = 1; legeRijIndex < rijen.size(); legeRijIndex += 2) {
            int gevuldeRijIndex;
            if (!rijenVerwijderd) {
                gevuldeRijIndex = legeRijIndex + 1;
            } else {
                gevuldeRijIndex = legeRijIndex - 1;
                rijenVerwijderd = false;
            }

            final Lo3Categorie<Lo3KindInhoud> legeRij = rijen.get(legeRijIndex);
            final Lo3Categorie<Lo3KindInhoud> gevuldeRij = rijen.get(gevuldeRijIndex);

            final int legeRijDatum = legeRij.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
            final int gevuldeRijDatum = gevuldeRij.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();

            // Bepaal of actueel in de periode van gevuldRij t/m legeRij valt.
            if (gevuldeRijDatum <= actueelDatum && actueelDatum <= legeRijDatum) {
                // actueel valt binnen geselecteerde periode, verwijder deze beide regels. Eerst de gevulde, dan de
                // lege rij en verlaag dan de legeRijIndex met -2 zodat de legeRijIndex de volgende keer de juiste
                // volgende lege rij pakt
                rijen.remove(gevuldeRijIndex);
                rijen.remove(legeRijIndex);
                rijenVerwijderd = true;
            }
        }
    }

    private TussenGroep<BrpIdentificatienummersInhoud> migreerIdentificatienummers(final Lo3Categorie<Lo3KindInhoud> kind) {
        final Lo3KindInhoud inhoud = kind.getInhoud();
        return getUtils().maakIdentificatieGroep(
            inhoud.getaNummer(),
            inhoud.getBurgerservicenummer(),
            kind.getHistorie(),
            kind.getDocumentatie(),
            kind.getLo3Herkomst());
    }

    private TussenGroep<BrpGeboorteInhoud> migreerGeboorte(final Lo3Categorie<Lo3KindInhoud> kind) {
        final Lo3KindInhoud inhoud = kind.getInhoud();
        return getUtils().maakGeboorteGroep(
            inhoud.getGeboorteGemeenteCode(),
            inhoud.getGeboorteLandCode(),
            inhoud.getGeboortedatum(),
            kind.getHistorie(),
            kind.getDocumentatie(),
            kind.getLo3Herkomst());
    }

    private TussenGroep<BrpSamengesteldeNaamInhoud> migreerNaam(final Lo3Categorie<Lo3KindInhoud> kind) {
        final Lo3KindInhoud inhoud = kind.getInhoud();
        return getUtils().maakSamengesteldeNaamGroep(
            inhoud.getAdellijkeTitelPredikaatCode(),
            inhoud.getVoornamen(),
            inhoud.getVoorvoegselGeslachtsnaam(),
            inhoud.getGeslachtsnaam(),
            kind.getHistorie(),
            kind.getDocumentatie(),
            kind.getLo3Herkomst());
    }

    private TussenGroep<BrpOuderInhoud> migreerOuder(final Lo3Categorie<Lo3KindInhoud> kind) {
        final Lo3KindInhoud inhoud = kind.getInhoud();

        final Boolean indicatieKind = !inhoud.isLeeg();
        BrpOuderInhoud ouderInhoud = new BrpOuderInhoud(null, null);
        if (indicatieKind) {
            ouderInhoud = new BrpOuderInhoud(new BrpBoolean(true, null), null);
        }
        return new TussenGroep<>(ouderInhoud, kind.getHistorie(), kind.getDocumentatie(), kind.getLo3Herkomst());
    }
}

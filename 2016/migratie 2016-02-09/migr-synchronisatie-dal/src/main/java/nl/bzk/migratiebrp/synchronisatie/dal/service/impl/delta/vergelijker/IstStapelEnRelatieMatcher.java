/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingOuder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.IstStapelMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.StapelMatchType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.BetrokkenheidDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.PersoonDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.RelatieDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelVoorkomenDecorator;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Class die de IST-stapels, Relaties, Betrokkenheden en gerelateerde personen met elkaar match, zodat er een goede
 * vergelijking gedaan kan worden.
 */
public final class IstStapelEnRelatieMatcher {

    private static final String KRUIMELPAD_ID = "IST Stapel Matcher: ";

    /**
     * Matched de IST-stapels en vervolgens de Betrokkenheden, Relaties en Gerelateerde personen die gekoppeld zijn aan
     * deze IST-stapels.
     *
     * @param context
     *            de context van deltabepaling
     * @return een set {@link DeltaRootEntiteitMatch} objecten.
     */
    public Set<DeltaRootEntiteitMatch> matchIstGegevens(final DeltaBepalingContext context) {
        final PersoonDecorator decoratedBestaandePersoon = PersoonDecorator.decorate(context.getBestaandePersoon());
        final PersoonDecorator decoratedNieuwePersoon = PersoonDecorator.decorate(context.getNieuwePersoon());

        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = new LinkedHashSet<>();

        deltaRootEntiteitMatches.addAll(matchOuderStapels(context, decoratedBestaandePersoon, decoratedNieuwePersoon));
        deltaRootEntiteitMatches.addAll(matchHuwelijkOfGpStapels(context, decoratedBestaandePersoon, decoratedNieuwePersoon));
        deltaRootEntiteitMatches.addAll(matchKindStapels(context, decoratedBestaandePersoon, decoratedNieuwePersoon));
        deltaRootEntiteitMatches.addAll(matchGezagsverhoudingStapels(context, decoratedBestaandePersoon, decoratedNieuwePersoon));

        return deltaRootEntiteitMatches;
    }

    private Set<DeltaRootEntiteitMatch> matchKindStapels(
        final DeltaBepalingContext context,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon)
    {
        final Set<StapelDecorator> bestaandeStapels = bestaandePersoon.getKindStapels();
        final Set<StapelDecorator> kluizenaarStapels = nieuwePersoon.getKindStapels();

        final Set<IstStapelMatch> istStapelMatches = matchStapels(bestaandeStapels, kluizenaarStapels);
        return converteerNaarEntiteitMatchesEnZoekKernMatches(context, istStapelMatches, bestaandePersoon, nieuwePersoon, Lo3RelatieType.KIND);
    }

    private Set<DeltaRootEntiteitMatch> matchHuwelijkOfGpStapels(
        final DeltaBepalingContext context,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator kluizenaar)
    {
        final Set<StapelDecorator> bestaandeStapels = bestaandePersoon.getHuwelijkOfGpStapels();
        final Set<StapelDecorator> kluizenaarStapels = kluizenaar.getHuwelijkOfGpStapels();

        final Set<IstStapelMatch> istStapelMatches = matchStapels(bestaandeStapels, kluizenaarStapels);
        return converteerNaarEntiteitMatchesEnZoekKernMatches(context, istStapelMatches, bestaandePersoon, kluizenaar, Lo3RelatieType.HUWELIJK_OF_GP);
    }

    private Set<DeltaRootEntiteitMatch> matchOuderStapels(
        final DeltaBepalingContext context,
        final PersoonDecorator bestaandePersoonDecorator,
        final PersoonDecorator nieuwePersoonDecorator)
    {
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = new LinkedHashSet<>();

        final IstStapelMatch ouder1IstMatch = matchOuderStapel(context, bestaandePersoonDecorator, nieuwePersoonDecorator, true);
        final IstStapelMatch ouder2IstMatch = matchOuderStapel(context, bestaandePersoonDecorator, nieuwePersoonDecorator, false);

        /*
         * De volgende situaties kunnen ontstaan: 1. Beide stapels bestaan nog niet en worden beiden toegevoegd -> 1
         * nieuwe betrokkenheid en relatie. En deze zijn voor beide stapel hetzelfde. 2. Beide stapels bestaan al en
         * blijven bestaan -> Geen nieuwe betrokkenheid en relatie, deze kunnen wel gematched worden 3. Beide stapels
         * bestaan en worden beide verwijderd -> Betrokkenheid kan als verwijderd gemarkeerd worden 4. Beide stapels
         * bestaan, maar 1 stapel wordt verwijderd -> Betrokkenheid en relatie moeten blijven bestaan. Alleen stapel
         * markeren als te verwijderen 5. Er bestaat 1 stapel en de ander wordt toegevoegd -> Betrokkenheid en relatie
         * bestaat al. Nieuwe stapel 'omhangen' naar de bestaande relatie.
         * 
         * 1-3 kunnen ook voor 1 stapel alleen gelezen worden.
         */
        if (ouder1IstMatch != null || ouder2IstMatch != null) {
            final boolean betrokkenheidBestaatAl = bestaatBetrokkenheidAl(ouder1IstMatch, ouder2IstMatch);
            final boolean betrokkenheidMoetVerwijderdWorden = moetBetrokkenheidVerwijderdWorden(ouder1IstMatch, ouder2IstMatch);

            if (betrokkenheidMoetVerwijderdWorden) {
                // Beide stapels zijn niet meer gevonden in de nieuwe set. Markeren als te verwijderen. Ook de
                // betrokkenheid markeren als te verwijderen.
                markeerOuderIstMatch(deltaRootEntiteitMatches, ouder1IstMatch, bestaandePersoonDecorator, nieuwePersoonDecorator, false);
                markeerOuderIstMatch(deltaRootEntiteitMatches, ouder2IstMatch, bestaandePersoonDecorator, nieuwePersoonDecorator, false);
            } else {
                // 1 of beide stapels zijn nieuw, of beide zijn gematched.
                if (betrokkenheidBestaatAl) {
                    // In ieder geval 1 stapel bestond al, dus de betrokkenheid hoeft nooit als nieuw toe te voegen te
                    // worden gemarkeerd.
                    final BetrokkenheidDecorator ikBetrokkenheid = bepaalIkBetrokkenheidOuders(ouder1IstMatch, ouder2IstMatch, bestaandePersoonDecorator);
                    if (ouder1IstMatch != null) {
                        markeerOuderKernInhoud(
                            deltaRootEntiteitMatches,
                            ouder1IstMatch,
                            ikBetrokkenheid,
                            bestaandePersoonDecorator,
                            nieuwePersoonDecorator,
                            AanduidingOuder.OUDER_1);
                    }
                    if (ouder2IstMatch != null) {
                        markeerOuderKernInhoud(
                            deltaRootEntiteitMatches,
                            ouder2IstMatch,
                            ikBetrokkenheid,
                            bestaandePersoonDecorator,
                            nieuwePersoonDecorator,
                            AanduidingOuder.OUDER_2);
                    }
                } else {
                    // Compleet nieuwe stapel(s). Betrokkenheid markeren als nieuw toe te voegen.
                    markeerOuderIstMatch(deltaRootEntiteitMatches, ouder1IstMatch, bestaandePersoonDecorator, nieuwePersoonDecorator, true);
                    markeerOuderIstMatch(deltaRootEntiteitMatches, ouder2IstMatch, bestaandePersoonDecorator, nieuwePersoonDecorator, true);
                }
            }
        }

        return deltaRootEntiteitMatches;
    }

    private boolean moetBetrokkenheidVerwijderdWorden(final IstStapelMatch ouder1IstMatch, final IstStapelMatch ouder2IstMatch) {

        return (ouder1IstMatch == null || StapelMatchType.STAPEL_VERWIJDERD.equals(ouder1IstMatch.getMatchType()))
               && (ouder2IstMatch == null || StapelMatchType.STAPEL_VERWIJDERD.equals(ouder2IstMatch.getMatchType()));
    }

    private boolean bestaatBetrokkenheidAl(final IstStapelMatch ouder1IstMatch, final IstStapelMatch ouder2IstMatch) {
        return ouder1IstMatch != null
               && !StapelMatchType.STAPEL_NIEUW.equals(ouder1IstMatch.getMatchType())
               || ouder2IstMatch != null
               && !StapelMatchType.STAPEL_NIEUW.equals(ouder2IstMatch.getMatchType());
    }

    private void markeerOuderIstMatch(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final IstStapelMatch istStapelMatch,
        final PersoonDecorator bestaandePersoonDecorator,
        final PersoonDecorator nieuwePersoonDecorator,
        final boolean toevoegen)
    {
        if (istStapelMatch != null) {
            final Persoon bestaandePersoon = bestaandePersoonDecorator.getPersoon();
            final StapelDecorator stapelDecorator = istStapelMatch.getStapel();

            if (toevoegen) {
                final DeltaRootEntiteit nieuweStapel = stapelDecorator.getStapel();
                final BetrokkenheidDecorator nieuweBetrokkenheid = getIkBetrokkenheidDecorator(nieuwePersoonDecorator, stapelDecorator.getRelatie());

                deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(null, nieuweStapel, bestaandePersoon, Persoon.STAPELS));
                voegBetrokkenhedenMatchToe(deltaRootEntiteitMatches, null, nieuweBetrokkenheid, bestaandePersoon, Persoon.BETROKKENHEID_SET);
            } else {
                final DeltaRootEntiteit bestaandeStapel = stapelDecorator.getStapel();
                deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(bestaandeStapel, null, bestaandePersoon, Persoon.STAPELS));
            }
        }
    }

    private void markeerOuderKernInhoud(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final IstStapelMatch istStapelMatch,
        final BetrokkenheidDecorator ikBetrokkenheid,
        final PersoonDecorator bestaandePersoonDecorator,
        final PersoonDecorator nieuwePersoon,
        final AanduidingOuder aanduidingOuder)
    {
        // Stapel Match kan NIEUW, VERWIJDERD of MATCHED zijn. Indien NIEUW of VERWIJDERD, de betrokkenheden van ouder
        // als te verwijderen/toevoegen markeren.
        final Persoon bestaandePersoon = bestaandePersoonDecorator.getPersoon();
        final RelatieDecorator bestaandeRelatie = ikBetrokkenheid.getRelatie();
        final StapelMatchType stapelMatchType = istStapelMatch.getMatchType();

        if (StapelMatchType.STAPEL_VERWIJDERD.equals(stapelMatchType)) {
            final Set<BetrokkenheidDecorator> bestaandeBetrokkenheden = bestaandeRelatie.getAndereOuderBetrokkenheden(ikBetrokkenheid, aanduidingOuder);
            for (final BetrokkenheidDecorator bestaandeBetrokkenheid : bestaandeBetrokkenheden) {
                voegStapelMatchToe(deltaRootEntiteitMatches, istStapelMatch.getStapel(), null, bestaandePersoon);
                voegBetrokkenhedenMatchToe(
                    deltaRootEntiteitMatches,
                    bestaandeBetrokkenheid,
                    null,
                    bestaandeRelatie.getRelatie(),
                    Relatie.BETROKKENHEID_SET);
            }
        } else {
            // NIEUW of MATCHED
            final StapelDecorator matchingStapel =
                    StapelMatchType.STAPEL_NIEUW.equals(stapelMatchType) ? istStapelMatch.getStapel() : istStapelMatch.getMatchingStapel();
            final RelatieDecorator nieuweRelatie = matchingStapel.getRelatie();
            final BetrokkenheidDecorator nieuweIkBetrokkenheid = getIkBetrokkenheidDecorator(nieuwePersoon, nieuweRelatie);

            if (StapelMatchType.STAPEL_NIEUW.equals(stapelMatchType)) {
                final StapelDecorator nieuweStapel = istStapelMatch.getStapel();
                nieuweStapel.ontkoppelRelaties();

                voegStapelMatchToe(deltaRootEntiteitMatches, null, nieuweStapel, bestaandePersoon);
                final Set<BetrokkenheidDecorator> nieuweBetrokkenheden =
                        nieuweRelatie.getAndereOuderBetrokkenheden(nieuweIkBetrokkenheid, aanduidingOuder);
                for (final BetrokkenheidDecorator nieuweBetrokkenheid : nieuweBetrokkenheden) {
                    voegBetrokkenhedenMatchToe(
                        deltaRootEntiteitMatches,
                        null,
                        nieuweBetrokkenheid,
                        bestaandeRelatie.getRelatie(),
                        Relatie.BETROKKENHEID_SET);
                }
            } else {
                // Stapel match toevoegen
                voegStapelMatchToe(deltaRootEntiteitMatches, istStapelMatch.getStapel(), istStapelMatch.getMatchingStapel(), bestaandePersoon);
                // Ik-betrokkenheid match toevoegen
                voegBetrokkenhedenMatchToe(deltaRootEntiteitMatches, ikBetrokkenheid, nieuweIkBetrokkenheid, bestaandePersoon, Persoon.BETROKKENHEID_SET);
                // Controleer de relatie en gerelateerde betrokkenheden en personen.
                matchBetrokkenhedenEnGerelateerdenOuder(deltaRootEntiteitMatches, ikBetrokkenheid, nieuweIkBetrokkenheid, aanduidingOuder);
            }
        }
    }

    private void matchBetrokkenhedenEnGerelateerdenOuder(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final BetrokkenheidDecorator bestaandeIkBetrokkenheid,
        final BetrokkenheidDecorator nieuweIkBetrokkenheid,
        final AanduidingOuder aanduidingOuder)
    {
        final RelatieDecorator bestaandeRelatie = bestaandeIkBetrokkenheid.getRelatie();
        final RelatieDecorator nieuweRelatie = nieuweIkBetrokkenheid.getRelatie();

        // Relaties worden niet gematched omdat hier geen veranderingen in kunnen optreden. Historie reageert hier
        // hetzelfde als de betrokkenheid historie.

        final Set<BetrokkenheidDecorator> bestaandeBetrokkenheden =
                bestaandeRelatie.getAndereOuderBetrokkenheden(bestaandeIkBetrokkenheid, aanduidingOuder);
        final Set<BetrokkenheidDecorator> nieuweBetrokkenheden = nieuweRelatie.getAndereOuderBetrokkenheden(nieuweIkBetrokkenheid, aanduidingOuder);

        final Set<BetrokkenheidDecorator> matchendeBestaandeBetrokkenheden = new HashSet<>();

        for (final BetrokkenheidDecorator bestaandeBetrokkenheid : bestaandeBetrokkenheden) {
            final BetrokkenheidDecorator matchendeBetrokkenheid = bestaandeBetrokkenheid.zoekMatchendeOuderBetrokkenheid(nieuweBetrokkenheden);
            if (matchendeBetrokkenheid != null) {
                matchendeBestaandeBetrokkenheden.add(matchendeBetrokkenheid);
            }
            voegGerelateerdeMatchToe(deltaRootEntiteitMatches, bestaandeBetrokkenheid, matchendeBetrokkenheid, Betrokkenheid.PERSOON);

            voegBetrokkenhedenMatchToe(
                deltaRootEntiteitMatches,
                bestaandeBetrokkenheid,
                matchendeBetrokkenheid,
                bestaandeRelatie.getRelatie(),
                Relatie.BETROKKENHEID_SET);
        }

        // Andere betrokkenheden vinden, deze zijn nieuw
        final Set<BetrokkenheidDecorator> nieuweOverigeBetrokkenheden =
                getOverigeOuderBetrokkenheden(matchendeBestaandeBetrokkenheden, nieuweBetrokkenheden);
        for (final BetrokkenheidDecorator nieuweOverigeBetrokkenheid : nieuweOverigeBetrokkenheden) {
            voegBetrokkenhedenMatchToe(
                deltaRootEntiteitMatches,
                null,
                nieuweOverigeBetrokkenheid,
                bestaandeRelatie.getRelatie(),
                Relatie.BETROKKENHEID_SET);
        }
    }

    private BetrokkenheidDecorator bepaalIkBetrokkenheidOuders(
        final IstStapelMatch ouder1IstMatch,
        final IstStapelMatch ouder2IstMatch,
        final PersoonDecorator persoonDecorator)
    {
        final RelatieDecorator relatieDecorator;
        if (ouder1IstMatch != null && ouder1IstMatch.getMatchType().equals(StapelMatchType.MATCHED)) {
            relatieDecorator = ouder1IstMatch.getStapel().getRelatie();
        } else if (ouder2IstMatch != null && ouder2IstMatch.getMatchType().equals(StapelMatchType.MATCHED)) {
            relatieDecorator = ouder2IstMatch.getStapel().getRelatie();
        } else {
            relatieDecorator = null;
        }
        return getIkBetrokkenheidDecorator(persoonDecorator, relatieDecorator);
    }

    private Set<DeltaRootEntiteitMatch> matchGezagsverhoudingStapels(
        final DeltaBepalingContext context,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon)
    {
        // Er zijn 3 mogelijke situaties:
        // 1. Nieuwe stapel => controleren of relatie gekoppeld is en of deze al bestaat/nieuw wordt toegevoegd.
        // 2. Bestaande stapel => match maken
        // 3. Verwijderde stapel => stapel(relatie) markeren als te verwijderen.

        final StapelDecorator bestaandeStapel = bestaandePersoon.getGezagsverhoudingStapel();
        final StapelDecorator nieuweStapel = nieuwePersoon.getGezagsverhoudingStapel();
        final StapelMatches<StapelDecorator> stapelMatches;
        if (bestaandeStapel == null) {
            if (nieuweStapel != null) {
                stapelMatches = new StapelMatches<>();
                stapelMatches.toevoegenMatch(null, nieuweStapel);
            } else {
                stapelMatches = null;
            }
        } else {
            if (nieuweStapel != null) {
                stapelMatches = new StapelMatches<>();
                stapelMatches.toevoegenMatch(bestaandeStapel, nieuweStapel);
            } else {
                stapelMatches = new StapelMatches<>();
                stapelMatches.toevoegenMatch(bestaandeStapel, null);
            }
        }

        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = new HashSet<>();
        if (stapelMatches != null) {
            final IstStapelMatch istStapelMatch;
            final Set<IstStapelMatch> istStapelMatches = converteerNaarIstStapelMatches(stapelMatches);
            boolean isActueel = true;
            istStapelMatch = istStapelMatches.iterator().next();

            final StapelDecorator stapelDecorator = istStapelMatch.getStapel();
            switch (istStapelMatch.getMatchType()) {
                case MATCHED:
                    voegStapelMatchToe(deltaRootEntiteitMatches, stapelDecorator, istStapelMatch.getMatchingStapel(), bestaandePersoon.getPersoon());
                    break;
                case STAPEL_NIEUW:
                    voegStapelMatchToe(deltaRootEntiteitMatches, null, stapelDecorator, bestaandePersoon.getPersoon());
                    // Verwijder de stapel relatie. Wordt in het proces weer aan gekoppeld met de bestaande ouder
                    // relatie.
                    stapelDecorator.ontkoppelRelaties();
                    break;
                case STAPEL_VERWIJDERD:
                    isActueel = false;
                    voegStapelMatchToe(deltaRootEntiteitMatches, stapelDecorator, null, bestaandePersoon.getPersoon());
                    break;
                default:
                    throw new IllegalStateException("Stapel matching voor gezagsverhouding kan niet in deze matchtypes eindigen");
            }

            maakKruimelpadLoggingEnUpdateContext(Lo3RelatieType.GEZAGSVERHOUDING, istStapelMatches, isActueel, context);
        }

        return deltaRootEntiteitMatches;
    }

    private IstStapelMatch matchOuderStapel(
        final DeltaBepalingContext context,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon,
        final boolean isOuder1)
    {
        final StapelDecorator bestaandeOuderStapel;
        final StapelDecorator nieuweOuderStapel;
        if (isOuder1) {
            bestaandeOuderStapel = bestaandePersoon.getOuder1Stapel();
            nieuweOuderStapel = nieuwePersoon.getOuder1Stapel();
        } else {
            bestaandeOuderStapel = bestaandePersoon.getOuder2Stapel();
            nieuweOuderStapel = nieuwePersoon.getOuder2Stapel();
        }

        boolean isActueel = true;
        // Er zijn 4 mogelijke situaties:
        // 1. er zijn geen stapels => Alleen mogelijk als het een RNI PL is. Geen match nodig dus.
        // 2. Er zijn wel stapels => 'Normale' PL. Match maken. Er is maar 1 stapel voor Ouder1/2 per PL.
        // 3. Er bestaat een stapel, maar in de nieuwe PL niet meer => Eigenlijk niet mogelijk, maar kan bij opschonen
        // naar RNI. Rij verwijderd.
        // 4. Er bestond geen stapel, maar in de nieuwe PL wel => RNI PL naar nu een 'Normale' PL. Markeren als nieuwe
        // stapel.
        final StapelMatches<StapelDecorator> stapelMatches;
        if (bestaandeOuderStapel == null) {
            if (nieuweOuderStapel == null) {
                // Situatie 1
                stapelMatches = null;
            } else {
                // Situatie 4
                stapelMatches = new StapelMatches<>();
                stapelMatches.toevoegenMatch(null, nieuweOuderStapel);
            }
        } else {
            if (nieuweOuderStapel == null) {
                // Situatie 3
                stapelMatches = new StapelMatches<>();
                stapelMatches.toevoegenMatch(bestaandeOuderStapel, null);
                isActueel = false;
            } else {
                // Situatie 2
                stapelMatches = new StapelMatches<>();
                stapelMatches.toevoegenMatch(bestaandeOuderStapel, nieuweOuderStapel);
            }
        }

        final IstStapelMatch resultaat;
        if (stapelMatches != null) {
            final Set<IstStapelMatch> istStapelMatches = converteerNaarIstStapelMatches(stapelMatches);
            maakKruimelpadLoggingEnUpdateContext(isOuder1 ? Lo3RelatieType.OUDER1 : Lo3RelatieType.OUDER2, istStapelMatches, isActueel, context);
            resultaat = istStapelMatches.iterator().next();
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    private Set<IstStapelMatch> matchStapels(final Set<StapelDecorator> bestaandeStapels, final Set<StapelDecorator> nieuweStapels) {
        final StapelMatches<StapelDecorator> stapelMatches = new StapelMatches<>();

        if (!bestaandeStapels.isEmpty()) {
            // Er bestonden al IST stapels. Controleren of deze nog steeds in de kluizenaar aanwezig zijn.
            for (final StapelDecorator stapel : bestaandeStapels) {
                matchStapel(stapelMatches, stapel, nieuweStapels);
            }
        }

        for (final StapelDecorator nieuweStapel : nieuweStapels) {
            if (!stapelMatches.bevatKoppelingVoorStapel(nieuweStapel, false)) {
                stapelMatches.toevoegenMatch(null, nieuweStapel);
            }
        }

        return converteerNaarIstStapelMatches(stapelMatches);
    }

    private void matchStapel(
        final StapelMatches<StapelDecorator> stapelMatches,
        final StapelDecorator zoekStapel,
        final Set<StapelDecorator> stapels)
    {
        boolean matchFound = false;
        final StapelVoorkomenDecorator zoekActueelVoorkomen = zoekStapel.getActueelVoorkomen();
        for (final StapelDecorator nieuweStapel : stapels) {
            if (nieuweStapel.bevatStapelVoorkomen(zoekActueelVoorkomen)) {
                matchFound = true;
                stapelMatches.toevoegenMatch(zoekStapel, nieuweStapel);
            }
        }

        if (!matchFound) {
            stapelMatches.toevoegenMatch(zoekStapel, null);
        }
    }

    private void maakKruimelpadLoggingEnUpdateContext(
        final Lo3RelatieType relatieType,
        final Set<IstStapelMatch> stapelMatches,
        final boolean isActueel,
        final DeltaBepalingContext context)
    {
        if (stapelMatches.isEmpty()) {
            return;
        }

        SynchronisatieLogging.addMelding(String.format(KRUIMELPAD_ID + "Resultaten IST stapel (%1$s) matching:", relatieType));

        for (final IstStapelMatch stapelMatch : stapelMatches) {
            final List<StapelDecorator> matchingStapels = stapelMatch.getMatchingStapels();
            final StringBuilder matchingStapelsOmschrijving = new StringBuilder();
            final Iterator<StapelDecorator> matchingStapelsIterator = matchingStapels.iterator();

            while (matchingStapelsIterator.hasNext()) {
                final StapelDecorator stapel = matchingStapelsIterator.next();
                matchingStapelsOmschrijving.append(stapel).append(" (").append(stapelMatch.getMatchType()).append(")");
                if (matchingStapelsIterator.hasNext()) {
                    matchingStapelsOmschrijving.append(", ");
                }
            }

            if (matchingStapels.size() == 0) {
                // Stapel verwijderd of toegevoegd:
                SynchronisatieLogging.addMelding(String.format(
                    KRUIMELPAD_ID + "Stapel=%1$s heeft geen match (%2$s)",
                    stapelMatch.getStapel(),
                    stapelMatch.getMatchType()));
            } else if (matchingStapels.size() == 1) {
                if (StapelMatchType.MATCHED.equals(stapelMatch.getMatchType())) {
                    // Exacte match
                    SynchronisatieLogging.addMelding(String.format(
                        KRUIMELPAD_ID + "Stapel=%1$s heeft exact 1 match met %2$s",
                        stapelMatch.getStapel(),
                        matchingStapelsOmschrijving));
                } else {
                    // Stapel matched met 1 andere stapel, maar deze stapel matched met meer dan 1 andere stapel
                    SynchronisatieLogging.addMelding(String.format(
                        KRUIMELPAD_ID + "Stapel=%1$s heeft 1 match, maar deze match is niet uniek voor %2$s",
                        stapelMatch.getStapel(),
                        matchingStapelsOmschrijving));
                }
            } else {
                SynchronisatieLogging.addMelding(String.format(
                    KRUIMELPAD_ID + "Stapel=%1$s is meer dan 1x gematched %2$s",
                    stapelMatch.getStapel(),
                    matchingStapelsOmschrijving));
            }
        }
        SynchronisatieLogging.addMelding(String.format("IST Stapel (%s) matching: Voorlopige conclusie: Is bijhouding actueel: %b", relatieType, isActueel));
        if (!isActueel) {
            context.setBijhoudingOverig();
        }
    }

    private Set<DeltaRootEntiteitMatch> converteerNaarEntiteitMatchesEnZoekKernMatches(
        final DeltaBepalingContext context,
        final Set<IstStapelMatch> stapelMatches,
        final PersoonDecorator bestaandePersoonDecorator,
        final PersoonDecorator nieuwePersoonDecorator,
        final Lo3RelatieType relatieType)
    {
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = new LinkedHashSet<>();

        boolean isActueel = true;
        for (final IstStapelMatch stapelMatch : stapelMatches) {
            final StapelDecorator stapelDecorator = stapelMatch.getStapel();
            final Persoon bestaandePersoon = bestaandePersoonDecorator.getPersoon();

            switch (stapelMatch.getMatchType()) {
                case MATCHED:
                    final StapelDecorator nieuweStapelDecorator = stapelMatch.getMatchingStapel();
                    voegStapelMatchToe(deltaRootEntiteitMatches, stapelDecorator, nieuweStapelDecorator, bestaandePersoon);
                    matchKernInhoudelijk(
                        deltaRootEntiteitMatches,
                        stapelDecorator,
                        nieuweStapelDecorator,
                        bestaandePersoonDecorator,
                        nieuwePersoonDecorator,
                        relatieType);
                    break;
                case STAPEL_VERWIJDERD:
                    isActueel = false;
                    voegStapelMatchToe(deltaRootEntiteitMatches, stapelDecorator, null, bestaandePersoon);
                    markeerBetrokkenheid(deltaRootEntiteitMatches, stapelDecorator, bestaandePersoonDecorator, bestaandePersoon, false);
                    break;
                case STAPEL_NIEUW:
                    voegStapelMatchToe(deltaRootEntiteitMatches, null, stapelDecorator, bestaandePersoon);
                    markeerBetrokkenheid(deltaRootEntiteitMatches, stapelDecorator, nieuwePersoonDecorator, bestaandePersoon, true);
                    if (stapelDecorator.getVoorkomens().size() > 1) {
                        isActueel = false;
                    }
                    break;
                case NON_UNIQUE_MATCH:
                default:
                    isActueel = false;
                    // Bestaande stapel als te verwijderen markeren
                    voegStapelMatchToe(deltaRootEntiteitMatches, stapelDecorator, null, bestaandePersoon);

                    // Alle matchende stapels al toe te voegen markeren
                    for (final StapelDecorator matchingStapelDecorator : stapelMatch.getMatchingStapels()) {
                        voegStapelMatchToe(deltaRootEntiteitMatches, null, matchingStapelDecorator, bestaandePersoon);
                        markeerBetrokkenheid(deltaRootEntiteitMatches, matchingStapelDecorator, nieuwePersoonDecorator, bestaandePersoon, true);
                    }
            }
        }
        maakKruimelpadLoggingEnUpdateContext(relatieType, stapelMatches, isActueel, context);

        return deltaRootEntiteitMatches;
    }

    private void markeerBetrokkenheid(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final StapelDecorator stapelDecorator,
        final PersoonDecorator ikPersoonDecorator,
        final Persoon bestaandePersoon,
        final boolean toevoegen)
    {
        for (final RelatieDecorator relatieDecorator : stapelDecorator.getRelaties()) {
            final BetrokkenheidDecorator ikBetrokkenheidDecorator = getIkBetrokkenheidDecorator(ikPersoonDecorator, relatieDecorator);
            final Betrokkenheid ikBetrokkenheid = ikBetrokkenheidDecorator.getBetrokkenheid();

            final DeltaRootEntiteit bestaandeEntiteit;
            final DeltaRootEntiteit nieuweEntiteit;
            if (toevoegen) {
                bestaandeEntiteit = null;
                nieuweEntiteit = ikBetrokkenheid;
            } else {
                bestaandeEntiteit = ikBetrokkenheid;
                nieuweEntiteit = null;
            }
            deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(bestaandeEntiteit, nieuweEntiteit, bestaandePersoon, Persoon.BETROKKENHEID_SET));
        }
    }

    private void matchKernInhoudelijk(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final StapelDecorator bestaandeStapelDecorator,
        final StapelDecorator nieuweStapelDecorator,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon,
        final Lo3RelatieType relatieType)
    {
        switch (relatieType) {
            case HUWELIJK_OF_GP:
                matchHuwelijkOfGpKernInhoud(deltaRootEntiteitMatches, bestaandePersoon, nieuwePersoon, bestaandeStapelDecorator, nieuweStapelDecorator);
                break;
            case KIND:
                matchKindKernInhoud(deltaRootEntiteitMatches, bestaandeStapelDecorator, nieuweStapelDecorator, bestaandePersoon, nieuwePersoon);
                break;
            default:
                throw new IllegalStateException("Illegaal relatietype voor deze enumeratie");
        }
    }

    private void matchRelatieKernInhoud(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon,
        final RelatieDecorator bestaandeRelatie,
        final RelatieDecorator nieuweRelatie)
    {
        if (bestaandeRelatie != null) {
            if (nieuweRelatie != null) {
                // Bepaal ik-betrokkenheid
                final BetrokkenheidDecorator bestaandeIkBetrokkenheid = getIkBetrokkenheidDecorator(bestaandePersoon, bestaandeRelatie);
                final BetrokkenheidDecorator nieuweIkBetrokkenheid = getIkBetrokkenheidDecorator(nieuwePersoon, nieuweRelatie);

                voegBetrokkenhedenMatchToe(
                    deltaRootEntiteitMatches,
                    bestaandeIkBetrokkenheid,
                    nieuweIkBetrokkenheid,
                    bestaandePersoon.getPersoon(),
                    Persoon.BETROKKENHEID_SET);

                // LO3 Kind en huwelijk/GP krijgen de volgende opmaak qua relatie:
                // Persoon - Betrokkenheid - Relatie - Betrokkenheid - Persoon

                final BetrokkenheidDecorator bestaandeGerelateerdeBetrokkenheid = bestaandeRelatie.getAndereBetrokkenheid(bestaandeIkBetrokkenheid);
                final BetrokkenheidDecorator nieuweGerelateerdeBetrokkenheid = nieuweRelatie.getAndereBetrokkenheid(nieuweIkBetrokkenheid);

                voegBetrokkenhedenMatchToe(
                    deltaRootEntiteitMatches,
                    bestaandeGerelateerdeBetrokkenheid,
                    nieuweGerelateerdeBetrokkenheid,
                    bestaandeRelatie.getRelatie(),
                    Relatie.BETROKKENHEID_SET);
                voegGerelateerdeMatchToe(
                    deltaRootEntiteitMatches,
                    bestaandeGerelateerdeBetrokkenheid,
                    nieuweGerelateerdeBetrokkenheid,
                    Betrokkenheid.PERSOON);
                voegRelatieMatchToe(deltaRootEntiteitMatches, bestaandeRelatie, nieuweRelatie);
            } else {
                // Als er wel een bestaandeRelatie is, maar geen nieuweRelatie -> Betrokkenheid markeren als te
                // verwijderen.
                final BetrokkenheidDecorator bestaandeIkBetrokkenheid = getIkBetrokkenheidDecorator(bestaandePersoon, bestaandeRelatie);
                voegBetrokkenhedenMatchToe(
                    deltaRootEntiteitMatches,
                    bestaandeIkBetrokkenheid,
                    null,
                    bestaandePersoon.getPersoon(),
                    Persoon.BETROKKENHEID_SET);
            }
        } else {
            if (nieuweRelatie != null) {
                // Als er wel een nieuweIkBetrokkenheid is, maar geen bestaandeIkBetrokkenheid -> Betrokkenheid,
                // relatie en gerelateerde persoon markeren als toe te voegen
                final BetrokkenheidDecorator nieuweIkBetrokkenheid = getIkBetrokkenheidDecorator(nieuwePersoon, nieuweRelatie);
                nieuwePersoon.verwijderBetrokkenheid(nieuweIkBetrokkenheid);

                voegBetrokkenhedenMatchToe(deltaRootEntiteitMatches, null, nieuweIkBetrokkenheid, bestaandePersoon.getPersoon(), Persoon.BETROKKENHEID_SET);
            }
        }
    }

    private BetrokkenheidDecorator getIkBetrokkenheidDecorator(final PersoonDecorator persoon, final RelatieDecorator relatie) {
        if (relatie == null) {
            return null;
        }
        return relatie.getBetrokkenheid(persoon);
    }

    private void matchKindKernInhoud(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final StapelDecorator bestaandeStapel,
        final StapelDecorator nieuweStapel,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon)
    {
        final List<RelatieDecorator> bestaandeRelaties = bestaandeStapel.getRelaties();
        final RelatieDecorator bestaandeRelatie = bestaandeRelaties.isEmpty() ? null : bestaandeRelaties.get(0);

        final List<RelatieDecorator> nieuweRelaties = nieuweStapel.getRelaties();
        final RelatieDecorator nieuweRelatie = nieuweRelaties.isEmpty() ? null : nieuweRelaties.get(0);

        matchRelatieKernInhoud(deltaRootEntiteitMatches, bestaandePersoon, nieuwePersoon, bestaandeRelatie, nieuweRelatie);
    }

    private void matchHuwelijkOfGpKernInhoud(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon,
        final StapelDecorator bestaandeStapel,
        final StapelDecorator nieuweStapel)
    {
        final List<RelatieDecorator> bestaandeRelaties = bestaandeStapel.getRelaties();
        final List<RelatieDecorator> nieuweRelaties = nieuweStapel.getRelaties();

        if (bestaandeRelaties.size() == 1 && nieuweRelaties.size() == 1) {
            final RelatieDecorator bestaandeRelatie = bestaandeRelaties.iterator().next();
            final RelatieDecorator nieuweRelatie = nieuweRelaties.iterator().next();

            matchRelatieKernInhoud(deltaRootEntiteitMatches, bestaandePersoon, nieuwePersoon, bestaandeRelatie, nieuweRelatie);
        } else {
            final Set<RelatieDecorator> matchendeNieuweRelaties = new HashSet<>();
            for (final RelatieDecorator bestaandeRelatie : bestaandeRelaties) {
                final RelatieDecorator matchendeNieuweRelatie = bestaandeRelatie.zoekMatchendeRelatie(nieuweRelaties);
                matchRelatieKernInhoud(deltaRootEntiteitMatches, bestaandePersoon, nieuwePersoon, bestaandeRelatie, matchendeNieuweRelatie);
                if (matchendeNieuweRelatie != null) {
                    matchendeNieuweRelaties.add(matchendeNieuweRelatie);
                }
            }

            // Andere relatie(s) vinden, deze zijn nieuw
            final Set<RelatieDecorator> nieuweOverigeRelaties = nieuweStapel.getOverigeRelaties(matchendeNieuweRelaties, true);
            for (final RelatieDecorator nieuweOverigeRelatie : nieuweOverigeRelaties) {
                matchRelatieKernInhoud(deltaRootEntiteitMatches, bestaandePersoon, nieuwePersoon, null, nieuweOverigeRelatie);
            }
        }
    }

    private void voegGerelateerdeMatchToe(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final BetrokkenheidDecorator bestaandeGerelateerdeBetrokkenheidDecorator,
        final BetrokkenheidDecorator nieuweGerelateerdeBetrokkenheidDecorator,
        final String eigenaarVeldnaam)
    {
        final PersoonDecorator bestaandeGerelateerdeDecorator = bestaandeGerelateerdeBetrokkenheidDecorator.getPersoonDecorator();
        final PersoonDecorator nieuweGerelateerdeDecorator =
                nieuweGerelateerdeBetrokkenheidDecorator == null ? null : nieuweGerelateerdeBetrokkenheidDecorator.getPersoonDecorator();

        final DeltaRootEntiteit bestaandeGerelateerde = bestaandeGerelateerdeDecorator == null ? null : bestaandeGerelateerdeDecorator.getPersoon();
        final DeltaRootEntiteit nieuweGerelateerde = nieuweGerelateerdeDecorator == null ? null : nieuweGerelateerdeDecorator.getPersoon();

        if (bestaandeGerelateerde == null && nieuweGerelateerde != null) {
            // Kan alleen in geval van puntouder naar volledige ouder
            final Persoon gerelateerdePersoon = (Persoon) nieuweGerelateerde;
            for (final Betrokkenheid betrokkenheid : gerelateerdePersoon.getBetrokkenheidSet()) {
                gerelateerdePersoon.removeBetrokkenheid(betrokkenheid);
            }
        }

        // Bij een punt ouder heb je geen gerelateerde
        if (bestaandeGerelateerde != null || nieuweGerelateerde != null) {
            deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(
                bestaandeGerelateerde,
                nieuweGerelateerde,
                bestaandeGerelateerdeBetrokkenheidDecorator.getBetrokkenheid(),
                eigenaarVeldnaam));
        }
    }

    private void voegStapelMatchToe(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final StapelDecorator bestaandeStapelDecorator,
        final StapelDecorator nieuweStapelDecorator,
        final DeltaRootEntiteit eigenaarRootEntiteit)
    {
        final DeltaRootEntiteit bestaandeStapel = bestaandeStapelDecorator == null ? null : bestaandeStapelDecorator.getStapel();
        final DeltaRootEntiteit nieuweStapel = nieuweStapelDecorator == null ? null : nieuweStapelDecorator.getStapel();

        deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(bestaandeStapel, nieuweStapel, eigenaarRootEntiteit, Persoon.STAPELS));
    }

    private void voegBetrokkenhedenMatchToe(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final BetrokkenheidDecorator bestaandeBetrokkenheidDecorator,
        final BetrokkenheidDecorator nieuweBetrokkenheidDecorator,
        final DeltaRootEntiteit eigenaarRootEntiteit,
        final String eigenaarVeldnaam)
    {
        final DeltaRootEntiteit nieuweBetrokkenheid;

        // Generatie van de sleutels voor delta + onderzoek
        if (nieuweBetrokkenheidDecorator != null) {
            nieuweBetrokkenheid = nieuweBetrokkenheidDecorator.getBetrokkenheid();
        } else {
            nieuweBetrokkenheid = null;
        }

        final DeltaRootEntiteit bestaandeBetrokkenheid =
                bestaandeBetrokkenheidDecorator == null ? null : bestaandeBetrokkenheidDecorator.getBetrokkenheid();

        deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(bestaandeBetrokkenheid, nieuweBetrokkenheid, eigenaarRootEntiteit, eigenaarVeldnaam));
    }

    private void voegRelatieMatchToe(
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches,
        final RelatieDecorator bestaandeRelatieDecorator,
        final RelatieDecorator nieuweRelatieDecorator)
    {
        final DeltaRootEntiteit bestaandeRelatie = bestaandeRelatieDecorator.getRelatie();
        final DeltaRootEntiteit nieuweRelatie = nieuweRelatieDecorator.getRelatie();

        deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(bestaandeRelatie, nieuweRelatie, null, null));
    }

    /**
     * Enumeratie om de type relatie, vanuit Lo3 gezien, aan te duiden.
     */
    private enum Lo3RelatieType {
        OUDER1, OUDER2, HUWELIJK_OF_GP, KIND, GEZAGSVERHOUDING
    }

    private Set<IstStapelMatch> converteerNaarIstStapelMatches(final StapelMatches<StapelDecorator> stapelMatches) {
        final Set<IstStapelMatch> istStapelMatches = new TreeSet<>(IstStapelMatch.getSorteerder());

        for (final StapelDecorator stapel : stapelMatches.getBestaandeStapels()) {
            istStapelMatches.add(new IstStapelMatch(stapel, stapelMatches.getMatchesVoorBestaandeStapel(stapel), stapelMatches.bepaalMatchType(
                stapel,
                true)));
        }

        // Alleen toevoegen als het match type NIEUW is. Anders is deze al meegenomen bij de bestaande stapels
        for (final StapelDecorator stapel : stapelMatches.getNieuweStapels()) {
            final StapelMatchType matchType = stapelMatches.bepaalMatchType(stapel, false);
            if (matchType.equals(StapelMatchType.STAPEL_NIEUW)) {
                istStapelMatches.add(new IstStapelMatch(stapel, stapelMatches.getMatchesVoorNieuweStapel(stapel), matchType));
            }
        }

        return istStapelMatches;
    }

    /**
     * Geeft de andere betrokkenheden, dan de meegegeven betrokkenheden, terug die gekoppeld zijn aan de relatie voor
     * het meegegeven aanduidingOuder.
     *
     * @param matchendeOuderBetrokkenheden
     *            bestaande ouder betrokkenheden uit deze relatie zodat de andere betrokkenheden goed geidentificeerd
     *            kunnen worden
     * @param ouderBetrokkenheden
     *            de ouder betrokkenheden van een bepaald LO3-voorkomen, zonder de ik-betrokkenheid.
     * @return de andere betrokkenheden die behoren tot de aanduidingOuder van deze relatie of een lege lijst.
     */
    private Set<BetrokkenheidDecorator> getOverigeOuderBetrokkenheden(
            final Set<BetrokkenheidDecorator> matchendeOuderBetrokkenheden,
            final Set<BetrokkenheidDecorator> ouderBetrokkenheden)
    {
        // Als er geen matchendeOuderBetrokkenheden zijn gevonden, dan is alles overig => toevoegen aan het resultaat
        // Als er wel matchendeOuderBetrokkenheden zijn gevonden, dan deze uit de lijst van ouderBetrokkenheden halen.

        final Set<BetrokkenheidDecorator> resultaat = new HashSet<>(ouderBetrokkenheden);

        if (!matchendeOuderBetrokkenheden.isEmpty()) {
            for (final BetrokkenheidDecorator matchingBetrokkenheid : matchendeOuderBetrokkenheden) {
                final BetrokkenheidDecorator gevondenBetrokkenheid = matchingBetrokkenheid.zoekMatchendeOuderBetrokkenheid(resultaat);
                if (gevondenBetrokkenheid != null) {
                    resultaat.remove(gevondenBetrokkenheid);
                }
            }
        }

        return resultaat;
    }
}

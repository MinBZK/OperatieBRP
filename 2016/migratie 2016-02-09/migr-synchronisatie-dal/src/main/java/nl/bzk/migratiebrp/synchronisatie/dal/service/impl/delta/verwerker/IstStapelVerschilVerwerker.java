/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingOuder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.BetrokkenheidDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.PersoonDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.RelatieDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelVoorkomenDecorator;

/**
 * Verwerker van de gevonden verschillen tussen de bestaande en de nieuwe versie van de {@link Stapel} objecten van een
 * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon}. Deze class is stateful en moet voor
 * elke lijst van verschillen op de {@link Stapel} objecten opnieuw worden geconstrueerd.
 */
public final class IstStapelVerschilVerwerker {

    private final Set<StapelDecorator> teVerwijderenStapels = new HashSet<>();
    private final Map<StapelDecorator, Set<StapelVoorkomenDecorator>> teVerwijderenVoorkomensPerStapel = new HashMap<>();

    /**
     * Verwerk alle wijzigingen die gevonden zijn mbt tot IST-stapels ({@link Stapel}).
     *
     * @param vergelijkerResultaat
     *            {@link VergelijkerResultaat} object met daarin de lijst met verschillen
     * @param context
     *            de context van deltabepaling.
     */
    public void verwerkWijzigingen(final VergelijkerResultaat vergelijkerResultaat, final DeltaBepalingContext context) {
        final PersoonDecorator bestaandePersoon = PersoonDecorator.decorate(context.getBestaandePersoon());
        final PersoonDecorator nieuwePersoon = PersoonDecorator.decorate(context.getNieuwePersoon());

        // Stapels verwijderen
        verwijderStapels(bestaandePersoon, vergelijkerResultaat.getVerschillen(VerschilType.RIJ_VERWIJDERD, null), context.getActieVervalTbvLeveringMuts());

        // StapelVoorkomens die verwijderd en opnieuw zijn toegevoegd als de stapel wel matched, maar de inhoud verder
        // niet
        verwijderenToevoegenVoorkomensBijStapel(vergelijkerResultaat);

        // verwijderen van relaties bij de stapel
        verwijderenRelaties(vergelijkerResultaat, bestaandePersoon);

        // Verplaatsen van stapels onderling
        herschikStapels(vergelijkerResultaat, bestaandePersoon);

        // Stapels toevoegen
        toevoegenStapels(vergelijkerResultaat, bestaandePersoon, nieuwePersoon);

        // StapelVoorkomens die nieuw zijn toegevoegd aan de stapel (hangt aan de stapel sleutel)
        toevoegenStapelVoorkomens(vergelijkerResultaat, bestaandePersoon);

        // toevoegen van relaties bij de stapel
        toevoegenRelaties(vergelijkerResultaat, bestaandePersoon);

        // voorkomens die als te verwijderen gemarkeerd zijn verwijderen
        verwijderGemarkeerdeVoorkomens();
        // Stapels die als 'leeg' gemarkeerd zijn, verwijderen
        bestaandePersoon.verwijderStapels(teVerwijderenStapels);
    }

    private void verwijderGemarkeerdeVoorkomens() {
        for (StapelDecorator stapel : teVerwijderenVoorkomensPerStapel.keySet()) {
            for (StapelVoorkomenDecorator voorkomen : teVerwijderenVoorkomensPerStapel.get(stapel)) {
                stapel.verwijderVoorkomen(voorkomen);
            }
            teVerwijderenVoorkomensPerStapel.remove(stapel);
        }
    }

    private void toevoegenStapels(
        final VergelijkerResultaat vergelijkerResultaat,
        final PersoonDecorator bestaandePersoon,
        final PersoonDecorator nieuwePersoon)
    {
        final Set<Verschil> toegevoegdeStapels = vergelijkerResultaat.getVerschillen(VerschilType.RIJ_TOEGEVOEGD, null);
        for (final Verschil verschil : toegevoegdeStapels) {
            final StapelDecorator nieuweStapel = StapelDecorator.decorate((Stapel) verschil.getNieuweWaarde());
            final StapelDecorator bestaandeStapel = zoekStapelBijPersoon(bestaandePersoon, verschil);

            if (bestaandeStapel != null) {
                verwerkNieuwVersieStapel(bestaandeStapel, nieuweStapel);
                teVerwijderenStapels.remove(bestaandeStapel);
            } else {
                bestaandePersoon.addStapel(nieuweStapel);
            }

            final Set<BetrokkenheidDecorator> nieuweBetrokkenheden = nieuwePersoon.zoekIkBetrokkenhedenVoorStapel(nieuweStapel);
            bestaandePersoon.addBetrokkenheden(nieuweBetrokkenheden);
        }
    }

    private void herschikStapels(final VergelijkerResultaat vergelijkerResultaat, final PersoonDecorator bestaandePersoon) {
        Verschil verplaatsteStapelVerschil = vergelijkerResultaat.getVerschil(VerschilType.ELEMENT_AANGEPAST);
        while (verplaatsteStapelVerschil != null) {
            final StapelDecorator bronStapel = zoekStapelBijPersoon(bestaandePersoon, verplaatsteStapelVerschil);
            verplaatsStapel(vergelijkerResultaat, bestaandePersoon, verplaatsteStapelVerschil, bronStapel, null);
            verplaatsteStapelVerschil = vergelijkerResultaat.getVerschil(VerschilType.ELEMENT_AANGEPAST);
        }
    }

    private void toevoegenStapelVoorkomens(final VergelijkerResultaat vergelijkerResultaat, final PersoonDecorator bestaandePersoon) {
        final Set<Verschil> nieuwVoorkomens = vergelijkerResultaat.getVerschillen(VerschilType.RIJ_TOEGEVOEGD, true, Stapel.STAPEL_VOORKOMENS);
        for (final Verschil verschil : nieuwVoorkomens) {
            final StapelVoorkomenDecorator stapelVoorkomen = (StapelVoorkomenDecorator) verschil.getNieuweWaarde();
            final StapelDecorator stapel = bestaandePersoon.zoekStapel(stapelVoorkomen.getStapel());
            Set<StapelVoorkomenDecorator> teVerwijderenVoorkomens = teVerwijderenVoorkomensPerStapel.get(stapel);
            if (teVerwijderenVoorkomens == null) {
                teVerwijderenVoorkomens = Collections.emptySet();
            }

            stapel.voegNieuwActueelVoorkomenToe(stapelVoorkomen, teVerwijderenVoorkomens);
        }
    }

    private void verwijderenRelaties(final VergelijkerResultaat vergelijkerResultaat, final PersoonDecorator bestaandePersoon) {
        final Set<Verschil> verwijderdeRijen = vergelijkerResultaat.getVerschillen(VerschilType.RIJ_VERWIJDERD, Stapel.RELATIES);
        toevoegenVerwijderenRelaties(verwijderdeRijen, bestaandePersoon, false);
    }

    private void toevoegenRelaties(final VergelijkerResultaat vergelijkerResultaat, final PersoonDecorator bestaandePersoon) {
        final Set<Verschil> toegevoegdeRijen = vergelijkerResultaat.getVerschillen(VerschilType.RIJ_TOEGEVOEGD, Stapel.RELATIES);
        toevoegenVerwijderenRelaties(toegevoegdeRijen, bestaandePersoon, true);
    }

    private void toevoegenVerwijderenRelaties(final Set<Verschil> verschillen, final PersoonDecorator bestaandePersoon, final boolean isToevoegenModus) {
        for (final Verschil verschil : verschillen) {
            final Sleutel sleutel = verschil.getSleutel();
            final IstSleutel istSleutel = (IstSleutel) sleutel;
            final StapelDecorator stapel = bestaandePersoon.zoekStapel(istSleutel.getCategorie(), istSleutel.getStapelnummer());
            if (isToevoegenModus) {
                stapel.koppelRelatie(RelatieDecorator.decorate((Relatie) verschil.getNieuweWaarde()));
            } else {
                stapel.ontkoppelRelatie(RelatieDecorator.decorate((Relatie) verschil.getOudeWaarde()));
            }
        }
    }

    private void verwijderStapels(
        final PersoonDecorator bestaandePersoon,
        final Set<Verschil> verwijderdeStapels,
        final BRPActie actieVervalTbvLeveringMuts)
    {
        for (final Verschil verschil : verwijderdeStapels) {
            final StapelDecorator stapel = zoekStapelBijPersoon(bestaandePersoon, verschil);

            if (stapel == null) {
                throw new IllegalStateException("Stapel niet gevonden, maar wel verschil op bepaald (rij verwijderd)");
            }

            final List<RelatieDecorator> bestaandeRelaties = stapel.getRelaties();
            for (final RelatieDecorator relatieDecorator : bestaandeRelaties) {
                final AanduidingOuder aanduidingOuder;
                if (stapel.isOuder1Stapel()) {
                    aanduidingOuder = AanduidingOuder.OUDER_1;
                } else if (stapel.isOuder2Stapel()) {
                    aanduidingOuder = AanduidingOuder.OUDER_2;
                } else {
                    aanduidingOuder = null;
                }
                relatieDecorator.laatVervallen(actieVervalTbvLeveringMuts, bestaandePersoon, aanduidingOuder);
            }

            stapel.ontkoppelRelaties();
            teVerwijderenStapels.add(stapel);
        }
    }

    private StapelDecorator zoekStapelBijPersoon(final PersoonDecorator bestaandePersoon, final Verschil verschil) {
        final IstSleutel sleutel = (IstSleutel) verschil.getSleutel();
        return bestaandePersoon.zoekStapel(sleutel.getCategorie(), sleutel.getStapelnummer());
    }

    private void verplaatsStapel(
        final VergelijkerResultaat vergelijkerResultaat,
        final PersoonDecorator bestaandePersoon,
        final Verschil verplaatsingVerschil,
        final StapelDecorator startStapel,
        final DummyStapel vorigeDummyStapel)
    {
        vergelijkerResultaat.verwijderVerschil(verplaatsingVerschil);

        final StapelDecorator bronStapel = zoekStapelBijPersoon(bestaandePersoon, verplaatsingVerschil);
        final StapelDecorator zoekStapel = (StapelDecorator) verplaatsingVerschil.getNieuweWaarde();

        StapelDecorator doelStapel = bestaandePersoon.zoekStapel(zoekStapel);
        DummyStapel kopieHuidigeStapel = null;

        final boolean wasDoelStapelLeeg;
        if (doelStapel == null) {
            doelStapel = bestaandePersoon.maakStapel(zoekStapel.getCategorie(), zoekStapel.getStapelNummer());
            wasDoelStapelLeeg = true;
        } else {
            // Er bestond al een stapel, deze inhoud dus als dummy tijdelijk opslaan
            kopieHuidigeStapel = new DummyStapel(doelStapel);
            wasDoelStapelLeeg = teVerwijderenStapels.remove(doelStapel);
        }
        Set<StapelVoorkomenDecorator> teverwijderenVoorkomens;
        if (vorigeDummyStapel == null) {
            teverwijderenVoorkomens = doelStapel.setVoorkomensEnRelaties(bronStapel.getVoorkomens(), bronStapel.getRelaties(), false);
        } else {
            teverwijderenVoorkomens = doelStapel.setVoorkomensEnRelaties(vorigeDummyStapel.getVoorkomens(), vorigeDummyStapel.getRelaties(), false);
        }
        if (!teverwijderenVoorkomens.isEmpty()) {
            teVerwijderenVoorkomensPerStapel.put(doelStapel, teverwijderenVoorkomens);
        }
        if (!teVerwijderenStapels.contains(startStapel) && !startStapel.equals(doelStapel)) {
            startStapel.ontkoppelRelaties();
            teVerwijderenStapels.add(startStapel);
        }

        if (!wasDoelStapelLeeg) {
            final Verschil verschil =
                    vergelijkerResultaat.zoekVerschil(new IstSleutel(doelStapel.getStapel(), Stapel.VOLGNUMMER, true), VerschilType.ELEMENT_AANGEPAST);
            if (verschil == null) {
                throw new IllegalStateException("Fout tijdens verplaatsen inhoud stapels. Geen verschil gevonden terwijl doelstapel niet leeg was.");
            }
            verplaatsStapel(vergelijkerResultaat, bestaandePersoon, verschil, startStapel, kopieHuidigeStapel);
        }
    }

    private void verwerkNieuwVersieStapel(final StapelDecorator bestaandeStapel, final StapelDecorator nieuweStapel) {
        // Verwijder de nieuwePersoon stapel bij de relaties
        final List<RelatieDecorator> nieuweStapelRelaties = nieuweStapel.getRelaties();
        for (final RelatieDecorator relatieDecorator : nieuweStapelRelaties) {
            relatieDecorator.removeStapel(nieuweStapel);
        }

        bestaandeStapel.setVoorkomensEnRelaties(nieuweStapel.getVoorkomens(), nieuweStapelRelaties, true);
    }

    private void verwijderenToevoegenVoorkomensBijStapel(final VergelijkerResultaat vergelijkerResultaat) {
        // Deze worden altijd in duos toegevoegd aan het resultaat tijdens vergelijken.
        final Set<Verschil> verwijderdeVoorkomens = vergelijkerResultaat.getVerschillen(VerschilType.ELEMENT_VERWIJDERD, null);

        for (final Verschil verschil : verwijderdeVoorkomens) {
            final StapelDecorator oudStapel = (StapelDecorator) verschil.getOudeWaarde();
            final Verschil nieuwStapelVerschil =
                    vergelijkerResultaat.zoekVerschil(new IstSleutel(oudStapel.getStapel(), false), VerschilType.ELEMENT_NIEUW);
            final StapelDecorator nieuwStapel = (StapelDecorator) nieuwStapelVerschil.getNieuweWaarde();

            oudStapel.setVoorkomens(nieuwStapel.getVoorkomens(), true);
        }
    }

    /**
     * Dummy stapel om de relaties en voorkomens uit een bestaande stapel tijdelijk op te slaan.
     */
    private static final class DummyStapel {
        private final List<RelatieDecorator> relaties = new ArrayList<>();
        private final Set<StapelVoorkomenDecorator> voorkomens = new HashSet<>();

        private DummyStapel(final StapelDecorator stapel) {
            setRelaties(stapel);
            setVoorkomens(stapel.getVoorkomens());
        }

        private List<RelatieDecorator> getRelaties() {
            return relaties;
        }

        private void setRelaties(final StapelDecorator stapel) {
            for (final RelatieDecorator relatie : stapel.getRelaties()) {
                relatie.removeStapel(stapel);
                this.relaties.add(relatie);
            }
        }

        private Set<StapelVoorkomenDecorator> getVoorkomens() {
            return voorkomens;
        }

        private void setVoorkomens(final Set<StapelVoorkomenDecorator> origineleSet) {
            for (final StapelVoorkomenDecorator origineel : origineleSet) {
                voorkomens.add(origineel.kopieer());
            }
        }
    }
}

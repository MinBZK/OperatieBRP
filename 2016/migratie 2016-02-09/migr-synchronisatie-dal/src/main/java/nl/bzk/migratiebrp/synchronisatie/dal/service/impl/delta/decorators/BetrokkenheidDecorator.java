/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.util.PersistenceUtil;

/**
 * Decorator voor {@link Betrokkenheid} met daarin logica die niet in de entiteit zit.
 */
public final class BetrokkenheidDecorator {

    private final Betrokkenheid betrokkenheid;

    /**
     * Maakt een PersoonDecorator object.
     *
     * @param betrokkenheid
     *            het object waaraan functionaliteit moet worden toegevoegd
     */
    private BetrokkenheidDecorator(final Betrokkenheid betrokkenheid) {
        ValidationUtils.controleerOpNullWaarden("Betrokkenheid mag niet null zijn", betrokkenheid);
        this.betrokkenheid = PersistenceUtil.getPojoFromObject(betrokkenheid);
    }

    /**
     * @param betrokkenheid
     *            het te decoreren Betrokkenheid object
     * @return een BetrokkenheidDecorator object
     */
    public static BetrokkenheidDecorator decorate(final Betrokkenheid betrokkenheid) {
        final BetrokkenheidDecorator result;
        if (betrokkenheid == null) {
            result = null;
        } else {
            result = new BetrokkenheidDecorator(PersistenceUtil.getPojoFromObject(betrokkenheid));
        }
        return result;
    }

    /**
     * Geeft het soort betrokkenheid terug.
     *
     * @return het {@link SoortBetrokkenheid} van deze betrokkenheid
     */
    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return betrokkenheid.getSoortBetrokkenheid();
    }

    /**
     * Geeft de betrokkenheid terug waar deze decorator voor gemaakt is.
     *
     * @return de betrokkenheid
     */
    public Betrokkenheid getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * Geeft de relatie waar deze betrokkenheid bij hoort.
     *
     * @return de relatie
     */
    public RelatieDecorator getRelatie() {
        return RelatieDecorator.decorate(betrokkenheid.getRelatie());
    }

    /**
     * Geeft de persoon van deze betrokkenheid terug als @{link PersoonDecorator}.
     *
     * @return de persoon
     */
    public PersoonDecorator getPersoonDecorator() {
        return PersoonDecorator.decorate(PersistenceUtil.getPojoFromObject(betrokkenheid.getPersoon()));
    }

    /**
     * Verwijderd de persoon bij deze betrokkenheid, daarmee wordt de betrokkenheid los gekoppeld van de persoon.
     */
    public void removePersoon() {
        betrokkenheid.setPersoon(null);
    }

    /**
     * Zoekt in de meegegeven set van betrokkenheden een (deels) matchende betrokkenheid.<br>
     * In de volgende gevallen is het GEEN match
     * <UL>
     * <LI>Als de datum aanvang geldigheid niet overeenkomt</LI>
     * <LI>Als de datum einde geldigheid niet null is van deze betrokkenheid en niet overeenkomt</LI>
     * <LI>Als er meer dan 1 gedeeltelijke overeenkomst is(datumAanvangGeldigheid is gelijk en datumEindeGeldigheid is
     * null)</LI>
     * </UL>
     *
     * @param betrokkenheden
     *            de set van relaties waar een mogelijk match in zit.
     * @return de betrokkenheid wat matched met de huidige instantie.
     */
    public BetrokkenheidDecorator zoekMatchendeOuderBetrokkenheid(final Set<BetrokkenheidDecorator> betrokkenheden) {
        final List<BetrokkenheidDecorator> partialMatches = new ArrayList<>();
        final List<BetrokkenheidDecorator> volledigeMatches = new ArrayList<>();

        for (final BetrokkenheidDecorator mogelijkeMatchendeBetrokkenheid : betrokkenheden) {
            final Set<BetrokkenheidOuderHistorie> ouderHistorieSet = betrokkenheid.getBetrokkenheidOuderHistorieSet();
            final Set<BetrokkenheidOuderHistorie> zoekOuderHistorieSet =
                    mogelijkeMatchendeBetrokkenheid.getBetrokkenheid().getBetrokkenheidOuderHistorieSet();
            if (!ouderHistorieSet.isEmpty() && !zoekOuderHistorieSet.isEmpty()) {
                final BetrokkenheidOuderHistorie ouderHistorie = ouderHistorieSet.iterator().next();
                final BetrokkenheidOuderHistorie zoekOuderHistorie = zoekOuderHistorieSet.iterator().next();

                final Integer datumAanvangGeldigheid = ouderHistorie.getDatumAanvangGeldigheid();
                final Integer zoekDatumAanvangGeldigheid = zoekOuderHistorie.getDatumAanvangGeldigheid();
                final Integer datumEindeGeldigheid = ouderHistorie.getDatumEindeGeldigheid();
                final Integer zoekDatumEindeGeldigheid = zoekOuderHistorie.getDatumEindeGeldigheid();

                if (Objects.equals(datumAanvangGeldigheid, zoekDatumAanvangGeldigheid)) {
                    final boolean eindDatumMatched = Objects.equals(datumEindeGeldigheid, zoekDatumEindeGeldigheid);
                    // als einddatum leeg is en einddatumMatched is true -> match
                    // als einddatum gevuld is en einddatumMatched is true -> match
                    // als einddatum leeg is en einddatumMatched is false -> partial match
                    // als einddatum gevuld is en einddatumMatched is false -> geen match

                    if (eindDatumMatched) {
                        volledigeMatches.add(mogelijkeMatchendeBetrokkenheid);
                    } else if (datumEindeGeldigheid == null) {
                        partialMatches.add(mogelijkeMatchendeBetrokkenheid);
                    }
                }
            }

        }

        if (volledigeMatches.size() > 1) {
            throw new IllegalStateException("Meer betrokkenheden matchen voor de gezochte betrokkenheid");
        }

        final BetrokkenheidDecorator result;
        if (!volledigeMatches.isEmpty()) {
            result = volledigeMatches.get(0);
        } else if (partialMatches.size() == 1) {
            result = partialMatches.get(0);
        } else {
            result = null;
        }

        return result;
    }

    /**
     * Laat deze betrokkenheid vervallen. Maw de historie van de betrokkenheid wordt geconverteerd naar M-rijen. Ook
     * wordt de bijbehorende persoon vervallen. Tenzij deze persoon de persoon is waarvan we de persoonslijst aan het
     * bewerken zijn.
     * 
     * @param actieVervalTbvLeveringMuts
     *            de actie tbv actie verval levering mutaties.
     * @param eigenPersoon
     *            de persoon waarmee we qua persoonslijst mee bezig zijn.
     */
    public void laatVervallen(final BRPActie actieVervalTbvLeveringMuts, final PersoonDecorator eigenPersoon) {
        for (final BetrokkenheidHistorie historie : betrokkenheid.getBetrokkenheidHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }

        for (final BetrokkenheidOuderHistorie historie : betrokkenheid.getBetrokkenheidOuderHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }

        for (final BetrokkenheidOuderlijkGezagHistorie historie : betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet()) {
            historie.converteerNaarMRij(actieVervalTbvLeveringMuts);
        }

        final PersoonDecorator betrokkenPersoonDecorator = getPersoonDecorator();
        if (!betrokkenPersoonDecorator.equals(eigenPersoon) && betrokkenPersoonDecorator.kanVervallen()) {
            betrokkenPersoonDecorator.laatVervallen(actieVervalTbvLeveringMuts);
        }
    }
}

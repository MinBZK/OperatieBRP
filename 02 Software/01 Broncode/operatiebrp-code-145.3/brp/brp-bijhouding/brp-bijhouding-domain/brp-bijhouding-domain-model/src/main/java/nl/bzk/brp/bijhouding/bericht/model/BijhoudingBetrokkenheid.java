/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegateBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;

/**
 * Deze decorator class voegt bijhoudingsfunctionaliteit toe aan de betrokkenheid entiteit.
 */
public final class BijhoudingBetrokkenheid extends AbstractDelegateBetrokkenheid implements BijhoudingEntiteit {
    private static final long serialVersionUID = 1;

    /**
     * Maakt een nieuwe BijhoudingBetrokkenheid object.
     * @param delegate de betrokkenheid die moet worden uigebreid met bijhoudingsfunctionaliteit
     */
    private BijhoudingBetrokkenheid(final Betrokkenheid delegate) {
        super(delegate);
    }

    /**
     * Maakt een nieuwe BijhoudingBetrokkenheid object.
     * @param delegate de betrokkenheid die moet worden uigebreid met bijhoudingsfunctionaliteit
     * @return een betrokkenheid met bijhoudingsfunctionaliteit
     */
    public static BijhoudingBetrokkenheid decorate(final Betrokkenheid delegate) {
        if (delegate == null) {
            return null;
        }
        delegate.getRelatie().addBetrokkenheid(delegate);
        return new BijhoudingBetrokkenheid(delegate);
    }

    /**
     * Voegt betrokkenheid historie toe aan deze betrokkenheid entiteit.
     * @param actie de actie inhoud voor het nieuwe historie voorkomen
     */
    public void voegBetrokkenheidHistorieToe(final BRPActie actie) {
        final BetrokkenheidHistorie betrokkenheidHistorie = new BetrokkenheidHistorie(getDelegate());
        BijhoudingEntiteit.voegFormeleHistorieToe(betrokkenheidHistorie, actie, getBetrokkenheidHistorieSet());
    }

    /**
     * Voegt een persoon uit het bijhoudingverzoekbericht toe aan deze betrokkenheid entiteit.
     * @param persoon de persoon uit het bijhoudingverzoek
     * @param actie de actie inhoud
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     */
    public void voegPersoonToe(final PersoonGegevensElement persoon, final BRPActie actie, final int datumAanvangGeldigheid) {
        final BijhoudingPersoon persoonEntiteit;
        if (persoon.heeftPersoonEntiteit()) {
            persoonEntiteit = persoon.getPersoonEntiteit();
        } else {
            persoonEntiteit = persoon.maakPseudoPersoonEntiteit(actie, datumAanvangGeldigheid);
        }
        persoonEntiteit.addBetrokkenheid(getDelegate());
    }

    /**
     * Voegt een ouderschap toe aan de betrokkenheid.
     * @param ouderschap het {@link OuderschapElement} uit het bericht
     * @param actie de actie inhoud
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     */
    public void voegOuderschapToe(final OuderschapElement ouderschap, final BRPActie actie, final int datumAanvangGeldigheid) {
        final BetrokkenheidOuderHistorie ouderHistorie = new BetrokkenheidOuderHistorie(this.getDelegate());
        ouderHistorie.setIndicatieOuderUitWieKindIsGeboren(ouderschap != null ? ouderschap.getIndicatieOuderUitWieKindIsGeboren().getWaarde() : null);
        BijhoudingEntiteit.voegMaterieleHistorieToe(ouderHistorie, actie, datumAanvangGeldigheid, getBetrokkenheidOuderHistorieSet());
    }
}

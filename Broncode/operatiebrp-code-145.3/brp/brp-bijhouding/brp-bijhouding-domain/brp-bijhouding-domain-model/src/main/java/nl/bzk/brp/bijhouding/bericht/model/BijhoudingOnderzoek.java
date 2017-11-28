/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegateOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;

/**
 * Deze decorator class voegt bijhoudingsfunctionaliteit toe aan de onderzoek entiteit.
 */
public final class BijhoudingOnderzoek extends AbstractDelegateOnderzoek implements BijhoudingEntiteit {
    private static final long serialVersionUID = 1;

    /**
     * Maakt een nieuwe BijhoudingOnderzoek object.
     * @param delegate het onderzoek dat moet worden uigebreid met bijhoudingsfunctionaliteit
     */
    private BijhoudingOnderzoek(final Onderzoek delegate) {
        super(delegate);
    }

    /**
     * Maakt een nieuwe BijhoudingOnderzoek object.
     * @param delegate het onderzoek dat moet worden uigebreid met bijhoudingsfunctionaliteit
     * @return een onderzoek met bijhoudingsfunctionaliteit
     */
    public static BijhoudingOnderzoek decorate(final Onderzoek delegate) {
        if (delegate == null) {
            return null;
        }
        return new BijhoudingOnderzoek(delegate);
    }

    /**
     * Deze methode wijzigt de status van dit onderzoek door een nieuw {@link OnderzoekHistorie} voorkomen te maken o.b.v. het actuele voorkomen, hiervan
     * wordt de status gewijzigd in de gegeven status.
     * @param actie de verantwoording voor deze status wijziging
     * @param statusOnderzoek de nieuwe status van dit onderzoek
     */
    public void wijzigStatusOnderzoek(final BRPActie actie, final StatusOnderzoek statusOnderzoek) {
        final OnderzoekHistorie actueelHistorieVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(getOnderzoekHistorieSet());
        final OnderzoekHistorie nieuwHistorieVoorkomen = new OnderzoekHistorie(actueelHistorieVoorkomen);
        nieuwHistorieVoorkomen.setStatusOnderzoek(statusOnderzoek);
        BijhoudingEntiteit.voegFormeleHistorieToe(nieuwHistorieVoorkomen, actie, getOnderzoekHistorieSet());
    }
}

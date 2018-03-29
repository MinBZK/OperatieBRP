/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;

/**
 * De interface voor huwelijk of geregeistreerd partnerschap uit het bijhoudingsbericht.
 */
public interface HuwelijkOfGpElement extends RelatieElement {

    /**
     * Zoekt in de relatie entiteit naar een betrokken pseudo persoon die voldoet aan de sleutels uit het bericht en
     * werkt deze persoon bij conform de gegeven groep in het bijhoudingsbericht.
     *
     * @param actie de actie met verantwoording voor het einde van de relatie
     * @param bericht het bericht waar dit element deel vanuit maakt
     * @param datumAanvangGeldigheid de datum aanvang geldigheid die gebruikt wordt voor het aanmaken van groepen met materiele historie
     */
    void werkPseudoPersoonInRelatieEntiteitBij(BRPActie actie, BijhoudingVerzoekBericht bericht, DatumElement datumAanvangGeldigheid);
}

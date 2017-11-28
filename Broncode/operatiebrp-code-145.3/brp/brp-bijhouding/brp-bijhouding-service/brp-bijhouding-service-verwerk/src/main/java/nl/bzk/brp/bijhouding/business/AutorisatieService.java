/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;

/**
 * Autorisatie service interface voor de bijhouding.
 */
public interface AutorisatieService {

    /**
     * Controleert of er een autorisatie bestaat voor het gegeven bericht.
     *
     * @param bericht het bericht
     * @return de lijst met autorisatie meldingen
     */
    List<MeldingElement> autoriseer(BijhoudingVerzoekBericht bericht);
}

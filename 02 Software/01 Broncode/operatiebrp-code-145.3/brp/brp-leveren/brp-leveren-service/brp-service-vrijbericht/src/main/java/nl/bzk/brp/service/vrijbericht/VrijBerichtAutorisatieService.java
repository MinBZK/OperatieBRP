/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import nl.bzk.brp.service.algemeen.autorisatie.AutorisatieException;

/**
 * Service om de autorisatie voor vrije berichten te controleren.
 */
interface VrijBerichtAutorisatieService {

    /**
     * @param verzoek verzoek
     * @throws AutorisatieException indien autorisatie faalt
     */
    void valideerAutorisatie(VrijBerichtVerzoek verzoek) throws AutorisatieException;

    /**
     * Controleer autorisatie voor vrij bericht verzoek.
     * @param verzoek vrijBericht verzoek
     * @throws AutorisatieException indien autorisatie faalt
     */
    void valideerAutorisatieBrpZender(VrijBerichtVerzoek verzoek) throws AutorisatieException;

    /**
     * @param verzoek verzoek
     * @throws AutorisatieException indien autorisatie faalt
     */
    void valideerAutorisatieBrpOntvanger(VrijBerichtVerzoek verzoek) throws AutorisatieException;
}

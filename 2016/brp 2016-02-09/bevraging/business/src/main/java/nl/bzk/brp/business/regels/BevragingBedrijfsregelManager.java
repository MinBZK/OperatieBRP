/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;

/**
 * Bedrijfsregelmanager voor bevraging.
 */
public interface BevragingBedrijfsregelManager extends BedrijfsregelManager {

    /**
     * Geef de regels die uitgevoerd moeten worden voor het bericht wordt verwerkt.
     *
     * @param soortBericht type bericht waarvoor de regels moeten gelden
     * @return lijst met regels
     */
    List<RegelInterface> getUitTeVoerenRegelsVoorBericht(SoortBericht soortBericht);

    /**
     * Geef de regels uit te voeren voor verwerking.
     *
     * @param soortBericht type bericht waarvoor de regels moeten gelden
     * @return lijst met regels
     */
    List<RegelInterface> getUitTeVoerenRegelsVoorVerwerking(SoortBericht soortBericht);

    /**
     * Geef de regels uit te voeren na de verwerking.
     *
     * @param soortBericht type bericht waarvoor de regels moeten gelden
     * @return lijst met regels
     */
    List<RegelInterface> getUitTeVoerenRegelsNaVerwerking(SoortBericht soortBericht);

}

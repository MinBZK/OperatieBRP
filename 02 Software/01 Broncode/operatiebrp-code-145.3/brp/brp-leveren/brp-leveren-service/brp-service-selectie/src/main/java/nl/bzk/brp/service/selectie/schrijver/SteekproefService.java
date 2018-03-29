/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;

/**
 * Service voor het maken van een steekproefbestand.
 */
public interface SteekproefService {

    /**
     * Het maximaal aantal personen dat opgenomen wordt in een steekproefbestand.
     * @return aantal personen
     */
    int maxPersonenInSteekproef();

    /**
     * Maakt het steekproef bestand.
     * @param taak de {@link MaakSelectieResultaatTaak}
     * @throws SelectieResultaatVerwerkException als het steekproefbestand niet gemaakt kan worden
     */
    void maakSteekproefBestand(MaakSelectieResultaatTaak taak) throws SelectieResultaatVerwerkException;
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import java.util.List;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;

/**
 * MaakSelectieResultaatTaakPublicatieService.
 */
public interface MaakSelectieResultaatTaakPublicatieService {
    /**
     * Publiceer maak selectie resultaat taken.
     * @param maakSelectieResultaatTaken maakSelectieResultaatTaken
     */
    void publiceerMaakSelectieResultaatTaken(List<MaakSelectieResultaatTaak> maakSelectieResultaatTaken);

    /**
     * Publiceer maak selectie geen resultaat netwerk taken.
     * @param maakSelectieGeenResultaatNetwerkTaken maakSelectieGeenResultaatNetwerkTaken
     */
    void publiceerMaakSelectieGeenResultaatNetwerkTaak(List<MaakSelectieResultaatTaak> maakSelectieGeenResultaatNetwerkTaken);
}

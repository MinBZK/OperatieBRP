/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import java.util.Collection;

/**
 * Interface van de service voor het filteren van berekende selectietaken op basis van de al gepersisteerde selectietaken. Als een gepersisteerde taak geen
 * foutieve eindstatus heeft, wordt de berekende taak dit het dichtst in de buurt ligt van deze taak verwijderd.
 */
interface OverlappendeSelectieTaakFilterService {

    /**
     * Verwerk de selectietaken.
     *
     * @param berekendeTaakDtos de berekende taak DTO's
     * @param gepersisteerdeTaakDtos de gepersisteerde taak DTO's
     * @return de gefilterde collectie
     */
    Collection<SelectieTaakDTO> filter(Collection<SelectieTaakDTO> berekendeTaakDtos,
                                       Collection<SelectieTaakDTO> gepersisteerdeTaakDtos);
}

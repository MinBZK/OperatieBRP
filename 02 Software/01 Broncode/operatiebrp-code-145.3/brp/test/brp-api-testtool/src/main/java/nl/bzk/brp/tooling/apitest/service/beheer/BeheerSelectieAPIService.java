/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * API service voor Beheer Selectie.
 */
public interface BeheerSelectieAPIService extends Stateful {

    /**
     * Maak selectietaken obv de sectieList resource.
     * @param sectieList een lijst van {@link DslSectie}s
     */
    void maakSelectietaken(List<DslSectie> sectieList);

    /**
     * Haal de berekende taken op binnen een bepaalde periode.
     * @param beginDatum de begindatum van de periode
     * @param eindDatum de einddatum van de periode
     */
    void geefBerekendeTaken(String beginDatum, String eindDatum);

    /**
     * Vergelijk de berekende selectietaken met een lijst verwachte taken.
     * @param aantalTaken
     * @param verwachteTaken de lijst met verwachte taken
     */
    void assertBerekendeTaken(Integer aantalTaken, List<Map<String, String>> verwachteTaken);
}

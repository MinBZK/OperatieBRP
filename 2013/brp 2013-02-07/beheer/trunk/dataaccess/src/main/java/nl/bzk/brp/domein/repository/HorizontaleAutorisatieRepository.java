/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.repository;

import java.util.List;


/**
 * Repository voor het filteren van een lijst van personen ids op basis van een of twee opgegeven populatie criteria
 * filters. Deze filters retourneren de lijst van persoonids die zowel behoren tot de reeds opgegeven persoonids als
 * voldoen aan de opgegevens filter(s).
 */
public interface HorizontaleAutorisatieRepository {

    /**
     * Retourneert een lijst met daarin die ids van personen die zowel deel uitmaken van de opgegeven lijst van
     * persoon ids als voldoen aan de opgegeven JPQL querie(s).
     *
     * @param persoonIds lijst van persoonids die gefilterd moet worden.
     * @param filters JPQL querie(s) waarop gefilterd moet worden.
     * @return een lijst van persoon ids van personen die zowel in de opgegeven lijst voorkomen als voldoen aan
     *         beide van de opgegeven JPQL filter queries.
     */
    List<Long> filterPersonenBijFilter(List<Long> persoonIds, String... filters);

}

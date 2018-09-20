/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.partijen.service;

import nl.bzk.brp.beheer.model.Partij;
import org.displaytag.pagination.PaginatedListImpl;


/**
 * Service partij te zoeken en te bewerken.
 *
 */
public interface PartijService {

    /**
     * Haal het partij op met de id.
     *
     * @param id de id van het partij
     * @return het partij
     */
    Partij ophalenPartij(final int id);

    /**
     * Zoek partijen op met de opgegeven waarde.
     *
     * @param waarde zoek waarde
     * @param paginatedList PaginatedList
     * @return PaginatedList met resultaten
     */
    PaginatedListImpl<Partij> zoekPartij(final String waarde, final PaginatedListImpl<Partij> paginatedList);

    /**
     *
     * @param partij Partij
     * @return nieuwe referentie naar de opgeslagen partij
     */
    Partij opslaan(final Partij partij);
}

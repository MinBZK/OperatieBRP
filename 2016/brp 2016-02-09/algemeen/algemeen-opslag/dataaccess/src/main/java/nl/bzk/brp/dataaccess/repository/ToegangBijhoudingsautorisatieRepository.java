/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;


import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangBijhoudingsautorisatie;

/**
 * De interface voor de methoden die met toegang leveringsautorisaties te maken hebben.
 */
public interface ToegangBijhoudingsautorisatieRepository {

    /**
     * Haal een {@link ToegangBijhoudingsautorisatie} op voor een partij met een bepaalde code.
     * @param partijcode de partijcode op basis waarvan de autorisatie opgehaald dient te worden
     * @return de gevonden {@link ToegangBijhoudingsautorisatie} of null
     */
    List<ToegangBijhoudingsautorisatie> geefToegangBijhoudingsautorisatie(Integer partijcode);
}

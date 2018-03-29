/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import java.util.Collection;

/**
 * Service API interface welke de standaardfuncties van selectietaken beschrijft.
 */
public interface SelectieTaakService {

    /**
     * Geef alle selectietaken binnen een periode.
     * @return alle selectietaken
     * @param selectiePeriode de selectieperiode
     */
    Collection<SelectieTaakDTO> getSelectieTaken(SelectiePeriodeDTO selectiePeriode);

    /**
     * Sla een selectietaak op.
     * @param selectieTaak de selectietaak
     * @return de opgeslagen selectietaak
     */
    SelectieTaakDTO slaSelectieTaakOp(SelectieTaakDTO selectieTaak);
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.verwerker;

import java.util.Collection;
import javax.annotation.Nonnull;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;

/**
 * Publicatie van plaatsen/verwijderen afnemerindicatie verzoeken voor selecties.
 */
public interface AfnemerindicatieVerzoekPublicatieService {

    /**
     * Publiceer een collectie van {@link SelectieAfnemerindicatieTaak}.
     * @param verzoeken de verzoeken
     */
    void publiceerAfnemerindicatieVerzoeken(@Nonnull Collection<SelectieAfnemerindicatieTaak> verzoeken);
}

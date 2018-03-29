/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.dal;

import java.time.LocalDate;
import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;

/**
 * Repository voor {@link Selectietaak}.
 */
public interface SelectieTaakRepository  {

    /**
     * Geef de geldige {@link Selectietaak selectietaken} binnen een periode.
     * @param beginDatum de begindatum van de periode
     * @param eindDatum de einddatum van de periode
     * @return de geldige selectietaken
     */
    Collection<Selectietaak> getGeldigeSelectieTakenBinnenPeriode(LocalDate beginDatum, LocalDate eindDatum);

    /**
     * Sla een selectietaak op.
     * @param selectietaak de selectietaak
     * @return de opgeslagen selectietaak
     */
    Selectietaak slaOp(Selectietaak selectietaak);

    /**
     * Vind een selectietaak op basis van het ID.
     * @param id het ID
     * @return de opgeslagen selectietaak
     */
    Selectietaak vindSelectietaak(Integer id);
}

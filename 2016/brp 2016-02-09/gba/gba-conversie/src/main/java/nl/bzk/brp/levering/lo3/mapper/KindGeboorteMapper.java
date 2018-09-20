/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt de geboorte (van een kind).
 */
@Component
public final class KindGeboorteMapper extends AbstractGeboorteMapper {

    /**
     * Constructor.
     */
    public KindGeboorteMapper() {
        super(ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_TIJDSTIPVERVAL,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE,
              ElementEnum.GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt de samengestelde naam (van een partner).
 */
@Component
public final class HuwelijkSamengesteldeNaamMapper extends AbstractSamengesteldeNaamMapper {

    /**
     * Constructor.
     */
    public HuwelijkSamengesteldeNaamMapper() {
        super(ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS,
              ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
    }
}

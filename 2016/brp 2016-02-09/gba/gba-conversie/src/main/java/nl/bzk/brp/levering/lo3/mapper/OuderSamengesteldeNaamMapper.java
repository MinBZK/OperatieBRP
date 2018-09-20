/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt de samengestelde naam (van een ouder).
 */
@Component
public final class OuderSamengesteldeNaamMapper extends AbstractSamengesteldeNaamMapper {

    /**
     * Constructor.
     */
    public OuderSamengesteldeNaamMapper() {
        super(ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS,
              ElementEnum.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
    }
}

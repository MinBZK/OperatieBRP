/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Mapt de samengestelde naam (van een geregistreerd partnerschap).
 */
@Component
public final class GeregistreerdPartnerschapSamengesteldeNaamMapper extends AbstractSamengesteldeNaamMapper {

    /**
     * Constructor.
     */
    public GeregistreerdPartnerschapSamengesteldeNaamMapper() {
        super(ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS,
              ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonOverlijdenHistorie;

import org.springframework.stereotype.Component;

/**
 * Map overlijden van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpOverlijdenMapper extends BrpMapper<PersoonOverlijdenHistorie, BrpOverlijdenInhoud> {

    @Override
    protected BrpOverlijdenInhoud mapInhoud(final PersoonOverlijdenHistorie historie) {
        // @formatter:off
        return new BrpOverlijdenInhoud(
                BrpMapperUtil.mapDatum(historie.getDatumOverlijden()), 
                BrpMapperUtil.mapBrpGemeenteCode(historie.getPartij()), 
                BrpMapperUtil.mapBrpPlaatsCode(historie.getPlaats()), 
                historie.getBuitenlandsePlaatsOverlijden(), 
                historie.getBuitenlandseRegioOverlijden(), 
                BrpMapperUtil.mapBrpLandCode(historie.getLand()),
                historie.getOmschrijvingLocatieOverlijden()
            );
        // @formatter:on
    }
}

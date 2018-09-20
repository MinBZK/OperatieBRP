/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeboorteHistorie;

import org.springframework.stereotype.Component;

/**
 * Map geboorte van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpGeboorteMapper extends BrpMapper<PersoonGeboorteHistorie, BrpGeboorteInhoud> {

    @Override
    protected BrpGeboorteInhoud mapInhoud(final PersoonGeboorteHistorie historie) {
        // @formatter:off
        return new BrpGeboorteInhoud(
                BrpMapperUtil.mapDatum(historie.getDatumGeboorte()), 
                BrpMapperUtil.mapBrpGemeenteCode(historie.getPartij()),
                BrpMapperUtil.mapBrpPlaatsCode(historie.getPlaats()), 
                historie.getBuitenlandseGeboorteplaats(),
                historie.getBuitenlandseRegioGeboorte(),
                BrpMapperUtil.mapBrpLandCode(historie.getLand()),
                historie.getOmschrijvingGeboortelocatie()
            );
        // @formatter:on
    }

}

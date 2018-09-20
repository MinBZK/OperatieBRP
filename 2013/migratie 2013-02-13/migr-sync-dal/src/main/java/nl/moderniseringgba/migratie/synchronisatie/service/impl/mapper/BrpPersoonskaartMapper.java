/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonPersoonskaartHistorie;

import org.springframework.stereotype.Component;

/**
 * Map persoonskaart van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpPersoonskaartMapper extends BrpMapper<PersoonPersoonskaartHistorie, BrpPersoonskaartInhoud> {

    @Override
    protected BrpPersoonskaartInhoud mapInhoud(final PersoonPersoonskaartHistorie historie) {
        // @formatter:off
        return new BrpPersoonskaartInhoud(
                BrpMapperUtil.mapBrpGemeenteCode(historie.getPartij()), 
                historie.getIndicatiePersoonskaartVolledigGeconverteerd()
            );
        // @formatter:on
    }

}

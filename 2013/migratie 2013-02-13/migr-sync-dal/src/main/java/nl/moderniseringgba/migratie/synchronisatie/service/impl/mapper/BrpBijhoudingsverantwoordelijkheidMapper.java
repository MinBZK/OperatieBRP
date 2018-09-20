/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonBijhoudingsverantwoordelijkheidHistorie;

import org.springframework.stereotype.Component;

/**
 * Map bijhoudingsverantwoordelijkheid van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpBijhoudingsverantwoordelijkheidMapper extends
        BrpMapper<PersoonBijhoudingsverantwoordelijkheidHistorie, BrpBijhoudingsverantwoordelijkheidInhoud> {

    @Override
    protected BrpBijhoudingsverantwoordelijkheidInhoud mapInhoud(
            final PersoonBijhoudingsverantwoordelijkheidHistorie historie) {
        // @formatter:off
        return new BrpBijhoudingsverantwoordelijkheidInhoud(
                BrpMapperUtil.mapBrpVerantwoordelijkCode(historie.getVerantwoordelijke()), 
                null // Gegevensset element
            );
        // @formatter:on
    }
}

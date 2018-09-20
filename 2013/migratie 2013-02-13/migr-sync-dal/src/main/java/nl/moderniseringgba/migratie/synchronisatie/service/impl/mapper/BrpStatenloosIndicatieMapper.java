/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatieHistorie;

import org.springframework.stereotype.Component;

/**
 * Map statenloos indicatie van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpStatenloosIndicatieMapper extends
        BrpMapper<PersoonIndicatieHistorie, BrpStatenloosIndicatieInhoud> {

    @Override
    protected BrpStatenloosIndicatieInhoud mapInhoud(final PersoonIndicatieHistorie historie) {
        return new BrpStatenloosIndicatieInhoud(historie.getWaarde());
    }
}

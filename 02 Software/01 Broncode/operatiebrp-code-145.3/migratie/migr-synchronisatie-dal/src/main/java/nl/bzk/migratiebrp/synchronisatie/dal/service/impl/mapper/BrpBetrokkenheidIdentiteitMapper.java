/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map ouderlijk gezag van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpBetrokkenheidIdentiteitMapper extends AbstractBrpMapper<BetrokkenheidHistorie, BrpIdentiteitInhoud> {

    @Override
    protected BrpIdentiteitInhoud mapInhoud(final BetrokkenheidHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        return BrpIdentiteitInhoud.IDENTITEIT;
    }
}

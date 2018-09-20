/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstAttenderingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;

import org.springframework.stereotype.Component;

/**
 * Map dienst attendering van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDienstAttenderingMapper extends AbstractBrpMapper<DienstAttenderingHistorie, BrpDienstAttenderingInhoud> {

    /**
     * Map een database entiteit partij naar een BRP conversie model object.
     *
     * @param dienstAttenderingHistorieSet
     *            database entiteit
     * @return conversie model object
     */
    public BrpStapel<BrpDienstAttenderingInhoud> mapDienstAttendering(final Set<DienstAttenderingHistorie> dienstAttenderingHistorieSet) {
        return map(dienstAttenderingHistorieSet, null);
    }

    @Override
    protected BrpDienstAttenderingInhoud mapInhoud(final DienstAttenderingHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        return new BrpDienstAttenderingInhoud(historie.getAttenderingscriterium());
    }
}

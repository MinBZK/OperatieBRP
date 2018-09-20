/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;

import org.springframework.stereotype.Component;

/**
 * Map partij van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpPartijMapper extends AbstractBrpMapper<PartijHistorie, BrpPartijInhoud> {

    /**
     * Map een database entiteit partij naar een BRP conversie model object.
     *
     * @param partij
     *            database entiteit
     * @param brpOnderzoekMapper
     *            De mapper voor onderzoeken
     * @return conversie model object
     */
    public BrpPartij mapPartij(final Partij partij, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpStapel<BrpPartijInhoud> partijStapel = map(partij.getHisPartijen(), brpOnderzoekMapper);
        return new BrpPartij(null, partij.getNaam(), BrpMapperUtil.mapBrpPartijCode(partij), partijStapel);
    }

    @Override
    protected BrpPartijInhoud mapInhoud(final PartijHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        // @formatter:off
        return new BrpPartijInhoud(BrpMapperUtil.mapDatum(historie.getDatumIngang()),
            BrpMapperUtil.mapDatum(historie.getDatumEinde()),
            historie.isIndicatieVerstrekkingsbeperkingMogelijk(),
            true);
        // @formatter:on
    }

}

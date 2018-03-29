/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpSoortDienstCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienst;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map dienst van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDienstMapper extends AbstractBrpMapper<DienstHistorie, BrpDienstInhoud> {

    /**
     * Map een database entiteit dienst naar een BRP conversie model object.
     * @param dienst database entiteit
     * @return conversie model object
     */
    public BrpDienst mapDienst(final Dienst dienst) {
        final BrpSoortDienstCode soortDienstCode = new BrpSoortDienstCode((short) dienst.getSoortDienst().getId());
        final BrpEffectAfnemerindicatiesCode effectAfnemerindicatiesCode =
                BrpMapperUtil.mapBrpEffectAfnemersindicatiesCode(dienst.getEffectAfnemerindicaties());
        dienst.getAttenderingscriterium();
        dienst.getEersteSelectieDatum();

        final BrpStapel<BrpDienstInhoud> dienstStapel = map(dienst.getDienstHistorieSet(), null);
        final BrpStapel<BrpDienstAttenderingInhoud> dienstAttenderingStapel =
                new BrpDienstAttenderingMapper().map(dienst.getDienstAttenderingHistorieSet(), null);
        final BrpStapel<BrpDienstSelectieInhoud> dienstSelectieStapel = new BrpDienstSelectieMapper().map(dienst.getDienstSelectieHistorieSet(), null);

        return new BrpDienst(effectAfnemerindicatiesCode, soortDienstCode, dienstStapel, dienstAttenderingStapel, dienstSelectieStapel);
    }

    @Override
    protected BrpDienstInhoud mapInhoud(final DienstHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        return new BrpDienstInhoud(
                BrpMapperUtil.mapDatum(historie.getDatumIngang()),
                BrpMapperUtil.mapDatum(historie.getDatumEinde()),
                historie.getIndicatieGeblokkeerd());
    }
}

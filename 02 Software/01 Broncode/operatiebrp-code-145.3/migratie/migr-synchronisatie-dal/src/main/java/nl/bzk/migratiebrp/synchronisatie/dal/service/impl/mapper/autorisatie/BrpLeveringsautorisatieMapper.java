/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsautorisatieHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map leveringsautorisatie van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpLeveringsautorisatieMapper extends AbstractBrpMapper<LeveringsautorisatieHistorie, BrpLeveringsautorisatieInhoud> {

    /**
     * Map een database entiteit leveringsautorisatie naar een BRP conversie model object.
     * @param leveringsautorisatieHistorieSet database entiteit
     * @return conversie model object
     */
    public BrpStapel<BrpLeveringsautorisatieInhoud> mapLeveringsautorisatie(final Set<LeveringsautorisatieHistorie> leveringsautorisatieHistorieSet) {
        return map(leveringsautorisatieHistorieSet, null);
    }

    @Override
    protected BrpLeveringsautorisatieInhoud mapInhoud(final LeveringsautorisatieHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        return new BrpLeveringsautorisatieInhoud(
                historie.getNaam(),
                BrpMapperUtil.mapBrpProtocolleringsniveauCode(historie.getProtocolleringsniveau()),
                historie.getIndicatieAliasSoortAdministratieveHandelingLeveren(),
                historie.getIndicatieGeblokkeerd(),
                historie.getPopulatiebeperking(),
                BrpMapperUtil.mapDatum(historie.getDatumIngang()),
                BrpMapperUtil.mapDatum(historie.getDatumEinde()),
                historie.getToelichting());
    }
}

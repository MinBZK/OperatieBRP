/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstSelectieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map dienst selectie van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDienstSelectieMapper extends AbstractBrpMapper<DienstSelectieHistorie, BrpDienstSelectieInhoud> {

    /**
     * Map een database entiteit partij naar een BRP conversie model object.
     * @param dienstSelectieHistorieSet database entiteit
     * @return conversie model object
     */
    public BrpStapel<BrpDienstSelectieInhoud> mapDienstAttendering(final Set<DienstSelectieHistorie> dienstSelectieHistorieSet) {
        return map(dienstSelectieHistorieSet, null);
    }

    @Override
    protected BrpDienstSelectieInhoud mapInhoud(final DienstSelectieHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {

        return new BrpDienstSelectieInhoud(
                SoortSelectie.parseId(historie.getSoortSelectie()),
                BrpMapperUtil.mapDatum(historie.getEersteSelectieDatum()),
                bepaalSelectiePeriodeInMaand(historie),
                historie.getIndVerzVolBerBijWijzAfniNaSelectie(),
                LeverwijzeSelectie.parseId(historie.getLeverwijzeSelectie()));
    }

    private Short bepaalSelectiePeriodeInMaand(final DienstSelectieHistorie historie) {
        if (EenheidSelectieInterval.MAAND == EenheidSelectieInterval.parseId(historie.getEenheidSelectieInterval())
                && historie.getSelectieInterval() != null) {
            return historie.getSelectieInterval().shortValue();
        }
        return null;


    }
}

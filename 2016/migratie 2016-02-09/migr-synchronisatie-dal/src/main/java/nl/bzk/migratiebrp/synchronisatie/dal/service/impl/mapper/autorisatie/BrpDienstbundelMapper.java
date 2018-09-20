/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.DienstbundelHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.AbstractBrpMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpMapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;

import org.springframework.stereotype.Component;

/**
 * Map dienstbundel van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDienstbundelMapper extends AbstractBrpMapper<DienstbundelHistorie, BrpDienstbundelInhoud> {

    /**
     * Map een database entiteit dienstbundel naar een BRP conversie model object.
     *
     * @param leveringsautorisatieHistorieSet
     *            database entiteit
     * @return conversie model object
     */
    public BrpStapel<BrpDienstbundelInhoud> mapDienstbundel(final Set<DienstbundelHistorie> leveringsautorisatieHistorieSet) {
        return map(leveringsautorisatieHistorieSet, null);
    }

    @Override
    protected BrpDienstbundelInhoud mapInhoud(final DienstbundelHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        return new BrpDienstbundelInhoud(
            historie.getNaam(),
            BrpMapperUtil.mapDatum(historie.getDatumIngang()),
            BrpMapperUtil.mapDatum(historie.getDatumEinde()),
            historie.getNaderePopulatiebeperking(),
            historie.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(),
            historie.getToelichting(),
            historie.getIndicatieGeblokkeerd());
    }
}

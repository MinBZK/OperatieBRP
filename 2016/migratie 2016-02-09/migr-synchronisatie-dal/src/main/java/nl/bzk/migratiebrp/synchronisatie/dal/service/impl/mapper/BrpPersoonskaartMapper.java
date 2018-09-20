/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonPersoonskaartHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map persoonskaart van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpPersoonskaartMapper extends AbstractBrpMapper<PersoonPersoonskaartHistorie, BrpPersoonskaartInhoud> {

    @Override
    protected BrpPersoonskaartInhoud mapInhoud(final PersoonPersoonskaartHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpBoolean indicatie =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getIndicatiePersoonskaartVolledigGeconverteerd(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD, true));
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(
                    historie.getPartij(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_PERSOONSKAART_PARTIJCODE, true));
        return new BrpPersoonskaartInhoud(partijCode, indicatie);
    }

}

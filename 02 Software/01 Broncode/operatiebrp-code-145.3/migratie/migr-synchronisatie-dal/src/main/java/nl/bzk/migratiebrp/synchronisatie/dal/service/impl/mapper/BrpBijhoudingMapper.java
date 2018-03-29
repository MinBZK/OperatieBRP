/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map bijhouding van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpBijhoudingMapper extends AbstractBrpMapper<PersoonBijhoudingHistorie, BrpBijhoudingInhoud> {

    @Override
    protected BrpBijhoudingInhoud mapInhoud(final PersoonBijhoudingHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpPartijCode bijhoudingPartij;
        bijhoudingPartij =
                BrpMapperUtil.mapBrpPartijCode(
                        historie.getPartij(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_BIJHOUDING_PARTIJCODE, true));
        final BrpBijhoudingsaardCode bijhoudingsaard;
        bijhoudingsaard =
                BrpMapperUtil.mapBrpBijhoudingsaard(
                        historie.getBijhoudingsaard(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, true));
        final BrpNadereBijhoudingsaardCode nadereBijhoudingsaardCode;
        nadereBijhoudingsaardCode =
                BrpMapperUtil.mapBrpNadereBijhoudingsaard(
                        historie.getNadereBijhoudingsaard(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, true));

        return new BrpBijhoudingInhoud(bijhoudingPartij, bijhoudingsaard, nadereBijhoudingsaardCode);
    }
}

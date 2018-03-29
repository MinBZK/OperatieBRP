/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map geslachtsaanduiding van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpGeslachtsaanduidingMapper extends AbstractBrpMapper<PersoonGeslachtsaanduidingHistorie, BrpGeslachtsaanduidingInhoud> {

    @Override
    protected BrpGeslachtsaanduidingInhoud mapInhoud(final PersoonGeslachtsaanduidingHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final Lo3Onderzoek onderzoekGeslachtsaanduiding = brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSAANDUIDING_CODE, true);
        final BrpGeslachtsaanduidingCode geslachtsaanduiding =
                BrpMapperUtil.mapBrpGeslachtsaanduidingCode(historie.getGeslachtsaanduiding(), onderzoekGeslachtsaanduiding);

        return new BrpGeslachtsaanduidingInhoud(geslachtsaanduiding);

    }

}

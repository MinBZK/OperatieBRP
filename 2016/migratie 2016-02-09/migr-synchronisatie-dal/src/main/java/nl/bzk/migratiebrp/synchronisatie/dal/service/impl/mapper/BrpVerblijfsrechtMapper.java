/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map verblijfsrecht van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpVerblijfsrechtMapper extends AbstractBrpMapper<PersoonVerblijfsrechtHistorie, BrpVerblijfsrechtInhoud> {

    @Override
    protected BrpVerblijfsrechtInhoud mapInhoud(final PersoonVerblijfsrechtHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpVerblijfsrechtCode verblijfsrechtCode;
        verblijfsrechtCode =
                BrpMapperUtil.mapBrpVerblijfsrechtCode(
                    historie.getVerblijfsrecht(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE, true));
        final BrpDatum datumMededelingVerblijfsrecht;
        datumMededelingVerblijfsrecht =
                BrpMapperUtil.mapDatum(
                    historie.getDatumMededelingVerblijfsrecht(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING, true));
        final BrpDatum datumVoorzienEindeVerblijfsrecht;
        datumVoorzienEindeVerblijfsrecht =
                BrpMapperUtil.mapDatum(
                        historie.getDatumVoorzienEindeVerblijfsrecht(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE, true));
        final BrpDatum datumAanvangVerblijfsrecht;
        datumAanvangVerblijfsrecht =
                BrpMapperUtil.mapDatum(
                        historie.getDatumAanvangVerblijfsrecht(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERBLIJFSRECHT_DATUMAANVANG, true));

        return new BrpVerblijfsrechtInhoud(verblijfsrechtCode, datumMededelingVerblijfsrecht, datumVoorzienEindeVerblijfsrecht, datumAanvangVerblijfsrecht);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map uitsluiting nederlands kiesrecht van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpUitsluitingKiesrechtMapper extends AbstractBrpMapper<PersoonUitsluitingKiesrechtHistorie, BrpUitsluitingKiesrechtInhoud> {

    @Override
    protected BrpUitsluitingKiesrechtInhoud mapInhoud(final PersoonUitsluitingKiesrechtHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpBoolean indUitsluitingKiesrecht =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getIndicatieUitsluitingKiesrecht(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE, true));
        final BrpDatum datumVoorzienEindeUitsluitingKiesrecht;
        datumVoorzienEindeUitsluitingKiesrecht =
                BrpMapperUtil.mapDatum(
                    historie.getDatumVoorzienEindeUitsluitingKiesrecht(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE, true));

        return new BrpUitsluitingKiesrechtInhoud(indUitsluitingKiesrecht, datumVoorzienEindeUitsluitingKiesrecht);
    }

}

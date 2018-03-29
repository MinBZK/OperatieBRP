/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map europese verkiezing van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpDeelnameEuVerkiezingenMapper extends AbstractBrpMapper<PersoonDeelnameEuVerkiezingenHistorie, BrpDeelnameEuVerkiezingenInhoud> {

    @Override
    protected BrpDeelnameEuVerkiezingenInhoud mapInhoud(final PersoonDeelnameEuVerkiezingenHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpBoolean indDeelnameEuVerkiezingen;
        indDeelnameEuVerkiezingen =
                BrpMapperUtil.mapBrpBoolean(
                        historie.getIndicatieDeelnameEuVerkiezingen(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME, true));
        final BrpDatum datumAanleidingAanpassingDeelnameEuVerkiezingen;
        final Lo3Onderzoek onderzoekDatumAanleidingAanpassing;
        onderzoekDatumAanleidingAanpassing =
                brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING, true);
        datumAanleidingAanpassingDeelnameEuVerkiezingen =
                BrpMapperUtil.mapDatum(historie.getDatumAanleidingAanpassingDeelnameEuVerkiezingen(), onderzoekDatumAanleidingAanpassing);
        final BrpDatum datumVoorzienEindeUitsluitingEuVerkiezingen;
        final Lo3Onderzoek onderzoekDatumVoorzienEindeUitsluiting;
        onderzoekDatumVoorzienEindeUitsluiting =
                brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING, true);
        datumVoorzienEindeUitsluitingEuVerkiezingen =
                BrpMapperUtil.mapDatum(historie.getDatumVoorzienEindeUitsluitingEuVerkiezingen(), onderzoekDatumVoorzienEindeUitsluiting);

        return new BrpDeelnameEuVerkiezingenInhoud(
                indDeelnameEuVerkiezingen,
                datumAanleidingAanpassingDeelnameEuVerkiezingen,
                datumVoorzienEindeUitsluitingEuVerkiezingen);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map overlijden van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpOverlijdenMapper extends AbstractBrpMapper<PersoonOverlijdenHistorie, BrpOverlijdenInhoud> {

    @Override
    protected BrpOverlijdenInhoud mapInhoud(final PersoonOverlijdenHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpDatum datumOverlijden =
                BrpMapperUtil.mapDatum(historie.getDatumOverlijden(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_DATUM, true));
        final BrpGemeenteCode gemeenteOverlijden;
        gemeenteOverlijden =
                BrpMapperUtil.mapBrpGemeenteCode(
                        historie.getGemeente(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_GEMEENTECODE, true));
        final BrpString woonplaatsnaamOverlijden =
                BrpString.wrap(
                        historie.getWoonplaatsnaamOverlijden(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM, true));
        final BrpString buitenlandsePlaatsOverlijden =
                BrpString.wrap(
                        historie.getBuitenlandsePlaatsOverlijden(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS, true));
        final BrpString buitenlandseRegioOverlijden =
                BrpString.wrap(
                        historie.getBuitenlandseRegioOverlijden(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO, true));
        final BrpLandOfGebiedCode landOfGebiedOverlijden;
        landOfGebiedOverlijden =
                BrpMapperUtil.mapBrpLandOfGebiedCode(
                        historie.getLandOfGebied(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE, true));
        final BrpString omschrijvingLocatieOverlijden =
                BrpString.wrap(
                        historie.getOmschrijvingLocatieOverlijden(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE, true));

        return new BrpOverlijdenInhoud(
                datumOverlijden,
                gemeenteOverlijden,
                woonplaatsnaamOverlijden,
                buitenlandsePlaatsOverlijden,
                buitenlandseRegioOverlijden,
                landOfGebiedOverlijden,
                omschrijvingLocatieOverlijden);
    }
}

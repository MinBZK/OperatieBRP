/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeboorteHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map geboorte van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpGeboorteMapper extends AbstractBrpMapper<PersoonGeboorteHistorie, BrpGeboorteInhoud> {

    @Override
    protected BrpGeboorteInhoud mapInhoud(final PersoonGeboorteHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpDatum datumGeboorte =
                BrpMapperUtil.mapDatum(historie.getDatumGeboorte(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_DATUM, true));
        final BrpGemeenteCode gemeenteGeboorte =
                BrpMapperUtil.mapBrpGemeenteCode(
                    historie.getGemeente(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_GEMEENTECODE, true));
        final BrpString woonplaatsnaamGeboorte =
                BrpString.wrap(
                    historie.getWoonplaatsnaamGeboorte(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_WOONPLAATSNAAM, true));
        final BrpString buitenlandsePlaatsGeboorte =
                BrpString.wrap(
                    historie.getBuitenlandsePlaatsGeboorte(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS, true));
        final BrpString buitenlandseRegioGeboorte =
                BrpString.wrap(
                    historie.getBuitenlandseRegioGeboorte(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_BUITENLANDSEREGIO, true));
        final BrpLandOfGebiedCode landOfGebiedGeboorte;
        landOfGebiedGeboorte =
                BrpMapperUtil.mapBrpLandOfGebiedCode(
                    historie.getLandOfGebied(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_LANDGEBIEDCODE, true));
        final BrpString omschrijvingLocatieGeboorte =
                BrpString.wrap(
                    historie.getOmschrijvingGeboortelocatie(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, true));

        return new BrpGeboorteInhoud(
            datumGeboorte,
            gemeenteGeboorte,
            woonplaatsnaamGeboorte,
            buitenlandsePlaatsGeboorte,
            buitenlandseRegioGeboorte,
            landOfGebiedGeboorte,
            omschrijvingLocatieGeboorte);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map nummerverwijsing van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpNummerverwijzingMapper extends AbstractBrpMapper<PersoonNummerverwijzingHistorie, BrpNummerverwijzingInhoud> {

    @Override
    protected BrpNummerverwijzingInhoud mapInhoud(final PersoonNummerverwijzingHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpString vorigAdministratienummer =
                BrpString.wrap(
                        historie.getVorigeAdministratienummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER, true));
        final BrpString volgendAdministratienummer =
                BrpString.wrap(
                        historie.getVolgendeAdministratienummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER, true));
        final BrpString vorigBurgerservicenummer =
                BrpString.wrap(
                        historie.getVorigeBurgerservicenummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER, true));
        final BrpString volgendBurgerservicenummer =
                BrpString.wrap(
                        historie.getVolgendeBurgerservicenummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER, true));
        return new BrpNummerverwijzingInhoud(vorigAdministratienummer, volgendAdministratienummer, vorigBurgerservicenummer, volgendBurgerservicenummer);
    }
}

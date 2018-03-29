/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map identificatienummers van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpIdentificatienummersMapper extends AbstractBrpMapper<PersoonIDHistorie, BrpIdentificatienummersInhoud> {

    @Override
    protected BrpIdentificatienummersInhoud mapInhoud(final PersoonIDHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpString administratienummer =
                BrpString.wrap(
                        historie.getAdministratienummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, true));
        final BrpString burgerservicenummer =
                BrpString.wrap(
                        historie.getBurgerservicenummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, true));
        return new BrpIdentificatienummersInhoud(administratienummer, burgerservicenummer);
    }

}

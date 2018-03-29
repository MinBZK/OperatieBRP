/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * BrpPersoonAfgeleidAdministratief mapper.
 */
@Component
public final class BrpPersoonAfgeleidAdministratiefMapper
        extends AbstractBrpMapper<PersoonAfgeleidAdministratiefHistorie, BrpPersoonAfgeleidAdministratiefInhoud> {

    @Override
    public BrpPersoonAfgeleidAdministratiefInhoud mapInhoud(
            final PersoonAfgeleidAdministratiefHistorie historie,
            final BrpOnderzoekMapper brpOnderzoekMapper) {
        return new BrpPersoonAfgeleidAdministratiefInhoud();
    }
}

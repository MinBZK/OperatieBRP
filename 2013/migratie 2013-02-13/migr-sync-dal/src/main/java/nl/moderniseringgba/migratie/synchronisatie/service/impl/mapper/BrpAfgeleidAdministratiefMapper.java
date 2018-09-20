/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.Collections;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

import org.springframework.stereotype.Component;

/**
 * BrpAfgeleidAdministratief mapper.
 */
@Component
public final class BrpAfgeleidAdministratiefMapper {

    /**
     * Map de gegevens.
     * 
     * @param persoon
     *            persoon
     * @return afgeleid administratief
     */
    public BrpStapel<BrpAfgeleidAdministratiefInhoud> map(final Persoon persoon) {
        // @formatter:off
        final BrpAfgeleidAdministratiefInhoud inhoud = new BrpAfgeleidAdministratiefInhoud(
                BrpMapperUtil.mapBrpDatumTijd(persoon.getTijdstipLaatsteWijziging()),
                null
            );
        // @formatter:on

        final BrpHistorie historie = new BrpHistorie(null, null, BrpDatumTijd.fromDatumTijd(0L), null);

        final BrpGroep<BrpAfgeleidAdministratiefInhoud> groep =
                new BrpGroep<BrpAfgeleidAdministratiefInhoud>(inhoud, historie, null, null, null);

        return new BrpStapel<BrpAfgeleidAdministratiefInhoud>(Collections.singletonList(groep));
    }
}

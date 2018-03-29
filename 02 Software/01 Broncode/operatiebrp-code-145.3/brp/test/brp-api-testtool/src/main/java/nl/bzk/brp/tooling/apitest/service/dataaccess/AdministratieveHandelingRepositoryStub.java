/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.brp.service.dalapi.AdministratieveHandelingRepository;
import nl.bzk.brp.service.dalapi.TeLeverenHandelingDTO;

/**
 * Stub voor AdministratieveHandelingRepository.
 */
final class AdministratieveHandelingRepositoryStub implements AdministratieveHandelingRepository {

    @Inject
    private PersoonDataStubService persoonDataStubService;

    @Override
    public List<BRPActie> haalAdministratieveHandelingOp(final Long administratieveHandelingId) {
        return persoonDataStubService.getActiesVanHandeling(administratieveHandelingId);
    }

    @Override
    public int markeerAdministratieveHandelingAlsVerwerkt(final Long administratieveHandelingId) {
        return 1;
    }

    @Override
    public int markeerAdministratieveHandelingAlsFout(final Long administratieveHandelingId) {
        return 1;
    }

    @Override
    public List<TeLeverenHandelingDTO> geefHandelingenVoorAdmhndPublicatie() {
        return Collections.emptyList();
    }

    @Override
    public int zetHandelingenStatusInLevering(final Set<Long> ids) {
        return ids.size();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.service.dalapi.PartijRepository;
import nl.bzk.brp.tooling.apitest.autorisatie.Partijen;

/**
 * Stub voor PartijRepository.
 */
final class PartijRepositoryStub implements PartijRepository {

    @Override
    public List<Partij> get() {
        return Partijen.getPartijen();
    }
}

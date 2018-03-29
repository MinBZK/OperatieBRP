/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import java.util.Collection;
import nl.bzk.brp.beheer.service.dal.StamdataRepository;
import nl.bzk.brp.beheer.service.stamdata.StatischeStamdataDTO;
import org.springframework.stereotype.Component;

/**
 * Stub voor {@link StamdataRepository}.
 */
@Component
final class StamdataRepositoryStub implements StamdataRepository {

    @Override
    public Collection<StatischeStamdataDTO> getStatischeStamdata(String tabel) {
        return null;
    }
}

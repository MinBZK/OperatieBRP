/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bericht;
import nl.bzk.brp.archivering.service.dal.ArchiefBerichtRepository;

/**
 * Stub implementatie van de {@link ArchiefBerichtRepository} t.b.v. API testing.
 */
public class ArchiveringRepositoryStub implements ArchiefBerichtRepository, ArchiveringStubService {

    private List<Bericht> berichten = Lists.newArrayList();

    @Override
    public void accept(final Bericht bericht) {
        berichten.add(bericht);
    }

    @Override
    public void reset() {
        berichten.clear();
    }

    @Override
    public boolean erIsGearchiveerd() {
        return !berichten.isEmpty();
    }

    public List<Bericht> getBerichten() {
        return berichten;
    }

}

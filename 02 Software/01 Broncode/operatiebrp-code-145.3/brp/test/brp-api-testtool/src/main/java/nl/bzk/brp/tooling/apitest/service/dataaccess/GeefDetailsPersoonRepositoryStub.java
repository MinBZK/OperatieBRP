/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.service.dalapi.GeefDetailsPersoonRepository;

/**
 * Stub voor ZoekPersoonRepository.
 */
final class GeefDetailsPersoonRepositoryStub implements GeefDetailsPersoonRepository {

    @Inject
    private PersoonDataStubService persoonDataStubService;

    @Override
    public List<Long> zoekIdsPersoonMetBsn(final String bsn) {
        final Multimap<String, Long> bsnIdMap = persoonDataStubService.getBsnIdMap();
        if (bsnIdMap == null) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(bsnIdMap.get(bsn));
    }

    @Override
    public List<Long> zoekIdsPersoonMetAnummer(final String anr) {
        final Multimap<String, Long> anrIdMap = persoonDataStubService.getAnrIdMap();
        if (anrIdMap == null) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(anrIdMap.get(anr));
    }
}

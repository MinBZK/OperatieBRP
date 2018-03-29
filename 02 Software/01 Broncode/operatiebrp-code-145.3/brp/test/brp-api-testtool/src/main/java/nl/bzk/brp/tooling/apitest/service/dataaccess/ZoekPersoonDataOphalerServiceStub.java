/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.service.dalapi.AlgemeneQueryFout;
import nl.bzk.brp.service.dalapi.QueryNietUitgevoerdException;
import nl.bzk.brp.service.dalapi.QueryTeDuurException;
import nl.bzk.brp.service.dalapi.ZoekPersoonDataOphalerService;

/**
 * Stub voor ZoekPersoonDataOphalerService.
 */
final class ZoekPersoonDataOphalerServiceStub implements ZoekPersoonDataOphalerService {

    @Inject
    private PersoonDataStubService persoonDataStubService;

    @Override
    public List<Long> zoekPersonenActueel(final Set<ZoekCriterium> zoekCriteria, final int maxResults) throws QueryNietUitgevoerdException {
        return Lists.newArrayList(persoonDataStubService.getIdBsnMapZonderPseudopersonen().keys());
    }

    @Override
    public List<Long> zoekPersonenHistorisch(final Set<ZoekCriterium> zoekCriteria, final Integer materieelPeilmoment, final boolean peilperiode,
        final int maxResults)
        throws AlgemeneQueryFout, QueryTeDuurException
    {
        return Lists.newArrayList(persoonDataStubService.getIdBsnMapZonderPseudopersonen().keys());
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;

/**
 * De stub voor protocollering.
 */
public final class ProtocolleringRepositoryStub implements ProtocolleringRepository, ProtocolleringStubService {

    private List<Leveringsaantekening> opdrachten = Lists.newArrayList();
    private Multimap<Leveringsaantekening, LeveringsaantekeningPersoon> bulkProtocollering = HashMultimap.create();

    @Override
    public void reset() {
        opdrachten.clear();
    }

    @Override
    public boolean erIsGeprotocolleerd() {
        return !opdrachten.isEmpty();
    }

    @Override
    public List<Leveringsaantekening> getLeveringsaantekeningen() {
        return opdrachten;
    }

    @Override
    public void opslaanNieuweLevering(Leveringsaantekening leveringsaantekening) {
        opdrachten.add(leveringsaantekening);
    }

    @Override
    public void opslaanNieuwScopePatroon(ScopePatroon scopePatroon) {

    }

    @Override
    public List<ScopePatroon> getScopePatronen() {
        return Lists.newArrayList();
    }

    @Override
    public void slaBulkPersonenOpBijLeveringsaantekening(final Leveringsaantekening leveringsaantekening,
                                                         final List<LeveringsaantekeningPersoon> persoonList) {
        bulkProtocollering.putAll(leveringsaantekening, persoonList);
    }

    @Override
    public Multimap<Leveringsaantekening, LeveringsaantekeningPersoon> getBulkProtocollering() {
        return bulkProtocollering;
    }
}

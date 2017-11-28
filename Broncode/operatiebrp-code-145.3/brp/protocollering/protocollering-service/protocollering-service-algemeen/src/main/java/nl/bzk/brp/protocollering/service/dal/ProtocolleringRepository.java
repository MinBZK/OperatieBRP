/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.dal;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;

/**
 * ProtocolleringRepository.
 */
public interface ProtocolleringRepository {

    /**
     * @param leveringsaantekening leveringsaantekening
     */
    void opslaanNieuweLevering(Leveringsaantekening leveringsaantekening);

    /**
     * @param scopePatroon scopePatroon
     */
    void opslaanNieuwScopePatroon(ScopePatroon scopePatroon);

    /**
     * @return lijst met scopepatronen
     */
    List<ScopePatroon> getScopePatronen();

    /**
     * Protocolleert (en persisteert) enkel de gegeven {@link LeveringPersoon}en bij de gegeven {@link Leveringsaantekening}.
     *
     * @param leveringsaantekening de {@link Leveringsaantekening}
     * @param persoonList een lijst met protocolleren personen
     */
    void slaBulkPersonenOpBijLeveringsaantekening(final Leveringsaantekening leveringsaantekening,
                                                  List<LeveringsaantekeningPersoon> persoonList);
}

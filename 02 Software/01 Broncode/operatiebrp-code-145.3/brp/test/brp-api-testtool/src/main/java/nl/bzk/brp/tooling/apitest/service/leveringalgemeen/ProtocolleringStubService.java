/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import com.google.common.collect.Multimap;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LeveringsaantekeningPersoon;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Stub voor het afvangen van alle protocolleringopdrachten.
 */
public interface ProtocolleringStubService extends Stateful {

    /**
     * @return indicatie of er geprotocolleerd is.
     */
    boolean erIsGeprotocolleerd();

    /**
     * @return alle leverings aantekeningen.
     */
    List<Leveringsaantekening> getLeveringsaantekeningen();

    /**
     * @return de bulk protocollering tbv selecties
     */
    Multimap<Leveringsaantekening, LeveringsaantekeningPersoon> getBulkProtocollering();
}

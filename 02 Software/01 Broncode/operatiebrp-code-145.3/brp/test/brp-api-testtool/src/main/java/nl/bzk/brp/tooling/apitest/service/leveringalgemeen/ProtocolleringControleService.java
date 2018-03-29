/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import java.util.List;
import nl.bzk.brp.test.common.jbehave.GeprotocolleerdePersoon;
import nl.bzk.brp.test.common.jbehave.ScopeElement;
import nl.bzk.brp.test.common.jbehave.VeldEnWaarde;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Service voor het controleren van de protocollering.
 */
public interface ProtocolleringControleService extends Stateful {

    /**
     * Assert dat het laatste synchrone bericht is geprotocolleerd.
     */
    void assertLaatsteSynchroneBerichtGeprotocolleerd();

    /**
     * Assert dat het laatste synchrone bericht niet geprotocolleerd is.
     */
    void assertLaatsteSynchroneBerichtNietGeprotocolleerd();

    /**
     * Assert dat er geprotocolleerd is met de gegeven waarden.
     *
     * @param gegevens lijst met waarden.
     */
    void assertIsGeprotocolleerdMetDeWaarden(final List<VeldEnWaarde> gegevens);

    /**
     * Assert dat er geprotocolleerd voor personen met de gegeven waarden.
     *
     * @param geprotocolleerdePersonen lijst met geprotocolleerde waarden.
     */
    void assertLaatstGeprotocolleerdePersonenMetWaarden(List<GeprotocolleerdePersoon> geprotocolleerdePersonen);

    /**
     * Assert dat er geprotocolleerd is met de gegeven scope.
     *
     * @param scopeElementen lijst met scope elementen.
     */
    void assertLaatstGeprotocolleerdeScopeElementen(List<ScopeElement> scopeElementen);

    /**
     * Assert dat er geen scopeelementen geprotocolleerd zijn.
     */
    void assertGeenScopeElementenGeprotocolleerd();
}

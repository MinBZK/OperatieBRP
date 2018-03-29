/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import java.util.List;
import nl.bzk.brp.test.common.jbehave.GeprotocolleerdePersoon;
import nl.bzk.brp.test.common.jbehave.ScopeElement;
import nl.bzk.brp.test.common.jbehave.VeldEnWaarde;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 * Steps voor het controleren van protocollering.
 */
public final class ProtocolleringSteps extends Steps {

    /**
     * Assert dat het laatste synchrone bericht is geprotocolleerd.
     */
    @Then("is het laatste bericht geprotocolleerd")
    public void isLaatsteSynchroneBerichtGeprotocolleerd() {
        StoryController.getOmgeving().getProtocolleringControleService().assertLaatsteSynchroneBerichtGeprotocolleerd();
    }

    /**
     * Assert dat het laatste synchrone bericht niet geprotocolleerd is.
     */
    @Then("is het laatste bericht niet geprotocolleerd")
    public void isLaatsteSynchroneBerichtNietGeprotocolleerd() {
        StoryController.getOmgeving().getProtocolleringControleService().assertLaatsteSynchroneBerichtNietGeprotocolleerd();
    }

    /**
     * Assert dat er geprotocolleerd is met de gegeven waarden.
     *
     * @param gegevens lijst met waarden.
     */
    @Then("is het laatste bericht geprotocolleerd met de gegevens: $gegevens")
    public void isLaatsteSynchroneBerichtGeprotocolleerdMetDeGegevens(final List<VeldEnWaarde> gegevens) {
        StoryController.getOmgeving().getProtocolleringControleService().assertIsGeprotocolleerdMetDeWaarden(gegevens);
    }

    /**
     * Assert dat er geprotocolleerd voor personen met de gegeven waarden.
     *
     * @param geprotocolleerdePersonen lijst met geprotocolleerde waarden.
     */
    @Then("zijn de laatst geprotocolleerde personen: $geprotocolleerdePersonen")
    public void zijnDeLaatstGeprotocolleerdePersonenen(final List<GeprotocolleerdePersoon> geprotocolleerdePersonen) {
        StoryController.getOmgeving().getProtocolleringControleService().assertLaatstGeprotocolleerdePersonenMetWaarden(geprotocolleerdePersonen);
    }

    /**
     * Assert dat er geprotocolleerd is met de gegeven scope.
     *
     * @param scopeElementen lijst met scope elementen.
     */
    @Then("zijn de laatst geprotocolleerde scopeelementen: $scopeElementen")
    public void zijnDeLaatstGeprotocolleerdeScopeElementen(final List<ScopeElement> scopeElementen) {
        StoryController.getOmgeving().getProtocolleringControleService().assertLaatstGeprotocolleerdeScopeElementen(scopeElementen);
    }

    /**
     * Assert dat er er geen scoping geprotocolleerd is.
     */
    @Then("zijn er geen scopeelementen geprotocoleerd")
    public void zijnErGeenScopeElementenGeprotocolleerd() {
        StoryController.getOmgeving().getProtocolleringControleService().assertGeenScopeElementenGeprotocolleerd();
    }
}

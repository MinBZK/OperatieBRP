/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DockerNaam;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 * JBehave steps tbv controle protocollering.
 */
public class ProtocolleringControleSteps extends Steps {


    /**
     * Leegt de ber.ber tabellen.
     */
    @BeforeScenario
    public void leegBerichtenTabel() {
        final BrpOmgeving brpOmgeving = JBehaveState.get();
        if (brpOmgeving != null && brpOmgeving.bevat(DockerNaam.PROTOCOLLERINGDB)) {
            brpOmgeving.protocollering().reset();
        }
    }

    /**
     * Test dat er niet geprotocolleerd is voor persoon met bsn
     * @param bsn persoon bsn
     */
    @Then("is er niet geprotocolleerd voor persoon $bsn")
    public void isErNietGeprotocolleerdVoorPersoon(final String bsn) throws InterruptedException {
        JBehaveState.get().protocollering().assertNietGeprotocolleerdVoorPersoon(bsn);
    }

    /**
     * Test dat er niet geprotocolleerd
     */
    @Then("is er niet geprotocolleerd")
    public void isErNietGeprotocolleerd() throws InterruptedException {
        JBehaveState.get().protocollering().assertNietGeprotocolleerd();
    }

    /**
     * Controleert of de administratieve handeling correct geprotocolleerd is Controleren of de administratieve handeling voor een bepaalde BSN correct
     * geprotocolleerd is.
     * @param bsn het burgerservicenummer waarvoor de protocollering gecontroleerd moet worden
     */
    @Then("is de administratieve handeling voor bsn $bsn correct geprotocolleerd")
    public void deAdministratieveHandelingIsCorrectGeprotocolleerd(final String bsn) throws Exception {
        JBehaveState.get().protocollering().assertAdministratievehandelingCorrectGeprotocolleerd(bsn);
    }


    @Then("is er voor leveringsautorisatie $leveringsautorisatie en partij $partij geprotocolleerd met de volgende gegevens: $gegevensRegels")
    public void thenIsErGeprotocolleerdMetDeVolgendeGegevens(final String leveringsautorisatie, final String partij,
                                                             final List<GegevensRegels> gegevensRegels) {
        final Map<String, String> map = GegevensRegels.map(gegevensRegels);
        JBehaveState.get().protocollering().assertGeprotocolleerdMetDeVolgendeGegevens(leveringsautorisatie, partij, map);
    }
}


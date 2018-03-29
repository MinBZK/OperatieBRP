/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 */
public class VrijBerichtSteps extends Steps {

    @Then("is een vrij bericht voor partij $partij verstuurd naar afleverpunt $afleverpunt")
    public void assertIsErEenVrijBerichtVoorPartijVerstuurdNaarAfleverpunt(final String partij, final String afleverpunt) {
        StoryController.getOmgeving().getVrijBerichtControleService()
                .assertErIsEenVrijBerichtVoorPartijVerstuurdNaarAfleverpunt(partij, afleverpunt);
    }

    @Then("is er geen vrij bericht verzonden")
    public void assertGeenVrijBerichtVerzonden() {
        StoryController.getOmgeving().getVrijBerichtControleService().assertErIsGeenVrijBerichtVerzonden();
    }

    @Then("is het verstuurde vrij bericht gelijk aan $bestand")
    public void assertIsVerstuurdeVrijBerichtGelijkAan(final String bestand) throws Exception {
        StoryController.getOmgeving().getVrijBerichtControleService().assertIsVerstuurdVrijBerichtGelijkAan(bestand);
    }

    @Then("is er een vrij bericht opgeslagen met zendende partij $zendendepartij en soortnaam gelijk aan $soortnaam en inhoud gelijk aan $inhoud")
    public void assertIsVrijBerichtCorrectOpgeslagen(final String zendendePartij, final String soortnaam, final String inhoud) throws Exception {
        StoryController.getOmgeving().getVrijBerichtControleService().assertVrijBerichtCorrectOpgeslagen(zendendePartij, soortnaam, inhoud);
    }
}

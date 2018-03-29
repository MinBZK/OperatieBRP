/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import java.util.List;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.tooling.apitest.service.beheer.BeheerSelectieAPIService;
import nl.bzk.brp.tooling.apitest.service.beheer.ExamplesTable2DslSectie;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;

/**
 * JBehave steps specifiek voor Beheer Selectie.
 */
public class BeheerSelectieSteps extends Steps {

    /**
     * Step voor het laden van selectietaken binnen beheer.
     * @param takenTabel de taken tabel
     */
    @Given("de volgende selectie taken: $taken")
    public void laadSelectieTaken(ExamplesTable takenTabel) {
        List<DslSectie> dslSecties = ExamplesTable2DslSectie.convert(takenTabel);
        getSelectieApiService().maakSelectietaken(dslSecties);

    }

    private BeheerSelectieAPIService getSelectieApiService() {
        return StoryController.getOmgeving().getApiService().getBeheerSelectieApiService();
    }

    /**
     * Step om de berekende selectietaken op te halen binnen die vallen binnen een bepaalde periode.
     * @param beginDatum de begindatum van de periode
     * @param eindDatum de einddatum van de periode
     */
    @When("de berekende taken opgevraagd worden voor de begindatum $beginDatum en einddatum $eindDatum")
    public void haalBerekendeTakenOp(String beginDatum, String eindDatum) {
        getSelectieApiService().geefBerekendeTaken(beginDatum, eindDatum);
    }

    /**
     * Step om de berekende selectietaken te controleren.
     * @param aantalTaken het verwachte aantal taken
     * @param berekendeTaken de verwachte berekende taken
     */
    @Then("zijn er $aantalTaken selectietaken berekend met de volgende inhoud: $berekendeTaken")
    public void assertBerekendeTaken(Integer aantalTaken, ExamplesTable berekendeTaken) {
        getSelectieApiService().assertBerekendeTaken(aantalTaken, berekendeTaken.getRows());
    }
}

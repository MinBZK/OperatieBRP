/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.io.IOException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.service.SelectieService;
import nl.bzk.brp.dockertest.util.ResourceUtils;
import nl.bzk.brp.test.common.jbehave.ExamplesTable2DslSectie;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;

/**
 * SelectieSteps.
 */
public class SelectieSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Delete de inhoud van target/selectie directory.
     */
    @BeforeScenario
    public void deleteTargetSelectieDirectory() throws IOException {
        if (JBehaveState.get().bevat(DockerNaam.SELECTIE)) {
            getSelectieService().deleteTargetSelectieDirectory();
        }
        getSelectieService().reset();
    }


    /**
     * Delete de inhoud van target/selectie/selectiebestand directory.
     */
    @AfterScenario
    public void deleteTargetSelectiebestandDirectory() throws IOException {
        if (JBehaveState.get().bevat(DockerNaam.SELECTIE)) {
            getSelectieService().deleteTargetSelectiebestandDirectory();
        }
        getSelectieService().reset();
    }

    /**
     * Verwijdert alle selectietaken uit de database.
     */
    @Given("alle selectietaken zijn verwijderd")
    public void verwijderAlleSelectietaken() {
        JBehaveState.get().testdata().verwijderAlleSelectietaken();
    }

    /**
     *
     */
    @When("start selectie run")
    public void startSelectieRun() {
        getSelectieService().startSelectie();
    }

    /**
     * @throws InterruptedException
     */
    @When("wacht tot selectie run gestart")
    public void isSelectieGestart() throws InterruptedException {
        getSelectieService().wachtTotSelectieGestart();
    }

    /**
     * @param expectationTable expectationTable
     */
    @Then("heeft de actuele selectietaak rij de volgende waarden: $expectationTable")
    public void waardeControleSelectietaak(final ExamplesTable expectationTable) {
        getSelectieService().waardeControleSelectietaak(expectationTable.getRows());
    }

    /**
     * @param dienstsleutel
     * @param datumuitvoer
     * @param expectationTable
     * @throws Exception
     */
    @Then("zijn de volgende resultaat files aanwezig voor selectietaak met dienstsleutel '$dienstsleutel' en datumuitvoer '$datumuitvoer': $expectationTable")
    public void resultaatFilesAanwezigVoorTaakEnDatumUitvoer(final String dienstsleutel, final String datumuitvoer, final ExamplesTable expectationTable)
            throws Exception {
        getSelectieService().assertFilesAanwezigMetExpectations(dienstsleutel, datumuitvoer, expectationTable.getRows());
    }

    /**
     *
     * @param expectationTable
     */
    @Then("zijn de volgende selectie taken correct geprotocolleerd: $expectationTable")
    public void selectietakenCorrectGeprotocolleerd(final ExamplesTable expectationTable) {
        getSelectieService().assertSelectietakenCorrectGeprotocolleerd(ExamplesTable2DslSectie.convert(expectationTable));
    }

    /**
     *
     * @param expectationTable
     */
    @Then("heeft de actuele status rij de volgende waarden: $expectationTable")
    public void takenEindStatusControle(final ExamplesTable expectationTable) {
        getSelectieService().assertSelectietakenStatus(expectationTable.getRows());
    }

    /**
     *
     */
    @When("stop selectie run")
    public void stopSelectieRun() {
        getSelectieService().stopSelectie();
    }

    /**
     *
     * @param minuten
     * @throws InterruptedException
     */
    @When("wacht maximaal $aantalMinuten minuten tot selectie run klaar")
    public void wachtTotKlaar(final Integer minuten) throws InterruptedException {
        getSelectieService().wachtTotSelectieKlaar(minuten);
    }

    /**
     */
    @When("is selectie run gestopt")
    public void isSelectieGestopt() {
        getSelectieService().assertSelectieRunGestopt();
    }

    /**
     */
    @Given("kopieer perscache naar selectie database")
    public void kopieerPerscacheNaarSelectieDatabase() {
        getSelectieService().kopieerPerscacheNaarSelectieDatabase();
    }

    /**
     *
     * @param bestand
     */
    @Given("selectietaken uit $bestand")
    public void givenSelectakenUitBestand(String bestand) {
        getSelectieService().maakSelectietaken(ResourceUtils.resolveResource(bestand));
    }

    /**
     *
     * @param examplesTable
     */
    @Given("een selectierun met de volgende selectie taken: $taken")
    public void givenEenSelectierunMetDeVolgendeSelectieTaken(ExamplesTable examplesTable) {
        getSelectieService().maakSelectietaken(ExamplesTable2DslSectie.convert(examplesTable));
    }

    /**
     * Start protocolleren voor selecties.
     */
    @Given("protocolleren voor selecties wordt gestart")
    public void givenProtocollerenVoorSelectiesWordtGestart() throws IOException {
        getSelectieService().startProtocollerenVoorSelecties();
    }

    /**
     * Wacht tot protcolleren voor selectie klaar.
     */
    @Given("wacht maximaal $aantalminuten minuten tot protocolleren voor selecties klaar")
    public void givenProtocollerenVoorSelectiesKlaar(Integer minuten) throws InterruptedException {
        getSelectieService().wachtTotProtocollerenKlaar(minuten);
    }

    /**
     * Assert dat geprotocolleerd is met de volgende gegevens.
     * @param aantalLeveringsaantekening aantal leveringsaantekeningen
     * @param aantalPersonen aantal personen
     */
    @Then("is de selectie geprotocolleerd met $aantalLeveringsaantekening leveringsaantekeningen en $aantalLeveringsaantekeningPersoon personen")
    public void assertSelectieGeprotocolleerd(int aantalLeveringsaantekening, int aantalPersonen) {
        getSelectieService().assertGeprotocolleerdVoorSelectie(aantalLeveringsaantekening, aantalPersonen);
    }

    /**
     * Zet de status van de selectietaken op {@link SelectietaakStatus#TE_PROTOCOLLEREN}
     */
    @Given("de beheerder aangeeft dat de volgende selectietaken geprotocolleerd dienen te worden: $taken")
    public void givenDeSelectietaakKrijgtStatusTeProtocolleren(ExamplesTable examplesTable) {
        getSelectieService().setStatusOpTeProtocolleren(ExamplesTable2DslSectie.convert(examplesTable));
    }

    /**
     * Creert 1 of meer selectielijsten o.b.v. waarden tabel.
     * Inhoud van selectielijsten wordt gelogd, er komen geen blijvende lijsten in de target directory.
     */
    @Given("selectielijsten per dienst: $lijsten")
    public void givenSelectielijstPerDienst(ExamplesTable examplesTable) {
        getSelectieService().maakSelectielijsten(ExamplesTable2DslSectie.convert(examplesTable));
    }

    private SelectieService getSelectieService() {
        return JBehaveState.get().selectieService();
    }


}

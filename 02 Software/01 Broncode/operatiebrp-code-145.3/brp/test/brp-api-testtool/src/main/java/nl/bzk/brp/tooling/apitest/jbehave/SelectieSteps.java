/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import java.io.IOException;
import nl.bzk.brp.test.common.jbehave.ConversieUtil;
import nl.bzk.brp.test.common.jbehave.ExamplesTable2DslSectie;
import nl.bzk.brp.tooling.apitest.service.selectie.SelectieAPIService;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

/**
 * Steps tbv van selecties,
 */
public class SelectieSteps {

    /**
     * Stap voor het starten van de selectie.
     */
    @When("de selectie wordt gestart")
    public void givenDeSelectieWordtGestart() throws IOException, InterruptedException {
        getSelectieApiService().startRun();
    }

    /**
     * Stap voor het starten van de selectie.
     */
    @When("de selectie wordt gestart in single-threaded mode")
    public void givenDeSelectieWordtGestartSingleThreaded() throws IOException, InterruptedException {
        getSelectieApiService().startRunSingleThreaded();
    }

    /**
     * Stap voor wachten tot selectie run klaar.
     * @param aantalSeconden aantalSeconden
     * @throws InterruptedException fout tijdens wachten
     */
    @When("wacht $aantalSeconden seconden tot selectie run klaar")
    public void wachtAantalSecondenTotSelectieRunKlaar(final String aantalSeconden) throws InterruptedException {
        getSelectieApiService().wachtAantalSecondenTotSelectieRunKlaar(Integer.parseInt(aantalSeconden), false);
    }

    /**
     * Stap voor wachten tot selectie run klaar met fouten.
     * @param aantalSeconden aantalSeconden
     * @throws InterruptedException fout tijdens wachten
     */
    @When("wacht $aantalSeconden seconden tot selectie run klaar met fouten")
    public void wachtAantalSecondenTotSelectieRunKlaarMetFouten(final String aantalSeconden) throws InterruptedException {
        getSelectieApiService().wachtAantalSecondenTotSelectieRunKlaar(Integer.parseInt(aantalSeconden), true);
    }

    /**
     * Stap voor controle resultaat files.
     */
    @Then("resultaat files aanwezig voor selectietaak '$id' en datumplanning '$datumplanning'")
    public void resultaatFilesAanwezig(final Integer id, final String datumPlanning) throws IOException {
        getSelectieApiService().assertFilesAanwezig(id, datumPlanning);
    }

    /**
     * Stap voor controle resultaat files.
     */
    @Then("geen resultaat files aanwezig voor selectietaak '$id' en datumplanning '$datumplanning'")
    public void geenResultaatFilesAanwezig(final Integer id, final String datumplanning) throws IOException {
        getSelectieApiService().assertGeenFilesAanwezig(id, datumplanning);
    }

    /**
     * Stap voor controle resultaat files.
     */
    @Then("resultaat files aanwezig voor selectietaak '$id' en datumplanning '$datumplanning' met '$aantal' personen")
    public void resultaatFilesAanwezigMetAantalPersonen(final Integer id, final String datumPlanning, final Integer aantalPersonen)
            throws IOException {
        getSelectieApiService().assertFilesAanwezigMetAantalPersonen(id, datumPlanning, aantalPersonen);
    }

    /**
     * Stap voor controle resultaat files.
     */
    @Then("resultaat files aanwezig voor selectietaak '$id' en datumplanning '$datumplanning' met '$aantal' personen in '$aantalFiles' resultaatbestanden")
    public void resultaatFilesAanwezigMetAantalPersonenInAantalFiles(final Integer id, final String datumPlanning, final Integer aantalPersonen,
                                                                     final Integer aantalFiles)
            throws IOException {
        getSelectieApiService().assertFilesAanwezigMetAantalPersonenEnAantalFiles(id, datumPlanning, aantalPersonen, aantalFiles);
    }

    @Then("zijn de volgende resultaat files aanwezig voor selectietaak '$id' en datumplanning '$datumplanning': $expectationTable")
    public void resultaatFilesAanwezigVoorTaakEnDatumUitvoer(final Integer id, final String datumPlanning, final ExamplesTable expectationTable)
            throws IOException {
        getSelectieApiService().assertFilesAanwezigMetExpectations(id, datumPlanning, expectationTable.getRows());
    }

    /**
     * Stap voor controle resultaat files.
     */
    @Then("is het totalenbestand voor selectietaak '$id' en datumplanning '$datumPlanning' gelijk aan '$expectedPath'")
    public void totalenbestandAanwezigVoorSelectietaakEnAantalPersonen(final Integer id, final String datumPlanning, final String expectedPath)
            throws IOException {
        getSelectieApiService().assertTotalenbestandVoorTaakGelijkAan(id, datumPlanning, expectedPath);
    }

    @Then("is protocollering bestand aanwezig '$waar' voor selectietaak '$id' en datumplanning '$datumPlanning'")
    public void protocolleringBestandAanwezig(final Boolean aanwezig, final Integer id, final String datumPlanning) {
        getSelectieApiService().assertProtocolleringBestand(id, datumPlanning, aanwezig);
    }

    @Given("een corrupt protocollering bestand voor selectietaak '$id' en datumplanning '$datumPlanning'")
    public void protocolleringBestandIsInvalid(final Integer id, final String datumPlanning) {
        getSelectieApiService().maakProtocolleringsBestandInvalid(id, datumPlanning);
    }


    /**
     * Start protocollering voor selecties.
     */
    @When("protocollering voor selecties wordt gestart")
    public void whenProtocolleringVoorSelectiesWordtGestart() {
        getSelectieApiService().startProtocollering();
    }

    /**
     * Assert dat geprotocolleerd is met de volgende gegevens.
     * @param aantalLeveringsaantekening aantal leveringsaantekeningen
     * @param aantalPersonen aantal personen
     */
    @Then("is de selectie geprotocolleerd met $aantalLeveringsaantekening leveringsaantekeningen en $aantalLeveringsaantekeningPersoon personen")
    public void assertSelectieGeprotocolleerd(int aantalLeveringsaantekening, int aantalPersonen) {
        getSelectieApiService().assertGeprotocolleerdVoorSelectie(aantalLeveringsaantekening, aantalPersonen);
    }

    /**
     * Past de status aan van de gegeven selectietaak.
     * @param selectietaakId id van de selectietaak
     */
    @Given("de beheerder aangeeft dat selectietaak $taakId geprotocolleerd dient te worden")
    public void givenDeSelectietaakKrijgtStatusTeProtocolleren(int selectietaakId) {
        getSelectieApiService().setStatusOpTeProtocolleren(selectietaakId);
    }

    @Given("het protocolleringsbestand voor selectietaak $taakId en datum planning  '$datumPlanning' is verwijderd")
    public void givenhetProtocolleringsbetandIsVerwijderd(int selectietaakld, String datumPlanning) {
        getSelectieApiService().verwijderProtocolleringbestand(selectietaakld, datumPlanning);
    }

    @Given("selectietaken uit $bestand")
    public void givenSelectakenUitBestand(String bestand) {
        getSelectieApiService().maakSelectietaken(StoryController.get().getStoryOmgeving().getStoryService().resolvePath(bestand));
    }

    @Given("een selectierun met de volgende selectie taken: $taken")
    public void givenEenSelectierunMetDeVolgendeSelectieTaken(ExamplesTable examplesTable) {
        getSelectieApiService().
                maakSelectietaken(ExamplesTable2DslSectie.convert(examplesTable));
    }

    @Then("hebben de volgende taken een selectie run id: $taken")
    public void thenHebbenDeVolgendeSelectietakenEenSelectieId(ExamplesTable examplesTable) {
        getSelectieApiService().
                assertTakenHebbenEenSelectierunId(ExamplesTable2DslSectie.convert(examplesTable));
    }


    @Given("selectielijsten per dienst: $lijsten")
    public void givenSelectielijstPerDienst(ExamplesTable examplesTable) {
        getSelectieApiService().
                maakSelectielijsten(ExamplesTable2DslSectie.convert(examplesTable));
    }

    /**
     * Controleert xpath Controleert of gegeven xpath evalueert naar een node uit een van de selectie resultaten.
     * @param xpathExpressie een xPath expressie
     */
    @Then("is voor selectietaak '$id' en datumplanning '$datumplanning' voor xpath '$xpathExpressie' een node aanwezig in de selectieresultaat persoonbestanden")
    public void selectieResultatenHebbenNodeMetXPath(final Integer id, final String datumUitvoer, final String xpathExpressie) throws Exception {
        getSelectieApiService().assertNodeBestaatInPersoonBestanden(id, datumUitvoer, xpathExpressie);
    }

    /**
     * Step om de aanwezigheid van controlebestanden te controleren.
     */
    @Then("zijn er de volgende controlebestanden: $table")
    public void thenIsErGeenControlebestandAanwezig(ExamplesTable table) throws IOException {
        getSelectieApiService().assertControlebestand(ConversieUtil.alsListMap(table));
    }

    @Then("is status transitie: $table")
    public void thenIsStatusTransitie(ExamplesTable table) throws IOException {
        getSelectieApiService().assertStatusTransitie(ConversieUtil.alsListMap(table));
    }

    @Then("zijn peilmomenten op selectietaak: $table")
    public void thenZijnPeilmomentenOpSelectietaak(ExamplesTable table) throws IOException {
        getSelectieApiService().assertPeilmomentenSelectietaak(ConversieUtil.alsListMap(table));
    }


    @Given("bulk mode actief met $aantal personen")
    public void givenBulkModusActief(int aantalPersonen) {
        getSelectieApiService().activeerBulkModus(aantalPersonen);
    }

    private SelectieAPIService getSelectieApiService() {
        return StoryController.getOmgeving().getApiService().getSelectieApiService();
    }

}

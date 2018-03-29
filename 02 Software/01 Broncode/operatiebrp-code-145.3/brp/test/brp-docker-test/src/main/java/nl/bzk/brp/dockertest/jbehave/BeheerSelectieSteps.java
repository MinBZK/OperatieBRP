/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;


/**
 * JBehave steps voor beheer selectie.
 */
public class BeheerSelectieSteps extends Steps {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Voor scenario.
     */
    @BeforeScenario
    public void setupWebDriver() {
        // Njente op het moment he
    }

    /**
     * Na scenario.
     */
    @AfterScenario
    public void quitWebDriver() {
        JBehaveState.get().beheerSelectieService().quitWebDriver();
    }

    /**
     * Step om verbinding te maken met de website.
     */
    @Given("er is een verbinding met de applicatie")
    public void gegevenErIsEenVerbindingMetDeApplicatie(@Named("driverSoort") String driverSoort, @Named("binaryPath") String binaryPath,
                                                        @Named("closeDriver") String closeDriver) {
        JBehaveState.get().beheerSelectieService().setupWebDriver(driverSoort, binaryPath, Boolean.parseBoolean(closeDriver));
    }

    /**
     * Step om op de begindatum aan te passen met meegegeven string in formaat 01-12-2018
     */
    @When("begindatum is aangepast naar $beginDatum")
    public void beginDatumIsAangepastNaar(final String beginDatum) {
        JBehaveState.get().beheerSelectieService().beginDatumIsAangepastNaar(beginDatum);
    }

    /**
     * Step om op de einddatum aan te passen met meegegeven string in formaat 01-12-2018
     */
    @When("einddatum is aangepast naar $eindDatum")
    public void eindDatumIsAangepastNaar(final String eindDatum) {
        JBehaveState.get().beheerSelectieService().eindDatumIsAangepastNaar(eindDatum);
    }

    /**
     * Step om op de zoeken knop mee te klikken.
     */
    @When("er op zoeken wordt geklikt")
    public void alsErOpZoekenWordtGeklikt() {
        JBehaveState.get().beheerSelectieService().klikOpZoeken();
    }

    /**
     * Step om op de zoeken knop mee te klikken.
     */
    @When("de volgende checkboxen worden aangevinkt: $checkboxTable")
    public void alsDeVolgendeCheckboxenWordenAangevinkt(ExamplesTable checkboxTable) {
        JBehaveState.get().beheerSelectieService().vinkCheckboxenAan(checkboxTable.getRows());
    }

    /**
     * Step om filterwaarde op te geven voor het selectie taken overzicht
     */
    @When("voor kolom $kolom de filterwaarde $filterwaarde wordt opgegeven")
    public void geefFilterwaardeVoorKolomOp(String kolom, String filterwaarde){
        JBehaveState.get().beheerSelectieService().geefFilterWaardeOp(kolom, filterwaarde);
    }

    /**
     * @param aantalTaken aantal taken die aanwezig dienen te zijn
     */
    @Then("{zijn|is} er $aantalTaken {selectietaak|selectietaken} aanwezig")
    public void danZijnErEenAantalSelectieTakenAanwezig(String aantalTaken) {
        JBehaveState.get().beheerSelectieService().geselecteerdTotaalgetText(aantalTaken);
    }

    /**
     * Open de eerste rij voor details van de taak
     */
    @When("de details van de eerste taak worden bekeken")
    public void openEersteSelectieTaak() {
        JBehaveState.get().beheerSelectieService().openEersteSelectieTaak();
    }

    /**
     * Check datumPlanning
     */
    @Then("is de datumplanning $datumPlanning")
    public void controleDatumPlanning(String datumPlanning) {
        JBehaveState.get().beheerSelectieService().controleDatumPlanning(datumPlanning);
    }
    /**
     * Controleer of de zoek button disabled is
     */
    @Then("is de zoek button niet klikbaar")
    public void isDeZoekButtonNietKlikbaar(){
        JBehaveState.get().beheerSelectieService().assertZoekenDisabled();
    }

    /**
     * Check op de standaard zoekperiode
     * Begindatum vandaag minus 1 maand
     * Einddatum vandaag plus 3 maanden
     */
    @Then("wordt de standaard zoekperiode correct getoond")
    public void controleStandaardZoekPeriode() {
        JBehaveState.get().beheerSelectieService().checkStandaardZoekPeriode();
    }

    /**
     *
     * @param meldingstekst
     * controleer of de opgegeven meldingstekst aanwezig is op het scherm
     */
    @Then("is de melding met tekst '$meldingstekst' aanwezig")
    public void isMeldingsTekstAanwezig(String meldingstekst){
        JBehaveState.get().beheerSelectieService().assertMeldingdsTekstAanwezig(meldingstekst);
    }
    @Then("wacht tot meldingstekst '$meldingstekst' aanwezig is")
    public void wachtTotmeldingsTekstAanwezigIs(String meldingstekst){
        JBehaveState.get().beheerSelectieService().wachtOpMelding(meldingstekst);
    }

    @Then("is kolom $kolom aanwezig in het taken overzicht")
    public void isKolomAanwezigInOverzicht(String kolom){
        JBehaveState.get().beheerSelectieService().isKolomAanwezigInTaakOverzicht(kolom);
    }

    @Then ("worden in het detailoverzicht de selectietaakgegevens getoond")
    public void klikTaakGegevens(){
        JBehaveState.get().beheerSelectieService().toonTaakDetails();
    }

    @Then ("worden in het detailoverzicht de dienstgegevens getoond")
    public void klikDienstGegevens(){
        JBehaveState.get().beheerSelectieService().toonDienstDetails();
    }

    @Then ("worden in het detailoverzicht de toeganggegevens getoond")
    public void klikToegangGegevens(){
        JBehaveState.get().beheerSelectieService().toonToegangDetails();
    }
    @Then ("worden de volgende taakgegevens getoond: $taakGegevens")
    public void getoondeTaakGegevens(ExamplesTable taakGegevens){
        JBehaveState.get().beheerSelectieService().controleerDetailGegevens(taakGegevens.getRows());

    }


}

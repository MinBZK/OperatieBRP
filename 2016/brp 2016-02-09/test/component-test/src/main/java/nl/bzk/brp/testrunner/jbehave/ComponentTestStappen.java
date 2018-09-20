/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.jbehave;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import javax.jms.JMSException;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.component.BrpOmgeving;
import nl.bzk.brp.testrunner.component.OmgevingBouwer;
import nl.bzk.brp.testrunner.component.util.Afnemerbericht;
import nl.bzk.brp.testrunner.component.util.MigratieModelXmlOutput;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpVergelijker;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.springframework.core.io.ClassPathResource;

/**
 */
public final class ComponentTestStappen extends Steps {


    private static final Logger LOGGER           = LoggerFactory.getLogger();
    private static final String EXTENSIE_XML     = ".xml";
    private static final String MUTATIEBERICHT   = "mutatiebericht";
    private static final String VOLLEDIGBERICHT  = "volledigbericht";
    private static final String FOLDER_OUTPUT    = "output";
    private static final String FOLDER_EXPECTEDS = "expecteds";

    /**
     * Given stap voor het synchroniseren van een persoon.
     */
    @Given("synchroniseer persoon")
    public void synchroniseerPersoon() {
        getBrpOmgeving().synchronisatie().synchroniseerPersoon();
    }

    /**
     * Given stap voor levering autorisaties.
     *
     * @param bestanden de bestanden met daarin de levering autorisaties
     */
    @Given("leveringautorisatie uit %bestanden")
    public void givenLeveringautorisaties(final List<String> bestanden) {
        final BrpOmgeving omgeving = getBrpOmgeving();
        omgeving.leveringautorisaties().uitBestand(bestanden.toArray(new String[bestanden.size()]));
    }

    /**
     * Given stap voor het maken van een BRP persoon op basis van een DSL beschrijving in de story.
     *
     * @param dslString de persoon omschrijving in DSL.
     */
    @Given("de persoon beschrijvingen: %dslString")
    public void givenPersoonDSLString(final String dslString) {
        final BrpOmgeving omgeving = geefStoryController().getBrpOmgeving();
        omgeving.persoonDsl().uitString(dslString);
    }


    /**
     * Given stap voor het maken van een BRP persoon op basis van een DSL-bestand met daarin de beschrijving van een persoon.
     *
     * @param dslBestand bestandsnaam van het DSL-bestand
     */
    @Given("het persoon beschrijving bestand: %dsl")
    public void givenPersoonDSLBestand(final String dslBestand) {
        final BrpOmgeving omgeving = geefStoryController().getBrpOmgeving();
        omgeving.persoonDsl().uitBestand(dslBestand);
    }


    /**
     * Given stap voor het verwerken van een LO3 persoonlijst als synchronisatie naar BRP.
     *
     * @param file het bestand waar de LO3 persoonslijst in staat
     * @throws IOException                     als het bestand niet ingelezen kan worden
     * @throws ExcelAdapterException           als het bestand geen Excel-sheet is of als er een fout ontstaat bij het lezen van de Excel-sheet
     * @throws Lo3SyntaxException              Als de persoonlijst een syntax fout bevat
     * @throws OngeldigePersoonslijstException Als de persoonlijst niet voldoet aan de precondities
     */
    @Given("een sync uit bestand %file")
    public void givenEenSyncUitBestand(final String file) throws ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException, IOException {
        getBrpOmgeving().gba().plMetSync(new ClassPathResource(file.trim()));
    }

    /**
     * Given stap voor het verwijderen van alle personen.
     */
    @Given("alle personen zijn verwijderd")
    public void givenAllePersonenZijnVerwijderd() {
        getBrpOmgeving().database().verwijderAllePersonen();
    }

    /**
     * When stap waarbij de laatste handeling voor de gegeven persoon wordt geleverd.
     *
     * @param bsn BSN voor de persoon waar de laatste handeling voor geleverd moet worden
     */
    @When("voor persoon %bsn wordt de laatste handeling geleverd")
    public void persoonLeverLaatsteHandeling(final int bsn) {
        getBrpOmgeving().handeling().leverLaatsteHandeling(bsn);
    }

    private BrpOmgeving getBrpOmgeving() {
        return StoryController.get().getBrpOmgeving();
    }

    /**
     * When stap waarbij een specifieke handeling wordt geleverd.
     *
     * @param bsn   BSN voor de persoon waar de handeling voor geleverd wordt
     * @param index de index van de handeling
     */
    @When("voor persoon %bsn wordt de %index na laatste handeling geleverd")
    public void leverHandeling(final String bsn, final String index) {
        getBrpOmgeving().handeling().leverHandeling(Integer.parseInt(bsn), Integer.parseInt(index + 1));
    }

    /**
     * When stap waarbij een mutatiebericht voor een specifieke autorisatie is ontvangen en bekeken.
     *
     * @param leveringautorisatieNaam naam van de leverings autorisatie
     * @throws JMSException als er fout optreedt bij het versturen van berichten
     */
    @When("het mutatiebericht voor leveringsautorisatie %leveringautorisatieNaam is ontvangen en wordt bekeken")
    public void whenMutatieberichtIsOntvangenEnWordtBekeken(final String leveringautorisatieNaam) throws JMSException {
        geefBerichten(MUTATIEBERICHT, leveringautorisatieNaam);
    }

    /**
     * When stap waarbij wordt gekeken naar het mutatiebericht voor de specifieke autorsatie.
     *
     * @param leveringautorisatieNaam naam van de leverings autorisatie
     * @throws JMSException als er fout optreedt bij het versturen van berichten
     */
    @When("mutatiebericht voor leveringsautorisatie %leveringautorisatieNaam wordt bekeken")
    public void whenMutatieberichtWordtBekeken(final String leveringautorisatieNaam) throws JMSException {
        geefBerichten(MUTATIEBERICHT, leveringautorisatieNaam);
    }

    /**
     * When stap waarbij het volledige bericht is ontvangen en bekeken wordt.
     *
     * @param leveringautorisatieNaam naam van de leverings autorisatie
     * @throws JMSException als er fout optreedt bij het versturen van berichten
     */
    @When("het volledigbericht voor leveringsautorisatie %leveringautorisatieNaam is ontvangen en wordt bekeken")
    public void whenVolledigberichtIsOntvangenEnWordtBekeken(final String leveringautorisatieNaam) throws JMSException {
        geefBerichten(VOLLEDIGBERICHT, leveringautorisatieNaam);
    }

    /**
     * When stap waarbij het volledige bericht wordt bekeken.
     *
     * @param leveringautorisatieNaam naam van de leverings autorisatie
     * @throws JMSException als er fout optreedt bij het versturen van berichten
     */
    @When("volledigbericht voor leveringsautorisatie %leveringautorisatieNaam wordt bekeken")
    public void whenVolledigberichtWordtBekeken(final String leveringautorisatieNaam) throws JMSException {
        geefBerichten(VOLLEDIGBERICHT, leveringautorisatieNaam);
    }

    /**
     * When stap waarbij de persoon wordt gerelateerd met zijn niet ingeschreven gerelateerde personen.
     *
     * @param anummer A-nummer van de persoon
     */
    @When("persoon %anummer wordt gerelateerd")
    public void whenPersonenWordenGerelateerd(final long anummer) {
        getBrpOmgeving().relateren().relateren();
    }

    /**
     * Then stap waarbij gewacht wordt tot alle berichten zijn ontvangen.
     *
     * @deprecated methode is deprecated.
     */
    @Deprecated
    @Then("wacht tot alle berichten zijn ontvangen")
    public void thenWachtTotAlleBerichtenZijnOntvangen() {
        LOGGER.warn("Step: 'wacht tot alle berichten zijn ontvangen' is deprecated");
    }

    /**
     * Then stap waarbij de opgegeven persoon wordt terug gemapped naar het conversie model.
     *
     * @param anummer A-nummer van de persoon
     * @throws IOException        als het bestand niet kan worden geschreven
     * @throws URISyntaxException Als het opgegeven pad van het output-bestand niet geldig is
     */
    @Then("vergelijk persoon %anummer met expected")
    public void thenVergelijkPersoonMetExcpected(final Long anummer) throws URISyntaxException, IOException {
        final BrpPersoonslijst brpPersoonslijst = getBrpOmgeving().gba().mapTerug(anummer);
        final Path storiesPath = geefStoryController().getStoryPath();
        final String scenarioNaam = geefStoryController().getScenarioNaam();

        schrijfPersoonNaarBestand(anummer, brpPersoonslijst, storiesPath, scenarioNaam);
        final BrpPersoonslijst expectedPersoonslijst = leesVerwachtePersoonslijst(anummer, storiesPath, scenarioNaam);

        if (expectedPersoonslijst != null) {
            final StringBuilder builder = new StringBuilder();
            final boolean result = BrpVergelijker.vergelijkPersoonslijsten(new StringBuilder(), expectedPersoonslijst, brpPersoonslijst, true, true);

            assert result;
        }
    }

    private void schrijfPersoonNaarBestand(final Long anummer, final BrpPersoonslijst brpPersoonslijst, final Path storiesPath, final String scenarioNaam)
        throws URISyntaxException, IOException
    {
        final String bestandsnaam = getBestandsnaam(anummer, storiesPath, scenarioNaam, FOLDER_OUTPUT);
        final URI fileLocation = new URI(bestandsnaam);

        final File xmlFile = new File(fileLocation);
        System.out.println("writing to file: " + xmlFile.toString());
        final MigratieModelXmlOutput writer = new MigratieModelXmlOutput();
        writer.schrijfXml(brpPersoonslijst, xmlFile);
    }

    private BrpPersoonslijst leesVerwachtePersoonslijst(final Long anummer, final Path storiesPath, final String scenarioNaam) throws URISyntaxException,
        IOException
    {
        final String bestandsnaam = getBestandsnaam(anummer, storiesPath, scenarioNaam, FOLDER_EXPECTEDS);
        final URI fileLocation = new URI(bestandsnaam);

        final File xmlFile = new File(fileLocation);
        System.out.println("reading file: " + xmlFile.toString());

        return MigratieModelXmlOutput.readXml(BrpPersoonslijst.class, xmlFile);
    }

    private String getBestandsnaam(final Long anummer, final Path storiesPath, final String scenarioNaam, final String foldernaam) {
        final StringBuilder bestandsnaam = new StringBuilder(storiesPath.toString()).append(File.separator).append(foldernaam).append(File
            .separator);
        if (!scenarioNaam.isEmpty()) {
            bestandsnaam.append(scenarioNaam).append("-");
        }
        bestandsnaam.append(anummer).append(EXTENSIE_XML);
        return bestandsnaam.toString();
    }

    private void geefBerichten(final String berichtType, final String abo) throws JMSException {
        final List<Afnemerbericht> berichten = getBrpOmgeving().routering().geefAfnemerberichten("AFNEMER-199900");
        for (Afnemerbericht afnemerbericht : berichten) {
            LOGGER.info(afnemerbericht.toString());
            //TODO check volledig / mutatie
            if (abo.equals(afnemerbericht.getAbonnement())) {
                LOGGER.info("Gevonden!");
            }
        }
        //assertFalse(berichten.isEmpty());
    }

    /**
     * Then stap waarbij gecontroleerd wordt of het bericht voldoet aan de XSD.
     */
    @Then("is het bericht xsd-valide")
    public void berichtValide() {

    }

    /**
     * Before scenario stap waarbij aan de hand van de genoemde componenten in de stories, de juiste omgeving wordt klaar gezet.
     *
     * @param componenten de lijst van componenten die gestart moeten worden
     * @throws InterruptedException als het wachten tot de omgeving klaar is en draait, wordt onderbroken
     */
    @BeforeScenario
    public void voorScenario(@Named("componenten") final List<String> componenten) throws InterruptedException {
        LOGGER.info("Voor Scenario");

        final StoryController controller = StoryController.get();
        if (controller.getBrpOmgeving() != null) {
            LOGGER.info("Omgeving reeds gestart");
            controller.getBrpOmgeving().database().verwijderAllePersonen();
            return;
        }

        final OmgevingBouwer bouwer = new OmgevingBouwer();
        for (final String component : componenten) {
            if ("database".equals(component)) {
                bouwer.metLegeDatabase();
            } else if ("routeringcentrale".equals(component)) {
                bouwer.metDummyRouteringCentrale();
            } else if ("mutatielevering".equals(component)) {
                bouwer.metMutatielevering();
            } else if ("relateren".equals(component)) {
                bouwer.metRelateren();
            }
        }

        final BrpOmgeving omgeving = bouwer.maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        geefStoryController().setBrpOmgeving(omgeving);
    }

    /***
     * Before story stap.
     */
    @BeforeStory
    public void voorStory() {
        Thread.currentThread().setName("storyrunner: " + Thread.currentThread().getName());
    }

    /**
     * After scenario stap als het resultaat van de test een {@link org.jbehave.core.annotations.AfterScenario.Outcome#FAILURE} is.
     */
    @AfterScenario(uponOutcome = AfterScenario.Outcome.FAILURE)
    public void naGefaaldScenario() {
        System.err.println("Na gefaald scenario");
    }

    /**
     * After scenario stap waarbij de BRP omgeving gestopt wordt.
     */
    @AfterStory
    public void naStory() {
        if (geefStoryController().getBrpOmgeving() != null) {
            geefStoryController().getBrpOmgeving().stop();
        }
        geefStoryController().setBrpOmgeving(null);
    }


    private StoryController geefStoryController() {
        return StoryController.get();
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.DockerNaam;
import nl.bzk.brp.dockertest.component.Environment;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 * JBehave steps voor het starten en stoppen van een omgeving.
 */
public class OmgevingSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Instant starttijdStories;
    private Instant starttijdStory;
    private Instant starttijdScenario;

    /**
     * Before stories.
     */
    @BeforeStories
    public void beforeStories() {
        starttijdStories = Instant.now();
    }

    /**
     * Before story.
     */
    @BeforeStory
    public void beforeStory() {
        starttijdStory = Instant.now();
    }

    /**
     * BeforeScenario
     */
    @BeforeScenario
    public void beforeScenario() {
        starttijdScenario = Instant.now();
    }

    /**
     * After scenario.
     */
    @AfterScenario
    public void afterScenario() {
        final Instant eindtijdScenario = Instant.now();
        final Duration scenarioDuur = Duration.between(starttijdScenario, eindtijdScenario);
        final int milliseconden = Math.floorDiv(scenarioDuur.getNano(), 1000000);
        LOGGER.info(String.format("Scenario '%s' duurde %d seconde(n) en %d milliseconde(n)", JBehaveState.getScenarioState().getCurrectScenario(),
                scenarioDuur.getSeconds(), milliseconden));
        starttijdScenario = null;
    }

    /**
     * After stories.
     */
    @AfterStories
    public void naStories() {
        final Instant eindtijdStories = Instant.now();
        final Duration scenarioDuur = Duration.between(starttijdStories, eindtijdStories);
        final int milliseconden = Math.floorDiv(scenarioDuur.getNano(), 1000000);
        LOGGER.info(String.format("E2E teststories duurden %d seconde(n) en %d milliseconde(n)", scenarioDuur.getSeconds(), milliseconden));
        starttijdStories = null;
    }

    /**
     * Logregel validatie step.
     * @param regel de bedrijfsregel code
     */
    @Then("is er een logregel gelogd met regel $regel in container $container")
    public void isErEenLogregelGelogdMetRegel(String regel, String container) {
        JBehaveState.get().algemeenService().isErEenLogregelGelogdMetRegel(regel, DockerNaam.valueOf(container));
    }

    /**
     * After story.
     */
    @AfterStory
    public void afterStory() {
        final Instant eindtijdStory = Instant.now();
        final Duration scenarioDuur = Duration.between(starttijdStory, eindtijdStory);
        final int milliseconden = Math.floorDiv(scenarioDuur.getNano(), 1000000);
        LOGGER.info(String.format("Story duurde %d seconde(n) en %d milliseconde(n)", scenarioDuur.getSeconds(), milliseconden));
        starttijdStory = null;
    }

    @Then("wacht")
    public void wacht() throws InterruptedException {
        if (Environment.isJenkinsRun()) {
            return;
        }
        TimeUnit.MINUTES.sleep(5);
    }

    /**
     * wacht het gegeven aantal seconden
     * @param aantalSeconden aantal seconden
     */
    @Given("wacht $aantalSeconden seconden")
    public void wachtSeconden(String aantalSeconden) throws InterruptedException {
        if (Environment.isJenkinsRun()) {
            return;
        }
        LOGGER.info("Wacht {} seconden", aantalSeconden);
        TimeUnit.SECONDS.sleep(Integer.parseInt(aantalSeconden));
    }

    /**
     * wacht het gegeven aantal seconden
     * @param minuten aantal seconden
     */
    @Given("wacht $minuten minuten")
    public void wachtMinuten(String minuten) throws InterruptedException {
        if (Environment.isJenkinsRun()) {
            return;
        }
        LOGGER.info("Wacht {} minuten", minuten);
        TimeUnit.MINUTES.sleep(Integer.parseInt(minuten));
    }

    /**
     * ==== Zet indicatie dat cache ververst dient te worden Zet indicatie dat cache ververst dient te worden
     */
    @Deprecated
    @Given("de cache is herladen")
    public void herlaadCache() {
        LOGGER.info("Dit gebeurt nu selectief bij het verzoek");
        JBehaveState.get().cache().refresh();
    }

    /**
     * After scenario stap als het resultaat van de test een {@link org.jbehave.core.annotations.AfterScenario.Outcome#FAILURE} is.
     */
    @AfterScenario(uponOutcome = AfterScenario.Outcome.FAILURE)
    public void naGefaaldScenario() throws InterruptedException {
        LOGGER.error("Scenario gefaald");
        //start nieuwe thread om interrupts te voorkomen die de tests doen stoppen
        doLog();

    }

    @Then("print de logbestanden")
    public void doLog() throws InterruptedException {
        JBehaveState.get().algemeenService().doLog();;
    }

}

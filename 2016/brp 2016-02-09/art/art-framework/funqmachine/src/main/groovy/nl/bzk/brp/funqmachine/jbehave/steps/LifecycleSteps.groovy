package nl.bzk.brp.funqmachine.jbehave.steps

import org.jbehave.core.annotations.BeforeStory

import javax.inject.Inject
import nl.bzk.brp.funqmachine.jbehave.context.BevraagbaarContextView
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioRunContext
import nl.bzk.brp.funqmachine.ontvanger.LeveringOntvanger
import org.jbehave.core.annotations.AfterScenario
import org.jbehave.core.annotations.BeforeScenario
import org.jbehave.core.annotations.Then
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
/**
 *
 */
@Steps
class LifecycleSteps {
    private Logger logger = LoggerFactory.getLogger(BrpContextStepMonitor)

    @Inject
    private JmxConnectorService jmxConnector;

    @Autowired
    private ScenarioRunContext runContext

    @Autowired
    private BevraagbaarContextView contextView

    @Inject
    private LeveringOntvanger ontvanger

    @Autowired
    private DatabaseSteps databaseSteps

    @BeforeScenario
    final void setUp() {
        runContext.start()
        jmxConnector.purgeQueues()
    }

    @AfterScenario(uponOutcome = AfterScenario.Outcome.ANY)
    final void saveState() {
        runContext.schrijfResultaat(contextView)
        logger.debug 'Beschikbare resultaten weggeschreven voor story={} scenario={}', contextView.story, contextView.scenario
    }

    @AfterScenario(uponOutcome = AfterScenario.Outcome.ANY)
    void logKlaar() {
        logger.info 'Klaar met story={} scenario={}', contextView.story, contextView.scenario
    }

    @BeforeStory
    void voorElkeStory() {
//        databaseSteps.resetDeHeleDatabase()
    }

    /**
     * ==== Scenario resultaten weggooien
     * Indien het niet nodig is om resultaten van een scenario te bewaren,
     * kan deze step aan het einde daarvan worden toegevoegd. De verzamelde
     * requests en responses worden dan niet bewaard (en weggeschreven). Indien een
     * voorgaan step faalt, wordt deze step niet uitgevoerd en de dan beschikbare
     * resultaten dus wel gegeschreven. Hiermee wordt effectief alleen het resultaat
     * weggegooid als het scenario succesvol is uitgevoerd.
     *
     * Een andere mogelijkheid is om in een uitgebreid scenario, halverwege de resultaten
     * weg te gooien, omdat deze niet interessant (genoeg) zijn voor de uitkomst van het
     * scenario.
     */
    @Then("gooi scenario resultaten weg")
    void scenarioResultatenWeggooien() {
        runContext.discardResultaten()
        logger.debug 'Resultaten weggegooid van story={} scenario={}', contextView.story, contextView.scenario
    }
}

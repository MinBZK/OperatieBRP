package nl.bzk.brp.funqmachine.jbehave.reporters

/**
 * Abstractie voor een jbehave .story .
 */
class TestStory {
    String name

    /** De scenario's in deze story. */
    @Delegate List<TestScenario> scenarios = []

    /**
     * Geeft het aantal scenario's met een error.
     */
    int errors() {
        scenarios.count { it.error() }
    }

    /**
     * Geeft het aantal scenario's dat is gefaald.
     */
    int failed() {
        scenarios.count { it.failed() }
    }

    /**
     * Geeft het aantal scenario's dat is overgeslagen.
     */
    int skipped() {
        scenarios.count { it.skipped }
    }

    /**
     * Geeft de naam van de story, ofwel de naam van het .story bestand
     * zonder de extensie.
     */
    String getName() {
        return name.split('/').last().split('\\.').head()
    }
}

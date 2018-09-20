package nl.bzk.brp.funqmachine.jbehave.reporters

/**
 * Abstractie voor een jbehave scenario.
 */
class TestScenario {
    String name

    /** Is dit scenario overgeslagen? */
    boolean skipped

    /** De steps in dit scenario. */
    @Delegate List<TestStep> steps = []

    /**
     * Heeft dit scenario een step met de status error.
     */
    boolean error() {
        steps.any { it.status == TestStep.Status.ERROR }
    }

    /**
     * Heeft dit scenario een step met de status gefaald.
     */
    boolean failed() {
        steps.any { it.status == TestStep.Status.FAILED }
    }

    /**
     * Geeft de exceptie van de gefaalde step.
     * @return de exceptie
     */
    Throwable getError() {
        steps.find { it.status == TestStep.Status.FAILED }?.ex
    }
}

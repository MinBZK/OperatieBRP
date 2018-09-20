package nl.bzk.brp.funqmachine.jbehave.reporters

/**
 * Abstractie voor een jbehave step in een scenario.
 */
class TestStep {
    String name
    /** De gegooide exceptie. */
    Throwable ex
    /** De status van deze step. */
    Status status

    /**
     * Was de step succesvol?
     */
    boolean successful() {
        !ex
    }

    /**
     * Is de step gefaald?
     */
    boolean failed() {
        !successful()
    }

    /**
     * Mogelijke statussen voor een step.
     */
    enum Status {
        FAILED, ERROR, SKIPPED
    }
}

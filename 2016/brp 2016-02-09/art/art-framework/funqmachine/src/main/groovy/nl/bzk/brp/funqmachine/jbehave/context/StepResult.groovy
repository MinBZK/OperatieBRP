package nl.bzk.brp.funqmachine.jbehave.context

/**
 * Resultaat van een {@link org.jbehave.core.steps.Step}.
 */
class StepResult {
    final Soort soort

    def request
    def response
    def expected

    /**
     * Constructor.
     * @param soort het soort resultaat
     */
    StepResult(final Soort soort) {
        this.soort = soort
    }

    /**
     * De soorten van het resultaat dat kan worden vastgelegd.
     */
    public enum Soort {
        /** Resultaat uit een database. */
        DATA,
        /** Resultaat van een request-response Soap verzoek. */
        SOAP,
        /** Resultaat van data. */
        VALUES,
        /** Resultaat vna een asynchroon ontvangen Soap bericht. */
        LEVERING
    }
}

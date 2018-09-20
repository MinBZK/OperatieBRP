package nl.bzk.brp.funqmachine.jbehave.context
/**
 * Lijst van resultaten van de verschillende {@link org.jbehave.core.steps.Step}s.
 */
class ScenarioResult implements Iterable<StepResult> {
    @Delegate
    List<StepResult> stappen
    String story
    String identifier

    /**
     * Constructor, met default identifier.
     */
    ScenarioResult() {
        this('Onbekend')
    }

    /**
     * Constructor met de naam van het scenario als identifier voor het resultaat.
     * @param identifier de id voor dit resultaat
     */
    ScenarioResult(final String identifier) {
        this.identifier = identifier
        this.stappen = []
    }

    StepResult soapResult() {
        stappen.findAll { it.soort == StepResult.Soort.SOAP }.last()
    }

    StepResult data() {
        stappen.findAll { it.soort == StepResult.Soort.VALUES }.last()
    }

    /**
     * Geeft de identifier van dit resultaat.
     */
    String getIdentifier() {
        return identifier.replaceAll(' ', '_')
    }

    /**
     * Geeft een geformatteerde versie van de story.
     * @return De story naam, ofwel de story zonder de {@code .story} extensie
     */
    String getStoryName() {
        return story.split('/').last().split('\\.').first().replaceAll(' ', '_')
    }

    /**
     * Zet de waardes van story en identifier, opgehaald uit de context.
     *
     * @param context de context voor dit resultaat
     */
    void voorContext(BevraagbaarContextView context) {
        this.identifier = context.scenario
        this.story = context.story
    }

    /**
     * Geeft een path, waar de {@link StepResult} resultaten worden geplaatst.
     *
     * @return path, gebaseerd op story en identifier
     */
    String getPath() {
        return [getStoryName(), getIdentifier().take(40)].join('/')
    }

    @Override
    Iterator<StepResult> iterator() {
        return stappen.iterator()
    }
}

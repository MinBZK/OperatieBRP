package nl.bzk.brp.funqmachine.jbehave.context

import nl.bzk.brp.funqmachine.schrijvers.FileWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Default (en enige) implementatie van {@link ScenarioRunContext}.
 */
@Component
@Scope('story')
class DefaultScenarioRunContext implements ScenarioRunContext {
    private Map<String, Object> data = [:]

    @Autowired
    private FileWriter fileWriter

    private boolean cacheHeeftActivatieRefreshNodig = false
    private boolean cacheHeeftRefreshNodig = false

    /** Het scenario resultaat in deze context. */
    @Delegate private ScenarioResult result

    /**
     * Constructor.
     */
    DefaultScenarioRunContext() {
        this.result = new ScenarioResult()
    }

    @Override
    void start() {
        this.result = new ScenarioResult()
        this.data = [:]
    }

    @Override
    void schrijfResultaat(BevraagbaarContextView contextView) {
        if (result) {
            result.voorContext(contextView)
            fileWriter.write(result, true)
        }
    }

    @Override
    void stop() {
        this.result = null
        this.data.clear()
    }

    @Override
    void discardResultaten() {
        this.result.clear()
    }

    @Override
    Map<String, Object> getData() {
        return this.data
    }

    @Override
    void setData(Map<String, Object> map) {
        this.data = map
    }

    @Override
    boolean isCacheHeeftActivatieRefreshNodig() {
        return cacheHeeftActivatieRefreshNodig
    }

    @Override
    void setCacheHeeftActivatieRefreshNodig(final boolean cacheHeeftActivatieRefreshNodig) {
        this.cacheHeeftActivatieRefreshNodig = cacheHeeftActivatieRefreshNodig
    }

    @Override
    boolean isCacheHeeftRefreshNodig() {
        return cacheHeeftRefreshNodig
    }

    @Override
    void setCacheHeeftRefreshNodig(final boolean cacheHeeftRefreshNodig) {
        this.cacheHeeftRefreshNodig = cacheHeeftRefreshNodig
    }

    @Override
    public StepResult geefLaatsteVerzoek() {
        return result.soapResult()
    }

    /**
     * Voeg een {@link StepResult} toe aan de context.
     * @param stepResult
     */
    void leftShift(StepResult stepResult) {
        this.result.leftShift(stepResult)
    }

    /**
     * Voeg een {@link StepResult} toe aan de context.
     * @param stepResult
     */
    boolean add(StepResult stepResult) {
        return this.result.add(stepResult)
    }
}

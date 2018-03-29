/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.funqmachine.schrijvers.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Default (en enige) implementatie van {@link ScenarioRunContext}.
 */
@Component
@Scope("story")
public class DefaultScenarioRunContext implements ScenarioRunContext {

    private Map<String, Object> data = new LinkedHashMap<>();
    private final FileWriter fileWriter;

    /**
     * Het scenario resultaat in deze context.
     */
    private ScenarioResult result;

    /**
     * Constructor.
     * @param fileWriter een {@link FileWriter}
     */
    @Autowired
    public DefaultScenarioRunContext(final FileWriter fileWriter) {
        this.result = new ScenarioResult();
        this.fileWriter = fileWriter;
    }

    @Override
    public void start() {
        this.result = new ScenarioResult();
        this.data = new LinkedHashMap<>();
    }

    @Override
    public void schrijfResultaat(final BevraagbaarContextView contextView) {
        if (result.bevatResultatenVoorSteps()) {
            result.voorContext(contextView);
            fileWriter.write(result, true);
        }
    }

    @Override
    public Map<String, Object> getData() {
        return this.data;
    }

    @Override
    public void setData(final Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public StepResult geefLaatsteVerzoek() {
        return result.getSoapResult();
    }

    @Override
    public List<StepResult> geefErrorResultaten() {
        return result.geefErrorResultaten();
    }

    @Override
    public StepResult getBlobResult(final Number persId) {
        return result.blobResult(persId);
    }

    /**
     * Voeg een {@link StepResult} toe aan de context.
     * @param stepResult het resultaat van een step
     */
    public void add(final StepResult stepResult) {
        this.result.voegStepResultaatToe(stepResult);
    }

    @Override
    public void voegStepResultaatToe(final StepResult stepResult) {
        this.result.voegStepResultaatToe(stepResult);
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

import org.jbehave.core.context.ContextView;
import org.springframework.stereotype.Component;

/**
 * Implementatie van een ContextView waaraan we de waardes kunnen opvragen.
 */

@Component
public class BevraagbaarContextView implements ContextView {

    private String story;
    private String scenario;
    private String step;

    @Override
    public void show(final String sty, final String scn, final String stp) {
        story = sty;
        scenario = scn;
        step = stp;
    }

    @Override
    public void close() {
        // doe niks
    }

    @Override
    public String toString() {
        return "BevraagbaarContextView{story=\'" + story + "\', scenario=\'" + scenario + "\', step=\'" + step + "\'}";
    }

    public String getStory() {
        return story;
    }

    public void setStory(final String story) {
        this.story = story;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(final String scenario) {
        this.scenario = scenario;
    }
}

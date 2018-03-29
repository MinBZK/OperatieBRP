/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import nl.bzk.brp.dockertest.component.BrpOmgeving;
import org.jbehave.core.model.Story;

/**
 * JBehave helper class voor het vasthouden van de omgeving.
 */
public class JBehaveState {

    private static final ThreadLocal<BrpOmgeving> OMGEVING_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<ScenarioState> STATE_THREAD_LOCAL = ThreadLocal.withInitial(ScenarioState::new);

    /**
     *
     * @return
     */
    public static BrpOmgeving get() {
        return OMGEVING_THREAD_LOCAL.get();
    }

    public static void set(BrpOmgeving jBehaveState) {
        OMGEVING_THREAD_LOCAL.set(jBehaveState);
    }

    public static ScenarioState getScenarioState() {
        return STATE_THREAD_LOCAL.get();
    }

    /**
     *
     */
    public static final class ScenarioState {

        private Story currentStory;
        private String currectScenario;

        public void setCurrentStory(final Story currentStory) {
            this.currentStory = currentStory;
        }

        public void setCurrectScenario(final String currectScenario) {
            this.currectScenario = currectScenario;
        }

        public String getCurrectScenario() {
            return currectScenario;
        }

        public Story getCurrentStory() {
            return currentStory;
        }
    }
}

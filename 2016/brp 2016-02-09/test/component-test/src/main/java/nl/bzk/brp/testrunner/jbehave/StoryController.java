/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.jbehave;

import java.nio.file.Path;
import nl.bzk.brp.testrunner.component.BrpOmgeving;

/**
 * Controller met daarin informatie welke nodig is tijdens het verwerken van de stories.
 */
public final class StoryController {

    private static ThreadLocal<StoryController> threadLocal = new ThreadLocal<StoryController>() {
        @Override
        protected StoryController initialValue() {
            return new StoryController();
        }
    };
    private BrpOmgeving brpOmgeving;
    private Path        storyPath;
    private String      scenarioNaam;

    /**
     * Private constructor voor een utility-class.
     */
    private StoryController() {
    }

    /**
     * Geeft de instantie van {@link StoryController} terug.
     *
     * @return de {@link StoryController}
     */
    public static StoryController get() {
        return threadLocal.get();
    }

    public BrpOmgeving getBrpOmgeving() {
        return brpOmgeving;
    }

    public void setBrpOmgeving(final BrpOmgeving brpOmgeving) {
        this.brpOmgeving = brpOmgeving;
    }

    public void setStoryPath(final Path storyPath) {
        this.storyPath = storyPath;
    }

    public Path getStoryPath() {
        return this.storyPath;
    }

    public String getScenarioNaam() {
        return scenarioNaam;
    }

    public void setScenarioNaam(final String scenarionaam) {
        this.scenarioNaam = scenarionaam;
    }
}

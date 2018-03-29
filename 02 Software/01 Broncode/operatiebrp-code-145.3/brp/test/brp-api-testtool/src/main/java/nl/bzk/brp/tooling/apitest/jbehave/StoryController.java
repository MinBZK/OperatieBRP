/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.tooling.apitest.StoryOmgeving;

/**
 * Controller met daarin informatie welke nodig is tijdens het verwerken van de stories.
 */
public final class StoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final ThreadLocal<StoryController> THREADLOCAL = new ThreadLocal<StoryController>() {
        @Override
        protected StoryController initialValue() {
            return new StoryController();
        }
    };

    private final StoryOmgeving storyOmgeving = new StoryOmgeving();

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
        return THREADLOCAL.get();
    }

    /**
     * Geeft de instantie van {@link StoryController} terug.
     *
     * @return de {@link StoryController}
     */
    public static StoryOmgeving getOmgeving() {
        return THREADLOCAL.get().getStoryOmgeving();
    }

    public StoryOmgeving getStoryOmgeving() {
        return storyOmgeving;
    }

    /**
     * Jbehave hook, wordt aangeroepen voor na elk scenario.
     */
    public void naScenario() {
        storyOmgeving.reset();
    }

    /**
     * Jbehave hook, wordt aangeroepen voor elke story.
     */
    public void voorStory() {
    }

    /**
     * Jbehave hook, wordt aangeroepen na elke story.
     */
    public void naStory() {
    }
}

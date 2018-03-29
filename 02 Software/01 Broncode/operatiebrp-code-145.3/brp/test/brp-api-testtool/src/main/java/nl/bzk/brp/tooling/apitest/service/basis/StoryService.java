/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis;

import java.io.File;
import org.springframework.core.io.Resource;

/**
 * Stub interface voor storyinformatie.
 */
public interface StoryService {

    /**
     * Zet de story.
     * @param story de story
     */
    void setStory(String story);

    /**
     * Zet het scenario.
     * @param scenario het scenario
     */
    void setScenario(String scenario);

    /**
     * Zet de step.
     * @param step de step.
     */
    void setStep(String step);

    /**
     * @return de actuele story
     */
    String getStory();

    /**
     * @return het actuele scenario
     */
    String getScenario();

    /**
     * @return de actuele step
     */
    String getStep();

    /**
     * @return de actuele output directory
     */
    File getOutputDir();

    /**
     * Creert een resource obv het gegeven path. Indien path begint met '/' wordt het gezien als een classpath lookup, in het
     * andere geval wordt het gezien als een relatief path ten opzichten van de story.
     *
     * @param path het pad naar een gegeven file
     * @return een resource
     */
    Resource resolvePath(String path);
}

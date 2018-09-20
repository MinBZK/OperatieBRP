/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.funqmachine.AcceptanceTestsConfiguration;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringUtils;

/**
 * Basic test for running JBehave stories.
 */
@ContextConfiguration(classes = AcceptanceTestsConfiguration.class)
@ActiveProfiles("tests")
@DirtiesContext
public final class ArtStoryRunner extends AbstractSpringJBehaveStories {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    protected List<String> alleStoryPaths() {
        final String filter = System.getProperty("storyfilter");
        if (StringUtils.isEmpty(filter)) {
            throw new IllegalArgumentException("-Dstoryfilter is niet gezet");
        }

        LOGGER.info("Start ArtStoryRunner met filter: " + filter);
        //return Collections.singletonList(filter);

        final String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, Collections.singletonList(filter), asList(""));
    }

    /**
     * metafilter
     **/
    protected List<String> metaFilters() {
        return Collections.singletonList("groovy: status == 'Klaar'");
    }

}

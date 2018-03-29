/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.brp.acceptance.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.funqmachine.AcceptanceTest;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
public class UseCaseStoryFileRunner extends AbstractSpringJBehaveStories {
    @Override
    protected List<String> alleStoryPaths() {
        List<String> includes = includeFilter();

        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, includes, Arrays.asList(""));
    }

    public List<String> includeFilter() {
        return new ArrayList<String>(Arrays.asList("**/use_case_arts_nieuw/**/**/1.1_Element_Stamgegevens.story"));
    }

}

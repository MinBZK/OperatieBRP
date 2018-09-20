package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.StoryFinder

import static java.util.Arrays.asList

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class UseCaseStoryFileRunner extends AbstractSpringJBehaveStories {



    @Override
    protected List<String> alleStoryPaths() {
        def includes = includeFilter()

        String codeLocation = CodeLocations.codeLocationFromClass(this.class).file
        return new StoryFinder().findClassNames(codeLocation, includes, asList(""))
    }


    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/**/**/1.1_Element_Stamgegevens.story']
    }
}
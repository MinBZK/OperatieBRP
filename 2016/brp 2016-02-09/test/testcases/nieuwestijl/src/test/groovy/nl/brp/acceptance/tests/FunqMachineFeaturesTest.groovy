package nl.brp.acceptance.tests

import static java.util.Arrays.asList

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.StoryFinder

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class FunqMachineFeaturesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
        ["groovy: status == 'Uitgeschakeld' && auteur == 'sasme'"]
    }

    @Override
    protected List<String> alleStoryPaths() {
        def includes = ['**/demo/**/*.story']

        String codeLocation = CodeLocations.codeLocationFromClass(this.class).file
        return new StoryFinder().findClassNames(codeLocation, includes, asList(""))
    }

}

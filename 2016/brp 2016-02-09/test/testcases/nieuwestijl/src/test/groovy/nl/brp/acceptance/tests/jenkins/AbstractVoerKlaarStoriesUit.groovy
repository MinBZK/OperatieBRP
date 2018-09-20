package nl.brp.acceptance.tests.jenkins

import static java.util.Arrays.asList

import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.StoryFinder

/**
 * Abstracte klasse die alle stories die de status "Klaar" hebben selecteert.
 */
abstract class AbstractVoerKlaarStoriesUit extends AbstractSpringJBehaveStories {

    abstract List<String> includeFilter();

    @Override
    protected List<String> alleStoryPaths() {
        def includes = includeFilter()

        String codeLocation = CodeLocations.codeLocationFromClass(this.class).file
        return new StoryFinder().findClassNames(codeLocation, includes, asList(""))
    }

    @Override
    protected List<String> metaFilters() {
        ["groovy: status == 'Klaar'"]
    }
}

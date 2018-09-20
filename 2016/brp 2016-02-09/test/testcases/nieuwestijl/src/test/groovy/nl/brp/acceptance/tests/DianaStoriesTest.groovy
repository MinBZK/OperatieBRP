package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class DianaStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
        ["groovy: sleutelwoorden == 'diana'"]
    }
}

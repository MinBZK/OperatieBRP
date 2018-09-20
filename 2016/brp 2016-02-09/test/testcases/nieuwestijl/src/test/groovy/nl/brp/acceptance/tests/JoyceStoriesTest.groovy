package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class JoyceStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
        ["groovy: regels =~ 'R1348_L04'"]
    }
}

package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class GreetStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
//      ["groovy: status == 'Onderhanden' && auteur == 'dihoe'"]
        ["groovy: regels == 'greet'"]
//      ["groovy: regels && regels.split(',').contains('VR00078')"]
    }
}

package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class VoerAlleStoriesUitTest extends AbstractSpringJBehaveStories {

    @Override
    protected List<String> metaFilters() {
        ["groovy: status == 'Klaar'"]
    }
}

package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

@AcceptanceTest
class MichaelStoriesTest extends AbstractSpringJBehaveStories {

    @Override
    List<String> metaFilters() {
        ["groovy: regels =~ 'R1260, R1261, R1262, R1263, R1264, R2054, R2053, R2055, R2056'"]
    }
}
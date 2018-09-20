package nl.bzk.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories


@AcceptanceTest
class IntakeMutatieleveringTest extends AbstractSpringJBehaveStories {

    @Override
    protected List<String> metaFilters() {
        return ["groovy: module == 'intake-mutatielevering' && status == 'Klaar'"]
    }

}

package nl.bzk.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories

@AcceptanceTest
class TechnischALPTest extends AbstractSpringJBehaveStories {

    @Override
    protected List<String> metaFilters() {
        return ["groovy: module == 'technisch-alp' && status == 'Klaar'"]
    }
}

package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class AfnemerindicatiesStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/afnemerindicaties/**/*.story']
    }
}

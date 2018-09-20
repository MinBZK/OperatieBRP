package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class MigratieStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/migratie/**/*.story']
    }
}

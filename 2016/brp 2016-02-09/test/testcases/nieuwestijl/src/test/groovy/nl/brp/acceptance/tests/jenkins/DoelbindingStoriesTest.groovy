package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class DoelbindingStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/doelbinding/**/*.story']
    }
}

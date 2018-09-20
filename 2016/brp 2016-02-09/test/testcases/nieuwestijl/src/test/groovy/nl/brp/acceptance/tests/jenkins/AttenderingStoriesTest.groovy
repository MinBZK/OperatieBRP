package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class AttenderingStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/attendering/**/*.story']
    }
}

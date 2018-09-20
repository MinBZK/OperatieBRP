package nl.brp.acceptance.tests

import nl.brp.acceptance.tests.jenkins.AbstractVoerKlaarStoriesUit
import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class ProeftuinStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/proeftuin/**/*.story']
    }
}

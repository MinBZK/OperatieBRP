package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class GeconverteerdeLeveringArtStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/geconverteerd_ARTS/**/*.story']
    }
}

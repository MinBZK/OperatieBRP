package nl.brp.acceptance.tests.jenkins
import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class UseCaseArtTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/**/*.story']
    }

}

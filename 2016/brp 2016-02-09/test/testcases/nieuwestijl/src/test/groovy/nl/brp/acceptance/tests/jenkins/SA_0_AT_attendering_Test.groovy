package nl.brp.acceptance.tests.jenkins
import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class SA_0_AT_attendering_Test extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/SA.1.LM_Lever_Mutaties/SA.0.AT_attendering/*.story']
    }

}

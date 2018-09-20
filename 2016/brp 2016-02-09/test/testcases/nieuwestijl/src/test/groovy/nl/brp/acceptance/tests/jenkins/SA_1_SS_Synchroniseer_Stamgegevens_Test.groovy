package nl.brp.acceptance.tests.jenkins
import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class SA_1_SS_Synchroniseer_Stamgegevens_Test extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/SA.1.SS_Synchroniseer_Stamgegevens/*.story']
    }

}

package nl.brp.acceptance.tests.jenkins
import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class SA_1_PA_Plaats_Afnemerindicatie_Test extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/SA.1.PA_Plaats_Afnemerindicatie/*.story']
    }

}

package nl.brp.acceptance.tests.jenkins
import nl.bzk.brp.funqmachine.AcceptanceTest

@AcceptanceTest
class SA_0_AA_attendering_met_plaatsen_afnemerindicatie_Test extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/SA.1.LM_Lever_Mutaties/SA.0.AA_attendering_met_plaatsen_afnemerindicatie/*.story']
    }

}

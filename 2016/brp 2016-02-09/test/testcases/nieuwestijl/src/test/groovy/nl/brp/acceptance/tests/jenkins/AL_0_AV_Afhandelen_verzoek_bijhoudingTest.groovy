package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

/**
 * Draait de testen in de AL.0.AV - Afhandelen verzoek map binnen de testcases/bijhouding map.
 */
@AcceptanceTest
class AL_0_AV_Afhandelen_verzoek_bijhoudingTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/bijhouding/AL.0.AV_Afhandelen_verzoek/**/*.story']
    }

}

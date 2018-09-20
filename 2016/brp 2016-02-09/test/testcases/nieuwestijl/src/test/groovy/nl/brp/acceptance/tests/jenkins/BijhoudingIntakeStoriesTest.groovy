package nl.brp.acceptance.tests.jenkins

import nl.bzk.brp.funqmachine.AcceptanceTest

/**
 * De klasse om de Bijhouding Intaketest te draaien binnen de Funqmachine.
 */
@AcceptanceTest
class BijhoudingIntakeStoriesTest extends AbstractVoerKlaarStoriesUit {

    @Override
    List<String> includeFilter() {
        ['**/testcases/bijhouding/intake/**/*.story']
    }
}

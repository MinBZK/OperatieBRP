package nl.brp.acceptance.tests

import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.StoryFinder

import static java.util.Arrays.asList

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
class JaronStoriesTest extends AbstractSpringJBehaveStories {

    List<String> includeFilter() {
        //['**/use_case_arts_nieuw/**/**/archiveer_bericht_lvg_syn.story']
        //['**/testcases/**/**/voltrekking_huwelijk_in_nederland.story']
        ['**/testcases/**/**/casus_50_onderzoek_datum_einde_kleiner_dan_datum_aanvang_materiele_periode_bij_afnemerindicatie.story']
    }
}

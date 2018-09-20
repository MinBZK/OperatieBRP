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
class StoryFileRunner extends AbstractSpringJBehaveStories {



    @Override
    protected List<String> alleStoryPaths() {
        def includes = includeFilter()

        String codeLocation = CodeLocations.codeLocationFromClass(this.class).file
        return new StoryFinder().findClassNames(codeLocation, includes, asList(""))
    }


    List<String> includeFilter() {
        //['**/use_case_arts_nieuw/**/**/archiveer_bericht_lvg_syn.story']
        //['**/testcases/**/**/voltrekking_huwelijk_in_nederland.story']
        //['**/testcases/**/**/opnieuw_plaatsen_afnemerindicatie.story']
        //['**/testcases/**/**/datum_einde_geldigheid_gelijk_of_kleiner_dan_datum_materiele_periode.story']
        ['**/testcases/**/**/datum_einde_geldigheid_onbekend_groepen_wel_geleverd.story']
    }
}
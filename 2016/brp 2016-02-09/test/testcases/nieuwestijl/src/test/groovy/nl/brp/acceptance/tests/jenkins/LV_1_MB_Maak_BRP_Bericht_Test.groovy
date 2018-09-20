package nl.brp.acceptance.tests.jenkins
import nl.bzk.brp.funqmachine.AcceptanceTest
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories
import org.jbehave.core.io.CodeLocations
import org.jbehave.core.io.StoryFinder

import static java.util.Arrays.asList

@AcceptanceTest
class LV_1_MB_Maak_BRP_Bericht_Test extends AbstractSpringJBehaveStories {



    @Override
    protected List<String> alleStoryPaths() {
        def includes = includeFilter()

        String codeLocation = CodeLocations.codeLocationFromClass(this.class).file
        return new StoryFinder().findClassNames(codeLocation, includes, asList(""))
    }


    List<String> includeFilter() {
        ['**/use_case_arts_nieuw/LV.1.MB_Maak_BRP_Bericht/*.story']
    }
}
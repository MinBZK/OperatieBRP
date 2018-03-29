/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.brp.acceptance.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.funqmachine.AcceptanceTest;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
public class FuchungStoriesTest extends AbstractSpringJBehaveStories {
    @Override
    protected List<String> alleStoryPaths() {
        List<String> includes = includeFilter();

        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, includes, Arrays.asList(""));
    }

    public List<String> includeFilter() {
//       ['**/use_case_arts_nieuw/**/**/2.Archiveer_bericht_lvg_syn.story']
        //['**/testcases/**/**/VHNL01C130T0.story',
        //'**/testcases/**/**/VHNL01C20T10.story',
        //'**/testcases/**/**/VHNL01C20T20.story',
        //'**/testcases/**/**/VHNL01C20T30.story',
        return new ArrayList<String>(
                Arrays.asList("**/testcases/**/**/AGNL02C40T20.story", "**/testcases/**/**/AGNL02C40T10.story", "**/testcases/**/**/AGNL02C30T20.story",
                        "**/testcases/**/**/AGNL02C30T10.story", "**/testcases/**/**/AGNL02C20T10.story", "**/testcases/**/**/AGNL02C10T10.story"));


    }

}

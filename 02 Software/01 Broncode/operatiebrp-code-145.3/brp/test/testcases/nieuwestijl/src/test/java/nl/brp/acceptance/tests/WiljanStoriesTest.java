/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.brp.acceptance.tests;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.funqmachine.AcceptanceTest;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.junit.BeforeClass;

/**
 * Basic test for running JBehave stories.
 */
@AcceptanceTest
public class WiljanStoriesTest extends AbstractSpringJBehaveStories {
    @BeforeClass
    public static void setup() {
        //AbstractSpringJBehaveStories.bewaarVorigeOutput();
    }

    @Override
    protected List<String> alleStoryPaths() {
        List<String> includes = includeFilter();

        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, includes, Collections.singletonList(""));
    }

    public List<String> includeFilter() {
//       ['**/use_case_arts_nieuw/**/**/2.Archiveer_bericht_lvg_syn.story']
        //['**/testcases/**/**/VHNL01C130T0.story',
        //'**/testcases/**/**/VHNL01C20T10.story',
        //'**/testcases/**/**/VHNL01C20T20.story',
        //'**/testcases/**/**/VHNL01C20T30.story',
        //'**/testcases/**/**/VHNL01C20T40.story',
        //'**/testcases/**/**/VHNL01C270T10.story',
        //'**/testcases/**/**/VHNL01C30T20.story',
        //'**/testcases/**/**/VHNL06C20T20.story',
        // '**/testcases/**/**/VHNL06C20T10.story',
        //['**/testcases/**/**/AUAH01C80T10.story']
        //  ['**/testcases/**/**/GVGB04C10T10.story']
        return Arrays.asList("**/testcases/**/**/VHNL03C120T10.story");
    }
}

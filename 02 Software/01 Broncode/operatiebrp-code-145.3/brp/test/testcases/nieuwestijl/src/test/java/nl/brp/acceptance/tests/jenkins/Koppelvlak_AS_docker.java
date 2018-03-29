/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.brp.acceptance.tests.jenkins;

import java.util.ArrayList;
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
public class Koppelvlak_AS_docker extends AbstractSpringJBehaveStories {
    @BeforeClass
    public static void setup() {
//        System.setProperty("omgeving","localhost-dok")
        System.setProperty("omgeving", "test-bij-as-dok");
    }

    @Override
    protected List<String> alleStoryPaths() {
        List<String> includes = includeFilter();

        String codeLocation = CodeLocations.codeLocationFromClass(this.getClass()).getFile();
        return new StoryFinder().findClassNames(codeLocation, includes, Collections.singletonList(""));
    }

    public List<String> includeFilter() {
        return new ArrayList<String>(Collections.singletonList("**/testcases/bijhouding_AS/**/*.story"));
    }

    public void dummy() {
        new ArrayList();
    }

    @Override
    protected List<String> metaFilters() {
        return new ArrayList<String>(
                Collections.singletonList("groovy: status == 'Klaar' && !(sleutelwoorden ==~ /.*(Fout|Logging|join kern met ber query).*/)"));
    }

}

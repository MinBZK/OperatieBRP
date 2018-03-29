/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.brp.acceptance.tests.jenkins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.funqmachine.AcceptanceTest;
import nl.bzk.brp.funqmachine.jbehave.stories.AbstractSpringJBehaveStories;
import org.junit.BeforeClass;

/**
 * Regressie tests voor Bijhouding Aangaan geregistreerd Partnerschap Buitenland.
 */
@AcceptanceTest
public class BijhoudingBLOBTest extends AbstractVoerKlaarStoriesUit {
    @BeforeClass
    public static void setup() {
        AbstractSpringJBehaveStories.maakBlobs();
    }

    @Override
    public List<String> includeFilter() {
        return new ArrayList<String>(
                Arrays.asList("**/testcases/bijhouding/**/**/*04*", "**/testcases/bijhouding_VA/**/*04*", "**/testcases/bijhouding_AS/**/*04*",
                        "**/testcases/bijhouding/**/**/VHNL05C10T10*", "**/testcases/bijhouding/**/**/VHNL01C360T40*",
                        "**/testcases/bijhouding/**/**/VHNL02C780T20*", "**/testcases/bijhouding/**/**/VHNL02C780T30*",
                        "**/testcases/bijhouding/**/**/VHNL06C10T10*", "**/testcases/bijhouding/**/**/EGNL01C70T30*",
                        "**/testcases/bijhouding/**/**/OMGP02C10T10*", "**/testcases/bijhouding/**/**/AGBL02C10T40*",
                        "**/testcases/bijhouding/**/**/AGNL01C800T10*", "**/testcases/bijhouding/**/EGBL02C10T10*", "**/testcases/bijhouding/**/EGBL02C10T20*",
                        "**/testcases/bijhouding/**/**/NGNL04C10T50*", "**/testcases/bijhouding/**/**/NHNL02C10T50*",
                        "**/testcases/bijhouding/**/**/OHBL02C10T10*", "**/testcases/bijhouding_VA/VZIG/VZIG01C30T80*",
                        "**/testcases/bijhouding_VA/WBVP/WBVP02C10T30*", "**/testcases/bijhouding_VA/WBVP/WBVP02C10T20*",
                        "**/testcases/bijhouding/VHNL/VHNL01C360T40*", "**/testcases/bijhouding/VHNL/VZIG04C20T20*", "**/testcases/overig/PERS/*",
                        "**/testcases/overig/TEAM-ROOD/*"));
    }

}

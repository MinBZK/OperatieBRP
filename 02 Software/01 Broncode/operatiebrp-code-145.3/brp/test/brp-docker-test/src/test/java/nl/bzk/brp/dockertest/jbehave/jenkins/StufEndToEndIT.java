/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.STUF;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/SVGS_Stuf/*.story"
)
@Dockertest(
        naam = "stuf",
        componenten = {STUF},
        autorisaties = {
                "/levering_autorisaties/Stuf/StandaardStufAutorisatie",
                "/levering_autorisaties/Stuf/R2258_FALSE_STUF"
        }
)
public class StufEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(STUF);
    }
}

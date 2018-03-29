/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE_SCHRIJVER;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE_VERWERKER;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

/**
 * SelectieEndToEndIT.
 */
@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/SL_Selectie/*.story"
)
@Dockertest(
        naam = "selectie",
        componenten = {SELECTIE, SELECTIE_SCHRIJVER, SELECTIE_VERWERKER},
        autorisaties = {
                "/levering_autorisaties/Selectie", "/levering_autorisaties/SelectieMetFouteDienstSoort"
        }
)
public class SelectieEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(SELECTIE);
    }
}

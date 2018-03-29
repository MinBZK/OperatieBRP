/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.BEVRAGING;
import static nl.bzk.brp.dockertest.component.DockerNaam.ONDERHOUDAFNEMERINDICATIES;
import static nl.bzk.brp.dockertest.component.DockerNaam.SYNCHRONISATIE;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/LV1CP_Controleer_Persoonsselectie/*.story"
)
@Dockertest(
        naam = "persoonselectie",
        componenten = {SYNCHRONISATIE, BEVRAGING, ONDERHOUDAFNEMERINDICATIES},
        autorisaties = {
                "/levering_autorisaties/GeefDetailsPersoon_PopulatieBeperking"
        }
)
public class ControleerPersoonSelectieEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BEVRAGING);
    }

}
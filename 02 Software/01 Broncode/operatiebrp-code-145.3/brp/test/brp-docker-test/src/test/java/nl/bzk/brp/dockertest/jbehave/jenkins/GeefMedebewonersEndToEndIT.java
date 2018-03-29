/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.BEVRAGING;
import static nl.bzk.brp.dockertest.component.DockerNaam.BIJHOUDING;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/BV0GM_Geef_Medebewoners/*.story"
)
@Dockertest(
        naam = "geefmedebewoners",
        componenten = {//zonde...voor 1 scenario
                BIJHOUDING,
                BEVRAGING, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "/levering_autorisaties/GeefMedebewoners/GeefMedebewoners",
                "/levering_autorisaties/R2398/Geef_Medebewoners_Verstrekkingsbeperking",
                "/levering_autorisaties/R2399/GeefMedebewoners"
        }
)
public class GeefMedebewonersEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BEVRAGING);
    }


}

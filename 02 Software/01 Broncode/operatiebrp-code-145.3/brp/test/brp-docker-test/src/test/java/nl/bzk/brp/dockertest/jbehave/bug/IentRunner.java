/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.bug;

import static nl.bzk.brp.dockertest.component.DockerNaam.BRPDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIEBLOB_DATABASE;

import nl.bzk.brp.dockertest.component.DockerMountingInfo;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

/**
 * Runner voor reproduceren van IENT bugs met databasedump.
 */
@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/IENT/*.story"
)
@Dockertest(
        naam = "-e2e-ientrunner",
        componenten = {BRPDB, SELECTIEBLOB_DATABASE},
        mount = @DockerMountingInfo(hostDir = "src/test/resources/testcases/IENT/databasedump", containerDir = "/dump")
)
public class IentRunner extends EndToEndTestSetup {

}

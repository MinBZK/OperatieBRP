/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.paralleltest;

import static nl.bzk.brp.dockertest.component.DockerNaam.BRPDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIEBLOB_DATABASE;

import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/Bug/ptest8.story"
)
@Dockertest(
        naam = "parallel8",
        componenten = {BRPDB, SELECTIEBLOB_DATABASE}
)
public class PTest8 extends EndToEndTestSetup {
}

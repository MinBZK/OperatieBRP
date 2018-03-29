/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.AFNEMERVOORBEELD;
import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.VERZENDING;
import static nl.bzk.brp.dockertest.component.DockerNaam.VRIJBERICHT;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/VB0AV_Vrij_Bericht/**/*.story"
)
@Dockertest(
        naam = "vrijbericht",
        componenten = {AFNEMERVOORBEELD, VERZENDING, VRIJBERICHT, ARCHIVERINGDB}
)
public class VrijBerichtEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(VRIJBERICHT);
    }
}

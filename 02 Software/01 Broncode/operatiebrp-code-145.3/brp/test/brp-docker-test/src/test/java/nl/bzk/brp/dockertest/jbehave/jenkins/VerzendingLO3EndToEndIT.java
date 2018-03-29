/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.AFNEMERVOORBEELD;
import static nl.bzk.brp.dockertest.component.DockerNaam.AH_PUBLICATIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.BIJHOUDING;
import static nl.bzk.brp.dockertest.component.DockerNaam.MUTATIELEVERING;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.VERZENDING;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/AL1VZ_Verzenden_LO3/**/*.story"
)
@Dockertest(
        naam = "verzending-lo3",
        componenten = {AFNEMERVOORBEELD, BIJHOUDING, VERZENDING, MUTATIELEVERING
                , AH_PUBLICATIE, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "levering_autorisaties/gba_geen_pop_bep_levering_op_basis_van_doelbinding"
        }
)
public class VerzendingLO3EndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BIJHOUDING);
    }
}

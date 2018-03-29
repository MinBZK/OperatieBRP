/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.AFNEMERVOORBEELD;
import static nl.bzk.brp.dockertest.component.DockerNaam.AH_PUBLICATIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.BEVRAGING;
import static nl.bzk.brp.dockertest.component.DockerNaam.BIJHOUDING;
import static nl.bzk.brp.dockertest.component.DockerNaam.MUTATIELEVERING;
import static nl.bzk.brp.dockertest.component.DockerNaam.ONDERHOUDAFNEMERINDICATIES;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.SYNCHRONISATIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.VERZENDING;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/Overige_testen/*.story"
)
@Dockertest(
        naam = "overig",
        componenten = {BEVRAGING, AFNEMERVOORBEELD, VERZENDING, SYNCHRONISATIE,
                AH_PUBLICATIE, ONDERHOUDAFNEMERINDICATIES, BIJHOUDING,
                MUTATIELEVERING, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "/levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie"
        }
)
public class OverigeTestenEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BIJHOUDING);
    }
}

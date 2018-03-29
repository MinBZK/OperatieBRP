/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.AFNEMERVOORBEELD;
import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.ONDERHOUDAFNEMERINDICATIES;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.SYNCHRONISATIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.VERZENDING;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(searchIn = "src/test/resources",
        includes = {"testcases/SA1SP_Synchroniseer_Persoon/*.story",
                "testcases/SA1SS_Synchroniseer_Stamgegeven/*.story",
        }
)
@Dockertest(
        naam = "synchronisatie",
        componenten = {AFNEMERVOORBEELD, ONDERHOUDAFNEMERINDICATIES, VERZENDING, SYNCHRONISATIE, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "/levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding",
                "/levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding_Gemeente_Ongeldig",
                "/levering_autorisaties/synchronisatie_stamgegeven",
                "/levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding_geenafleverpunt",
                "/levering_autorisaties/Geen_pop.bep_levering_op_basis_van_afnemerindicatie",
                "/levering_autorisaties/Bewerker_autorisatie_Partijrol_Bijhoudingsorgaan_Minister",
                "/levering_autorisaties/R2258/R2258_NULL",
                "/levering_autorisaties/R2258/R2258_FALSE_NULL",
                "/levering_autorisaties/R2258/R2258_FALSE",
        }
)
public class SynchronisatieEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(SYNCHRONISATIE);
    }
}

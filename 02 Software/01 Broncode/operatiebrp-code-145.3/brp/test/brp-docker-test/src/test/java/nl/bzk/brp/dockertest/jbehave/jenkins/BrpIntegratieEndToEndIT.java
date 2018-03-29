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
        includes = "testcases/BRP_INTEGRATIE_TEST/IT_VHNL/*.story"
)
@Dockertest(
        naam = "brpintegratie",
        componenten = {AFNEMERVOORBEELD,
                VERZENDING, MUTATIELEVERING,
                AH_PUBLICATIE, BIJHOUDING, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "/levering_autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding",
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_beeindigd_in_het_verleden",
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_indnaderepopbeperkingvolconv_FALSE",
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_zonder_dienstbundel",
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_zonder_groepen",
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_zonder_toegang",
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_zonder_diensten",
                "/levering_autorisaties/PDT/Attendering_Mutatielevering_afnemerindicatie",
                "/levering_autorisaties/PDT/Attendering_afnemerindicatie_250602",
                "/levering_autorisaties/PDT/Attendering_afnemerindicatie_610401",
                "/levering_autorisaties/PDT/Bevraging_607102",
                "/levering_autorisaties/PDT/Mutatielevering_afnemerindicatie_201301",
                "/levering_autorisaties/PDT/Mutatielevering_afnemerindicatie_250001",
                "/levering_autorisaties/PDT/Mutatielevering_afnemerindicatie_613603",
                "/levering_autorisaties/PDT/Mutatielevering_afnemerindicatie_700301"
        }
)
public class BrpIntegratieEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(AFNEMERVOORBEELD);
    }

}

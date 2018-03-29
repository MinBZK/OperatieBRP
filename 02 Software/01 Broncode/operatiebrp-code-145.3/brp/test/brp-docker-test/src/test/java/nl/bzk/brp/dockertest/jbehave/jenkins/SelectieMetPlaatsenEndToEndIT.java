/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.AFNEMERVOORBEELD;
import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.BIJHOUDING;
import static nl.bzk.brp.dockertest.component.DockerNaam.ONDERHOUDAFNEMERINDICATIES;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE_AFNEMERINDICATIE;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE_SCHRIJVER;
import static nl.bzk.brp.dockertest.component.DockerNaam.SELECTIE_VERWERKER;
import static nl.bzk.brp.dockertest.component.DockerNaam.VERZENDING;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

/**
 * SelectieMetPlaatsenEndToEndIT.
 */
@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/SL0PA_Selectiedienst_met_plaatsing_afnemerindicatie/R1539_Selectie_naderebijhoudingsaard.story"
)
@Dockertest(
        naam = "selectie-plaatsen",
        componenten = {SELECTIE, SELECTIE_SCHRIJVER, SELECTIE_VERWERKER, SELECTIE_AFNEMERINDICATIE,
                VERZENDING, AFNEMERVOORBEELD, ONDERHOUDAFNEMERINDICATIES, ARCHIVERINGDB, PROTOCOLLERINGDB, BIJHOUDING},
        autorisaties = {
                "/levering_autorisaties/SelectieMetPlaatsingEnVolledigBericht",
                "/levering_autorisaties/SelectieMetPlaatsenAfnemerindicatie",
                "/levering_autorisaties/SelectieMetPlaatsenAfnemerindicatieModelAut",
                "/levering_autorisaties/SelectieMetMeerdereToegangLevAut"
        }
)
public class SelectieMetPlaatsenEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(SELECTIE);
    }

}

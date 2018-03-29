/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

import static nl.bzk.brp.dockertest.component.DockerNaam.BEHEER_SELECTIE_FRONTEND;
import static nl.bzk.brp.dockertest.component.DockerNaam.BRPDB;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/**/Raadplegen_details_selectietaak.story"
)
@Dockertest(
        naam = "beheer-selectie",
        componenten = {BRPDB, BEHEER_SELECTIE_FRONTEND},
        autorisaties = {
                "/levering_autorisaties/Beheer_Selecties/BeheerSelectieEenmalig"
        }
)
public class BeheerSelectieTakenDetailsEndToEndIT extends EndToEndTestSetup {

    static {
//        Environment.setTeDebuggenComponent(BEHEER_SELECTIE);
    }


}

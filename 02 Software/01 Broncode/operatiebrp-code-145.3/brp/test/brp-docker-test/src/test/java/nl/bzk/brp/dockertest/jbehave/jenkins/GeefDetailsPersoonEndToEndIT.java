/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.BEVRAGING;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/BV0GD_Geef_Details_Persoon/*.story"
)
@Dockertest(
        naam = "geefdetailpers",
        componenten = {BEVRAGING},
        autorisaties = {
                "/levering_autorisaties/ongeldige_autorisaties/Autorisatie_indnaderepopbeperkingvolconv_FALSE",
                "/levering_autorisaties/GeefDetailsPersoon/abo_geef_details_persoon_OndertekenaarTransporteur",
                "/levering_autorisaties/GeefDetailsPersoon/abo_geef_details_persoon",
                "levering_autorisaties/Bewerker_autorisatie_Partijrol_Bijhoudingsorgaan_Minister",
                "levering_autorisaties/GeefDetailsPersoon/GeefDetailsPersoon_AfnemerIndicaties_Bijhouder",
                "levering_autorisaties/Bewerker_autorisatie",
                "levering_autorisaties/Bewerker_autorisatie_protniveau2",
                "/levering_autorisaties/R2258/R2258_NULL",
                "/levering_autorisaties/R2258/R2258_FALSE_NULL",
                "/levering_autorisaties/R2258/R2258_FALSE",
        }
)
public class GeefDetailsPersoonEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BEVRAGING);
    }


}

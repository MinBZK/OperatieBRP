/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.BEVRAGING;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;


@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/BV0ZA_Zoek_persoon_op_adresgegevens/*.story"
)
@Dockertest(
        naam = "zoekpersoonadres",
        componenten = { BEVRAGING, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "/levering_autorisaties/ZoekPersoonAdres/ZoekPersoonOpAdres",
                "/levering_autorisaties/ZoekPersoonAdres/Zoek_Persoon_totale_populatiebeperking_GEVULD",
                "/levering_autorisaties/R2376/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX1",
                "/levering_autorisaties/R2376/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX2",
                "/levering_autorisaties/R2376/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX4",
                "/levering_autorisaties/ZoekPersoonAdres/Zoek_Persoon_Adres_MAX_resultaten",
                "/levering_autorisaties/R2398/Zoek_Persoon_Adres_Verstrekkingsbeperking"
        }
)
public class ZoekPersoonOpAdresgegevensEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BEVRAGING);
    }
}

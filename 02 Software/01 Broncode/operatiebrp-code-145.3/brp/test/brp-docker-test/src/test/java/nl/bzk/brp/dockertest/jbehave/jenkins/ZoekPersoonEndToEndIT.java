/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave.jenkins;

import static nl.bzk.brp.dockertest.component.DockerNaam.ARCHIVERINGDB;
import static nl.bzk.brp.dockertest.component.DockerNaam.BEVRAGING;
import static nl.bzk.brp.dockertest.component.DockerNaam.BIJHOUDING;
import static nl.bzk.brp.dockertest.component.DockerNaam.PROTOCOLLERINGDB;

import nl.bzk.brp.dockertest.component.Environment;
import nl.bzk.brp.dockertest.jbehave.Dockertest;
import nl.bzk.brp.dockertest.jbehave.EndToEndTestSetup;
import org.jbehave.core.annotations.UsingPaths;

@UsingPaths(
        searchIn = "src/test/resources",
        includes = "testcases/BV1ZP_ZoekPersoon/**/*.story"
)
@Dockertest(
        naam = "zoekpersoon",
        componenten = {                    //zonde...voor 1 scenario
                BIJHOUDING, BEVRAGING, ARCHIVERINGDB, PROTOCOLLERINGDB},
        autorisaties = {
                "/levering_autorisaties/Zoek_Persoon",
                "/levering_autorisaties/Zoek_Persoon3",
                "/levering_autorisaties/Zoek_Persoon4",
                "/levering_autorisaties/R2287/Zoek_Persoon_MAX_resultaten",
                "/levering_autorisaties/ZoekPersoon/ZoekPersoon",
                "/levering_autorisaties/R2398/Zoek_Persoon_Verstrekkingsbeperking",
                "/levering_autorisaties/R2287/Zoek_Persoon_dienstbundel_nad_pop_voldoet_niet",
                "/levering_autorisaties/R2287/Zoek_Persoon_toegang_leveringsautorisatie_nad_pop_voldoet_niet",
                "/levering_autorisaties/R2287/Zoek_Persoon_leveringsautorisatie_pop_bep_voldoet_niet",
                "/levering_autorisaties/R2287/Zoek_Persoon_totale_populatiebeperking_LEEG",
                "/levering_autorisaties/R2287/Zoek_Persoon_totale_populatiebeperking_GEVULD",
                "/levering_autorisaties/R2287/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX",
                "/levering_autorisaties/ZoekPersoon/Zoek_Persoon_huisnummer_niet_geautoriseerd",
                "/levering_autorisaties/R2297/Zoek_Persoon_Geen_Materiele_historie",
                "/levering_autorisaties/GeefDetailsPersoon/abo_geef_details_persoon",
                "/levering_autorisaties/ZoekPersoon/Zoek_Persoon_bijhouder",
                "/levering_autorisaties/R2376/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX1",
                "/levering_autorisaties/R2376/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX2",
                "/levering_autorisaties/R2376/Zoek_Persoon_totale_populatiebeperking_GEVULD_MAX4",
                "/levering_autorisaties/R2398/Geef_Medebewoners_Verstrekkingsbeperking",
                "/levering_autorisaties/R2398/Zoek_Persoon_Adres_Verstrekkingsbeperking",
        }
)
public class ZoekPersoonEndToEndIT extends EndToEndTestSetup {

    static {
        Environment.setTeDebuggenComponent(BEVRAGING);
    }
}

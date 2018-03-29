/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.leveringalgemeen;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.tooling.apitest.dto.OntvangenBericht;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * Service interface voor de asynchroon ontvangen berichten.
 */
public interface LeverberichtStubService extends Stateful {

    /**
     * Assert dat een bericht is ontvangen voor de gegeven autorisatie en soortSynchronisatie.
     *
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     * @param soortSynchronisatie      het soort synchronisatie
     */
    void assertBerichtIsOntvangen(String leveringsautorisatieNaam, final SoortSynchronisatie soortSynchronisatie);

    /**
     * Assert dat een bericht is ontvangen voor de gegeven autorisatie en soortSynchronisatie.
     *
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     * @param soortSynchronisatie      het soort synchronisatie
     * @param afnemerPartijCode        code van de ontvangende partij
     */
    void assertBerichtIsOntvangen(String leveringsautorisatieNaam, SoortSynchronisatie soortSynchronisatie, String afnemerPartijCode);

    /**
     * @return het laatst bekeken bericht
     */
    String getLaatstBekekenBericht();

    /**
     * @return alle ontvangen berichten
     */
    List<OntvangenBericht> getOntvangenBerichten();

    /**
     * Assert dat het aantal ontvangen berichten gelijk is aan het opgegeven aantal.
     *
     * @param count het aantal verwachtte berichten
     */
    void assertAantalOntvangenBerichten(int count);

    /**
     * Assert dat het er geen berichten zijn ontvangen voor de gegeven leveringsautorisatie.
     *
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     */
    void assertGeenLevering(String leveringsautorisatieNaam);
}

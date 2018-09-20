package nl.bzk.brp.funqmachine.mapper

import org.junit.Test

class BerichtenMapperTest {
    @Test
    void testGeefBerichtVoorVerkrijgingVreemdeNationaliteit() {
        final String soortBericht = BerichtenMapper.geefBerichtVoorSoortVerzoek('verkrijgingVreemdeNationaliteit')
        assert soortBericht == 'bhg_natRegistreerNationaliteit'
    }

    @Test(expected = IllegalArgumentException)
    void testGeefBerichtVoorOnjuisteAdministratieveHandeling() {
        BerichtenMapper.geefBerichtVoorSoortVerzoek('blablabla')
    }

    @Test(expected = IllegalArgumentException)
    void testGeefBerichtVoorAmbiguVerzoek() {
        BerichtenMapper.geefBerichtVoorSoortVerzoek('zoekcriteriaPersoon')
    }

    @Test
    void testGeefNamespace() {
        def namespace = BerichtenMapper.geefNamespaceVoorVerzoek('verkrijgingVreemdeNationaliteit')
        assert namespace == 'http://www.bzk.nl/brp/bijhouding/service'
    }

    @Test
    void testGeefEndpoint() {
        def endpoint = BerichtenMapper.geefEndpointVoorVerzoek('verkrijgingVreemdeNationaliteit')
        assert endpoint == '${applications.host}/bijhouding/BijhoudingService/bhgNationaliteit'
    }

    /**
     * Beveiliging tegen lukraak toevoegen van berichten / handelingen aan de configuratie.
     */
    @Test
    void valideerAantalBekendeBerichten() {
        assert BerichtenMapper.berichtHandelingMap.size() == 51
        assert BerichtenMapper.berichtHandelingMap.keySet().size() == 22
    }
}

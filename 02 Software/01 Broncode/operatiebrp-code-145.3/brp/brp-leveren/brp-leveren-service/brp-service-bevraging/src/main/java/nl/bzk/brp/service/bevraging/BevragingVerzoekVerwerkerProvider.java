/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.algemeen.GeneriekeBevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.detailspersoon.GeefDetailsPersoonVerzoek;
import nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVerzoek;
import nl.bzk.brp.service.bevraging.zoekpersoonopadres.ZoekPersoonOpAdresVerzoek;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provider van {@link BevragingVerzoekVerwerker}s per {@link SoortDienst}.
 *
 * NB. Spring heeft om een generiek object te wiren een uniek object nodig, vandaar de anonieme
 * implementaties.
 */
@Configuration
class BevragingVerzoekVerwerkerProvider {

    /**
     * @return verwerkerMap
     */
    @Bean
    public Map<SoortDienst, BevragingVerzoekVerwerker> bevragingVerzoekVerwerkerMap() {
        final Map<SoortDienst, BevragingVerzoekVerwerker> verwerkerMap = Maps.newHashMap();
        verwerkerMap.put(SoortDienst.GEEF_DETAILS_PERSOON, geefDetailsPersoonVerzoekVerwerker());
        verwerkerMap.put(SoortDienst.ZOEK_PERSOON, zoekPersoonVerzoekVerwerker());
        verwerkerMap.put(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS, zoekPersoonOpAdresVerzoekVerwerker());
        verwerkerMap.put(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON, geefMebewonersVerzoekVerwerker());
        return verwerkerMap;
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor Geef Details Persoon.
     * @return de geef details persoon verzoek verwerker
     */
    @Bean
    @Named("geefDetailsPersoonVerwerker")
    public BevragingVerzoekVerwerker<GeefDetailsPersoonVerzoek> geefDetailsPersoonVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<GeefDetailsPersoonVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor Zoek Persoon.
     * @return de zoek persoon verzoek verwerker
     */
    @Bean
    @Named("zoekPersoon")
    public BevragingVerzoekVerwerker<ZoekPersoonVerzoek> zoekPersoonVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<ZoekPersoonVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor Zoek Persoon Op Adres.
     * @return de zoek persoon op adres verzoek verwerker
     */
    @Bean
    @Named("zoekPersoonOpAdres")
    public BevragingVerzoekVerwerker<ZoekPersoonOpAdresVerzoek> zoekPersoonOpAdresVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<ZoekPersoonOpAdresVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor Geef Medebewoners.
     * @return de geef medebewoners verzoek verwerker
     */
    @Bean
    @Named("geefMedebewoners")
    public BevragingVerzoekVerwerker<GeefMedebewonersVerzoek> geefMebewonersVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<GeefMedebewonersVerzoek, BevragingResultaat>() {
        };
    }
}

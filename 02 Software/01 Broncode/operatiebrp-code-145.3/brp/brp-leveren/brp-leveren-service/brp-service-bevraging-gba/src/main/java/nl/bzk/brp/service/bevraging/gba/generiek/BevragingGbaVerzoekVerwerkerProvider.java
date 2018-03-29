/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.algemeen.GeneriekeBevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.gba.adres.AdresvraagVerzoek;
import nl.bzk.brp.service.bevraging.gba.adres.AdresvraagWebserviceVerzoek;
import nl.bzk.brp.service.bevraging.gba.persoon.OngeprotocolleerdPersoonsvraagVerzoek;
import nl.bzk.brp.service.bevraging.gba.persoon.OpvragenPLWebserviceVerzoek;
import nl.bzk.brp.service.bevraging.gba.persoon.PersoonsvraagVerzoek;
import nl.bzk.brp.service.bevraging.gba.persoon.PersoonsvraagWebserviceVerzoek;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provider van {@link BevragingVerzoekVerwerker}s per {@link SoortDienst}.
 *
 * NB. Spring heeft om een generiek object te wiren een uniek object nodig, vandaar de anonieme
 * implementaties.
 */
@Configuration
class BevragingGbaVerzoekVerwerkerProvider {

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor GBA Opvragen PL.
     * @return de GBA Opvragen PL
     */
    @Bean
    @Named("gbaOpvragenPLVerwerker")
    public BevragingVerzoekVerwerker<OpvragenPLWebserviceVerzoek> opvragenPLVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<OpvragenPLWebserviceVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor GBA Ad hoc (Persoons)vraag.
     * @return de GBA Ad hoc (Persoons)vraag verwerker
     */
    @Bean
    @Named("gbaPersoonsvraagVerwerker")
    public BevragingVerzoekVerwerker<PersoonsvraagVerzoek> persoonsvraagVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<PersoonsvraagVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor GBA Ad hoc webservice(Persoons)vraag.
     * @return de GBA Ad hoc webservice (Persoons)vraag verwerker
     */
    @Bean
    @Named("gbaPersoonsvraagWebserviceVerwerker")
    public BevragingVerzoekVerwerker<PersoonsvraagWebserviceVerzoek> persoonsvraagWebserviceVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<PersoonsvraagWebserviceVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor GBA Ad hoc webservice(Persoons)vraag.
     * @return de GBA Ad hoc webservice (Adres)vraag verwerker
     */
    @Bean
    @Named("gbaAdresvraagWebserviceVerwerker")
    public BevragingVerzoekVerwerker<AdresvraagWebserviceVerzoek> adresvraagWebserviceVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<AdresvraagWebserviceVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor GBA Ad hoc (Persoons)vraag.
     * @return de GBA Ad hoc (Persoons)vraag verwerker
     */
    @Bean
    @Named("gbaOngeprotocolleerdPersoonsvraagVerwerker")
    public BevragingVerzoekVerwerker<OngeprotocolleerdPersoonsvraagVerzoek> ongeprotocolleerdPersoonsvraagVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<OngeprotocolleerdPersoonsvraagVerzoek, BevragingResultaat>() {
        };
    }

    /**
     * {@link BevragingVerzoekVerwerker} specifiek voor GBA Ad hoc (Adres)vraag.
     * @return de GBA Ad hoc (Adres)vraag verwerker
     */
    @Bean
    @Named("gbaAdresvraagVerwerker")
    public BevragingVerzoekVerwerker<AdresvraagVerzoek> adresvraagVerzoekVerwerker() {
        return new GeneriekeBevragingVerzoekVerwerker<AdresvraagVerzoek, BevragingResultaat>() {
        };
    }
}

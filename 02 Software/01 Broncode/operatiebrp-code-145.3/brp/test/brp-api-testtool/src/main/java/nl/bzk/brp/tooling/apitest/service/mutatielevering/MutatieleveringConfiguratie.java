/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.mutatielevering;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.AdhocWebserviceAntwoord;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.bericht.OpvragenPLWebserviceAntwoord;
import nl.bzk.brp.levering.lo3.bericht.Xa01Bericht;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

/**
 * Bevat de Spring configuratie voor de mutatielevering services.
 */
@ComponentScan("nl.bzk.brp.service.mutatielevering")
public class MutatieleveringConfiguratie {

    @Bean
    @SuppressWarnings("all")
    public static PropertyPlaceholderConfigurer ppc() throws IOException {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("test.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    @Bean
    @SuppressWarnings("all")
    MutatieleveringApiServiceImpl maakMutatieleveringApiService() {
        return new MutatieleveringApiServiceImpl();
    }

    @Bean
    @SuppressWarnings("all")
    DummyBerichtFactoryStub maakBerichtFactory() {
        return new DummyBerichtFactoryStub();
    }

    @Bean
    @SuppressWarnings("all")
    DummyLo3FilterRubriekRepositoryStub maakLo3FilterRubriekRepository() {
        return new DummyLo3FilterRubriekRepositoryStub();
    }

    private static final class DummyLo3FilterRubriekRepositoryStub implements Lo3FilterRubriekRepository {
        @Override
        public List<String> haalLo3FilterRubriekenVoorDienstbundel(final Integer dienstbundelId) {
            return null;
        }
    }

    private static final class DummyBerichtFactoryStub implements BerichtFactory {
        @Override
        public List<Bericht> maakBerichten(final Autorisatiebundel leveringAutorisatie, final Map<Persoonslijst, Populatie> populatie,
                                           final AdministratieveHandeling administratieveHandeling, final IdentificatienummerMutatie
                                                   identificatienummerMutatieResultaat) {
            return Collections.emptyList();
        }

        @Override
        public Bericht maakAg01Bericht(final Persoonslijst persoon) {
            return null;
        }

        @Override
        public Bericht maakAg11Bericht(final Persoonslijst persoon) {
            return null;
        }

        @Override
        public Bericht maakHa01Bericht(final Persoonslijst persoon) {
            return null;
        }

        @Override
        public Bericht maakSf01Bericht(final Persoonslijst persoon) {
            return null;
        }

        @Override
        public Bericht maakSv01Bericht(final Persoonslijst persoon) {
            return null;
        }

        @Override
        public Bericht maakSv11Bericht() {
            return null;
        }

        @Override
        public Xa01Bericht maakXa01Bericht(final List<Persoonslijst> personen) {
            return null;
        }

        @Override
        public AdhocWebserviceAntwoord maakAdhocAntwoord(final List<Persoonslijst> personen) {
            return null;
        }

        @Override
        public OpvragenPLWebserviceAntwoord maakVraagPLAntwoord(final List<Persoonslijst> personen) {
            return null;
        }
    }
}

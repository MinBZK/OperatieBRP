/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest;

import nl.bzk.brp.tooling.apitest.service.afnemerindicatie.OnderhoudAfnemerindicatieConfiguratie;
import nl.bzk.brp.tooling.apitest.service.basis.ApiService;
import nl.bzk.brp.tooling.apitest.service.basis.impl.BasisConfiguratie;
import nl.bzk.brp.tooling.apitest.service.bevraging.BevragingConfiguratie;
import nl.bzk.brp.tooling.apitest.service.mutatielevering.MutatieleveringConfiguratie;
import nl.bzk.brp.tooling.apitest.service.selectie.SelectieConfiguratie;
import nl.bzk.brp.tooling.apitest.service.synchronisatie.SynchronisatieConfiguratie;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Verifieert of de configuraties werken
 */
public class ConfiguratieTest {

    @Test
    public void testBasisConfiguratie() {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
    }

    @Test
    public void testMutatieleveringConfiguratie() {
        final AnnotationConfigApplicationContext basisContext = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
        final AnnotationConfigApplicationContext bevragingContext = new AnnotationConfigApplicationContext();
        bevragingContext.setParent(basisContext);
        bevragingContext.register(MutatieleveringConfiguratie.class);
        bevragingContext.refresh();
    }

    @Test
    public void testSynchronisatieConfiguratie() {
        final AnnotationConfigApplicationContext basisContext = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
        final AnnotationConfigApplicationContext synchronisatieContext = new AnnotationConfigApplicationContext();
        synchronisatieContext.setParent(basisContext);
        synchronisatieContext.register(SynchronisatieConfiguratie.class);
        synchronisatieContext.refresh();
        basisContext.getBean(ApiService.class).getSynchronisatieApiService();

    }

    @Test
    public void testBevragingConfiguratie() {
        final AnnotationConfigApplicationContext basisContext = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
        final AnnotationConfigApplicationContext bevragingContext = new AnnotationConfigApplicationContext();
        bevragingContext.setParent(basisContext);
        bevragingContext.register(BevragingConfiguratie.class);
        bevragingContext.refresh();
        basisContext.getBean(ApiService.class).getBevragingApiService();
    }

    @Test
    public void testOnderhoudAfnemerindicatieConfiguratie() {
        final AnnotationConfigApplicationContext basisContext = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
        final AnnotationConfigApplicationContext bevragingContext = new AnnotationConfigApplicationContext();
        bevragingContext.setParent(basisContext);
        bevragingContext.register(OnderhoudAfnemerindicatieConfiguratie.class);
        bevragingContext.refresh();
        basisContext.getBean(ApiService.class).getOnderhoudAfnemerindicatieService();
    }

    @Test
    public void testSelectieConfiguratie() {
        final AnnotationConfigApplicationContext basisContext = new AnnotationConfigApplicationContext(BasisConfiguratie.class);
        final AnnotationConfigApplicationContext bevragingContext = new AnnotationConfigApplicationContext();
        bevragingContext.setParent(basisContext);
        bevragingContext.register(SelectieConfiguratie.class);
        bevragingContext.refresh();
        basisContext.getBean(ApiService.class).getSelectieApiService();
    }
}

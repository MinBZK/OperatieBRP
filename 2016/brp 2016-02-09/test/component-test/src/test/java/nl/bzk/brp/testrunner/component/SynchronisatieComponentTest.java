/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.ClassPathResource;

@RunWith(JUnit4.class)
public class SynchronisatieComponentTest extends TestCase {



    @Test
    public void testMinimaleOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metDummyRouteringCentrale().metSynchronisatie().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.cache().update();
        omgeving.stop();
    }

    @Test
    public void testSynchroniseerPersoon() throws InterruptedException {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metDummyRouteringCentrale().metSynchronisatie().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.leveringautorisaties().uitBestand("/testdata/abonnement.sql");
        omgeving.persoonDsl().uitBestand("/dsl/persoon1.groovy");
        omgeving.cache().update();
        String response = omgeving.synchronisatie().synchroniseerPersoon();
        System.err.println(response);
        omgeving.stop();
    }

}

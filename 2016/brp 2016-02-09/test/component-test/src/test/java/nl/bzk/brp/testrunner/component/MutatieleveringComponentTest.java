/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.io.IOException;
import java.util.List;
import javax.jms.JMSException;
import junit.framework.TestCase;
import nl.bzk.brp.testrunner.component.util.Afnemerbericht;
import nl.bzk.brp.testrunner.omgeving.OmgevingIncompleetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.ClassPathResource;

@RunWith(JUnit4.class)
public class MutatieleveringComponentTest extends TestCase {

    @Test
    public void testMaakMutatieleveringOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRouteringCentrale().metMutatielevering().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.cache().update();
        omgeving.stop();
    }


    @Test(expected = OmgevingIncompleetException.class)
    public void testIncompleteOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metMutatielevering().maak();
        omgeving.start();
        omgeving.stop();
    }

    @Test
    public void testOntvangen() throws InterruptedException, JMSException, IOException {

        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRouteringCentrale().metMutatielevering().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.leveringautorisaties().uitBestand("/autorisaties/geen_pop_bep_levering_op_basis_van_doelbinding");
        omgeving.persoonDsl().uitBestand("/dsl/persoon1.groovy");
        omgeving.cache().update();
        omgeving.handeling().leverLaatsteHandeling(123434538);

        final List<Afnemerbericht> berichten = omgeving.geefComponent(RouteringCentrale.class).geefAfnemerberichten("AFNEMER-34401");
        assertFalse(berichten.isEmpty());

        omgeving.stop();
    }
}

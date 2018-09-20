/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import nl.bzk.brp.testrunner.omgeving.Omgeving;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RouteringcentraleComponentTest {


    @Test
    public void testMaakRouteringOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().metRouteringCentrale().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.stop();
    }

    @Test
    public void testMaakDummyRouteringOmgeving() throws Exception {
        final BrpOmgeving omgeving = new OmgevingBouwer().metDummyRouteringCentrale().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();
        omgeving.stop();
    }

}

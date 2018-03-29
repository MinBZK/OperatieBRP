/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de technische flows rondom het lq01 bericht.
 */
public class Uc811Lq01Test extends AbstractUc811Test {

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void startProces() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void maximumHerhalingen() {
        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        getBericht(Lq01Bericht.class);

        // Herhalingen
        forceerTimeout(Lq01Bericht.class, "VOISC");

        // Beheerderkeuze: restart
        signalHumanTask("restartAtVragen");

        // Lq01
        controleerBerichten(0, 1, 0);
        getBericht(Lq01Bericht.class);
        signalProcess("timeout");

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.synchronisatievraag.maximumherhalingen");
        signalHumanTask("end");
    }

    @Test
    public void foutiefBericht() {
        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Ongeldig bericht
        final Lo3Bericht ongeldigBericht = maakOnverwachtBericht(lq01Bericht);
        signalVoisc(ongeldigBericht);

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.synchronisatievraag.foutiefbericht");
        signalHumanTask("end");

        // Controleren pf verstuurd.
        controleerBerichten(0, 1, 0);
        getBericht(Pf01Bericht.class);
    }

    @Test
    public void ongeldigBericht() {
        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Ongeldig bericht
        final Lo3Bericht ongeldigBericht = maakOngeldigeSyntaxBericht(lq01Bericht);
        ongeldigBericht.setCorrelationId(lq01Bericht.getMessageId());
        ongeldigBericht.setBronPartijCode(lq01Bericht.getDoelPartijCode());
        ongeldigBericht.setDoelPartijCode(lq01Bericht.getBronPartijCode());
        signalVoisc(ongeldigBericht);

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.synchronisatievraag.ongeldigbericht");
        signalHumanTask("end");

        // Controleren pf verstuurd.
        controleerBerichten(0, 1, 0);
        getBericht(Pf01Bericht.class);
    }

    @Test
    public void fout() {
        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Antwoord met Lf01
        final Lf01Bericht foutBericht = maakLf01Bericht(lq01Bericht, Lf01Bericht.Foutreden.G, null);
        signalVoisc(foutBericht);

        // Beheerderkeuze: end
        signalHumanTask("end");
    }

    @Test
    public void protocolfout() {
        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Antwoord met Pf01
        final Pf01Bericht foutBericht = maakPf01Bericht(lq01Bericht);
        signalVoisc(foutBericht);

        // Beheerderkeuze: end
        signalHumanTask("end");
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc811;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lq01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de technische flows rondom het synchroniseren naar BRP.
 */
public class Uc811SyncNaarBrpTest extends AbstractUc811Test {

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void startProces() throws Exception {
        // Start
        startProcess(maakUc811Bericht("0599", 1231231234L));

        // Lq01 bericht verwacht op kanaal VOISC.
        controleerBerichten(0, 1, 0);
        final Lq01Bericht lq01Bericht = getBericht(Lq01Bericht.class);

        // Maak een La01 bericht en signal het kanaal VOISC.
        signalVoisc(maakLa01Bericht(lq01Bericht, true));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void wachtOpLeveringBericht() {
        controleerBerichten(0, 0, 1);
        SynchroniseerNaarBrpVerzoekBericht syncVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);

        // Nog niet geleverd bericht
        final SyncBericht nogNietGeleverdBericht = maakSynchroniseerNaarBrpAntwoordBericht(syncVerzoek, StatusType.VORIGE_HANDELINGEN_NIET_GELEVERD, null);
        signalSync(nogNietGeleverdBericht);

        controleerNode("VI-3. Wachten");
        this.signalProcess("timeout");

        controleerBerichten(0, 0, 1);
        syncVerzoek = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);

        // Afbreken
        signalProcess("afbreken");

        // Beheerderskeuze: end
        checkVariabele("foutafhandelingFout", "uc811.syncnaarbrp.afgebroken");
        signalHumanTask("endWithoutPf03");
    }

    @Test
    public void afbreken() {
        controleerBerichten(0, 0, 1);
        getBericht(SynchroniseerNaarBrpVerzoekBericht.class);

        // Afbreken
        signalProcess("afbreken");

        // Beheerderkeuze: restart
        signalHumanTask("restartAtSynchronisatieNaarBrp");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        getBericht(SynchroniseerNaarBrpVerzoekBericht.class);
        signalProcess("afbreken");

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.syncnaarbrp.afgebroken");
        signalHumanTask("endWithoutPf03");
    }

    @Test
    public void foutiefBericht() {
        controleerBerichten(0, 0, 1);
        final SynchroniseerNaarBrpVerzoekBericht synchroniseerNaarBrpVerzoekBericht = getBericht(SynchroniseerNaarBrpVerzoekBericht.class);

        // Ongeldig bericht
        final SyncBericht ongeldigBericht = maakOnverwachtBericht(synchroniseerNaarBrpVerzoekBericht);
        signalSync(ongeldigBericht);

        // Beheerderkeuze: end
        checkVariabele("foutafhandelingFout", "uc811.syncnaarbrp.foutiefbericht");
        signalHumanTask("endWithoutPf03");
    }
}

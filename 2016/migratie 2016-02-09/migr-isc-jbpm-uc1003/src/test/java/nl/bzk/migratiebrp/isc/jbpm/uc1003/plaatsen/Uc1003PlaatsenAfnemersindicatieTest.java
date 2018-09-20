/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AbstractUc1003Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de technische flows rondom het plaatsen afnemersindicatie.
 */
public class Uc1003PlaatsenAfnemersindicatieTest extends AbstractUc1003Test {

    private static final String END = "end";
    private static final String FOUTAFHANDELING_FOUT = "foutafhandelingFout";
    private static final String ACHTERNAAM = "Jansen";
    private static final int BSN = 123456789;
    private static final long A_NUMMER = 1234567890L;
    private static final String AFNEMER = "580001";

    public Uc1003PlaatsenAfnemersindicatieTest() {
        super("/uc1003-plaatsen/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void startProces() {
        // Start
        startProcess(PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, A_NUMMER, BSN, ACHTERNAAM, null));

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 1));
    }

    @After
    public void endProces() {
        controleerBerichten(0, 0, 0);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void afbreken() {
        controleerBerichten(0, 0, 1);
        getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);

        // Afbreken
        signalProcess("afbreken");

        // Beheerderskeuze: restart
        signalHumanTask("restartAtPlaatsen");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);
        signalProcess("afbreken");

        // Beheerderskeuze: end
        checkVariabele(FOUTAFHANDELING_FOUT, "uc1003.plaatsen.afgebroken");
        signalHumanTask(END);

        // Verwacht 1 output bericht (af01) om de ap01/av01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);
    }

    @Test
    public void afbrekenGeenPf03() {
        controleerBerichten(0, 0, 1);
        getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);

        // Afbreken
        signalProcess("afbreken");

        // Beheerderskeuze
        signalHumanTask("endWithoutPf03");
    }

    @Test
    public void foutiefBericht() {
        controleerBerichten(0, 0, 1);
        final PlaatsAfnemersindicatieVerzoekBericht plaatsenAfnIndVerzoek = getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);

        // Onverwacht bericht
        final SyncBericht foutiefBericht = maakOnverwachtBericht(plaatsenAfnIndVerzoek);
        signalSync(foutiefBericht);

        // Beheerderskeuze: restart
        signalHumanTask("restartAtPlaatsen");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        final PlaatsAfnemersindicatieVerzoekBericht plaatsenAfnIndVerzoek2 = getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);
        final SyncBericht foutiefBericht2 = maakOnverwachtBericht(plaatsenAfnIndVerzoek2);
        signalSync(foutiefBericht2);

        // Beheerderskeuze: end
        checkVariabele(FOUTAFHANDELING_FOUT, "uc1003.plaatsen.foutiefbericht");
        signalHumanTask(END);

        // Verwacht 2 output berichten (Pf03 en Vb01) om de ap01/av01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);
    }

    @Test
    public void foutiefBerichtGeenPf03() {
        controleerBerichten(0, 0, 1);
        final PlaatsAfnemersindicatieVerzoekBericht plaatsenAfnIndVerzoek = getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);

        // Onverwacht bericht
        final SyncBericht foutiefBericht = maakOnverwachtBericht(plaatsenAfnIndVerzoek);
        signalSync(foutiefBericht);

        // Beheerderskeuze
        signalHumanTask("endWithoutPf03");
    }

}

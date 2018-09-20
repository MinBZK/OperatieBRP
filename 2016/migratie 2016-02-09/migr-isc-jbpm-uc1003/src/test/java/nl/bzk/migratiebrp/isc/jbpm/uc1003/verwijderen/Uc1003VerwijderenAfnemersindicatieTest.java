/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AbstractUc1003Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de technische flows rondom het verwijderen afnemersindicatie.
 */
public class Uc1003VerwijderenAfnemersindicatieTest extends AbstractUc1003Test {

    private static final String FOUT_AFHANDELING_FOUT = "foutafhandelingFout";
    private static final String END = "end";

    public Uc1003VerwijderenAfnemersindicatieTest() {
        super("/uc1003-verwijderen/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void startProces() {
        // Start
        startProcess(VerwijderenAfnIndTestUtil.maakAv01Bericht("580001", "1234567890"));

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
        getBericht(VerwijderAfnemersindicatieVerzoekBericht.class);

        // Afbreken
        signalProcess("afbreken");

        // Beheerderskeuze: restart
        signalHumanTask("restartAtVerwijderen");

        // Nog een keer
        controleerBerichten(0, 0, 1);
        getBericht(VerwijderAfnemersindicatieVerzoekBericht.class);
        signalProcess("afbreken");

        // Beheerderskeuze: end
        checkVariabele(FOUT_AFHANDELING_FOUT, "uc1003.verwijderen.afgebroken");
        signalHumanTask(END);

        // Verwacht 1 output bericht (pf) om de ap01/av01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);
    }

    @Test
    public void foutiefBericht() {
        controleerBerichten(0, 0, 1);
        final VerwijderAfnemersindicatieVerzoekBericht verwijderenAfnIndVerzoek = getBericht(VerwijderAfnemersindicatieVerzoekBericht.class);

        // Onverwacht bericht
        final SyncBericht foutiefBericht = maakOnverwachtBericht(verwijderenAfnIndVerzoek);
        signalSync(foutiefBericht);

        // Beheerderskeuze: end
        checkVariabele(FOUT_AFHANDELING_FOUT, "uc1003.verwijderen.foutiefbericht");
        signalHumanTask(END);

        // Verwacht 2 output bericht (Pf03 en Vb01) om de ap01/av01 cyclus netjes af te ronden
        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);
    }
}

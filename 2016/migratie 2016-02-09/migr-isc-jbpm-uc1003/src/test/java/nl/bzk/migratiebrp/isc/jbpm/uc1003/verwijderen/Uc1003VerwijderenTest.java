/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwijderAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AbstractUc1003Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de functionele flows van uc1003-verwijderen.
 */
public class Uc1003VerwijderenTest extends AbstractUc1003Test {

    public Uc1003VerwijderenTest() {
        super("/uc1003-verwijderen/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Test
    public void happyFlowVerwijderd() {
        // Start
        startProcess(VerwijderenAfnIndTestUtil.maakAv01Bericht("580001", "1234567890"));

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 1));

        // Verwijderen
        controleerBerichten(0, 0, 1);
        final VerwijderAfnemersindicatieVerzoekBericht verwijderVerzoekBericht = getBericht(VerwijderAfnemersindicatieVerzoekBericht.class);
        signalSync(maakVerwerkAfnemersindicatieAntwoordBericht(verwijderVerzoekBericht, null));

        // Einde
        controleerBerichten(0, 1, 0);
        final NullBericht nullBericht = getBericht(NullBericht.class);
        Assert.assertNotNull(nullBericht);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowAfnemerBestaatNiet() {
        // Start
        final Av01Bericht av01 = VerwijderenAfnIndTestUtil.maakAv01Bericht("123456", "1234567890");
        startProcess(av01);

        // Af11
        controleerBerichten(0, 1, 0);
        final Af11Bericht af11 = getBericht(Af11Bericht.class);
        Assert.assertEquals("X", af11.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af11.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af11.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(av01.getANummer(), af11.getANummer());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowGeenPersoonGevonden() {
        // Start
        final Av01Bericht av01 = VerwijderenAfnIndTestUtil.maakAv01Bericht("580001", "1234567890");
        startProcess(av01);

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 0));

        // Af11
        controleerBerichten(0, 1, 0);
        final Af11Bericht af11 = getBericht(Af11Bericht.class);
        Assert.assertEquals("G", af11.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af11.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af11.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(av01.getANummer(), af11.getANummer());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowMeerderePersonenGevonden() {
        // Start
        final Av01Bericht av01 = VerwijderenAfnIndTestUtil.maakAv01Bericht("580001", "1234567890");
        startProcess(av01);

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 2));

        // Af11
        controleerBerichten(0, 1, 0);
        final Af11Bericht af11 = getBericht(Af11Bericht.class);
        Assert.assertEquals("U", af11.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af11.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af11.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(av01.getANummer(), af11.getANummer());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowReedsNietGeplaatst() {
        // Start
        final Av01Bericht av01 = VerwijderenAfnIndTestUtil.maakAv01Bericht("580001", "1234567890");
        startProcess(av01);

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 1));

        // Plaatsen
        controleerBerichten(0, 0, 1);
        final VerwijderAfnemersindicatieVerzoekBericht verwijderVerzoekBericht = getBericht(VerwijderAfnemersindicatieVerzoekBericht.class);
        signalSync(maakVerwerkAfnemersindicatieAntwoordBericht(verwijderVerzoekBericht, "I"));

        // Af11
        controleerBerichten(0, 1, 0);
        final Af11Bericht af11 = getBericht(Af11Bericht.class);
        Assert.assertEquals("I", af11.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af11.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("1234567890", af11.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(av01.getANummer(), af11.getANummer());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.plaatsen;

import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.PlaatsAfnemersindicatieVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.isc.jbpm.uc1003.AbstractUc1003Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de functionele flows van uc1003-plaatsen.
 */
public class Uc1003PlaatsenTest extends AbstractUc1003Test {

    private static final String ACHTERNAAM = "Jansen";
    private static final int BSN = 123456789;
    private static final long A_NUMMER = 1234567890L;
    private static final String AFNEMER = "580001";

    public Uc1003PlaatsenTest() {
        super("/uc1003-plaatsen/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Test
    public void happyFlowGeplaatst() {
        // Start
        startProcess(PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, A_NUMMER, BSN, ACHTERNAAM, null));

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 1));

        // Plaatsen
        controleerBerichten(0, 0, 1);
        final PlaatsAfnemersindicatieVerzoekBericht plaatsVerzoekBericht = getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);
        signalSync(maakVerwerkAfnemersindicatieAntwoordBericht(plaatsVerzoekBericht, null));

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowAfnemerBestaatNiet() {
        // Start
        final Ap01Bericht ap01 = PlaatsenAfnIndTestUtil.maakAp01Bericht("123456", 1234567890L, 123456789, ACHTERNAAM, null);
        startProcess(ap01);

        // Af01
        controleerBerichten(0, 1, 0);
        final Af01Bericht af01 = getBericht(Af01Bericht.class);
        Assert.assertEquals("X", af01.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af01.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af01.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(ap01.getCategorieen(), af01.getCategorieen());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowGeenPersoonGevonden() {
        // Start
        final Ap01Bericht ap01 = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, 1234567890L, 123456789, ACHTERNAAM, null);
        startProcess(ap01);

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 0));

        // Af01
        controleerBerichten(0, 1, 0);
        final Af01Bericht af01 = getBericht(Af01Bericht.class);
        Assert.assertEquals("G", af01.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af01.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af01.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(ap01.getCategorieen(), af01.getCategorieen());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowMeerderePersonenGevonden() {
        // Start
        final Ap01Bericht ap01 = PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, 1234567890L, 123456789, ACHTERNAAM, null);
        startProcess(ap01);

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 2));

        // Af01
        controleerBerichten(0, 1, 0);
        final Af01Bericht af01 = getBericht(Af01Bericht.class);
        Assert.assertEquals("U", af01.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af01.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("0000000000", af01.getHeader(Lo3HeaderVeld.A_NUMMER));
        Assert.assertEquals(ap01.getCategorieen(), af01.getCategorieen());

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowReedsGeplaatst() {
        // Start
        startProcess(PlaatsenAfnIndTestUtil.maakAp01Bericht(AFNEMER, 1234567890L, 123456789, ACHTERNAAM, null));

        // Zoek Persoon
        controleerBerichten(0, 0, 1);
        final ZoekPersoonOpActueleGegevensVerzoekBericht zoekPersoonVerzoek = getBericht(ZoekPersoonOpActueleGegevensVerzoekBericht.class);
        signalSync(maakZoekPersoonAntwoordBericht(zoekPersoonVerzoek, 1));

        // Plaatsen
        controleerBerichten(0, 0, 1);
        final PlaatsAfnemersindicatieVerzoekBericht plaatsVerzoekBericht = getBericht(PlaatsAfnemersindicatieVerzoekBericht.class);
        signalSync(maakVerwerkAfnemersindicatieAntwoordBericht(plaatsVerzoekBericht, "I"));

        // Af01
        controleerBerichten(0, 1, 0);
        final Af01Bericht af01 = getBericht(Af01Bericht.class);
        Assert.assertEquals("I", af01.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("0000", af01.getHeader(Lo3HeaderVeld.GEMEENTE));
        Assert.assertEquals("1234567890", af01.getHeader(Lo3HeaderVeld.A_NUMMER));

        // Einde
        controleerBerichten(0, 0, 0);
        Assert.assertTrue(processEnded());
    }
}

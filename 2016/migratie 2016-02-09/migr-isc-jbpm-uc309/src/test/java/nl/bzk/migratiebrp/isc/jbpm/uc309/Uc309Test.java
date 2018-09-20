/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf21Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test uc309
 */
@ContextConfiguration(locations = {"classpath:/uc309-test-beans.xml" })
public class Uc309Test extends AbstractJbpmTest {

    private final Tb02Factory tb02Factory = new Tb02Factory();

    public Uc309Test() {
        super("/uc309/processdefinition.xml,/foutafhandeling/processdefinition.xml");
    }

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Before
    public void setupGemeenteRegister() {
        final List<Gemeente> gemeenten = new ArrayList<>();
        gemeenten.add(new Gemeente("3333", "582222", null));
        gemeenten.add(new Gemeente("0429", "580429", null));
        gemeenten.add(new Gemeente("2222", "583333", intToDate(19900101)));
        gemeenten.add(new Gemeente("0717", "580717", null));
        setGemeenteRegister(new GemeenteRegisterImpl(gemeenten));
    }

    @Test
    public void happyFlow() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);
        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final VerwerkToevalligeGebeurtenisAntwoordBericht tgAntwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        tgAntwoord.setStatus(StatusType.OK);
        signalSync(tgAntwoord);

        controleerBerichten(0, 1, 0);
        final NullBericht antwoord = getBericht(NullBericht.class);
        Assert.assertEquals("NullBericht moet gecorreleerd zijn aan Tb02", antwoord.getCorrelationId(), bericht.getMessageId());

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void tf21() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);

        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final VerwerkToevalligeGebeurtenisAntwoordBericht tgAntwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        tgAntwoord.setStatus(StatusType.FOUT);
        tgAntwoord.setFoutreden(FoutredenType.B);
        signalSync(tgAntwoord);

        controleerBerichten(0, 1, 0);

        final Tf21Bericht antwoord = getBericht(Tf21Bericht.class);
        Assert.assertNotNull("Verwacht bericht moet een Tf21 bericht zijn", antwoord);

        Assert.assertEquals("Foutreden zou B moeten zijn", "B", antwoord.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("Tf21Bericht moet gecorreleerd zijn aan tb02 bericht", bericht.getMessageId(), antwoord.getCorrelationId());
        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlow2aFout() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.ONTBINDING_INCORRECT);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlowAfbrekenEnd() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);

        getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        signalProcess("afbreken");
        signalHumanTask("end");

        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlowAfbrekenEndWithoutPf03() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);

        getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        signalProcess("afbreken");
        signalHumanTask("endWithoutPf03");

        controleerBerichten(0, 0, 0);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlowAfbrekenRestartAtVerstuurBerichtNullBericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);

        getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        signalProcess("afbreken");
        signalHumanTask("restartAtVerstuurBericht");

        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final VerwerkToevalligeGebeurtenisAntwoordBericht tgAntwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        tgAntwoord.setStatus(StatusType.OK);
        signalSync(tgAntwoord);

        controleerBerichten(0, 1, 0);
        final NullBericht antwoord = getBericht(NullBericht.class);
        Assert.assertEquals("NullBericht moet gecorreleerd zijn aan Tb02", antwoord.getCorrelationId(), bericht.getMessageId());

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlowAfbrekenRestartAtVerstuurBerichtTf21() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);

        getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        signalProcess("afbreken");
        signalHumanTask("restartAtVerstuurBericht");

        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final VerwerkToevalligeGebeurtenisAntwoordBericht tgAntwoord = new VerwerkToevalligeGebeurtenisAntwoordBericht();
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        tgAntwoord.setStatus(StatusType.FOUT);
        tgAntwoord.setFoutreden(FoutredenType.B);
        signalSync(tgAntwoord);

        controleerBerichten(0, 1, 0);

        final Tf21Bericht antwoord = getBericht(Tf21Bericht.class);
        Assert.assertNotNull("Verwacht bericht moet een Tf21 bericht zijn", antwoord);

        Assert.assertEquals("Foutreden zou B moeten zijn", "B", antwoord.getHeader(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("Tf21Bericht moet gecorreleerd zijn aan tb02 bericht", bericht.getMessageId(), antwoord.getCorrelationId());
        Assert.assertTrue(processEnded());

    }

    @Test
    public void badFlow3aFoutiefBericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);
        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final AutorisatieAntwoordBericht tgAntwoord = new AutorisatieAntwoordBericht();
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        tgAntwoord.setStatus(StatusType.OK);
        signalSync(tgAntwoord);

        signalHumanTask("end");

        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlow3bOngeldigBericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.SLUITING);
        bericht.setDoelGemeente("2222");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);
        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final OngeldigBericht tgAntwoord = new OngeldigBericht("tb02Bericht", "stuk");
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        signalSync(tgAntwoord);

        signalHumanTask("end");

        controleerBerichten(0, 2, 0);
        getBericht(Pf03Bericht.class);
        getBericht(Vb01Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.NullBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf21Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieAntwoordRecordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test uc309
 */
@ContextConfiguration(locations = {"classpath:/uc309-test-beans.xml"})
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
    public void setupPartijRegister() {
        final List<Partij> partijen = new ArrayList<>();
        partijen.add(new Partij("333301", "3333", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("042901", "0429", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("051801", "0518", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("222201", "2222", intToDate(19900101), Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        partijen.add(new Partij("071701", "0717", null, Arrays.asList(Rol.BIJHOUDINGSORGAAN_COLLEGE, Rol.AFNEMER)));
        setPartijRegister(new PartijRegisterImpl(partijen));
    }

    @Test
    public void happyFlow3A() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
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
    public void happyFlow2A() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakOverlijdenTb02Bericht();
        bericht.setDoelPartijCode("222201");
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
    public void happyFlow2A_11C10T010() throws Exception {
        //

        final String
                tb02 =
                "00000000Tb0200010101102 "
                        +
                        "A123400193011060110010159525750501200091265564530210007Jantine0240007Hermans03100081996010103200040599033000460300410001V0607708100082015010108200040518083000460308110004051881200072 A1234851000820150101";

        final Tb02Bericht bericht = (Tb02Bericht) new Lo3BerichtFactory().getBericht(tb02);

        // final Tb02Bericht bericht = tb02Factory.maakTb02Bericht(Tb02Factory.Soort.OVERLIJDEN);
        bericht.setBronPartijCode("051801");
        bericht.setDoelPartijCode("222201");
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
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
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

        Assert.assertEquals("Foutreden zou B moeten zijn", "B", antwoord.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("Tf21Bericht moet gecorreleerd zijn aan tb02 bericht", bericht.getMessageId(), antwoord.getCorrelationId());
        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlow2aFout() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakOntbindingIncorrectTb02Bericht();
        bericht.setDoelPartijCode("888801");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlowAfbrekenEnd() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);

        getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        signalProcess("afbreken");
        signalHumanTask("end");

        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlowAfbrekenEndWithoutPf03() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
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
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
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
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
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

        Assert.assertEquals("Foutreden zou B moeten zijn", "B", antwoord.getHeaderWaarde(Lo3HeaderVeld.FOUTREDEN));
        Assert.assertEquals("Tf21Bericht moet gecorreleerd zijn aan tb02 bericht", bericht.getMessageId(), antwoord.getCorrelationId());
        Assert.assertTrue(processEnded());

    }

    @Test
    public void badFlow3aFoutiefBericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);
        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final AutorisatieAntwoordBericht tgAntwoord = new AutorisatieAntwoordBericht();
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());

        AutorisatieAntwoordRecordType record = new AutorisatieAntwoordRecordType();
        record.setStatus(StatusType.TOEGEVOEGD);
        record.setAutorisatieId(34534L);
        tgAntwoord.getAutorisatieTabelRegels().add(record);
        signalSync(tgAntwoord);

        signalHumanTask("end");

        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }

    @Test
    public void badFlow3bOngeldigBericht() throws Exception {
        final Tb02Bericht bericht = tb02Factory.maakSluitingTb02Bericht();
        bericht.setDoelPartijCode("222201");
        bericht.setMessageId("123456");
        startProcess(bericht);

        controleerBerichten(0, 0, 1);
        final VerwerkToevalligeGebeurtenisVerzoekBericht tgVerzoek = getBericht(VerwerkToevalligeGebeurtenisVerzoekBericht.class);

        final OngeldigBericht tgAntwoord = new OngeldigBericht("tb02Bericht", "stuk");
        tgAntwoord.setCorrelationId(tgVerzoek.getMessageId());
        signalSync(tgAntwoord);

        signalHumanTask("end");

        controleerBerichten(0, 1, 0);
        getBericht(Pf03Bericht.class);

        Assert.assertTrue("Proces moet beeindigd zijn", processEnded());
    }
}

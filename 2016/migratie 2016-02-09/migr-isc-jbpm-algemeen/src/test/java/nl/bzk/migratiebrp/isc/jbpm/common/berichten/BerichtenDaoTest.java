/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.berichten;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtMetaData;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf03Bericht;
import nl.bzk.migratiebrp.isc.jbpm.common.AbstractJbpmDaoTest;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao.Direction;
import nl.bzk.migratiebrp.isc.jbpm.common.dao.VirtueelProcesDao;
import org.hibernate.jdbc.Work;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class BerichtenDaoTest extends AbstractJbpmDaoTest {

    @Autowired
    private BerichtenDao subject;

    @Autowired
    private VirtueelProcesDao virtueelProcesDao;

    private ExecutionContext executionContext;

    public BerichtenDaoTest() {
        super("/sql/mig-create.sql");
    }

    @Before
    public void setup() {
        executionContext = Mockito.mock(ExecutionContext.class);
        ExecutionContext.pushCurrentContext(executionContext);

        getSession().doWork(new Work() {
            @Override
            public void execute(final Connection connection) throws SQLException {
                try (PreparedStatement statement =
                        connection.prepareStatement("insert into jbpm_processinstance(id_, version_, issuspended_) values(?, 0, false)")) {
                    statement.setLong(1, 42L);
                    statement.executeUpdate();
                    statement.setLong(1, 4444L);
                    statement.executeUpdate();
                    statement.setLong(1, 5555L);
                    statement.executeUpdate();
                }
            }
        });
        final ProcessInstance processInstance = (ProcessInstance) getSession().load(ProcessInstance.class, 42L);
        Mockito.when(executionContext.getProcessInstance()).thenReturn(processInstance);

    }

    @After
    public void clear() {
        ExecutionContext.popCurrentContext(executionContext);
    }

    @Test
    public void test() throws Exception {
        final Pf03Bericht bericht = new Pf03Bericht();
        bericht.setMessageId("msg-id-1");
        bericht.setCorrelationId("corr-id-1");
        bericht.setBronGemeente("0518");
        bericht.setDoelGemeente("0599");

        final Long berichtId = subject.bewaarBericht(bericht);
        Assert.assertNotNull(berichtId);

        final Bericht check = subject.leesBericht(berichtId);
        Assert.assertNotNull(check);
        Assert.assertEquals(bericht, check);

        final BerichtMetaData metaData = subject.leesBerichtMetaData(berichtId);
        Assert.assertEquals(berichtId, metaData.getId());
        Assert.assertNotNull(metaData.getTijdstip());
        Assert.assertEquals("VOSPG", metaData.getKanaal());
        Assert.assertNull(metaData.getRichting());
        Assert.assertEquals("msg-id-1", metaData.getMessageId());
        Assert.assertEquals("corr-id-1", metaData.getCorrelationId());
        Assert.assertEquals("Pf03", metaData.getBerichtType());
    }

    @Test
    public void testEsb() throws Exception {
        final String msgId = UUID.randomUUID().toString();

        // Test
        final Long berichtId = subject.bewaar("TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", "from", "to", 123L);
        Assert.assertNotNull(berichtId);
        nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", null, null, null, "from", "to");

        subject.updateNaam(berichtId, "NAAM");
        bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", "NAAM", null, null, "from", "to");

        subject.updateProcessInstance(berichtId, 5555L);
        bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", "NAAM", 5555L, null, "from", "to");

        subject.updateActie(berichtId, "Getest");

        System.out.println("Voor zoekVraagBericht");
        bericht = subject.zoekVraagBericht(msgId, "TEST", "to", "from");
        System.out.println("Na zoekVraagBericht");
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", "NAAM", 5555L, null, "from", "to");

        bericht = subject.zoekVraagBericht("psg-id", "TEST", "to", "from");
        Assert.assertNull(bericht);
        bericht = subject.zoekVraagBericht(msgId, "PEST", "to", "from");
        Assert.assertNull(bericht);
        bericht = subject.zoekVraagBericht(msgId, "TEST", "top", "from");
        Assert.assertNull(bericht);
        bericht = subject.zoekVraagBericht(msgId, "TEST", "to", "fromw");
        Assert.assertNull(bericht);

        List<nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht> berichten = subject.zoekOpMsSequenceNumberBehalveId(123L, berichtId);
        Assert.assertEquals(0, berichten.size());

        berichten = subject.zoekOpMsSequenceNumberBehalveId(123L, -berichtId);
        Assert.assertEquals(1, berichten.size());

        int count = subject.telBerichtenBehalveId(msgId, "from", "to", "TEST", Direction.INKOMEND, berichtId);
        Assert.assertEquals(0, count);

        count = subject.telBerichtenBehalveId(msgId, "from", "to", "TEST", Direction.INKOMEND, -berichtId);
        Assert.assertEquals(1, count);

        bericht = subject.zoekMeestRecenteAntwoord("corr-id", "to", "from", "TEST");
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", "NAAM", 5555L, null, "from", "to");

        final String msgId2 = UUID.randomUUID().toString();
        Assert.assertFalse(msgId.equals(msgId2));

        subject.updateMessageId(berichtId, msgId2);
        bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId2, "corr-id", "inhoud", "NAAM", 5555L, null, "from", "to");

        subject.updateKanaalEnRichting(berichtId, "TEST2", Direction.UITGAAND);
        bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST2", Direction.UITGAAND, msgId2, "corr-id", "inhoud", "NAAM", 5555L, null, "from", "to");
    }

    @Test
    public void testVirtueel() throws Exception {

        final String msgId = UUID.randomUUID().toString();

        // Test
        final Long berichtId = subject.bewaar("TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", "from", "to", 123L);
        Assert.assertNotNull(berichtId);
        nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", null, null, null, "from", "to");

        final long virtueelProcesId = virtueelProcesDao.maak();
        subject.updateVirtueelProcesId(berichtId, virtueelProcesId);

        bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", null, null, virtueelProcesId, "from", "to");

        subject.omzettenVirtueelProcesNaarActueelProces(virtueelProcesId, 4444L);
        bericht = subject.getBericht(berichtId);
        assertBericht(bericht, berichtId, "TEST", Direction.INKOMEND, msgId, "corr-id", "inhoud", null, 4444L, null, "from", "to");
    }

    private void assertBericht(
        final nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht bericht,
        final Long id,
        final String kanaal,
        final Direction richting,
        final String messageId,
        final String correlationId,
        final String inhoud,
        final String naam,
        final Long processInstanceId,
        final Long virtuelProcesId,
        final String originator,
        final String recipient)
    {
        Assert.assertNotNull("Geen bericht", bericht);
        Assert.assertEquals(id, bericht.getId());
        Assert.assertNotNull(bericht.getTijdstip());
        Assert.assertEquals(kanaal, bericht.getKanaal());
        Assert.assertEquals(richting.getCode(), bericht.getRichting());
        Assert.assertEquals(messageId, bericht.getMessageId());
        Assert.assertEquals(correlationId, bericht.getCorrelationId());
        Assert.assertEquals(inhoud, bericht.getBericht());
        Assert.assertEquals(naam, bericht.getNaam());
        Assert.assertEquals(processInstanceId, bericht.getProcessInstance() == null ? null : bericht.getProcessInstance().getId());
        Assert.assertEquals(virtuelProcesId, bericht.getVirtueelProces() == null ? null : bericht.getVirtueelProces().getId());
        Assert.assertEquals(originator, bericht.getVerzendendePartij());
        Assert.assertEquals(recipient, bericht.getOntvangendePartij());
    }
}

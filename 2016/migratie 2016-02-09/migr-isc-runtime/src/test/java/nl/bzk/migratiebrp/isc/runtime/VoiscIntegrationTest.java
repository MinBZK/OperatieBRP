/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.util.Date;
import java.util.List;
import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf11Bericht;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Test;

public class VoiscIntegrationTest extends AbstractIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Test
    public void testGeenCorrelatieGevonden() {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setMessageId("tf11-001");
        input.setCorrelationId("anderbericht-001");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 234L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01);
    }

    @Test
    public void testNullMsSequenceNummer() throws InterruptedException {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setMessageId("tf11-002");

        putMessage(VOISC_ONTVANGST_QUEUE, input, null, null);

        // Even wachten
        Thread.sleep(5000);

        // Verwacht: beheerdere genotificeerd
        controleerFoutmeldingsProces(input.getMessageId(), "esb.mssequencenumber.leeg");
    }

    @Test
    public void testDubbeleMsSequenceNummerVanAndereAfzender() throws InterruptedException {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setBronGemeente("0616");
        input.setDoelGemeente("3000250");
        input.setMessageId("tf11-003");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 345L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01);

        // Start
        input.setMessageId("tf11-003a");
        input.setBronGemeente("0599");
        putMessage(VOISC_ONTVANGST_QUEUE, input, 345L, null);

        // Even wachten
        Thread.sleep(5000);

        // Verwacht: beheerdere genotificeerd
        controleerFoutmeldingsProces(input.getMessageId(), "esb.mssequencenumber.fout");
    }

    @Test
    public void testHerhalingNegeren() throws InterruptedException {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setBronGemeente("0717");
        input.setDoelGemeente("3000250");
        input.setMessageId("tf11-005");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 567L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01);

        // Herhaling
        putMessage(VOISC_ONTVANGST_QUEUE, input, 568L, null);

        Thread.sleep(5000);

        executeInJbpmInTransaction(new JbpmExecution<Void>() {
            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
                final Query query =
                        session.createQuery("from nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht where msSequenceNumber = :msSequenceNumber");
                query.setLong("msSequenceNumber", 568L);

                final List<?> result = query.list();
                Assert.assertEquals("1 bericht verwacht", 1, result.size());

                final Bericht bericht = (Bericht) query.list().iterator().next();
                Assert.assertEquals("Actie komt niet overeen", "herhalingGenegeerd", bericht.getActie());

                return null;
            }
        });
    }

    @Test
    public void testHerhalingBeantwoorden() {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setBronGemeente("0717");
        input.setDoelGemeente("3000250");
        input.setMessageId("tf11-006");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 667L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01);

        // Update uitgaand bericht tijdstip
        executeInJbpmInTransaction(new JbpmExecution<Void>() {

            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
                final Query query = session.createQuery("from nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht where messageId = :messageId");
                query.setString("messageId", pf01.getMessageId());

                final List<?> result = query.list();
                Assert.assertEquals("1 bericht verwacht", 1, result.size());

                ((Bericht) query.list().iterator().next()).setTijdstip(new Date(1L));

                return null;
            }
        });

        // Herhaling
        putMessage(VOISC_ONTVANGST_QUEUE, input, 668L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01_2 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01_2);

    }

    private void controleerFoutmeldingsProces(final String messageId, final String foutCode) {
        final Long processInstanceId = bepaalProcessInstanceIdObvMessageId(messageId);

        executeInJbpmInTransaction(new JbpmExecution<Void>() {

            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                final ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                Assert.assertEquals("Process zou een lopende taak moeten hebben", 1, processInstance.getTaskMgmtInstance().getTaskInstances().size());

                if (foutCode != null) {
                    Assert.assertEquals("Foutcode is incorrect.", foutCode, processInstance.getContextInstance().getVariable("fout"));
                }
                return null;
            }
        });
    }

    private Long bepaalProcessInstanceIdObvMessageId(final String messageId) {
        return executeInJbpmInTransaction(new JbpmExecution<Long>() {
            @Override
            public Long doInContext(final JbpmContext jbpmContext) {
                final Session session = (Session) jbpmContext.getServices().getPersistenceService().getCustomSession(Session.class);
                final Query query = session.createQuery("from nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht where messageId = :messageId");
                query.setString("messageId", messageId);

                final List<?> result = query.list();
                Assert.assertEquals("1 bericht verwacht", 1, result.size());

                return ((Bericht) query.list().iterator().next()).getProcessInstance().getId();
            }
        });

    }

}

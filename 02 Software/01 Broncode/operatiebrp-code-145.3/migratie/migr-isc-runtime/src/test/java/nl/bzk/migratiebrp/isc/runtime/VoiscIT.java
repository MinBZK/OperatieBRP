/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Af11Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.DeliveryReport;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Pf01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.StatusReport;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tf11Bericht;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker.JbpmExecution;
import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Bericht;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.junit.Assert;
import org.junit.Test;

public class VoiscIT extends AbstractIT {

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
        TimeUnit.MILLISECONDS.sleep(5000);

        // Verwacht: beheerdere genotificeerd
        controleerFoutmeldingsProces(input.getMessageId(), "esb.mssequencenumber.leeg");
    }

    @Test
    public void testDubbeleMsSequenceNummerVanAndereAfzender() throws InterruptedException {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setBronPartijCode("0616");
        input.setDoelPartijCode("3000250");
        input.setMessageId("tf11-003");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 345L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01);

        // Start
        input.setMessageId("tf11-003a");
        input.setBronPartijCode("0599");
        putMessage(VOISC_ONTVANGST_QUEUE, input, 345L, null);

        // Even wachten
        TimeUnit.MILLISECONDS.sleep(5000);

        // Verwacht: beheerdere genotificeerd
        controleerFoutmeldingsProces(input.getMessageId(), "esb.mssequencenumber.fout");
    }

    @Test
    public void testHerhalingNegeren() throws InterruptedException {
        // Start
        final Lo3Bericht input = new Tf11Bericht();
        input.setBronPartijCode("0717");
        input.setDoelPartijCode("3000250");
        input.setMessageId("tf11-005");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 567L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01);

        // Herhaling
        putMessage(VOISC_ONTVANGST_QUEUE, input, 568L, null);

        TimeUnit.MILLISECONDS.sleep(5000);

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
        input.setBronPartijCode("0717");
        input.setDoelPartijCode("3000250");
        input.setMessageId("tf11-006");

        putMessage(VOISC_ONTVANGST_QUEUE, input, 6667L, null);

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
        putMessage(VOISC_ONTVANGST_QUEUE, input, 6668L, null);

        // Verwacht Pf01
        final Pf01Bericht pf01_2 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", pf01_2);

    }

    @Test
    public void testDelRep() throws InterruptedException {
        // Setup
        final Lo3Bericht oorspronkelijkBericht = new Af01Bericht();
        oorspronkelijkBericht.setBronPartijCode("0699");
        oorspronkelijkBericht.setDoelPartijCode("3000220");
        oorspronkelijkBericht.setMessageId("af01-007");

        putMessage(VOISC_ONTVANGST_QUEUE, oorspronkelijkBericht, 678L, null);

        // Verwacht Pf01
        final Pf01Bericht eerstePf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", eerstePf01);

        // Start
        final Lo3Bericht input = new DeliveryReport();
        input.setBronPartijCode("0699");
        input.setDoelPartijCode("3000220");
        input.setMessageId("DELREP-001");
        ((DeliveryReport) input).setNonDeliveryReason(1035);
        input.setCorrelationId(eerstePf01.getMessageId());

        putMessage(VOISC_ONTVANGST_QUEUE, input, 767L, null);

        // Even wachten
        TimeUnit.MILLISECONDS.sleep(5000);

        // Controleer of er een beheerderstaak is aangemaakt.
        controleerFoutmeldingsProces(input.getMessageId(), "NonDeliveryReport");
    }

    @Test
    public void testStaRep() throws InterruptedException {
        // Setup
        final Lo3Bericht oorspronkelijkBericht = new Af01Bericht();
        oorspronkelijkBericht.setBronPartijCode("0717");
        oorspronkelijkBericht.setDoelPartijCode("3000220");
        oorspronkelijkBericht.setMessageId("af01-017");

        putMessage(VOISC_ONTVANGST_QUEUE, oorspronkelijkBericht, 878L, null);

        // Verwacht Pf01
        final Pf01Bericht eerstePf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", eerstePf01);

        // Start
        final Lo3Bericht input = new StatusReport();
        input.setBronPartijCode("0717");
        input.setDoelPartijCode("3000220");
        input.setMessageId("STAREP-001");
        ((StatusReport) input).setNotificationType(1);
        input.setCorrelationId(eerstePf01.getMessageId());

        putMessage(VOISC_ONTVANGST_QUEUE, input, 967L, null);

        // Even wachten
        TimeUnit.MILLISECONDS.sleep(10000);

        // Controleer of er een beheerderstaak is aangemaakt.
        controleerFoutmeldingsProces(input.getMessageId(), "StatusReport");

        // Verwacht Pf01
        final Pf01Bericht tweedePf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", tweedePf01);
        Assert.assertEquals("Message Id's van beide Pf01's dienen hetzelfde te zijn.", eerstePf01.getMessageId(), tweedePf01.getMessageId());

    }

    @Test
    public void testHerhaaldeStaRep() throws InterruptedException {
        // Setup
        final Lo3Bericht oorspronkelijkBericht = new Af11Bericht();
        oorspronkelijkBericht.setBronPartijCode("0717");
        oorspronkelijkBericht.setDoelPartijCode("3000220");
        oorspronkelijkBericht.setMessageId("af11-027");

        putMessage(VOISC_ONTVANGST_QUEUE, oorspronkelijkBericht, 1178L, null);

        // Verwacht Pf01
        final Pf01Bericht eerstePf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", eerstePf01);

        // Start
        final Lo3Bericht input = new StatusReport();
        input.setBronPartijCode("0717");
        input.setDoelPartijCode("3000220");
        input.setMessageId("STAREP-101");
        ((StatusReport) input).setNotificationType(1);
        input.setCorrelationId(eerstePf01.getMessageId());

        putMessage(VOISC_ONTVANGST_QUEUE, input, 1967L, null);

        // Even wachten
        TimeUnit.MILLISECONDS.sleep(10000);

        // Controleer of er een beheerderstaak is aangemaakt.
        controleerFoutmeldingsProces(input.getMessageId(), "StatusReport");

        // Verwacht Pf01
        final Pf01Bericht tweedePf01 = expectMessage(VOISC_VERZENDEN_QUEUE, Pf01Bericht.class);
        Assert.assertNotNull("Pf01 verwacht.", tweedePf01);
        Assert.assertEquals("Message Id's van beide Pf01's dienen hetzelfde te zijn.", eerstePf01.getMessageId(), tweedePf01.getMessageId());

        // Tweede StaRep
        final Lo3Bericht tweedeInput = new StatusReport();
        tweedeInput.setBronPartijCode("0717");
        tweedeInput.setDoelPartijCode("3000220");
        tweedeInput.setMessageId("STAREP-102");
        ((StatusReport) tweedeInput).setNotificationType(1);
        tweedeInput.setCorrelationId(eerstePf01.getMessageId());

        putMessage(VOISC_ONTVANGST_QUEUE, tweedeInput, 2967L, null);

        // Even wachten
        TimeUnit.MILLISECONDS.sleep(10000);

        // Controleer of er een beheerderstaak is aangemaakt.
        controleerFoutmeldingsProces(tweedeInput.getMessageId(), "StatusReport");

    }

    private void controleerFoutmeldingsProces(final String messageId, final String foutCode) {
        final Long processInstanceId = bepaalProcessInstanceIdObvMessageId(messageId);

        executeInJbpmInTransaction(new JbpmExecution<Void>() {

            @Override
            public Void doInContext(final JbpmContext jbpmContext) {
                LOGGER.error("Laden proces met id: {} ", processInstanceId);
                ProcessInstance processInstance = jbpmContext.loadProcessInstance(processInstanceId);
                LOGGER.error("Procesnaam = {} ", processInstance.getProcessDefinition().getName());
                if (!processInstance.hasEnded()) {
                    // Corrigeer voor herhaal proces, aangezien foutafhandeling er een subproces van is.
                    if ("herhaal".equalsIgnoreCase(processInstance.getProcessDefinition().getName())) {
                        final Token rootToken = processInstance.getRootToken();
                        processInstance = rootToken.getSubProcessInstance();
                        LOGGER.info(
                                "Proces is herhaalproces, id gewijzigd naar foutadhandeling proces: {} -> {}",
                                processInstanceId,
                                processInstance.getId());
                    }
                    Assert.assertEquals("Proces zou een taak moeten hebben", 1, processInstance.getTaskMgmtInstance().getTaskInstances().size());
                } else {
                    Assert.assertTrue(
                            "Alleen het herhaalproces mag beeindigd zijn door automatisch herhalen.",
                            "herhaal".equalsIgnoreCase(processInstance.getProcessDefinition().getName()));
                }
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

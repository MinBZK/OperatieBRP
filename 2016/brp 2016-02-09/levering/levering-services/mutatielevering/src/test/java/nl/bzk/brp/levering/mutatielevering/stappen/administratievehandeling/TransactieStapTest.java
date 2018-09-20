/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContextImpl;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.validatie.Melding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

@RunWith(MockitoJUnitRunner.class)
public class TransactieStapTest {

    @InjectMocks
    private final TransactieStap transactieStap = new TransactieStap();

    @Mock
    private PlatformTransactionManager transactionManagerMaster;

    @Mock
    private PlatformTransactionManager jmsTransactionManagerAfnemers;

    @Test
    public final void testTransactieWordtGecreeerdMetJuisteParameters() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        final ArgumentCaptor<DefaultTransactionDefinition> argument =
            ArgumentCaptor.forClass(DefaultTransactionDefinition.class);

        transactieStap
            .voerStapUit(onderwerp, context, new AdministratieveHandelingVerwerkingResultaat());

        verify(transactionManagerMaster).getTransaction(argument.capture());

        assertFalse(argument.getValue().isReadOnly());
        assertEquals(TransactionDefinition.PROPAGATION_REQUIRES_NEW,
            argument.getValue().getPropagationBehavior());
        assertEquals("Transactie tbv verwerking van administratieve handeling met id: 1",
            argument.getValue().getName());
    }

    @Test
    public final void testJmsTransactieWordtGecreeerdMetJuisteParameters() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        final ArgumentCaptor<DefaultTransactionDefinition> argument =
            ArgumentCaptor.forClass(DefaultTransactionDefinition.class);

        transactieStap
            .voerStapUit(onderwerp, context, new AdministratieveHandelingVerwerkingResultaat());

        verify(jmsTransactionManagerAfnemers).getTransaction(argument.capture());

        assertFalse(argument.getValue().isReadOnly());
        assertEquals(TransactionDefinition.PROPAGATION_REQUIRES_NEW,
            argument.getValue().getPropagationBehavior());
        assertEquals("JMS transactie tbv verwerking van administratieve handeling met id: 1",
            argument.getValue().getName());
    }

    @Test
    public final void testGeenTransactionStatus() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();
        context.setTransactionStatus(null);
        context.setJmsTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testGeenJmsTransactionStatus() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();
        context.setTransactionStatus(getTransactionStatusCompleted());
        context.setJmsTransactionStatus(null);

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testGeenSuccesvolResultaat() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = getResultaatMetFoutMeldingen();
        context.setTransactionStatus(getTransactionStatusCompleted());
        context.setJmsTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testFoutResultaatVeroorzaaktRollbackInNaverwerking() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = getResultaatMetFoutMeldingen();
        context.setTransactionStatus(getTransactionStatusCompleted());
        context.setJmsTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testFoutResultaatVeroorzaaktJmsRollbackInNaverwerking() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = getResultaatMetFoutMeldingen();
        context.setJmsTransactionStatus(getTransactionStatusCompleted());
        context.setTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testRollbackStatusVeroorzaaktRollbackInNaverwerking() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();
        context.setTransactionStatus(getTransactionStatusCompleted());
        context.setJmsTransactionStatus(getTransactionStatusCompleted());
        //Markeer rollback
        context.getTransactionStatus().setRollbackOnly();

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testJmsRollbackStatusVeroorzaaktRollbackInNaverwerking() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();
        context.setJmsTransactionStatus(getTransactionStatusCompleted());
        context.setTransactionStatus(getTransactionStatusCompleted());
        //Markeer rollback
        context.getJmsTransactionStatus().setRollbackOnly();

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(jmsTransactionManagerAfnemers).rollback(Matchers.any(TransactionStatus.class));
        verify(transactionManagerMaster).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testNormaleFlow() {
        final AdministratieveHandelingMutatie onderwerp = new AdministratieveHandelingMutatie(1L);
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();

        context.setTransactionStatus(getTransactionStatusCompleted());
        context.setJmsTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManagerMaster).commit(Matchers.any(TransactionStatus.class));
        verify(jmsTransactionManagerAfnemers).commit(Matchers.any(TransactionStatus.class));
    }

    private TransactionStatus getTransactionStatusCompleted() {
        final DefaultTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        return status;
    }

    private AdministratieveHandelingVerwerkingResultaat getResultaatMetFoutMeldingen() {
        final AdministratieveHandelingVerwerkingResultaat resultaat = new AdministratieveHandelingVerwerkingResultaat();
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Deze fout veroorzaakt een rollback"));
        return resultaat;
    }

}

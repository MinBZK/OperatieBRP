/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;


/**
 * Unit test voor de {@link CreeerTransactieStap} class.
 */
public class CreeerTransactieStapTest {

    @Mock
    private EntityManager              em;
    @Mock
    private PlatformTransactionManager transactionManager;

    private CreeerTransactieStap       stap;
    private BerichtContext             context;

    @Test
    public void testTransactieTimeoutGetterEnSetter() {
        CreeerTransactieStap lokaleStap = new CreeerTransactieStap();

        assertNull(lokaleStap.getTransactionTimeoutInSeconds());
        lokaleStap.setTransactionTimeoutInSeconds(3L);
        assertEquals(new Long(3), lokaleStap.getTransactionTimeoutInSeconds());
        lokaleStap.setTransactionTimeoutInSeconds(5L);
        assertEquals(new Long(5), lokaleStap.getTransactionTimeoutInSeconds());
    }

    @Test
    public void testContextNaNormaleTransactieCreatieMetInitieelLegeContext() {
        assertNull(context.getBusinessTransactionStatus());
        stap.setTransactionTimeoutInSeconds(3L);

        boolean result = stap.voerVerwerkingsStapUitVoorBericht(null, context, null);
        assertTrue(result);
        assertNotNull(context.getBusinessTransactionStatus());
    }

    @Test
    public void testTransactieCreatieMetReedsAanwezigeBusinessTransactieInContext() {
        context.setBusinessTransactionStatus(Mockito.mock(TransactionStatus.class));
        stap.setTransactionTimeoutInSeconds(3L);

        boolean result = stap.voerVerwerkingsStapUitVoorBericht(null, context, null);
        assertFalse(result);
    }

    @Test
    public void testNaVerwerkingRollbackNodig() {
        TransactionStatus status = initContext(true, true);

        stap.naVerwerkingsStapVoorBericht(null, context);
        Mockito.verify(transactionManager).rollback(status);
        Mockito.verify(transactionManager, Mockito.times(0)).commit(Matchers.any(TransactionStatus.class));
        assertNull(context.getBusinessTransactionStatus());
    }

    @Test
    public void testNaVerwerkingNormaleCommit() {
        TransactionStatus status = initContext(false, true);

        stap.naVerwerkingsStapVoorBericht(null, context);
        Mockito.verify(transactionManager, Mockito.times(0)).rollback(Matchers.any(TransactionStatus.class));
        Mockito.verify(transactionManager).commit(status);
        assertNull(context.getBusinessTransactionStatus());
    }

    @Test
    public void testNaVerwerkingMetFalendeCommit() {
        TransactionStatus status = initContext(false, false, true);
        Mockito.doThrow(new RuntimeException()).when(transactionManager).commit(status);

        stap.naVerwerkingsStapVoorBericht(null, context);
        Mockito.verify(transactionManager).commit(status);
        Mockito.verify(transactionManager).rollback(status);
        assertNull(context.getBusinessTransactionStatus());
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        TransactionStatus status = Mockito.mock(TransactionStatus.class);
        Query query = Mockito.mock(Query.class);
        Mockito.when(transactionManager.getTransaction(Matchers.any(TransactionDefinition.class))).thenReturn(status);
        Mockito.when(em.createNativeQuery(Matchers.anyString())).thenReturn(query);

        stap = new CreeerTransactieStap();
        context = new BerichtContext();

        ReflectionTestUtils.setField(stap, "em", em);
        ReflectionTestUtils.setField(stap, "transactionManager", transactionManager);
    }

    private TransactionStatus initContext(final Boolean isRollbackOnly, final Boolean isCompleted, final Boolean... nextIsCompleted) {
        TransactionStatus status = Mockito.mock(TransactionStatus.class);
        Mockito.when(status.isRollbackOnly()).thenReturn(isRollbackOnly);
        Mockito.when(status.isCompleted()).thenReturn(isCompleted, nextIsCompleted);

        context.setBusinessTransactionStatus(status);
        return status;
    }
}

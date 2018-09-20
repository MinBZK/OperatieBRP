/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class BevragingTransactieStapTest {

    private BevragingTransactieStap bevragingTransactieStap;

    @Mock
    private PlatformTransactionManager transactionManager;

    private BevragingBerichtContext context;


    @Before
    public final void init() {
        bevragingTransactieStap = new BevragingTransactieStap();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(bevragingTransactieStap, "transactionManager", transactionManager);
        context = new BevragingBerichtContextBasis(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref", null);
    }

    @Test
    public final void testTransactieIsReadOnly() {
        final BevragingsBericht bericht = new GeefDetailsPersoonBericht();
        final ArgumentCaptor<DefaultTransactionDefinition> argument =
            ArgumentCaptor.forClass(DefaultTransactionDefinition.class);
        bevragingTransactieStap
            .voerStapUit(bericht, context, new BevragingResultaat(null));
        Mockito.verify(transactionManager).getTransaction(argument.capture());

        Assert.assertTrue(argument.getValue().isReadOnly());
    }

    @Test
    public final void testNaVerwerkingDoetEenRollback() {
        final BevragingsBericht bericht = new GeefDetailsPersoonBericht();
        context = new BevragingBerichtContextBasis(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref", null);
        final DefaultTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bevragingTransactieStap.voerNabewerkingStapUit(bericht, context, new BevragingResultaat(null));
        Mockito.verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testGeenTransactionStatusInNaWerking() {
        final BevragingsBericht bericht = new GeefDetailsPersoonBericht();
        bevragingTransactieStap.voerNabewerkingStapUit(bericht, context, new BevragingResultaat(null));
        Mockito.verify(transactionManager, Mockito.times(0)).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test(expected = IllegalStateException.class)
    public final void testBusinessTransactieMagNietGeclearedWordenIndienInCompleet() {
        final BevragingsBericht bericht = new GeefDetailsPersoonBericht();
        final TransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        context.setTransactionStatus(status);
        bevragingTransactieStap.voerNabewerkingStapUit(bericht, context, new BevragingResultaat(null));
    }
}

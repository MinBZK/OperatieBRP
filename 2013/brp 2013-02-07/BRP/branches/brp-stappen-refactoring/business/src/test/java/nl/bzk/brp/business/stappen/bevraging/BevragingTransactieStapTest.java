/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bevraging.AbstractBevragingsBericht;
import nl.bzk.brp.business.dto.bevraging.VraagDetailsPersoonBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
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
import org.springframework.transaction.support.AbstractTransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class BevragingTransactieStapTest {

    private BevragingTransactieStap bevragingTransactieStap;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Before
    public void init() {
        bevragingTransactieStap = new BevragingTransactieStap();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(bevragingTransactieStap, "transactionManager", transactionManager);
    }

    @Test
    public void testTransactieIsReadOnly() {
        AbstractBevragingsBericht bericht = new VraagDetailsPersoonBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref");
        ArgumentCaptor<DefaultTransactionDefinition> argument =
            ArgumentCaptor.forClass(DefaultTransactionDefinition.class);
        bevragingTransactieStap
            .voerVerwerkingsStapUitVoorBericht(bericht, context, new BerichtVerwerkingsResultaat(null));
        Mockito.verify(transactionManager).getTransaction(argument.capture());

        Assert.assertTrue(argument.getValue().isReadOnly());
    }

    @Test
    public void testNaVerwerkingDoetEenRollback() {
        AbstractBevragingsBericht bericht = new VraagDetailsPersoonBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref");
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bevragingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, new BerichtVerwerkingsResultaat(null));
        Mockito.verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public void testGeenTransactionStatusInNaWerking() {
        AbstractBevragingsBericht bericht = new VraagDetailsPersoonBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref");
        bevragingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, new BerichtVerwerkingsResultaat(null));
        Mockito.verify(transactionManager, Mockito.times(0)).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test(expected = IllegalStateException.class)
    public void testBusinessTransactieMagNietGeclearedWordenIndienInCompleet() {
        AbstractBevragingsBericht bericht = new VraagDetailsPersoonBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref");
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        context.setTransactionStatus(status);
        bevragingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, new BerichtVerwerkingsResultaat(null));
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.gedeeld.Partij;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractTransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class BijhoudingTransactieStapTest {

    private BijhoudingTransactieStap bijhoudingTransactieStap;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bijhoudingTransactieStap = new BijhoudingTransactieStap();
        ReflectionTestUtils.setField(bijhoudingTransactieStap, "transactionManager", transactionManager);
    }

    @Test
    public void testTransactieWordtGecreeerdMetJuisteParameters() {
        BijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij());
        ArgumentCaptor<DefaultTransactionDefinition> argument = ArgumentCaptor.forClass(DefaultTransactionDefinition.class);
        bijhoudingTransactieStap.voerVerwerkingsStapUitVoorBericht(bericht, context, new BerichtResultaat(null));
        Mockito.verify(transactionManager).getTransaction(argument.capture());

        Assert.assertFalse(argument.getValue().isReadOnly());
        Assert.assertEquals(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW, argument.getValue().getPropagationBehavior());
    }

    @Test
    public void testFoutResultaatVeroorzaaktRollbackInNaverwerking() {
        BijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij());
        final BerichtResultaat resultaat = new BerichtResultaat(null);
        resultaat.markeerVerwerkingAlsFoutief();
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        bericht.getBerichtStuurgegevens().setPrevalidatieBericht(false);
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).rollback(Mockito.any(TransactionStatus.class));
    }

    @Test
    public void testRollbackStatusVeroorzaaktRollbackInNaverwerking() {
        BijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij());
        final BerichtResultaat resultaat = new BerichtResultaat(null);
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        ReflectionTestUtils.setField(resultaat, "resultaat", ResultaatCode.GOED);
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        bericht.getBerichtStuurgegevens().setPrevalidatieBericht(false);
        //Markeer rollback
        context.getTransactionStatus().setRollbackOnly();
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).rollback(Mockito.any(TransactionStatus.class));
    }

    @Test
    public void testPreValidatieBerichtVeroorzaaktRollbackInNaverwerking() {
        BijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij());
        final BerichtResultaat resultaat = new BerichtResultaat(null);
        ReflectionTestUtils.setField(resultaat, "resultaat", ResultaatCode.GOED);
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        //Markeer bericht voor prevalidatie
        bericht.getBerichtStuurgegevens().setPrevalidatieBericht(true);
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).rollback(Mockito.any(TransactionStatus.class));
    }

    @Test
    public void testNormaleFlow() {
        BijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij());
        final BerichtResultaat resultaat = new BerichtResultaat(null);
        ReflectionTestUtils.setField(resultaat, "resultaat", ResultaatCode.GOED);
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevens(new BerichtStuurgegevens());
        bericht.getBerichtStuurgegevens().setPrevalidatieBericht(false);
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).commit(Mockito.any(TransactionStatus.class));
    }
}

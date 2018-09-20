/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import junit.framework.Assert;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
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
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        ArgumentCaptor<DefaultTransactionDefinition> argument = ArgumentCaptor.forClass(DefaultTransactionDefinition.class);
        bijhoudingTransactieStap.voerVerwerkingsStapUitVoorBericht(bericht, context, new BerichtVerwerkingsResultaat(null));
        Mockito.verify(transactionManager).getTransaction(argument.capture());

        Assert.assertFalse(argument.getValue().isReadOnly());
        Assert.assertEquals(TransactionDefinition.PROPAGATION_REQUIRES_NEW, argument.getValue().getPropagationBehavior());
    }

    @Test
    public void testFoutResultaatVeroorzaaktRollbackInNaverwerking() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        resultaat.markeerVerwerkingAlsFoutief();
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setIndPrevalidatie(null);
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public void testRollbackStatusVeroorzaaktRollbackInNaverwerking() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setIndPrevalidatie(null);
        //Markeer rollback
        context.getTransactionStatus().setRollbackOnly();
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public void testPreValidatieBerichtVeroorzaaktRollbackInNaverwerking() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setIndPrevalidatie(Ja.Ja);
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public void testNormaleFlow() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        final BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setBerichtStuurgegevensGroep(new BerichtStuurgegevensGroepBericht());
        ((BerichtStuurgegevensGroepBericht) bericht.getBerichtStuurgegevensGroep()).setIndPrevalidatie(null);
        bijhoudingTransactieStap.naVerwerkingsStapVoorBericht(bericht, context, resultaat);
        Mockito.verify(transactionManager).commit(Matchers.any(TransactionStatus.class));
    }
}

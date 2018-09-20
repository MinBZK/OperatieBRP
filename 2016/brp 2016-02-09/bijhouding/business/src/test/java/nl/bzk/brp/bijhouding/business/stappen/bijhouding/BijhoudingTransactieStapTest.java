/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.locking.BrpLocker;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingswijzeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

    @Mock
    private BrpLocker brpLocker;

    private BijhoudingBerichtContext context;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bijhoudingTransactieStap = new BijhoudingTransactieStap();
        ReflectionTestUtils.setField(bijhoudingTransactieStap, "transactionManager", transactionManager);
        ReflectionTestUtils.setField(bijhoudingTransactieStap, "brpLocker", brpLocker);

        context = new BijhoudingBerichtContext(new BerichtenIds(1L, 1L), Mockito.mock(Partij.class), "ref", null);
    }

    @Test
    public void testTransactieWordtGecreeerdMetJuisteParameters() {
        context.setAdministratieveHandeling(maakAdministratieveHandelingMetId(123));
        ArgumentCaptor<DefaultTransactionDefinition> argument = ArgumentCaptor.forClass(DefaultTransactionDefinition.class);
        bijhoudingTransactieStap.startTransactie(context);
        verify(transactionManager).getTransaction(argument.capture());

        Assert.assertFalse(argument.getValue().isReadOnly());
        Assert.assertEquals(TransactionDefinition.PROPAGATION_REQUIRES_NEW, argument.getValue().getPropagationBehavior());
        assertThat(context.getResultaatId(), CoreMatchers.notNullValue());
    }

    @Test
    public void testFoutResultaatVeroorzaaktRollbackInNaverwerking() {
        BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();
        context.setAdministratieveHandeling(maakAdministratieveHandelingMetId(123));
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setVerwerkingswijze(null);

        bijhoudingTransactieStap.stopTransactie(bericht, context, true);
        verify(transactionManager).rollback(any(TransactionStatus.class));
        assertThat(context.getResultaatId(), CoreMatchers.nullValue());
    }

    @Test
    public void testRollbackStatusVeroorzaaktRollbackInNaverwerking() {
        BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();
        context.setAdministratieveHandeling(maakAdministratieveHandelingMetId(123));
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setVerwerkingswijze(null);
        //Markeer rollback
        context.getTransactionStatus().setRollbackOnly();
        bijhoudingTransactieStap.stopTransactie(bericht, context, false);
        verify(transactionManager).rollback(any(TransactionStatus.class));
        assertThat(context.getResultaatId(), CoreMatchers.nullValue());
    }

    @Test
    public void testPreValidatieBerichtVeroorzaaktRollbackInNaverwerking() {
        BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();

        context.setAdministratieveHandeling(maakAdministratieveHandelingMetId(123));
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setVerwerkingswijze(new VerwerkingswijzeAttribuut(Verwerkingswijze.PREVALIDATIE));
        bijhoudingTransactieStap.stopTransactie(bericht, context, false);
        verify(transactionManager).rollback(any(TransactionStatus.class));
        assertThat(context.getResultaatId(), CoreMatchers.nullValue());
    }

    @Test
    public void testNormaleFlow() throws BrpLockerExceptie {
        BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();

        context.setAdministratieveHandeling(maakAdministratieveHandelingMetId(123));
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setVerwerkingswijze(null);
        when(brpLocker.isLockNogAanwezig()).thenReturn(Boolean.TRUE);
        bijhoudingTransactieStap.stopTransactie(bericht, context, false);
        verify(brpLocker).isLockNogAanwezig();
        verify(transactionManager).commit(any(TransactionStatus.class));
        brpLocker.unLock();
        assertThat(context.getResultaatId(), CoreMatchers.notNullValue());
    }

    @Test
    public void testRollbackFlowVeroorzaaktDoordatGeenLockAanwezigIs() throws BrpLockerExceptie {
        BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();

        context.setAdministratieveHandeling(maakAdministratieveHandelingMetId(123));
        AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        context.setTransactionStatus(status);
        bericht.setStuurgegevens(new BerichtStuurgegevensGroepBericht());
        bericht.setParameters(new BerichtParametersGroepBericht());
        bericht.getParameters().setVerwerkingswijze(null);
        when(brpLocker.isLockNogAanwezig()).thenReturn(Boolean.FALSE);
        bijhoudingTransactieStap.stopTransactie(bericht, context, false);
        verify(transactionManager).rollback(any(TransactionStatus.class));
        assertThat(context.getResultaatId(), CoreMatchers.nullValue());
    }

    private AdministratieveHandelingModel maakAdministratieveHandelingMetId(final int id) {
        AdministratieveHandelingModel administratieveHandeling = new AdministratieveHandelingModel(
                new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.DUMMY), null, null, null
        );
        ReflectionTestUtils.setField(administratieveHandeling, "iD", (long) id);
        return administratieveHandeling;
    }
}

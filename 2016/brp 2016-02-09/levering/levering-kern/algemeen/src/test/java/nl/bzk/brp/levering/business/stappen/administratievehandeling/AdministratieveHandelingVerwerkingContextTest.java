/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.administratievehandeling;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;


public class AdministratieveHandelingVerwerkingContextTest {

    @Test
    public final void testGettersEnSetters() {
        // given
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        // when
        context.setBijgehoudenPersoonIds(asList(1, 2, 3));
        context.setHuidigeAdministratieveHandeling(mock(AdministratieveHandelingModel.class));
        context.setBijgehoudenPersonenVolledig(asList(mock(PersoonHisVolledig.class)));

        context.setLeveringPopulatieMap(new HashMap<Leveringinformatie, Map<Integer, Populatie>>());

        context.setJmsTransactionStatus(mock(TransactionStatus.class));
        context.setTransactionStatus(mock(TransactionStatus.class));

        // then
        assertThat(context.getBijgehoudenPersoonIds(), contains(1, 2, 3));
        assertThat(context.getBijgehoudenPersonenVolledig(), notNullValue());
        assertThat(context.getHuidigeAdministratieveHandeling(), notNullValue());
        assertThat(context.getLeveringPopulatieMap(), notNullValue());
        assertThat(context.getJmsTransactionStatus(), notNullValue());
        assertThat(context.getTransactionStatus(), notNullValue());

        assertThat(context.getResultaatId(), nullValue());
        assertThat(context.getReferentieId(), notNullValue());
    }

    @Test
    public final void testGezetReferentieId() throws Exception {
        // given
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        final AdministratieveHandelingModel model = mock(AdministratieveHandelingModel.class);
        when(model.getID()).thenReturn(3L);

        context.setHuidigeAdministratieveHandeling(model);

        // when
        final Long refId = context.getReferentieId();

        // then
        assertThat(refId, is(3L));
    }

    @Test
    public final void testNietGezetReferentieId() throws Exception {
        // given
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        // when
        final Long refId = context.getReferentieId();

        // then
        assertThat(refId, is(nullValue()));
    }

    @Test(expected = IllegalStateException.class)
    public final void transactieOpruimenZonderTransacties() {
        // given
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();
        context.setJmsTransactionStatus(mock(TransactionStatus.class));
        context.setTransactionStatus(mock(TransactionStatus.class));

        // when
        context.clearBusinessTransactionStatus();
    }

    @Test
    public final void transactieOpruimenMetCompleteTransacties() {
        // given
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        final TransactionStatus jmsStatus = mock(TransactionStatus.class);
        when(jmsStatus.isCompleted()).thenReturn(Boolean.TRUE);
        context.setJmsTransactionStatus(jmsStatus);

        final TransactionStatus status = mock(TransactionStatus.class);
        when(status.isCompleted()).thenReturn(Boolean.TRUE);
        context.setTransactionStatus(status);

        // when
        context.clearBusinessTransactionStatus();

        // then
        assertThat(context.getJmsTransactionStatus(), nullValue());
        assertThat(context.getTransactionStatus(), nullValue());
    }

    @Test(expected = IllegalStateException.class)
    public final void testtransactieOpruimenHalfCompleet() throws Exception {
        // given
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        final TransactionStatus jmsStatus = mock(TransactionStatus.class);
        when(jmsStatus.isCompleted()).thenReturn(Boolean.FALSE);
        context.setJmsTransactionStatus(jmsStatus);

        final TransactionStatus status = mock(TransactionStatus.class);
        when(status.isCompleted()).thenReturn(Boolean.TRUE);
        context.setTransactionStatus(status);

        // when
        context.clearBusinessTransactionStatus();
    }

    @Test
    public final void testTransactieOpruimenStatussenNull() throws Exception {
        final AdministratieveHandelingVerwerkingContext context = new AdministratieveHandelingVerwerkingContextImpl();

        context.setJmsTransactionStatus(null);
        context.setTransactionStatus(null);

        context.clearBusinessTransactionStatus();

        Assert.assertNull(context.getTransactionStatus());
        Assert.assertNull(context.getJmsTransactionStatus());
    }
}

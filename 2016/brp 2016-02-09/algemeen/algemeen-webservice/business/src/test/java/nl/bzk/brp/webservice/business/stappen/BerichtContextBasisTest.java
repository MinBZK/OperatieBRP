/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractTransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;


/**
 * Unit test voor de {@link BerichtContextBasis} class.
 */
public class BerichtContextBasisTest {

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private AbstractBerichtContextBasis mockContext;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructorEnGetters() {
        final AbstractBerichtContextBasis context =
                getBerichtContextBasis(new BerichtenIds(2L, 3L), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                        "ref", null);
        assertNotNull(context.getPartij());
        assertEquals(2, context.getIngaandBerichtId());
        assertEquals(2, context.getReferentieId().longValue());
        assertEquals(3, context.getUitgaandBerichtId());
        assertNotNull(context.getTijdstipVerwerking());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullVoorBerichtenIds() {
        getBerichtContextBasis(null, StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref", null);
    }


    @Test
    public void zouTransactieStatusMoetenZetten() {
        final AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        mockContext.setTransactionStatus(status);
        Mockito.verify(mockContext).setTransactionStatus(Matchers.any(TransactionStatus.class));

    }


    @Test(expected = IllegalStateException.class)
    public void zouTransactieStatusNietMogenLeegmaken() {
        final AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        final AbstractBerichtContextBasis berichtContextBasis = getBerichtContextBasis(new BerichtenIds(123L, 456L), null, "123", null);
        berichtContextBasis.setTransactionStatus(status);
        Assert.assertNotNull(berichtContextBasis.getTransactionStatus());
        Assert.assertFalse(berichtContextBasis.getTransactionStatus().isCompleted());
        berichtContextBasis.clearBusinessTransactionStatus();
    }

    @Test
    public void transactieStatusMagLeegNaAfgerondeTransactie() {
        final AbstractTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();

        final AbstractBerichtContextBasis berichtContextBasis = getBerichtContextBasis(new BerichtenIds(123L, 456L), null, "123", null);

        berichtContextBasis.setTransactionStatus(status);

        Assert.assertNotNull(berichtContextBasis.getTransactionStatus());
        Assert.assertTrue(berichtContextBasis.getTransactionStatus().isCompleted());
        berichtContextBasis.clearBusinessTransactionStatus();
        Assert.assertNull(berichtContextBasis.getTransactionStatus());

    }

    private AbstractBerichtContextBasis getBerichtContextBasis(final BerichtenIds berichtIds, final Partij afzender,
            final String berichtReferentieNr, final CommunicatieIdMap identificeerbareObj)
    {
        return new AbstractBerichtContextBasis(berichtIds, afzender, berichtReferentieNr, identificeerbareObj) {

            @Override
            public LockingMode getLockingMode() {
                return LockingMode.SHARED;
            }

            @Override
            public Collection<Integer> getLockingIds() {
                return Arrays.asList(1, 2);
            }
        };
    }
}

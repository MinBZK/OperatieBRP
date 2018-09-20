/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.aut.AuthenticatieMiddel;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.TransactionStatus;


/**
 * Eenvoudige unit test class voor de {@link BerichtContext} class. Test eigenlijk alleen de getters en setters.
 */
public class BerichtContextTest {

    @Mock
    private TransactionStatus businessTransactionStatus;

    @Test
    public void testNullNaInitialisatie() {
        BerichtContext context = new BerichtContext();

        assertNull(context.getAbonnement());
        assertNull(context.getAbonnementId());
        assertNull(context.getAuthenticatieMiddel());
        assertNull(context.getAuthenticatieMiddelId());
        assertNull(context.getIngaandBerichtId());
        assertNull(context.getBusinessTransactionStatus());
        assertNull(context.getPartij());
        assertNull(context.getPartijId());
    }

    @Test
    public void testGettersEnSetters() {
        BerichtContext context = new BerichtContext();

        context.setIngaandBerichtId(2L);
        assertEquals(new Long(2), context.getIngaandBerichtId());

        context.setPartijId(3L);
        assertEquals(new Long(3), context.getPartijId());

        Partij partij = new Partij(SoortPartij.GEMEENTE);
        context.setPartij(partij);
        assertSame(partij, context.getPartij());

        context.setAbonnementId(4L);
        assertEquals(new Long(4), context.getAbonnementId());

        Abonnement abonnement = new Abonnement(null, SoortAbonnement.LEVERING);
        context.setAbonnement(abonnement);
        assertSame(abonnement, context.getAbonnement());

        context.setAuthenticatieMiddelId(5L);
        assertEquals(new Long(5), context.getAuthenticatieMiddelId());

        AuthenticatieMiddel authenticatieMiddel = new AuthenticatieMiddel(null, null);
        context.setAuthenticatieMiddel(authenticatieMiddel);
        assertSame(authenticatieMiddel, context.getAuthenticatieMiddel());

        context.setBusinessTransactionStatus(businessTransactionStatus);
        assertSame(businessTransactionStatus, context.getBusinessTransactionStatus());
    }

    @Test
    public void testClearBusinessTransactionStatus() {
        BerichtContext context = new BerichtContext();

        // Test met null als transactie  status
        context.setBusinessTransactionStatus(null);
        context.clearBusinessTransactionStatus();
        assertNull(context.getBusinessTransactionStatus());

        // Test met een completed transactie status
        Mockito.when(businessTransactionStatus.isCompleted()).thenReturn(true);
        context.setBusinessTransactionStatus(businessTransactionStatus);
        context.clearBusinessTransactionStatus();
        assertNull(context.getBusinessTransactionStatus());
    }

    @Test(expected = IllegalStateException.class)
    public void testClearBusinessTransactionStatusDieNietCompletedIs() {
        BerichtContext context = new BerichtContext();

        Mockito.when(businessTransactionStatus.isCompleted()).thenReturn(false);
        context.setBusinessTransactionStatus(businessTransactionStatus);
        context.clearBusinessTransactionStatus();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
}

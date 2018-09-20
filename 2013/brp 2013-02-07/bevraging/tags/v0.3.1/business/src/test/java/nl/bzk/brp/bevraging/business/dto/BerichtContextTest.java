/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import nl.bzk.brp.domein.DomeinObjectFactory;
import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.SoortPartij;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.SoortAbonnement;
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
    private TransactionStatus   businessTransactionStatus;

    private final DomeinObjectFactory domeinObjectFactory = new PersistentDomeinObjectFactory();

    @Test
    public void testNullNaInitialisatie() {
        BerichtContext context = new BerichtContext();

        assertNull(context.getAbonnement());
        assertNull(context.getAbonnementId());
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

        context.setPartijId(3);
        assertEquals(Integer.valueOf(3), context.getPartijId());

        Partij partij = domeinObjectFactory.createPartij();
        partij.setSoort(SoortPartij.GEMEENTE);
        context.setPartij(partij);
        assertSame(partij, context.getPartij());

        context.setAbonnementId(4);
        assertEquals(Integer.valueOf(4), context.getAbonnementId());

        Abonnement abonnement = domeinObjectFactory.createAbonnement();
        abonnement.setSoortAbonnement(SoortAbonnement.LEVERING);
        context.setAbonnement(abonnement);
        assertSame(abonnement, context.getAbonnement());

        context.setAuthenticatieMiddelId(5);
        assertEquals(Integer.valueOf(5), context.getAuthenticatieMiddelId());

        context.setBusinessTransactionStatus(businessTransactionStatus);
        assertSame(businessTransactionStatus, context.getBusinessTransactionStatus());
    }

    @Test
    public void testClearBusinessTransactionStatus() {
        BerichtContext context = new BerichtContext();

        // Test met null als transactie status
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

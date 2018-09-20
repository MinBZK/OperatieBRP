/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import nl.bzk.brp.blobifier.exceptie.PersoonCacheNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.junit.Before;
import org.junit.Test;

public class PersoonHisVolledigImplLuieLaderTest {
    private static final int PERSOON_ID = 100;

    private final BlobifierService blobifierService = mock(BlobifierService.class);

    private PersoonHisVolledigImplLuieLader persoonHisVolledigImplLuieLader;

    @Before
    public void setup() {
        persoonHisVolledigImplLuieLader = new PersoonHisVolledigImplLuieLader(blobifierService, PERSOON_ID);

        when(blobifierService.leesBlob(PERSOON_ID)).thenReturn(mock(PersoonHisVolledigImpl.class));
    }

    @Test
    public void testGetProxy() {
        final PersoonHisVolledigImpl persoonProxy = persoonHisVolledigImplLuieLader.getProxy();

        assertNotNull(persoonProxy);
        verifyZeroInteractions(blobifierService);
    }

    @Test
    public void testHaalLuiOpEenmalig() {
        final PersoonHisVolledigImpl persoonProxy = persoonHisVolledigImplLuieLader.getProxy();

        persoonProxy.getPersoonIdentificatienummersHistorie();

        verify(blobifierService).leesBlob(PERSOON_ID);
    }

    @Test
    public void testHaalEenmaalLuiOpBijTweemaligeAanroep() {
        final PersoonHisVolledigImpl persoonProxy = persoonHisVolledigImplLuieLader.getProxy();

        persoonProxy.getPersoonIdentificatienummersHistorie();
        persoonProxy.getPersoonIdentificatienummersHistorie();

        verify(blobifierService).leesBlob(PERSOON_ID);
    }

    @Test(expected = PersoonCacheNietAanwezigExceptie.class)
    public void testHaalLuiOpNietsGevonden() {
        when(blobifierService.leesBlob(PERSOON_ID)).thenReturn(null);
        final PersoonHisVolledigImpl persoonProxy = persoonHisVolledigImplLuieLader.getProxy();

        persoonProxy.getPersoonIdentificatienummersHistorie();

        verify(blobifierService).leesBlob(PERSOON_ID);
    }

    @Test
    public void testHaalNietsOpAlsIdWordtAangeroepen() {
        final PersoonHisVolledigImpl persoonProxy = persoonHisVolledigImplLuieLader.getProxy();

        final int resultaat = persoonProxy.getID();

        assertEquals(PERSOON_ID, resultaat);
        verifyZeroInteractions(blobifierService);
    }

    @Test
    public void testHaalNietsOpAlsFinalizeWordtAangeroepen() {
        persoonHisVolledigImplLuieLader.getProxy();

        System.gc();

        verifyZeroInteractions(blobifierService);
    }

}

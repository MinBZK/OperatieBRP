/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.blobifier.exceptie.AfnemerindicatiesNietAanwezigExceptie;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import org.junit.Before;
import org.junit.Test;

public class AfnemerindicatiesHisVolledigImplLuieLaderTest {
    private static final int PERSOON_ID = 100;

    private final AfnemerIndicatieBlobifierService blobifierService = mock(AfnemerIndicatieBlobifierService.class);

    private AfnemerindicatiesHisVolledigImplLuieLader afnemerindicatiesHisVolledigImplLuieLader;

    @Before
    public void setup() {
        afnemerindicatiesHisVolledigImplLuieLader = new AfnemerindicatiesHisVolledigImplLuieLader(blobifierService, PERSOON_ID);

        when(blobifierService.leesBlob(PERSOON_ID)).thenReturn(mock(HashSet.class));
    }

    @Test
    public void testGetProxy() {
        final Set<?> persoonProxy = afnemerindicatiesHisVolledigImplLuieLader.getProxy();

        assertNotNull(persoonProxy);
        verifyZeroInteractions(blobifierService);
    }

    @Test
    public void testHaalLuiOpEenmalig() {
        final Set<?> persoonProxy = afnemerindicatiesHisVolledigImplLuieLader.getProxy();

        persoonProxy.size();

        verify(blobifierService).leesBlob(PERSOON_ID);
    }

    @Test
    public void testHaalEenmaalLuiOpBijTweemaligeAanroep() {
        final Set<?> persoonProxy = afnemerindicatiesHisVolledigImplLuieLader.getProxy();

        persoonProxy.iterator();
        persoonProxy.iterator();

        verify(blobifierService).leesBlob(PERSOON_ID);
    }

    @Test(expected = AfnemerindicatiesNietAanwezigExceptie.class)
    public void testHaalLuiOpNietsGevonden() {
        when(blobifierService.leesBlob(PERSOON_ID)).thenReturn(null);
        final Set<?> persoonProxy = afnemerindicatiesHisVolledigImplLuieLader.getProxy();

        persoonProxy.isEmpty();

        verify(blobifierService).leesBlob(PERSOON_ID);
    }

    @Test
    public void testHaalNietsOpAlsFinalizeWordtAangeroepen() {
        afnemerindicatiesHisVolledigImplLuieLader.getProxy();

        System.gc();

        verifyZeroInteractions(blobifierService);
    }

}

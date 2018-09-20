/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import nl.bzk.brp.blobifier.exceptie.PersoonNietAanwezigExceptie;
import nl.bzk.brp.blobifier.repository.alleenlezen.LeesPersoonCacheRepository;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheStandaardGroepModel;
import nl.bzk.brp.serialisatie.persoon.impl.PersoonSerialiseerderImpl;
import nl.bzk.brp.vergrendeling.SleutelVergrendelaar;
import nl.bzk.brp.vergrendeling.VergrendelFout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class PersoonSerialiseerderTest {

    public static final String OVERSCHRIJF_BESTAANDE_CACHES = "overschrijfBestaandeCaches";
    @InjectMocks
    private PersoonSerialiseerderImpl serialiseerder;

    @Mock
    private LeesPersoonCacheRepository leesPersoonCacheRepository;

    @Mock
    private BlobifierService blobifierService;

    @Mock
    private SleutelVergrendelaar sleutelVergrendelaar;

    @Mock
    private PersoonCacheModel persoonCache;

    @Mock
    private PersoonHisVolledig persoonHisVolledig;

    @Before
    public void setup() {
        when(persoonCache.getStandaard()).thenReturn(mock(PersoonCacheStandaardGroepModel.class));
        when(persoonCache.getStandaard().getPersoonHistorieVolledigGegevens())
                .thenReturn(new ByteaopslagAttribuut(new byte[]{}));
    }

    @Test(expected = PersoonNietAanwezigExceptie.class)
    public void serialisatiePersoonHisVolledigIsNullFout() throws VergrendelFout {
        ReflectionTestUtils.setField(serialiseerder, OVERSCHRIJF_BESTAANDE_CACHES, true);

        doThrow(PersoonNietAanwezigExceptie.class).when(blobifierService).blobify(any(Integer.class), anyBoolean());

        serialiseerder.serialiseerPersoon(1);
    }

    @Test
    public void testSerialiseerPersoonGeenBestaandeCache() throws VergrendelFout {
        when(leesPersoonCacheRepository.haalPersoonCacheOp(anyInt())).thenReturn(null);
        ReflectionTestUtils.setField(serialiseerder, OVERSCHRIJF_BESTAANDE_CACHES, false);

        serialiseerder.serialiseerPersoon(1);

        verify(blobifierService).blobify(1, false);
    }

    @Test
    public void testSerialiseerPersoonBestaandeCacheOverschrijven() throws VergrendelFout {
        when(leesPersoonCacheRepository.haalPersoonCacheOp(anyInt())).thenReturn(persoonCache);
        ReflectionTestUtils.setField(serialiseerder, OVERSCHRIJF_BESTAANDE_CACHES, true);

        serialiseerder.serialiseerPersoon(1);

        verify(blobifierService).blobify(1, false);
    }

    @Test
    public void testSerialiseerPersoonBestaandeCacheOverslaan() throws VergrendelFout {
        when(leesPersoonCacheRepository.haalPersoonCacheOp(anyInt())).thenReturn(persoonCache);
        ReflectionTestUtils.setField(serialiseerder, OVERSCHRIJF_BESTAANDE_CACHES, false);

        serialiseerder.serialiseerPersoon(1);

        ///v/erify(persoonHisVolledigRepository, times(0)).opslaanInPersoonCache(persoonHisVolledig);
        //verify(blobifierService).blobify(1, false);
        verifyZeroInteractions(blobifierService);
    }
}

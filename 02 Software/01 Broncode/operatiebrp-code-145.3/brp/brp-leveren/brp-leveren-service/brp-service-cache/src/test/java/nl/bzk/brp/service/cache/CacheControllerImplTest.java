/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import static org.mockito.Mockito.verifyZeroInteractions;

import com.google.common.collect.Lists;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class CacheControllerImplTest {

    @Mock
    private PartijCache partijCache;
    @Mock
    private StamTabelCache stamTabelCache;
    @Mock
    private LeveringsAutorisatieCache leveringsAutorisatieCache;
    @Mock
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;
    @Mock
    private ApplicationContext applicationContext;
    @Mock
    private BrpCache brpCache;

    @InjectMocks
    private CacheControllerImpl cacheController;


    @Test
    public void testAllesHerladen() {

        ReflectionTestUtils.setField(cacheController, "stamtabelCacheEnabled", true);
        Mockito.when(partijCache.herlaad()).thenReturn(new CacheEntry("test", new PartijCacheImpl.Data(Lists.newArrayList())));

        cacheController.herlaadCaches();

        final InOrder inOrder = Mockito.inOrder(partijCache, stamTabelCache, leveringsAutorisatieCache, geldigeAttributenElementenCache);
        inOrder.verify(partijCache).herlaad();
        inOrder.verify(stamTabelCache).herlaad();
        inOrder.verify(leveringsAutorisatieCache).herlaad(Mockito.any());
        inOrder.verify(geldigeAttributenElementenCache).herlaad();
    }

    @Test
    public void testStamtabellenNietHerladen() {

        ReflectionTestUtils.setField(cacheController, "stamtabelCacheEnabled", false);
        Mockito.when(partijCache.herlaad()).thenReturn(new CacheEntry("test", new PartijCacheImpl.Data(Lists.newArrayList())));

        cacheController.herlaadCaches();

        final InOrder inOrder = Mockito.inOrder(partijCache, leveringsAutorisatieCache, geldigeAttributenElementenCache);
        inOrder.verify(partijCache).herlaad();
        inOrder.verify(leveringsAutorisatieCache).herlaad(Mockito.any());
        inOrder.verify(geldigeAttributenElementenCache).herlaad();

        Mockito.verifyZeroInteractions(stamTabelCache);
    }

    @Test
    public void testGeenVerversUitgvoerd() {
        ReflectionTestUtils.setField(cacheController, "laatsteCacheVerversTijd", new Date());
        ReflectionTestUtils.setField(cacheController, "minTijdsintervalSec", 100);

        Mockito.when(partijCache.herlaad()).thenReturn(new CacheEntry("test", new PartijCacheImpl.Data(Lists.newArrayList())));

        cacheController.herlaadCaches();

        verifyZeroInteractions(partijCache);
        verifyZeroInteractions(stamTabelCache);
        verifyZeroInteractions(leveringsAutorisatieCache);
        verifyZeroInteractions(geldigeAttributenElementenCache);
    }
}

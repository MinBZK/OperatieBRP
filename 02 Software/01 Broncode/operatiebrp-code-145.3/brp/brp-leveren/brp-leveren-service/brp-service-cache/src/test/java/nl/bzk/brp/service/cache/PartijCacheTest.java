/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.service.dalapi.PartijRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PartijCacheTest {

    @Mock
    private BrpCache brpCache;

    @Mock
    private PartijRepository partijRepository;

    @InjectMocks
    private PartijCacheImpl partijCache;

    private final static String partijIdEenCode = "000001";
    private final static String partijIdEenOin = "123";

    @Before
    public final void voorTest() {
        final List<Partij> partijen = new ArrayList<>();
        Partij partijEen = new Partij("een", partijIdEenCode);
        partijEen.addPartijRol(new PartijRol(partijEen, Rol.AFNEMER));
        partijEen.setId(Short.parseShort(partijIdEenCode));
        partijEen.setOin(partijIdEenOin);

        Partij partijTwee = new Partij("twee", "000002");
        partijTwee.addPartijRol(new PartijRol(partijTwee, Rol.BIJHOUDINGSORGAAN_MINISTER));
        partijTwee.setId((short) 2);
        partijen.add(partijEen);
        partijen.add(partijTwee);
        Mockito.when(partijRepository.get()).thenReturn(partijen);

        final CacheEntry cacheEntry = partijCache.herlaad();
        Mockito.when(brpCache.getCache(PartijCacheImpl.CACHE_NAAM)).thenReturn(cacheEntry.getData());

    }

    @Test
    public void testGeefPartijMetCode() {
        partijCache.herlaad();
        final Partij partij = partijCache.geefPartij(partijIdEenCode);
        Assert.assertNotNull(partij);
        Assert.assertEquals(partijIdEenCode, partij.getCode());
    }

    @Test
    public void testGeefPartijMetOin() {
        partijCache.herlaad();
        final Partij partij = partijCache.geefPartijMetOin(partijIdEenOin);
        Assert.assertNotNull(partij);
        Assert.assertEquals(partijIdEenOin, partij.getOin());
    }
}

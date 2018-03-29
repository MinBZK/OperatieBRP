/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;


/**
 * GeldigeAttributenElementenCacheImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class GeldigeAttributenElementenCacheImplTest {

    @Mock
    private BrpCache brpCache;

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private GeldigeAttributenElementenCacheImpl geldigeAttributenElementenCache;


    @Before
    public void beforeTest() {
        final CacheEntry cacheEntry = geldigeAttributenElementenCache.herlaad();
        Mockito.when(brpCache.getCache(GeldigeAttributenElementenCacheImpl.CACHE_NAAM)).thenReturn(cacheEntry.getData());
    }

    @Test
    public void testCacheGeldig() {
        final boolean
                geldig =
                geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER));
        Assert.assertTrue(geldig);
    }

    @Test
    public void testCacheOngeldigStructuur() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_OBJECTSLEUTEL);
        Assert.assertEquals(attribuutElement.getAutorisatie(), SoortElementAutorisatie.STRUCTUUR);
        final boolean geldig = geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(attribuutElement);
        Assert.assertFalse(geldig);
    }

    @Test
    public void testCacheOngeldigGroepsAutorisatie() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_TIJDSTIPREGISTRATIE);
        Assert.assertEquals(attribuutElement.getAutorisatie(), SoortElementAutorisatie.VIA_GROEPSAUTORISATIE);
        final boolean geldig = geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(attribuutElement);
        Assert.assertFalse(geldig);
    }

    @Test
    public void testCacheGeldigGroepsAutorisatie() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_TIJDSTIPREGISTRATIE);
        Assert.assertEquals(attribuutElement.getAutorisatie(), SoortElementAutorisatie.VIA_GROEPSAUTORISATIE);
        final boolean geldig = geldigeAttributenElementenCache.geldigVoorGroepAutorisatie(attribuutElement);
        Assert.assertTrue(geldig);
    }

    @Test
    public void testCacheOngeldigNietVerstrekken() {
        final AttribuutElement attribuutElement = ElementHelper.getAttribuutElement(Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE);
        Assert.assertEquals(attribuutElement.getAutorisatie(), SoortElementAutorisatie.NIET_VERSTREKKEN);
        final boolean geldig = geldigeAttributenElementenCache.geldigVoorAttribuutAutorisatie(
                attribuutElement);
        Assert.assertFalse(geldig);
    }
}

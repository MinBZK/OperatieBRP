/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.util.cache.DalCacheController;
import nl.bzk.brp.bijhouding.dal.AbstractRepositoryTest.PortInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:brp-bijhouding-dal-test.xml"}, initializers = {PortInitializer.class})
public class CacheControllerTest {

    private static final String NAAM_AANGEPAST = "Aangepast";

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;
    @Inject
    private DalCacheController cacheController;

    @Test
    public void testCacheControllerEnJmxSupport() {
        assertNotSame(NAAM_AANGEPAST, getCacheWaarde().getNaam());
        getCacheWaarde().setNaam(NAAM_AANGEPAST);
        assertEquals(NAAM_AANGEPAST, getCacheWaarde().getNaam());
        cacheController.maakCachesLeeg();
        assertNotSame(NAAM_AANGEPAST, getCacheWaarde().getNaam());
        getCacheWaarde().setNaam(NAAM_AANGEPAST);
        assertEquals(NAAM_AANGEPAST, getCacheWaarde().getNaam());
        cacheController.maakCachesLeeg();
        assertNotSame(NAAM_AANGEPAST, getCacheWaarde().getNaam());
    }

    private LandOfGebied getCacheWaarde() {
        return dynamischeStamtabelRepository.getLandOfGebiedByCode("6030");
    }
}

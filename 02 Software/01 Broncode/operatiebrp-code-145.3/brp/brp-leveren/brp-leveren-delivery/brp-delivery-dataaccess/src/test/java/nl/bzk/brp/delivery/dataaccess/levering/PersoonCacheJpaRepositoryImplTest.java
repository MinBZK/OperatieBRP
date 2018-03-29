/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import com.google.common.collect.Sets;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import org.junit.Assert;
import org.junit.Test;

/**
 * PersoonCacheJpaRepositoryImplTest.
 */
@Data(resources = {
        "classpath:/data/aut-lev.xml", "/data/dataset_zoekpersoon.xml"})
public class PersoonCacheJpaRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private PersoonCacheRepository persoonCacheJpaRepository;

    @Test
    public void testHaalPersoonCachesOp() {
        final List<PersoonCache> caches = persoonCacheJpaRepository.haalPersoonCachesOp(Sets.newHashSet(1L));
        Assert.assertNotNull(caches);
        Assert.assertEquals(1, caches.size());
    }

    @Test
    public void testHaalPersoonCachesOpLegeLijst() {
        final List<PersoonCache> caches = persoonCacheJpaRepository.haalPersoonCachesOp(Sets.newHashSet());
        Assert.assertNotNull(caches);
        Assert.assertTrue(caches.isEmpty());
    }

    @Test
    public void testUpdateAfnemerIndicaties() throws BlobException {
        persoonCacheJpaRepository.updateAfnemerindicatieBlob(1L, 1L, 1L);
        persoonCacheJpaRepository.updateAfnemerindicatieBlob(1L, 1L, 2L);
    }

    @Test
    public void testUpdateAfnemerIndicatiesInitieelNullScenario() throws BlobException {
        persoonCacheJpaRepository.updateAfnemerindicatieBlob(3L, 1L, null);
        persoonCacheJpaRepository.updateAfnemerindicatieBlob(3L, 1L, 1L);
    }

    @Test(expected = OptimisticLockException.class)
    public void testUpdateAfnemerIndicatiesOptimisticlockExceptionPersoon() throws BlobException {
        persoonCacheJpaRepository.updateAfnemerindicatieBlob(2L, 0L, 1L);
    }

    @Test(expected = OptimisticLockException.class)
    public void testUpdateAfnemerIndicatiesOptimisticlockExceptionAfnemerindicatie() throws BlobException {
        persoonCacheJpaRepository.updateAfnemerindicatieBlob(2L, 1L, 0L);
    }

    @Test
    public void testHaalPersoonCacheOp() {
        final PersoonCache cache = persoonCacheJpaRepository.haalPersoonCacheOp(1);
        Assert.assertNotNull(cache);
    }

    public void testHaalPersoonCacheOpGeenIdMatch() {
        final PersoonCache cache = persoonCacheJpaRepository.haalPersoonCacheOp(-1);
        Assert.assertNull(cache);
    }
}

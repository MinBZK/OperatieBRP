/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.selectie;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.brp.delivery.dataaccess.EmbeddedDatabaseConfiguration;
import nl.bzk.brp.service.selectie.lezer.MinMaxPersoonCacheDTO;
import nl.bzk.brp.service.selectie.lezer.PersoonCacheSelectieRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

/**
 * PersoonCacheSelectieJpaRepositoryImplTest.
 */
@ContextConfiguration(classes = AbstractDataAccessTest.DataAccessTestConfiguratie.class)
@Data(resources = {
        "classpath:/data/dataset.xml"})
public class PersoonCacheSelectieJpaRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private PersoonCacheSelectieRepository persoonCacheSelectieRepository;

    @Test
    public void testMinMax() {
        final MinMaxPersoonCacheDTO minMaxPersoonCacheDTO = persoonCacheSelectieRepository.selecteerMinMaxIdVoorSelectie();
        Assert.assertEquals(1, minMaxPersoonCacheDTO.getMinId());
        Assert.assertEquals(10, minMaxPersoonCacheDTO.getMaxId());
    }

    @Test
    public void testHaalPersoonCachesOp() {
        //6 geldige personen in testset
        final List<PersoonCache> persoonCaches = persoonCacheSelectieRepository.haalPersoonCachesOp(1, 100);
        Assert.assertEquals(6, persoonCaches.size());
    }

    @Test
    public void testHaalPersoonCachesOpOpGrens() {
        //4 geldige maar bovengrens in exclusief
        final List<PersoonCache> persoonCaches = persoonCacheSelectieRepository.haalPersoonCachesOp(1, 4);
        Assert.assertEquals(3, persoonCaches.size());
    }

    @Test
    public void testHaalPersoonCachesOpOpGrensPlus1() {
        //4 geldige maar bovengrens in exclusief
        final List<PersoonCache> persoonCaches = persoonCacheSelectieRepository.haalPersoonCachesOp(1, 5);
        Assert.assertEquals(4, persoonCaches.size());
    }


    @Configuration
    @Import(EmbeddedDatabaseConfiguration.class)
    @ImportResource(value = {"classpath:delivery-selectie-dataaccess-beans.xml", "classpath:delivery-selectie-datasource-context.xml"})
    public static class SelectieSchrijverTestConfiguratie {
    }
}

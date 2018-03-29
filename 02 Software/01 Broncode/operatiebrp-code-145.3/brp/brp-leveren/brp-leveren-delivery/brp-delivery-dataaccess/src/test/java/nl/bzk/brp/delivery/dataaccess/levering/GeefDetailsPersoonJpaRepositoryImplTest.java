/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.GeefDetailsPersoonRepository;
import org.junit.Assert;
import org.junit.Test;

/**
 * GeefDetailsPersoonJpaRepositoryImplTest.
 */
@Data(resources = {
        "classpath:/data/aut-lev.xml",
        "classpath:/data/dataset_zoekpersoon.xml"})
public class GeefDetailsPersoonJpaRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private GeefDetailsPersoonRepository geefDetailsPersoonRepository;

    @Test
    public void testZoekIdsPersoonMetBsn() {
        final String bsn = "402533928";
        final List<Long> persoonIds = geefDetailsPersoonRepository.zoekIdsPersoonMetBsn(bsn);
        Assert.assertEquals(1, persoonIds.size());
    }

    @Test
    public void testZoekIdsPersoonMetNietBestaandBsn() {
        final String bsn = "-402533928";
        final List<Long> persoonIds = geefDetailsPersoonRepository.zoekIdsPersoonMetBsn(bsn);
        Assert.assertTrue(persoonIds.isEmpty());
    }

    @Test
    public void testZoekIdsPersoonMetAnummer() {
        final String anr = "1268046023";
        final List<Long> persoonIds = geefDetailsPersoonRepository.zoekIdsPersoonMetAnummer(anr);
        Assert.assertEquals(1, persoonIds.size());
    }
}

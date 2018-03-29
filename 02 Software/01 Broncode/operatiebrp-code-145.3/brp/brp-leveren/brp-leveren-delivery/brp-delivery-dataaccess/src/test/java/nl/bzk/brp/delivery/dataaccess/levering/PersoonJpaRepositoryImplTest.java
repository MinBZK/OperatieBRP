/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.brp.delivery.dataaccess.AbstractDataAccessTest;
import nl.bzk.algemeenbrp.test.dal.data.Data;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import org.junit.Assert;
import org.junit.Test;

/**
 * PersoonJpaRepositoryImplTest.
 */
@Data(resources = {
        "classpath:/data/dataset_zoekpersoon.xml"})
public class PersoonJpaRepositoryImplTest extends AbstractDataAccessTest {

    @Inject
    private PersoonRepository persoonJpaRepository;

    @Test
    public void testHaalPersoonOp() {
        final Persoon persoon = persoonJpaRepository.haalPersoonOp(1);
        Assert.assertNotNull(persoon);
    }
}

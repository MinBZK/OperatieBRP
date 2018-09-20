/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.consistency.test;

import javax.inject.Inject;

import nl.bzk.brp.metaregister.consistency.BmrDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/consistency-context.xml" })
@TransactionConfiguration
@Transactional
public class AlgemeneSanityTest {

    @Inject
    private BmrDao bmrDao;

    @Test
    public void testInhoudAanwezig() {
        Assert.assertFalse(this.bmrDao.getObjectTypen().isEmpty());
        Assert.assertFalse(this.bmrDao.getAttribuutTypen().isEmpty());
    }

}

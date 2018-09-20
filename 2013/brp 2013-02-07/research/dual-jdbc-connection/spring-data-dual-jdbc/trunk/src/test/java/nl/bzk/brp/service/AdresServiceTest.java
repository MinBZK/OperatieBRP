/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service;

import junit.framework.Assert;
import nl.bzk.brp.model.Adres;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AdresServiceTest {

    @Inject
    private AdresService adresService;

    @Test
    public void testAdres() {
        Adres adres = adresService.create("Haya van somerenlaan");

        Assert.assertNotNull(adres);
        Assert.assertNotSame(0L, adres.getId());
    }


}

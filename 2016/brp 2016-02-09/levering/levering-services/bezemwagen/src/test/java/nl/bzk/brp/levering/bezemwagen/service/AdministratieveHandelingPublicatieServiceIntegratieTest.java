/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.bezemwagen.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdministratieveHandelingPublicatieServiceIntegratieTest extends AbstractIntegratieTest {

    @Autowired
    private AdministratieveHandelingPublicatieService administratieveHandelingPublicatieService;

    @Test
    public void zetAantalAdministratieveHandelingenPerKeerProperty() {
        final Integer handelingenPerKeer = (Integer) ReflectionTestUtils
            .getField(administratieveHandelingPublicatieService, "aantalAdministratieveHandelingenPerKeer");

        assertThat(handelingenPerKeer, is(10));
    }
}

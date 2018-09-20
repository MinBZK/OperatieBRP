/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service;

import nl.bzk.brp.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.fail;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PersonServiceTest extends AbstractJUnit4SpringContextTests {

        @Inject
        private PersonService personService;

        private Person nieuwPersoon;

        @Before
        public void setUp() {
            nieuwPersoon = Person.getBuilder("Mike", "van Vendeloo").build();
        }

        @Test
        public void create() {

            Person persoon = personService.create(nieuwPersoon);

            Assert.assertNotSame(0L, persoon.getId());
        }

    @Test(expected = InvalidDataAccessResourceUsageException.class)
    public void createAnother() {

        //DataSourceContextHolder.setDataSourceType(AvailableDataSourceTypes.WRITE);
        Person persoon = personService.create(nieuwPersoon);

        fail("Persoon kan niet zijn aangemaakt in tweede datasource");
    }

}
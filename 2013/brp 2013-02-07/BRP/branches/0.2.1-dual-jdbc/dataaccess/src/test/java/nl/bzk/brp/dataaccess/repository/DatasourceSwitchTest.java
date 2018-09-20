/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.AvailableDataSourceTypes;
import nl.bzk.brp.dataaccess.DataSourceContextHolder;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 *
 */
@Ignore
public class DatasourceSwitchTest extends AbstractRepositoryTestCase {
    private static Logger LOGGER = LoggerFactory.getLogger(DatasourceSwitchTest.class);

    @Inject
    private PersoonRepository repository;

    @Test
     // Deze faalt omdat de switch niet goed gaat. Als ie naar een lege database wijst, wat de bedoeling is, dan vind hij niets
    public void testDefaultConnectie() {

        DataSourceContextHolder.setDataSourceType(AvailableDataSourceTypes.DEFAULT);

        PersoonModel persoon = repository.findByBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertNull(persoon);
        Assert.assertEquals(1, persoon.getId().intValue());
    }

    @Test
    public void testReadWriteConnection() {
        DataSourceContextHolder.setDataSourceType(AvailableDataSourceTypes.WRITE);

        PersoonModel persoon = repository.findByBurgerservicenummer(new Burgerservicenummer("123456789"));
        Assert.assertNotNull(persoon);
        Assert.assertEquals(1, persoon.getId().intValue());
     }

}

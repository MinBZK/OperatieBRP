/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.test.dal.data.DBUnitLoaderTestExecutionListener;
import nl.bzk.algemeenbrp.test.dal.data.DataSourceProvider;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@Rollback()
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional("masterTransactionManager")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestExecutionListeners({DBUnitLoaderTestExecutionListener.class})
@ContextConfiguration(classes = AbstractDataAccessTest.DataAccessTestConfiguratie.class)
public abstract class AbstractDataAccessTest extends AbstractTransactionalJUnit4SpringContextTests implements DataSourceProvider {

    private DataSource dataSource;

    @Override
    @Inject
    @Named("masterDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public final DataSource getDataSource() {
        return this.dataSource;
    }


    @Configuration
    @ComponentScan(value = {"nl.bzk.brp.delivery.dataaccess"})
    @Import(TestPropertiesConfiguration.class)
    @ImportResource(value = {"config/test-embedded-database.xml", "/config/brp-dal-test-datasource.xml"})
    public static class DataAccessTestConfiguratie {
    }
}

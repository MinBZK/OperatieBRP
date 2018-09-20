/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import nl.bzk.brp.dataaccess.test.DBUnitLoaderTestExecutionListener;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.dataaccess.test.DataSourceProvider;


/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration(locations = { "/config/test-context.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional(transactionManager = "lezenSchrijvenTransactionManager")
@Rollback
@TestExecutionListeners(DBUnitLoaderTestExecutionListener.class)
@Data(resources = {
        "classpath:/data/kern.xml",
        "classpath:/data/autaut.xml",
        "classpath:/data/ist.xml" })
public abstract class AbstractIntegratieTest extends AbstractTransactionalJUnit4SpringContextTests implements DataSourceProvider {

    private DataSource dataSource;

    @Override
    @Inject
    @Named("lezenSchrijvenDataSource")
    public void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public final DataSource getDataSource() {
        return this.dataSource;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.test;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.dataaccess.test.DataSourceProvider;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstracte klasse, die rekening houdt met het laden van DBUnit data, in transactionele testen.
 */
@ContextConfiguration(locations = {"/config/test-context.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional(transactionManager = "lezenSchrijvenTransactionManager")
@Rollback
@TestExecutionListeners(DBUnitLoaderTestExecutionListener.class)
@Data(resources = {"classpath:/data/stamgegevensStatisch.xml",
        "classpath:/data/blob/statisch.xml",
        "classpath:/data/blob/dataset.xml",
        "classpath:/data/blob/cleanup.xml" })
public abstract class AbstractDBUnitIntegratieTest extends AbstractTransactionalJUnit4SpringContextTests implements DataSourceProvider {

    private DataSource dataSource;

    @Override
    @Inject
    @Named("lezenSchrijvenDataSource")
    public final void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.dataaccess.test.DBUnitLoaderTestExecutionListener;
import nl.bzk.brp.dataaccess.test.Data;
import nl.bzk.brp.dataaccess.test.DataSourceProvider;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration(locations = {"/nl/bzk/brp/dataaccess/AbstractRepositoryTestCase-context.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Transactional(transactionManager = "lezenSchrijvenTransactionManager")
@Rollback
@TestExecutionListeners(DBUnitLoaderTestExecutionListener.class)
@Data(resources = {
        "classpath:/data/stamgegevensStatisch.xml",
        "classpath:/data/stamgegevensLandGebied.xml",
        "classpath:/data/stamgegevensNationaliteit.xml",
        "classpath:/data/testdata.xml",
        "classpath:/data/testdata-autaut.xml",
        // Leegt de conversie tabellen met stamgegevens (vanwege foreign keys)
        // Let op: het bestand heet 'pre-', maar staat aan het eind, vanwege de dbunit afhandelvolgorde.
        "classpath:/data/pre-conv-clean.xml" })
public abstract class AbstractRepositoryTestCase extends AbstractTransactionalJUnit4SpringContextTests implements
        DataSourceProvider
{

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

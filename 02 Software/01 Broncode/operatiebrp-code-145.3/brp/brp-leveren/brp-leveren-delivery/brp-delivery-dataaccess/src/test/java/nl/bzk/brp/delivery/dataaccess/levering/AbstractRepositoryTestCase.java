/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.delivery.dataaccess.levering;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.sql.DataSource;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.transaction.annotation.Transactional;
//
//
///**
// * Abstracte superclass voor repository (persistence) testcases.
// */
//@ContextConfiguration(locations = {"classpath:config/test-context.xml"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
//@Transactional(transactionManager = "masterTransactionManager")
//@Rollback
//@TestExecutionListeners(DBUnitLoaderTestExecutionListener.class)
//@Data(resources = {"classpath:/data/testdata-autaut.xml"})
//public abstract class AbstractRepositoryTestCase extends AbstractTransactionalJUnit4SpringContextTests implements
//        DataSourceProvider {
//
//    private DataSource dataSource;
//
//    @Override
//    @Inject
//    @Named("masterDatasource")
//    public final void setDataSource(final DataSource dataSource) {
//        super.setDataSource(dataSource);
//        this.dataSource = dataSource;
//    }
//
//    @Override
//    public final DataSource getDataSource() {
//        return dataSource;
//    }
//}

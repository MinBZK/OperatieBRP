/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.sql.SQLException;
import nl.bzk.algemeenbrp.test.dal.DBUnit;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration(locations = {"/config/gba-algemeen-test-context.xml"})
@Rollback(value = false)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(transactionManager = "masterTransactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractIntegratieTest {

    @Before
    public void before() throws SQLException, DatabaseUnitException {
        DBUnit.setInMemory();
        final IDatabaseConnection connection = DBUnit.createConnection();
        DBUnit.insertBefore(connection);
        connection.close();
    }

    @AfterTransaction
    public void afterTransaction() throws DatabaseUnitException, SQLException {
        DBUnit.verify();
    }
}

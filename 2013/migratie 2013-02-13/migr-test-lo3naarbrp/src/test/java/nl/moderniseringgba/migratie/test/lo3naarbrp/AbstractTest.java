/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.lo3naarbrp;

import java.sql.SQLException;

import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit;

import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@TransactionConfiguration(defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@ContextConfiguration("classpath:test-beans.xml")
public abstract class AbstractTest {

    /**
     * De DBUnit verify() dient maar een keer uitgevoerd te worden. Of tijdens de {@linkplain @After}, of tijdens de
     * {@linkplain @AfterTransaction}. Dit laatste is nodig omdat dan zeker is dat de transactie is gecommit.
     */
    private boolean transactional;

    @Before
    public void before() throws SQLException, DatabaseUnitException {
        DBUnit.resetSequences();
        DBUnit.truncateTables();
        DBUnit.insertBefore();
    }

    @After
    public void after() throws DatabaseUnitException, SQLException {
        if (!transactional) {
            DBUnit.verify();
        }
    }

    @BeforeTransaction
    public void beforeTransaction() {
        transactional = true;
    }

    @AfterTransaction
    public void afterTransaction() throws DatabaseUnitException, SQLException {
        DBUnit.verify();
        transactional = false;
    }
}

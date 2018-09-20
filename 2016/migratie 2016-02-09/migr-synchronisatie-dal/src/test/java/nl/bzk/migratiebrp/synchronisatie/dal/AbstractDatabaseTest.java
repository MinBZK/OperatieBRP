/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal;

import java.sql.SQLException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;

import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;

/**
 * Abstract supertype voor alle testen die een Spring-context nodig hebben. Hierdoor staat de verwijzing naar de
 * spring-test-context xml-configuratie op een plek.
 *
 *
 */
@Rollback(value = false)
@Transactional(transactionManager = "syncDalTransactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DBUnit.TestExecutionListener.class, TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = {"classpath:synchronisatie-beans-test.xml" })
public abstract class AbstractDatabaseTest {


    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    protected EntityManager em;

    /**
     * De DBUnit verify() dient maar een keer uitgevoerd te worden. Of tijdens de {@linkplain @After}, of tijdens de
     * {@linkplain @AfterTransaction}. Dit laatste is nodig omdat dan zeker is dat de transactie is gecommit.
     */
    private boolean transactional;
    private boolean rolledback;

    @Inject()
    @Named("syncDalTransactionManager")
    private JtaTransactionManager syncDalTransactionManager;

    @Before
    public void before() throws SQLException, DatabaseUnitException {
        DBUnit.setInMemory();
        final IDatabaseConnection connection = DBUnit.createConnection();
        DBUnit.truncateTables(connection);
        DBUnit.resetSequences(connection);
        DBUnit.insertBefore(connection);
        DBUnit.setStamgegevensSequences(connection);
        connection.close();
        final Session s = (Session) em.getDelegate();
        final SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictQueryRegions();
    }

    @After
    public void after() throws DatabaseUnitException, SQLException, SystemException {
        if (!transactional) {
            DBUnit.verify();
        } else {
            if (syncDalTransactionManager.getTransaction(null).isRollbackOnly()) {
                // stop het uitvoeren van AfterTransaction zodat de foutmelding die tot de rollback heeft geleid niet
                // wordt ingeslikt
                rolledback = true;
            }
        }
    }

    @BeforeTransaction
    public void beforeTransaction() {
        transactional = true;
    }

    @AfterTransaction
    public void afterTransaction() throws DatabaseUnitException, SQLException, SystemException {
        if (!rolledback) {
            DBUnit.verify();
        }
        transactional = false;
    }
}

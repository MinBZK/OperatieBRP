/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.lezenschrijven;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.levering.dataaccess.AbstractIntegratieTest;
import nl.bzk.brp.levering.dataaccess.repository.lezenenschrijven.AdministratieveHandelingVergrendelRepository;
import nl.bzk.brp.utils.junit.OverslaanBijInMemoryDatabase;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@Transactional(transactionManager = "lezenSchrijvenTransactionManager")
@Rollback
@Category(OverslaanBijInMemoryDatabase.class)
public class AdministratieveHandelingVergrendelRepositoryIntegratieTest extends AbstractIntegratieTest {

    private static final long ADMINISTRATIEVE_HANDELING_ID = 501L;

    @Autowired
    private AdministratieveHandelingVergrendelRepository administratieveHandelingLockRepository;

    private final QueryRunner queryRunner = new QueryRunner();

    private Connection connectie;

    @Override
    @Autowired
    @Named("dataSourceMasterLockTest")
    public final void setDataSource(final DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Before
    public final void setup() throws SQLException {
        connectie = DataSourceUtils.getConnection(getDataSource());
        connectie.setAutoCommit(false);
    }

    @Test
    @Transactional
    public final void testLockAdministratieveHandeling() throws SQLException {
        final boolean resultaat =
            administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);

        assertTrue(resultaat);
    }

    @Test(expected = SQLException.class)
    @Transactional
    public final void testLockAdministratieveHandelingTweeKeerMetDaardoorExceptie() throws SQLException {
        final boolean resultaat =
            administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);
        assertTrue(resultaat);

        tryLockFromSql();
    }

    @Test
    @Transactional
    public final void testLockAdministratieveHandelingTweeKeerMetDaardoorNegatiefResultaat() throws SQLException {
        tryLockFromSql();

        final boolean resultaat =
            administratieveHandelingLockRepository.vergrendelAlsNogNietIsVerwerkt(ADMINISTRATIEVE_HANDELING_ID);
        assertFalse(resultaat);
    }

    /**
     * Probeert via een 2e datasource een lock te leggen via een native query.
     *
     * @throws java.sql.SQLException Als de lock niet gelegd kan worden.
     */
    private void tryLockFromSql() throws SQLException {
        queryRunner.query(connectie, "SELECT true FROM kern.admhnd ah WHERE ah.id = ? FOR UPDATE NOWAIT",
            new ScalarHandler(), ADMINISTRATIEVE_HANDELING_ID);
    }

}

/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:isc-opschoner.xml", "classpath:test-datasource.xml"})
// @Transactional(transactionManager = "opschonerTransactionManager")
// @Rollback(true)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractDaoTest {

    @Inject
    @Named("opschonerDataSource")
    private DataSource datasource;

    /**
     * Geef de waarde van connection.
     * @return connection
     * @throws SQLException the SQL exception
     */
    protected Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    /**
     * Geeft het resultaat van de query terug. De methode gaat er vanuit dat de query het verwachte
     * class type teruggeeft.
     * @param query De count query.
     * @param <T> Type object dat terug wordt verwacht.
     * @return Het resultaat van de query.
     * @throws SQLException In het geval iets mis gaat.
     */
    protected <T> T geefQueryResultaat(final String query, final Class<T> clazz) throws SQLException {
        try (final ResultSet resultSet = getConnection().createStatement().executeQuery(query)) {
            if (resultSet.next() == false) {
                // resultSet is after last row, so result didn't contain any records
                return null;
            } else {
                final T resultaat = clazz.cast(resultSet.getObject(1));
                return resultaat;
            }
        }
    }

    /**
     * Geeft het lijst resultaat van de query terug. De methode gaat er vanuit dat de query een
     * lijst van het verwachte class type teruggeeft.
     * @param query De count query.
     * @param <T> Type object dat als lijst terug wordt verwacht.
     * @return Het resultaat van de query.
     * @throws SQLException In het geval iets mis gaat.
     */
    protected <T> List<T> geefQueryLijstResultaat(final String query, final Class<T> clazz) throws SQLException {

        final List<T> resultaat = new ArrayList<>();

        try (final ResultSet resultSet = getConnection().createStatement().executeQuery(query)) {
            while (resultSet.next()) {
                resultaat.add(clazz.cast(resultSet.getObject(1)));
            }
        }

        return resultaat;
    }

    protected boolean voerInsertQueryUit(final String query, final int verwachtAantalInserts) throws SQLException {
        return verwachtAantalInserts == getConnection().createStatement().executeUpdate(query);
    }

    protected boolean voerUpdateQueryUit(final String query, final int verwachtAantalUpdates) throws SQLException {
        return verwachtAantalUpdates == getConnection().createStatement().executeUpdate(query);
    }
}

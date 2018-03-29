/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

/**
 * Hibernate interceptor voor test de {@link PersoonCache} en {@link Persoon} entiteiten aan te passen nadat deze zijn geladen.
 */
@Component
public final class OptimisticLockingTestInterceptor extends EmptyInterceptor {

    @Inject
    private DataSource masterDataSource;

    @Override
    public boolean onLoad(final Object entity, final Serializable id, final Object[] state, final String[] propertyNames, final Type[] types) {
        if (entity instanceof PersoonCache && "true".equals(System.getProperty("pasPersoonCacheAan"))) {
            updatePersoonCache();
        }
        if (entity instanceof Persoon && "true".equals(System.getProperty("pasPersoonAan"))) {
            updatePersoon();
        }
        return super.onLoad(entity, id, state, propertyNames, types);
    }

    private void updatePersoonCache() {
        try {
            final Connection connection = masterDataSource.getConnection();
            final Statement statement = connection.createStatement();
            statement.execute("update kern.perscache set lockversieafnemerindicatiege=123456 where id=1");
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePersoon() {
        try {
            final Connection connection = masterDataSource.getConnection();
            final Statement statement = connection.createStatement();
            statement.execute("update kern.pers set lockVersie=123456 where id=5000020");
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

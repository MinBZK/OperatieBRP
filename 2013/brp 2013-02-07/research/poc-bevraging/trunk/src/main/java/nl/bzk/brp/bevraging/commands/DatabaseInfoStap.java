/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.persister.collection.AbstractCollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

/**
 * Stap die informatie uit de database haalt.
 */
@Component
public class DatabaseInfoStap implements Command {

    @Inject
    private EntityManagerFactory entityManagerFactory;

    @Override
    public boolean execute(final Context context) throws Exception {
        HibernateEntityManagerFactory hbmanagerfactory = (HibernateEntityManagerFactory) entityManagerFactory;
        SessionFactory sessionFactory = hbmanagerfactory.getSessionFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        // connection info
        Map<String, Object> info = sessionFactory.openSession().doReturningWork(new ConnectionInfo());

        // cache info
        List<String> cachedEntities = new ArrayList<String>();

        Map classMetadata = sessionFactory.getAllClassMetadata();
        for (Object ep : classMetadata.values()) {
            if (((EntityPersister) ep).hasCache()) {
                cachedEntities.add(((EntityPersister) ep).getEntityName());
            }
        }

        classMetadata = sessionFactory.getAllCollectionMetadata();
        for (Object ep : classMetadata.values()) {
            if (((AbstractCollectionPersister) ep).hasCache()) {
                cachedEntities.add(((AbstractCollectionPersister) ep).getName());
            }
        }
        info.put("cachedEntities", cachedEntities);

        // persoon count
        TypedQuery<Long> tQuery = em.createQuery("SELECT count(p) FROM PersoonModel p", Long.class);
        Long persoonCount = tQuery.getSingleResult();
        info.put("persoonModelCount", persoonCount);

        context.put(ContextParameterNames.DATABASE_INFO, info);

        return false;
    }

    /**
     * Geeft connectie informatie.
     */
    private class ConnectionInfo implements ReturningWork<Map<String, Object>> {
        @Override
        public Map<String, Object> execute(final Connection connection) throws SQLException {
            Map<String, Object> result = new HashMap<String, Object>();

            String dataBaseUrl = connection.getMetaData().getURL();
            String dataBaseProductName = connection.getMetaData().getDatabaseProductName();
            String driverName = connection.getMetaData().getDriverName();

            result.put("dataBaseUrl", dataBaseUrl);
            result.put("dataBaseProductName", dataBaseProductName);
            result.put("driverName", driverName);

            return result;
        }
    }
}

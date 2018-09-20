/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.repository.standard;

import nl.bzk.brp.model.Adres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Map;

/**
 *
 */
@Repository
public class AdresRepositoryImpl implements AdresRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdresRepositoryImpl.class);

    @PersistenceContext( unitName = "write")
    private EntityManager entityManager;


    @Override
    public Adres create(String straat) {
        //LOGGER.debug(Arrays.toString(entityManager.getEntityManagerFactory().getProperties().keySet().toArray()));
        logConnectionInfo();
        Adres adres = new Adres(straat);
        entityManager.persist(adres);
        LOGGER.debug("Adres toegevoegd:" + adres);
        return adres;
    }

    private void logConnectionInfo() {
        Map<String, Object> properties = entityManager.getEntityManagerFactory().getProperties();
        String connectionUrl = (String) properties.get("hibernate.connection.url");
        String connectionUserName = (String) properties.get("hibernate.connection.username");
        DataSource dataSource= (DataSource) properties.get("hibernate.connection.datasource");

        LOGGER.info("Database connectie: \n\tdatasource:" + dataSource + "\n\tconnectie:" + connectionUrl + "\n\tusername:" + connectionUserName);
    }
}

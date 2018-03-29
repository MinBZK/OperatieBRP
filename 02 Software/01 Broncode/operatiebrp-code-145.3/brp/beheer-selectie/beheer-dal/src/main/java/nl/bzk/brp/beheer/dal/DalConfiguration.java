/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * DAL Configuratie.
 */
@Configuration
@EnableTransactionManagement
@EntityScan("nl.bzk.algemeenbrp.dal.domein")
public class DalConfiguration {

    /**
     * HSQLDB database manager voor dev.
     * @param dataSourceUrl datasource url
     * @param userName username
     * @return null
     */
    @Bean
    @Profile("utils")
    public Void dbManagerSetup(@Value("${spring.datasource.url}") String dataSourceUrl,
                               @Value("${spring.datasource.username}") String userName) {
        DatabaseManagerSwing.main(
                new String[] { "--url", dataSourceUrl, "--user", userName, "--password", ""});
        return null;
    }
}

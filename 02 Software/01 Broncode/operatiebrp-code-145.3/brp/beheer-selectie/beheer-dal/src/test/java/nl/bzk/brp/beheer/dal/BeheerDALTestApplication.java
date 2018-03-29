/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot starter voor DAL tests.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class BeheerDALTestApplication {

    /**
     * Main method.
     * @param args application parameters
     */
    public static void main(String[] args) {
        SpringApplication.run(BeheerDALTestApplication.class, args);
    }
}

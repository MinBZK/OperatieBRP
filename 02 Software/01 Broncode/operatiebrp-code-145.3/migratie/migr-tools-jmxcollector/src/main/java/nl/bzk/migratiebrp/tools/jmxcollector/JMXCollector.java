/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hoofdclass voor het verzamelen van metingen van gedockerde applicaties.
 */
@SpringBootApplication
@EnableScheduling
public class JMXCollector {

    /**
     * Start van de applicatie.
     * @param args commandline argumenten
     */
    public static void main(final String[] args) {
        SpringApplication.run(JMXCollector.class, args);
    }

}

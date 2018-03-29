/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Interface voor printen van een logfile in de docker.
 */
public interface LogfileAware extends Docker {

    Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Voert de logging uit.
     */
    default void doLog() {
        final List<String> commandList = getOmgeving().getDockerCommandList("exec", getDockerContainerId(), "tail", "-n",
                System.getProperty("brp.e2e.error.log.aantalregels", "50"), logFile());
        final String output = ProcessHelper.DEFAULT.startProces(commandList).geefOutput();
        LOGGER.warn("\n\n\n\n\n");
        LOGGER.warn("**************************************");
        LOGGER.warn("LOG van {}", getLogischeNaam());
        LOGGER.warn(output);
        LOGGER.warn("**************************************");
    }

    /**
     * @return de logfile binnen de docker welke relevant is om te tonen in het geval van een error.
     */
    default String logFile() {
        return "logs/systeem.log";
    }
}

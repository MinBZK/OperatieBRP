/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.jta.util;

import java.io.File;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

/**
 * Directory creator.
 */
public class DirectoryCreator implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String directory;

    /**
     * Set directory to create.
     *
     * @param directory directory
     */
    @Required
    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    /**
     * Create the directory.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (new File(directory).mkdirs()) {
            LOGGER.info("Created directory: {}" + directory);

        }
    }
}

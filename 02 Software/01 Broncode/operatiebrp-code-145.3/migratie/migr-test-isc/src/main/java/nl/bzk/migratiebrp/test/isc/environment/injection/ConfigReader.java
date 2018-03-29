/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Configuratie lezer.
 */
public interface ConfigReader {

    /**
     * Lees de configuratie.
     * @param file configuratie bestand
     * @return lijst van configuratie regels
     * @throws IOException bij lees fouten
     */
    List<Config> readConfig(File file) throws IOException;
}

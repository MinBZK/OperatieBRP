/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.proefsynchronisatie.service;

import java.util.Properties;

/**
 * Interface voor de proefsynchronisatie service.
 */
public interface ProefSynchronisatieService {

    /**
     * Vult de proefsynchronisatiebericht tabel met de te verwerken berichten.
     *
     * @param config
     *            De configuratie.
     */
    void laadInitProefSynchronisatieBerichtenTabel(Properties config);

    /**
     * Verstuurt de te verwerken berichten via JMS zodat deze worden verwerkt door ISC.
     *
     * @param config
     *            De configuratie.
     */
    void voerProefSynchronisatieUit(Properties config);

}

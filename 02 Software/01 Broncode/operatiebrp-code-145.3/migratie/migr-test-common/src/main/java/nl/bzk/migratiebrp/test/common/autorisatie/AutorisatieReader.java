/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.autorisatie;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;

/**
 * Autorisatie reader.
 */
public interface AutorisatieReader {

    /**
     * Lees de autorisaties uit een input.
     * @param input input
     * @return autorisaties
     * @throws IOException bij lees fouten
     */
    List<Lo3Autorisatie> read(final InputStream input) throws IOException;
}
